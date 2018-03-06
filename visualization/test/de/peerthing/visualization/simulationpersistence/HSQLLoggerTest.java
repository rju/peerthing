package de.peerthing.visualization.simulationpersistence;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;

import de.peerthing.visualization.VisualizationPlugin;

import junit.framework.TestCase;

public class HSQLLoggerTest extends TestCase {
	HSQLLogger logger;
	DBinterface dbinterface;
	String filename = "logdata";
	IProject project;
	IFile file;
	protected void setUp() throws Exception {
		super.setUp(); 
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		project = workspace.getRoot().getProject("dbitest");
			
		if (!project.exists()) {
			project.delete(true,true,null);
		}
		
		project.create(null);
		project.open(null);
		
        
		file = project.getFile(filename);        
        if (file.exists()){
        	file.delete(true, false, null);
        }
        //file.create(null,true,null);
        dbinterface = VisualizationPlugin.getDefault().getDB(file);
        DBTools.createTables(dbinterface);
		logger = new HSQLLogger();
		logger.startLog(file);
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		VisualizationPlugin.getDefault().removeDB(dbinterface);
		dbinterface.shutdown();
		logger.endLog();
		project.delete(true,true,null);
	}

	/*
	 * Test method for 'de.peerthing.visualization.simulationpersistence.HSQLLogger.addMessage(int, int, int, int, long, long, String, boolean)'
	 */
	public void testAddMessage() {
		logger.addMessage(1,1,1,1,1,1,"test",true);		
	}

	/*
	 * Test method for 'de.peerthing.visualization.simulationpersistence.HSQLLogger.addSessionInformation(long, long, long)'
	 */
	public void testAddSessionInformation() {
		logger.addSessionInformation(1L,1L,2L);
	}

	/*
	 * Test method for 'de.peerthing.visualization.simulationpersistence.HSQLLogger.changeSessionEndTime(long, long)'
	 */
	public void testChangeSessionEndTime() {
		logger.changeSessionEndTime(1L,100L);
	}

	/*
	 * Test method for 'de.peerthing.visualization.simulationpersistence.HSQLLogger.addResource(int, int)'
	 */
	public void testAddResource() {
		logger.addResource(Integer.MAX_VALUE, Integer.MAX_VALUE);
		logger.addResource(Integer.MIN_VALUE, Integer.MIN_VALUE);
		
	}

	/*
	 * Test method for 'de.peerthing.visualization.simulationpersistence.HSQLLogger.addResourceChange(int, int, double, long, int)'
	 */
	public void testAddResourceChange() {
		logger.addResourceChange(1,1,.5,1,1);
	}

	/*
	 * Test method for 'de.peerthing.visualization.simulationpersistence.HSQLLogger.addNodeStateChange(int, long, int)'
	 */
	public void testAddNodeStateChange() {
		logger.addNodeStateChange(1,1,1);
	}

	/*
	 * Test method for 'de.peerthing.visualization.simulationpersistence.HSQLLogger.addNodeInformation(int, int, int, int, int, String, String)'
	 */
	public void testAddNodeInformation() {
		logger.addNodeInformation(1,1,1,1,1,"test","test2");
	}

	/*
	 * Test method for 'de.peerthing.visualization.simulationpersistence.HSQLLogger.debug(String)'
	 */
	public void testDebug() {
		/*
		 * no test needed
		 */
	}

	/*
	 * Test method for 'de.peerthing.visualization.simulationpersistence.HSQLLogger.startLog(IFile)'
	 */
	public void testStartLog() {
		/*
		 * implicit testet in setup
		 */
	}

	/*
	 * Test method for 'de.peerthing.visualization.simulationpersistence.HSQLLogger.endLog()'
	 */
	public void testEndLog() {
		logger.endLog();
		logger.addMessage(1,1,1,1,1,1,"11",true);
	}

	/*
	 * Test method for 'de.peerthing.visualization.simulationpersistence.HSQLLogger.addUserLogInformation(int, long, String, String)'
	 */
	public void testAddUserLogInformation() {
		logger.addUserLogInformation(1,1,"test","test");
	}

	/*
	 * Test method for 'de.peerthing.visualization.simulationpersistence.HSQLLogger.discardLog()'
	 */
	public void testDiscardLog() {
		logger.addNodeInformation(1, 1, 1, 1, 1, "1", "1");
		assertEquals(1,BasicQueries.getNumberOfSimulationRuns(dbinterface));
		logger.startLog(file);
		logger.addNodeInformation(1, 1, 1, 1, 1, "1", "1");
		assertEquals(2,BasicQueries.getNumberOfSimulationRuns(dbinterface));
		logger.discardLog();
		assertEquals(1,BasicQueries.getNumberOfSimulationRuns(dbinterface));
	}

}
