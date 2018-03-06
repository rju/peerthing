/**
 * Description:
 *

 */
package de.peerthing.simulation.data;

import de.peerthing.simulation.interfaces.IXPathAttribute;
import de.peerthing.simulation.interfaces.IXPathContainer;
import de.peerthing.simulation.interfaces.IXPathObject;

/**
 * A parameter of an action, message or event.
 *
 * @author prefec2
 * @review Patrik, 2006-03-25
 */
public class Parameter extends XPathContainer implements IXPathContainer {

	private String name;

	public Parameter(String name) {
		super();
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see simulation.ISimulation#getElementName()
	 */
	public String getElementName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see simulation.ISimulation#isElement()
	 */
	public boolean isElement() {
		return true;
	}

	/**
	 * @return name of the parameter
	 */
	public String getName() {
		return this.name;
	}
	

    public IXPathObject duplicate() {
        Parameter obj = new Parameter(name);
        obj.setParent(this.getParent());
        obj.setDocument(this.document);
        obj.setContent(getContent());
        for (IXPathAttribute attr : this.attributeList)
            obj.addAttribute(attr);
        for (IXPathObject element : this.elementList)
            obj.elementList.add(element.duplicate());
        return obj;
    }
    
    /**
	 * check for similarity
	 */
	public boolean isSimilarTo(IXPathObject object) {
		if (object instanceof Parameter) 
			return super.isSimilarTo(object);
		else
			return false;
	}
}
