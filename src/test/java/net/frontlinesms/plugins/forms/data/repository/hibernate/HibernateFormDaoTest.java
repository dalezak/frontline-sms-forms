/**
 * 
 */
package net.frontlinesms.plugins.forms.data.repository.hibernate;

import net.frontlinesms.data.DuplicateKeyException;
import net.frontlinesms.data.domain.Group;
import net.frontlinesms.data.repository.GroupDao;
import net.frontlinesms.junit.HibernateTestCase;
import net.frontlinesms.plugins.forms.data.domain.Form;
import net.frontlinesms.plugins.forms.data.domain.FormField;
import net.frontlinesms.plugins.forms.data.domain.FormFieldType;
import net.frontlinesms.plugins.forms.data.repository.FormDao;

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

//> TEST METHODS

	public void testForms() throws DuplicateKeyException {
		final int MOBILE_ID_ONE = 1;
		final int MOBILE_ID_TWO = 2;
		
		Form formOne = new Form("form1");
		formDao.saveForm(formOne);
		assertEquals(1, formDao.getCount());
		
		Form formTwo = new Form("form2");
		formDao.saveForm(formTwo);
		assertEquals(2, formDao.getCount());
		
		
		Group group = new Group(new Group(null, null), "Group");
		groupDao.saveGroup(group);
		formOne.setPermittedGroup(group);
		formOne.setMobileId(MOBILE_ID_ONE);
		
		assertEquals(formOne, formDao.getFromMobileId(MOBILE_ID_ONE));
		assertNotSame(formOne, formDao.getFromMobileId(MOBILE_ID_TWO));
		
		formDao.deleteForm(formOne);
		assertEquals(1, formDao.getCount());
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

	@Override
	public void doTearDown() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void test() throws Throwable {
		// TODO Auto-generated method stub
		
	}
}
