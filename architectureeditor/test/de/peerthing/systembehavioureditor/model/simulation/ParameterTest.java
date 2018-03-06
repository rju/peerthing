package de.peerthing.systembehavioureditor.model.simulation;

import de.peerthing.systembehavioureditor.model.IAction;
import junit.framework.TestCase;

public class ParameterTest extends TestCase {

	Parameter para;
	Parameter paratwo;
	private IAction action;
	private String name;
	private String key;
	private String value;
	private String expression;
	protected void setUp() throws Exception {
		super.setUp();
		para = new Parameter();
		paratwo = new Parameter(name, action, key);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Parameter.Parameter(String, IAction, String)'
	 */
	public void testParameterStringIActionString() {

	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Parameter.Parameter()'
	 */
	public void testParameter() {

	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Parameter.setAction(IAction)'
	 */
	public void testSetAction() {
		para.setAction(action);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Parameter.getAction()'
	 */
	public void testGetAction() {
		assertNull(para.getAction());
		para.setAction(action);
		assertSame(action, para.getAction());
		assertSame(action, paratwo.getAction());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Parameter.setName(String)'
	 */
	public void testSetName() {
		para.setName(name);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Parameter.getName()'
	 */
	public void testGetName() {
		assertEquals("", para.getName());
		para.setName(name);
		assertEquals(name, para.getName());
		assertEquals(name, paratwo.getName());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Parameter.setValue(String)'
	 */
	public void testSetValue() {
		para.setValue(value);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Parameter.getValue()'
	 */
	public void testGetValue() {
		assertEquals("", para.getValue());
		para.setValue(value);
		assertEquals(value, para.getValue());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Parameter.setExpression(String)'
	 */
	public void testSetExpression() {
		para.setExpression(expression);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Parameter.getExpression()'
	 */
	public void testGetExpression() {
		assertEquals("", para.getExpression());
		para.setExpression(expression);
		assertEquals(expression, para.getExpression());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Parameter.getKey()'
	 */
	public void testGetKey() {
		assertNull(para.getKey());
		assertSame(key, para.getKey());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.Parameter.getSystemBehaviour()'
	 */
	public void testGetSystemBehaviour() {
		assertNull(para.getSystemBehaviour());
	}

}
