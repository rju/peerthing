package de.peerthing.systembehavioureditor.interchange;

import java.util.Map;

/**
 * Represents an action that is performed as part of a transition.
 *
 * @author Michael Gottschalk
 */
public interface IAIAction extends IAITransitionContent {

	/**
	 * Returns the parameters for this action.
	 *
	 * @return
	 */
	public Map<String, IAIParameter> getParameters();

	/**
	 * Returns the name of the action, which should be the name of a function.
	 *
	 * @return the name of the action
	 */
	public String getName();


	/**
	 * Returns an expression which should reference a variable to which
	 * the result of the action is to be saved.
	 *
	 * @return an expression pointing to a variable
	 */
	public String getResult();

}
