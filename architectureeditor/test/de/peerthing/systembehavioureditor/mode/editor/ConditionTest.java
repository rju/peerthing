package de.peerthing.systembehavioureditor.mode.editor;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import de.peerthing.systembehavioureditor.model.ICaseArchitecture;
import de.peerthing.systembehavioureditor.model.IContentContainer;
import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.editor.CaseSystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Condition;
import de.peerthing.systembehavioureditor.model.editor.Node;
import de.peerthing.systembehavioureditor.model.editor.State;
import de.peerthing.systembehavioureditor.model.editor.Task;

public class ConditionTest extends MockObjectTestCase {

	Condition con;
	Condition con2;
	Condition copycon;
	Mock defaultCase;
	private int arg0;
	IState state;
	INodeType node;
	ICaseArchitecture caseArch;
	private String expression;
	private String name;
	private IContentContainer container;
	private int expected;
	private ITask task;
	protected void setUp() throws Exception {
		super.setUp();
		defaultCase = mock(ICaseArchitecture.class);
		con = new Condition();
		caseArch = new CaseSystemBehaviour();
		con2 = new Condition((CaseSystemBehaviour)caseArch);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.Condition.Condition()'
	 */
	public void testCondition() {

	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.Condition.Condition(CaseSystemBehaviour)'
	 */
	public void testConditionCaseSystemBehaviour() {
		
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.Condition.Condition(Condition)'
	 */
	public void testConditionCondition() {
		con2.getDefaultCase().setExpression(expression);
		copycon = new Condition(con2);

	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.Condition.getCases()'
	 */
	public void testGetCases() {
		con.addCase(caseArch);
		assertEquals(caseArch, con.getCase(arg0));
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.Condition.setName(String)'
	 */
	public void testSetName() {
		con.setName(name);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.Condition.getName()'
	 */
	public void testGetName() {
		assertEquals("Condition", con.getName());
		con.setName(name);
		assertEquals(name, con.getName());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.Condition.setDefaultCase(ICaseArchitecture)'
	 */
	public void testSetDefaultCase() {
		con.setDefaultCase((ICaseArchitecture)defaultCase.proxy());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.Condition.getDefaultCase()'
	 */
	public void testGetDefaultCase() {
		con.setDefaultCase((ICaseArchitecture)defaultCase.proxy());
		assertEquals((ICaseArchitecture)defaultCase.proxy(), con.getDefaultCase());
		assertEquals(((CaseSystemBehaviour)caseArch), con2.getDefaultCase());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.Condition.setContainer(IContentContainer)'
	 */
	public void testSetContainer() {
		con.setContainer(container);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.Condition.getContainer()'
	 */
	public void testGetContainer() {
		assertNull(con.getContainer());
		con.setContainer(container);
		assertSame(container, con.getContainer());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.Condition.addCase(ICaseArchitecture)'
	 */
	public void testAddCase() {
		assertEquals(expected,con.addCase(caseArch));
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.Condition.getCase(int)'
	 */
	public void testGetCase() {
		expected = con.addCase(caseArch);
		assertEquals(caseArch, con.getCase(expected));
		
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.Condition.removeCase(ICaseArchitecture)'
	 */
	public void testRemoveCase() {
		expected = con.addCase(caseArch);
		assertEquals(caseArch, con.getCase(expected));
		con.removeCase(caseArch);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.Condition.getSystemBehaviour()'
	 */
	public void testGetSystemBehaviour() {
		state = new State();
		task = new Task();
		node = new Node();
		task.setNode(node);
		state.setTask(task);
		con.setContainer(state);
		assertEquals(state.getSystemBehaviour(), con.getSystemBehaviour());
	}

}
