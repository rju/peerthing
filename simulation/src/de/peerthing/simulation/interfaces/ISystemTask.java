package de.peerthing.simulation.interfaces;

import de.peerthing.systembehavioureditor.interchange.IAIState;
import de.peerthing.systembehavioureditor.interchange.IAITask;

/**
 * Represents a task currently running on a specific node.
 * 
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 * 
 */
public interface ISystemTask extends IVariableContainer, ITask {

	/**
	 * Returns the state that is currently active in this task.
	 * 
	 * @return
	 */
	public IAIState getActiveState();

	/**
	 * Sets the state that is currently active in this task.
	 * 
	 * @param state
	 */
	public void setActiveState(IAIState state);

	/**
	 * Returns the description of this task from the architecture of the node.
	 * 
	 * @return
	 */
	public IAITask getTaskImplementation();

	/**
	 * Sets the description of this task from the architecture of the node.
	 * 
	 * @param task
	 */
	public void setTaskImplementation(IAITask task);

	/**
	 * Returns the node to which this task belongs.
	 * 
	 * @return the node (the parent of this task)
	 */
	public INode getNode();

	/**
	 * Assign a message to a task. This is done when a task receives an event.
	 * In this way, parameters of an event can be evaluated in the receiving
	 * transition.
	 */
	public void setMessage(IMessage message);

	/**
	 * Returns the message that has previously been assigned to a task.
	 */
	public IMessage getMessage();
	
	/**
	 * Remove messages from the task
	 */
	public void removeMessage();

	/**
	 * Tell the task the present simulation time.
	 */
	public void setTaskTime(long time);

	/**
	 * Increment the local time of the task.
	 * 
	 * @param time
	 */
	public void advanceTaskTime(long time);

	/**
	 * 
	 * @return returns the local time of the task
	 */
	public long getTaskTime();

	/**
	 * Sets the timeout event with the given name to the given event. Only one
	 * timeout event with each name can exist.
	 * 
	 * @param name
	 *            The name of the event
	 * @param event
	 *            The event object. If null, the the event is removed.
	 */
	public void setTimeoutEvent(String name, IEvent event);

	/**
	 * Returns the timeout event with the given name, or null if no such timeout
	 * event exists in this task.
	 * 
	 * @param name
	 *            The name of the event
	 * @return
	 */
	public IEvent getTimoutEvent(String name);

	/**
	 * Add a session to the task
	 * 
	 * @param id
	 *            id of the session
	 * @param port
	 *            port assigned to the session
	 */
	public void addSession(long id, IPort port);

	/**
	 * Get the session by a given port
	 * 
	 * @param port
	 * 
	 * @return returns the assigned session or null if none
	 */
	public ISession getSessionByPort(IPort port);
	
	/**
	 * remove a session by id
	 */
	public void removeSession(long id);
}
