package de.peerthing.scenarioeditor.model.impl;

import de.peerthing.scenarioeditor.model.IConnectionCategory;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.IScenario;
import junit.framework.TestCase;

public class NodeConnectionTest extends TestCase {

	NodeConnection nc;
	private IConnectionCategory category;
	private IScenario scen;
	private int number;
	private INodeCategory nodecategory;
	protected void setUp() throws Exception {
		super.setUp();
		scen = new Scenario();
		nc = new NodeConnection();
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.NodeConnection.NodeConnection()'
	 */
	public void testNodeConnection() {

	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.NodeConnection.NodeConnection(INodeConnection, IScenario, INodeCategory)'
	 */
	public void testNodeConnectionINodeConnectionIScenarioINodeCategory() {

	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.NodeConnection.NodeConnection(int)'
	 */
	public void testNodeConnectionInt() {

	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.NodeConnection.NodeConnection(INodeConnection)'
	 */
	public void testNodeConnectionINodeConnection() {

	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.NodeConnection.setCategory(IConnectionCategory)'
	 */
	public void testSetCategory() {
		nc.setCategory(category);
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.NodeConnection.getCategory()'
	 */
	public void testGetCategory() {
		assertNull(nc.getCategory());
		category = new ConnectionCategory(scen);
		nc.setCategory(category);
		assertEquals(category, nc.getCategory());
		assertNotNull(nc.getCategory());
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.NodeConnection.setNumberOfNodes(int)'
	 */
	public void testSetNumberOfNodes() {
		nc.setNumberOfNodes(number);
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.NodeConnection.getNumberOfNodes()'
	 */
	public void testGetNumberOfNodes() {
		assertEquals(0, nc.getNumberOfNodes());
		nc.setNumberOfNodes(number);
		assertEquals(number, nc.getNumberOfNodes());
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.NodeConnection.setNode(INodeCategory)'
	 */
	public void testSetNode() {
		nc.setNode(nodecategory);
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.NodeConnection.getNode()'
	 */
	public void testGetNode() {
		assertNull(nc.getNode());
		nodecategory = new NodeCategory(scen);
		nc.setNode(nodecategory);
		assertNotNull(nc.getNode());
		assertEquals(nodecategory, nc.getNode());
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.NodeConnection.getScenario()'
	 */
	public void testGetScenario() {
		scen = new Scenario();
		nodecategory = new NodeCategory(scen);
		nc.setNode(nodecategory);
		assertNotNull(nc.getScenario());
		assertEquals(scen, nc.getScenario());
	}

}
