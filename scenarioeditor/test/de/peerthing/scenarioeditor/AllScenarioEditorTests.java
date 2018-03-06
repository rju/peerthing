package de.peerthing.scenarioeditor;

import de.peerthing.scenarioeditor.model.impl.CallUserBehaviourTest;
import de.peerthing.scenarioeditor.model.impl.CaseTest;
import de.peerthing.scenarioeditor.model.impl.ConnectionCategoryTest;
import de.peerthing.scenarioeditor.model.impl.DefaultCaseTest;
import de.peerthing.scenarioeditor.model.impl.DelayTest;
import de.peerthing.scenarioeditor.model.impl.DistributionTest;
import de.peerthing.scenarioeditor.model.impl.ExecuteAdditionTest;
import de.peerthing.scenarioeditor.model.impl.LinkSpeedTest;
import de.peerthing.scenarioeditor.model.impl.ListOfUndosTest;
import de.peerthing.scenarioeditor.model.impl.ListWithParentTest;
import de.peerthing.scenarioeditor.model.impl.ListenTest;
import de.peerthing.scenarioeditor.model.impl.LoopTest;
import de.peerthing.scenarioeditor.model.impl.NameTestTest;
import de.peerthing.scenarioeditor.model.impl.NodeCategoryTest;
import de.peerthing.scenarioeditor.model.impl.NodeConnectionTest;
import de.peerthing.scenarioeditor.model.impl.NodeResourceTest;
import de.peerthing.scenarioeditor.model.impl.PasteCallBehaviourTestTest;
import de.peerthing.scenarioeditor.model.impl.ProvideUserBehaviourTest;
import de.peerthing.scenarioeditor.model.impl.ProvidesPositionOfCommandTest;
import de.peerthing.scenarioeditor.model.impl.ResourceCategoryTest;
import de.peerthing.scenarioeditor.model.impl.ScenarioConditionTest;
import de.peerthing.scenarioeditor.model.impl.ScenarioTest;
import de.peerthing.scenarioeditor.model.impl.ScenarioUndoTest;
import de.peerthing.scenarioeditor.model.impl.UserActionTest;
import de.peerthing.scenarioeditor.model.impl.UserBehaviourTest;
import junit.framework.*;
public class AllScenarioEditorTests {

	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(NameTestTest.class);
		suite.addTestSuite(ExecuteAdditionTest.class);
		suite.addTestSuite(NodeCategoryTest.class);
		suite.addTestSuite(ScenarioUndoTest.class);
		suite.addTestSuite(LoopTest.class);
		suite.addTestSuite(ScenarioTest.class);
		suite.addTestSuite(CallUserBehaviourTest.class);
		suite.addTestSuite(DelayTest.class);
		suite.addTestSuite(ResourceCategoryTest.class);
		suite.addTestSuite(ProvidesPositionOfCommandTest.class);
		suite.addTestSuite(ListWithParentTest.class);
		suite.addTestSuite(DistributionTest.class);
		suite.addTestSuite(CaseTest.class);
		suite.addTestSuite(UserBehaviourTest.class);
		suite.addTestSuite(ProvideUserBehaviourTest.class);
		suite.addTestSuite(NodeConnectionTest.class);
		suite.addTestSuite(ListOfUndosTest.class);
		suite.addTestSuite(PasteCallBehaviourTestTest.class);
		suite.addTestSuite(ScenarioConditionTest.class);
		suite.addTestSuite(UserActionTest.class);
		suite.addTestSuite(ListenTest.class);
		suite.addTestSuite(NodeResourceTest.class);
		suite.addTestSuite(DefaultCaseTest.class);
		suite.addTestSuite(ConnectionCategoryTest.class);
//		suite.addTestSuite(UndoOperationValuesTest.class);
		suite.addTestSuite(LinkSpeedTest.class);
		return suite;
	}

}
