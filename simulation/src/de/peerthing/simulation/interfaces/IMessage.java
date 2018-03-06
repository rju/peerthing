package de.peerthing.simulation.interfaces;

/**
 * A message is sent from one node to the other.
 * 
 * @author mg
 * @review Boris, 2006-03-27
 * 
 */
public interface IMessage extends IXPathObject {
	/**
	 * Sets the name of the message, e.g. "searchRequest", "connect", ...
	 * 
	 */
	public void setName(String name);

	/**
	 * Returns the name of the message.
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * Returns the simulation time at which this message has been sent.
	 * 
	 * @return
	 */
	public long getTimeSent();

	/**
	 * Sets the simulation time at which this message has been sent.
	 * 
	 * @param time
	 */
	public void setTimeSent(long time);

	/**
	 * Returns the simulation time at which this message has been received.
	 * 
	 * @return
	 */
	public long getTimeReceived();

	/**
	 * Sets the simulation time at which this message has been received.
	 * 
	 * @param time
	 */
	public void setTimeReceived(long time);

	/**
	 * Sets the ID of this message. This should be unique for each message and
	 * exactly identify it.
	 * 
	 * @param id
	 */
	public void setId(int id);

	/**
	 * Returns the ID of this message. This is unique for each message and
	 * exactly identifies it.
	 * 
	 * @return
	 */
	public int getId();

	/**
	 * Sets the size of this message, which is given in bytes.
	 * 
	 * @param bytes
	 */
	public void setSize(int bytes);

	/**
	 * Returns the size of the message, in bytes.
	 * 
	 * @return
	 */
	public int getSize();

	/**
	 * Sets the session ID for this message. A session id is used for every
	 * message resulting from a user interaction, e.g. when the user model sends
	 * a search message to the peer, all resulting messages from this search
	 * message are tagged with the same session ID.
	 * 
	 * @param sessionId
	 */
	public void setSessionId(long sessionId);

	/**
	 * Returns the session ID for this message. A session id is used for every
	 * message resulting from a user interaction, e.g. when the user model sends
	 * a search message to the peer, all resulting messages from this search
	 * message are tagged with the same session ID.
	 * 
	 * @return
	 */
	public long getSessionId();

	/**
	 * Get the port used to send the message
	 * 
	 * @return returns the source port
	 */
	public IPort getSource();

	/**
	 * Set the source port for the message
	 * 
	 * @param port
	 */
	public void setSource(IPort port);

	/**
	 * Get the port used to receive the message
	 * 
	 * @return returns the destination port
	 */
	public IPort getDestination();

	/**
	 * Set the destination port for the message
	 * 
	 * @param port
	 */
	public void setDestinationNode(IPort port);

	/**
	 * get a parameter from a message by name
	 * 
	 * @param name
	 * @return a parameter or null if no such parameter is set
	 */
	public IXPathContainer getParameter(String name);

	/**
	 * add a parameter to a message
	 * 
	 * @param parameter
	 */
	public void addParameter(IXPathContainer parameter);
}
