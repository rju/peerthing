package de.peerthing.simulation.data;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.junit.Before;
import org.junit.Test;

import de.peerthing.scenarioeditor.interchange.ISIConnectionCategory;

public class SystemTaskTest extends MockObjectTestCase {

	SystemTask systemTask;

	Message msg1, msg2;

	Mock mockIPort, mockConnectionCategory;

	Node node;

	Port port;

	@Before
	public void setUp() {

		mockConnectionCategory = mock(ISIConnectionCategory.class,
				"mockConnectionCategory");

		node = new Node(1, "peer", "test",
				(ISIConnectionCategory) mockConnectionCategory.proxy());
		port = new Port(node);
		systemTask = new SystemTask(1, null, null);

		msg1 = new Message("msg1", 1, 1L, port, port, null);
		msg2 = new Message("msg2", 2, 2L, port, port, null);

	}

	@Test
	public void testSetMessage() {
		// Elementlist should be empty
		assertTrue(systemTask.elementList.size() == 0);

		// add one message; elementlist should increment by 1;
		systemTask.setMessage(msg2);
		assertTrue(systemTask.elementList.size() == 1);

		// add an other message; the first one should be deleted so elementlist
		// still contains
		// one element
		systemTask.setMessage(msg1);
		assertTrue(systemTask.elementList.size() == 1);

	}
}
