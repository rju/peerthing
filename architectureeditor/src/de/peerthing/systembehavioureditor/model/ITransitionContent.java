package de.peerthing.systembehavioureditor.model;

/**
 * This interface is a super-interface of IAction and ICondition since
 * both can be part of a transition. It should not be implemented directly!
 * 
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface ITransitionContent extends ISystemBehaviourObject {
	/**
	 * Sets the container to which this transition content belongs
	 * (either ITransition or ICaseArchitecture)
	 * 
	 * @param transition
	 */
	public void setContainer(IContentContainer container);
	
	/**
	 * Returns the container to which this transition content belongs
	 * (either ITransition or ICaseArchitecture)
	 * 
	 * @return
	 */
	public IContentContainer getContainer();
}
