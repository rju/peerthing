/**
 * 
 */
package de.peerthing.simulation.interfaces;

/**
 * @author prefec2
 * 
 */
public interface IXPathAttribute extends IXPathObject {
	/**
	 * Get the value of the attribute
	 * 
	 * @return return the value
	 */
	public Object getValue();

	/**
	 * Set the value of an attribute
	 * 
	 * @param value
	 */
	public void setValue(Object value);

	/**
	 * @return returns the value as a string
	 */
	public String getAttributeStringValue();
}
