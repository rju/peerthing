package de.peerthing.systembehavioureditor.propertyeditor.plugindatahandler;

import java.util.Vector;

import org.eclipse.core.runtime.IConfigurationElement;

/**
 * This class manages the storage of an action and its attributes.
 * 
 * @author Sebastian
 * @Reviewer
 */
public class PlugInAction {

	/**
	 * The name of the action
	 */
	private String name;

	/**
	 * The description of the action, if none existing its "no desription"
	 */
	private String description = "no description";

	/**
	 * A value to mark if custom parameter are allowed
	 */
	private boolean customParametersAllowed;

	/**
	 * The defined parameter for the action
	 */
	Vector<PlugInParameter> pluginparameter = new Vector<PlugInParameter>();

	/**
	 * The actions are created in the constructor
	 */
	public PlugInAction(IConfigurationElement elem) {
		this.name = elem.getAttribute("name");
		for (IConfigurationElement ice : elem.getChildren()) {
			if (ice.getName().equals("description")) {
				setDescription(ice.getValue());
			}
			if (ice.getName().equals("param")) {
				getPluginparameter().add(new PlugInParameter(ice));
			}
		}
		setCustomParametersAllowed(elem.getAttribute("customParametersAllowed")
				.equals("true"));
	}

	/**
	 * Returns true if custom parameter are allowed
	 * 
	 * @return isCustomParametersAllowed
	 */
	public boolean isCustomParametersAllowed() {
		return customParametersAllowed;
	}

	/**
	 * Change the value for the custom parameter
	 * 
	 * @param customParametersAllowed
	 */
	public void setCustomParametersAllowed(boolean customParametersAllowed) {
		this.customParametersAllowed = customParametersAllowed;
	}

	/**
	 * Returns the description of the action
	 * 
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Change the description of the action
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the name of the action
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Change the name of the action
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns all parameter of an action
	 * 
	 * @return PlugInParameter
	 */
	public Vector<PlugInParameter> getPluginparameter() {
		return pluginparameter;
	}

	/**
	 * Returns a special Parameter searched by its name
	 * 
	 * @param name
	 * @return PlugInParameter
	 */
	public PlugInParameter getPluginparameter(String name) {
		for (PlugInParameter pp : pluginparameter) {
			System.out.println(pp.getName());
			if (pp.getName().equals(name)) {
				return pp;
			}
		}
		return null;
	}

	/**
	 * Change the current parameter
	 * 
	 * @param pluginparameter
	 */
	public void setPluginparameter(Vector<PlugInParameter> pluginparameter) {
		this.pluginparameter = pluginparameter;
	}

}
