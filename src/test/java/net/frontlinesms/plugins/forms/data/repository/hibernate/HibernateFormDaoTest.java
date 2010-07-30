/**
 * 
 */
package net.frontlinesms.plugins.forms.data.repository.hibernate;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import net.frontlinesms.data.DuplicateKeyException;
import net.frontlinesms.data.domain.Contact;
import net.frontlinesms.data.domain.Group;
import net.frontlinesms.data.repository.ContactDao;
import net.frontlinesms.data.repository.GroupDao;
import net.frontlinesms.data.repository.GroupMembershipDao;
import net.frontlinesms.junit.HibernateTestCase;
import net.frontlinesms.plugins.forms.data.domain.Form;
import net.frontlinesms.plugins.forms.data.repository.FormDao;
import net.frontlinesms.plugins.forms.request.NewFormRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

/**
 * Test class for {@link HibernateFormDao}
 * @author Morgan Belkadi <morgan@frontlinesms.com>
 */
public class HibernateFormDaoTest extends HibernateTestCase {
//> PROPERTIES
	@Autowired
	private FormDao formDao;
	@Autowired
	private GroupDao groupDao;
	@Autowired
	private ContactDao contactDao;
	@Autowired
	private GroupMembershipDao groupMembershipDao;

//> TEST METHODS

	public void testSaveAndDeleteForms() throws DuplicateKeyException {
		Form formOne = new Form("form1");
		formDao.saveForm(formOne);
		assertEquals(1, formDao.getCount());
		
		Form formTwo = new Form("form2");
		formDao.saveForm(formTwo);
		assertEquals(2, formDao.getCount());
		
		final int FORM_ID_ONE = (int)formOne.getFormMobileId();
		final int FORM_ID_TWO = (int)formTwo.getFormMobileId();
		
		Group group = new Group(new Group(null, null), "Group");
		groupDao.saveGroup(group);
		formOne.setPermittedGroup(group);
		formOne.finalise();
		
		assertEquals(formOne, formDao.getFromId(FORM_ID_ONE));
		assertNotSame(formOne, formDao.getFromId(FORM_ID_TWO));
		
		formDao.deleteForm(formOne);
		assertEquals(1, formDao.getCount());
	}
	
	public void testFormsForUser () throws DuplicateKeyException {
		Form formOne = new Form("Form with no groups");
		Form formTwo = new Form("Form with group");
		Form formThree = new Form("Form with group");
		formDao.saveForm(formOne);
		formDao.saveForm(formTwo);
		formDao.saveForm(formThree);
		
		Group groupOne = new Group(new Group(null, null), "Group without forms");
		Group groupTwo = new Group(new Group(null, null), "Group with forms");
		Group groupThree = new Group(new Group(null, null), "Another group with forms");
		
		groupDao.saveGroup(groupOne);
		groupDao.saveGroup(groupTwo);
		groupDao.saveGroup(groupThree);
		
		Contact contactOne = createContact("Contact One in no group");
		Contact contactTwo = createContact("Contact Two in one group with forms", groupOne, groupTwo);
		Contact contactThree = createContact("Contact Three in two group with forms", groupTwo, groupThree);
		
		final Collection<Integer> NO_FORM_IDS = Collections.emptySet();
		
		NewFormRequest request = new NewFormRequest(NO_FORM_IDS);
		// Test with a contact with no groups. Shouldn't return any form.
		assertEquals(0, this.formDao.getFormsForUser(contactOne, request.getCurrentFormIds()).size());
		// Test with a contact whose groups are not attached to a form. Shouldn't return any form.
		assertEquals(0, this.formDao.getFormsForUser(contactTwo, request.getCurrentFormIds()).size());
		
		formOne.setPermittedGroup(groupTwo);
		formOne.finalise();
		
		// Test with a contact with one group attached to a form. Should return one form.
		assertEquals(1, this.formDao.getFormsForUser(contactTwo, request.getCurrentFormIds()).size());
		
		formTwo.setPermittedGroup(groupThree);
		formTwo.finalise();
		// Test with a contact with two groups attached to two different forms. Should then return both forms.
		assertEquals(2, this.formDao.getFormsForUser(contactThree, request.getCurrentFormIds()).size());
		
			
		Collection<Integer> formIds = new HashSet<Integer>();
		formIds.add(formOne.getFormMobileId());
		request = new NewFormRequest(formIds);
		// Test with the last contact, but specifying it already has one of the forms. Should then return only the other form.
		assertEquals(1, this.formDao.getFormsForUser(contactThree, request.getCurrentFormIds()).size());
		
		formDao.deleteForm(formTwo);
		// Test with the last contact, but deleting the form it was still registered one. Shouldn't return any form.
		assertEquals(0, this.formDao.getFormsForUser(contactThree, request.getCurrentFormIds()).size());
	}

	public void testDereferenceGroup() throws DuplicateKeyException {
		Group group1 = new Group(new Group(null, null), "Test group");
		this.groupDao.saveGroup(group1);
		
		Group group2 = new Group(group1, "Test child group A");
		this.groupDao.saveGroup(group2);
		
		Group group3 = new Group(group1, "Test child group B");
		this.groupDao.saveGroup(group3);

		Form form1 = new Form("Test form 1");
		form1.setPermittedGroup(group1);
		this.formDao.saveForm(form1);

		Form form2 = new Form("Test form 2");
		form2.setPermittedGroup(group2);
		this.formDao.saveForm(form2);

		Form form3 = new Form("Test form 3");
		form3.setPermittedGroup(group3);
		this.formDao.saveForm(form3);

		this.groupDao.deleteGroup(group1, false);
		this.groupDao.deleteGroup(group2, false);
		this.groupDao.deleteGroup(group3, false);
		
		// check that dereferencing the groups and THEN deleting them will succeed
		assertTrue(this.formDao.getFromId(form1.getFormMobileId()).getPermittedGroup() == null);
		assertTrue(this.formDao.getFromId(form2.getFormMobileId()).getPermittedGroup() == null);
		assertTrue(this.formDao.getFromId(form3.getFormMobileId()).getPermittedGroup() == null);
	}
	
//> HELPER METHODS
	/** Creates a new contact with a given name, and a generated phone number. */
	private Contact createContact(String name, Group ... groups) {
		// Generate a random phone number, as we won't be testing with this  TODO we may be testing with phone number at a later date
		String phoneNumber = Integer.toString(name.hashCode());
		Contact contact = new Contact(name, phoneNumber, null, null, null, true);
		
		try {
			this.contactDao.saveContact(contact);
		} catch (DuplicateKeyException e) {
			throw new IllegalStateException("Failed to set up test.  Could not save contact with name: " + name + " and phoneNumber: " + phoneNumber);
		}
		
		for (Group group : groups) {
			this.groupMembershipDao.addMember(group, contact);
		}
		
		return contact;
	}
	
//> TEST SETUP/TEARDOWN
	
//> ACCESSORS
	/** @param formDao The DAO to use for the test. */
	@Required
	public void setFormDao(FormDao formDao) {
		this.formDao = formDao;
	}
	
	/** @param groupDao The DAO to use for the test. */
	@Required
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	/** @param contactDao The DAO to use for the test. */
	@Required
	public void setContactDao(ContactDao contactDao) {
		this.contactDao = contactDao;
	}
	
	/** @param groupMembershipDao The DAO to use for the test. */
	@Required
	public void setGroupMembershipDao(GroupMembershipDao groupMembershipDao) {
		this.groupMembershipDao = groupMembershipDao;
	}
}
