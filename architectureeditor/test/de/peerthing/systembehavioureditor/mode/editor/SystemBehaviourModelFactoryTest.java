package de.peerthing.systembehavioureditor.mode.editor;

import de.peerthing.systembehavioureditor.model.ISystemBehaviour;
import de.peerthing.systembehavioureditor.model.SystemBehaviourModelFactory;
import de.peerthing.systembehavioureditor.model.simulation.Action;
import de.peerthing.systembehavioureditor.model.simulation.CaseSystemBehaviour;
import de.peerthing.systembehavioureditor.model.simulation.Condition;
import de.peerthing.systembehavioureditor.model.simulation.Node;
import de.peerthing.systembehavioureditor.model.simulation.Parameter;
import de.peerthing.systembehavioureditor.model.simulation.State;
import de.peerthing.systembehavioureditor.model.simulation.SystemBehaviour;
import de.peerthing.systembehavioureditor.model.simulation.Task;
import de.peerthing.systembehavioureditor.model.simulation.Transition;
import de.peerthing.systembehavioureditor.model.simulation.Variable;
import junit.framework.TestCase;

public class SystemBehaviourModelFactoryTest extends TestCase {

	SystemBehaviourModelFactory sbmf;
	protected void setUp() throws Exception {
		super.setUp();
		sbmf = new SystemBehaviourModelFactory();
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.SystemBehaviourModelFactory.createArchitecture()'
	 */
	public void testCreateArchitecture() {
		ISystemBehaviour sb = new SystemBehaviour();
		assertEquals(sb.getClass(), sbmf.createArchitecture().getClass());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.SystemBehaviourModelFactory.createState()'
	 */
	public void testCreateState() {
		State state = new State();
		assertEquals(state.getClass(), sbmf.createState().getClass());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.SystemBehaviourModelFactory.createAction()'
	 */
	public void testCreateAction() {
		Action action = new Action();
		assertEquals(action.getClass(), sbmf.createAction().getClass());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.SystemBehaviourModelFactory.createCondition()'
	 */
	public void testCreateCondition() {
		Condition cond = new Condition();
		assertEquals(cond.getClass(), sbmf.createCondition().getClass());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.SystemBehaviourModelFactory.createNode()'
	 */
	public void testCreateNode() {
		Node node = new Node();
		assertEquals(node.getClass(), sbmf.createNode().getClass());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.SystemBehaviourModelFactory.createTask()'
	 */
	public void testCreateTask() {
		Task task = new Task();
		assertEquals(task.getClass(), sbmf.createTask().getClass());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.SystemBehaviourModelFactory.createTransition()'
	 */
	public void testCreateTransition() {
		Transition tran = new Transition();
		assertEquals(tran.getClass(), sbmf.createTransition().getClass());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.SystemBehaviourModelFactory.createVariable()'
	 */
	public void testCreateVariable() {
		Variable var = new Variable();
		assertEquals(var.getClass(), sbmf.createVariable().getClass());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.SystemBehaviourModelFactory.createParameter()'
	 */
	public void testCreateParameter() {
		Parameter para = new Parameter();
		assertEquals(para.getClass(), sbmf.createParameter().getClass());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.editor.SystemBehaviourModelFactory.createCaseArchitecture()'
	 */
	public void testCreateCaseArchitecture() {
		CaseSystemBehaviour casesystem = new CaseSystemBehaviour();
		assertEquals(casesystem.getClass(),sbmf.createCaseArchitecture().getClass());
	}

}
