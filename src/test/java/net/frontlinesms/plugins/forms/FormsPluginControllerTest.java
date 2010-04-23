package net.frontlinesms.plugins.forms;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mockito.internal.verification.NoMoreInteractions;
import org.mockito.internal.verification.api.VerificationMode;

import net.frontlinesms.data.domain.Contact;
import net.frontlinesms.data.domain.Group;
import net.frontlinesms.data.repository.ContactDao;
import net.frontlinesms.data.repository.GroupDao;
import net.frontlinesms.junit.BaseTestCase;
import net.frontlinesms.plugins.forms.data.domain.Form;
import net.frontlinesms.plugins.forms.data.domain.FormField;
import net.frontlinesms.plugins.forms.data.domain.FormFieldType;
import net.frontlinesms.plugins.forms.data.domain.FormResponse;
import net.frontlinesms.plugins.forms.data.domain.ResponseValue;
import net.frontlinesms.plugins.forms.data.repository.FormDao;
import net.frontlinesms.plugins.forms.data.repository.FormResponseDao;
import net.frontlinesms.plugins.forms.request.DataSubmissionRequest;
import net.frontlinesms.plugins.forms.request.NewFormRequest;
import net.frontlinesms.plugins.forms.request.SubmittedFormData;
import net.frontlinesms.plugins.forms.response.NewFormsResponse;
import net.frontlinesms.plugins.forms.response.SubmittedDataResponse;

public class FormsPluginControllerTest extends BaseTestCase {
	private static final String UNREGISTERED_MSISDN = "+44123456879";
	private static final String CONTACT_WITH_NO_FORMS_MSISDN = "+44123456870";
	private static final Contact CONTACT_WITH_NO_FORMS = new Contact("I have no forms", CONTACT_WITH_NO_FORMS_MSISDN, null, null, null, true);
	private static final String CONTACT_INACTIVE_MSISDN = "+44123456871";
	private static final Contact CONTACT_INACTIVE = new Contact("I have no forms", CONTACT_WITH_NO_FORMS_MSISDN, null, null, null, false);
	private static final String CONTACT_OK_MSISDN = "+44123456872";
	private static final Contact CONTACT_OK = new Contact("I'm ok, thank you", CONTACT_OK_MSISDN, null, null, null, false);
	
	private static final Collection<Integer> NO_FORM_IDS = Collections.emptySet();
	private static final Collection<Form> COLLECTION_NO_FORMS = Collections.emptySet();

	public void testHandleNewFormRequest() {
		FormsPluginController controller = new FormsPluginController();

		FormDao formDao = mock(FormDao.class);
		
		controller.setFormsDao(formDao);
		when(formDao.getFormsForUser(eq(CONTACT_WITH_NO_FORMS), anyCollection())).thenReturn(COLLECTION_NO_FORMS);

		ContactDao contactDao = mock(ContactDao.class);
		controller.setContactDao(contactDao);
		when(contactDao.getFromMsisdn(UNREGISTERED_MSISDN)).thenReturn(null);
		when(contactDao.getFromMsisdn(CONTACT_INACTIVE_MSISDN)).thenReturn(CONTACT_INACTIVE);
		when(contactDao.getFromMsisdn(CONTACT_WITH_NO_FORMS_MSISDN)).thenReturn(CONTACT_WITH_NO_FORMS);
		
		NewFormRequest request = new NewFormRequest(NO_FORM_IDS);

		NewFormsResponse responseNone = controller.handleNewFormRequest(request, UNREGISTERED_MSISDN);
		assertNull("No response should be sent back to an unregistered contact", responseNone);
		
		NewFormsResponse responseInactive = controller.handleNewFormRequest(request, CONTACT_INACTIVE_MSISDN);
		assertNull("No response should be sent back to an inactive contact", responseInactive);
		
		NewFormsResponse responseNoForms = controller.handleNewFormRequest(request, CONTACT_WITH_NO_FORMS_MSISDN);
		assertEquals("No forms should be sent back to an contact unregistered for this form", 0, responseNoForms.getNewForms().size());
		
		
	}
	
	public void testHandleDataSubmissionRequest() {
		FormsPluginController controller = new FormsPluginController();

		Form formOne = createForm(2);
		
		FormDao formDao = mock(FormDao.class);
		when(formDao.getFromId(formOne.getFormMobileId())).thenReturn(formOne);
		
		FormResponseDao formResponseDao = mock(FormResponseDao.class);
		
		ContactDao contactDao = mock(ContactDao.class);
		when(contactDao.getFromMsisdn(CONTACT_OK_MSISDN)).thenReturn(CONTACT_OK);
		controller.setContactDao(contactDao);
		controller.setFormsDao(formDao);
		controller.setFormResponseDao(formResponseDao);
			
		
		SubmittedFormData formDataOne = new SubmittedFormData(formOne.getFormMobileId(), 0, createResponseValues(1));
		SubmittedFormData formDataTwo = new SubmittedFormData(formOne.getFormMobileId(), 1, createResponseValues(2));
		SubmittedFormData formDataThree = new SubmittedFormData(-1, 2, createResponseValues(2));
		SubmittedFormData formDataFour = new SubmittedFormData(formOne.getFormMobileId(), 3, createResponseValues(2));
		
		// Test with no data. Response should be empty
		Set<SubmittedFormData> submittedFormData = new HashSet<SubmittedFormData>();
		DataSubmissionRequest request = new DataSubmissionRequest(submittedFormData);
		Collection<SubmittedFormData> submittedData = controller.handleDataSubmissionRequest(request, CONTACT_OK_MSISDN).getSubmittedData();
		assertEquals(0, submittedData.size());
		
		// Test with data containing a different number of fields than the form it's pointing on. Response should be empty.
		submittedFormData.add(formDataOne);
		request = new DataSubmissionRequest(submittedFormData);
		assertEquals(0, controller.handleDataSubmissionRequest(request, CONTACT_OK_MSISDN).getSubmittedData().size());
		
		// Test with a good mobile ID and a good number of field. Should now be 1 SubmittedFormData in the Set.
		submittedFormData.add(formDataTwo);
		request = new DataSubmissionRequest(submittedFormData);
		assertEquals(1, controller.handleDataSubmissionRequest(request, CONTACT_OK_MSISDN).getSubmittedData().size());
		
		// Test with a bad mobile ID. Reponse should still contain one and only one SubmittedFormData.
		submittedFormData.add(formDataThree);
		request = new DataSubmissionRequest(submittedFormData);
		assertEquals(1, controller.handleDataSubmissionRequest(request, CONTACT_OK_MSISDN).getSubmittedData().size());
		
		// Test with data containing a different number of fields than the pointed form. Response should be empty.
		submittedFormData.add(formDataFour);
		request = new DataSubmissionRequest(submittedFormData);
		assertEquals(2, controller.handleDataSubmissionRequest(request, CONTACT_OK_MSISDN).getSubmittedData().size());
	}
	
	public void testInit() throws Throwable {
		// Test bad handler name
		FormsPluginController controller = new FormsPluginController();
		try {
			controller.setHandler("does.not.exist");
			fail("Should have thrown ClassNotFoundException.");
		} catch(ClassNotFoundException ex) { /* expected */ }
	}

	/**
	 * Create a {@link Form} with a certain number of fields and the given mobileId
	 * @param numberOfFields
	 * @param mobileId
	 * @return The created {@link Form}
	 */
	private Form createForm(int numberOfFields) {
		Form form = new Form("form" + numberOfFields);
		
		for (int i = 0; i < numberOfFields; i++) {
			form.addField(new FormField(FormFieldType.CHECK_BOX, "CB" + i));	
		}
		
		form.setPermittedGroup(new Group(null, null));

		return form;
	}

	private List<ResponseValue> createResponseValues(int nbResponseValues) {
		ArrayList<ResponseValue> responseValues = new ArrayList<ResponseValue>();
		for (int i = 0; i < nbResponseValues; i++) {
			responseValues.add(new ResponseValue("response" + i));			
		}
		
		return responseValues;
	}
}
