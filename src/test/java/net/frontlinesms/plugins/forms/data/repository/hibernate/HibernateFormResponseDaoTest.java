/**
 * 
 */
package net.frontlinesms.plugins.forms.data.repository.hibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.frontlinesms.junit.HibernateTestCase;
import net.frontlinesms.plugins.forms.data.domain.Form;
import net.frontlinesms.plugins.forms.data.domain.FormField;
import net.frontlinesms.plugins.forms.data.domain.FormFieldType;
import net.frontlinesms.plugins.forms.data.domain.FormResponse;
import net.frontlinesms.plugins.forms.data.domain.ResponseValue;
import net.frontlinesms.plugins.forms.data.repository.FormDao;
import net.frontlinesms.plugins.forms.data.repository.FormResponseDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

/**
 * Test class for {@link HibernateFormResponseDao}
 * @author Alex Anderson <alex@frontlinesms.com>
 */
public class HibernateFormResponseDaoTest extends HibernateTestCase {
	
//> STATIC CONSTANTS
	private static final String TEST_MSISDN = "+123456789";
	
//> INSTANCE PROPERTIES
	@Autowired
	private FormDao formDao;
	@Autowired
	private FormResponseDao formResponseDao;

//> TEST METHODS
	public void testGetFormResponses() {
		// Create and save a form
		Form form1 = new Form("Test Form 1");
		final int FIELD_COUNT_1 = 3;
		form1.addField(new FormField(FormFieldType.CHECK_BOX, "Do you really like it?"));
		form1.addField(new FormField(FormFieldType.CHECK_BOX, "Is it is it wicked?"));
		form1.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Ayia Napa?"));
		this.formDao.saveForm(form1);

		Form form2 = new Form("Test Form 2");
		final int FIELD_COUNT_2 = 21;
		form2.addField(new FormField(FormFieldType.CHECK_BOX, "Do you really like it?"));
		form2.addField(new FormField(FormFieldType.CHECK_BOX, "Is it is it wicked?"));
		form2.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Ayia Napa?"));
		form2.addField(new FormField(FormFieldType.TEXT_AREA, "And now please write a long description of your favourite novel..."));
		form2.addField(new FormField(FormFieldType.CHECK_BOX, "Is it is it wicked?"));
		form2.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Ayia Napa?"));
		form2.addField(new FormField(FormFieldType.CHECK_BOX, "Do you really like it?"));
		form2.addField(new FormField(FormFieldType.CHECK_BOX, "Is it is it wicked?"));
		form2.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Ayia Napa?"));
		form2.addField(new FormField(FormFieldType.CHECK_BOX, "Do you really like it?"));
		form2.addField(new FormField(FormFieldType.CHECK_BOX, "Is it is it wicked?"));
		form2.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Ayia Napa?"));
		form2.addField(new FormField(FormFieldType.CHECK_BOX, "Do you really like it?"));
		form2.addField(new FormField(FormFieldType.CHECK_BOX, "Is it is it wicked?"));
		form2.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Ayia Napa?"));
		form2.addField(new FormField(FormFieldType.CHECK_BOX, "Do you really like it?"));
		form2.addField(new FormField(FormFieldType.CHECK_BOX, "Is it is it wicked?"));
		form2.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Ayia Napa?"));
		form2.addField(new FormField(FormFieldType.CHECK_BOX, "Do you really like it?"));
		form2.addField(new FormField(FormFieldType.CHECK_BOX, "Is it is it wicked?"));
		form2.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Ayia Napa?"));
		this.formDao.saveForm(form2);

		Form form3 = new Form("Child Logistics");
		final int FIELD_COUNT_3 = 31;
		form3.addField(new FormField(FormFieldType.TEXT_FIELD, "Month"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Year"));
		form3.addField(new FormField(FormFieldType.TEXT_FIELD, "Village Clinic"));
		form3.addField(new FormField(FormFieldType.TRUNCATED_TEXT, "LA6x1"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "On Hand"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Dispensed"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Losses"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Adjustment"));
		form3.addField(new FormField(FormFieldType.TRUNCATED_TEXT, "LA6x2"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "On Hand"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Dispensed"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Losses"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Adjustment"));
		form3.addField(new FormField(FormFieldType.TRUNCATED_TEXT, "Paracetamol"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "On Hand"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Dispensed"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Losses"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Adjustment"));
		form3.addField(new FormField(FormFieldType.TRUNCATED_TEXT, "ORS"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "On Hand"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Dispensed"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Losses"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Adjustment"));
		form3.addField(new FormField(FormFieldType.TRUNCATED_TEXT, "Cotrimoxazole"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "On Hand"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Dispensed"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Losses"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Adjustment"));
		form3.addField(new FormField(FormFieldType.TRUNCATED_TEXT, "Zinc"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "On Hand"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Dispensed"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Losses"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Adjustment"));
		form3.addField(new FormField(FormFieldType.TRUNCATED_TEXT, "Eye Ointment"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "On Hand"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Dispensed"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Losses"));
		form3.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Adjustment"));
		this.formDao.saveForm(form3);

		final int EXPECTED_RESPONSE_COUNT_3 = 28;
		createFormResponse(form3, "AUG", 2010, "BALAKA", 2, 5, 5, 5, 5, 5, 5, 5, 6, 9, 56, 9, 5, 1, 6, 6, 4, 4, 9, 9, 9, 6, 96, 9, 6, 6, 96, 6);
		createFormResponse(form3, "APRIL", 2010, "NYANYALA", 0, 0, 0, 0, 0, 0, 0, 0, 29, 210, 0, 0, 0, 0, 0, 0, 0, 390, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		createFormResponse(form3, "JULY", 2010, "UTALE", 35, 35, 0, 0, 38, 20, 0, 0, 1279, 245, 19, 0, 0, 0, 0, 0, 2080, 590, 29, 0, 0, 0, 0, 0, 7, 1, 0, 0);
		createFormResponse(form3, "JULY", 2010, "DISI", 102, 60, 0, 0, 252, 72, 0, 0, 156, 78, 11, 0, 9, 12, 0, 12, 378, 85, 7, 0, 0, 0, 0, 0, 9, 2, 0, 0);
		createFormResponse(form3, "APRIL", 2010, "MGOMWA", 0, 0, 0, 0, 0, 0, 0, 0, 796, 306, 8, 0, 52, 26, 0, 0, 1072, 465, 12, 0, 0, 0, 0, 0, 1, 1, 0, 0);
		createFormResponse(form3, "JULY", 2010, "NAMANOLO", 2, 2, 0, 0, 166, 145, 0, 0, 563, 232, 7, 0, 15, 13, 7, 0, 378, 321, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		createFormResponse(form3, "APRIL", 2010, "ZIDYANA", 0, 0, 0, 0, 0, 0, 0, 0, 966, 21, 0, 0, 4, 2, 0, 0, 888, 90, 0, 0, 0, 0, 0, 0, 2, 1, 0, 0);
		createFormResponse(form3, "JULY", 2010, "CHASUCHIRA", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		createFormResponse(form3, "JULY", 2010, "MTELERA", 0, 0, 0, 0, 0, 0, 0, 0, 487, 27, 6, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		createFormResponse(form3, "APRIL", 2010, "KADYALUNDA", 0, 0, 0, 0, 0, 0, 0, 0, 710, 84, 20, 0, 0, 0, 0, 0, 740, 225, 35, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		createFormResponse(form3, "JULY", 2010, "MSILILI", 318, 54, 0, 0, 36, 0, 0, 0, 271, 45, 0, 0, 0, 0, 0, 0, 372, 70, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		createFormResponse(form3, "APRIL", 2010, "CHASUCHIRA", 0, 0, 0, 0, 0, 0, 0, 0, 75, 274, 8, 0, 0, 0, 0, 0, 1134, 419, 25, 0, 0, 0, 0, 0, 4, 3, 0, 0);
		createFormResponse(form3, "APRIL", 2010, "NAMANOLO", 0, 0, 0, 0, 0, 0, 0, 0, 702, 259, 43, 0, 56, 46, 0, 0, 845, 346, 31, 100, 0, 0, 0, 0, 6, 2, 0, 1);
		createFormResponse(form3, "APRIL", 2010, "NSAMANYADA", 0, 0, 0, 0, 0, 0, 0, 0, 164, 90, 2, 0, 0, 0, 0, 0, 1030, 300, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		createFormResponse(form3, "APRIL", 2010, "NSAMANYADA", 0, 0, 0, 0, 0, 0, 0, 0, 164, 90, 2, 0, 0, 0, 0, 0, 1030, 300, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		createFormResponse(form3, "APRIL", 2010, "NAMANOLO", 0, 0, 0, 0, 0, 0, 0, 0, 702, 259, 43, 0, 56, 46, 0, 0, 845, 346, 31, 100, 0, 0, 0, 0, 6, 2, 0, 1);
		createFormResponse(form3, "APRIL", 2010, "CHASUCHIRA", 0, 0, 0, 0, 0, 0, 0, 0, 75, 274, 8, 0, 0, 0, 0, 0, 1134, 419, 25, 0, 0, 0, 0, 0, 4, 3, 0, 0);
		createFormResponse(form3, "JULY", 2010, "MSILILI", 318, 54, 0, 0, 36, 0, 0, 0, 271, 45, 0, 0, 0, 0, 0, 0, 372, 70, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		createFormResponse(form3, "APRIL", 2010, "KADYALUNDA", 0, 0, 0, 0, 0, 0, 0, 0, 710, 84, 20, 0, 0, 0, 0, 0, 740, 225, 35, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		createFormResponse(form3, "JULY", 2010, "MTELERA", 0, 0, 0, 0, 0, 0, 0, 0, 487, 27, 6, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		createFormResponse(form3, "JULY", 2010, "CHASUCHIRA", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		createFormResponse(form3, "APRIL", 2010, "ZIDYANA", 0, 0, 0, 0, 0, 0, 0, 0, 966, 21, 0, 0, 4, 2, 0, 0, 888, 90, 0, 0, 0, 0, 0, 0, 2, 1, 0, 0);
		createFormResponse(form3, "JULY", 2010, "NAMANOLO", 2, 2, 0, 0, 166, 145, 0, 0, 563, 232, 7, 0, 15, 13, 7, 0, 378, 321, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		createFormResponse(form3, "APRIL", 2010, "MGOMWA", 0, 0, 0, 0, 0, 0, 0, 0, 796, 306, 8, 0, 52, 26, 0, 0, 1072, 465, 12, 0, 0, 0, 0, 0, 1, 1, 0, 0);
		createFormResponse(form3, "JULY", 2010, "DISI", 102, 60, 0, 0, 252, 72, 0, 0, 156, 78, 11, 0, 9, 12, 0, 12, 378, 85, 7, 0, 0, 0, 0, 0, 9, 2, 0, 0);
		createFormResponse(form3, "JULY", 2010, "UTALE", 35, 35, 0, 0, 38, 20, 0, 0, 1279, 245, 19, 0, 0, 0, 0, 0, 2080, 590, 29, 0, 0, 0, 0, 0, 7, 1, 0, 0);
		createFormResponse(form3, "APRIL", 2010, "NYANYALA", 0, 0, 0, 0, 0, 0, 0, 0, 29, 210, 0, 0, 0, 0, 0, 0, 0, 390, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		createFormResponse(form3, "AUG", 2010, "BALAKA", 2, 5, 5, 5, 5, 5, 5, 5, 6, 9, 56, 9, 5, 1, 6, 6, 4, 4, 9, 9, 9, 6, 96, 9, 6, 6, 96, 6);
		
		Form form4 = new Form("Child Stats");
		final int FIELD_COUNT_4 = 47;
		form4.addField(new FormField(FormFieldType.TEXT_FIELD, "Month"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Year"));
		form4.addField(new FormField(FormFieldType.TEXT_FIELD, "Village Clinic"));
		form4.addField(new FormField(FormFieldType.TRUNCATED_TEXT, "Fever"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "New2-11M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "New12-59M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Referrals2-11M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Referrals12-59M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Deaths2-11M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Deaths12-59M"));
		form4.addField(new FormField(FormFieldType.TRUNCATED_TEXT, "Diarrhoea"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "New2-11M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "New12-59M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Referrals2-11M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Referrals12-59M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Deaths2-11M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Deaths12-59M"));
		form4.addField(new FormField(FormFieldType.TRUNCATED_TEXT, "Fast Breathing"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "New2-11M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "New12-59M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Referrals2-11M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Referrals12-59M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Deaths2-11M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Deaths12-59M"));
		form4.addField(new FormField(FormFieldType.TRUNCATED_TEXT, "Red Eye"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "New2-11M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "New12-59M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Referrals2-11M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Referrals12-59M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Deaths2-11M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Deaths12-59Mon"));
		form4.addField(new FormField(FormFieldType.TRUNCATED_TEXT, "Malnutrition"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "New2-11M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "New12-59M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Referrals2-11M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Referrals12-59M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Deaths2-11M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Deaths12-59M"));
		form4.addField(new FormField(FormFieldType.TRUNCATED_TEXT, "Pallor"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "New2-11M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "New12-59M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Referrals2-11M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Referrals12-59M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Deaths2-11M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Deaths12-59M"));
		form4.addField(new FormField(FormFieldType.TRUNCATED_TEXT, "Other"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "New2-11M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "New12-59M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Referrals2-11M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Referrals12-59M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Deaths2-11M"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Deaths12-59M"));
		form4.addField(new FormField(FormFieldType.TRUNCATED_TEXT, "New Cases By Gender"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Male"));
		form4.addField(new FormField(FormFieldType.NUMERIC_TEXT_FIELD, "Female"));
		this.formDao.saveForm(form4);

		final int EXPECTED_RESPONSE_COUNT_4 = 38;
		createFormResponse(form4, "AUG", 2011, "BALAKA", 2, 2, 2, 52, 2, 2, 2, 52, 52, 52, 6, 6, 5, 5, 9, 4, 6, 6, 6, 6, 6, 41, 6, 69, 6, 6, 6, 63, 6, 63, 3, 6, 6, 6, 6, 6, 63, 6, 6, 63, 96, 6, 2, 2);
		createFormResponse(form4, "MAY", 2010, "MPOSERA", 29, 153, 0, 1, 0, 0, 8, 17, 25, 0, 0, 0, 18, 86, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 149, 133);
		createFormResponse(form4, "JULY", 2010, "DISI", 4, 12, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 3, 7, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 13, 7);
		createFormResponse(form4, "JULY", 2010, "NAMANOLO", 2, 10, 0, 0, 0, 0, 3, 10, 0, 0, 0, 0, 8, 28, 0, 0, 0, 0, 2, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 2, 1, 2, 0, 0, 38, 51);
		createFormResponse(form4, "JULY", 2010, "CHASUCHIRA", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		createFormResponse(form4, "APRIL", 2010, "NYANYALA", 6, 47, 6, 47, 0, 0, 2, 6, 0, 0, 0, 0, 6, 36, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60, 43);
		createFormResponse(form4, "APRIL", 2010, "NSAMANYADA", 7, 16, 7, 16, 0, 0, 2, 2, 2, 2, 0, 0, 7, 32, 39, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 33, 35);
		createFormResponse(form4, "APRIL", 2010, "KADYALUNDA", 4, 23, 4, 13, 0, 0, 1, 2, 0, 2, 0, 0, 4, 27, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 35, 31);
		createFormResponse(form4, "JULY", 2010, "UTALE ", 16, 68, 1, 37, 0, 0, 2, 2, 0, 0, 0, 0, 14, 53, 3, 1, 0, 0, 3, 2, 0, 0, 0, 0, 2, 3, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 4, 2, 2, 3, 0, 0, 63, 86);
		createFormResponse(form4, "APRIL", 2010, "ZIDYANA", 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 9, 9, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 16);
		createFormResponse(form4, "APRIL", 2010, "NAMANOLO", 12, 40, 12, 40, 0, 0, 9, 12, 0, 0, 0, 0, 10, 33, 1, 3, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 58);
		createFormResponse(form4, "JULY", 2010, "MTELERA", 2, 8, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 8, 2);
		createFormResponse(form4, "APRIL", 2010, "CHASUCHIRA", 22, 50, 8, 20, 0, 0, 0, 1, 0, 1, 0, 0, 24, 39, 63, 0, 0, 0, 1, 3, 0, 0, 0, 0, 1, 1, 2, 2, 0, 0, 1, 2, 1, 2, 0, 0, 0, 5, 5, 5, 0, 0, 2, 2);
		createFormResponse(form4, "JULY", 2010, "CHIKOLONGO", 18, 53, 6, 19, 0, 0, 7, 15, 1, 0, 0, 0, 16, 52, 1, 11, 0, 0, 3, 11, 2, 8, 0, 0, 0, 3, 0, 1, 0, 0, 6, 5, 1, 0, 0, 0, 2, 0, 4, 10, 0, 0, 83, 88);
		createFormResponse(form4, "JULY", 2010, "NANDUMBO", 22, 58, 0, 2, 3, 0, 33, 51, 0, 0, 1, 0, 26, 41, 0, 0, 0, 0, 13, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 61, 44);
		createFormResponse(form4, "APRIL", 2010, "NAMWERA", 63, 196, 51, 109, 0, 0, 12, 17, 8, 9, 0, 0, 53, 141, 1, 0, 3, 0, 1, 6, 1, 6, 5, 7, 2, 6, 5, 7, 0, 0, 0, 5, 0, 5, 7, 0, 8, 29, 26, 32, 0, 0, 248, 278);
		createFormResponse(form4, "JULY", 2010, "KWITANDA", 140, 430, 6, 2, 0, 0, 30, 53, 1, 0, 0, 0, 155, 603, 5, 3, 0, 0, 16, 25, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 18, 3, 18, 0, 0, 574, 559);
		createFormResponse(form4, "APRIL", 2010, "MGOMWA", 21, 58, 21, 58, 0, 0, 9, 4, 0, 0, 0, 0, 15, 39, 0, 0, 0, 0, 2, 3, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 5, 0, 0, 20, 24);
		createFormResponse(form4, "JULY", 2010, "CHILANGA", 1, 3, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0);
		createFormResponse(form4, "JULY", 2010, "CHILANGA", 1, 3, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0);
		createFormResponse(form4, "APRIL", 2010, "NAMWERA", 63, 196, 51, 109, 0, 0, 12, 17, 8, 9, 0, 0, 53, 141, 1, 0, 3, 0, 1, 6, 1, 6, 5, 7, 2, 6, 5, 7, 0, 0, 0, 5, 0, 5, 7, 0, 8, 29, 26, 32, 0, 0, 248, 278);
		createFormResponse(form4, "APRIL", 2010, "MGOMWA", 21, 58, 21, 58, 0, 0, 9, 4, 0, 0, 0, 0, 15, 39, 0, 0, 0, 0, 2, 3, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 5, 0, 0, 20, 24);
		createFormResponse(form4, "JULY", 2010, "NANDUMBO", 22, 58, 0, 2, 3, 0, 33, 51, 0, 0, 1, 0, 26, 41, 0, 0, 0, 0, 13, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 61, 44);
		createFormResponse(form4, "JULY", 2010, "KWITANDA", 140, 430, 6, 2, 0, 0, 30, 53, 1, 0, 0, 0, 155, 603, 5, 3, 0, 0, 16, 25, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 18, 3, 18, 0, 0, 574, 559);
		createFormResponse(form4, "JULY", 2010, "CHIKOLONGO", 18, 53, 6, 19, 0, 0, 7, 15, 1, 0, 0, 0, 16, 52, 1, 11, 0, 0, 3, 11, 2, 8, 0, 0, 0, 3, 0, 1, 0, 0, 6, 5, 1, 0, 0, 0, 2, 0, 4, 10, 0, 0, 83, 88);
		createFormResponse(form4, "APRIL", 2010, "CHASUCHIRA", 22, 50, 8, 20, 0, 0, 0, 1, 0, 1, 0, 0, 24, 39, 63, 0, 0, 0, 1, 3, 0, 0, 0, 0, 1, 1, 2, 2, 0, 0, 1, 2, 1, 2, 0, 0, 0, 5, 5, 5, 0, 0, 2, 2);
		createFormResponse(form4, "JULY", 2010, "MTELERA", 2, 8, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 8, 2);
		createFormResponse(form4, "APRIL", 2010, "NAMANOLO", 12, 40, 12, 40, 0, 0, 9, 12, 0, 0, 0, 0, 10, 33, 1, 3, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 58);
		createFormResponse(form4, "APRIL", 2010, "NSAMANYADA", 7, 16, 7, 16, 0, 0, 2, 2, 2, 2, 0, 0, 7, 32, 39, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 33, 35);
		createFormResponse(form4, "APRIL", 2010, "NYANYALA", 6, 47, 6, 47, 0, 0, 2, 6, 0, 0, 0, 0, 6, 36, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60, 43);
		createFormResponse(form4, "APRIL", 2010, "ZIDYANA", 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 9, 9, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 16);
		createFormResponse(form4, "JULY", 2010, "CHASUCHIRA", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		createFormResponse(form4, "JULY", 2010, "UTALE ", 16, 68, 1, 37, 0, 0, 2, 2, 0, 0, 0, 0, 14, 53, 3, 1, 0, 0, 3, 2, 0, 0, 0, 0, 2, 3, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 4, 2, 2, 3, 0, 0, 63, 86);
		createFormResponse(form4, "JULY", 2010, "NAMANOLO", 2, 10, 0, 0, 0, 0, 3, 10, 0, 0, 0, 0, 8, 28, 0, 0, 0, 0, 2, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 2, 1, 2, 0, 0, 38, 51);
		createFormResponse(form4, "APRIL", 2010, "KADYALUNDA", 4, 23, 4, 13, 0, 0, 1, 2, 0, 2, 0, 0, 4, 27, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 35, 31);
		createFormResponse(form4, "JULY", 2010, "DISI", 4, 12, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 3, 7, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 13, 7);
		createFormResponse(form4, "MAY", 2010, "MPOSERA", 29, 153, 0, 1, 0, 0, 8, 17, 25, 0, 0, 0, 18, 86, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 149, 133);
		createFormResponse(form4, "AUG", 2011, "BALAKA", 2, 2, 2, 52, 2, 2, 2, 52, 52, 52, 6, 6, 5, 5, 9, 4, 6, 6, 6, 6, 6, 41, 6, 69, 6, 6, 6, 63, 6, 63, 3, 6, 6, 6, 6, 6, 63, 6, 6, 63, 96, 6, 2, 2);



		
		// Create and save some responses
		final int EXPECTED_RESPONSE_COUNT_1 = 5;
		createFormResponse(form1, true, true, 3);
		createFormResponse(form1, true, true, 1);
		createFormResponse(form1, true, false, 3);
		createFormResponse(form1, false, true, 3);
		createFormResponse(form1, false, false, 3);
		
		final int EXPECTED_RESPONSE_COUNT_2 = 5;
		createFormResponse(form2, true, true, 3, "Here is a little info about the novel", true, 3, true, true, 3, true, true, 3, true, true, 3, true, true, 3, true, true, 3);
		createFormResponse(form2, true, true, 3, "my opinion is that its great", true, 3, true, true, 3, true, true, 3, true, true, 3, true, true, 3, true, true, 3);
		createFormResponse(form2, true, true, 3, "actually i lied i don't like this novel", true, 3, true, true, 3, true, true, 3, true, true, 3, true, true, 3, true, true, 3);
		createFormResponse(form2, true, true, 3, "wuthering hites is a great song by kat bush", true, 3, true, true, 3, true, true, 3, true, true, 3, true, true, 3, true, true, 3);
		createFormResponse(form2, true, true, 3, "i just love hte words they are so beuatifully crafted.,", true, 3, true, true, 3, true, true, 3, true, true, 3, true, true, 3, true, true, 3);
		
		// Fetch the responses
		List<FormResponse> responses1 = this.formResponseDao.getFormResponses(form1, 0, EXPECTED_RESPONSE_COUNT_1);
		List<FormResponse> responses2 = this.formResponseDao.getFormResponses(form2, 0, EXPECTED_RESPONSE_COUNT_2);
		List<FormResponse> responses3 = this.formResponseDao.getFormResponses(form3, 0, EXPECTED_RESPONSE_COUNT_3);
		List<FormResponse> responses4 = this.formResponseDao.getFormResponses(form4, 0, EXPECTED_RESPONSE_COUNT_4);
//		List<FormResponse> responsesX = this.formResponseDao.getFormResponses(formX, 0, EXPECTED_RESPONSE_COUNT_X);

		testResponses(1, form1, FIELD_COUNT_1, responses1, EXPECTED_RESPONSE_COUNT_1);
		testResponses(2, form2, FIELD_COUNT_2, responses2, EXPECTED_RESPONSE_COUNT_2);
		testResponses(3, form3, FIELD_COUNT_3, responses3, EXPECTED_RESPONSE_COUNT_3);
		testResponses(4, form4, FIELD_COUNT_4, responses4, EXPECTED_RESPONSE_COUNT_4);
//		testResponses(X, formX, FIELD_COUNT_X, responsesX, EXPECTED_RESPONSE_COUNT_X);
		
		// TODO Delete the responses
	}
	
	private void testResponses(int testId, Form form, int expectedFieldCount, List<FormResponse> responses1, int expectedResponseCount) {
		// Check that all the responses are present
		assertEquals("Test " + testId, expectedResponseCount, responses1.size());
		
		// Check that all the responses have the correct number of results
		for(FormResponse r : responses1) {
			assertEquals("Test " + testId, expectedFieldCount, r.getResults().size());
		}
	}
	
	private void createFormResponse(Form form, Object... fieldValues) {
		ArrayList<ResponseValue> values = new ArrayList<ResponseValue>();
		for(Object o : fieldValues) values.add(new ResponseValue(o.toString()));
		FormResponse r = new FormResponse(TEST_MSISDN, form, values);
		this.formResponseDao.saveResponse(r);
	}
	
//> TEST SETUP/TEARDOWN
	
//> ACCESSORS
	/** @param formDao The DAO to use for the test. */
	@Required
	public void setFormDao(FormDao formDao) {
		this.formDao = formDao;
	}
	/** @param formDao The DAO to use for the test. */
	@Required
	public void setFormResponseDao(FormResponseDao formResponseDao) {
		this.formResponseDao = formResponseDao;
	}
}
