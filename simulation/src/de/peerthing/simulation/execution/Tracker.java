/**
 * 
 */
package de.peerthing.simulation.execution;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.simulation.interfaces.ISystemTask;

/**
 * @author prefec2
 *
 */
public class Tracker {
	private List<ISystemTask> taskList;
	private int refCount;
	
	public Tracker () {
		this.taskList = new ArrayList<ISystemTask>();
		this.refCount = 0;
	}

	/**
	 * @return Returns the refCount.
	 */
	public int getRefCount() {
		return refCount;
	}

	/**
	 * 
	 *
	 */
	public void incRefCount() {
		this.refCount++;
	}
	
	public void decRefCount() {
		this.refCount--;
	}
	
	public void addTask(ISystemTask node) {
		this.taskList.add(node);
	}
	
	public List<ISystemTask> getTaskList() {
		return this.taskList;
	}
}
