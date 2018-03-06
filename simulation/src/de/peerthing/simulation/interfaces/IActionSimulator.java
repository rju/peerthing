/**
 * 
 */
package de.peerthing.simulation.interfaces;

import java.util.List;

/**
 * The interface to the simulator for a component that contributes actions.
 * 
 * @author prefec2
 * 
 */
public interface IActionSimulator {

	/**
	 * 
	 * @return returns a new unique id for a message
	 */
	public long getNextMessageId();

	/**
	 * 
	 * @return returns a new unique id for a session
	 */
	public long getNextSessionId();

	/**
	 * @return returns a new unique id for a resource
	 */
	public long getNextResourceId();

	/**
	 * Create a new event and add it to the event queue.
	 * 
	 * @param name
	 *            name of the event
	 * @param time
	 *            time the event occurs
	 * @param locationNode
	 *            location node where the event occurs
	 * @param locationTask
	 *            the task where the event occurs
	 * @param parameterList
	 *            a list of parameters which are attached to the event
	 * 
	 * @return returns the new event for further use.
	 */
	public IEvent emitEvent(String name, long time, INode locationNode,
			ITask locationTask, List<IXPathContainer> parameterList);

	/**
	 * Removes the given event from the event queue.
	 * 
	 * @param event
	 */
	public void removeEvent(IEvent event);

	/**
	 * Create a new empty parameter list
	 * 
	 * @return returns the new list
	 */
	public List<IXPathContainer> createParameterList();

	/**
	 * Create a new parameter without a value
	 * 
	 * @param name
	 *            name of the new parameter
	 * 
	 * @return returns the new parameter
	 */
	public IXPathContainer createParameter(String name);

	/**
	 * create a new parameter with a string value
	 * 
	 * @param name
	 *            name of the parameter
	 * @param value
	 *            value of the parameter
	 * 
	 * @return returns the new parameter
	 */
	public IXPathContainer createParameter(String name, String value);

	/**
	 * Create a new parameter with a transmission log as value
	 * 
	 * @param name
	 *            name of the parameter
	 * @param value
	 *            value of the parameter
	 * 
	 * @return returns the new parameter
	 */
	public IXPathContainer createParameter(String name, ITransmissionLog value);

	/**
	 * Log the change of the connection state of the given node
	 * 
	 * @param contextNode
	 *            the node which connection state change is logged
	 */
	public void logNodeConnectionStateChange(INode contextNode);

	/**
	 * Create a variable as IXPathContainer
	 * 
	 * @param name
	 *            name of the variable
	 * @param value
	 *            value fo the variable
	 * 
	 * @return returns the new variable
	 */
	public IXPathContainer createVariable(String name, String value);

	/**
	 * Returns the logger currently used in the simulation.
	 * 
	 * @return the logger
	 */
	public ILogger getLogger();
	
	/**
	 * Add a message to the session tracker
	 */
	public void trackerIncRefCount(long id);
}
