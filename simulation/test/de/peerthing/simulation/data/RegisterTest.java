package de.peerthing.simulation.data;

import org.junit.Before;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;

public class RegisterTest extends TestCase {

	Register register;

	TransmissionLog tlog1;

	TransmissionLog tlog2;

	TransmissionLog tlog3;

	Message msg1;

	Message msg2;

	Message msg3;

	@Before
	public void setUp() {

		// creates dummy messages
		msg1 = new Message("msg1", 0, 0L, null, null, null);
		msg2 = new Message("msg2", 0, 0L, null, null, null);
		msg3 = new Message("msg3", 0, 0L, null, null, null);

		tlog1 = new TransmissionLog(0, msg1);
		tlog2 = new TransmissionLog(0, msg2);
		tlog3 = new TransmissionLog(0, msg3);

		register = new Register();
	}

	@Test
	public void testAddRemoveTransmissionsByLog() {
		assertNull(register.getTransmission(tlog1));

		register.addTransmission(tlog1);
		assertNotNull(register.getTransmission(tlog1));
		assertNull(register.getTransmission(tlog2));

		register.addTransmission(tlog2);
		register.addTransmission(tlog3);

		assertEquals(tlog1, register.getTransmission(tlog1));
		assertEquals(tlog1.getMessage().getName(), register.getTransmission(
				tlog1).getMessage().getName());
		assertNotSame(tlog3, register.getTransmission(tlog2));

		register.removeTransmission(tlog2);
		assertNull(register.getTransmission(tlog2));
	}

	@Test
	public void testGetRemoveTransmissionsByMessage() {
		assertNull(register.getTransmission(msg1));

		register.addTransmission(tlog1);
		assertNotNull(register.getTransmission(msg1));
		assertNull(register.getTransmission(msg2));

		register.addTransmission(tlog2);
		register.addTransmission(tlog3);

		assertEquals(tlog1, register.getTransmission(msg1));
		assertEquals(tlog1.getMessage().getName(), register.getTransmission(
				msg1).getMessage().getName());
		assertNotSame(tlog3, register.getTransmission(msg2));

		register.removeTransmission(msg2);
		assertNull(register.getTransmission(msg2));

		register.removeTransmission(msg1);
		register.removeTransmission(msg3);

		assertNull(register.getTransmission(msg1));

	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(RegisterTest.class);
	}
}
