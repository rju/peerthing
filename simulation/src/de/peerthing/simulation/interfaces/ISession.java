/**
 * 
 */
package de.peerthing.simulation.interfaces;

/**
 * Interface for sessions
 * 
 * @author prefec2
 * 
 */
public interface ISession extends IXPathObject {
	/**
	 * Get the port which is assigend to a session
	 * 
	 * @return returns a port or null if no port is set.
	 */
	public IPort getPort();

	/**
	 * Set the port for the session
	 * 
	 * @param port
	 */
	public void setPort(IPort port);

	/**
	 * 
	 * @return returns the id for the session
	 */
	public long getSessionId();
}
