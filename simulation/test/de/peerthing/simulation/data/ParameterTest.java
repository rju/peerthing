package de.peerthing.simulation.data;

import junit.framework.JUnit4TestAdapter;

import org.jmock.MockObjectTestCase;
import org.junit.Before;
import org.junit.Test;

public class ParameterTest extends MockObjectTestCase {

	Parameter param;

	Variable var1, var2, parent;

	@Before
	public void setUp() {
		param = new Parameter("Testparam");
		parent = new Variable("parent");
		var1 = new Variable("var1");
		var1.setParent(parent);
		var2 = new Variable("var2");
	}

	@Test
	public void testAddElement() {

		// make sure elementlist is empty
		assertTrue(param.elementList.size() == 0);

		// var1 should have a parent
		assertNotNull(var1.getParent());

		// insert element var1
		assertTrue(param.addElement(var1));
		// make sure parent is still the same
		assertSame(parent, var1.getParent());
		assertTrue(var1.getParent() instanceof Variable);

		// var2 should have no parent
		assertNull(var2.getParent());
		// insert var2
		assertTrue(param.addElement(var2));
		// superclass should have added a parent
		assertNotNull(var2.getParent());
		assertFalse(var2.getParent() instanceof Variable);
	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(ParameterTest.class);
	}
}
