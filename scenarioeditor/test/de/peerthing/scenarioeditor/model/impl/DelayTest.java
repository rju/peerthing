package de.peerthing.scenarioeditor.model.impl;

import junit.framework.TestCase;
import de.peerthing.scenarioeditor.model.IDistribution;
import de.peerthing.scenarioeditor.model.IScenario;

public class DelayTest extends TestCase {

	Delay delay;

	IScenario scen;

	private IDistribution distribution;

	protected void setUp() throws Exception {
		super.setUp();
		scen = new Scenario();
		delay = new Delay(scen);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Delay.Delay(IScenario)'
	 */
	public void testDelayIScenario() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Delay.Delay(IDelay)'
	 */
	public void testDelayIDelay() {
		Delay delay2 = new Delay(delay);
		assertNotSame(delay, delay2);
		assertEquals(delay.getClass(), delay2.getClass());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Delay.Delay(IDelay, IScenario,
	 * Object)'
	 */
	public void testDelayIDelayIScenarioObject() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Delay.setDistribution(IDistribution)'
	 */
	public void testSetDistribution() {
		delay.setDistribution(distribution);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Delay.getDistribution()'
	 */
	public void testGetDistribution() {
		distribution = new Distribution(scen);
		assertEquals(distribution.getClass(), delay.getDistribution()
				.getClass());
	}

}
