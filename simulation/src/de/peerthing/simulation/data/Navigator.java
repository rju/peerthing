package de.peerthing.simulation.data;

import java.util.Iterator;
import org.jaxen.FunctionCallException;
import org.jaxen.UnsupportedAxisException;
import org.jaxen.XPath;
import org.jaxen.saxpath.SAXPathException;

import de.peerthing.simulation.interfaces.IXPathObject;

/**
 * The navigator is some sort of factory for the XPath environment of the
 * simulation runtime environment.
 * 
 * @author prefec2
 * @review Patrik, 2006-03-25
 */
public class Navigator implements org.jaxen.Navigator {

	private IXPathObject main;

	private static final long serialVersionUID = 0;

	/**
	 * 
	 * @param sim
	 */
	public Navigator(IXPathObject main) {
		this.main = main;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getChildAxisIterator(java.lang.Object)
	 */
	public Iterator getChildAxisIterator(Object arg0)
			throws UnsupportedAxisException {
		if (arg0 != null) {
			if (((IXPathObject) arg0).getChildAxis() != null)
				return ((IXPathObject) arg0).getChildAxis().iterator();
			else
				return null;
		} else
			return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getDescendantAxisIterator(java.lang.Object)
	 */
	public Iterator getDescendantAxisIterator(Object arg0)
			throws UnsupportedAxisException {
		return ((IXPathObject) arg0).getDescendantAxisList().iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getParentAxisIterator(java.lang.Object)
	 */
	public Iterator getParentAxisIterator(Object arg0)
			throws UnsupportedAxisException {
		return ((IXPathObject) arg0).getParentAxisList().iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getAncestorAxisIterator(java.lang.Object)
	 */
	public Iterator getAncestorAxisIterator(Object arg0)
			throws UnsupportedAxisException {
		return ((IXPathObject) arg0).getAncestorAxisList().iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getFollowingSiblingAxisIterator(java.lang.Object)
	 */
	public Iterator getFollowingSiblingAxisIterator(Object arg0)
			throws UnsupportedAxisException {
		return ((IXPathObject) arg0).getFollowingSiblingAxisList().iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getPrecedingSiblingAxisIterator(java.lang.Object)
	 */
	public Iterator getPrecedingSiblingAxisIterator(Object arg0)
			throws UnsupportedAxisException {
		return ((IXPathObject) arg0).getPrecedingSiblingAxisList()
				.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getFollowingAxisIterator(java.lang.Object)
	 */
	public Iterator getFollowingAxisIterator(Object arg0)
			throws UnsupportedAxisException {
		return ((IXPathObject) arg0).getFollowingAxisList().iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getPrecedingAxisIterator(java.lang.Object)
	 */
	public Iterator getPrecedingAxisIterator(Object arg0)
			throws UnsupportedAxisException {
		return ((IXPathObject) arg0).getPrecedingAxisList().iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getAttributeAxisIterator(java.lang.Object)
	 */
	public Iterator getAttributeAxisIterator(Object arg0)
			throws UnsupportedAxisException {
		return ((IXPathObject) arg0).getAttributeAxisList().iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getNamespaceAxisIterator(java.lang.Object)
	 */
	public Iterator getNamespaceAxisIterator(Object arg0)
			throws UnsupportedAxisException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getSelfAxisIterator(java.lang.Object)
	 */
	public Iterator getSelfAxisIterator(Object arg0)
			throws UnsupportedAxisException {
		return ((IXPathObject) arg0).getSelfAxisList().iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getDescendantOrSelfAxisIterator(java.lang.Object)
	 */
	public Iterator getDescendantOrSelfAxisIterator(Object arg0)
			throws UnsupportedAxisException {
		return ((IXPathObject) arg0).getDescendantOrSelfAxisList()
				.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getAncestorOrSelfAxisIterator(java.lang.Object)
	 */
	public Iterator getAncestorOrSelfAxisIterator(Object arg0)
			throws UnsupportedAxisException {
		return ((IXPathObject) arg0).getAncestorOrSelfAxisList()
				.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getDocument(java.lang.String)
	 */
	public Object getDocument(String arg0) throws FunctionCallException {
		System.out
				.println("getDocument --- not implemented, no document loader");
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getDocumentNode(java.lang.Object)
	 */
	public Object getDocumentNode(Object arg0) {
		return this.main;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getParentNode(java.lang.Object)
	 */
	public Object getParentNode(Object arg0) throws UnsupportedAxisException {
		if (arg0 != null)
			return ((IXPathObject) arg0).getParent();
		else
			return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getElementNamespaceUri(java.lang.Object)
	 */
	public String getElementNamespaceUri(Object arg0) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getElementName(java.lang.Object)
	 */
	public String getElementName(Object arg0) {
		return ((IXPathObject) arg0).getElementName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getElementQName(java.lang.Object)
	 */
	public String getElementQName(Object arg0) {
		return ((IXPathObject) arg0).getElementName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getAttributeNamespaceUri(java.lang.Object)
	 */
	public String getAttributeNamespaceUri(Object arg0) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getAttributeName(java.lang.Object)
	 */
	public String getAttributeName(Object arg0) {
		return ((IXPathObject) arg0).getAttributeName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getAttributeQName(java.lang.Object)
	 */
	public String getAttributeQName(Object arg0) {
		return ((IXPathObject) arg0).getAttributeName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getProcessingInstructionTarget(java.lang.Object)
	 */
	public String getProcessingInstructionTarget(Object arg0) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getProcessingInstructionData(java.lang.Object)
	 */
	public String getProcessingInstructionData(Object arg0) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#isDocument(java.lang.Object)
	 */
	public boolean isDocument(Object arg0) {
		return ((IXPathObject) arg0).isDocument();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#isElement(java.lang.Object)
	 */
	public boolean isElement(Object arg0) {
		if (arg0 != null)
			return ((IXPathObject) arg0).isElement();
		else
			return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#isAttribute(java.lang.Object)
	 */
	public boolean isAttribute(Object arg0) {
		if (arg0 != null)
			return ((IXPathObject) arg0).isAttribute();
		else
			return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#isNamespace(java.lang.Object)
	 */
	public boolean isNamespace(Object arg0) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#isComment(java.lang.Object)
	 */
	public boolean isComment(Object arg0) {
		return ((IXPathObject) arg0).isComment();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#isText(java.lang.Object)
	 */
	public boolean isText(Object arg0) {
		if (arg0 instanceof IXPathObject)
			return ((IXPathObject) arg0).isText();
		else if (arg0 instanceof String)
			return true;
		else
			return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#isProcessingInstruction(java.lang.Object)
	 */
	public boolean isProcessingInstruction(Object arg0) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getCommentStringValue(java.lang.Object)
	 */
	public String getCommentStringValue(Object arg0) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getElementStringValue(java.lang.Object)
	 */
	public String getElementStringValue(Object arg0) {
		return ((IXPathObject) arg0).getElementStringValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getAttributeStringValue(java.lang.Object)
	 */
	public String getAttributeStringValue(Object arg0) {
		return ((IXPathObject) arg0).getAttributeStringValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getNamespaceStringValue(java.lang.Object)
	 */
	public String getNamespaceStringValue(Object arg0) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getTextStringValue(java.lang.Object)
	 */
	public String getTextStringValue(Object arg0) {
		if (arg0 instanceof String) {
			return (String) arg0;
		} else if (arg0 instanceof XPathObject) {
			return ((XPathObject) arg0).getTextStringValue();
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getNamespacePrefix(java.lang.Object)
	 */
	public String getNamespacePrefix(Object arg0) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#translateNamespacePrefixToUri(java.lang.String,
	 *      java.lang.Object)
	 */
	public String translateNamespacePrefixToUri(String arg0, Object arg1) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#parseXPath(java.lang.String)
	 */
	public XPath parseXPath(String arg0) throws SAXPathException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getElementById(java.lang.Object,
	 *      java.lang.String)
	 */
	public Object getElementById(Object arg0, String arg1) {
		return ((IXPathObject) arg0).getElementById(arg1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jaxen.Navigator#getNodeType(java.lang.Object)
	 */
	public short getNodeType(Object arg0) {
		return ((IXPathObject) arg0).getXPathNodeType();
	}

}
