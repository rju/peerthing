package de.peerthing.scenarioeditor.model.impl;

import java.util.ArrayList;

import junit.framework.TestCase;
import de.peerthing.scenarioeditor.model.ICommand;
import de.peerthing.scenarioeditor.model.IDistribution;
import de.peerthing.scenarioeditor.model.IScenario;

public class LoopTest extends TestCase {

	Loop loop, loop2;

	private IScenario scen;

	private Command cont;

	private String expr;

	private IDistribution distr;

	private ArrayList<ICommand> commands;

	protected void setUp() throws Exception {
		super.setUp();
		scen = new Scenario();
		loop = new Loop(scen);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Loop.Loop(IScenario)'
	 */
	public void testLoopIScenario() {

	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.Loop.Loop(ILoop)'
	 */
	public void testLoopILoop() {
		loop2 = new Loop(loop);
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.Loop.Loop(ILoop,
	 * IScenario, Object)'
	 */
	public void testLoopILoopIScenarioObject() {
		loop2 = new Loop(loop, scen, cont);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Loop.setUntilCondition(String)'
	 */
	public void testSetUntilCondition() {
		loop.setUntilCondition(expr);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Loop.getUntilCondition()'
	 */
	public void testGetUntilCondition() {
		assertNull(loop.getUntilCondition());
		loop.setUntilCondition(expr);
		assertEquals(expr, loop.getUntilCondition());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Loop.setDistribution(IDistribution)'
	 */
	public void testSetDistribution() {
		loop.setDistribution(distr);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Loop.getDistribution()'
	 */
	public void testGetDistribution() {
		distr = new Distribution(scen);
		assertEquals(distr.getClass(), loop.getDistribution().getClass());
		loop.setDistribution(distr);
		assertSame(distr, loop.getDistribution());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Loop.getCommands()'
	 */
	public void testGetCommands() {
		commands = new ArrayList<ICommand>();
		assertEquals(commands, loop.getCommands());
	}

}
