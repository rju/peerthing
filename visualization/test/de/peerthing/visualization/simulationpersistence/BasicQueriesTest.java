package de.peerthing.visualization.simulationpersistence;

import java.util.Random;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;

import de.peerthing.visualization.VisualizationPlugin;
import junit.framework.TestCase;

public class BasicQueriesTest extends TestCase {
	HSQLLogger logger;
	DBinterface dbinterface;
	String filename = "logdata";
	IProject project;
	Random random = new Random();
	IFile file;
	
	protected void setUp() throws Exception {
		super.setUp();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		project = workspace.getRoot().getProject("basicquerytest");
			
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
	 * Test method for 'de.peerthing.visualization.simulationpersistence.BasicQueries.getMaxSimulationTime(DBinterface, int)'
	 */
	public final void testGetMaxSimulationTimeDBinterfaceInt() {		
		for (int run = 0; run < 10; run++)  {
			logger.addNodeInformation(run,1,1,1,1,"fakey", "fake");
			int time = 1;
			for (int x=0;x<100;x++) {
				time = time + random.nextInt(10);				
				logger.addMessage(random.nextInt(),random.nextInt(),run,random.nextInt(),random.nextInt(),time,"test",true);
				//System.out.println(time);				
				assertEquals(time,BasicQueries.getMaxSimulationTime(dbinterface, run));
			}
			int maxtime = time;
			for (int x=0;x<100;x++) {
				time = time - random.nextInt(10);
				logger.addMessage(random.nextInt(),random.nextInt(),run,random.nextInt(),random.nextInt(),time,"test",true);
				assertEquals(maxtime,BasicQueries.getMaxSimulationTime(dbinterface, run));
			}
			logger.addMessage(random.nextInt(),random.nextInt(),run,random.nextInt(),random.nextInt(),-maxtime,"test",true);
			assertEquals(maxtime,BasicQueries.getMaxSimulationTime(dbinterface, run));
			logger.addMessage(random.nextInt(),random.nextInt(),run,random.nextInt(),random.nextInt(),-time,"test",true);
			assertEquals(maxtime,BasicQueries.getMaxSimulationTime(dbinterface,run));
			logger.startLog(file);
		}
	}

	/*
	 * Test method for 'de.peerthing.visualization.simulationpersistence.BasicQueries.getMaxSimulationTime(DBinterface)'
	 */
	public final void testGetMaxSimulationTimeDBinterface() {
		int maxtime = 0;
        
		for (int x = 0; x<100;x++) {
			int run = random.nextInt();
			int time = random.nextInt();
			if (time>maxtime) {
				maxtime = time;
			}
			logger.addMessage(1,2,run,1,1,time,"test",true);			
		}
		assertEquals(maxtime,BasicQueries.getMaxSimulationTime(dbinterface));
	}

	/*
	 * Test method for 'de.peerthing.visualization.simulationpersistence.BasicQueries.getNumberOfSimulationRuns(DBinterface)'
	 * Test method for 'de.peerthing.visualization.simulationpersistence.BasicQueries.getIncludedSimulationRuns(DBinterface)'
	 * Test method for 'de.peerthing.visualization.simulationpersistence.BasicQueries.removeSimulationRun(DBinterface, int)'
	 */
	public final void testGetNumberOfIncludedRemoveSimulationRuns() {
		logger.addNodeInformation(1,1,1,1,1,"ladida", "ladiad");
		assertEquals(1,BasicQueries.getNumberOfSimulationRuns(dbinterface));
		int[] i = new int[1];
		i[0]=0;
		assertEquals(i[0], BasicQueries.getIncludedSimulationRuns(dbinterface)[0]);
		
		logger.startLog(file);
		logger.addNodeInformation(1,1,1,1,1,"ladida", "ladiad");
		assertEquals(2,BasicQueries.getNumberOfSimulationRuns(dbinterface));
		i = new int[2];
		i[0]=0;i[1]=1;
		assertEquals(i[0], BasicQueries.getIncludedSimulationRuns(dbinterface)[0]);
		assertEquals(i[1], BasicQueries.getIncludedSimulationRuns(dbinterface)[1]);
		
		logger.startLog(file);
		logger.addNodeInformation(1,1,1,1,1,"ladida", "ladiad");
		assertEquals(3,BasicQueries.getNumberOfSimulationRuns(dbinterface));
		i = new int[3];
		i[0]=0;i[1]=1;i[2]=2;
		assertEquals(i[0], BasicQueries.getIncludedSimulationRuns(dbinterface)[0]);
		assertEquals(i[1], BasicQueries.getIncludedSimulationRuns(dbinterface)[1]);
		assertEquals(i[2], BasicQueries.getIncludedSimulationRuns(dbinterface)[2]);
		
		BasicQueries.removeSimulationRun(dbinterface, 1);
		assertEquals(2,BasicQueries.getNumberOfSimulationRuns(dbinterface));
		i = new int[2];
		i[0]=0;i[1]=2;
		assertEquals(i[0], BasicQueries.getIncludedSimulationRuns(dbinterface)[0]);
		assertEquals(i[1], BasicQueries.getIncludedSimulationRuns(dbinterface)[1]);
	}
}
