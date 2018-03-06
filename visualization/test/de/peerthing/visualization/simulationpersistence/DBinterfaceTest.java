package de.peerthing.visualization.simulationpersistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Vector;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
/**
 * 
 * @author gom
 *
 */
public class DBinterfaceTest extends TestCase {
	private DBinterface dbinterface;
	private IProject project;
	
	public DBinterfaceTest(String name) {
		super(name);
	}
	private void createTable(String s) {
		dbinterface.dbUpdate("CREATE TABLE "+ s +"(pkey INTEGER, s VARCHAR)");
	}
	private void dropTable(String s) {
		dbinterface.dbUpdate("DROP TABLE "+ s +" IF EXISTS");
	}
	
	/**
	 * checks if the tmp-files from the last test still exist, if so they're getting deleted 
	 */
	protected void setUp() throws Exception {
		super.setUp();	
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		project = workspace.getRoot().getProject("dbitest");
			
		if (!project.exists()) {
			project.delete(true,true,null);
		}
		
		project.create(null);
		project.open(null);
         
		dbinterface = new DBinterface();		
	}
	/**
	 * shutsdown the Database and deletes the Project
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		dbinterface.shutdown();
		project.close(null);
		project.delete(true,true,null);
	}
	

	/*
	 * Test method for 'de.peerthing.visualization.simulationpersistence.DBinterface.dbUpdate(String)'
	 */
	/**
	 * creates a table, inserts 2 lines and checks if there are 2 Lines in the table...
	 */
	public void testDbUpdate() {		
		assertEquals(0,dbinterface.dbUpdate("CREATE TABLE TMP(a VARCHAR, b VARCHAR)"));
		assertEquals(1,dbinterface.dbUpdate("INSERT INTO TMP VALUES('A','B')"));
		assertEquals(1,dbinterface.dbUpdate("INSERT INTO TMP VALUES('A2','B2')"));
		ResultSet rs = dbinterface.dbQuery("SELECT * FROM TMP"); 
		try {
			rs.last();
			assertEquals(2,rs.getRow());
		} catch (SQLException e) {
			e.printStackTrace();
		}				
	}

	/*
	 * Test method for 'de.peerthing.visualization.simulationpersistence.DBinterface.getDatabase()'
	 */
	/** 
	 * checks if the returned Database-Name is correct.
	 */
	public void testGetDatabase() {
		assertNull(dbinterface.getDatabase());
	}

	/*
	 * Test method for 'de.peerthing.visualization.simulationpersistence.DBinterface.queryOneResult(String)'
	 */
	/**
	 * fills a table and checks if the returned String are correct.
	 */
	public void testQueryOneResult() {
		Random rand = new Random();		
		createTable("gnibbel");
		String[] strings = new String[1000];
		for (int i = 0; i < 1000; i++) {
			strings[i] = rand.nextInt()+"";
			dbinterface.dbUpdate("INSERT INTO gnibbel(pkey, s) VALUES ("+i+","+strings[i]+")");			
		}
		for (int i = 0; i < 1000; i++) {
			assertEquals(strings[i],dbinterface.queryOneResult("SELECT s from gnibbel where pkey="+i));
			assertEquals(i+"",dbinterface.queryOneResult("SELECT pkey from gnibbel where pkey="+i));
		}		
		assertNull(dbinterface.queryOneResult("SELECT s from gnibbel where pkey=2000"));
	}
	/*
	 * Test method for 'de.peerthing.visualization.simulationpersistence.DBinterface.tablesEmpty()'
	 */
	/**
	 * tests if the tables are empty.
	 */
	public void testTablesEmpty() {
		DBTools.createTables(dbinterface);
		assertTrue(dbinterface.tablesEmpty());
		dbinterface.dbUpdate("INSERT INTO SESSION(SessionID, SimulationRun) VALUES(1,1)");
		assertFalse(dbinterface.tablesEmpty());		
	}
	/*
	 * Test method for 'de.peerthing.visualization.simulationpersistence.DBinterface.tablesExist()'
	 */
	/**
	 * checks if tablesexist() reports false if one up to n-1 tables are missing or true if all tables 
	 * (MESSAGE; NODE; NODESTATE; RESOURCE; RESOURCECHANGE; SESSION) exist 
	 */
	public void testTablesExist() {
		//String[] tablenames = {"MESSAGE","NODE","NODESTATE","RESOURCE","RESOURCECHANGE","SESSION"};
		Vector<String> tablenames = DBTools.getUpperCaseTableNames();
		for (String s : tablenames) {
			createTable(s);
		}
			for (String s2 : tablenames) {
				dropTable(s2);
				assertFalse(dbinterface.tablesExist());
				createTable(s2);
			}		
		assertTrue(dbinterface.tablesExist());		
	}

}
