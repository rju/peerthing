package de.peerthing.simulation.execution;

import junit.framework.TestCase;

/**
 * 
 * TestCase for Class EventQueueComparator
 * 
 * @author jojo
 * 
 */
public class EventQueueComparatorTest extends TestCase {

	Event eventOne;

	Event eventTwo;

	EventQueueComparator eqc;

	int diff;

	int value1 = 10;

	int value2 = 5;

	private String e1Name = "Event One";

	private String e2Name = "Event Two";

	protected void setUp() throws Exception {
		super.setUp();
		eqc = new EventQueueComparator();
		eventOne = new Event(e1Name, value1, null, null, null);
		eventTwo = new Event(e2Name, value2, null, null, null);
		diff = value1 - value2;
	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.execution.EventQueueComparator.compare(IEvent,
	 * IEvent)'
	 */
	public void testCompare() {
		assertEquals(diff, eqc.compare(eventOne, eventTwo));

	}

}
