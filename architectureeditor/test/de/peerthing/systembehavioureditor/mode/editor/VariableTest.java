package de.peerthing.systembehavioureditor.mode.editor;

import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.editor.Node;
import de.peerthing.systembehavioureditor.model.editor.Variable;
import junit.framework.TestCase;

/**
 * 
 * @author jojo
 *
 */
public class VariableTest extends TestCase {

	Variable var;
	private String name;
	private ITask task;
	private INodeType node;
	private String value;
	private ISystemBehaviour arch;
	protected void setUp() throws Exception {
		super.setUp();
		var = new Variable();
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Variable.Variable()'
	 */
	public void testVariable() {

	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Variable.getName()'
	 */
	public void testGetName() {
		assertNull(var.getName());
		var.setName(name);
		assertSame(name, var.getName());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Variable.setName(String)'
	 */
	public void testSetName() {
		var.setName(name);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Variable.getSystemBehaviour()'
	 */
	public void testGetSystemBehaviour() {
		node = new Node();
		node.setArchitecture(arch);
		var.setNode(node);
		assertEquals(arch, var.getSystemBehaviour());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Variable.getTask()'
	 */
	public void testGetTask() {
		assertNull(var.getTask());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Variable.setTask(ITask)'
	 */
	public void testSetTask() {
		var.setTask(task);
		/**
		 * not implemented
		 */
		
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Variable.setNode(INodeType)'
	 */
	public void testSetNode() {
		var.setNode(node);
		/**
		 * not implemented
		 */
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Variable.getNode()'
	 */
	public void testGetNode() {
		assertNull(var.getNode());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Variable.getInitialValue()'
	 */
	public void testGetInitialValue() {
		assertEquals("", var.getInitialValue());
		var.setInitialValue(null);
		assertEquals("", var.getInitialValue());
		value = "test";
		var.setInitialValue(value);
		assertSame(value, var.getInitialValue());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Variable.setInitialValue(String)'
	 */
	public void testSetInitialValue() {
		var.setInitialValue(value);
	}

}
