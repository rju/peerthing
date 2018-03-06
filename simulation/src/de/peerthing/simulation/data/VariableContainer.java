/*
 *
 */
package de.peerthing.simulation.data;

import java.util.ArrayList;

import de.peerthing.simulation.interfaces.IVariableContainer;
import de.peerthing.simulation.interfaces.IXPathContainer;
import de.peerthing.simulation.interfaces.IXPathObject;

/**
 * implements the IVariableContainer interface
 *
 * @author prefec2
 * @review Patrik, 2006-03-25
 */
public abstract class VariableContainer extends XPathContainer implements
		IVariableContainer {

	/**
	 * reminder: Variables are handled as XML-environments, which lie in a node
	 * environment. these variables are children of the node environment. a
	 * problem here is that variables must not be named task or availResource,
	 * because these environment/tag names are already in use.
	 *
	 * reminder: the method does not work properly, because of the design of the
	 * XPath component.
	 *
	 * get a variable by name
	 *
	 * @param name
	 *            of the variable
	 *
	 * @return the variable
	 */
	public IXPathContainer getVariable(String name) {
		for (IXPathObject element : this.getChildAxis()) {
			if (element.getElementName().equals(name))
				if (element instanceof Variable)
					return (IXPathContainer) element;
		}
		return null;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IVariableContainer#setVariable(java.lang.String,
	 *      java.lang.Object)
	 */
	public void setVariable(String name, IXPathContainer value) {
		for (IXPathObject element : this.getChildAxis()) {
			if (element.getElementName().equals(name)) {
				if (element instanceof Variable) {
					/* delete content */
					((Variable) element).removeAllElement();
					/*
					 * must be cloned, because multiple instances could be
					 * generated
					 */
					((Variable) element).addElement(((Variable) value)
							.duplicate());
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IVariableContainer#setVariable(java.lang.String,
	 *      java.util.List)
	 */
	public void setVariable(String name, ArrayList<IXPathContainer> content) {
		for (IXPathObject element : this.getChildAxis()) {
			if (element.getElementName().equals(name)) {
				if (element instanceof Variable) {
					/* delete content */
					((Variable) element).removeAllElement();
					/*
					 * must be cloned, because multiple instances could be
					 * generated
					 */
					for (IXPathContainer var : content) {
						((Variable) element).addElement(((Variable) var)
								.duplicate());
					}
				}
			}
		}
	}

	/**
	 * remove a variable, specified by an expression
	 *
	 * @param expression
	 *            which points to the variable
	 */
	public void removeVariable(String name) {
		/* Modified in order to avoid the concurrentModificationException during testing */
		for(int i = 0; i < this.getChildAxis().size(); i++){
			IXPathObject element = this.getChildAxis().get(i);
			if (element.getElementName().equals(name))
				if (element instanceof Variable)
					this.removeElement((Variable) element);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IVariableContainer#addVariable(de.peerthing.simulation.interfaces.IXPathContainer)
	 */
	public void addVariable(IXPathContainer variable) {
		this.addElement((Variable) variable);
	}
}
