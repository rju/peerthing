package de.peerthing.scenarioeditor.model.impl;

import junit.framework.TestCase;
import de.peerthing.scenarioeditor.model.IScenario;

public class LinkSpeedTest extends TestCase {

	LinkSpeed ls, ls2, ls3;

	private IScenario scen;

	private long long1;

	protected void setUp() throws Exception {
		super.setUp();
		scen = new Scenario();
		ls = new LinkSpeed(scen);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.LinkSpeed.LinkSpeed(IScenario)'
	 */
	public void testLinkSpeedIScenario() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.LinkSpeed.LinkSpeed(ILinkSpeed,
	 * IScenario)'
	 */
	public void testLinkSpeedILinkSpeedIScenario() {
		ls2 = new LinkSpeed(ls, scen);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.LinkSpeed.LinkSpeed(ILinkSpeed)'
	 */
	public void testLinkSpeedILinkSpeed() {
		ls3 = new LinkSpeed(ls);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.LinkSpeed.setDelay(long)'
	 */
	public void testSetDelay() {
		ls.setDelay(long1);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.LinkSpeed.getDelay()'
	 */
	public void testGetDelay() {
		assertEquals(long1, ls.getDelay());
		long1 = 6;
		ls.setDelay(long1);
		assertEquals(long1, ls.getDelay());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.LinkSpeed.setSpeed(long)'
	 */
	public void testSetSpeed() {
		ls.setSpeed(long1);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.LinkSpeed.getSpeed()'
	 */
	public void testGetSpeed() {
		assertEquals(long1, ls.getSpeed());
		long1 = 6;
		ls.setSpeed(long1);
		assertEquals(long1, ls.getSpeed());

	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.LinkSpeed.setScenario(IScenario)'
	 */
	public void testSetScenario() {
		ls.setScenario(scen);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.LinkSpeed.getScenario()'
	 */
	public void testGetScenario() {
		assertSame(scen, ls.getScenario());
		scen = new Scenario();
		assertNotSame(scen, ls.getScenario());
		ls.setScenario(scen);
		assertSame(scen, ls.getScenario());
	}

}
