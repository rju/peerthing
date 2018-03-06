package de.peerthing.systembehavioureditor;
import de.peerthing.systembehavioureditor.mode.editor.ActionTest;
import de.peerthing.systembehavioureditor.mode.editor.CaseSystemBehaviourTest;
import de.peerthing.systembehavioureditor.mode.editor.ConditionTest;
import de.peerthing.systembehavioureditor.mode.editor.ParameterTest;
import de.peerthing.systembehavioureditor.mode.editor.SystemBehaviourGEFModelFactoryTest;
import de.peerthing.systembehavioureditor.mode.editor.SystemBehaviourModelFactoryTest;
import de.peerthing.systembehavioureditor.mode.editor.VariableTest;
import de.peerthing.systembehavioureditor.model.simulation.NodeTest;
import de.peerthing.systembehavioureditor.model.simulation.StateTest;
import de.peerthing.systembehavioureditor.model.simulation.SystemBehaviourTest;
import de.peerthing.systembehavioureditor.model.simulation.TaskTest;
import de.peerthing.systembehavioureditor.model.simulation.TransitionTest;
import junit.framework.*;

public class AllSystembehaviourTests {
  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTestSuite(ActionTest.class);
    suite.addTestSuite(CaseSystemBehaviourTest.class);
    suite.addTestSuite(ConditionTest.class);
    suite.addTestSuite(ParameterTest.class);
    suite.addTestSuite(SystemBehaviourGEFModelFactoryTest.class);
    suite.addTestSuite(SystemBehaviourModelFactoryTest.class);
    suite.addTestSuite(VariableTest.class);
    suite.addTestSuite(de.peerthing.systembehavioureditor.model.simulation.ActionTest.class);
    suite.addTestSuite(de.peerthing.systembehavioureditor.model.simulation.CaseSystemBehaviourTest.class);
    suite.addTestSuite(de.peerthing.systembehavioureditor.model.simulation.ConditionTest.class);
    suite.addTestSuite(NodeTest.class);
    suite.addTestSuite(de.peerthing.systembehavioureditor.model.simulation.ParameterTest.class);
    suite.addTestSuite(StateTest.class);
    suite.addTestSuite(SystemBehaviourTest.class);
    suite.addTestSuite(TaskTest.class);
    suite.addTestSuite(TransitionTest.class);
    suite.addTestSuite(de.peerthing.systembehavioureditor.model.simulation.VariableTest.class);
    return suite;
  }
}