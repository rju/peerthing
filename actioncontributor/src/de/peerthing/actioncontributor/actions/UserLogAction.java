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

/**
 * Logs a key-value-pair to the database into the userlog-table.
 * 
 * @author prefec2, Michael Gottschalk
 * 
 */
public class UserLogAction implements IActionExecutor {

    /*
     * (non-Javadoc)
     * 
     * @see de.peerthing.simulation.interfaces.IActionExecutor#getExecutionTime()
     */
    public long getExecutionTime() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.peerthing.simulation.interfaces.IActionExecutor#executeAction(de.peerthing.simulation.interfaces.IActionSimulator,
     *      de.peerthing.simulation.interfaces.INode,
     *      de.peerthing.simulation.interfaces.ISystemTask, long,
     *      de.peerthing.simulation.interfaces.IDataStorage, java.util.Map)
     */
    public Object executeAction(IActionSimulator simulator, INode contextNode,
            ISystemTask task, long time, IDataStorage dataStorage,
            Map<String, Object> parameters) {

        for (String param : parameters.keySet()) {
            String value = "";
            Object valueObj = parameters.get(param);
            if (valueObj instanceof List) {
                List valueList = (List) valueObj;
                if (valueList.size() > 0) {
                    value = valueList.get(0).toString();
                }
            } else {
                value = valueObj.toString();
            }

            simulator.getLogger().addUserLogInformation(task.getNode().getId(),
                    time, param, value);
        }

        return null;
    }

}
