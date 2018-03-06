package de.peerthing.simulation.execution;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 * 
 * TestCase for Class ExecutionFactory
 * 
 * @author jojo
 * 
 */
public class ExecutionFactoryTest extends MockObjectTestCase {

	private String fName = "TestLog";

	private IFile dbfile;

	Mock mockPlatform;

	IProject project = null;

	protected void setUp() throws Exception {
		super.setUp();
		// TODO zum testen muss hier ein extensionpoint existieren

		project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				"de.peerthing.simulation.logging");
		project.setResourceAttributes(null);
		if (!project.exists()) {
			project.create(null);
			project.open(null);
		}
		Platform.getExtensionRegistry().getExtensionPoint(
				"de.peerthing.simulation.logging");
	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.execution.ExecutionFactory.createLogger(IFile,
	 * String)'
	 */
	public void testCreateLogger() {
		ExecutionFactory.createLogger(dbfile, fName);

	}

}
