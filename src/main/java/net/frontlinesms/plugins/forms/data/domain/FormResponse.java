package net.frontlinesms.plugins.forms.data.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * A response to a {@link Form}.
 * @author Alex
 */
@SuppressWarnings("serial")
@Entity
public class FormResponse implements Serializable {
	
//> COLUMN NAMES
	/** Column name for field {@link #parentForm} */
	public static final String FIELD_FORM = "parentForm";

//> INSTANCE PROPERTIES
	/** Unique id for this entity.  This is for hibernate usage. */
	@SuppressWarnings("unused")
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true,nullable=false,updatable=false)
	private long id;
	
	/** The form that this object is a response to. */
	@ManyToOne(fetch=FetchType.LAZY)
	private Form parentForm;
	
	/** The SMS message that this response was received in. */
	private String senderMsisdn;
	
	/** The data content of the form response. */
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<ResponseValue> results = new ArrayList<ResponseValue>();
	
//> CONSTRUCTORS
	/** Empty constructor for hibernate */
	FormResponse() {}
	
	/**
	 * Create a new form response.
	 * @param message
	 * @param parentForm
	 * @param results
	 */
	public FormResponse(String senderMsisdn, Form parentForm, List<ResponseValue> results) {
		this.senderMsisdn = senderMsisdn;
		this.parentForm = parentForm;
		this.results = results;
	}
	
//> ACCESSOR METHODS
	/** @return {@link #results} */
	public List<ResponseValue> getResults() {
		return this.results;
	}
	
	/** @return the MSISDN of the form submitter */
	public String getSubmitter() {
		return this.senderMsisdn;
	}
}
