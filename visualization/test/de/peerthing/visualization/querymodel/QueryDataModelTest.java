package de.peerthing.visualization.querymodel;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;

public class QueryDataModelTest extends TestCase{
	private QueryDataModel qdm;
	private Query query;
	private IFile file;
	private IProject project;
	protected void setUp() throws Exception {
		super.setUp();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		project = workspace.getRoot().getProject("qdmtest");
			
		if (project.exists()) {
			project.delete(true,true,null);
		}
		
		project.create(null);
		project.open(null);

		qdm = new QueryDataModel();
		file = project.getFile("qdmfiletest");
		if (!file.exists()) {
			file.create(null, true, null);
		}
		qdm.setFile(file);
		query = new Query(qdm);
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		project.close(null);
		project.delete(true, null);
	}

	/*
	 * Test method for 'de.peerthing.visualization.querymodel.QueryDataModel.getQueries()'
	 */
	public void testGetQueries() {
		assertNotNull(qdm.getQueries());
		assertEquals(0,qdm.getQueries().size());
		qdm.getQueries().add(query);
		assertEquals(1,qdm.getQueries().size());		
	}

	/*
	 * Test method for 'de.peerthing.visualization.querymodel.QueryDataModel.getFile()'
	 */
	public void testGetFile() {
		assertEquals(file,qdm.getFile());	
		
	}
}
