/*
 * FrontlineSMS <http://www.frontlinesms.com>
 * Copyright 2007, 2008 kiwanja
 * 
 * This file is part of FrontlineSMS.
 * 
 * FrontlineSMS is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 * 
 * FrontlineSMS is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with FrontlineSMS. If not, see <http://www.gnu.org/licenses/>.
 */
package net.frontlinesms.plugins.forms;

import java.util.Collection;

import net.frontlinesms.data.domain.FrontlineMessage;
import net.frontlinesms.plugins.forms.data.FormHandlingException;
import net.frontlinesms.plugins.forms.request.FormsRequestDescription;
import net.frontlinesms.plugins.forms.response.FormsResponseDescription;

/**
 * Class for processing forms messages from their underlying format to a universal format.
 * @author Alex
 */
public interface FormsMessageHandler {
	/**
	 * Handle an incoming forms message.  If the message was not a forms message,
	 * a {@link FormHandlingException} will be thrown. 
	 * @param message the incoming message
	 * @return a description of the forms request
	 * @throws FormHandlingException if there was a problem processing the forms message
	 */
	public FormsRequestDescription handleIncomingMessage(FrontlineMessage message) throws FormHandlingException;

	/**
	 * Get the {@link FormsMessageHandler} to encode the {@link FormsRequestDescription} as text messages.
	 * @param response description of the response to send
	 * @return messages to send in response to this form
	 * @throws FormHandlingException if there was a problem generating the response
	 */
	public Collection<FrontlineMessage> handleOutgoingMessage(FormsResponseDescription response) throws FormHandlingException;

	/**
	 * Initialise the forms message handler, giving it its {@link FormsPluginController}
	 * @param formsPluginController
	 */
	public void init(FormsPluginController formsPluginController);
}
