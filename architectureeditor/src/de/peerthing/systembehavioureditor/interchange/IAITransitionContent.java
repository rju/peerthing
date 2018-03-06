package de.peerthing.systembehavioureditor.interchange;

/**
 * This interface is a super-interface of IAction and ICondition since
 * both can be part of a transition. It should not be implemented directly!
 *
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface IAITransitionContent {
	/**
	 * Returns the container to which this transition content belongs
	 * (either ITransition or ICaseArchitecture)
	 *
	 * @return
	 */
	public IAIContentContainer getContainer();
}
