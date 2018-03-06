package de.peerthing.systembehavioureditor.model.simulation;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.IVariable;
import junit.framework.TestCase;

public class TaskTest extends TestCase {

	Task task;
	private int newX;
	private int newY;
	private String newName;
	private IState state;
	private IState statetwo;
	private List<IVariable> vars;
	private ArrayList<IState>arraylist;
	private INodeType node;
	
	protected void setUp() throws Exception {
		super.setUp();
		task = new Task();
		arraylist =  new ArrayList<IState>();
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Task.Task()'
	 */
	public void testTask() {
		
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Task.getX()'
	 */
	public void testGetX() {
		assertEquals(10, task.getX());
		task.setX(newX);
		assertSame(newX, task.getX());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Task.getY()'
	 */
	public void testGetY() {
		assertEquals(10, task.getY());
		task.setY(newY);
		assertSame(newY, task.getY());

	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Task.setX(int)'
	 */
	public void testSetX() {
		task.setX(newX);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Task.setY(int)'
	 */
	public void testSetY() {
		task.setY(newY);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Task.getName()'
	 */
	public void testGetName() {
		assertEquals("", task.getName());
		task.setName(newName);
		assertEquals(newName, task.getName());		
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Task.setName(String)'
	 */
	public void testSetName() {
		task.setName(newName);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Task.getStartState()'
	 */
	public void testGetStartState() {
		assertNull(task.getStartState());
		task.setStartState(state);
		assertSame(state, task.getStartState());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Task.addState(IState)'
	 */
	public void testAddState() {
		task.addState(statetwo);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Task.setStartState(IState)'
	 */
	public void testSetStartState() {
		task.setStartState(state);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Task.getVariables()'
	 */
	public void testGetVariables() {
		assertEquals(new ArrayList<IVariable>(), task.getVariables());
		task.setVariables(vars);
		assertSame(vars, task.getVariables());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Task.getStates()'
	 */
	public void testGetStates() {
		assertEquals(arraylist , task.getStates());
		task.addState(statetwo);
		arraylist.add(statetwo);
		assertEquals(arraylist, task.getStates());		
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Task.getNode()'
	 */
	public void testGetNode() {
		assertNull(task.getNode());
		task.setNode(node);
		assertSame(node, task.getNode());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Task.setNode(INodeType)'
	 */
	public void testSetNode() {
		task.setNode(node);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Task.removeState(IState)'
	 */
	public void testRemoveState() {
		task.removeState(state);
		/*
		 * not implemented
		 */
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Task.getSystemBehaviour()'
	 */
	public void testGetSystemBehaviour() {
		assertNull(task.getSystemBehaviour());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Task.setVariables(List<IVariable>)'
	 */
	public void testSetVariables() {
		task.setVariables(vars);
	}

}
