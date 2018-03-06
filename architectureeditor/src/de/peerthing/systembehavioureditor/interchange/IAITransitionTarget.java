package de.peerthing.systembehavioureditor.interchange;

/**
 * This interface is a super-interface of ITask and IState since
 * both can be targets for transitions. It should not be implemented directly.
 *
 * @author Michael Gottschalk
 */
public interface IAITransitionTarget {
	/**
	 * Returns the name of the transistion target.
	 *
	 * @return the name
	 */
	public String getName();

}
