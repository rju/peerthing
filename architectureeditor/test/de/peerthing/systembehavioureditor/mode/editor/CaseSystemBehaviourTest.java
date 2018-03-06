package de.peerthing.systembehavioureditor.mode.editor;

import java.util.List;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import de.peerthing.systembehavioureditor.model.ICondition;
import de.peerthing.systembehavioureditor.model.IContentContainer;
import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.ITransition;
import de.peerthing.systembehavioureditor.model.ITransitionContent;
import de.peerthing.systembehavioureditor.model.editor.CaseSystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Condition;
import de.peerthing.systembehavioureditor.model.editor.Node;
import de.peerthing.systembehavioureditor.model.editor.State;
import de.peerthing.systembehavioureditor.model.editor.Task;
import de.peerthing.systembehavioureditor.model.editor.Transition;

public class CaseSystemBehaviourTest extends MockObjectTestCase {

	CaseSystemBehaviour csb;
	Mock mockCondition;
	private String expression;
	private List<ITransitionContent> listITC;
	protected void setUp() throws Exception {
		super.setUp();
		csb = new CaseSystemBehaviour();
		mockCondition = mock(ICondition.class);
		
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.CaseSystemBehaviour.CaseSystemBehaviour()'
	 */
	public void testCaseSystemBehaviour() {

	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.CaseSystemBehaviour.setCondition(ICondition)'
	 */
	public void testSetCondition() {
		csb.setCondition((ICondition)mockCondition.proxy());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.CaseSystemBehaviour.getCondition()'
	 */
	public void testGetCondition() {
		assertNull(csb.getCondition());
		csb.setCondition((ICondition)mockCondition.proxy());
		assertEquals((ICondition)mockCondition.proxy(), csb.getCondition());

	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.CaseSystemBehaviour.setExpression(String)'
	 */
	public void testSetExpression() {
		csb.setExpression(expression);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.CaseSystemBehaviour.getExpression()'
	 */
	public void testGetExpression() {
		assertNull(csb.getExpression());
		csb.setExpression(expression);
		assertEquals(expression, csb.getExpression());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.CaseSystemBehaviour.getContents()'
	 */
	public void testGetContents() {
		csb.setContents(listITC);
		assertEquals(listITC, csb.getContents());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.CaseSystemBehaviour.setContents(List<ITransitionContent>)'
	 */
	public void testSetContents() {
		csb.setContents(listITC);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.CaseSystemBehaviour.removeAction(IAction)'
	 */
	public void testRemoveAction() {
		/**
		 * list operation
		 */
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.CaseSystemBehaviour.removeCondition(ICondition)'
	 */
	public void testRemoveCondition() {
		/**
		 * list operation
		 */
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.CaseSystemBehaviour.getSystemBehaviour()'
	 */
	public void testGetSystemBehaviour() {
		ICondition cond = new Condition();
		ITransition tran = new Transition();
		IState state = new State();
		Task task = new Task();
		INodeType node = new Node();
		task.setNode(node);
		state.setTask(task);
		tran.setState(state);
		cond.setContainer((IContentContainer)tran);
		csb.setCondition(cond);
		assertEquals(cond.getSystemBehaviour(), csb.getSystemBehaviour());
	}

}
