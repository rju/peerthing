/**
 * 
 */
package largeDataAggregator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;

import de.peerthing.visualization.querymodel.interfaces.IListWithParent;
import de.peerthing.visualization.querymodel.interfaces.IQuery;
import de.peerthing.visualization.querymodel.interfaces.IQueryDataModel;
import de.peerthing.visualization.querymodel.interfaces.IVisualizationData;
import de.peerthing.visualization.querypersistence.QueryPersistence;
import de.peerthing.visualization.simulationpersistence.DBTools;

/**
 * @author prefec2
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/* get parameters:
		 * 1. result database and csv-file
		 * 2. query definition
		 * 3. which query
		 * 4. which visualisation
		 * 5. aggregation file
		 * 6. input database
		 * 7. comma seperated list of runs
		 * 8. next database with
		 * 9. next list of runs
		 */
		
		if (args.length>7) {
			Database result = new Database(args[0]);
			IFile file = new NormalFile(args[1]);
			QueryPersistence qp = new QueryPersistence();
			IQueryDataModel queryModel = qp.loadQueries(file);
			
			/* find queries */
			String preparingQuery = null;
			String visualQuery = null;
			for (IQuery query : queryModel.getQueries()) {
				System.out.println("Have " + query.getName());
				if (query.getName().equals(args[2])) { /* ok this query shall be executed */
					preparingQuery = query.getPreparingQueries();
					/* check whether an visualisation query is issued too */
					if (!args[3].equals("-")) { /* visualisation required */
						IListWithParent<IVisualizationData> visualList = 
							query.getVisualizationData();
						for (IVisualizationData visual : visualList) {
							if (visual.getName().equals(args[3])) {
								visualQuery = visual.getDataQuery();
								break;
							}
						}
					}
					break;
				}
			}
			
			/* open aggregation file */
			try {
				FileInputStream aggregate = new FileInputStream(args[4]);
			
				/* find databases and runs */
				List<Database> dbList = new ArrayList<Database>();
				for(int j=5;j<args.length;j+=2) {
					System.out.print ("Open input db " + args[j] + " with runs ");
					Database db = new Database(args[j]);
					for(String run : args[j+1].split(",")) {
						db.addRun(new Run(Integer.parseInt(run)));
						System.out.print(run + " ");
					}
					System.out.println("");
					dbList.add(db);
				}
				
				/* print start summary */
				System.out.println("Destination db is " + args[0]);
				if (preparingQuery != null) {
					System.out.println("Main query is " + args[2]);
				} else {
					System.out.println("Main query " + args[2] + " not found.");
					usage();
					return;
				}
				if (visualQuery != null) 
					System.out.println("Visualisation query is " + args[3]);
				else {
					System.out.println("No valid visualisation query specified.");
					usage();
					return;
				}
				/* open result database */
				result.open();
				
				/* execute query on each run and database */
				String tableString = null;
				for (Database database : dbList) {
					System.out.println("Connecting to database: " + database.getName());
					database.open();
					if (database.tableExist()) {
						for (Run run : database.getRunList()) {
							run.setDBHandle(database.getDBHandle());
							System.out.println("\tWorking on run : " + run.getNumber());
							ResultSet resultSet = run.executeQuery(preparingQuery, visualQuery);
							/* insert result into the global database */
							try {
								if (tableString == null) {
									tableString = "DROP TABLE IF EXISTS aggregator ; CREATE TABLE aggregator (";
									for (int i=1;i<=resultSet.getMetaData().getColumnCount();i++) {
										tableString += resultSet.getMetaData().getColumnName(i) +
												" " + resultSet.getMetaData().getColumnTypeName(i);
										if (i<resultSet.getMetaData().getColumnCount())
											tableString+=", ";
									}
									tableString += ");";
									System.out.println("Aggregator table is: " + tableString);
									result.executeQuery(tableString);
								}
								while (resultSet.next()) {
									String insert = "INSERT INTO aggregator VALUES (";
									for (int i=1;i<=resultSet.getMetaData().getColumnCount();i++) {
										String typeName = resultSet.getMetaData().getColumnTypeName(i);
										if (typeName.equals("VARCHAR"))
											insert += "'" + resultSet.getString(i) + "'";
										else if (typeName.equals("NUMERIC"))
											insert += resultSet.getDouble(i);
										else if (typeName.equals("DECIMAL"))
											insert += resultSet.getDouble(i);
										else
											insert += resultSet.getInt(i);
										if (i<resultSet.getMetaData().getColumnCount())
											insert += ", ";
									}
									insert += ");";
									result.executeQuery(insert);
								}
							} catch (SQLException e) {
							 	System.out.println("SQL exception: " + e.getMessage());
							 	usage();
							}
						}
					} else {
						System.out.println("DB either empty or not found.");
						usage();
					}
					database.close();
				}
						
				/* run all strings form aggregate */
				String line = aggregator(aggregate);
				if (!line.equals("")) { /* read was successful */
					System.out.println("Aggregating");
					result.executeQuery(line);
				
					/* Add basic tables to trick PeerThing to believe that this 
					 * is a PeerThing database.
					 */
					if (!result.getDBHandle().tablesExist()) 
						DBTools.createTables(result.getDBHandle());
					result.executeQuery("INSERT INTO Node VALUES(0,0,0,0,0,'','',0);");
					
					/* print out result database */
					result.writeCSV();
				}
				result.close();
				System.out.println("Done.");
			} catch (FileNotFoundException e) {
				System.out.println("Aggregation file not found: " + args[4]);
				usage();
				return;
			}
		} else 
			usage();
	}
	
	private static String aggregator (InputStream stream) {
		String ret = "";
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	        	ret += line + "\n";
	        }
	        reader.close();
	        stream.close();
		} catch (IOException e) {
			System.out.println("Cannot read aggregation: " + e.getMessage());
		}
	    return ret;		
	}

	private static void usage() {
		System.out.println("Usage: lda <result db> <query definition> <query selection> <aggregation file>");
		System.out.println("\t<input database> <comma seperated list of runs>");
		System.out.println("\t[<next input database> <comma seperated list of runs>]*");
	}
}
