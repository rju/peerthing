package de.peerthing.simulation.interfaces;

import java.util.List;

public interface IXPathContainer extends IXPathObject {

	/**
	 * Add an element to the container and set its parent and document attributes
	 * to the new context.
	 * 
	 * @param element
	 *            the element to be added
	 * 
	 * @return returns true on success else false
	 */
	public boolean addElement(IXPathObject element);
	
	/**
	 * add all elements in the list to this context. The parent and document
	 * references are set to the new location.
	 * 
	 * @param list
	 * @return true on success
	 */
	public boolean addAllElement(List<IXPathObject> list);

	/**
	 * Remove the given elementfrom the element ArrayList
	 */
	public void removeElement(IXPathObject element);

	/**
	 * Remove all elements and their descendants
	 */
	public void removeAllElement();

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulation.ISimulation#getChildAxisCollection()
	 */
	public List<IXPathObject> getChildAxis();

	/**
	 * includes all childs and childs of childs ...
	 */
	public List<IXPathObject> getDescendantAxisList();

	/**
	 * Set the content of the container. This will remove all elements in this
	 * container.
	 * 
	 * @param value
	 *            String content to be added.
	 */
	public void setContent(String value);

	/**
	 * 
	 * @return returns the content or null if none
	 */
	public String getContent();

	/**
	 * Append a string to the present content
	 * 
	 * @param value
	 */
	public void appendContent(String value);

	/**
	 * Print the object to stdout.
	 */
	public void printObject();
	
	/**
	 * check if the given object is child of the container
	 */
	public boolean contains(IXPathObject object);
}
