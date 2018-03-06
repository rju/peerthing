package de.peerthing.scenarioeditor.model.impl;

import junit.framework.TestCase;
import de.peerthing.scenarioeditor.model.IScenario;

public class DefaultCaseTest extends TestCase {

	DefaultCase dc;

	DefaultCase dc2;

	IScenario scen;

	protected void setUp() throws Exception {
		super.setUp();
		scen = new Scenario();
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.DefaultCase.DefaultCase(IScenario)'
	 */
	public void testDefaultCaseIScenario() {
		dc = new DefaultCase(scen);

	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.DefaultCase.DefaultCase(IDefaultCase,
	 * IScenario)'
	 */
	public void testDefaultCaseIDefaultCaseIScenario() {
		dc = new DefaultCase(scen);
		dc2 = new DefaultCase(dc, scen);

	}

}
