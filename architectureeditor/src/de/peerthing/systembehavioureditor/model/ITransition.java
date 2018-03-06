package de.peerthing.systembehavioureditor.model;

/**
 * Represents a transition that is part of a state
 * in the architectural model.
 * 
 *
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 *
 */
public interface ITransition extends IContentContainer {
	/**
	 * Returns the target of this state.
	 * 
	 * @return the target, which can either be of type IState or ITask
	 */
	public ITransitionTarget getNextState();
	
	/**
	 * Sets the target of this transition. Can be either
	 * an IState or an ITask. If it is an ITask, then
	 * another thread is started when this transition is fired
	 * and the first state of this thread is the start state of the
	 * task.
	 * 
	 * @param target the target
	 */
	public void setNextState(ITransitionTarget target);
	
	/**
	 * Returns true if firing this transition ends the currently
	 * running task.
	 * 
	 * @return true, if the task should be ended by this transition, false otherwise
	 */
	public boolean isEndTask();
	
	/**
	 * Sets whether firing this transition ends the current thread or not. 
	 * 
	 * @param endTask true, if the thread should be ended, false otherwise
	 */
	public void setEndTask(boolean endTask);
	
	public String getEvent();
	
	/**
	 * Sets the event to which this transition reacts. Can be
	 * null, then the transition always fires after entering the
	 * associated state if the conditions are true.
	 * 
	 * @param event The name of the event
	 */
	public void setEvent(String event);
	
	/**
	 * Returns the state to which this transition belongs.
	 * 
	 * @return the state
	 */
	public IState getState();
	
	/**
	 * Sets the state to which this transition belongs.
	 * 
	 * @param state the state
	 */
	public void setState(IState state);

	/**
     * TODO: remove
     * 
     * @deprecated This is used only internally in the editor, so it does not
     *         have to be in the interface.
     *
	 */
	public void connect();
	
}
