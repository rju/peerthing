package de.peerthing.simulation.interfaces;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.scenarioeditor.interchange.ISIConnectionCategory;

/**
 * A INode is a node that is used during the simulation.
 * 
 * @author mg
 * @review Boris, 2006-03-27
 * 
 */
public interface INode extends IVariableContainer {

	/**
	 * Sets the unique ID of this node (the equivalent of an IP address)
	 * 
	 * @param id
	 */
	public void setId(int id);

	/**
	 * Returns the unique ID of this node
	 * 
	 * @return
	 */
	public int getId();

	/**
	 * Adds an active task to this node.
	 * 
	 * @param task
	 */
	public void addSystemTask(ISystemTask task);

	/**
	 * Remove a system task from the node
	 * 
	 * @param task
	 *            the task to be removed
	 */
	public void removeSystemTask(ISystemTask task);

	/**
	 * Returns the task with the given id
	 */
	public ISystemTask getSystemTask(int id);

	/**
	 * Return the primary system task (the first)
	 */
	public ISystemTask getPrimarySystemTask();

	/**
	 * Adds an active userTask to this node.
	 * 
	 * @param task
	 */
	public void addUserTask(IUserTask task);

	/**
	 * Returns the user task list
	 */
	public ArrayList<IUserTask> getUserTaskList();

	/**
	 * Sets the connection of a node
	 * 
	 * @param connection
	 */
	public void setConnectionCategory(ISIConnectionCategory connectionCategory);

	/**
	 * 
	 * @return the connection category
	 */
	public ISIConnectionCategory getConnectionCategory();

	/**
	 * add a resource to a node. The resource becomes then available
	 * 
	 * @param resource
	 *            to add
	 */
	public void addResource(IResource resource);

	/**
	 * search for a specific resource and return it
	 * 
	 * @param id
	 *            of the resource
	 * @return the resource on success or null if no such resource is available
	 */
	public IResource getResource(int id);

	/**
	 * remove a resource from a node
	 * 
	 * @param id
	 *            of the resource
	 */
	public void removeResource(int id);

	/**
	 * get the send message register
	 * 
	 * @return Returns the send message register
	 */
	public IRegister getSendMessageRegister();

	/**
	 * get the receive message register
	 * 
	 * @return Returns the receive message register
	 */
	public IRegister getReceiveMessageRegister();

	/**
	 * add a variable to a node.
	 * 
	 * @param variable
	 *            to add
	 */
	public void addVariable(IXPathContainer variable);

	/**
	 * search for a specific variable and return it
	 * 
	 * @param name
	 *            of the variable
	 * @return Returns the variable on success or null if no such variable
	 *         exists
	 */
	public IXPathContainer getVariable(String name);

	/**
	 * 
	 * @return returns the name of the system behaviour for the node
	 */
	public String getSystemBehaviourName();

	/**
	 * 
	 * @return returns the name of the user behaviour
	 */
	public String getUserBevahiourName();

	/**
	 * 
	 * @return returns the name of the connection category
	 */
	public String getConnectionCategoryName();

	/**
	 * Create and send a message to the main task of the destination node
	 * 
	 * @param name
	 *            name of the message
	 * @param session
	 *            session id for the message
	 * @param time
	 *            time the message is sent
	 * @param size
	 *            the size of the message
	 * @param sourceTask
	 *            the source task
	 * @param destinationNode
	 *            the destination node
	 * @param parameterList
	 *            a list of parameters for the message
	 * 
	 * @return returns the transmission log for the message.
	 */
	public ITransmissionLog sendMessage(String name, long session, long time,
			int size, ISystemTask sourceTask, INode destinationNode,
			List<IXPathContainer> parameterList);

	/**
	 * Send a message through an established connection
	 * 
	 * @param name
	 *            name of the message
	 * @param session
	 *            session id for the message
	 * @param time
	 *            time the message is sent
	 * @param size
	 *            the size of the message
	 * @param sourceTask
	 *            the source task
	 * @param remotePort
	 *            the port the message is send to
	 * @param parameterList
	 *            a list of parameters for the message
	 * 
	 * @return returns the transmission log for the message.
	 */
	public ITransmissionLog sendMessage(String name, long session, long time,
			int size, ISystemTask sourceTask, IPort remotePort,
			List<IXPathContainer> parameterList);

	/**
	 * check if the downstream is available. In a half duplex situation, the
	 * upstream is checked too.
	 * 
	 * @return Returns true if the downstream is available else false
	 */
	public boolean isDownstreamAvailable(long begin, long end);

	/**
	 * check if the upstream is available. In a half duplex situation, the
	 * downstream is checked too.
	 * 
	 * @return Returns true if the upstream is available else false
	 */
	public boolean isUpstreamAvailable(long begin, long end);

	/**
	 * block downstream for a certain time. If the period is not free, the
	 * operation will not be performed.
	 * 
	 * @param begin
	 *            begin of the interval to be reserved
	 * @param end
	 *            end of the interval to be reserved
	 */
	public void reserveDownstream(long begin, long end);

	/**
	 * reserve upstream. No checks are performed, if the upstream is already in
	 * use and the method is called, the upstreamUsable value is changed.
	 * 
	 * @param end
	 *            time until the upstream shall be reserved
	 */
	public void reserveUpstream(long begin, long end);

	/**
	 * return the earliest possible time frame with the length of duration
	 * (upstream)
	 * 
	 * @param time
	 * @param duration
	 * @return a time stamp
	 */
	public long getNextFreeUpstreamTime(long time, long duration);

	/**
	 * return the earliest possible time frame with the length of duration
	 * (downstream)
	 * 
	 * @param time
	 * @param duration
	 * @return a time stamp
	 */
	public long getNextFreeDownstreamTime(long time, long duration);

	/**
	 * Get the state of the connection
	 * 
	 * @return returns the state as a string
	 */
	public String getConnectionState();

	/**
	 * Set the state of the connection.
	 * 
	 * @param value
	 */
	public void setConnectionState(String value);

	/**
	 * Add a port to the node
	 * 
	 * @param port
	 */
	public void addPort(IPort port);

	/**
	 * Remove a port from the node
	 * 
	 * @param port
	 */
	public void removePort(IPort port);

	/**
	 * Duplicate the port handles for a system task.
	 * 
	 * @param oldTask
	 *            old task with certain handles
	 * @param newTask
	 *            new task which gets a copy of the handles
	 */
	public void duplicatePortHandles(ISystemTask oldTask, ISystemTask newTask);
}
