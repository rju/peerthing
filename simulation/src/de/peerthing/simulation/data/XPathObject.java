/*
 *
 */
package de.peerthing.simulation.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import de.peerthing.simulation.interfaces.IEvaluate;
import de.peerthing.simulation.interfaces.IXPathAttribute;
import de.peerthing.simulation.interfaces.IXPathObject;

/**
 * Base class for all IXPathObjects. It handles attributes and basic axis
 * search.
 *
 * @author prefec2
 * @review Patrik, 2006-03-25
 */
public abstract class XPathObject implements IEvaluate, IXPathObject {

	private IXPathObject parent; /* reference to the parent object */

	protected IXPathObject document; /* reference to the document root */

	protected List<IXPathAttribute> attributeList; /* list of attributes */

	public XPathObject() {
		this.parent = null;
		this.document = null;
		this.attributeList = new ArrayList<IXPathAttribute>();
	}

	/**
	 * Retrieve an Collection matching the ancestor XPathObject axis.
	 *
	 * @return a Collection of XPathObjects
	 */
	public List<IXPathObject> getAncestorAxisList() {
		List<IXPathObject> ancestor = new ArrayList<IXPathObject>();
		IXPathObject parent = this.parent;
		do {
			if (parent != null) {
				ancestor.add(parent);
				parent = parent.getParent();
			}
		} while (parent != null);

		return ancestor;
	}

	/**
	 * Retrieve an Collection matching the ancestor-or-self XPathObject axis.
	 *
	 * @return
	 */
	public List<IXPathObject> getAncestorOrSelfAxisList() {
		List<IXPathObject> result = new ArrayList<IXPathObject>();
		IXPathObject node = this;
		do {
			if (node != null) {
				result.add(node);
				node = node.getParent();
			}
		} while (node != null);

		return result;
	}

	/**
	 * Retrieve an Collection matching the attribute IXPathObject axis.
	 *
	 * @return
	 */
	public List<IXPathAttribute> getAttributeAxisList() {
		return this.attributeList;
	}

	/**
	 * Retrieve an Collection matching the child IXPathObject axis.
	 *
	 * @return
	 */
	public List<IXPathObject> getChildAxis() {
		return null;
	}

	/**
	 * Retrieve an Collection matching the descendant IXPathObject axis.
	 *
	 * @return
	 */
	public List<IXPathObject> getDescendantAxisList() {
		return null;
	}

	/**
	 * Retrieve an Collection matching the descendant-or-self IXPathObject axis.
	 *
	 * @return
	 */
	public List<IXPathObject> getDescendantOrSelfAxisList() {
		List<IXPathObject> result = this.getDescendantAxisList();
		if(result == null)
			result = new ArrayList<IXPathObject>();

		result.add(0,this);
		return result;
	}

	/**
	 * Retrieve an Collection matching the parent IXPathObject axis.
	 *
	 * @return
	 */
	public List<IXPathObject> getParentAxisList() {
		List<IXPathObject> i = new ArrayList<IXPathObject>();

		if (this.parent != null)
			i.add(this.parent);
		return i;
	}

	/**
	 * Retrieve an Collection matching the following IXPathObject axis. get all
	 * following nodes (including the childs)
	 *
	 * @return Collection of IXPathObjects
	 */
	public List<IXPathObject> getFollowingAxisList() {
		List<IXPathObject> i = this.getFollowingSiblingAxisList();
		List<IXPathObject> result = new ArrayList<IXPathObject>();

		if (i != null)
			while (i.iterator().hasNext()) {
				XPathObject element = (XPathObject) i.iterator().next();
				result.add(element);
				result.addAll(element.getDescendantAxisList());
			}

		return result;
	}

	/**
	 * Retrieve an Iterator matching the following-sibling IXPathObject axis.
	 *
	 * @return
	 */
	public List<IXPathObject> getFollowingSiblingAxisList() {
		if (((XPathObject) this.parent) == null) { /*
													 * have no parent, so I
													 * should be document root
													 */
			if (this instanceof Document)
				return null;
			else {
				this.printObject();
				throw new RuntimeException(this.getElementName() + " should have a parent.");
			}

		}
		List<IXPathObject> result = new ArrayList<IXPathObject>();

		boolean add = false;
		for(IXPathObject element : ((XPathObject) this.parent).getChildAxis()) {
			if (add)
				result.add(element);
			if (element == this)
				add=true;
		}

		return result;
	}

	/**
	 * Retrieve an Collection matching the preceding IXPathObject axis.
	 *
	 * @return
	 */
	public List<IXPathObject> getPrecedingAxisList() {
		List<IXPathObject> result = new ArrayList<IXPathObject>();
		List<IXPathObject> precedingSibling = this.getPrecedingSiblingAxisList();

		if(precedingSibling != null)
			result.addAll(precedingSibling);
		for(IXPathObject element : this.getAncestorAxisList()) {
			result.addAll(element.getPrecedingSiblingAxisList());
		}

		return result;
	}

	/**
	 * Retrieve an Collection matching the preceding-sibling IXPathObject axis.
	 *
	 * @return
	 */
	public List<IXPathObject> getPrecedingSiblingAxisList() {
		if (((XPathObject) this.parent) == null) { /*
													* have no parent, so I
													* should be document root
													*/
			if (this instanceof Document)
				return null;
			else {
				this.printObject();
				throw new RuntimeException(this.getElementName() + " should have a parent.");
			}
			
		}
		Iterator i = ((XPathObject) this.parent).getChildAxis().iterator();
		List<IXPathObject> result = new ArrayList<IXPathObject>();

		while (i.hasNext()) {
			IXPathObject element = (IXPathObject) i.next();

			if (element.equals(this))
				return result;
			else
				result.add(0,element);
		}

		return result;
	}

	/**
	 * Retrieve an Collection matching the self IXPathObject axis.
	 *
	 * @return
	 */
	public List<IXPathObject> getSelfAxisList() {
		List<IXPathObject> list = new ArrayList<IXPathObject>();

		list.add(this);
		return list;
	}

	/**
	 * Set the document node
	 *
	 * @param document
	 *            The document to set.
	 */
	public void setDocument(IXPathObject document) {
		this.document = document;
	}

	/**
	 *
	 * @return the document node that contains the given context node.
	 */
	public IXPathObject getDocument() {
		return document;
	}

	/**
	 * Set the parent object
	 *
	 * @param parent
	 *            The parent to set.
	 */
	public void setParent(IXPathObject parent) {
		this.parent = parent;

		if (parent != null) {
			this.document = parent.getDocument();
		}
	}

	/**
	 * Returns the parent of the given context node.
	 *
	 * @return
	 */
	public IXPathObject getParent() {
		return this.parent;
	}

	/**
	 * register an attribute to the attribute list
	 */
	public void addAttribute(IXPathAttribute attr) {
		attributeList.add(attr);
	}

	/**
	 * Replace the attribute with the given name with the given attribute value.
	 * If no such attribute exists, nothing is done.
	 *
	 * @param attributeName
	 */
	public void setAttribute(String attributeName, Object value) {
		for (IXPathAttribute attr : attributeList) {
			if (attr.getAttributeName() != null
					&& attr.getAttributeName().equals(attributeName)) {
				attr.setValue(value);
			}
		}
	}

	/**
	 * Retrieve the string-value of an attribute node.
	 *
	 * @return
	 */
	public String getAttributeStringValue() {
		System.out.println("getAttributeStringValue <-- IXPathObject ");
		return null;
	}

	/**
	 * Retrieve the name of the given attribute node.
	 *
	 * @return
	 */
	public String getAttributeName() {
		System.out.println("getAttributeName <-- IXPathObject ");
		return null;
	}

	/**
	 * Retrieve the string-value of a comment node.
	 *
	 * @return
	 */
	public String getCommentStringValue() {
		return null;
	}

	/**
	 *
	 * @param elementId
	 * @return the element whose ID is given by elementId.
	 */
	public Object getElementById(String elementId) {
		return null;
	}

	/**
	 * Retrieve the name of the given element node.
	 *
	 * @return
	 */
	public String getElementName() {
		return null;
	}

	/**
	 * Retrieve the string-value of an element node.
	 *
	 * @return
	 */
	public String getElementStringValue() {
		return null;
	}

	/**
	 * Returns a number that identifies the type of node that the given object
	 * represents in this navigator.
	 *
	 * @return
	 */
	public short getXPathNodeType() {
		return 0;
	}

	/**
	 * Retrieve the string-value of a text node.
	 *
	 * @return
	 */
	public String getTextStringValue() {
		return null;
	}

	/**
	 * Returns whether the given object is an attribute node.
	 *
	 * @return
	 */
	public boolean isAttribute() {
		return false;
	}

	/**
	 * Returns whether the given object is a comment node.
	 *
	 * @return
	 */
	public boolean isComment() {
		return false;
	}

	/**
	 * Returns whether the given object is a document node.
	 *
	 * @return
	 */
	public boolean isDocument() {
		return false;
	}

	/**
	 * Returns whether the given object is an element node.
	 *
	 * @return
	 */
	public boolean isElement() {
		return false;
	}

	/**
	 * Returns whether the given object is a processing-instruction node.
	 *
	 * @return
	 */
	public boolean isProcessingInstruction() {
		return false;
	}

	/**
	 * Returns whether the given object is a text node.
	 *
	 * @return
	 */
	public boolean isText() {
		return false;
	}

	/**
	 * abstract method, returns the id of an object or -1 if an object has no id
	 */
	public int getId() {
		return -1;
	}

	/**
	 * Evaluate an XPath expression against the IXPathObject context. If the
	 * expression evaluates to an XPath string, number, or boolean type, then
	 * the equivalent Java object type is returned. Otherwise, if the result is
	 * a node-set, then the returned value is a List of ??.
	 *
	 * @param expression
	 * @return Boolean, Double, String, List, null
	 */
	public Object evaluate(String expression) {
		if (getDocument() != null) {
			return ((Document) getDocument()).evaluate(this, expression);
		} else {
			System.out.println("Document NULL!!!!!! " + expression);
			return null;
		}
	}

	/**
	 * Evaluate an XPath expression against the IXPathObject context. Unlike
	 * "evaluate" "evaulteCondition" returns allways a boolean value. The method
	 * maps non boolean results to true and false according to the following
	 * rules.
	 *
	 * Boolean(true,false) => true,false Double(positive number, 0, negative
	 * number) => true, false, false String(non empty, empty) => true, false
	 * List(non empty, empty) => true, false null => false
	 *
	 * @param expression
	 * @return boolean
	 */
	public boolean evaluateCondition(String expression) {
		Object result = this.evaluate(expression);

		if (result instanceof Boolean)
			return ((Boolean) result).booleanValue();
		else if (result instanceof Double)
			// 0 is false
			return (((Double) result).doubleValue() != 0);
		else if (result instanceof String)
			// empty string is false
			return !((String) result).contentEquals("");
		else if (result instanceof List) {
			// empty list is false, else true
			return ((List) result).size() > 0;
		} else
			// if an illegal object is returned
			return false;
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
					+ a.getAttributeStringValue() + "\"");
		System.out.println("/>");
	}

	/**
	 * Return the size fo the object
	 */
	public int getSize() {
		int size = 0;
		for (IXPathAttribute a : this.attributeList) {
			size += a.getSize();
		}

		return size;
	}
	
	public boolean isSimilarTo(IXPathObject object) {
		if (object.getElementName() == null) {
			if (this.getElementName() == null) {
				List<IXPathAttribute> list = object.getAttributeAxisList();
				if (list.size() == this.attributeList.size()) {
					for (int i=0;i<list.size();i++) {
						if (!list.get(i).isSimilarTo(this.attributeList.get(i)))
							return false;
					}
					return true;
				}
			} else
				return false;
		} else {
			if (object.getElementName().equals(this.getElementName())) {
				List<IXPathAttribute> list = object.getAttributeAxisList();
				if (list.size() == this.attributeList.size()) {
					for (int i=0;i<list.size();i++) {
						if (!list.get(i).isSimilarTo(this.attributeList.get(i)))
							return false;
					}
					return true;
				}
			}
		}
		return false;
	}
	
	/*
	 * This methode exists only for test purpose
	 */
	public List<IXPathAttribute> getAttributeList(){
		return attributeList;
	}
}
