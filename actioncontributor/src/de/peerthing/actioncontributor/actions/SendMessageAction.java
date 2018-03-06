/**
 * 
 */
package de.peerthing.actioncontributor.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import de.peerthing.simulation.interfaces.IActionExecutor;
import de.peerthing.simulation.interfaces.IActionSimulator;
import de.peerthing.simulation.interfaces.IDataStorage;
import de.peerthing.simulation.interfaces.IPort;
import de.peerthing.simulation.interfaces.IResourceDefinition;
import de.peerthing.simulation.interfaces.ISession;
import de.peerthing.simulation.interfaces.ITransmissionLog;
import de.peerthing.simulation.interfaces.INode;
import de.peerthing.simulation.interfaces.ISystemTask;
import de.peerthing.simulation.interfaces.IXPathAttribute;
import de.peerthing.simulation.interfaces.IXPathContainer;
import de.peerthing.simulation.interfaces.IXPathObject;
import de.peerthing.simulation.interfaces.SimulationException;

/**
 * The sendMessageAction class implements an action, which creates a message and sends it to one or more destinations.
 * The action needs the parameters destination and name to work properly.
 * 
 * destination could be an id (string or number), a node, an attribute, a container with the id as content, a session or a port.
 * Also lists of these objects are allowed too.
 * 
 * If the message is sent to more than one desitnation, the message gets duplicated which includes the content. 
 * It is save to send "nodes" as message content, however it is stupid to do so.
 * 
 * @author prefec2
 * 
 */
public class SendMessageAction implements IActionExecutor {

    long runtime;
    
    private long sessionId = 0;

    private void startMessageTransfer(IActionSimulator simulator,
            INode contextNode, long time, ITransmissionLog transmission) {
        /* event construction */
        List<IXPathContainer> paramList = simulator.createParameterList();
        paramList.add(simulator.createParameter("transmission", transmission));
        /* emit the message delivery event */
        simulator.emitEvent("checkPacketDelivery", time, contextNode, null,
                paramList);
        this.runtime++;
    }
    
    /**
     * generate a new session id, but only once for each time sendMessageAction is called
     * @param simulator
     */
    private long fetchSessionId(IActionSimulator simulator) {
    	if (this.sessionId==0)
    		this.sessionId = simulator.getNextSessionId();
    	return this.sessionId;
    }

    /**
     * findDestination gets a list or element of destination descriptors, which are decoded to
     * nodes or ports. If a node or port is found, the method composes the message out of
     * the precomiled parameter list, the name and the destination and relays it to the
     * transport layer.
     * 
     * @param simulator the simulator object
     * @param contextNode the context node where the action si performed
     * @param task the context task where the action belongs to 
     * @param time time the message is sent 
     * @param dataStorage the data storage of the simulation (RAM)
     * @param destination the list or element which references the destination
     * @param messageParameterList the list of parameters of the message
     * @param length the calculated length of the message
     * @param name the name of the message
     */
    private void findDestination(IActionSimulator simulator, INode contextNode,
            ISystemTask task, long time, IDataStorage dataStorage,
            Object destination, List<IXPathContainer> messageParameterList,
            int length, String name) {

        if (destination instanceof Collection) { 
        	/* generate multiple messages */
            Collection list = (Collection) destination;
            if (list.size()>0)
            	for (Object obj : list)
            		findDestination(simulator, contextNode, task, time,
            				dataStorage, obj, messageParameterList, length, name);
            else
            	throw new SimulationException("action sendMessage: Destination is an empty list. No valid destination found.");
        } else if (destination instanceof String) {
            try {
                findDestination(simulator, contextNode, task, time,
                        dataStorage, dataStorage.getNode(Integer
                                .parseInt((String) destination)),
                        messageParameterList, length, name);
            } catch (NumberFormatException e) {
                throw new SimulationException("action sendMessage: Destination-Id ["
                        + (String) destination + "] is not a number.");
            }
        } else if (destination instanceof Integer) {
            findDestination(simulator, contextNode, task, time, dataStorage,
                    dataStorage.getNode((Integer) destination),
                    messageParameterList, length, name);
        } else if (destination instanceof INode) {
            long sid;
            if (task.getMessage() != null) /* reuse id if present */
                sid = task.getMessage().getSessionId();
            else
                /* create new session */
                sid = this.fetchSessionId(simulator);
            simulator.trackerIncRefCount(sid);
            this.startMessageTransfer(simulator, contextNode, time, contextNode
                    .sendMessage(name, sid, time, length, task,
                            (INode) destination, messageParameterList));
        } else if (destination instanceof IXPathContainer) {
            String idString = ((IXPathContainer) destination).getContent();
            if ((idString != null) && (idString.length() > 0))
                findDestination(simulator, contextNode, task, time,
                        dataStorage, idString, messageParameterList, length,
                        name);
            else
                findDestination(simulator, contextNode, task, time,
                        dataStorage, ((IXPathContainer) destination)
                                .getChildAxis(), messageParameterList, length,
                        name);
        } else if (destination instanceof IXPathAttribute) {
            findDestination(simulator, contextNode, task, time, dataStorage,
                    ((IXPathAttribute) destination).getValue(),
                    messageParameterList, length, name);
        } else if (destination instanceof IPort) {
            IPort port = (IPort) destination;
            long sid;
            ISession session = task.getSessionByPort(port);
            if (session != null)
                sid = session.getSessionId();
            else {
                if (task.getMessage() != null) /* reuse id if present */
                    sid = task.getMessage().getSessionId();
                else
                    /* create new session */
                    sid = this.fetchSessionId(simulator); 
            }
            simulator.trackerIncRefCount(sid);
            this.startMessageTransfer(simulator, contextNode, time, contextNode
                    .sendMessage(name, sid, time, length, task, port,
                            messageParameterList));
        } else if (destination instanceof ISession) {
            ISession session = (ISession) destination;
            simulator.trackerIncRefCount(session.getSessionId());
            this.startMessageTransfer(simulator, contextNode, time, contextNode
                    .sendMessage(name, session.getSessionId(), time, length,
                            task, session.getPort(), messageParameterList));
        } else if (destination == null)
            throw new SimulationException("action sendMessage: Destination is empty");
        else
            throw new SimulationException("action sendMessage: "
                    + destination.getClass().getName()
                    + " not allowed as destination address for messages.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.peerthing.simulation.interfaces.IActionExecutor#executeAction(de.peerthing.simulation.interfaces.IActionNode,
     *      long, de.peerthing.simulation.interfaces.IDataStorage,
     *      de.peerthing.simulation.interfaces.IParamValue, java.util.Map)
     */
    public Object executeAction(IActionSimulator simulator, INode contextNode,
            ISystemTask task, long time, IDataStorage dataStorage,
            Map<String, Object> parameters) {
        Object name = parameters.get("name");
        Object destination = parameters.get("destination");
        Object size = parameters.get("size");
        this.sessionId = 0; /* make sure sessionId is ZERO so at least one new id is fetched and not
        					   the id from the previous run is used */ 
        if ((name != null) && (destination != null)) {
            int length = 0;

            /* generate parameter list of the message */
            List<IXPathContainer> messageParameterList = new ArrayList<IXPathContainer>();
            for (String key : parameters.keySet()) {
                Object value = parameters.get(key);
                if (value instanceof String) {
                    length += ((String) value).length();
                    messageParameterList.add(simulator.createParameter(key,
                            (String) value));
                } else if (value instanceof Number) {
                    length += 4;
                    messageParameterList.add(simulator.createParameter(key,
                            String.valueOf((Number) value)));
                } else if (value instanceof List) {

                    List list = (List) value;
                    length += 4;
                    IXPathContainer parameter = simulator.createParameter(key);

                    for (Object obj : list) {
                        if (obj instanceof IXPathAttribute) {
                            IXPathAttribute attr = (IXPathAttribute) obj;
                            if (attr.getValue() instanceof String)
                                parameter.setContent((String) attr.getValue());
                            else if (attr.getValue() instanceof IXPathObject)
                                parameter.addElement((IXPathObject) attr
                                        .getValue());
                            else if (attr.getValue() instanceof Integer)
                                parameter.setContent(String
                                        .valueOf((Integer) attr.getValue()));
                            else
                                throw new SimulationException("action sendMessage: Fatal error: Attribute contains illegal object "
                                                + attr.getValue().getClass()
                                                        .getName());
                        } else if (obj instanceof IResourceDefinition) {
                            parameter.addElement((IResourceDefinition) obj);
                        } else if (obj instanceof ISession) {
                            parameter.addElement(((ISession) obj).getPort()
                                    .getRemoteNode());
                        } else if (obj instanceof IXPathObject) {
                            IXPathObject xpobj = (IXPathObject) obj;
                            length += xpobj.getSize();
                            parameter.addElement(xpobj);
                        } else
                            throw new SimulationException("action sendMessage: Fatal error: List contains illegal object "
                                            + obj.getClass().getName());
                    }
                    messageParameterList.add(parameter);
                } else {
                    throw new SimulationException(
                            "Implementation error: Missing handling for "
                                    + value.getClass().getName()
                                    + " in SendMessage");
                }

            }

            int v;
            if (size instanceof String)
                v = Integer.parseInt((String) size);
            else if (size instanceof Integer)
                v = ((Integer) size).intValue();
            else
                v = 0;
            if (length < v)
                length = v;

            /* calculate message parameters */
            if (name instanceof String)
                this.findDestination(simulator, contextNode, task, time,
                        dataStorage, destination, messageParameterList, length,
                        (String) name);
            else if (name instanceof ArrayList) { /*
                                                     * some boxing from a
                                                     * XPATh-expression
                                                     */
                /* check if we have only one element */
                ArrayList list = (ArrayList) name;
                if (list.size() == 1) { /* ok we have on content element */
                    Object obj = list.get(0);
                    if (obj instanceof IXPathContainer) { /* parameter box */
                        Object value = ((IXPathContainer) obj).getContent();
                        if (value instanceof String)
                            this.findDestination(simulator, contextNode, task,
                                    time, dataStorage, destination,
                                    messageParameterList, length,
                                    (String) value);
                        else
                            throw new SimulationException("action sendMessage: Message name must be a name not a "
                                            + value.getClass().getName());
                    } else if (obj instanceof IXPathContainer) { /*
                                                                     * not boxed
                                                                     * in a
                                                                     * parameter,
                                                                     * assume
                                                                     * container
                                                                     */
                        Object value = ((IXPathContainer) obj).getContent();
                        if (value instanceof String)
                            this.findDestination(simulator, contextNode, task,
                                    time, dataStorage, destination,
                                    messageParameterList, length,
                                    (String) value);
                        else
                            throw new SimulationException("action sendMessage: Message name must be a name not a "
                                            + value.getClass().getName());
                    } else
                        throw new SimulationException("action sendMessage: Message name must not be boxed in "
                                        + obj.getClass().getName());
                } else if (list.size() == 0) /* list is empty => no name */
                    throw new SimulationException("action sendMessage: Message has no name.");
                else
                    throw new SimulationException("action sendMessage: Message name is a list of values. but only one name is allowd.");

            } else
                throw new SimulationException("action sendMessage: Message name must be a name not a "
                        + name.getClass().getName());
        } else {
            throw new SimulationException("action sendMessage: Missing parameter for sendMessage. Expected: name, destination");
        }
        return null;
    }

    public long getExecutionTime() {
        return this.runtime;
    }

}
