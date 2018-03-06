/**
 * 
 */
package de.peerthing.simulation.interfaces;

import java.util.List;

/**
 * Defines the interface for ports, which are an integral part of the
 * communication model.
 * 
 * @author prefec2
 * 
 */
public interface IPort extends IXPathObject {

	/**
	 * assign a task to the port.
	 * 
	 * @param task
	 *            the task to be assigned
	 */
	public void assignTask(ISystemTask task);

	/**
	 * revoke the assignmet of a task to a port
	 * 
	 * @param task
	 *            the task to be revoked.
	 */
	public void resignTask(ISystemTask task);

	/**
	 * 
	 * @return returns the number of references to a port.
	 */
	public int getRefCount();

	/**
	 * 
	 * @return returns the remote node where to port is directed to.
	 */
	public INode getRemoteNode();

	/**
	 * 
	 * @return returns the remote port where this port is connected to.
	 */
	public IPort getRemotePort();

	/**
	 * Set the remote port where this port is connected to.
	 * 
	 * @param port
	 */
	public void setRemotePort(IPort port);

	/**
	 * Checks if the given task has a reference to this port
	 * 
	 * @param task
	 *            the task which migt be using this port
	 * 
	 * @return returns true if the task has a reference else false
	 */
	public boolean hasReference(ISystemTask task);

	/**
	 * Get the list of all local task which use this port
	 * 
	 * @return returns all
	 */
	public List<ISystemTask> getAllReferences();
}
