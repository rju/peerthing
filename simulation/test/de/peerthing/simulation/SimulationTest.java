package de.peerthing.simulation;

import java.util.ArrayList;

import junit.framework.TestCase;

import de.peerthing.simulation.data.Document;
import de.peerthing.simulation.data.Variable;
import de.peerthing.simulation.execution.Simulator;
import de.peerthing.simulation.interfaces.IEvaluate;
import de.peerthing.simulation.interfaces.INode;
import de.peerthing.simulation.interfaces.IXPathContainer;

public class SimulationTest extends TestCase {

	Simulator sim;

	Document doc;

	IEvaluate node;

	INode server;

	public void setUp() {
		sim = new Simulator();

		System.out.println(System.getProperty("user.dir"));
		sim.initialize(
						"trunk/de.peerthing.simulation/test/de/peerthing/simulation/simple.arch",
						"trunk/de.peerthing.simulation/examples/simple.scen",
						null);

		for (INode n : sim.getAllNodes()) {
			if (n.getSystemBehaviourName().equals("server"))
				server = n;
			if(n.getSystemBehaviourName().equals("peer"))
				node = (IEvaluate)n;
		}
//		node = (IEvaluate) sim.getAllNodes().iterator().next();

	}

	public void testEvaluate() {
		assertNotNull(node);

		// make sure there are two nodes in simulation
		assertTrue(new Double(3.0).doubleValue() == ((Double) node
				.evaluate("count(/simulation/node)")).doubleValue());

		// make sure all resources are created
		assertEquals(510.0, ((Double) node
				.evaluate("count(/simulation/resourceDefinition)"))
				.doubleValue());

		// get Server
		ArrayList list = (ArrayList) node
				.evaluate("/simulation/node[@type=\"server\"]");
		assertTrue(list instanceof ArrayList);
		// There should be only one element in the list, the server
		assertTrue(1 == list.size());

		assertSame(server, list.get(0));
	}

	public void testUserTaskRepeatCommandSequence() {

		//ArrayList tasks = ((INode) node).getUserTaskList();

		// assertTrue(tasks.size() == 0);

		// assertFalse(task.repeatCommandSequence());
	}

	public void testVariableDuplicate(){
		Variable var = new Variable("myVar1", "27");
		//duplicate varibale
		Variable duplicated = var.duplicate();

		//make sure that the object is not null
		assertNotNull(duplicated);

		//make sure variables don't reference to the same object.
		assertNotSame(var, duplicated);

	}

	public void testVariableContainerGetVariable() {

		for(int i =  0; i< 50; i++){
			sim.step();
		}

		assertNotNull(((INode) node).getVariable("knownPeer"));
		//make sure that the variable we get is an Object of type IXpathContainer
		assertTrue(((INode) node).getVariable("knownPeer") instanceof IXPathContainer);
		//make sure that we get a variable we really want to get
		assertTrue(((INode) node).getVariable("knownPeer").getElementName().equals("knownPeer"));
		//make sure that the variable we got contains the id of the server.
		assertTrue(((INode) node).getVariable("knownPeer") instanceof Variable);
		//cross check
		assertFalse(((INode) node).getVariable("someVar") != null);

		//get variable 'peerList'
		Variable peerList = (Variable)server.getVariable("peerList");
		assertTrue(peerList instanceof IXPathContainer);
		//make sure the variable contains two objects (list of peers).
		assertTrue(server.getVariable("peerList").getChildAxis().size() == 2);
	}

	public void testVariableContainerSetVaribaleSingle(){
		Variable var = new Variable("myVar1", "27");
		//make sure there is no variable called 'myVar1'
		assertFalse(((INode) node).getVariable("myVar1") != null);
		//add Variable to node.
		((INode) node).addVariable(var);
		//check if the variable now exists in the node.
		assertTrue(((INode) node).getVariable("myVar1") != null);
		//check the value of the variable. Should be 27.
		assertEquals("27", ((INode) node).getVariable("myVar1").getContent());

		//now let's change the value to 0.

		// XPathContainer can't be instantiated - it is abstract!
		/*XPathContainer val = new XPathContainer();
		val.setContent("0");


		((INode) node).setVariable("myVar1", val);
		//check again
		assertEquals("0", ((INode) node).getVariable("myVar1").getContent());*/
	}

	public void testVariableContainerSetVariableArray(){
		//siehe Fehler von 'testVariableContainerSetVaribaleSingle()'
	}

	public void testVariableContainerRemoveVariable(){
		Variable var = new Variable("myVar1", "27");
		//make sure there is no variable called 'myVar1'
		assertFalse(((INode) node).getVariable("myVar1") != null);
		//add Variable to node.
		((INode) node).addVariable(var);
		//check if the variable now exists in the node.
		assertTrue(((INode) node).getVariable("myVar1") != null);

		//remove the variable
		((INode) node).removeVariable("myVar1");

		//check if  the variable was removed
		assertNull(((INode) node).getVariable("myVar1"));
	}

	public void tearDown() {
		sim = null;
	}

}
