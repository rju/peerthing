package de.peerthing.systembehavioureditor.model.simulation;

import java.util.List;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import de.peerthing.systembehavioureditor.model.IAction;
import de.peerthing.systembehavioureditor.model.ICondition;
import de.peerthing.systembehavioureditor.model.ITransitionContent;

public class CaseSystemBehaviourTest extends MockObjectTestCase {

	CaseSystemBehaviour csb;
	Mock mockCondition;
	private String expression;
	private List<ITransitionContent> listITC;
	private List<IAction> listIA;
	private List<ICondition> listIC;
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
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.CaseSystemBehaviour.getActions()'
	 */
	public void testGetActions() {
		csb.setActions(listIA);
		assertSame(listIA, csb.getActions());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.CaseSystemBehaviour.getConditions()'
	 */
	public void testGetConditions() {
		assertNull(csb.getConditions());
		csb.setConditions(listIC);
		assertSame(listIC, csb.getConditions());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.CaseSystemBehaviour.setActions(List<IAction>)'
	 */
	public void testSetActions() {
		csb.setActions(listIA);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.CaseSystemBehaviour.setConditions(List<ICondition>)'
	 */
	public void testSetConditions() {
		csb.setConditions(listIC);
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
		assertNull(csb.getSystemBehaviour());
	}

}
