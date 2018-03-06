package de.peerthing.scenarioeditor.model.impl;

import java.util.ArrayList;

import junit.framework.TestCase;
import de.peerthing.scenarioeditor.model.ICommand;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IScenarioCondition;

public class CaseTest extends TestCase {

	Case caseA;

	IScenario scen;

	private String expression;

	private double prob;

	private ArrayList<ICommand> commands;

	private boolean bool;

	private IScenarioCondition scencond;

	protected void setUp() throws Exception {
		super.setUp();
		scen = new Scenario();
		caseA = new Case(scen);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Case.Case(IScenario)'
	 */
	public void testCaseIScenario() {

	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.Case.Case(Case)'
	 */
	public void testCaseCase() {

	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.Case.Case(ICase,
	 * IScenario)'
	 */
	public void testCaseICaseIScenario() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Case.setCondition(String)'
	 */
	public void testSetCondition() {
		caseA.setCondition(expression);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Case.getCondition()'
	 */
	public void testGetCondition() {
		assertEquals("", caseA.getCondition());
		caseA.setCondition(expression);
		assertEquals(expression, caseA.getCondition());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Case.setProbability(double)'
	 */
	public void testSetProbability() {
		caseA.setProbability(prob);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Case.getProbability()'
	 */
	public void testGetProbability() {
		caseA.setProbability(prob);
		assertEquals(prob, caseA.getProbability());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Case.getCommands()'
	 */
	public void testGetCommands() {
		commands = new ArrayList<ICommand>();
		assertEquals(commands, caseA.getCommands());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Case.isConditionUsed()'
	 */
	public void testIsConditionUsed() {
		assertFalse(caseA.isConditionUsed());
		caseA.setConditionUsed(bool);
		assertEquals(bool, caseA.isConditionUsed());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Case.setConditionUsed(boolean)'
	 */
	public void testSetConditionUsed() {
		caseA.setConditionUsed(bool);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Case.getScenario()'
	 */
	public void testGetScenario() {
		assertEquals(scen, caseA.getScenario());
		caseA.setScenario(scen);
		assertEquals(scen, caseA.getScenario());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Case.setScenarioCondition(IScenarioCondition)'
	 */
	public void testSetScenarioCondition() {
		caseA.setScenarioCondition(scencond);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Case.getScenarioCondition()'
	 */
	public void testGetScenarioCondition() {
		assertNull(caseA.getScenarioCondition());
		caseA.setScenarioCondition(scencond);
		assertEquals(scencond, caseA.getScenarioCondition());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Case.setScenario(IScenario)'
	 */
	public void testSetScenario() {
		caseA.setScenario(scen);
	}

}
