package de.peerthing.scenarioeditor.model.impl;

import junit.framework.TestCase;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IUserBehaviour;

public class CallUserBehaviourTest extends TestCase {

	IScenario scenario;

	IUserBehaviour behaviour;

	CallUserBehaviour cub;

	private boolean bool;

	private double prob;

	protected void setUp() throws Exception {
		super.setUp();
		scenario = new Scenario();
		behaviour = new UserBehaviour();
		cub = new CallUserBehaviour(scenario, behaviour);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.CallUserBehaviour.CallUserBehaviour(IScenario,
	 * IUserBehaviour)'
	 */
	public void testCallUserBehaviourIScenarioIUserBehaviour() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.CallUserBehaviour.CallUserBehaviour(CallUserBehaviour)'
	 */
	public void testCallUserBehaviourCallUserBehaviour() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.CallUserBehaviour.getStartTask()'
	 */
	public void testGetStartTask() {
		assertFalse(cub.getStartTask());
		cub.setStartTask(bool);
		assertEquals(bool, cub.getStartTask());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.CallUserBehaviour.CallUserBehaviour(ICallUserBehaviour,
	 * IScenario, Object)'
	 */
	public void testCallUserBehaviourICallUserBehaviourIScenarioObject() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.CallUserBehaviour.setBehaviour(IUserBehaviour)'
	 */
	public void testSetBehaviour() {
		cub.setBehaviour(behaviour);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.CallUserBehaviour.getBehaviour()'
	 */
	public void testGetBehaviour() {
		assertEquals(behaviour, cub.getBehaviour());
		behaviour = new UserBehaviour();
		cub.setBehaviour(behaviour);
		assertEquals(behaviour, cub.getBehaviour());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.CallUserBehaviour.setProbability(double)'
	 */
	public void testSetProbability() {
		cub.setProbability(prob);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.CallUserBehaviour.getProbability()'
	 */
	public void testGetProbability() {
		cub.setProbability(prob);
		assertEquals(prob, cub.getProbability());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.CallUserBehaviour.setStartTask(boolean)'
	 */
	public void testSetStartTask() {
		cub.setStartTask(bool);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.CallUserBehaviour.isStartTask()'
	 */
	public void testIsStartTask() {
		assertFalse(cub.isStartTask());
		cub.setStartTask(bool);
		assertSame(bool, cub.isStartTask());
	}

}
