package de.peerthing.scenarioeditor.model.impl;

import junit.framework.TestCase;
import de.peerthing.scenarioeditor.model.ILinkSpeed;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IConnectionCategory.DuplexOption;

public class ConnectionCategoryTest extends TestCase {

	ConnectionCategory con;

	IScenario scen;

	private String name;

	private DuplexOption duplex;

	private ILinkSpeed speed;

	protected void setUp() throws Exception {
		super.setUp();
		scen = new Scenario();
		con = new ConnectionCategory(scen);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.ConnectionCategory.ConnectionCategory(IScenario)'
	 */
	public void testConnectionCategoryIScenario() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.ConnectionCategory.ConnectionCategory(IConnectionCategory)'
	 */
	public void testConnectionCategoryIConnectionCategory() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.ConnectionCategory.ConnectionCategory(IConnectionCategory,
	 * IScenario)'
	 */
	public void testConnectionCategoryIConnectionCategoryIScenario() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.ConnectionCategory.setName(String)'
	 */
	public void testSetName() {
		con.setName(name);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.ConnectionCategory.getName()'
	 */
	public void testGetName() {
		assertEquals("unknown", con.getName());
		con.setName(name);
		assertEquals(name, con.getName());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.ConnectionCategory.setDuplex(DuplexOption)'
	 */
	public void testSetDuplex() {
		con.setDuplex(duplex);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.ConnectionCategory.getDuplex()'
	 */
	public void testGetDuplex() {
		assertEquals(DuplexOption.full, con.getDuplex());
		con.setDuplex(duplex);
		assertEquals(duplex, con.getDuplex());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.ConnectionCategory.setUplinkSpeed(ILinkSpeed)'
	 */
	public void testSetUplinkSpeed() {
		con.setUplinkSpeed(speed);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.ConnectionCategory.getUplinkSpeed()'
	 */
	public void testGetUplinkSpeed() {
		assertNull(con.getUplinkSpeed());
		con.setUplinkSpeed(speed);
		assertEquals(speed, con.getUplinkSpeed());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.ConnectionCategory.setDownlinkSpeed(ILinkSpeed)'
	 */
	public void testSetDownlinkSpeed() {
		con.setDownlinkSpeed(speed);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.ConnectionCategory.getDownlinkSpeed()'
	 */
	public void testGetDownlinkSpeed() {
		assertNull(con.getDownlinkSpeed());
		con.setDownlinkSpeed(speed);
		assertEquals(speed, con.getDownlinkSpeed());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.ConnectionCategory.setScenario(IScenario)'
	 */
	public void testSetScenario() {
		con.setScenario(scen);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.ConnectionCategory.getScenario()'
	 */
	public void testGetScenario() {
		assertEquals(scen, con.getScenario());
		scen = new Scenario();
		assertNotSame(scen, con.getScenario());
		con.setScenario(scen);
		assertEquals(scen, con.getScenario());
	}

}
