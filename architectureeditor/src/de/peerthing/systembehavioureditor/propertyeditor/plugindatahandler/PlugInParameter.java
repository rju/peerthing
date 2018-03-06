package de.peerthing.systembehavioureditor.propertyeditor.plugindatahandler;

import java.util.HashMap;

import org.eclipse.core.runtime.IConfigurationElement;

/**
 * This class manages the storage of a parameter and its attributes.
 * 
 * @author Sebastian
 * @Reviewer
 */
public class PlugInParameter {

	/**
	 * The description of the parameter, if none existing its "no desription"
	 */
	private String description = "no description";

	/**
	 * The name of the parameter
	 */
	private String name;

	/**
	 * Markes if the parameter is required or optional
	 */
	private boolean required;

	/**
	 * A value to mark if a reference is allowed
	 */
	private boolean referenceAllowed;

	/**
	 * A value to mark if a value is allowed
	 */
	private boolean valueAllowed;

	/**
	 * The defined possible values for the parameter
	 */
	HashMap<String, String> possibleValues = new HashMap<String, String>();

	/**
	 * The parameter are created in the constructor
	 */
	public PlugInParameter(IConfigurationElement elem) {
		this.name = elem.getAttribute("name");
		setRequired(elem.getAttribute("required").equals("true"));
		setReferenceAllowed(elem.getAttribute("referenceAllowed")
				.equals("true"));
		setValueAllowed(elem.getAttribute("valueAllowed").equals("true"));

		for (IConfigurationElement ice : elem.getChildren()) {
			if (ice.getName().equals("description")) {
				setDescription(ice.getValue());
			} else if (ice.getName().equals("possibleValue")) {
				possibleValues.put(ice.getAttribute("value"), ice
						.getAttribute("description"));

			}
		}
	}

	/**
	 * Returns the description of the parameter
	 * 
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Change the description of the parameter
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the name of the parameter
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Change the name of the parameter
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the possible values of the parameter
	 * 
	 * @return HashMap
	 */
	public HashMap<String, String> getPossibleValues() {
		return possibleValues;
	}

	/**
	 * Change the possible values of the parameter
	 * 
	 * @param possibleValues
	 */
	public void setPossibleValues(HashMap<String, String> possibleValues) {
		this.possibleValues = possibleValues;
	}

	/**
	 * Its true if a reference is allowed
	 * 
	 * @return boolean
	 */
	public boolean isReferenceAllowed() {
		return referenceAllowed;
	}

	/**
	 * Change if a reference is allowed
	 * 
	 * @param referenceAllowed
	 */
	public void setReferenceAllowed(boolean referenceAllowed) {
		this.referenceAllowed = referenceAllowed;
	}

	/**
	 * Its true if the parameter is required
	 * 
	 * @return boolean
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * Change if the parameter should be required
	 * 
	 * @param required
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}

	/**
	 * Its true if a value is allowed
	 * 
	 * @return boolean
	 */
	public boolean isValueAllowed() {
		return valueAllowed;
	}

	/**
	 * Change if a value should be allowed
	 * 
	 * @param valueAllowed
	 */
	public void setValueAllowed(boolean valueAllowed) {
		this.valueAllowed = valueAllowed;
	}

	/**
	 * Returns the description of a special value
	 * 
	 * @param value
	 * @return String
	 */
	public String getValueDescription(String value) {
		if (possibleValues.containsKey(value)) {
			if (possibleValues.get(value) != null) {
				return possibleValues.get(value);
			} else {
				return "no description";
			}
		} else {
			return "no description";
		}
	}
}
