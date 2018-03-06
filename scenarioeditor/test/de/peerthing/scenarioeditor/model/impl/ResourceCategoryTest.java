package de.peerthing.scenarioeditor.model.impl;

import de.peerthing.scenarioeditor.model.IDistribution;
import de.peerthing.scenarioeditor.model.IScenario;
import junit.framework.TestCase;

public class ResourceCategoryTest extends TestCase {

	ResourceCategory rc;
	IScenario scen;
	private String name;
	private int pop;
	private IDistribution size;
	protected void setUp() throws Exception {
		super.setUp();
		scen = new Scenario();
		rc = new ResourceCategory(scen);
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.ResourceCategory.ResourceCategory(IScenario)'
	 */
	public void testResourceCategoryIScenario() {

	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.ResourceCategory.ResourceCategory(IResourceCategory, IScenario)'
	 */
	public void testResourceCategoryIResourceCategoryIScenario() {

	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.ResourceCategory.ResourceCategory(IResourceCategory)'
	 */
	public void testResourceCategoryIResourceCategory() {

	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.ResourceCategory.setName(String)'
	 */
	public void testSetName() {
		rc.setName(name);
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.ResourceCategory.getName()'
	 */
	public void testGetName() {
		assertEquals("NewCategory", rc.getName());
		rc.setName(name);
		assertEquals(name, rc.getName());
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.ResourceCategory.setPopularity(int)'
	 */
	public void testSetPopularity() {
		rc.setPopularity(pop);
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.ResourceCategory.getPopularity()'
	 */
	public void testGetPopularity() {
		assertEquals(pop,rc.getPopularity());
		pop = 10;
		assertNotSame(pop,rc.getPopularity());
		rc.setPopularity(pop);
		assertEquals(pop, rc.getPopularity());
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.ResourceCategory.setDiversity(int)'
	 */
	public void testSetDiversity() {
		rc.setDiversity(pop);
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.ResourceCategory.getDiversity()'
	 */
	public void testGetDiversity() {
		assertEquals(pop,rc.getDiversity());
		pop = 10;
		assertNotSame(pop,rc.getDiversity());
		rc.setDiversity(pop);
		assertEquals(pop, rc.getDiversity());
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.ResourceCategory.setSize(IDistribution)'
	 */
	public void testSetSize() {
		rc.setSize(size);
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.ResourceCategory.getSize()'
	 */
	public void testGetSize() {
//		assertNull(rc.getSize());
//		assertEquals(size, rc.getSize());
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.ResourceCategory.setScenario(IScenario)'
	 */
	public void testSetScenario() {
		rc.setScenario(scen);
	}

	/*
	 * Test method for 'de.peerthing.scenarioeditor.model.impl.ResourceCategory.getScenario()'
	 */
	public void testGetScenario() {
		assertEquals(scen, rc.getScenario());
		scen = new Scenario();
		assertNotSame(scen, rc.getScenario());
		rc.setScenario(scen);
		assertEquals(scen, rc.getScenario());
	}

}
