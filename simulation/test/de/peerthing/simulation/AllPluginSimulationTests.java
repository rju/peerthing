package de.peerthing.simulation;


import junit.framework.Test;
import junit.framework.TestSuite;
import de.peerthing.simulation.data.UserStackElementTest;
import de.peerthing.simulation.execution.ExecutionFactoryTest;
import de.peerthing.simulation.execution.SimulatorTest;

public class AllPluginSimulationTests {
	

		public static Test suite() {
			TestSuite suite = new TestSuite("Test for de.peerthing.simulation");
			//$JUnit-BEGIN$
//			suite.addTestSuite(SimulatorTest.class);
			suite.addTestSuite(SimulationTest.class);
			suite.addTestSuite(XPathContainerTest.class);
			suite.addTestSuite(XPathObjectTest.class);
			//$JUnit-END$
			return suite;
		}

	
}
