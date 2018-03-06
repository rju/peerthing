package de.peerthing.visualization.querymodel;

import junit.framework.TestCase;

public class QueryTest extends TestCase {
	Query query;
	QueryDataModel parent;

	public QueryTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		parent = new QueryDataModel();
		query = new Query(parent);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*
	 * Test method for 'de.peerthing.visualization.querymodel.Query.Query(IQueryDataModel)'
	 */
	public final void testQuery() {
		Query tmp = new Query(null);
		assertNull(tmp.getQueryDataModel());
		assertEquals(0,tmp.getVisualizationData().size());
		tmp.addVisualizationData(new VisualizationData(tmp));
		assertEquals(1,tmp.getVisualizationData().size());
		
		tmp = new Query(parent);
		assertSame(parent, tmp.getQueryDataModel());
		assertEquals(0,tmp.getVisualizationData().size());
		tmp.addVisualizationData(new VisualizationData(tmp));
		assertEquals(1,tmp.getVisualizationData().size());		
	}

	/*
	 * Test method for 'de.peerthing.visualization.querymodel.Query.addVisualizationData(IVisualizationData)'
	 * Test method for 'de.peerthing.visualization.querymodel.Query.getVisualizationData()'
	 * Test method for 'de.peerthing.visualization.querymodel.Query.getVisualizationData(int)'
	 */
	
	public final void testAddGetRemoveVisualizationData() {
		int counter = 10;
		VisualizationData[] tmp = new VisualizationData[counter];

		int tmpsize = query.getVisualizationData().size();
		for (int y=0;y<counter;y++) {
			tmp[y] = new VisualizationData(query);
			query.addVisualizationData(tmp[y]);
		}
		assertEquals(counter+tmpsize, query.getVisualizationData().size());
		for (int y=0;y<counter;y++) {
			assertSame(tmp[y], query.getVisualizationData(y));
		}				
	}

	/*
	 * Test method for 'de.peerthing.visualization.querymodel.Query.getName()'
	 */
	public final void testGetSetName() {
		assertNotNull(query.getName());
		query.setName(null);
		assertNotNull(query.getName());
		String tmp = "testname";
		query.setName(tmp);
		assertEquals(tmp,query.getName());
	}

	/*
	 * Test method for 'de.peerthing.visualization.querymodel.Query.toString()'
	 */
	public final void testToString() {
		/* 
		 * no testing needed afait :) 
		 */
	}

	/*
	 * Test method for 'de.peerthing.visualization.querymodel.Query.getQueryDataModel()'
	 */
	public final void testGetQueryDataModel() {
		assertSame(parent, query.getQueryDataModel());
		Query tmp = new Query(null);
		assertNull(tmp.getQueryDataModel());		
	}	
	

	/*
	 * Test method for 'de.peerthing.visualization.querymodel.Query.getPreparingQueries()'
	 * Test method for 'de.peerthing.visualization.querymodel.Query.setPreparingQueries(String)'
	 */
	public final void testGetSetPreparingQueries() {
		assertNotNull(query.getPreparingQueries());
		query.setPreparingQueries(null);
		assertNotNull(query.getPreparingQueries());
		String tmp = "SELECT a,b FROM test";
		query.setPreparingQueries(tmp);
		assertEquals(tmp,query.getPreparingQueries());
	}

}
