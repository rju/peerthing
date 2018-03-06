/**
 *
 */
package de.peerthing.simulation.data;

import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

import de.peerthing.simulation.interfaces.IXPathAttribute;
import de.peerthing.simulation.interfaces.IXPathContainer;
import de.peerthing.simulation.interfaces.IXPathObject;

/**
 * Base class for all elements in the simulation data storage which are
 * containers.
 *
 * @author prefec2
 * @review Patrik, 2006-03-25
 */
public abstract class XPathContainer extends XPathObject implements IXPathContainer {

	protected ArrayList<IXPathObject> elementList;

	private String content;

	public XPathContainer() {
		super();
		this.elementList = new ArrayList<IXPathObject>();
		this.content = null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IXPathContainer#addElement(de.peerthing.simulation.interfaces.IXPathObject)
	 */
	public boolean addElement(IXPathObject element) {
		if (element.getParent() != null) {
			IXPathObject attachElement = element.duplicate();
			element = attachElement;
		}
		element.setParent(this);
		return this.elementList.add(element);
	}

	/**
	 * add all elements in the list to this context. The parent and document
	 * references are set to the new location.
	 *
	 * @param list
	 * @return true on success
	 */
	public boolean addAllElement(List<IXPathObject> list) {
		for (IXPathObject element : list) {
			element.setParent(this);
		}
		return this.elementList.addAll(list);
	}

	/**
	 * Remove the given elementfrom the element ArrayList
	 */
	public void removeElement(IXPathObject element) {
		this.elementList.remove(element);
	}

	/**
	 * Remove all elements and their descendants
	 */
	public void removeAllElement() {
		this.elementList.clear();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see simulation.ISimulation#getChildAxisCollection()
	 */
	public List<IXPathObject> getChildAxis() {
		return this.elementList;
	}

	/**
	 * includes all childs and childs of childs ...
	 */
	public List<IXPathObject> getDescendantAxisList() {
		List<IXPathObject> descendant = new ArrayList<IXPathObject>();

		descendant.addAll(this.elementList);
		Iterator i = this.elementList.iterator();
		while (i.hasNext()) {
			Collection<IXPathObject> list = ((XPathObject) i.next())
					.getDescendantAxisList();
			if (list != null)
				descendant.addAll(list);
		}

		return descendant;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IXPathContainer#setContent(java.lang.String)
	 */
	public void setContent(String value) {
		this.content = value;
		this.removeAllElement();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IXPathContainer#getContent()
	 */
	public String getContent() {
		return this.content;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IXPathContainer#appendContent(java.lang.String)
	 */
	public void appendContent(String value) {
		if (this.content == null)
			this.setContent(value);
		else
			this.content += value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IXPathObject#getSize()
	 */
	public int getSize() {
		int size = super.getSize();
		for (IXPathObject obj : elementList)
			size += obj.getSize();
		return size;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IXPathObject#printObject()
	 */
	public void printObject() {
		System.out.print("<" + this.getElementName() + " ");
		for (IXPathAttribute a : this.attributeList)
			System.out.print(a.getAttributeName() + "=\""
					+ a.getAttributeStringValue() + "\" ");
		System.out.println(">");
		if (this.content != null)
			System.out.println(this.content);
		else
			for (IXPathObject o : this.elementList)
				o.printObject();
		System.out.println("</" + this.getElementName() + ">");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IXPathObject#getElementStringValue()
	 */
	public String getElementStringValue() {
		return getContent();
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
	 *  (non-Javadoc)
	 * @see de.peerthing.simulation.interfaces.IXPathContainer#contains(de.peerthing.simulation.interfaces.IXPathObject)
	 */
	public boolean contains(IXPathObject object) {
		for (IXPathObject item : this.elementList) {
			if (item.isSimilarTo(object))
				return true;
		}
		return false;
	}
	
	/**
	 * check for similarity
	 */
	public boolean isSimilarTo(IXPathObject object) {
		if (object instanceof IXPathContainer) {
			IXPathContainer container = (IXPathContainer)object;
		
			List<IXPathObject> list = container.getChildAxis();
			if (list.size() == this.elementList.size()) {
				for (int i=0;i<list.size();i++) {
					if (!list.get(i).isSimilarTo(this.elementList.get(i)))
						return false;
				}
				if(this.content == null && container.getContent() == null)
					return super.isSimilarTo(container);
				if (this.content.equals(container.getContent()))
					return super.isSimilarTo(container);
			}
		}
		return false;
	}
}
