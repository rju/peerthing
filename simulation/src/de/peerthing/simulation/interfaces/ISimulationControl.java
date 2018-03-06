/**
 * 
 */
package de.peerthing.simulation.interfaces;

import java.util.Collection;

import org.eclipse.core.resources.IFile;

/**
 * Interface of the execution component to the GUI
 * 
 * @author prefec2
 * 
 */
public interface ISimulationControl {

	/**
	 * initialize the simulation.
	 * 
	 * @param architecture
	 * @param scenario
	 * @param log
	 */
	public void initialize(String architecture, String scenario, IFile log);

	/**
	 * Processes one event.
	 * 
	 */
	public boolean step();

	/**
	 * Ends the simulation. After calling this function, the simulation can't be
	 * resumed.
	 * 
	 */
	public void end();

	/**
	 * Returns the time of the last processed event.
	 * 
	 * @return
	 */
	public long getSimulationTime();

	/**
	 * Adds a listener to this simulation. Listeners are notified every time a
	 * message is received during the simulation run.
	 * 
	 * @param listener
	 *            The listener to add
	 */
	public void addSimulationListener(IListener listener);

	/**
	 * Returns all nodes that are currently participating in the simulation. May
	 * only be called after initialize.
	 * 
	 * @return
	 */
	public Collection<INode> getAllNodes();

}
