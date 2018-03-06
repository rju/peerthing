package de.peerthing.systembehavioureditor.model.simulation;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.systembehavioureditor.model.IAction;
import de.peerthing.systembehavioureditor.model.ICondition;
import de.peerthing.systembehavioureditor.model.IContentContainer;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.ITransitionContent;
import de.peerthing.systembehavioureditor.model.ITransitionTarget;
import junit.framework.TestCase;

public class TransitionTest extends TestCase {

	Transition trans;
	private ITransitionTarget target;
	private boolean bool;
	private List<ITransitionContent> content;
	private IState state;
	private String newName;
	private ITask task;
	private IContentContainer icc;
	private List<ITransitionContent> itc;
	private List<IAction> actions;
	private List<ICondition> conditions;
	protected void setUp() throws Exception {
		super.setUp();
		trans = new Transition();
		content = new ArrayList<ITransitionContent>();
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Transition.Transition()'
	 */
	public void testTransition() {

	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Transition.getNextState()'
	 */
	public void testGetNextState() {
		assertNull(trans.getNextState());
		trans.setNextState(target);
		assertSame(target, trans.getNextState());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Transition.setNextState(ITransitionTarget)'
	 */
	public void testSetNextState() {
		trans.setNextState(target);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Transition.isEndTask()'
	 */
	public void testIsEndTask() {
		assertFalse(trans.isEndTask());
		trans.setEndTask(bool);
		assertSame(bool, trans.isEndTask());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Transition.setEndTask(boolean)'
	 */
	public void testSetEndTask() {
		trans.setEndTask(bool);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Transition.getContents()'
	 */
	public void testGetContents() {
		assertEquals(content, trans.getContents());
		trans.setContents(content);
		assertSame(content, trans.getContents());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Transition.getState()'
	 */
	public void testGetState() {
		assertNull(trans.getState());
		trans.setState(state);
		assertSame(state, trans.getState());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Transition.setState(IState)'
	 */
	public void testSetState() {
		trans.setState(state);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Transition.getEvent()'
	 */
	public void testGetEvent() {
		assertNull(trans.getEvent());
		trans.setEvent(newName);
		assertEquals(newName, trans.getEvent());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Transition.setEvent(String)'
	 */
	public void testSetEvent() {
		trans.setEvent(newName);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Transition.setStartTask(ITask)'
	 */
	public void testSetStartTask() {
		trans.setStartTask(task);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Transition.getStartTask()'
	 */
	public void testGetStartTask() {
		assertNull(trans.getStartTask());
		trans.setStartTask(task);
		assertSame(task, trans.getStartTask());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Transition.getName()'
	 */
	public void testGetName() {
		target = new State();
		trans.setNextState(target);
		assertEquals(trans.getNextState().getName(),trans.getName());
		assertEquals(target.getName(),trans.getName());	
		trans.setName(newName);
		assertEquals(newName, trans.getName());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Transition.setName(String)'
	 */
	public void testSetName() {
		target = new State();
		trans.setNextState(target);
		trans.setName(newName);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Transition.setContainer(IContentContainer)'
	 */
	public void testSetContainer() {
		trans.setContainer(icc);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Transition.getContainer()'
	 */
	public void testGetContainer() {
		assertNull(trans.getContainer());
		trans.setContainer(icc);
		assertSame(icc, trans.getContainer());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Transition.setContents(List<ITransitionContent>)'
	 */
	public void testSetContents() {
		trans.setContents(itc);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Transition.getActions()'
	 */
	public void testGetActions() {
		assertNull(trans.getActions());
		trans.setActions(actions);
		assertSame(actions, trans.getActions());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Transition.getConditions()'
	 */
	public void testGetConditions() {
		assertNull(trans.getConditions());
		trans.setConditions(conditions);
		assertSame(conditions, trans.getConditions());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Transition.setActions(List<IAction>)'
	 */
	public void testSetActions() {
		trans.setActions(actions);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Transition.setConditions(List<ICondition>)'
	 */
	public void testSetConditions() {
		trans.setConditions(conditions);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Transition.connect()'
	 */
	public void testConnect() {
		trans.connect();
		/*
		 * not implemented
		 */
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Transition.getSystemBehaviour()'
	 */
	public void testGetSystemBehaviour() {
		assertNull(trans.getSystemBehaviour());
	}

}
