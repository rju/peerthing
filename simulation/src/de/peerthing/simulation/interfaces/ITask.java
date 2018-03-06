/**
 * 
 */
package de.peerthing.simulation.interfaces;

/**
 * @author prefec2
 * 
 */
public interface ITask extends IEvaluate, IXPathObject {
	/**
	 * Return the id of the task
	 */
	public int getId();
}
