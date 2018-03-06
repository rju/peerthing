package de.peerthing.simulation.data;

import de.peerthing.simulation.interfaces.IVariableContainer;
import de.peerthing.simulation.interfaces.IXPathAttribute;
import de.peerthing.simulation.interfaces.IXPathObject;

/**
 * Implements runtime variables for the simulation
 * 
 * @author prefec2
 * @review Patrik, 2006-03-25
 */
public class Variable extends VariableContainer implements IVariableContainer {

	private String name; /* element name, is not an attribute */

	public Variable(String name, String initialValue) {
		super();
		this.name = name;
		if (initialValue != null) {
			setContent(initialValue);
		}
	}

	public Variable(String name) {
		this(name, null);
	}

	/**
	 * Returns the name of the variable
	 */
	public String getElementName() {
		return this.name;
	}

	/**
	 * a variable is an element
	 */
	public boolean isElement() {
		return true;
	}

	/**
	 * Duplicate the variable
	 * 
	 * @return
	 */
	public Variable duplicate() {
		Variable obj = new Variable(this.name);

		obj.setParent(this.getParent());
		obj.setDocument(this.document);
		for (IXPathAttribute attr : this.attributeList)
			obj.addAttribute(attr);
		for (IXPathObject element : this.elementList)
			obj.elementList.add(element.duplicate());
		obj.setContent(this.getContent());
		return obj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.data.XPathObject#getTextStringValue()
	 */
	public String getTextStringValue() {
		return getContent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.interfaces.IXPathObject#isText()
	 */
	public boolean isText() {
		return getContent() != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.interfaces.IXPathObject#getElementStringValue()
	 */
	public String getElementStringValue() {
		return getContent();
	}
	
	/**
	 * check for similarity
	 */
	public boolean isSimilarTo(IXPathObject object) {
		if (object instanceof Variable) 
			return super.isSimilarTo(object);
		else
			return false;
	}
}
