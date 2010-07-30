/**
 * 
 */
package net.frontlinesms.plugins.forms.data.repository.hibernate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import net.frontlinesms.data.DuplicateKeyException;
import net.frontlinesms.data.domain.Contact;
import net.frontlinesms.data.domain.Group;
import net.frontlinesms.data.repository.ContactDao;
import net.frontlinesms.data.repository.GroupDao;
import net.frontlinesms.data.repository.GroupMembershipDao;
import net.frontlinesms.junit.HibernateTestCase;
import net.frontlinesms.plugins.forms.data.domain.Form;
import net.frontlinesms.plugins.forms.data.domain.FormField;
import net.frontlinesms.plugins.forms.data.domain.FormFieldType;
import net.frontlinesms.plugins.forms.data.domain.FormResponse;
import net.frontlinesms.plugins.forms.data.domain.ResponseValue;
import net.frontlinesms.plugins.forms.data.repository.FormDao;
import net.frontlinesms.plugins.forms.data.repository.FormResponseDao;
import net.frontlinesms.plugins.forms.request.NewFormRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

/**
 * Test class for {@link HibernateFormDao}
 * @author Morgan Belkadi <morgan@frontlinesms.com>
 */
public class HibernateFormResponseDaoTest extends HibernateTestCase {
//> PROPERTIES
	@Autowired
	private FormDao formDao;
	@Autowired
	private FormResponseDao formResponseDao;
	@Autowired
	private GroupDao groupDao;
	@Autowired
	private ContactDao contactDao;
	@Autowired
	private GroupMembershipDao groupMembershipDao;

//> TEST METHODS
	
	public void testSaveAndDeleteResponse () {
		Form formOne = new Form("form1");
		formOne.addField(new FormField(FormFieldType.CHECK_BOX, "cbTest"));
		
		Form formTwo = new Form("form2");
		formTwo.addField(new FormField(FormFieldType.CURRENCY_FIELD, "currTest"));
		formTwo.addField(new FormField(FormFieldType.DATE_FIELD, "dateTest"));
		
		formDao.saveForm(formOne);
		formDao.saveForm(formTwo);
		
		FormResponse responseOne = new FormResponse("+1555-9999", formOne, Arrays.asList(new ResponseValue[] { new ResponseValue("Value 1")}));
		FormResponse responseTwo = new FormResponse("+1555-9999", formOne, Arrays.asList(new ResponseValue[] { new ResponseValue("Value 2")}));
		FormResponse responseThree = new FormResponse("+1555-9999", formTwo, Arrays.asList(new ResponseValue[] { new ResponseValue("Value 3"), new ResponseValue("Value 4")}));
		formResponseDao.saveResponse(responseOne);
		formResponseDao.saveResponse(responseTwo);
		
		assertEquals(2, formResponseDao.getFormResponseCount(formOne));
		formResponseDao.delete(responseOne);
		assertEquals(1, formResponseDao.getFormResponseCount(formOne));
		assertEquals(0, formResponseDao.getFormResponseCount(formTwo));

		formResponseDao.saveResponse(responseThree);
		assertEquals(1, formResponseDao.getFormResponseCount(formTwo));
	}
	
	public void testSaveAndDeleteFormsWithResponse () {
		Form formOne = new Form("form1");
		formOne.addField(new FormField(FormFieldType.CHECK_BOX, "cbTest"));
		
		Form formTwo = new Form("form2");
		formTwo.addField(new FormField(FormFieldType.CURRENCY_FIELD, "currTest"));
		formTwo.addField(new FormField(FormFieldType.DATE_FIELD, "dateTest"));
		
		formDao.saveForm(formOne);
		formDao.saveForm(formTwo);
		
		FormResponse responseOne = new FormResponse("+1555-9999", formOne, Arrays.asList(new ResponseValue[] { new ResponseValue("Value 1")}));
		FormResponse responseTwo = new FormResponse("+1555-9999", formOne, Arrays.asList(new ResponseValue[] { new ResponseValue("Value 2")}));
		FormResponse responseThree = new FormResponse("+1555-9999", formTwo, Arrays.asList(new ResponseValue[] { new ResponseValue("Value 3"), new ResponseValue("Value 4")}));
		formResponseDao.saveResponse(responseOne);
		formResponseDao.saveResponse(responseTwo);
		
		
		assertEquals(2, formResponseDao.getFormResponseCount(formOne));
		formResponseDao.delete(responseOne);
		assertEquals(1, formResponseDao.getFormResponseCount(formOne));
		
		assertEquals(0, formResponseDao.getFormResponseCount(formTwo));
		formResponseDao.saveResponse(responseThree);
		assertEquals(1, formResponseDao.getFormResponseCount(formTwo));
		
		FormResponse responseFour = new FormResponse("+1555-9999", formOne, Arrays.asList(new ResponseValue[] { new ResponseValue("Value 4")}));
		formResponseDao.saveResponse(responseFour);
		assertEquals(2, formResponseDao.getFormResponseCount(formOne));
		formDao.deleteForm(formOne);
		assertEquals(0, formResponseDao.getFormResponseCount(formOne));
		formDao.deleteForm(formTwo);
		assertEquals(0, formResponseDao.getFormResponseCount(formTwo));
	}
	
//> HELPER METHODS
	
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
