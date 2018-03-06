/**
 * 
 */
package largeDataAggregator;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.peerthing.visualization.simulationpersistence.DBinterface;

/**
 * @author prefec2
 *
 */
public class Database {
	
	private String name;
	
	private List<Run> runList;
	
	private DBinterface db;

	public Database(String name) {
		this.name = name;
		this.runList = new ArrayList<Run>();
		this.db = null;
	}
	
	public String getName() {
		return this.name;
	}

	public void open() {
		if (this.db == null) {
			this.db = new DBinterface(new NormalFile(this.name));
		}
	}

	public List<Run> getRunList() {
		return this.runList;
	}

	public void close() {
		if (this.db != null) {
			this.db.shutdown();
			this.db = null;
		}
	}

	public void writeCSV() {
		try {
			PrintStream out = new PrintStream (this.name + ".csv");
			try {
				ResultSet resultSet = this.executeQuery("SELECT * FROM result;");
				for (int i=1;i<=resultSet.getMetaData().getColumnCount();i++) {
					out.print("\"" + resultSet.getMetaData().getColumnName(i) + "\"");
					if (i<resultSet.getMetaData().getColumnCount())
						out.print(",");
				}
				out.println();
				while (resultSet.next()) {
					for (int i=1;i<=resultSet.getMetaData().getColumnCount();i++) {
						String typeName = resultSet.getMetaData().getColumnTypeName(i);
						if (typeName.equals("VARCHAR"))
							out.print("\"" + resultSet.getString(i) + "\"");
						else if (typeName.equals("NUMERIC"))
							out.print("\"" + resultSet.getDouble(i) + "\"");
						else if (typeName.equals("DECIMAL"))
							out.print("\"" + resultSet.getDouble(i) + "\"");
						else
							out.print("\"" + resultSet.getInt(i) + "\"");
						if (i<resultSet.getMetaData().getColumnCount())
							out.print(",");
					}
					out.println();
				}
			} catch (SQLException e) {
			 	System.out.println("SQL exception: " + e.getMessage());
			}
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found exception: " + e.getMessage());
		}
	}

	public void addRun(Run run) {
		this.runList.add(run);
		run.setDBHandle(this.db);
	}

	public boolean tableExist() {
		return this.db.tablesExist();
	}

	public DBinterface getDBHandle() {
		return this.db;
	}

	public ResultSet executeQuery(String query) {
		return this.db.dbQuery(query);
	}

}
