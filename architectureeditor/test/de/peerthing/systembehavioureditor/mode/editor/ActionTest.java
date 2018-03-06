package de.peerthing.systembehavioureditor.mode.editor;

import java.util.Hashtable;

import junit.framework.TestCase;
import de.peerthing.systembehavioureditor.model.IContentContainer;
import de.peerthing.systembehavioureditor.model.IParameter;
import de.peerthing.systembehavioureditor.model.editor.Action;

public class ActionTest extends TestCase {

	Action action;
	private String name;
	private String result;
	private IContentContainer container;
	protected void setUp() throws Exception {
		super.setUp();
		action = new Action();
		name = "TestName";
		result = "TestResult";
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Action.Action()'
	 */
	public void testAction() {
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Action.removeParameter(String)'
	 */
	public void testRemoveParameter() {
		/*
		 * in this method there is only a MAP-operation called
		 */
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Action.getParameters()'
	 */
	public void testGetParameters() {
		assertEquals(new Hashtable<String, IParameter>(), action.getParameters());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Action.getName()'
	 */
	public void testGetName() {
		assertSame("", action.getName());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Action.setName(String)'
	 */
	public void testSetName() {
		assertSame("", action.getName());
		action.setName(name);
		assertEquals(name, action.getName());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Action.getResult()'
	 */
	public void testGetResult() {
		assertSame("", action.getResult());
		action.setResult(result);
		assertEquals(result, action.getResult());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Action.setResult(String)'
	 */
	public void testSetResult() {
		action.setResult(result);
		assertEquals(result, action.getResult());		
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Action.setContainer(IContentContainer)'
	 */
	public void testSetContainer() {
		action.setContainer(container);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Action.getContainer()'
	 */
	public void testGetContainer() {
		assertNull(action.getContainer());
		action.setContainer(container);
		assertSame(container, action.getContainer());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Action.setIsCase(boolean)'
	 */
	public void testSetIsCase() {
		/**
		 * not implemented
		 */
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Action.getSystemBehaviour()'
	 */
	public void testGetSystemBehaviour() {
		assertNull(action.getContainer());
	}

}
