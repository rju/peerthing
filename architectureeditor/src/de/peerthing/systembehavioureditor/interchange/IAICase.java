package de.peerthing.systembehavioureditor.interchange;

/**
 * Represents a case in an architecture description.
 *
 * @author Michael Gottschalk
 *
 */
public interface IAICase extends IAIContentContainer {
	/**
	 * Returns the condition to which this case belongs.
	 * @return
	 */
	public IAICondition getCondition();

	/**
	 * Returns an expression that represents a condition (XPath syntax).
	 * @return the expression
	 */
	public String getExpression();
}
