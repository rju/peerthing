package de.peerthing.simulation.data;

import de.peerthing.scenarioeditor.interchange.ISIResourceCategory;
import de.peerthing.simulation.interfaces.IResourceDefinition;
import de.peerthing.simulation.interfaces.IXPathAttribute;
import de.peerthing.simulation.interfaces.IXPathObject;

/**
 * Describes a resource for the simulation. These resources have a size and a
 * probability. The probability describes the probability to occure in a search.
 * This probability is implemented as a range (1:100) where 'start' ist the
 * begin of the reange and 'end' is the end.
 *
 * @author prefec2
 * @review Patrik, 2006-03-25
 */
public class ResourceDefinition extends XPathObject implements
		IResourceDefinition {

	private int start;

	private int end;

	private int size;

	private ISIResourceCategory category;

	/**
	 *
	 */
	public ResourceDefinition(int id, int size, int start, int end,
			ISIResourceCategory category) {
		super();
		this.attributeList.add(new XPathAttribute("id", String.valueOf(id)));
		this.attributeList.add(new XPathAttribute("size", "" + size));
		this.start = start;
		this.end = end;
		this.size = size;
		this.category = category;
	}

	/**
	 * Copy constructor
	 *
	 * @param copy the object to copy
	 */
	public ResourceDefinition(ResourceDefinition copy) {
		start = copy.start;
		end = copy.end;
		size = copy.size;
		for (IXPathAttribute attr : copy.getAttributeAxisList()) {
			addAttribute(attr);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see simulation.ISimulation#getElementName()
	 */
	public String getElementName() {
		return "resourceDefinition";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see simulation.ISimulation#isElement()
	 */
	public boolean isElement() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IResourceDefinition#getEnd()
	 */
	public int getEnd() {
		return this.end;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IResourceDefinition#setEnd(int)
	 */
	public void setEnd(int end) {
		this.end = end;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IXPathObject#getSize()
	 */
	public int getSize() {
		return this.size;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IResourceDefinition#setSize(int)
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IResourceDefinition#getStart()
	 */
	public int getStart() {
		return this.start;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IResourceDefinition#setStart(int)
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IResourceDefinition#setId(int)
	 */
	public void setId(int id) {
		this.attributeList.get(0).setValue(String.valueOf(id));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.data.XPathObject#getId()
	 */
	public int getId() {
		return Integer.parseInt((String) this.attributeList.get(0).getValue());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IResourceDefinition#getCategory()
	 */
	public ISIResourceCategory getCategory() {
		return this.category;
	}

	public IXPathObject duplicate() {
		return new ResourceDefinition(this);
	}
	
	/**
	 * check for similarity
	 */
	public boolean isSimilarTo(IXPathObject object) {
		if (object instanceof ResourceDefinition) 
			return super.isSimilarTo(object);
		else
			return false;
	}
}
