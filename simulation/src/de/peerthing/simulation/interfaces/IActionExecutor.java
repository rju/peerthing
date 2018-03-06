package de.peerthing.simulation.interfaces;

import java.util.Map;

/**
 * This interface must be implemented by classes that define actions for the
 * system behaviour.
 * 
 * @author Michael Gottschalk
 * 
 */
public interface IActionExecutor {

	/**
	 * Each action needs a specific amount of time to be processed. This amount
	 * depends on the complexity of the algorithm and is normally calculated by
	 * executeAction itself. This method is used to return the calculated
	 * processing time.
	 * 
	 * @return returns the execution time of the action.
	 */
	public long getExecutionTime();

	/**
	 * Perform a specific action. Like data storage tree transformations,
	 * calculations or anything else you can think of.
	 * 
	 * @param simulator
	 *            the simulator itself.
	 * @param contextNode
	 *            the node which called the action
	 * @param task
	 *            the task where the action belongs to
	 * @param time
	 *            the present time
	 * @param dataStorage
	 *            the global data storage
	 * @param parameters
	 *            a list of parametes for the action
	 * 
	 * @return returns a list or an object of different types, just like a
	 *         XPath-expression
	 */
	public Object executeAction(IActionSimulator simulator, INode contextNode,
			ISystemTask task, long time, IDataStorage dataStorage,
			Map<String, Object> parameters);
}
