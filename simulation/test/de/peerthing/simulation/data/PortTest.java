package de.peerthing.simulation.data;

import junit.framework.JUnit4TestAdapter;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.junit.Before;
import org.junit.Test;

import de.peerthing.scenarioeditor.interchange.ISIConnectionCategory;

public class PortTest extends MockObjectTestCase {

	Port port;

	SystemTask task1, task2, task3;

	Mock mockConnectionCategory;

	Node node;

	@Before
	public void setUp() {

		task1 = new SystemTask(1, null, null);
		task2 = new SystemTask(2, null, null);
		task3 = new SystemTask(3, null, null);

		mockConnectionCategory = mock(ISIConnectionCategory.class,
				"mockConnectionCategory");

		node = new Node(1, "peer", "test",
				(ISIConnectionCategory) mockConnectionCategory.proxy());

		port = new Port(node);
	}

	@Test
	public void testHasReference() {
		// make sure references list is empty
		assertTrue(port.references.size() == 0);

		// add tasks
		port.assignTask(task1);
		port.assignTask(task2);

		// test reference
		assertFalse(port.hasReference(task3));
		assertTrue(port.hasReference(task2));
		assertTrue(port.hasReference(task1));
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(PortTest.class);
	}
}
