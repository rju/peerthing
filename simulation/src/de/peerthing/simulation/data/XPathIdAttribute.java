/**
 * 
 */
package de.peerthing.simulation.data;

import de.peerthing.simulation.interfaces.IXPathObject;

/**
 * @author prefec2
 * 
 */
public class XPathIdAttribute extends XPathAttribute {

	/**
	 * @param name
	 * @param value
	 */
	public XPathIdAttribute(String name, IXPathObject value) {
		super(name, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulation.ISimulation#getAttributeStringValue()
	 */
	public String getAttributeStringValue() {
		return String.valueOf(((XPathObject) (this.value)).getId());
	}
}
