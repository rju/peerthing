package de.peerthing.systembehavioureditor.model;

/**
 * This interface is a super-interface of ITask and IState since
 * both can be targets for transitions. It should not be implemented directly.
 * 
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface ITransitionTarget extends ISystemBehaviourObject {
	/**
	 * Returns the name of the transistion target.
	 * 
	 * @return the name
	 */
	public String getName();
	
	/**
	 * Sets the name of the transition target. This must be unique among all
	 * transition targets since it is used as in identifier in the XML representation.
	 *
	 * @param name the name
	 */
	public void setName(String name);
}
