package de.peerthing.simulation.execution;

import java.util.List;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import de.peerthing.simulation.interfaces.INode;
import de.peerthing.simulation.interfaces.ITask;
import de.peerthing.simulation.interfaces.IXPathContainer;

/**
 * 
 * TestCase for Class Event
 * 
 * @author jojo
 * 
 */
public class EventTest extends MockObjectTestCase {

	Event event;

	String fName = "Test Event";

	Mock locationNode;

	Mock locationTask;

	List<IXPathContainer> parameterList;

	private long time = 0;

	protected void setUp() throws Exception {
		super.setUp();
		locationNode = mock(INode.class);
		locationTask = mock(ITask.class);
		event = new Event(fName, time, (INode) locationNode.proxy(),
				(ITask) locationTask.proxy(), parameterList);
	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.execution.Event.getLocationNode()'
	 */
	public void testGetLocationNode() {
		assertEquals((INode) locationNode.proxy(), event.getLocationNode());
	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.execution.Event.getLocationTask()'
	 */
	public void testGetLocationTask() {
		assertEquals((ITask) locationTask.proxy(), event.getLocationTask());
	}

	/*
	 * Test method for 'de.peerthing.simulation.execution.Event.getName()'
	 */
	public void testGetName() {
		assertEquals(this.fName, event.getName());
	}

	/*
	 * Test method for 'de.peerthing.simulation.execution.Event.getTime()'
	 */
	public void testGetTime() {
		assertEquals(this.time, event.getTime());
	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.execution.Event.getParameterList()'
	 */
	public void testGetParameterList() {
		assertEquals(this.parameterList, event.getParameterList());
	}

}
