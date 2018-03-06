package de.peerthing.systembehavioureditor.model;

/**
 * Represents a case in an architecture description.
 * 
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 *
 */
public interface ICaseArchitecture extends IContentContainer, ISystemBehaviourObject {
	
	/**
	 * Sets the condition to which this case belongs.
	 * @param condition
	 */
	public void setCondition(ICondition condition);
	
	/**
	 * Returns the condition to which this case belongs.
	 * @return
	 */
	public ICondition getCondition();
	
	/**
	 * Sets an expression that represents a condition (XPath syntax).
	 * 
	 * @param expression the expression
	 */	
	public void setExpression(String expression);
	
	/**
	 * Returns an expression that represents a condition (XPath syntax).
	 * @return the expression
	 */
	public String getExpression();
	
}
