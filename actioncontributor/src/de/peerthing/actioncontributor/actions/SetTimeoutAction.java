package de.peerthing.actioncontributor.actions;

import java.util.Map;

import de.peerthing.simulation.interfaces.IActionExecutor;
import de.peerthing.simulation.interfaces.IActionSimulator;
import de.peerthing.simulation.interfaces.IDataStorage;
import de.peerthing.simulation.interfaces.IEvent;
import de.peerthing.simulation.interfaces.INode;
import de.peerthing.simulation.interfaces.ISystemTask;

public class SetTimeoutAction implements IActionExecutor {

    public long getExecutionTime() {
        return 0;
    }

    public Object executeAction(IActionSimulator simulator, INode contextNode,
            ISystemTask task, long time, IDataStorage dataStorage,
            Map<String, Object> parameters) {
        String eventName = (String) parameters.get("event");
        long waitTime = Long.parseLong((String) parameters.get("time"));

        IEvent oldEvent = task.getTimoutEvent(eventName);
        if (oldEvent != null) {
            simulator.removeEvent(oldEvent);
        }
        
        if (waitTime >= 0) {
            IEvent event = simulator.emitEvent(eventName, time + waitTime, contextNode, task, simulator.createParameterList());
            task.setTimeoutEvent(eventName, event);
        }
        
        return null;
    }

}
