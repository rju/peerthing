package de.peerthing.systembehavioureditor.mode.editor;

import de.peerthing.systembehavioureditor.model.ISystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Action;
import de.peerthing.systembehavioureditor.model.editor.CaseSystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Condition;
import de.peerthing.systembehavioureditor.model.editor.Node;
import de.peerthing.systembehavioureditor.model.editor.Parameter;
import de.peerthing.systembehavioureditor.model.editor.State;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviourGEFModelFactory;
import de.peerthing.systembehavioureditor.model.editor.Task;
import de.peerthing.systembehavioureditor.model.editor.Transition;
import de.peerthing.systembehavioureditor.model.editor.Variable;
import junit.framework.TestCase;

public class SystemBehaviourGEFModelFactoryTest extends TestCase {

	SystemBehaviourGEFModelFactory sbGEF;
	protected void setUp() throws Exception {
		super.setUp();
		sbGEF = new SystemBehaviourGEFModelFactory();
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.SystemBehaviourGEFModelFactory.createArchitecture()'
	 */
	public void testCreateArchitecture() {
		ISystemBehaviour sb = new SystemBehaviour();
		assertEquals(sb.getClass(), sbGEF.createArchitecture().getClass());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.SystemBehaviourGEFModelFactory.createState()'
	 */
	public void testCreateState() {
		State state = new State();
		assertEquals(state.getClass(), sbGEF.createState().getClass());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.SystemBehaviourGEFModelFactory.createAction()'
	 */
	public void testCreateAction() {
		Action action = new Action();
		assertEquals(action.getClass(), sbGEF.createAction().getClass());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.SystemBehaviourGEFModelFactory.createCondition()'
	 */
	public void testCreateCondition() {
		Condition cond = new Condition();
		assertEquals(cond.getClass(), sbGEF.createCondition().getClass());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.SystemBehaviourGEFModelFactory.createNode()'
	 */
	public void testCreateNode() {
		Node node = new Node();
		assertEquals(node.getClass(), sbGEF.createNode().getClass());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.SystemBehaviourGEFModelFactory.createTask()'
	 */
	public void testCreateTask() {
		Task task = new Task();
		assertEquals(task.getClass(), sbGEF.createTask().getClass());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.SystemBehaviourGEFModelFactory.createTransition()'
	 */
	public void testCreateTransition() {
		Transition tran = new Transition();
		assertEquals(tran.getClass(), sbGEF.createTransition().getClass());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.SystemBehaviourGEFModelFactory.createVariable()'
	 */
	public void testCreateVariable() {
		Variable var = new Variable();
		assertEquals(var.getClass(), sbGEF.createVariable().getClass());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.SystemBehaviourGEFModelFactory.createParameter()'
	 */
	public void testCreateParameter() {
		Parameter para = new Parameter();
		assertEquals(para.getClass(), sbGEF.createParameter().getClass());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.SystemBehaviourGEFModelFactory.createCaseArchitecture()'
	 */
	public void testCreateCaseArchitecture() {
		CaseSystemBehaviour casesystem = new CaseSystemBehaviour();
		assertEquals(casesystem.getClass(),sbGEF.createCaseArchitecture().getClass());
	}

}
