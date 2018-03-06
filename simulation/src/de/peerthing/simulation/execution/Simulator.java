/*
 * Created on 12.02.2006
 * Changed on 22.03.2006
 */
package de.peerthing.simulation.execution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;

import de.peerthing.systembehavioureditor.interchange.IAIVariable;
import de.peerthing.scenarioeditor.interchange.ISIAction;
import de.peerthing.scenarioeditor.interchange.ISICallBehaviour;
import de.peerthing.scenarioeditor.interchange.ISICase;
import de.peerthing.scenarioeditor.interchange.ISICommand;
import de.peerthing.scenarioeditor.interchange.ISICondition;
import de.peerthing.scenarioeditor.interchange.ISIDelay;
import de.peerthing.scenarioeditor.interchange.ISIDistribution;
import de.peerthing.scenarioeditor.interchange.ISILinkSpeed;
import de.peerthing.scenarioeditor.interchange.ISIListen;
import de.peerthing.scenarioeditor.interchange.ISILoop;
import de.peerthing.scenarioeditor.interchange.ISINodeCategory;
import de.peerthing.scenarioeditor.interchange.ISINodeConnection;
import de.peerthing.scenarioeditor.interchange.ISINodeResource;
import de.peerthing.scenarioeditor.interchange.ISIResourceCategory;
import de.peerthing.scenarioeditor.interchange.ISIScenario;
import de.peerthing.scenarioeditor.interchange.ScenarioInterchange;
import de.peerthing.simulation.data.XPathIdAttribute;
import de.peerthing.simulation.interfaces.DataFactory;
import de.peerthing.simulation.interfaces.IActionExecutor;
import de.peerthing.simulation.interfaces.IActionSimulator;
import de.peerthing.simulation.interfaces.IEvent;
import de.peerthing.simulation.interfaces.IMessage;
import de.peerthing.simulation.interfaces.IDataStorage;
import de.peerthing.simulation.interfaces.IListener;
import de.peerthing.simulation.interfaces.INode;
import de.peerthing.simulation.interfaces.IXPathContainer;
import de.peerthing.simulation.interfaces.IPort;
import de.peerthing.simulation.interfaces.IResource;
import de.peerthing.simulation.interfaces.IResourceDefinition;
import de.peerthing.simulation.interfaces.ISimulationControl;
import de.peerthing.simulation.interfaces.ISystemTask;
import de.peerthing.simulation.interfaces.ILogger;
import de.peerthing.simulation.interfaces.ITask;
import de.peerthing.simulation.interfaces.ITransmissionLog;
import de.peerthing.simulation.interfaces.IUserTask;
import de.peerthing.simulation.interfaces.IXPathAttribute;
import de.peerthing.simulation.interfaces.IXPathObject;
import de.peerthing.simulation.interfaces.SimulationException;
import de.peerthing.systembehavioureditor.interchange.ArchitectureInterchange;
import de.peerthing.systembehavioureditor.interchange.IAIAction;
import de.peerthing.systembehavioureditor.interchange.IAIArchitecture;
import de.peerthing.systembehavioureditor.interchange.IAICase;
import de.peerthing.systembehavioureditor.interchange.IAICondition;
import de.peerthing.systembehavioureditor.interchange.IAINodeType;
import de.peerthing.systembehavioureditor.interchange.IAIParameter;
import de.peerthing.systembehavioureditor.interchange.IAIState;
import de.peerthing.systembehavioureditor.interchange.IAITask;
import de.peerthing.systembehavioureditor.interchange.IAITransition;
import de.peerthing.systembehavioureditor.interchange.IAITransitionContent;
import de.peerthing.systembehavioureditor.interchange.IAITransitionTarget;

/**
 * Main class starting a simulation. For running a simulation, first a new
 * instance of this class must be created. Then, initialize must be called which
 * sets up the nodes taking part in the simulation run. Then runSimulation must
 * be called to run the actual simulation.
 * 
 * 
 * @author Michael Gottschalk
 * @author Reiner Jung
 */
public class Simulator implements ISimulationControl, IActionSimulator {
    private int packetSize = 1400;

    private IAIArchitecture architecture;

    private ISIScenario scenario;

    private PriorityBlockingQueue<IEvent> queue;

    private IDataStorage data;

    private Counter nodeId;

    private Counter taskId;

    private Counter sessionId;

    private Counter messageId;

    private Counter resourceId;

    private List<IListener> listeners;

    private ILogger logger;

    private long time;

    private long lastTime;
    
    private Hashtable<Long,Tracker> sessionTracker;

    /**
     * The classes that execute actions. The key is the name of the action.
     */
    private Hashtable<String, IActionExecutor> actionExecutors;

    /**
     * Creates a new Simulator
     * 
     */
    public Simulator() {
        this.listeners = new ArrayList<IListener>();
        this.logger = null;
        this.architecture = null;
        this.scenario = null;
        this.queue = null;
        this.time = 0;
        this.nodeId = new Counter();
        this.taskId = new Counter();
        this.resourceId = new Counter();
        this.messageId = new Counter();
        this.sessionId = new Counter();
        this.sessionTracker = new Hashtable<Long,Tracker>();
    }

    /**
     * Initializes the simulation: creates the nodes taking part in the
     * simulation run, distributes the resources among the nodes, processes the
     * first scenario commands for each node and executes the actions defined in
     * the initialize part of the start state of the architecture of each node.
     * 
     * 
     * @param architecture
     *            The filename of the architecture to use
     * @param scenario
     *            The filename of the scenario to use
     * @param log
     *            name of the database for the log
     */
    public void initialize(String architecture, String scenario, IFile log) {
        /* initialize random generator */
        DataFactory.initializeRandomGenerator();

        /* load architecture files */
        this.architecture = ArchitectureInterchange
                .loadArchitecture(architecture);
        this.scenario = ScenarioInterchange.loadScenario(scenario);

        /* Get the action executors from the extension point */
        createActionContributors();

        /* start the logger */
        String logType;
        if (log == null)
            logType = "ConsoleLogger";
        else
            logType = "HSQLLogger";
        logger = ExecutionFactory.createLogger(log, logType);

        /* setup the event queue */
        this.queue = new PriorityBlockingQueue<IEvent>(11,
                new EventQueueComparator());

        /* initialize DataStorage */
        data = DataFactory.createSimulation(0);

        try {
            /* build resources */
            this.generateResources();

            /* build nodes and start them */
            this.generateNodes();
        } catch (SimulationCanceledException e) {
            logger.discardLog();
            throw e;
        }
    }

    /**
     * Find all action plugins for the simulator and register them in a global
     * hashtable this.actionExecutors
     * 
     */
    private void createActionContributors() {
        actionExecutors = new Hashtable<String, IActionExecutor>();

        IExtension[] extensions = Platform
                .getExtensionRegistry()
                .getExtensionPoint("de.peerthing.simulation.actionRegistration")
                .getExtensions();

        for (IExtension extension : extensions) {
            for (IConfigurationElement conf : extension
                    .getConfigurationElements()) {
                try {
                    String name = conf.getAttribute("name");
                    IActionExecutor ae = (IActionExecutor) conf
                            .createExecutableExtension("class");
                    this.actionExecutors.put(name, ae);

                } catch (Exception e) {
                    System.out
                            .println("Cannot instantiate class of a dependent component:");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Returns the current simulation time.
     * 
     * @return the current simulation time in milliseconds
     */
    public long getSimulationTime() {
        return this.time;
    }

    /**
     * Execute one single event.
     */
    public boolean step() {
        IEvent event = this.queue.poll();
        if (event != null) {
            this.processEvent(event);
            return true;
        } 
        return false;
    }

    /**
     * Check if a data package can be transmitted from one node to another node.
     * If possible, reserve network bandwidth and generate receivePacket and
     * checkPacketDelivery events if neccessary.
     * 
     * @param log
     *            the transmission log on the sender side.
     */
    private void calcTransmission(ITransmissionLog log,
            List<IXPathContainer> paramList) {
        INode source = log.getMessage().getDestination().getRemoteNode();
        INode destination = log.getMessage().getSource().getRemoteNode();
        ISILinkSpeed upload = source.getConnectionCategory().getUplinkSpeed();
        ISILinkSpeed download = destination.getConnectionCategory()
                .getDownlinkSpeed();
        long delay = upload.getDelay() + download.getDelay();
        /* get size of next packet */
        int packetSize;
        if ((log.getProgress() + this.packetSize) < log.getSize())
            packetSize = this.packetSize;
        else
            packetSize = log.getSize() - log.getProgress();

        /* use slower speed */
        long durationUpload;
        long durationDownload;

        durationDownload = (packetSize * 1000) / download.getSpeed();
        durationUpload = (packetSize * 1000) / upload.getSpeed();

        if (source.isUpstreamAvailable(this.time, this.time + durationUpload)
                && destination.isDownstreamAvailable(this.time + delay,
                        this.time + delay + durationDownload)) {
            /* reserve connections */
            source.reserveUpstream(this.time, this.time + durationUpload);
            destination.reserveDownstream(this.time + delay, this.time + delay
                    + durationDownload);

            /* construct receive event */
            List<IXPathContainer> recvParamList = new ArrayList<IXPathContainer>();
            recvParamList.add(DataFactory.createParameter("message", log
                    .getMessage()));
            recvParamList.add(DataFactory.createParameter("length", String
                    .valueOf(packetSize)));
            recvParamList.add(DataFactory.createParameter("offset", String
                    .valueOf(log.getProgress())));
            this.queue.add(new Event("receivePacket", this.time + delay
                    + durationDownload, destination, null, recvParamList));

            /* advance the transfer progress */
            log.setProgress(log.getProgress() + packetSize);

            /* check if this was the last packet */
            if (log.getProgress() < log.getSize()) {
                this.queue.add(new Event("checkPacketDelivery", this.time
                        + delay + durationUpload, source, null, paramList));
            } else {
                /* last packet, drop the log from the sending node */
                source.getSendMessageRegister().removeTransmission(log);
            }
        } else {
            /* recheck transmission */
            long t1 = source.getNextFreeUpstreamTime(this.time + delay,
                    durationUpload);
            long t2 = destination.getNextFreeDownstreamTime(this.time + delay,
                    durationDownload);
            if ((t2 == -1) || (t1 == -1)) {
                this.queue.add(new Event("transmissionError", this.time,
                        destination, destination.getPrimarySystemTask(),
                        paramList));
                this.queue.add(new Event("transmissionAbort", this.time,
                        source, source.getPrimarySystemTask(), paramList));
            } else {
                if (t1 < t2) /* use the bigger value */
                    t1 = t2;
                this.queue.add(new Event("checkPacketDelivery", t1, source,
                        null, paramList));
            }
        }
    }

    /**
     * Process the given event and advance the simulation timer
     * 
     * @param event
     *            the event to be processed
     */
    private void processEvent(IEvent event) {
        this.time = event.getTime();

        if (this.time / 100 > this.lastTime) {
            this.notifyListeners(this.time);
            this.lastTime = this.time / 100;
        }

        /* message transmission events */
        if (event.getName().equals("checkPacketDelivery")) {
            for (IXPathContainer parameter : event.getParameterList()) {
                if (parameter.getElementName().equals("transmission")) {
                    ITransmissionLog log = (ITransmissionLog) parameter
                            .getChildAxis().iterator().next();
                    /* check remote node for transmission */
                    this.calcTransmission(log, event.getParameterList());
                    return;
                }
            }
        } else if (event.getName().equals("receivePacket")) {
            IMessage message = null;
            int size = 0;
            int progress = 0;
            for (IXPathContainer parameter : event.getParameterList()) {
                if (parameter.getElementName().equals("message"))
                    message = (IMessage) parameter.getChildAxis().iterator()
                            .next();
                if (parameter.getElementName().equals("length"))
                    size = (Integer) Integer.valueOf(parameter.getContent());
                if (parameter.getElementName().equals("offset"))
                    progress = (Integer) Integer
                            .valueOf(parameter.getContent());
            }

            IPort destination = message.getDestination();
            IPort source = message.getSource();
            INode destinationNode = source.getRemoteNode();
            INode sourceNode = destination.getRemoteNode();

            /* check if receiver is online */
            if (source.getRemoteNode().getConnectionState().equals("online")) {
                /* try to find message in receiving register */
                ITransmissionLog log = destinationNode
                        .getReceiveMessageRegister().getTransmission(message);
                if (log == null) { /* no log, implies new transmission */
                    log = DataFactory.createTransmissionLog(message);
                    destinationNode.getReceiveMessageRegister()
                            .addTransmission(log);
                }

                /* check if transmission worked */
                if (progress == log.getProgress()) {
                    log.setProgress(log.getProgress() + size);
                    /* check if message is completely transmitted */
                    if (log.getProgress() == log.getSize()) {
                        /* message received */
                    	message.setTimeReceived(this.time);
                        /* cleanup transmission log */
                        destinationNode.getReceiveMessageRegister()
                                .removeTransmission(log);
                        /* construct receiver events */
                        boolean success = false;
                        for (ISystemTask destinationTask : destination
                                .getAllReferences()) {
                            IAIState state = destinationTask.getActiveState();
                            for (IAITransition transition : state
                                    .getTransitions()) {
                                if (transition.getEvent().equals(
                                        message.getName())) {            
                                    /* generate param list for event */
                                    List<IXPathContainer> parameterList = new ArrayList<IXPathContainer>();
                                    IXPathContainer parameter = DataFactory.createParameter("_message");
                                    parameter.addElement(message);
                                    parameterList.add(parameter);
                                    /* trigger task with event */
                                    this.queue.add(new Event(message.getName(),
                                            this.time, destinationNode,
                                            destinationTask, parameterList));
                                    success = true;
                                }
                            }
                        }
                        /* log message arrival */
                        this.logger.addMessage(sourceNode.getId(),
                                destinationNode.getId(),
                                (int)message.getSessionId(), message.getSize(),
                                message.getTimeSent(), message
                                        .getTimeReceived(), message.getName(),
                                success);
                        this.logger.changeSessionEndTime(
                                message.getSessionId(), this.time);
                        /* decrement session id reference counter, if the node will not respond to the message */ 
                        this.sessionTracker.get(message.getSessionId()).decRefCount();
            			
                        notifyListeners(message);
                    }
                } else {
                    List<IXPathContainer> paramList = new ArrayList<IXPathContainer>();
                    paramList.add(DataFactory.createParameter("message",
                            message));

                    this.queue.add(new Event("transmissionError", this.time,
                            destinationNode, destinationNode
                                    .getPrimarySystemTask(), paramList));
                }
            } else {
                List<IXPathContainer> paramList = new ArrayList<IXPathContainer>();
                paramList.add(DataFactory.createParameter("message", message));

                /*
                 * send the abort message to the first task assigned to the
                 * message
                 */
                this.queue
                        .add(new Event("transmissionAbort", this.time,
                                sourceNode, source.getAllReferences().get(0),
                                paramList));
            }
        } else if (event.getName().equals("transmissionAbort")) {
            for (IXPathContainer parameter : event.getParameterList()) {
                if (parameter.getElementName().equals("message")) {
                    IMessage message = (IMessage) parameter.getChildAxis()
                            .iterator().next();
                    INode destinationNode = message.getSource().getRemoteNode();
                    INode sourceNode = message.getDestination().getRemoteNode();
                    sourceNode.getSendMessageRegister().removeTransmission(
                            message);

                    /* log send message failure */
                    this.logger.addMessage(sourceNode.getId(), destinationNode
                            .getId(), (int)message.getSessionId(),
                            message.getSize(), message.getTimeSent(),
                            this.time, message.getName(), false);
                    return;
                }
            }
        } else if (event.getName().equals("transmissionError")) {
            for (IXPathContainer parameter : event.getParameterList()) {
                if (parameter.getElementName().equals("message")) {
                    IMessage message = (IMessage) parameter.getChildAxis()
                            .iterator().next();
                    INode destinationNode = message.getSource().getRemoteNode();
                    INode sourceNode = message.getDestination().getRemoteNode();
                    sourceNode.getSendMessageRegister().removeTransmission(
                            message);

                    /* log send message failure */
                    this.logger.addMessage(sourceNode.getId(), destinationNode
                            .getId(), (int)message.getSessionId(),
                            message.getSize(), message.getTimeSent(),
                            this.time, message.getName(), false);
                    return;
                }
            }
        } else { /* user defined events */
            /* find node and task */
            INode node = event.getLocationNode();
            ITask task = event.getLocationTask();
            if (task instanceof ISystemTask)
                this.executeSystemTask(node, (ISystemTask) task, event);
            else if (task instanceof IUserTask)
                this.executeUserTask(node, (IUserTask) task);
            else
                throw new RuntimeException("Task type "
                        + task.getClass().getName() + " not understood.");
        }
    }

    /**
     * Store the result (if possible) as content in a variable.
     * 
     * @param result
     *            is a list, a string, a integer or any xpath-object we can
     *            think of. But only strings, integer and variables are allowed.
     *            Also lists of these types are allowed.
     * @param variable
     *            the variable to be fill with data.
     */
    private void variableStoreResult(Object result, IXPathContainer variable) {
        if (result instanceof List) {
            /* more than one item */
            variable.setContent("");
            for (Object resultItem : ((List) result)) {
                if (resultItem instanceof IXPathAttribute) {
                    variable.setContent(variable.getContent()
                            + ((IXPathAttribute) resultItem)
                                    .getAttributeStringValue());
                } else if (resultItem instanceof IXPathContainer) {
                    variable.addElement((IXPathContainer) resultItem);
                } else
                    throw new RuntimeException(resultItem.getClass().getName()
                            + " objects cannot be stored in variables.");
            }
        } else {
            throw new RuntimeException(result.getClass().getName()
                    + " not allowed here. Only Lists are expected.");
        }
    }

    /**
     * Store a result (IXPathContainer) in a node
     * 
     * @param result
     * @param node
     */
    private void nodeStoreResult(Object result, INode node) {
        if (result instanceof IXPathContainer) {
            /* set this node variable */
            node.addVariable((IXPathContainer) result);
        } else
            throw new RuntimeException("Cannot insert an "
                    + result.getClass().getName() + " object in a node.");
    }

    /**
     * Store a result (IXPathContainer) in a system task
     * 
     * @param result
     * @param task
     */
    private void systemTaskStoreResult(Object result, ISystemTask task) {
        if (result instanceof IXPathContainer) {
            /* set this node variable */
            task.addVariable((IXPathContainer) result);
        } else
            throw new RuntimeException("Cannot insert an "
                    + result.getClass().getName() + " object in a system task.");
    }

    /**
     * Store a result (String, Integer) in a xpath-attribute
     * 
     * @param result
     * @param attribute
     */
    private void attributeStoreResult(Object result, IXPathAttribute attribute) {
        if (attribute instanceof XPathIdAttribute) {
            if ((result instanceof INode) || (result instanceof ISystemTask)
                    || (result instanceof IResourceDefinition))
                attribute.setValue(result);
            else
                throw new RuntimeException("Error: Try to store a "
                        + result.getClass().getName()
                        + " in an idref-attribute.");
        } else {
            if ((result instanceof String) || (result instanceof Integer)
                    || (result instanceof Double)
                    || (result instanceof Boolean))
                attribute.setValue(result);
            else
                throw new RuntimeException(
                        "Error: "
                                + result.getClass().getName()
                                + " cannot be stored in attributes, only strings and numbers.");
        }
    }

    /**
     * Execute an action of the system behaviour
     * 
     * @param node
     *            the context node where the action is performed
     * @param task
     *            the context system task of the context node where the action
     *            is performed
     * @param action
     *            the action to be performed
     */
    private void executeAction(INode node, ISystemTask task, IAIAction action) {
        Hashtable<String, Object> paramList = new Hashtable<String, Object>();
        for (String paramName : action.getParameters().keySet()) {
            IAIParameter value = action.getParameters().get(paramName);
            if (value.getExpression() != null
                    && !value.getExpression().equals("")) {
            	try {
            		paramList.put(paramName, task.evaluate(value.getExpression()));
            	} catch(SimulationException e) {
            		throw new SimulationException("[" + node.getId() + ":" + task.getId() + "] in a " + action.getName() + " action: parameter select expression " + value.getExpression() + " is faulty and generated the message: " + e.getMessage());
            	}
                /* ignore empty values */
            } else if (value.getValue() != null)
                paramList.put(paramName, value.getValue());
            /*
             * no default adding of a parameter, empty parameter are ignored
             */
        }

        IActionExecutor actionHandle = this.actionExecutors.get(action
                .getName());

        if (actionHandle != null) {
            Object result = actionHandle.executeAction(this, node, task, task
                    .getTaskTime(), this.data, paramList);
            task.advanceTaskTime(actionHandle.getExecutionTime());

            if (result != null) {
                /* find the location for the result */
                if (action.getResult() != null) {
                    Object destination = task.evaluate(action.getResult());
                    if (destination instanceof ArrayList) {
                        /* destination is a list */
                        for (Object obj : ((ArrayList) destination)) {
                            if (obj instanceof IXPathContainer) {
                                this.variableStoreResult(result,
                                        (IXPathContainer) obj);
                            } else if (obj instanceof INode) {
                                this.nodeStoreResult(result, (INode) obj);
                            } else if (obj instanceof ISystemTask) {
                                this.systemTaskStoreResult(result,
                                        (ISystemTask) obj);
                            } else if (obj instanceof IXPathAttribute) {
                                this.attributeStoreResult(result,
                                        (IXPathAttribute) obj);
                            } else
                                throw new RuntimeException(
                                        "Fatal: Missing code: add support for result object type "
                                                + result.getClass().getName());
                        }
                    } else
                        throw new RuntimeException(
                                "Fatal: Destination must always be a list.");
                }
            }
 
        } else {
            throw new SimulationException("Runtime: action " + action.getName()
                    + " is not supported by the active setup.");
        }
    }

    /**
     * Iterate over a list of system commands
     * 
     * @param node
     *            context node for the command sequence
     * @param task
     *            context task for the command sequence
     * @param list
     *            the command sequence
     */
    private void iterateSystemCommands(INode node, ISystemTask task,
            List<IAITransitionContent> list) {
        for (IAITransitionContent content : list) {
            if (content instanceof IAIAction) {
                this.executeAction(node, task, (IAIAction) content);
            } else if (content instanceof IAICondition) {
                this.executeCondition(node, task, (IAICondition) content);
            }
        }
    }

    /**
     * Process the condition statement
     * 
     * @param node
     *            the context node of the condition
     * @param task
     *            the context system task of the condition
     * @param condition
     *            the condition to be evaluated
     */
    private void executeCondition(INode node, ISystemTask task,
            IAICondition condition) {
        for (IAICase checkCase : condition.getCases()) {
        	try {
        		if (task.evaluateCondition(checkCase.getExpression())) {
        			/* case matched */
        			this.iterateSystemCommands(node, task, checkCase.getContents());
        			return;
        		}
            } catch (SimulationException e) {
            	throw new SimulationException("[" + node.getId() + ":" + task.getId() + "] case expression " + 
            			checkCase.getExpression() + " is faulty and generated the message: " + e.getMessage());
            }
        }
        this.iterateSystemCommands(node, task, condition.getDefaultCase()
                .getContents());
    }

    /**
     * Execute a system task. The method processes the body of an transition and
     * terminates at the end.
     * 
     * @param node
     *            context node of the system task
     * @param task
     *            the system task istself
     * @param event
     *            the event which triggerd the execution
     */
    private void executeSystemTask(INode node, ISystemTask task, IEvent event) {
        task.setTaskTime(this.time);
        for (IAITransition transition : task.getActiveState().getTransitions()) {
            if (transition.getEvent().equals(event.getName())) {
            	/* add the message */
            	List<IXPathContainer> list = event.getParameterList();
            	if (list != null) { /* event with parameter list */
	            	if (list.size()>0) { /* event with parameter */
	            		IXPathContainer param = list.get(0);
	            		IMessage message;
	            		if (param.getElementName().equals("_message")) 
	            			 message = (IMessage)param.getChildAxis().get(0);
	                 	else /* user event, construct a message */
	            			message = DataFactory.createMessage(event.getName(),this.getNextSessionId(),this.time,null,null,list);
	            		task.setMessage(message);
	            		if (this.sessionTracker.get(message.getSessionId()) != null)
	            			this.sessionTracker.get(message.getSessionId()).addTask(task);
	            	}
            	}
            	try {
	                /* found a transition which fires on the given event */
	                this.iterateSystemCommands(node, task, transition
	                                .getContents());
	                /* after processing the message, remove the message */
	                IMessage message = task.getMessage();
	                if (message != null) {
	                	this.cleanupSessions(message.getSessionId());
	                	task.removeMessage(); 
	                }
	                /* check target of the transition */
	                IAITransitionTarget target = transition.getNextState();
	                if (target != null) { /* we have a transition */
	                    if (target instanceof IAIState) {
	                        IAIState state = (IAIState) target;
	                        IAIState lastState = task.getActiveState();
	                        /* points to a state: goto new state */
	                        task.setActiveState(state);
	                        /* the state might have an initialize sequence */
	                        if (state.getInitializeEvaluation() != null) {
	                            /* initialize exists */
	                            switch (state.getInitializeEvaluation()) {
	                            case once: // if the previous state != the actual
	                                // state
	                                if (lastState != state)
	                                    this.iterateSystemCommands(node, task,
	                                            state.getContents());
	                                break;
	                            case each: // every time
	                                this.iterateSystemCommands(node, task, state
	                                        .getContents());
	                                break;
	                            default:
	                                throw new RuntimeException(
	                                        "Fatal error: illegal state initialization type");
	                            }
	                        }
	                    } else if (target instanceof IAITask) {
	                        /* points to a task: start task and remain in this state */
	                        IAITask taskImplementation = (IAITask) target;
	                        ISystemTask newTask = DataFactory.createSystemTask(
	                                this.taskId.next(), taskImplementation
	                                        .getStartState(), taskImplementation);
	                        node.addSystemTask(newTask);
	                        /* duplicate port handles */
	                        node.duplicatePortHandles(task, newTask);
	                        /* initialize start state if neccessary */
	                        IAIState state = taskImplementation.getStartState();
	                        if (state.getInitializeEvaluation() != null)
	                            this.iterateSystemCommands(node, task, state
	                                    .getContents());
	                    }
	                } else { /* no target specified => goto same state */
	                	IAIState state = task.getActiveState();
	                	if (state.getInitializeEvaluation() != null) {
                            /* initialize exists */
                            switch (state.getInitializeEvaluation()) {
                            case once: // if the previous state == this state
                                break;
                            case each: // every time
                                this.iterateSystemCommands(node, task, state
                                        .getContents());
                                break;
                            default:
                                throw new RuntimeException(
                                        "Fatal error: illegal state initialization type");
                            }
                        }
	                }
	                /* check if this task terminates */
	                if (transition.isEndTask())
	                    node.removeSystemTask(task);
            	} catch (SimulationException e) {
            		node.printObject();
            		throw new SimulationException("[" + node.getId() + ":" + task.getId() + "] in state '" + task.getActiveState().getName() + "' processing event '" + transition.getEvent() + "': " + e.getMessage());
            	}
            }
        }
    }

    /**
     * Execute a user task. The method processes the body of an user task until
     * it hits a delay or listen statement. Or if the behaviour pattern ends.
     * 
     * @param node
     *            context nod for the user behaviour
     * @param task
     *            the user task running the bahviour.
     */
    private void executeUserTask(INode node, IUserTask task) {
        while (true) {
            ISICommand command = task.getNextCommand();
            if (command instanceof ISIAction) {
                ISIAction action = (ISIAction) command;
                if (action.getProbability() >= DataFactory
                        .getNextRandomNumber()) {
                    /* generate parameters */
                    List<IXPathContainer> paramList = new ArrayList<IXPathContainer>();

                    IXPathContainer parameter;
                    for (String key : action.getParameters().keySet()) {
                        Object result = task.evaluate(action.getParameters()
                                .get(key));
                        parameter = DataFactory.createParameter(key);
                        if (result instanceof String)
                            parameter.setContent((String) result);
                        else if (result instanceof Double)
                        	parameter.setContent(String.valueOf((Double)result));
                        else if (result instanceof List) {
                            for (Object obj : (List) result)
                                if (obj instanceof IXPathObject)
                                    parameter.addElement((IXPathObject) obj);
                                else
                                    throw new RuntimeException(
                                            "Fatal: A list of IXPathObjects contained a non-IXPathObject.");
                        } else
                        	throw new RuntimeException("Fatal: Parameter of a user behaviour action has an unsupported data type " + result.getClass().getName());
                        paramList.add(parameter);
                    }
                    /* execute action */
                    Event event = new Event(action.getName(), this.time, node,
                            node.getPrimarySystemTask(), paramList);
                    this.queue.add(event);
                }
            } else if (command instanceof ISICallBehaviour) {
                ISICallBehaviour behaviour = (ISICallBehaviour) command;
                /* check whether a separat processing path has to be created */

                if (behaviour.isStartTask()) {
                    /* generate a separate user task */
                    IUserTask userTask = DataFactory.createUserTask(this.taskId
                            .next(), behaviour.getBehaviour().getCommands());
                    node.addUserTask(userTask);
                } else {
                    /*
                     * remember the current processing position and call the sub
                     * behaviour
                     */
                    task.pushProcessingEnvironment(DataFactory
                            .createUserStackElement(behaviour.getBehaviour()
                                    .getCommands(), 0, null));
                }
            } else if (command instanceof ISICondition) {
                ISICondition condition = (ISICondition) command;
                /*
                 * check whether the cases are probability based or expression
                 * based. The check tries to check if an expression is given, in
                 * that case the expression is valid else the probabilites are
                 * valid
                 */

                double random = DataFactory.getNextRandomNumber();
                double currentProbability = 0;
                ISICase caseTaken = null;
                for (ISICase c : condition.getCases()) {
                    if (c.getCondition() == null) {
                        currentProbability += c.getProbability();
                        if (random <= currentProbability) {
                            caseTaken = c;
                            break;
                        }
                    } else {
                        if (task.evaluateCondition(c.getCondition())) {
                            caseTaken = c;
                            break;
                        }
                    }
                }

                /* Take the default case if no condition evaluates to true */
                if (caseTaken == null) {
                    if (condition.getDefaultCase() != null) {
                        caseTaken = condition.getDefaultCase();
                    }
                }
                /* load case sequence into the processor */
                task.pushProcessingEnvironment(DataFactory
                        .createUserStackElement(caseTaken.getCommands(), 0,
                                null));
            } else if (command instanceof ISIDelay) {
                /*
                 * emit an event, which calls this user task after the given
                 * time
                 */
                ISIDelay delay = (ISIDelay) command;

                /* setup event */
                Event event = new Event("delay", this.time
                        + this.calcDistribution(delay.getDistribution()), node,
                        task, null);
                this.queue.add(event);
                return;
            } else if (command instanceof ISIListen) {
                /*
                 * emit an event, which calls this user task after the given
                 * time and set a receptor value for another event, which the
                 * user waits for. The delay event, must contain a parameter
                 * which indicates that it belongs to a listen instruction
                 * rather an delay intruction.
                 */
                ISIListen listen = (ISIListen) command;

                /* generate parameters */
                List<IXPathContainer> paramList = new ArrayList<IXPathContainer>();
                paramList.add(DataFactory.createParameter("listen", listen
                        .getEvent()));
                /* setup event */
                Event event = new Event("listen", this.time
                        + this.calcDistribution(listen.getDistribution()),
                        node, task, paramList);
                this.queue.add(event);
                /* mark task */
                task.setListenMark(listen.getEvent());
                return;
            } else if (command instanceof ISILoop) {
                /* initialize a loop by generating a new processing environment */
                ISILoop loop = (ISILoop) command;
                if (loop.getDistribution() != null) {
                    task.pushProcessingEnvironment(DataFactory
                            .createUserStackElement(loop.getCommands(), this
                                    .calcDistribution(loop.getDistribution()),
                                    loop.getUntilCondition()));
                } else {
                    /* loop without maximum loop count */
                    task.pushProcessingEnvironment(DataFactory
                            .createUserStackElement(loop.getCommands(), -1,
                                    loop.getUntilCondition()));
                }
            } else if (command == null) {
                /* end of sequence */
                /*
                 * check whether we are in a loop and if so, set the intruction
                 * pointer to the beginning of the active command sequence
                 */
                if (task.repeatCommandSequence())
                    task.setNextCommand(0);
                else {
                    if (!task.popProcessingEnvironment()) {
                        return;
                    }
                }
            } else {
                throw new RuntimeException("Fatal: "
                        + command.getClass().getName()
                        + " is no valid command.");
            }
        }
    }

    /**
     * Adds a listener to this simulation. Listeners are notified every time a
     * message is received during the simulation run.
     * 
     * @param listener
     *            The listener to add
     */
    public void addSimulationListener(IListener listener) {
        listeners.add(listener);
    }

    /**
     * Notifies the registered listeners when a message is received.
     * 
     * @param message
     *            the message that was received
     */
    private void notifyListeners(IMessage message) {
        for (IListener listener : listeners) {
            listener.messageReceived(message);
        }
    }

    /**
     * Notifies the registered listeners when (significant) a progress change
     * has occured.
     * 
     * @param now
     */
    private void notifyListeners(long now) {
        for (IListener listener : listeners) {
            listener.progress(now);
        }
    }

    /**
     * Returns the logger used for this simulation
     * 
     * @return returns the logger object
     */
    public ILogger getLogger() {
        return logger;
    }

    /**
     * Returns if the probability is evaluated to true at the moment (according
     * to a random number) or not
     * 
     * @param probability
     * @return
     */
    public boolean hasProbability(double probability) {
        return ((DataFactory.getNextRandomNumber() + probability) >= 1);
    }

    /**
     * Generate all resource for the simulation
     * 
     * @param scenario
     *            The scenario definition
     * @param logger
     *            the logger so we can log information
     * @return
     */
    private void generateResources() {
        int l = 0;

        for (ISIResourceCategory category : this.scenario
                .getResourceCategories()) {
            for (int k = 0; k < category.getDiversity(); k++) {
                if (Thread.interrupted()) {
                    throw new SimulationCanceledException();
                }

                int size = this.calcDistribution(category.getSize());
                IResourceDefinition resource = DataFactory
                        .createResourceDefinition(resourceId.next(), size, l, l
                                + category.getPopularity(), category);
                this.data.addResource(resource);

                /* Log information about the resource */
                logger.addResource(resource.getId(), resource.getSize());

                l += category.getPopularity() + 1;
            }
        }
        this.data.setResourcePopularityInterval(l);
    }

    /**
     * Attach resources to a node
     */
    private void attachResources(INode node, ISINodeCategory category) {
        for (ISINodeResource resource : category.getResources()) {
            ISIResourceCategory resourceCategory = resource.getCategory();
            int numOfResources = this.calcDistribution(resource
                    .getNumberDistribution());
            List<IResourceDefinition> definitionList = this.data
                    .getResourceDefinitionListByCategory(resourceCategory);
            if (definitionList.size() > 0) {
                for (int j = 0; (j < numOfResources)
                        && (definitionList.size() > 0); j++) {
                    int random = (int) (DataFactory.getNextRandomNumber() * (definitionList
                            .size() - 1));
                    IResourceDefinition definition = definitionList.get(random);
                    /*
                     * remove resource from the list, so the resource cannot be
                     * selected twice
                     */
                    definitionList.remove(random);
                    IResource res = DataFactory.createResource(definition);
                    ArrayList<String> qList = this.data.getQualityList();
                    res.setQuality(qList);
                    res.insertSegment(DataFactory.createSegment(0, definition
                            .getSize(), qList.get(qList.size() - 1)));
                    node.addResource(res);

                    /*
                     * Log information about the resource that is available on
                     * the node:
                     */
                    logger.addResourceChange(node.getId(), definition.getId(),
                            1, 0, 3);
                }
            } else
                throw new SimulationException("Empty resource category "
                        + resourceCategory.getName());
        }
    }

    /**
     * Generate all nodes for the simulation
     * 
     */
    private void generateNodes() {
        for (ISINodeCategory category : this.scenario.getNodeCategories()) {
            IAINodeType nodeType = this.architecture
                    .getNodeImplementationByName(category.getNodeType());
            if (nodeType == null) {
                throw new SimulationException(
                        "The scenario node category "
                                + category.getName()
                                + " references a non-existing system behaviour node type: "
                                + category.getNodeType());
            }

            for (ISINodeConnection connection : category.getConnections()) {
                /* generate several identical nodes for this connection type */
                for (int i = 0; i < connection.getNumberOfNodes(); i++) {
                    if (Thread.interrupted()) {
                        throw new SimulationCanceledException();
                    }

                    INode node = DataFactory.createNode(this.nodeId.next(),
                            nodeType.getName(), category.getName(), connection
                                    .getCategory());

                    /*
                     * add node to simulation (This must be done here. Otherwise
                     * the DOM is not set up correctly. Especially the document
                     * pointer in task get set wrong.
                     */
                    this.data.addNode(node);

                    logger.addNodeInformation(node.getId(), node
                            .getConnectionCategory().getUplinkSpeed()
                            .getSpeed(), node.getConnectionCategory()
                            .getDownlinkSpeed().getSpeed(), node
                            .getConnectionCategory().getUplinkSpeed()
                            .getDelay(), node.getConnectionCategory()
                            .getDownlinkSpeed().getDelay(), node
                            .getUserBevahiourName(), node
                            .getSystemBehaviourName());

                    /* create variables for the node */
                    for (IAIVariable variable : nodeType.getVariables()) {
                        node.addVariable(DataFactory.createVariable(variable
                                .getName(), variable.getInitialValue()));
                    }
                    
                    /* attach resources to the node */
                    this.attachResources(node, category);

                    /* setup primary system task */
                    IAIState startState = nodeType.getStartTask()
                            .getStartState();
                    IAITask taskImplementation = nodeType.getStartTask();
                    ISystemTask task = DataFactory.createSystemTask(this.taskId
                            .next(), startState, taskImplementation);
                    
                    /* create variables for the task */
                    for (IAIVariable variable : taskImplementation.getVariables()) {
                        task.addVariable(DataFactory.createVariable(variable
                                .getName(), variable.getInitialValue()));
                    }
                    
                    node.addSystemTask(task);

                    /* setup primary user task if a user task is specified */
                    if (category.getPrimaryBehaviour() != null) {
                        /* the node has a user behaviour */
                        List<ISICommand> commandList = category
                                .getPrimaryBehaviour().getCommands();
                        if (commandList.size() > 0) {
                            /* and the behaviour is not empty */
                            IUserTask userTask = DataFactory.createUserTask(
                                    this.taskId.next(), commandList);
                            node.addUserTask(userTask);
                        }
                    }
                }
            }
        }

        /* The nodes are layed out. Now initialize them and run the users */
        for (INode node : this.data.getAllNodes()) {
            ISystemTask task = node.getPrimarySystemTask();
            /* check if the system task has some initialization stuff */
            IAIState state = task.getTaskImplementation().getStartState();
            if (state.getInitializeEvaluation() != null)
                this.iterateSystemCommands(node, task, state.getContents());

            /* start user behaviour so we get the initial events */
            if (node.getUserTaskList().size() > 0)
                this.executeUserTask(node, node.getUserTaskList().get(0));
        }
    }

    /**
     * Calculate integer values for a given distirbution
     * 
     * @param distribution
     * 
     * @return returns an integer
     */
    private int calcDistribution(ISIDistribution distribution) {
        switch (distribution.getType()) {
        case normal:
            int normal = (int) (distribution.getMean() + 
            	DataFactory.getNextGausseanRandomNumber()*
            	distribution.getVariance());
            if (distribution.getMin() < distribution.getMax())
            	if (normal < distribution.getMin())
            		normal = (int) distribution.getMin();
            	if (normal > distribution.getMax())
            		normal = (int) distribution.getMax();
            return normal;
        case uniform:
            return (int) (DataFactory.getNextRandomNumber()
                    * (distribution.getMax() - distribution.getMin()) + distribution
                    .getMin());
        default:
            throw new RuntimeException("Fatal: Illegal distribution type "
                    + distribution.getType());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.peerthing.simulation.interfaces.IActionSimulator#getNextMessageId()
     */
    public long getNextMessageId() {
        return this.messageId.next();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.peerthing.simulation.interfaces.IActionSimulator#getNextSessionId()
     */
    public long getNextSessionId() {
        long id = this.sessionId.next();
        this.logger.addSessionInformation(id, this.time, this.time);
        Tracker tracker = new Tracker();
        tracker.incRefCount();
        this.sessionTracker.put(id,tracker);
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.peerthing.simulation.interfaces.IActionSimulator#getNextResourceId()
     */
    public long getNextResourceId() {
        return this.resourceId.next();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.peerthing.simulation.interfaces.IActionSimulator#emitEvent(java.lang.String,
     *      long, de.peerthing.simulation.interfaces.INode,
     *      de.peerthing.simulation.interfaces.ITask, java.util.List)
     */
    public IEvent emitEvent(String name, long time, INode locationNode,
            ITask locationTask, List<IXPathContainer> parameterList) {
        if (time < this.time)
            throw new RuntimeException(
                    "Fatal: new event occurs before now. Simulation time: "
                            + this.time + " event time " + time);
        IEvent event = new Event(name, time, (INode) locationNode,
                locationTask, parameterList);

        this.queue.add(event);

        return event;
    }

    /**
     * Create an empty parameter list.
     */
    public List<IXPathContainer> createParameterList() {
        return new ArrayList<IXPathContainer>();
    }

    /**
     * Create a single parameter with a name and a string value
     * 
     * @param name
     *            name of the parameter
     * @param value
     *            string value of the parameter
     * 
     * @return a new parameter
     */
    public IXPathContainer createParameter(String name, String value) {
        return DataFactory.createParameter(name, value);
    }

    /**
     * Create a single parameter with a name and without any value
     * 
     * @param name
     *            name of the parameter
     * 
     * @return a new parameter
     */
    public IXPathContainer createParameter(String name) {
        return DataFactory.createParameter(name);
    }

    /**
     * Create a single parameter with a name and a transmissionlog as value
     * 
     * @param name
     *            name of the parameter
     * @param value
     *            transmissionlog
     * 
     * @return a new parameter
     */
    public IXPathContainer createParameter(String name, ITransmissionLog value) {
        return DataFactory.createParameter(name, value);
    }

    /**
     * log the change of the connection state of a node
     * 
     * @param contextNode
     *            node which connection state changed
     */
    public void logNodeConnectionStateChange(INode contextNode) {
        int value;
        String state = contextNode.getConnectionState();
        if (state.equals("online"))
            value = 1;
        else if (state.equals("offline"))
            value = 0;
        else if (state.equals("fail"))
            value = 2;
        else
            value = 3;
        logger.addNodeStateChange(contextNode.getId(), this.time, value);
    }

    /**
     * create a new variable
     * 
     * @param name
     *            name of the variable
     * @param value
     *            string value of the variable
     * 
     * @return a new variable as IXPathContainer
     */
    public IXPathContainer createVariable(String name, String value) {
        return DataFactory.createVariable(name, value);
    }

    /**
     * close the log, opend by initialize
     */
    public void end() {
        if (logger != null) {
            logger.endLog();
        }
    }

    /**
     * Remove the given event from the queue
     * 
     * @param event
     *            the event to be removed
     */
    public void removeEvent(IEvent event) {
        queue.remove(event);
    }

    /**
     * @return returns all nodes known by the data storage as a collection
     * 
     */
    public Collection<INode> getAllNodes() {
        return data.getAllNodes();
    }

	public void trackerIncRefCount(long id) {
		this.sessionTracker.get(id).incRefCount();
	}
	
	private void cleanupSessions(Long id) {
		Tracker tracker = this.sessionTracker.get(id);
		if (tracker != null) {
			if (tracker.getRefCount()==0) {
				/* session is obsolete, drop it */
				for (ISystemTask task : tracker.getTaskList()) 
					task.removeSession(id);
				this.sessionTracker.remove(id);
			}
		}
	}
	
	/*
	 * This methode exists only for test purpose
	 */
	public Hashtable<String, IActionExecutor> getActionExecutors(){
		return this.actionExecutors;
	}
	
	/*
	 * This methode exists only for test purpose
	 */
	public IDataStorage getDataStorage(){
		return this.data;
	}
}
