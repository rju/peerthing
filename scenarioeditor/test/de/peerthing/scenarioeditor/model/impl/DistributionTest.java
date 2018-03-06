package de.peerthing.scenarioeditor.model.impl;

import junit.framework.TestCase;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IDistribution.DistributionType;

public class DistributionTest extends TestCase {

	Distribution dist;

	Distribution dist2;

	private IScenario scen;

	private DistributionType type;

	private double min;

	private double max;

	private double mean;

	private double var;

	protected void setUp() throws Exception {
		super.setUp();
		scen = new Scenario();
		dist = new Distribution(scen);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Distribution.Distribution(IScenario)'
	 */
	public void testDistributionIScenario() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Distribution.Distribution(IDistribution,
	 * IScenario)'
	 */
	public void testDistributionIDistributionIScenario() {
		dist2 = new Distribution(dist, scen);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Distribution.Distribution(IDistribution)'
	 */
	public void testDistributionIDistribution() {
		dist2 = new Distribution(dist);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Distribution.setType(DistributionType)'
	 */
	public void testSetType() {
		dist.setType(type);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Distribution.getType()'
	 */
	public void testGetType() {
		assertEquals(DistributionType.uniform, dist.getType());
		dist.setType(type);
		assertEquals(type, dist.getType());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Distribution.setMin(double)'
	 */
	public void testSetMin() {
		dist.setMin(min);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Distribution.getMin()'
	 */
	public void testGetMin() {
		assertNotNull(dist.getMin());
		dist.setMin(min);
		assertEquals(min, dist.getMin());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Distribution.setMax(double)'
	 */
	public void testSetMax() {
		dist.setMax(max);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Distribution.getMax()'
	 */
	public void testGetMax() {
		assertNotNull(dist.getMax());
		dist.setMax(max);
		assertEquals(max, dist.getMax());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Distribution.setMean(double)'
	 */
	public void testSetMean() {
		dist.setMean(mean);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Distribution.getMean()'
	 */
	public void testGetMean() {
		assertNotNull(dist.getMean());
		dist.setMax(mean);
		assertEquals(mean, dist.getMean());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Distribution.setVariance(double)'
	 */
	public void testSetVariance() {
		dist.setVariance(var);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Distribution.getVariance()'
	 */
	public void testGetVariance() {
		assertNotNull(dist.getVariance());
		dist.setMax(var);
		assertEquals(var, dist.getVariance());
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Distribution.setScenario(IScenario)'
	 */
	public void testSetScenario() {
		dist.setScenario(scen);
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.Distribution.getScenario()'
	 */
	public void testGetScenario() {
		assertSame(scen, dist.getScenario());
		scen = new Scenario();
		assertNotSame(scen, dist.getScenario());
		dist.setScenario(scen);
		assertSame(scen, dist.getScenario());
	}

}
