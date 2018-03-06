package de.peerthing.systembehavioureditor.propertyeditor.plugindatahandler;

import java.util.HashMap;
import java.util.Vector;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;

/**
 * This class manages the cooperation with the actionplugin. The defined actions
 * and their parameter and values are stored in the data-structure.
 * 
 * @author Sebastian
 * @Reviewer
 */
public class PlugInDataHandler {

	/**
	 * A variable to store the defined actions 
	 */
	private HashMap<String, PlugInAction> pluginActions = new HashMap<String, PlugInAction>();

	/**
	 * The actions are stored in the Map in the constructor
	 */
	public PlugInDataHandler() {
		IExtension[] extensions = Platform.getExtensionRegistry()
				.getExtensionPoint(
						"de.peerthing.systembehavioureditor.actionDescription")
				.getExtensions();
		for (IExtension ie : extensions) {
			for (IConfigurationElement elem : ie.getConfigurationElements()) {
				PlugInAction tmp = new PlugInAction(elem);
				pluginActions.put(tmp.getName(), tmp);
			}
		}

	}

	/**
	 * This method returns the actions
	 * 
	 * @return PlugInAction
	 */
	public Vector<PlugInAction> getPluginActions() {
		return new Vector<PlugInAction>(pluginActions.values());
	}

	/**
	 * This method returns a special action searched bei its name
	 * 
	 * @param item
	 * @return PligInAction
	 */
	public PlugInAction getPluginAction(String item) {
		return pluginActions.get(item);
	}

}
