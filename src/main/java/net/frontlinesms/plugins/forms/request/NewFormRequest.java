/**
 * 
 */
package net.frontlinesms.plugins.forms.request;

import java.util.Collection;

/**
 * A request for new forms.
 * @author Alex
 */
public class NewFormRequest extends FormsRequestDescription {
	/** IDs of the forms that this user already has. */
	private final Collection<Integer> currentFormIds;

	/**
	 * Create a new instance of this class.
	 * @param currentFormIds
	 */
	public NewFormRequest(Collection<Integer> currentFormIds) {
		this.currentFormIds = currentFormIds;
	}
	
	/** @return {@link #currentFormIds} */
	public Collection<Integer> getCurrentFormIds() {
		return this.currentFormIds;
	}
}
