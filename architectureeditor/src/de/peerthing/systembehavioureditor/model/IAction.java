package de.peerthing.systembehavioureditor.model;

import java.util.Map;

/**
 * Represents an action that is performed as part of a transition.
 * 
 * @author Michael Gottschalk, Peter
 * @review Boris, 2006-03-27
 */
public interface IAction extends ITransitionContent, ISystemBehaviourObject {
	
	/**
	 * Returns the parameters for this action.
	 * 
	 * @return
	 */
	public Map<String, IParameter> getParameters();
	
	/**
	 * Returns the name of the action, which should be the name of a function.
	 * 
	 * @return the name of the action
	 */
	public String getName();
	
	/**
	 * Sets the name of the action, which should be the name of a function.
	 * 
	 * @param name the name of the action
	 */
	public void setName(String name);
	
	/**
	 * Returns an expression which should reference a variable to which
	 * the result of the action is to be saved.
	 * 
	 * @return an expression pointing to a variable
	 */
	public String getResult();
	
	/**
	 * Sets an expression which should reference a variable to which
	 * the result of the action is to be saved.
	 *
	 * @param expression the expression pointing to a variable
	 */
	public void setResult(String expression);
	
    /**
     * Removes a parameter from the list parameters.
     * @param name the name of the parameter to delete
     */
	public void removeParameter(String name);
}
