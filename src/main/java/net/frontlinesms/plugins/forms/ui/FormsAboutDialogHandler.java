/**
 * 
 */
package net.frontlinesms.plugins.forms.ui;

import net.frontlinesms.plugins.forms.FormsPluginController;
import net.frontlinesms.ui.ThinletUiEventHandler;
import net.frontlinesms.ui.UiGeneratorController;

/**
 * UI handler for dialog describing the forms plugin and its current state.
 * @author Alex Anderson <alex@frontlinesms.com>
 */
public class FormsAboutDialogHandler implements ThinletUiEventHandler {

//> CONSTANTS
	/** Path to the XML file containing the UI layout descriptor */
	private static final String DIALOG_XML_PATH = "/ui/plugins/forms/dgAbout.xml";
	/** Thinlet UI component name: label containing version of the Forms plugin */
	private static final String COMPONENT_PLUGIN_VERSION = "plugin.forms.plugin.version";
	/** Thinlet UI component name: label containing the class of the Forms message handler */
	private static final String COMPONENT_HANDLER_CLASS = "plugin.forms.handler.class";
	/** Thinlet UI component name: label containing the version of the Forms message handler */
	private static final String COMPONENT_HANDLER_VERSION = "plugin.forms.handler.version";
	
//> INSTANCE FIELDS
	private UiGeneratorController ui;
	private Object dialog;

	public FormsAboutDialogHandler(UiGeneratorController ui) {
		this.ui = ui;
	}

	private void init(FormsPluginController pluginController) {
		// Load the dialog UI
		this.dialog = ui.loadComponentFromFile(DIALOG_XML_PATH, this);
		
		// init fields
		ui.setText(find(COMPONENT_PLUGIN_VERSION), getVersionAsString(pluginController));
		ui.setText(find(COMPONENT_HANDLER_CLASS), pluginController.getHandler().getClass().getName());
		ui.setText(find(COMPONENT_HANDLER_VERSION), getVersionAsString(pluginController.getHandler()));
	}
	
//> UI EVENT METHODS
	/** Remove this dialog from view. */
	public void removeDialog() {
		ui.removeDialog(this.dialog);
	}
	
	/** Open external web browser at a particular page */
	public void openBrowser(String url) {
		ui.openBrowser(url);
	}

//> UI HELPER METHODS
	/** Find a UI component within {@link #dialog} */
	private Object find(String componentName) {
		return ui.find(dialog, componentName);
	}

//> ACCESSORS
	/** @return {@link #dialog} */
	private Object getDialog() {
		return this.dialog;
	}

//> STATIC METHODS
	/** Create and show a new Forms about dialog. */
	public static void createAndShow(UiGeneratorController ui,
			FormsPluginController pluginController) {
		FormsAboutDialogHandler handler = new FormsAboutDialogHandler(ui);
		handler.init(pluginController);
		ui.add(handler.getDialog());
	}

	/** @return implementation version number of a class as a human-readable string */
	private static String getVersionAsString(Object obj) {
		String reportedVersion = obj.getClass().getPackage().getImplementationVersion();
		String displayVersion;
		if(reportedVersion != null) {
			displayVersion = reportedVersion;
		} else {
			displayVersion = "<?>";
		}
		return displayVersion;
	}
}
