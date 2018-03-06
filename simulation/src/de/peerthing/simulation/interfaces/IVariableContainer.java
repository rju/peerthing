package de.peerthing.simulation.interfaces;

import java.util.ArrayList;

/**
 * The super-interface of INode and ISystemTask since both can include
 * variables.
 * 
 * @author Michael Gottschalk
 * @author prefec2
 * @review Boris, 2006-03-27
 */
public interface IVariableContainer extends IXPathObject {

	/**
	 * Add a variable to a IVariableContainer.
	 * 
	 * @param variable
	 *            is the variable to be added
	 */
	public void addVariable(IXPathContainer variable);

	/**
	 * Returns the value of the variable with the given name.
	 * 
	 * @param name
	 *            of the variable
	 * @return
	 */
	public IXPathContainer getVariable(String name);

	/**
	 * Set the value of a variable specified by the given expression. The
	 * expression must be unique and point to a variable or variable item which
	 * holds one value. The value is a ISimulationXPath object.
	 * 
	 * @param name
	 *            the name of the variable
	 * @param value
	 *            the new value of the variable
	 */
	public void setVariable(String name, IXPathContainer value);

	/**
	 * Set the value of a variable specified by the given expression. The
	 * content is a list of ISimulationXPath objects.
	 * 
	 * @param name
	 *            the name of the variable
	 * @param content
	 *            the new content list of the variable
	 */
	public void setVariable(String name, ArrayList<IXPathContainer> content);

	/**
	 * Remove a variable or parts of its content (subvariable)
	 * 
	 * @param expression
	 *            the expression pointing to the to object to remove.
	 */
	public void removeVariable(String name);
}
