/**
 * 
 */
package de.peerthing.simulation.execution;

import java.util.List;

import de.peerthing.simulation.interfaces.IEvent;
import de.peerthing.simulation.interfaces.INode;
import de.peerthing.simulation.interfaces.IXPathContainer;
import de.peerthing.simulation.interfaces.ITask;

/**
 * @author prefec2
 * 
 */
public class Event implements IEvent {
	private String name; /* name of the event */

	private long time; /* occurance time */

	private INode locationNode; /* location where the event occurs */

	private ITask locationTask; /* task which the event is addressed to */

	private List<IXPathContainer> parameterList;

	public Event(String name, long time, INode locationNode,
			ITask locationTask, List<IXPathContainer> parameterList) {
		this.name = name;
		this.time = time;
		this.locationNode = locationNode;
		this.locationTask = locationTask;
		this.parameterList = parameterList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.interfaces.IEvent#getLocationNode()
	 */
	public INode getLocationNode() {
		return locationNode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.interfaces.IEvent#getLocationTask()
	 */
	public ITask getLocationTask() {
		return locationTask;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.interfaces.IEvent#getName()
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.interfaces.IEvent#getTime()
	 */
	public long getTime() {
		return time;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.interfaces.IEvent#getParameterList()
	 */
	public List<IXPathContainer> getParameterList() {
		return this.parameterList;
	}

}
