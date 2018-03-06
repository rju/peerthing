package de.peerthing.visualization;

import de.peerthing.visualization.querymodel.QueryDataModelTest;
import de.peerthing.visualization.querymodel.QueryTest;
import de.peerthing.visualization.querymodel.VisualizationDataTest;
import de.peerthing.visualization.querypersistence.QueryPersistenceTest;
import de.peerthing.visualization.simulationpersistence.BasicQueriesTest;
import de.peerthing.visualization.simulationpersistence.DBToolsTest;
import de.peerthing.visualization.simulationpersistence.DBinterfaceTest;
import de.peerthing.visualization.simulationpersistence.HSQLLoggerTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for Visualization");
		//$JUnit-BEGIN$
		suite.addTestSuite(DBToolsTest.class);
		suite.addTestSuite(QueryDataModelTest.class);
		suite.addTestSuite(DBinterfaceTest.class);
		suite.addTestSuite(QueryPersistenceTest.class);
		suite.addTestSuite(QueryDataModelTest.class);
		suite.addTestSuite(VisualizationDataTest.class);
		suite.addTestSuite(QueryTest.class);
		suite.addTestSuite(HSQLLoggerTest.class);
		suite.addTestSuite(BasicQueriesTest.class);
		//$JUnit-END$
		return suite;
	}

}
