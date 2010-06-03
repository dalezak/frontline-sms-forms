/**
 * 
 */
package net.frontlinesms.plugins.forms;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;

import net.frontlinesms.FrontlineSMS;
import net.frontlinesms.data.domain.Contact;
import net.frontlinesms.data.domain.Group;
import net.frontlinesms.data.domain.FrontlineMessage;
import net.frontlinesms.data.events.EntityDeleteWarning;
import net.frontlinesms.data.repository.ContactDao;
import net.frontlinesms.events.EventBus;
import net.frontlinesms.events.EventObserver;
import net.frontlinesms.events.FrontlineEventNotification;
import net.frontlinesms.listener.IncomingMessageListener;
import net.frontlinesms.plugins.BasePluginController;
import net.frontlinesms.plugins.PluginController;
import net.frontlinesms.plugins.PluginControllerProperties;
import net.frontlinesms.plugins.PluginInitialisationException;
import net.frontlinesms.plugins.forms.data.FormHandlingException;
import net.frontlinesms.plugins.forms.data.domain.Form;
import net.frontlinesms.plugins.forms.data.domain.FormResponse;
import net.frontlinesms.plugins.forms.data.domain.ResponseValue;
import net.frontlinesms.plugins.forms.data.repository.FormResponseDao;
import net.frontlinesms.plugins.forms.data.repository.FormDao;
import net.frontlinesms.plugins.forms.request.DataSubmissionRequest;
import net.frontlinesms.plugins.forms.request.FormsRequestDescription;
import net.frontlinesms.plugins.forms.request.NewFormRequest;
import net.frontlinesms.plugins.forms.request.SubmittedFormData;
import net.frontlinesms.plugins.forms.response.FormsResponseDescription;
import net.frontlinesms.plugins.forms.response.NewFormsResponse;
import net.frontlinesms.plugins.forms.response.SubmittedDataResponse;
import net.frontlinesms.plugins.forms.ui.FormsThinletTabController;
import net.frontlinesms.ui.UiGeneratorController;
import net.frontlinesms.ui.i18n.InternationalisationUtils;
import net.frontlinesms.ui.i18n.TextResourceKeyOwner;

/**
 * Controller for the FrontlineForms plugin.
 * @author Alex
 */
@PluginControllerProperties(name="Forms", iconPath="/icons/form.png",
		springConfigLocation="classpath:net/frontlinesms/plugins/forms/frontlineforms-spring-hibernate.xml",
		hibernateConfigPath="classpath:net/frontlinesms/plugins/forms/frontlineforms.hibernate.cfg.xml")
@TextResourceKeyOwner
public class FormsPluginController extends BasePluginController implements IncomingMessageListener, EventObserver {
//> CONSTANTS
	/** Filename and path of the XML for the FrontlineForms tab. */
	private static final String XML_FORMS_TAB = "/ui/plugins/forms/formsTab.xml";
	
	/** I18n Text key: SMS text: "There is a new form available: xyz" */
	private static final String I18N_NEW_FORMS_SMS = "sms.form.available";
	
//> INSTANCE PROPERTIES
	/** the {@link FrontlineSMS} instance that this plugin is attached to */
	private FrontlineSMS frontlineController;
	/** the {@link FormsMessageHandler} for processing incoming and outgoing messages */
	private FormsMessageHandler formsMessageHandler;
	/** DAO for forms */
	private FormDao formDao;
	/** DAO for contacts */
	private ContactDao contactDao;
	/** DAO for form responses */
	private FormResponseDao formResponseDao;
	
//> CONFIG METHODS
	/** @see PluginController#init(FrontlineSMS, ApplicationContext) */
	public void init(FrontlineSMS frontlineController, ApplicationContext applicationContext) throws PluginInitialisationException {
		this.frontlineController = frontlineController;
		this.contactDao = frontlineController.getContactDao();
		this.frontlineController.addIncomingMessageListener(this);
		
		try {
			this.formDao = (FormDao) applicationContext.getBean("formDao");
			this.formResponseDao = (FormResponseDao) applicationContext.getBean("formResponseDao");
			((EventBus) applicationContext.getBean("eventBus")).registerObserver(this);
			
			String handlerClassName = FormsProperties.getInstance().getHandlerClassName();
			setHandler(handlerClassName);
		} catch(Throwable t) {
			log.warn("Unable to load form handler class.", t);
			throw new PluginInitialisationException(t);
		}
	}
	
	void setHandler(String handlerClassName) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		this.formsMessageHandler = (FormsMessageHandler) Class.forName(handlerClassName).newInstance();
		this.formsMessageHandler.init(this);
	}

//> ACCESSORS
	/** @return {@link #formsMessageHandler} */
	public FormsMessageHandler getHandler() {
		return this.formsMessageHandler;
	}
	
	/** @return {@link #frontlineController} */
	public FrontlineSMS getFrontlineController() {
		return this.frontlineController;
	}
	/** @param frontlineController new value for {@link #frontlineController} */
	void setFrontlineController(FrontlineSMS frontlineController) {
		this.frontlineController = frontlineController;
	}
	void setFormsMessageHandler(FormsMessageHandler formsMessageHandler) {
		this.formsMessageHandler = formsMessageHandler;
	}

	/**
	 * Set {@link #formResponseDao}
	 * @param formResponseDao new value for {@link #formResponseDao}
	 */
	@Required
	public void setFormResponseDao(FormResponseDao formResponseDao) {
		this.formResponseDao = formResponseDao;
	}
	
	/**
	 * Set {@link #formDao}
	 * @param formsDao new value for {@link #formDao}
	 */
	@Required
	public void setFormsDao(FormDao formsDao) {
		this.formDao = formsDao;
	}
	public void setContactDao(ContactDao contactDao) {
		this.contactDao = contactDao;
	}
	
	/** @see net.frontlinesms.plugins.PluginController#deinit() */
	public void deinit() {
		this.frontlineController.removeIncomingMessageListener(this);
	}

	/** @see BasePluginController#initThinletTab(UiGeneratorController)  */
	public Object initThinletTab(UiGeneratorController uiController) {
		FormsThinletTabController tabController = new FormsThinletTabController(this, uiController);
		tabController.setContactDao(this.frontlineController.getContactDao());
		tabController.setGroupMembershipDao(this.frontlineController.getGroupMembershipDao());
		tabController.setFormsDao(formDao);
		tabController.setFormResponseDao(formResponseDao);

		Object formsTab = uiController.loadComponentFromFile(XML_FORMS_TAB, tabController);
		tabController.setTabComponent(formsTab);
		
		tabController.refresh();
		
		return formsTab;
	}

//> EVENT HANDLING METHODS
	/** Process a new message coming into the system. */
	public void incomingMessageEvent(FrontlineMessage message) {
		try {
			FormsRequestDescription request = this.formsMessageHandler.handleIncomingMessage(message);
			
			FormsResponseDescription response;
			if(request instanceof DataSubmissionRequest) {
				response = handleDataSubmissionRequest((DataSubmissionRequest)request, message.getSenderMsisdn());
			} else if(request instanceof NewFormRequest) {
				response = handleNewFormRequest((NewFormRequest)request, message.getSenderMsisdn());
			} else {
				throw new IllegalStateException("Unknown form request description type: " + request);
			}
			
			// If there is a response to send back to the form submitter, then process it
			if(response != null) {
				handleResponse(request.getSmsPort(), message.getSenderMsisdn(), response);
			}
		} catch (Throwable t) {
			log.info("There was a problem handling incoming message as forms message.", t);
		}
	}
	
	public void notify(FrontlineEventNotification notification) {
		if(notification instanceof EntityDeleteWarning<?>) {
			EntityDeleteWarning<?> deleteWarning = (EntityDeleteWarning<?>) notification;
			Object dbEntity = deleteWarning.getDatabaseEntity();
			
			if(dbEntity instanceof Group) {
				// de-reference any groups which are attached to forms
				this.formDao.dereferenceGroup((Group) dbEntity);
				return;
			}
		}
	}
	
	/* default access to allow for unit test */
	void handleResponse(Integer smsPort, String senderMsisdn, FormsResponseDescription response) throws FormHandlingException {
		Collection<FrontlineMessage> responseMessages = this.formsMessageHandler.handleOutgoingMessage(response);
		log.info("Sending forms response.  Response messages: " + responseMessages.size());
		for(FrontlineMessage responseMessage : responseMessages) {
			// Make sure that the response is sent to the correct recipient!
			responseMessage.setRecipientMsisdn(senderMsisdn);
			if(smsPort != null) {
				responseMessage.setRecipientSmsPort(smsPort);
			}
			this.frontlineController.sendMessage(responseMessage);
		}
		log.trace("Response messages sent.");	
	}

//> PRIVATE HELPER METHODS
	/**
	 * Handles a request of type: {@link DataSubmissionRequest}
	 * @param request 
	 * @param senderMsisdn 
	 * @return a response of type {@link SubmittedDataResponse}
	 */
	/* default access to allow for unit test */
	SubmittedDataResponse handleDataSubmissionRequest(DataSubmissionRequest request, String senderMsisdn) {
		/** List of data IDs of the successfully processed responses */
		Collection<SubmittedFormData> dataIds = new HashSet<SubmittedFormData>();
		for(SubmittedFormData submittedData : request.getSubmittedData()) {
			Form form = this.formDao.getFromId(submittedData.getFormId());
			if(form == null) {
				log.warn("No form found for submitted data with dataId: " + submittedData.getDataId());
				continue;
			}
			
			List<ResponseValue> responseValues = submittedData.getDataValues();
			if(form.getEditableFieldCount() != responseValues.size()) {
				log.info("Editable field count mismatch: submitted " + responseValues.size() + "/" + form.getEditableFieldCount());
				continue;
			}
			
			this.formResponseDao.saveResponse(new FormResponse(senderMsisdn, form, responseValues));
			dataIds.add(submittedData);
		}
		
		return new SubmittedDataResponse(dataIds);		
	}
	
	/**
	 * Handles a request of type {@link NewFormRequest}
	 * @param request
	 * @param message
	 * @return a response of type {@link NewFormsResponse}, or <code>null</code> if no response should be sent.
	 */
	/* default access to allow for unit test */
	NewFormsResponse handleNewFormRequest(NewFormRequest request, String senderMsisdn) {
		Contact contact = this.contactDao.getFromMsisdn(senderMsisdn);
		if(contact == null || !contact.isActive()) {
			// This contact is not known, so there cannot be any forms available for him
			return null;
		} else {
			Collection<Form> newForms = this.formDao.getFormsForUser(contact, ((NewFormRequest)request).getCurrentFormIds());
			return new NewFormsResponse(contact, newForms);
		}
	}

	/**
	 * Send a form to a collection of contacts.
	 * @param form the form to send
	 * @param contacts the contacts to send the form to
	 */
	public void sendForm(Form form, Collection<Contact> contacts) {
		// Send a text SMS to each contact informing them that a new form is available.
		String messageContent = InternationalisationUtils.getI18NString(I18N_NEW_FORMS_SMS, form.getName());
		for(Contact c : contacts) {
			// TODO if it is possible, we could send forms directly to people here
			this.frontlineController.sendTextMessage(c.getPhoneNumber(), messageContent);
		}
	}
	
	/**
	 * @return The {@link FormDao of the plugin controller}
	 */
	public FormDao getFormDao() {
		return formDao;
	}
}
