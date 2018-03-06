package de.peerthing.visualization.querymodel;

import junit.framework.TestCase;
import de.peerthing.visualization.querymodel.interfaces.IQuery;
import de.peerthing.visualization.querymodel.interfaces.IQueryDataModel;
import de.peerthing.visualization.querymodel.interfaces.IVisualizationData;

public class VisualizationDataTest extends TestCase {
	IQueryDataModel grandpa;
	IQuery parent;
	VisualizationData visdata;
	protected void setUp() throws Exception {
		super.setUp();
		grandpa = new QueryDataModel();
		parent = new Query(grandpa);
		visdata = new VisualizationData(parent);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*
	 * Test method for 'de.peerthing.visualization.querymodel.VisualizationData.VisualizationData(IQuery)'
	 */
	public final void testVisualizationData() {
		VisualizationData tmp = new VisualizationData(parent);
		assertSame(parent, tmp.getQuery());
		tmp = new VisualizationData(new Query(grandpa));
		assertNotSame(parent, tmp.getQuery());
		assertEquals(IVisualizationData.Styles.line, tmp.getStyle());		
	}

	/*
	 * Test method for 'de.peerthing.visualization.querymodel.VisualizationData.getDataQuery()'
	 */
	public final void testGetDataQuery() {
		assertNotNull(visdata.getDataQuery());
		assertEquals("",visdata.getDataQuery());
		visdata.setDataQuery(null);
		assertNotNull(visdata.getDataQuery());
		assertEquals("",visdata.getDataQuery());
	}
	
	/*
	 * Test method for 'de.peerthing.visualization.querymodel.VisualizationData.setDataQuery(String)'
	 */
	public final void testSetDataQuery() {
		assertEquals("",visdata.getDataQuery());
		String fName = "SELECT a,b From tmp";
		visdata.setDataQuery(fName);
		assertEquals(fName, visdata.getDataQuery());		
	}

	/*
	 * Test method for 'de.peerthing.visualization.querymodel.VisualizationData.setNormalized(boolean)'
	 */
	public final void testSetNormalized() {
		visdata.setNormalized(true);
		assertTrue(visdata.isNormalized());
		visdata.setNormalized(false);
		assertFalse(visdata.isNormalized());
	}

		/*
	 * Test method for 'de.peerthing.visualization.querymodel.VisualizationData.getQuery()'
	 */
	public final void testGetQuery() {
		assertSame(parent,visdata.getQuery());
	}

	/*
	 * Test method for 'de.peerthing.visualization.querymodel.VisualizationData.getQueryDataModel()'
	 */
	public final void testGetQueryDataModel() {
		assertSame(grandpa,visdata.getQueryDataModel());
	}
	
	public final void testGetSetName() {
		String tmp = visdata.getName();		
		assertNotNull(tmp);
		String tmp2=tmp+"abc"+tmp;
		visdata.setName(tmp2);
		assertEquals(tmp2, visdata.getName());
	}
	
	public final void testGetSetStyle() {
		/*
		 *no testing needed I think :)
		 */
	}
	
	public final void testSetGetLabels() {
		String labelname = "name";
		assertNotNull(visdata.getXAxisLabel());
		assertNotNull(visdata.getYAxisLabel());
		visdata.setXAxisLabel(null);
		visdata.setYAxisLabel(null);
		assertNull(visdata.getXAxisLabel());
		assertNull(visdata.getYAxisLabel());
		visdata.setXAxisLabel(labelname);
		visdata.setYAxisLabel(labelname);
		assertEquals(labelname, visdata.getXAxisLabel());
		assertEquals(labelname, visdata.getYAxisLabel());
	}
	
	public final void testSetIsNormalized() {
		assertFalse(visdata.isNormalized());
		visdata.setNormalized(true);
		assertTrue(visdata.isNormalized());
		visdata.setNormalized(false);
		assertFalse(visdata.isNormalized());
	}
}
