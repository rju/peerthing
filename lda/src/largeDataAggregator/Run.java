/**
 * 
 */
package largeDataAggregator;

import java.sql.ResultSet;

import de.peerthing.visualization.simulationpersistence.DBinterface;

/**
 * @author prefec2
 *
 */
public class Run {
	
	private int id;
	
	private DBinterface db;
	
	public Run(int id) {
		this.id = id;
		this.db = null;
	}

	public int getNumber() {
		return id;
	}

	public ResultSet executeQuery(String preparingQuery, String visualQuery) {
		if (this.db != null) {
			preparingQuery = preparingQuery.replace("$RUN$",String.valueOf(this.id));
			this.db.dbQuery(preparingQuery);
			visualQuery = visualQuery.replace("$RUN$",String.valueOf(this.id));
			return this.db.dbQuery(visualQuery);
		} else
			return null;
	}

	public void setDBHandle(DBinterface db) {
		this.db = db;		
	}

}
