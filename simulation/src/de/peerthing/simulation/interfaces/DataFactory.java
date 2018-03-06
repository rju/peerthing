/**
 * 
 */
package de.peerthing.simulation.interfaces;

import java.util.List;
import java.util.Random;

import de.peerthing.scenarioeditor.interchange.ISICommand;
import de.peerthing.scenarioeditor.interchange.ISIConnectionCategory;
import de.peerthing.scenarioeditor.interchange.ISIResourceCategory;
import de.peerthing.simulation.data.Document;
import de.peerthing.simulation.data.Message;
import de.peerthing.simulation.data.Node;
import de.peerthing.simulation.data.Parameter;
import de.peerthing.simulation.data.Resource;
import de.peerthing.simulation.data.ResourceDefinition;
import de.peerthing.simulation.data.Segment;
import de.peerthing.simulation.data.Simulation;
import de.peerthing.simulation.data.SystemTask;
import de.peerthing.simulation.data.TransmissionLog;
import de.peerthing.simulation.data.UserStackElement;
import de.peerthing.simulation.data.UserTask;
import de.peerthing.simulation.data.Variable;
import de.peerthing.systembehavioureditor.interchange.IAIState;
import de.peerthing.systembehavioureditor.interchange.IAITask;

/**
 * The DataFactory is a factory class for the data storage of the simulator
 * 
 * @author prefec2
 * 
 */
public class DataFactory {

	private static Random randomGenerator;

	/**
	 * Create a new data storage for the simulator.
	 * 
	 * @param endTime
	 *            end time of the simulation
	 * @return returns the main object for the data storage
	 */
	public static IDataStorage createSimulation(long endTime) {
		Simulation simulation = new Simulation(endTime);
		/*
		 * the document is never used directly, but it is neccessary for the
		 * XPath-engine. However, it also includes objects used for the
		 * evaluation of expression so that these do not need to be created with
		 * every expression evaluation.
		 * 
		 */
		new Document(simulation);

		return simulation;
	}

	/**
	 * Create a new node for the simulation.
	 * 
	 * @param id
	 *            id of the new node
	 * @param systemBehaviourName
	 *            the name of the system behaviour
	 * @param userBehaviourName
	 *            the name of the user behaviour
	 * @param connection
	 *            the connection model
	 * 
	 * @return returns a new node.
	 */
	public static INode createNode(int id, String systemBehaviourName,
			String userBehaviourName, ISIConnectionCategory connection) {
		return new Node(id, systemBehaviourName, userBehaviourName, connection);
	}

	/**
	 * Create a new resource definition
	 * 
	 * @param id
	 *            the unique id of the resource definition
	 * @param size
	 *            the size of the resource
	 * @param start
	 *            begin of the populatiry interval
	 * @param end
	 *            end of the popularity interval
	 * @param category
	 *            category where the resource belongs to
	 * 
	 * @return returns a new resource definition
	 */
	public static IResourceDefinition createResourceDefinition(int id,
			int size, int start, int end, ISIResourceCategory category) {
		return new ResourceDefinition(id, size, start, end, category);
	}

	/**
	 * Create a new resource definition, without the attachment of a resource
	 * category
	 * 
	 * @param id
	 *            the unique id of the resource definition
	 * @param size
	 *            the size of the resource
	 * @param start
	 *            begin of the populatiry interval
	 * @param end
	 *            end of the popularity interval
	 * 
	 * @return returns a new resource definition
	 */
	public static IResourceDefinition createResourceDefinition(int id,
			int size, int start, int end) {
		return new ResourceDefinition(id, size, start, end, null);
	}

	/**
	 * Create a new resource based on a resource definition
	 * 
	 * @param definition
	 *            the resource definition
	 * @return returns the new resource
	 */
	public static IResource createResource(IResourceDefinition definition) {
		return new Resource(definition.getId());
	}

	/**
	 * Create a new empty system task which knows its start state and its
	 * implementation. The id will not be checked for uniqueness.
	 * 
	 * @param id
	 *            unique id of the system task
	 * @param startState
	 *            start state of the task
	 * @param taskImplementation
	 *            task implementation
	 * 
	 * @return returns the new system task
	 */
	public static ISystemTask createSystemTask(int id, IAIState startState,
			IAITask taskImplementation) {
		return new SystemTask(id, startState, taskImplementation);
	}

	/**
	 * Create a new user task with a unique id and a command sequence. The id
	 * will not be checked for uniqueness.
	 * 
	 * @param id
	 *            unique id for the user task
	 * @param commandSequence
	 *            sequence of commands
	 * 
	 * @return returns a new user task
	 */
	public static IUserTask createUserTask(int id,
			List<ISICommand> commandSequence) {
		return new UserTask(id, commandSequence);
	}

	/**
	 * Create a user stack element.
	 * 
	 * @param commandSequence
	 *            sequence of commands
	 * @param maxLoopCount
	 *            number of maximum iterations
	 * @param untilExpr
	 *            expression which defines the end of the loop
	 * 
	 * @return returns a new user stack element
	 */
	public static IUserStackElement createUserStackElement(
			List<ISICommand> commandSequence, int maxLoopCount, String untilExpr) {
		return new UserStackElement(commandSequence, maxLoopCount, untilExpr);
	}

	/**
	 * Create a new variable
	 * 
	 * @param name
	 *            name of the variable
	 * @param initialValue
	 *            initial value of the variable
	 * 
	 * @return returns the newly formed variable
	 */
	public static IXPathContainer createVariable(String name,
			String initialValue) {
		return new Variable(name, initialValue);
	}

	/**
	 * Create a new transmission log, which can be used by the sender or the
	 * receiver of a transmission. For initialisation the message which is
	 * transfered has to be specified.
	 * 
	 * @param message
	 *            the message to send/receive
	 * 
	 * @return a new transmission log
	 */
	public static ITransmissionLog createTransmissionLog(IMessage message) {
		return new TransmissionLog(message.getSize(), message);
	}

	/**
	 * Create an parameter with value as its first element
	 * 
	 * @param name
	 *            name of the parameter
	 * @param value
	 *            value of the parameter
	 * 
	 * @return return the parameter
	 */
	public static IXPathContainer createParameter(String name,
			IXPathObject value) {
		IXPathContainer parameter = new Parameter(name);
		parameter.addElement(value);
		return parameter;
	}

	/**
	 * Create an parameter with value as its content string
	 * 
	 * @param name
	 *            name of the parameter
	 * @param value
	 *            value of the parameter
	 * 
	 * @return return the parameter
	 */
	public static IXPathContainer createParameter(String name, String value) {
		IXPathContainer parameter = new Parameter(name);
		parameter.setContent(value);
		return parameter;
	}

	/**
	 * Create an empty parameter the the given name
	 * 
	 * @param name
	 *            name of the parameter
	 * @return Return the parameter
	 */
	public static IXPathContainer createParameter(String name) {
		return new Parameter(name);
	}

	/**
	 * initialize a random generator for the simulation
	 * 
	 */
	public static void initializeRandomGenerator() {
		randomGenerator = new Random();
	}

	/**
	 * get the next random number
	 * 
	 * @return a random value between 0 and 1
	 */
	public static double getNextRandomNumber() {
		return randomGenerator.nextDouble();
	}
	
	/**
	 * get next gausean random number
	 */
	public static double getNextGausseanRandomNumber() {
		return randomGenerator.nextGaussian();
	}

	/**
	 * Create a data segment for a resource. start must be smaller than end
	 * 
	 * @param start
	 *            offset from the begin of the segment
	 * @param end
	 *            end of the segment
	 * @param quality
	 *            the quality of the segment
	 * 
	 * @return returns a new segment
	 */
	public static ISegment createSegment(int start, int end, String quality) {
		return new Segment(start, end, quality);
	}
	
	/**
	 * Create a user message 
	 */
	public static IMessage createMessage(String name, long sessionId, long time, IPort source,
			IPort destination, List<IXPathContainer> parameterList) {
		return new Message(name,sessionId,time,source,destination,parameterList);
	}
}
