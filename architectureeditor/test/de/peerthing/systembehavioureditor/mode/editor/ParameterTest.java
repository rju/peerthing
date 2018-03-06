package de.peerthing.systembehavioureditor.mode.editor;

import junit.framework.TestCase;
import de.peerthing.systembehavioureditor.model.IAction;
import de.peerthing.systembehavioureditor.model.editor.Action;
import de.peerthing.systembehavioureditor.model.editor.Node;
import de.peerthing.systembehavioureditor.model.editor.Parameter;
import de.peerthing.systembehavioureditor.model.editor.State;
import de.peerthing.systembehavioureditor.model.editor.Task;

public class ParameterTest extends TestCase {

	Parameter para;
	Parameter paratwo;
	private IAction action;
	private String name;
	private String value;
	private String expression;
	private Node node;
	private State state;
	private Task task;
	protected void setUp() throws Exception {
		super.setUp();
		para = new Parameter();
		paratwo = new Parameter(name, action);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Parameter.Parameter(String, IAction, String)'
	 */
	public void testParameterStringIActionString() {

	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Parameter.Parameter()'
	 */
	public void testParameter() {

	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Parameter.setAction(IAction)'
	 */
	public void testSetAction() {
		para.setAction(action);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Parameter.getAction()'
	 */
	public void testGetAction() {
		assertNull(para.getAction());
		para.setAction(action);
		assertSame(action, para.getAction());
		assertSame(action, paratwo.getAction());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Parameter.setName(String)'
	 */
	public void testSetName() {
		para.setName(name);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Parameter.getName()'
	 */
	public void testGetName() {
		assertEquals("", para.getName());
		para.setName(name);
		assertEquals(name, para.getName());
		assertEquals(name, paratwo.getName());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Parameter.setValue(String)'
	 */
	public void testSetValue() {
		para.setValue(value);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Parameter.getValue()'
	 */
	public void testGetValue() {
		assertEquals("", para.getValue());
		para.setValue(null);
		assertEquals("", para.getValue());
		value = "test";
		para.setValue(value);
		assertEquals(value, para.getValue());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Parameter.setExpression(String)'
	 */
	public void testSetExpression() {
		para.setExpression(expression);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Parameter.getExpression()'
	 */
	public void testGetExpression() {
		assertEquals("", para.getExpression());
		para.setExpression(null);
		assertEquals("", para.getExpression());
		expression = "test";
		para.setExpression(expression);
		assertEquals(expression, para.getExpression());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Parameter.getSystemBehaviour()'
	 */
	public void testGetSystemBehaviour() {
		action = new Action();
		state = new State();
		task = new Task();
		node = new Node();
		task.setNode(node);
		state.setTask(task);
		action.setContainer(state);
		para.setAction(action);
		assertEquals(action.getSystemBehaviour(), para.getSystemBehaviour());
	}

}
