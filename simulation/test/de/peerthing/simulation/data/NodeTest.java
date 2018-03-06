package de.peerthing.simulation.data;

import junit.framework.JUnit4TestAdapter;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
//import org.jmock.cglib.MockObjectTestCase;
import org.junit.Before;
import org.junit.Test;

import de.peerthing.scenarioeditor.interchange.ISIConnectionCategory;
import de.peerthing.scenarioeditor.interchange.ISIResourceCategory;
import de.peerthing.scenarioeditor.interchange.ISIConnectionCategory.DuplexOption;
import de.peerthing.systembehavioureditor.interchange.*;
import de.peerthing.simulation.interfaces.DataFactory;
import de.peerthing.simulation.interfaces.IResourceDefinition;
import de.peerthing.simulation.interfaces.ISystemTask;
import de.peerthing.simulation.interfaces.ITransmissionLog;

public class NodeTest extends MockObjectTestCase {

	Node node, destNode;

	Mock mockConnectionCategory;

	Mock mockISIResourceCategory;

	Mock mockISystemTask;

	Mock mockIAIState;

	Mock mockIAITask;

	@Before
	public void setUp() {
		mockConnectionCategory = mock(ISIConnectionCategory.class,
				"mockConnectionCategory");
		mockISIResourceCategory = mock(ISIResourceCategory.class);
		mockISystemTask = mock(ISystemTask.class);
		mockIAIState = mock(IAIState.class);
		mockIAITask = mock(IAITask.class);

		// Expectations
		mockConnectionCategory.expects(atLeastOnce()).method("getDuplex")
				.withNoArguments().will(returnValue(DuplexOption.full));

		node = new Node(1, "peer", "test",
				(ISIConnectionCategory) mockConnectionCategory.proxy());

		destNode = new Node(2, "peer", "destTest",
				(ISIConnectionCategory) mockConnectionCategory.proxy());
		// destNode.setLifetime(1000L);

		// TODO Halfduplex testen

		// This method call is needed to satisfy the expectations of
		// mockConnectionCategory
		node.getConnectionCategory().getDuplex();
	}

	@Test
	public void testDownstream() {
		assertTrue(node.isDownstreamAvailable(0L, 1000L));
		node.reserveDownstream(50L, 100L);
		node.reserveDownstream(120L, 200L);
		
		assertTrue(node.isDownstreamAvailable(101L, 119L));
		assertFalse(node.isDownstreamAvailable(100L, 119L));
		assertFalse(node.isDownstreamAvailable(101L, 120L));
		assertTrue(node.isDownstreamAvailable(500L, 1500L));

	}

	@Test
	public void testGetNextFreeDownstreamAvailable() {
		assertTrue(node.isDownstreamAvailable(0L, 1000L));
		node.reserveDownstream(50L, 100L);
		node.reserveDownstream(120L, 200L);

		assertEquals(0L, node.getNextFreeDownstreamTime(0L, 49L));

		assertTrue(
				"Das n?chste Downloadfenster der L?nge 52 sollte bei 201 liegen",
				node.getNextFreeDownstreamTime(130L, 52L) == 201L);
		assertFalse(node.isDownstreamAvailable(130L, 130L + 52L));
	}

	@Test
	public void testUpstream() {
		assertTrue(node.isUpstreamAvailable(0L, 1000L));

//		assertTrue((node.isUpstreamAvailable(0L, 1001L)));

		node.reserveUpstream(100L, 200L);

		assertFalse(node.isUpstreamAvailable(0L, 100L));
		assertFalse(node.isUpstreamAvailable(100L, 200L));
		assertTrue(node.isUpstreamAvailable(201L, 300L));

		node.reserveUpstream(190L, 300L);
		assertFalse(node.isUpstreamAvailable(201L, 300L));
		assertFalse(node.isUpstreamAvailable(189L, 300L));
		assertFalse(node.isUpstreamAvailable(299L, 400L));
		assertTrue(node.isUpstreamAvailable(300L, 1000L));
	}

	@Test
	public void testGetNextFreeUpstreamAvailable() {
		assertTrue(node.isUpstreamAvailable(0L, 1000L));
		node.reserveUpstream(100L, 200L);
		assertEquals(false, node.isUpstreamAvailable(0L, 99L));
		assertEquals(false, node.isUpstreamAvailable(0L, 100L));
		assertEquals(true, node.isUpstreamAvailable(201L, 300L));

	}

	@Test
	public void testAddRemoveResource() {
		// This method call is needed to satisfy the expectations of
		// mockConnectionCategory
		node.getConnectionCategory().getDuplex();

		
		IResourceDefinition rDef = DataFactory.createResourceDefinition(1, 1,
				1, 1, (ISIResourceCategory) mockISIResourceCategory.proxy());
		Resource resource = (Resource) DataFactory.createResource(rDef);
		// make sure the object is not NULL
		assertNotNull(resource);

		
		 assertEquals(1, ((Resource)resource).getId());

		// add resource to list. List should then return an object.
		node.addResource(resource);
		assertNotNull(node.getResource(1));

		// remove resource from list. List should be emtpy afterwards.
		node.removeResource(1);
		assertNull(node.getResource(1));

	}

	@Test
	public void testAddRemoveTask() {

		SystemTask task = (SystemTask) DataFactory.createSystemTask(1,
				(IAIState) mockIAIState.proxy(), (IAITask) mockIAITask.proxy());

		// Tasklist is empty. schould return NULL.
		assertNull(node.getSystemTask(task.getId()));

		// Fill tasklist with one element and make sure the element has been
		// added.
		node.addSystemTask(task);
		assertNotNull(node.getSystemTask(task.getId()));

		// remove task from list. List should be empty afterwards.
		node.removeSystemTask(task);
		assertNull(node.getSystemTask(task.getId()));
	}

	@Test
	public void testSendMessage() {

		SystemTask systemTask = (SystemTask) DataFactory.createSystemTask(1,
				(IAIState) mockIAIState.proxy(), (IAITask) mockIAITask.proxy());
		destNode.addSystemTask(systemTask);

		ITransmissionLog transmission;

		transmission = node.sendMessage("testMessage", 1, 2L, 5, systemTask,
				destNode, null);
		assertNotNull(transmission);

	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(NodeTest.class);
	}
}
