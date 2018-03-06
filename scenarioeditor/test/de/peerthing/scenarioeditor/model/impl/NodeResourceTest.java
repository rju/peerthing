package de.peerthing.scenarioeditor.model.impl;

import de.peerthing.scenarioeditor.model.IDistribution;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.IResourceCategory;
import de.peerthing.scenarioeditor.model.IScenario;
import junit.framework.TestCase;

public class NodeResourceTest extends TestCase {
	
	NodeResource nr;
	NodeResource nr2;
	NodeResource nr3;
	IScenario scen;
	INodeCategory ncat;
	private IResourceCategory cat;
	private IDistribution distr;
	protected void setUp() throws Exception {
		scen = new Scenario();
		ncat = new NodeCategory(scen);
		nr = new NodeResource();
//		nr2 = new NodeResource(nr, scen, ncat);
//		nr3 = new NodeResource(nr);
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.NodeResource.NodeResource()'
	 */
	public void testNodeResource() {

	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.NodeResource.NodeResource(INodeResource, IScenario, INodeCategory)'
	 */
	public void testNodeResourceINodeResourceIScenarioINodeCategory() {

	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.NodeResource.NodeResource(INodeResource)'
	 */
	public void testNodeResourceINodeResource() {

	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.NodeResource.setCategory(IResourceCategory)'
	 */
	public void testSetCategory() {
		nr.setCategory(cat);
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.NodeResource.getCategory()'
	 */
	public void testGetCategory() {
		assertNull(nr.getCategory());
		nr.setCategory(cat);
		assertEquals(cat, nr.getCategory());
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.NodeResource.setNumberDistribution(IDistribution)'
	 */
	public void testSetNumberDistribution() {
		nr.setNumberDistribution(distr);
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.NodeResource.getNumberDistribution()'
	 */
	public void testGetNumberDistribution() {
		assertNull(nr.getNumberDistribution());
		nr.setNumberDistribution(distr);
		assertEquals(distr, nr.getNumberDistribution());
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.NodeResource.setNode(INodeCategory)'
	 */
	public void testSetNode() {
		nr.setNode(ncat);
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.NodeResource.getNode()'
	 */
	public void testGetNode() {
		assertNull(nr.getNode());
		nr.setNode(ncat);
		assertEquals(ncat, nr.getNode());
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.NodeResource.getScenario()'
	 */
	public void testGetScenario() {
		nr.setNode(ncat);
		assertEquals(scen, nr.getScenario());
	}

}
