/**
 * 
 */
package de.peerthing.actioncontributor.actions;

import java.util.List;
import java.util.Map;

import de.peerthing.simulation.interfaces.IActionExecutor;
import de.peerthing.simulation.interfaces.IActionSimulator;
import de.peerthing.simulation.interfaces.IDataStorage;
import de.peerthing.simulation.interfaces.INode;
import de.peerthing.simulation.interfaces.ISystemTask;
import de.peerthing.simulation.interfaces.IUserTask;
import de.peerthing.simulation.interfaces.IXPathContainer;

/**
 * @author prefec2
 *
 */
public class SendEventAction implements IActionExecutor {

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
		/* event construction */
		List<IXPathContainer> paramList = simulator.createParameterList();
		for(String key : parameters.keySet()) {
			String value;
			Object obj = parameters.get(key);
			if (obj instanceof String) {
				value=(String)obj;
			} else
				value="not implemented";
			// TODO add more features for parameters
			paramList.add(simulator.createParameter(key,value));
		}
		/* emit the message delivery event */
		for(IUserTask userTask : contextNode.getUserTaskList()) {
			simulator.emitEvent((String)parameters.get("name"), time,
				contextNode, userTask, paramList);
		}
		return null;
	}

}
