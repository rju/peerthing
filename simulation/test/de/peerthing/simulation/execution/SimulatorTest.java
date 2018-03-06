package de.peerthing.simulation.execution;

import org.eclipse.core.resources.IFile;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 * 
 * TestCase for Class Simulator
 * 
 * @author jojo
 * 
 */
public class SimulatorTest extends MockObjectTestCase {
	Simulator sim;

	private String arch = "gnutella.arch";

	private String scen = "gnutella.scen";

	Mock mockfile;

	protected void setUp() throws Exception {
		super.setUp();
		sim = new Simulator();
		mockfile = mock(IFile.class);
		sim.initialize(arch, scen, (IFile) mockfile.proxy());
	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.execution.Simulator.initialize(String, String,
	 * IFile)'
	 */
	public final void testInitialize() {

	}

	/*
	 * Test method for 'de.peerthing.simulation.execution.Simulator.run()'
	 */
	public final void testRun() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.execution.Simulator.getSimulationTime()'
	 */
	public final void testGetSimulationTime() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.execution.Simulator.setSimulationEndTime(long)'
	 */
	public final void testSetSimulationEndTime() {

	}

	/*
	 * Test method for 'de.peerthing.simulation.execution.Simulator.step()'
	 */
	public final void testStep() {

	}

	/*
	 * Test method for 'de.peerthing.simulation.execution.Simulator.stop()'
	 */
	public final void testStop() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.execution.Simulator.getSimulationEndTime()'
	 */
	public final void testGetSimulationEndTime() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.execution.Simulator.addSimulationListener(IListener)'
	 */
	public final void testAddSimulationListener() {

	}

	/*
	 * Test method for 'de.peerthing.simulation.execution.Simulator.getLogger()'
	 */
	public final void testGetLogger() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.execution.Simulator.hasProbability(double)'
	 */
	public final void testHasProbability() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.execution.Simulator.getNextMessageId()'
	 */
	public final void testGetNextMessageId() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.execution.Simulator.getNextSessionId()'
	 */
	public final void testGetNextSessionId() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.execution.Simulator.emitEvent(String, long,
	 * INode, ITask, List<IParameter>)'
	 */
	public final void testEmitEvent() {
	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.execution.Simulator.createParameterList()'
	 */
	public final void testCreateParameterList() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.execution.Simulator.createParameter(String,
	 * String)'
	 */
	public final void testCreateParameterStringString() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.execution.Simulator.createParameter(String)'
	 */
	public final void testCreateParameterString() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.execution.Simulator.createParameter(String,
	 * ITransmissionLog)'
	 */
	public final void testCreateParameterStringITransmissionLog() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.execution.Simulator.logNodeConnectionStateChange(INode)'
	 */
	public final void testLogNodeConnectionStateChange() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.execution.Simulator.createVariable(String,
	 * String)'
	 */
	public final void testCreateVariable() {

	}

	/*
	 * Test method for 'de.peerthing.simulation.execution.Simulator.end()'
	 */
	public final void testEnd() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.execution.Simulator.removeEvent(IEvent)'
	 */
	public final void testRemoveEvent() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.execution.Simulator.getAllNodes()'
	 */
	public final void testGetAllNodes() {

	}

}
