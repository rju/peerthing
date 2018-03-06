package de.peerthing.simulation;

import de.peerthing.simulation.data.MessageTest;
import de.peerthing.simulation.data.NodeTest;
import de.peerthing.simulation.data.ParameterTest;
import de.peerthing.simulation.data.PortTest;
import de.peerthing.simulation.data.RegisterTest;
import de.peerthing.simulation.data.ResourceTest;
import de.peerthing.simulation.data.SystemTaskTest;
import de.peerthing.simulation.data.UserStackElementTest;
import de.peerthing.simulation.data.UserTaskTest;
import de.peerthing.simulation.data.VariableTest;
import de.peerthing.simulation.execution.CounterTest;
import de.peerthing.simulation.execution.EventQueueComparatorTest;
import de.peerthing.simulation.execution.EventTest;
import de.peerthing.simulation.execution.ExecutionFactoryTest;
import de.peerthing.simulation.execution.SimulatorTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllSimulationTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for de.peerthing.simulation");
		//$JUnit-BEGIN$
		suite.addTestSuite(EventTest.class);
		suite.addTestSuite(EventQueueComparatorTest.class);
		suite.addTestSuite(CounterTest.class);
		suite.addTestSuite(UserStackElementTest.class);
		suite.addTest(ResourceTest.suite());
		suite.addTest(ParameterTest.suite());
		suite.addTest(MessageTest.suite());
		suite.addTestSuite(VariableTest.class);
		suite.addTestSuite(SystemTaskTest.class);
		suite.addTest(PortTest.suite());
		suite.addTestSuite(UserTaskTest.class);
		suite.addTest(RegisterTest.suite());
		suite.addTest(NodeTest.suite());
		//$JUnit-END$
		return suite;
	}

}
