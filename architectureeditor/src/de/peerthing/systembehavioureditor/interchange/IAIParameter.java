package de.peerthing.systembehavioureditor.interchange;

/**
 * Represents a parameter for an action used in transitions
 * in the architectural model.
 *
 * @author Michael Gottschalk
 *
 */
public interface IAIParameter {
	public String getName();

    /**
     * Returns the value of this parameter. This is a String or an int that
     * does not have to be interpreted further.
     *
     * @return
     */
	public String getValue();

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
	public IAIAction getAction();

}
