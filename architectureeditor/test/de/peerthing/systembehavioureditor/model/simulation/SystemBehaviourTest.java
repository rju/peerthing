package de.peerthing.systembehavioureditor.model.simulation;

import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import de.peerthing.systembehavioureditor.model.INodeType;

public class SystemBehaviourTest extends MockObjectTestCase {

	SystemBehaviour sb;
	private String name;
	private State startstate;
	Mock mockfile;
	protected void setUp() throws Exception {
		super.setUp();
		sb = new SystemBehaviour();
		mockfile = mock(IFile.class);
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.SystemBehaviour.SystemBehaviour()'
	 */
	public void testSystemBehaviour() {
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.SystemBehaviour.getNodes()'
	 */
	public void testGetNodes() {
		assertEquals(new Vector<INodeType>(),sb.getNodes());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.SystemBehaviour.getName()'
	 */
	public void testGetName() {
		assertEquals("name", sb.getName());
		sb.setName(name);
		assertSame(name,sb.getName());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.SystemBehaviour.setName(String)'
	 */
	public void testSetName() {
		sb.setName(name);
		assertSame(name, sb.getName());
		sb.setName(null);
		assertNull(sb.getName());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.SystemBehaviour.getStartState()'
	 */
	public void testGetStartState() {
		assertNull(sb.getStartState());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.SystemBehaviour.setStartState(State)'
	 */
	public void testSetStartStateState() {
		sb.setStartState(startstate);
		assertSame(startstate, sb.getStartState());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.SystemBehaviour.setStartState(IState)'
	 */
	public void testSetStartStateIState() {
		/**
		 * not implemented
		 */
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.SystemBehaviour.setFile(IFile)'
	 */
	public void testSetFile() {
		/**
		 * not implemented
		 */
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.SystemBehaviour.getFile()'
	 */
	public void testGetFile() {
		assertNull(sb.getFile());
		sb.setFile((IFile)mockfile.proxy());
		assertNull(sb.getFile());		
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.model.simulation.SystemBehaviour.getSystemBehaviour()'
	 */
	public void testGetSystemBehaviour() {
		assertNull(sb.getSystemBehaviour());
	}

}
