package de.peerthing.systembehavioureditor.model;

/**
 * Represents a parameter for an action used in transitions
 * in the architectural model.
 * 
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 *
 */
public interface IParameter extends ISystemBehaviourObject {
	public void setName(String name);
	public String getName();
	
	/**
	 * Sets the value of this parameter. This is a String or an int that
	 * does not have to be interpreted further.
	 * @param value
	 */
	public void setValue(String value);
    
    /**
     * Returns the value of this parameter. This is a String or an int that
     * does not have to be interpreted further.
     * 
     * @return
     */
	public String getValue();
	
	/**
	 * Sets the expression of this parameter. This is an XPath expression
	 * that has to be evaluated before it can be processed further.
	 * @param expression
	 */
	public void setExpression(String expression);
    
    /**
     * Returns the expression of this parameter. This is an XPath expression
     * that has to be evaluated before it can be processed further.
     * 
     * @return
     */
	public String getExpression();
	
    /**
     * Returns the action to which this parameter belongs (the parent object).
     * 
     * @return
     */
	public IAction getAction();
    
    /**
     * Sets the action to which this parameter belongs (the parent object).
     * 
     * @param action
     */
	public void setAction(IAction action);
}
