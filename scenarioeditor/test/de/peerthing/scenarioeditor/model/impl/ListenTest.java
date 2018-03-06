package de.peerthing.scenarioeditor.model.impl;

import junit.framework.TestCase;
import de.peerthing.scenarioeditor.model.ICommandContainer;
import de.peerthing.scenarioeditor.model.IDistribution;
import de.peerthing.scenarioeditor.model.IScenario;

public class ListenTest extends TestCase {

	Listen listen, listen2;

	private IScenario scen;

	private ICommandContainer obj;

	private IDistribution distr;

	private String event;

	protected void setUp() throws Exception {
		super.setUp();
		scen = new Scenario();
		listen = new Listen(scen);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Listen.Listen(IScenario)'
	 */
	public void testListenIScenario() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Listen.Listen(IListen, IScenario,
	 * Object)'
	 */
	public void testListenIListenIScenarioObject() {
		obj = new Loop(scen);
		listen2 = new Listen(listen, scen, obj);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Listen.Listen(IListen)'
	 */
	public void testListenIListen() {
		listen2 = new Listen(listen);
		assertEquals(listen2.getClass(), listen.getClass());
		assertNotSame(listen, listen2);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Listen.setDistribution(IDistribution)'
	 */
	public void testSetDistribution() {
		listen.setDistribution(distr);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Listen.getDistribution()'
	 */
	public void testGetDistribution() {
		distr = new Distribution(scen);
		assertEquals(distr.getClass(), listen.getDistribution().getClass());
		listen.setDistribution(distr);
		assertEquals(distr, listen.getDistribution());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Listen.getEvent()'
	 */
	public void testGetEvent() {
		assertNull(listen.getEvent());
		listen.setEvent(event);
		assertEquals(event, listen.getEvent());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Listen.setEvent(String)'
	 */
	public void testSetEvent() {
		listen.setEvent(event);
	}

}
