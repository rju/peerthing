/**
 * 
 */
package de.peerthing.simulation.data;

import de.peerthing.simulation.interfaces.IXPathAttribute;
import de.peerthing.simulation.interfaces.IXPathObject;
import de.peerthing.systembehavioureditor.interchange.IAIState;

/**
 * Implements an attribute with a name and a value for XML-tags.
 * 
 * @author prefec2
 * @review Patrik, 2006-03-25
 */
public class XPathAttribute extends XPathObject implements IXPathAttribute {

	protected Object value;

	private String name;

	/**
	 * 
	 */
	public XPathAttribute(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.interfaces.IXPathObject#isAttribute()
	 */
	public boolean isAttribute() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulation.ISimulation#getAttributeName()
	 */
	public String getAttributeName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulation.ISimulation#getAttributeStringValue()
	 */
	public String getAttributeStringValue() {
		if (this.value instanceof Port)
			return String.valueOf(((Port) (this.value)).getRemotePort()
					.getRemoteNode().getId());
		else if (this.value != null)
			return this.value.toString();
		else
			return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulation.ISimulation#getAttributeStringValue()
	 */
	public Object getAttributeValue() {
		return this.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.data.XPathObject#getTextStringValue()
	 */
	public String getTextStringValue() {
		return this.value.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.interfaces.IXPathAttribute#getValue()
	 */
	public Object getValue() {
		return this.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.interfaces.IXPathAttribute#setValue(java.lang.Object)
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.interfaces.IXPathObject#getSize()
	 */
	public int getSize() {
		if (this.value instanceof String)
			return ((String) this.value).length();
		else if (this.value instanceof Double)
			return 8; /* 8 bytes/64 bit */
		else if (this.value instanceof Integer)
			return 4;
		else if (this.value instanceof Boolean)
			return 4;
		else if (this.value instanceof XPathObject)
			return ((XPathObject) this.value).getSize();
		else if (this.value instanceof IAIState)
			return ((IAIState)this.value).getName().length();
		else
			throw new RuntimeException(
					"Fatal error with attribute value. Cannot handle objects of class "
							+ this.value.getClass().getName());
	}
	
	public IXPathObject duplicate() {
		XPathAttribute obj = new XPathAttribute(this.name,this.value);
		obj.setParent(this.getParent());
		obj.setDocument(this.document);
		
		return obj;
	}

	/*
	 *  (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean isSimilarTo(XPathAttribute object) {
		XPathAttribute attr = (XPathAttribute)object;
		if (this.name.equals(attr.name)) {
			if ((this.value instanceof IXPathObject) &&
				(attr.value instanceof IXPathObject))
				return ((IXPathObject)(this.value)).isSimilarTo((IXPathObject)(attr.value));
			else
				this.value.equals(attr.value);
			return true;
		}
		return false;
	}
}
