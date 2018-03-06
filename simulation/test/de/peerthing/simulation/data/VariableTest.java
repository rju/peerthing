package de.peerthing.simulation.data;

import org.jmock.MockObjectTestCase;
import org.junit.Before;
import org.junit.Test;

public class VariableTest extends MockObjectTestCase {

	Variable v1, v2;

	@Before
	public void setUp() {
		v1 = new Variable("v1", "7");
	}

	@Test
	public void testDuplicate() {

	}
}
