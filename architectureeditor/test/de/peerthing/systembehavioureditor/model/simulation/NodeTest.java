package de.peerthing.systembehavioureditor.model.simulation;

import java.util.List;

import de.peerthing.systembehavioureditor.model.ISystemBehaviour;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.IVariable;
import junit.framework.TestCase;

public class NodeTest extends TestCase {

	Node node;
	private List<IVariable> vars;
	private String name;
	private ISystemBehaviour arch;
	private ITask startTask;
	protected void setUp() throws Exception {
		super.setUp();
		node = new Node();
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Node.Node(String)'
	 */
	public void testNodeString() {

	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Node.Node()'
	 */
	public void testNode() {

	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Node.getVariables()'
	 */
	public void testGetVariables() {
		assertNotNull(node.getVariables());
		node.setVariables(vars);
		assertSame(vars, node.getVariables());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Node.getTasks()'
	 */
	public void testGetTasks() {
		assertNotNull(node.getTasks());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Node.getName()'
	 */
	public void testGetName() {
		assertNull(node.getName());
		Node stringNode = new Node(name);
		assertEquals(name, stringNode.getName());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Node.setName(String)'
	 */
	public void testSetName() {
		node.setName(name);
		assertEquals(name, node.getName());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Node.setArchitecture(ISystemBehaviour)'
	 */
	public void testSetArchitecture() {
		node.setArchitecture(arch);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Node.getArchitecture()'
	 */
	public void testGetArchitecture() {
		assertNull(node.getArchitecture());
		node.setArchitecture(arch);
		assertSame(arch, node.getArchitecture());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Node.setStartTask(ITask)'
	 */
	public void testSetStartTask() {
		node.setStartTask(startTask);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Node.getStartTask()'
	 */
	public void testGetStartTask() {
		assertNull(node.getStartTask());
		node.setStartTask(startTask);
		assertSame(startTask, node.getStartTask());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Node.getSystemBehaviour()'
	 */
	public void testGetSystemBehaviour() {
		assertNull(node.getSystemBehaviour());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Node.setVariables(List<IVariable>)'
	 */
	public void testSetVariables() {
		node.setVariables(vars);
	}

}
