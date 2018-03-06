/*
 * Created on 12.02.2006
 *
 */
package de.peerthing.simulation.interfaces;

import org.eclipse.core.resources.IFile;

/**
 * Logs messages and changes in the topology during a simulation run. This
 * information is later used by the vizualization component for analysis of the
 * simulation.
 * 
 * 
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface ILogger {

	/**
	 * Add a message to the log.
	 * 
	 * @param sourceId
	 *            source node for the id
	 * @param destinationId
	 *            destination node for the id
	 * @param sessionId
	 *            session of the message
	 * @param size
	 *            size of the message
	 * @param timeSent
	 *            the time the message was created
	 * @param timeReceived
	 *            the time the last block was received
	 * @param name
	 *            the type/name of the message
	 * @param successful
	 *            true if the transmission was successful
	 */
	public void addMessage(int sourceId, int destinationId, int sessionId,
			int size, long timeSent, long timeReceived, String name,
			boolean successful);

	/**
	 * Adds information about globally available resources.
	 * 
	 * @param resourceId
	 *            ID of the resource
	 * @param sizeInBytes
	 *            Size of the resource in bytes
	 */
	public void addResource(int resourceId, int sizeInBytes);

	/**
	 * Adds a resource change event
	 * 
	 * @param nodeId
	 *            id of the node where the change occured
	 * @param resourceId
	 *            the id of the resource
	 * @param fractionAvailable
	 *            Number from 0 to 1 indicating how much of the resource is
	 *            present on the node OR -1 indicating that the resource is no
	 *            longer present at all
	 * @param time
	 *            the time of the change
	 * @param quality
	 *            Quality of the file. May be: 0 = not present, 1 = present, 2 =
	 *            validating, 3 = valid (checked with checksum)
	 */
	public void addResourceChange(int nodeId, int resourceId,
			double fractionAvailable, long time, int quality);

	/**
	 * logs the service state of a node
	 * 
	 * @param nodeId
	 * @param time
	 * @param state
	 */
	public void addNodeStateChange(int nodeId, long time, int state);

	/**
	 * store the node setup in the logging mechanism
	 * 
	 * @param nodeId
	 * @param uploadSpeed
	 * @param downloadSpeed
	 * @param uploadDelay
	 * @param downloadDelay
	 * @param categoryName
	 */
	public void addNodeInformation(int nodeId, long uploadSpeed,
			long downloadSpeed, long uploadDelay, long downloadDelay,
			String categoryName, String nodeType);

	/**
	 * Logs information about each session.
	 * 
	 * @param sessionId
	 * @param startTime
	 * @param endTime
	 */
	public void addSessionInformation(long sessionId, long startTime,
			long endTime);

	/**
	 * Changes the end time of a session
	 */
	public void changeSessionEndTime(long sessionId, long endTime);

	/**
	 * Logs information that comes from a user log action. With this action, the
	 * user can log arbitrary key-value information.
	 * 
	 * @param nodeId
	 *            The Id of the node from that the log action was called
	 * @param time
	 *            The time at which the log action was called
	 * @param name
	 *            The name of the parameter that should be logged
	 * @param value
	 *            The value of the parameter that should be logged
	 */
	public void addUserLogInformation(int nodeId, long time, String name,
			String value);

	/**
	 * Logs a debug statement to the standard output. Not intended for the
	 * vizualization component.
	 * 
	 * @param s
	 */
	public void debug(String s);

	/**
	 * Sets the filename of the database. The file must be created before this
	 * method can be called.
	 * 
	 * @param file
	 */
	public void startLog(IFile logFile);

	/**
	 * Indicates that the simulation run is finished and that the logging
	 * database may be closed. After a call to this method, no other methods may
	 * be called any longer.
	 * 
	 */
	public void endLog();

	/**
	 * Should be called if an error occured during the execution of a
	 * simulation. All data previously logged for this run is discarded.
	 * 
	 */
	public void discardLog();
}
