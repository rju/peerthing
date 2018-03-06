package de.peerthing.simulation.execution;

import org.junit.Before;

import junit.framework.TestCase;

/**
 * 
 * TestCase for Class Counter
 * 
 * @author jojo
 * 
 */
public class CounterTest extends TestCase {

	Counter counter;

	@Before
	public void setUp() {
		counter = new Counter();
	}

	/*
	 * Test method for 'de.peerthing.simulation.execution.Counter.next()'
	 */
	public void testNext() {
		int oldcounter = counter.next();
		assertNotSame(oldcounter, counter.next());
		assertSame(counter.next(), counter.next() - 1);
	}

}
