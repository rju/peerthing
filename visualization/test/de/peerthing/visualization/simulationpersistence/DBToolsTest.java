package de.peerthing.visualization.simulationpersistence;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import junit.framework.TestCase;

public class DBToolsTest extends TestCase {
	DBinterface dbinterface = null;
	
	protected void setUp() throws Exception {
		super.setUp();
		dbinterface = new DBinterface();		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		dbinterface.shutdown();
	}
	/*
	 *Test method for 'de.peerthing.visualization.simulationpersistence.DBTools.loadTableDefinition(String)' 
	 */
	
	/*
	 * Test method for 'de.peerthing.visualization.simulationpersistence.DBTools.createTables(DBinterface)'
	 */
	/**
	 * checks if all tables are created correct
	 */
	public void testCreateTables() {
		DBTools.createTables(dbinterface);
		DatabaseMetaData rmd = dbinterface.getDatabaseMetaData();
		try {
			ResultSet result = rmd.getTables(null, null, null, new String[] {"TABLE"});
			Vector<String> tabnames = new Vector<String>();
			
			result.beforeFirst();			
			while (result.next()) {tabnames.add(result.getString("TABLE_NAME"));}
			
			for (String s: tabnames) {
				assertTrue(DBTools.getUpperCaseTableNames().contains(s));				
			}
			
			assertEquals(DBTools.getUpperCaseTableNames().size(),tabnames.size());
			
		} catch (SQLException e) {
			
		}
		
		
	}

}
