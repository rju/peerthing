/*
 * Created on ??
 * Changed on 22.06.2006 
 */
package de.peerthing.simulation.execution;

import java.util.Comparator;

import de.peerthing.simulation.interfaces.IEvent;

/**
 * Comparator used internally by the event queue.
 * 
 * @author Gom
 * 
 */
public class EventQueueComparator implements Comparator<IEvent> {

	/**
	 * Default-Constructor
	 * 
	 * @see java.util.Comparator
	 */
	public EventQueueComparator() {
		super();
	}

	/**
	 * @see java.util.Comparator#compare(T, T)
	 */
	public int compare(IEvent arg0, IEvent arg1) {
		return (int) (arg0.getTime() - arg1.getTime());
	}

}
