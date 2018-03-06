package de.peerthing.simulation.interfaces;

import java.util.List;

public interface IXPathObject {

	/**
	 * 
	 * @param elementId
	 * @return the element whose ID is given by elementId.
	 */
	public Object getElementById(String elementId);

	/**
	 * Retrieve the name of the given element node.
	 * 
	 * @return
	 */
	public String getElementName();

	/**
	 * Retrieve the string-value of an element node.
	 * 
	 * @return
	 */
	public String getElementStringValue();

	/**
	 * Returns a number that identifies the type of node that the given object
	 * represents in this navigator.
	 * 
	 * @return
	 */
	public short getXPathNodeType();

	/**
	 * Set the document node
	 * 
	 * @param document
	 *            The document to set.
	 */
	public void setDocument(IXPathObject document);

	/**
	 * 
	 * @return the document node that contains the given context node.
	 */
	public IXPathObject getDocument();

	/**
	 * Set the parent object
	 * 
	 * @param parent
	 *            The parent to set.
	 */
	public void setParent(IXPathObject parent);

	/**
	 * Returns the parent of the given context node.
	 * 
	 * @return
	 */
	public IXPathObject getParent();

	/**
	 * 
	 * @return returns the list of children (is null for XPathObject)
	 */
	public List<IXPathObject> getChildAxis();

	/**
	 * 
	 * @return returns descendant elements
	 */
	public List<IXPathObject> getDescendantAxisList();

	/**
	 * 
	 * @return returns all parents
	 */
	public List<IXPathObject> getParentAxisList();

	/**
	 * 
	 * @return returns all ancestors
	 */
	public List<IXPathObject> getAncestorAxisList();

	/**
	 * 
	 * @return return the following sibling
	 */
	public List<IXPathObject> getFollowingSiblingAxisList();

	/**
	 * 
	 * @return return the preceding sibling
	 */
	public List<IXPathObject> getPrecedingSiblingAxisList();

	/**
	 * 
	 * @return return the following elements
	 */
	public List<IXPathObject> getFollowingAxisList();

	/**
	 * 
	 * @return return the preceding elements
	 */
	public List<IXPathObject> getPrecedingAxisList();

	/**
	 * 
	 * @return return all attributes
	 */
	public List<IXPathAttribute> getAttributeAxisList();

	/**
	 * 
	 * @return returns itself
	 */
	public List<IXPathObject> getSelfAxisList();

	/**
	 * 
	 * @return returns descendants or itself
	 */
	public List<IXPathObject> getDescendantOrSelfAxisList();

	/**
	 * 
	 * @return returns all ancestors or itself
	 */
	public List<IXPathObject> getAncestorOrSelfAxisList();

	/**
	 * 
	 * @return returns the attribute name if it is an attribute else null
	 */
	public String getAttributeName();

	/**
	 * 
	 * @return retruns true if it is a document node
	 */
	public boolean isDocument();

	/**
	 * 
	 * @return retruns true if it is an element
	 */
	public boolean isElement();

	/**
	 * 
	 * @return retruns true if it is an attribute
	 */
	public boolean isAttribute();

	/**
	 * 
	 * @return retruns true if it is a comment
	 */
	public boolean isComment();

	/**
	 * 
	 * @return retruns true if it is text
	 */
	public boolean isText();

	/**
	 * 
	 * @return retruns the attribute string value
	 */
	public String getAttributeStringValue();

	/**
	 * 
	 * @return retruns the simulation size of the object
	 */
	public int getSize();
	
	/**
	 * print the object to stdout.
	 * 
	 */
	public void printObject();
	
	/**
	 * duplicate an object
	 */
	public IXPathObject duplicate();
	
	/**
	 * is true if two objects contain the same information
	 */
	public boolean isSimilarTo(IXPathObject object);
}
