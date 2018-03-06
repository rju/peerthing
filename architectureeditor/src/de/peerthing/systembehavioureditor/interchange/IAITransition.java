package de.peerthing.systembehavioureditor.interchange;

/**
 * Represents a transition that is part of a state
 * in the architectural model.
 *
 *
 * @author Michael Gottschalk
 *
 */
public interface IAITransition extends IAIContentContainer {
	/**
	 * Returns the target of this state.
	 *
	 * @return the target, which can either be of type IState or ITask
	 */
	public IAITransitionTarget getNextState();

	/**
	 * Returns true if firing this transition ends the currently
	 * running task.
	 *
	 * @return true, if the task should be ended by this transition, false otherwise
	 */
	public boolean isEndTask();

	/**
	 * Returns the name of the event, where the transition is triggered by
	 * 
	 * @return
	 */
	public String getEvent();

	/**
	 * Returns the state to which this transition belongs.
	 *
	 * @return the state
	 */
	public IAIState getState();

}
