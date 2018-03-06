package de.peerthing.scenarioeditor.model.impl;

import junit.framework.TestCase;
import de.peerthing.scenarioeditor.model.INodeConnection;
import de.peerthing.scenarioeditor.model.INodeResource;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IUserBehaviour;

public class NodeCategoryTest extends TestCase {

	NodeCategory nc, nc2;

	private IScenario scen;

	private String name;

	private String node;

	private IUserBehaviour behaviour;

	protected void setUp() throws Exception {
		super.setUp();
		scen = new Scenario();
		nc = new NodeCategory(scen);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.NodeCategory.NodeCategory(IScenario)'
	 */
	public void testNodeCategoryIScenario() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.NodeCategory.NodeCategory(INodeCategory)'
	 */
	public void testNodeCategoryINodeCategory() {
		nc2 = new NodeCategory(nc);
		assertNotSame(nc, nc2);
		assertEquals(nc.getNodeType(), nc2.getNodeType());
		assertEquals(nc.getClass(), nc2.getClass());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.NodeCategory.NodeCategory(INodeCategory,
	 * IScenario)'
	 */
	public void testNodeCategoryINodeCategoryIScenario() {
		scen = new Scenario();
		nc2 = new NodeCategory(nc, scen);
		assertNotSame(nc, nc2);
		assertSame(nc.getNodeType(), nc2.getNodeType());
		assertSame(nc.getClass(), nc2.getClass());
		assertNotSame(nc.getScenario(), nc2.getScenario());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.NodeCategory.setName(String)'
	 */
	public void testSetName() {
		nc.setName(name);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.NodeCategory.getName()'
	 */
	public void testGetName() {
		assertEquals("unknown", nc.getName());
		nc.setName(name);
		assertEquals(name, nc.getName());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.NodeCategory.setNodeType(String)'
	 */
	public void testSetNodeType() {
		nc.setNodeType(node);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.NodeCategory.getNodeType()'
	 */
	public void testGetNodeType() {
		assertEquals("unknown", nc.getNodeType());
		nc.setNodeType(node);
		assertEquals(node, nc.getNodeType());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.NodeCategory.setPrimaryBehaviour(IUserBehaviour)'
	 */
	public void testSetPrimaryBehaviour() {
		nc.setPrimaryBehaviour(behaviour);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.NodeCategory.getPrimaryBehaviour()'
	 */
	public void testGetPrimaryBehaviour() {
		assertNull(nc.getPrimaryBehaviour());
		behaviour = new UserBehaviour();
		nc.setPrimaryBehaviour(behaviour);
		assertSame(behaviour, nc.getPrimaryBehaviour());
		assertNotNull(nc.getPrimaryBehaviour());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.NodeCategory.getConnections()'
	 */
	public void testGetConnections() {
		assertEquals(new ListWithParent<INodeConnection>(this,
				"Node Connections", scen), nc.getConnections());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.NodeCategory.getResources()'
	 */
	public void testGetResources() {
		assertEquals(new ListWithParent<INodeResource>(this, "Node Resources",
				scen), nc.getResources());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.NodeCategory.getBehaviours()'
	 */
	public void testGetBehaviours() {
		assertEquals(new ListWithParent<IUserBehaviour>(this,
				"Node Behaviours", scen), nc.getBehaviours());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.NodeCategory.setScenario(IScenario)'
	 */
	public void testSetScenario() {
		nc.setScenario(scen);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.NodeCategory.getScenario()'
	 */
	public void testGetScenario() {
		assertEquals(scen, nc.getScenario());
		scen = new Scenario();
		assertNotSame(scen, nc.getScenario());
		nc.setScenario(scen);
		assertSame(scen, nc.getScenario());
	}

}
