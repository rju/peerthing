package de.peerthing.systembehavioureditor.model.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import de.peerthing.systembehavioureditor.interchange.EAIInitializeEvaluation;
import de.peerthing.systembehavioureditor.model.IAction;
import de.peerthing.systembehavioureditor.model.ICondition;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.ITransition;
import de.peerthing.systembehavioureditor.model.ITransitionContent;
import junit.framework.TestCase;

public class StateTest extends TestCase {

	State state;
	private String name;
	private int xint;
	private int yint;
	private Vector<ITransition> trans;
	private ITask task;
	private Boolean bool;
	private List<ITransitionContent> actions;
	private EAIInitializeEvaluation eai;
	private List<IAction> iactions;
	private List<ICondition> cons;
	protected void setUp() throws Exception {
		super.setUp();
		state = new State();
		trans = new Vector<ITransition>();
		bool = true;
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.State.State()'
	 */
	public void testState() {

	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.State.getName()'
	 */
	public void testGetName() {
		assertNull(state.getName());
		state.setName(name);
		assertEquals(name, state.getName());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.State.getX()'
	 */
	public void testGetX() {
		assertEquals(50, state.getX());
		state.setX(xint);
		assertSame(xint, state.getX());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.State.getY()'
	 */
	public void testGetY() {
		assertEquals(50, state.getY());
		state.setY(yint);
		assertSame(yint, state.getY());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.State.setName(String)'
	 */
	public void testSetName() {
		state.setName(name);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.State.setX(int)'
	 */
	public void testSetX() {
		state.setX(xint);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.State.setY(int)'
	 */
	public void testSetY() {
		state.setY(yint);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.State.getTransitions()'
	 */
	public void testGetTransitions() {
		assertEquals(trans, state.getTransitions());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.State.getTask()'
	 */
	public void testGetTask() {
		assertNull(state.getTask());
		state.setTask(task);
		assertSame(task, state.getTask());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.State.setTask(ITask)'
	 */
	public void testSetTask() {
		state.setTask(task);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.State.getIsStartState()'
	 */
	public void testGetIsStartState() {
		assertFalse(state.getIsStartState());
		state.setIsStartState(bool);
		assertSame(bool, state.getIsStartState());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.State.setIsStartState(Boolean)'
	 */
	public void testSetIsStartStateBoolean() {
		state.setIsStartState(bool);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.State.setContents(List<ITransitionContent>)'
	 */
	public void testSetContents() {
		state.setContents(actions);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.State.getContents()'
	 */
	public void testGetContents() {
		assertEquals(new ArrayList<ITransitionContent>(), state.getContents());
		state.setContents(actions);
		assertSame(actions, state.getContents());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.State.setInitializeEvaluation(EAIInitializeEvaluation)'
	 */
	public void testSetInitializeEvaluation() {
		state.setInitializeEvaluation(eai);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.State.getInitializeEvaluation()'
	 */
	public void testGetInitializeEvaluation() {
		assertNull(state.getInitializeEvaluation());
		state.setInitializeEvaluation(eai);
		assertSame(eai, state.getInitializeEvaluation());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.State.getActions()'
	 */
	public void testGetActions() {
		assertNull(state.getActions());
		state.setActions(iactions);
		assertSame(iactions, state.getActions());		
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.State.getConditions()'
	 */
	public void testGetConditions() {
		assertNull(state.getConditions());
		state.setConditions(cons);
		assertSame(cons, state.getConditions());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.State.setActions(List<IAction>)'
	 */
	public void testSetActions() {
		state.setActions(iactions);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.State.setConditions(List<ICondition>)'
	 */
	public void testSetConditions() {
		state.setConditions(cons);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.State.getSystemBehaviour()'
	 */
	public void testGetSystemBehaviour() {
		assertNull(state.getSystemBehaviour());
	}

}
