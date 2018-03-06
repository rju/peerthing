package de.peerthing.scenarioeditor.model.impl;

import junit.framework.TestCase;
import de.peerthing.scenarioeditor.model.IScenarioObject;

public class ListOfUndosTest extends TestCase {

	ListOfUndos lou;

	private ScenarioUndo undo;

	private IScenarioObject chosen;

	private IScenarioObject involved;

	private byte obj;

	protected void setUp() throws Exception {
		super.setUp();
		chosen = new Scenario();
		involved = new Scenario();
		undo = new ScenarioUndo(chosen, involved, obj);
		lou = new ListOfUndos();
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.ListOfUndos.add(ScenarioUndo)'
	 */
	public void testAddScenarioUndo() {
		// lou.add(undo);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.ListOfUndos.addForRedo(ScenarioUndo)'
	 */
	public void testAddForRedo() {
		// TODO nullpointer
		// lou.addForRedo(undo);
	}

}
