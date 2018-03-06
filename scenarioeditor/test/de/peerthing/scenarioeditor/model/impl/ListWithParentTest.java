package de.peerthing.scenarioeditor.model.impl;

import junit.framework.TestCase;
import de.peerthing.scenarioeditor.model.IScenario;

public class ListWithParentTest extends TestCase {

	ListWithParent lwp;

	private Object parent;

	private String name;

	private IScenario scen;

	protected void setUp() throws Exception {
		super.setUp();
		parent = new Object();
		lwp = new ListWithParent(parent, name);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.ListWithParent.ListWithParent(Object,
	 * String, IScenario)'
	 */
	public void testListWithParentObjectStringIScenario() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.ListWithParent.ListWithParent(Object,
	 * String)'
	 */
	public void testListWithParentObjectString() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.ListWithParent.getName()'
	 */
	public void testGetName() {
		assertEquals(name, lwp.getName());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.ListWithParent.getParent()'
	 */
	public void testGetParent() {
		assertEquals(parent, lwp.getParent());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.ListWithParent.toString()'
	 */
	public void testToString() {
		assertEquals(name, lwp.toString());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.ListWithParent.getScenario()'
	 */
	public void testGetScenario() {
		scen = new Scenario();
		assertNull(lwp.getScenario());
		lwp.setScenario(scen);
		assertEquals(scen, lwp.getScenario());
		lwp = new ListWithParent(parent, name, scen);
		assertEquals(scen, lwp.getScenario());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.ListWithParent.setScenario(IScenario)'
	 */
	public void testSetScenario() {
		lwp.setScenario(scen);
	}

}
