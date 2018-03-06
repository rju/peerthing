package de.peerthing.simulation.interfaces;

import java.util.List;

/**
 * This interface encapsulated an event.
 * 
 * @author Michael Gottschalk
 * 
 */
public interface IEvent {

	/**
	 * @return Returns the location node.
	 */
	public INode getLocationNode();

	/**
	 * @return Returns the location task.
	 */
	public ITask getLocationTask();

	/**
	 * @return Returns the name.
	 */
	public String getName();

	/**
	 * @return Returns the time.
	 */
	public long getTime();

	/**
	 * @return Returns a collection over the parameter array
	 */
	public List<IXPathContainer> getParameterList();
}
