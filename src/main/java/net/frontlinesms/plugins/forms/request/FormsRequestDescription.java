/**
 * 
 */
package net.frontlinesms.plugins.forms.request;

/**
 * A decoded incoming forms message.
 * @author Alex Anderson <alex@frontlinesms.com>
 */
public abstract class FormsRequestDescription {
	
//> INSTANCE PROPERTIES
	/** Port that this contact should be sent forms on. */
	private Integer smsPort;
	/** Indicates whether this client can receive data messages out-of-session.  If <code>true</code>, we can send
	 * forms directly to this client; if <code>false</code> we should only send text notifications of new forms, and
	 * wait for the client to specifically request forms before sending a data message. */
	private boolean outOfSessionSmsSupported;
	
//> ACCESSOR METHODS
	/** @param smsPort the smsPort to set */
	public void setSmsPort(int smsPort) {
		this.smsPort = smsPort;
	}
	/** @return the smsPort */
	public Integer getSmsPort() {
		return smsPort;
	}

	/** @param outOfSessionSmsSupported new value for {@link #outOfSessionSmsSupported} */
	public void setOutOfSessionSmsSupported(boolean outOfSessionSmsSupported) {
		this.outOfSessionSmsSupported = outOfSessionSmsSupported;
	}
	/** @return {@link #outOfSessionSmsSupported} */
	public boolean isOutOfSessionSmsSupported() {
		return outOfSessionSmsSupported;
	}
}
