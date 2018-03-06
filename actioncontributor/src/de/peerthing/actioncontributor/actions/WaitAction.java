/**
 * 
 */
package de.peerthing.actioncontributor.actions;

import java.util.Map;

import de.peerthing.simulation.interfaces.IActionExecutor;
import de.peerthing.simulation.interfaces.IActionSimulator;
import de.peerthing.simulation.interfaces.IDataStorage;
import de.peerthing.simulation.interfaces.INode;
import de.peerthing.simulation.interfaces.ISystemTask;

/**
 * @author prefec2
 *
 */
public class WaitAction implements IActionExecutor {


	/* (non-Javadoc)
	 * @see de.peerthing.simulation.interfaces.IActionExecutor#getExecutionTime()
	 */
	public long getExecutionTime() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see de.peerthing.simulation.interfaces.IActionExecutor#executeAction(de.peerthing.simulation.interfaces.IActionSimulator, de.peerthing.simulation.interfaces.INode, de.peerthing.simulation.interfaces.ISystemTask, long, de.peerthing.simulation.interfaces.IDataStorage, java.util.Map)
	 */
	public Object executeAction(IActionSimulator simulator, INode contextNode,
			ISystemTask task, long time, IDataStorage dataStorage,
			Map<String, Object> parameters) {
		System.out.println("Press return to continue");
		try {
			System.in.read();
		} catch (Exception e) {
			System.out.println("Big oops in input stream:" + e.getMessage());
		}
		return null;
	}

}
