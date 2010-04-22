/**
 * 
 */
package net.frontlinesms.plugins.forms.data.repository.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.frontlinesms.data.DuplicateKeyException;
import net.frontlinesms.data.domain.Contact;
import net.frontlinesms.data.repository.hibernate.BaseHibernateDao;
import net.frontlinesms.plugins.forms.data.domain.Form;
import net.frontlinesms.plugins.forms.data.repository.FormDao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Hibernate implementation of {@link FormDao}
 * @author Alex Anderson <alex@frontlinesms.com>
 * @author Morgan Belkadi <morgan@frontlinesms.com>
 */
public class HibernateFormDao extends BaseHibernateDao<Form> implements FormDao {

//> CONSTRUCTOR
	/** Create new instance of this DAO */
	public HibernateFormDao() {
		super(Form.class);
	}
	
	/** @see FormDao#getFormsForUser(Contact, Collection) */
	@SuppressWarnings("unchecked")
	public Collection<Form> getFormsForUser(Contact contact, Collection<Integer> currentFormIds) {
		ArrayList<String> parametersNames = new ArrayList<String>();
		parametersNames.add("finalised");
		parametersNames.add("contact");
		
		ArrayList<Object> parametersValues = new ArrayList<Object>();
		parametersValues.add(true);
		parametersValues.add(contact);
		
		List<Form> fff = (List<Form>)super.getHibernateTemplate().find("SELECT DISTINCT form FROM Form AS form");
		for (Form f : fff)
			System.err.println("FORM: " + f.getName() + " --> " + f.isFinalised());
		
		String queryString = "SELECT DISTINCT form" +
							" FROM Form AS form, GroupMembership AS mem" +
							" WHERE form." + Form.FIELD_PERMITTED + " = mem.group" +
							" AND form." + Form.FIELD_FINALISED + " = :finalised" +
							" AND mem.contact = :contact";
		
		if (currentFormIds.size() > 0) {
			Long[] longFormIds = new Long[currentFormIds.size()];
			int i = 0;
			for (Integer integer : currentFormIds)
				longFormIds[i++] = (long)integer;
			
			parametersNames.add("ids");
			parametersValues.add(longFormIds);
			queryString += " AND form." + Form.FIELD_ID + " NOT IN (:ids)";
		}
		
		System.err.println(queryString);
		
		return super.getHibernateTemplate().findByNamedParam(queryString, parametersNames.toArray(new String[parametersNames.size()]), parametersValues.toArray());
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> getList(Class<T> entityClass, String hqlQuery, Object... values) {
		return this.getHibernateTemplate().find(hqlQuery, values);
	}

	/** @see FormDao#getFromMobileId(int) */
	public Form getFromId(long id) {
		DetachedCriteria criteria = super.getCriterion();
		criteria.add(Restrictions.eq(Form.FIELD_ID, id));
		return super.getUnique(criteria);
	}
	
	/** @see FormDao#saveForm(Form) */
	public void saveForm(Form form) {
		super.saveWithoutDuplicateHandling(form);
	}

	/** @see FormDao#updateForm(Form) */
	public void updateForm(Form form) {
		// We're not checking if the form is finalised here, because the group can be modified, even finalised
		super.updateWithoutDuplicateHandling(form);
	}

	/** @see FormDao#deleteForm(Form) */
	public void deleteForm(Form form) {
		super.delete(form);
	}

	/** @see FormDao#getAllForms() */
	public Collection<Form> getAllForms() {
		return super.getAll();
	}
	
	/** @see FormDao#getCount() */
	public int getCount() {
		return super.countAll();
	}

	/** @see FormDao#finaliseForm(Form) */
	public void finaliseForm(Form form) throws IllegalStateException {
		// FIXME calculate the new mobile ID
		form.finalise();
		
		try {
			super.update(form);
		} catch (DuplicateKeyException e) {
			throw new RuntimeException("This mobile ID has already been set.");
		}
	}
}
