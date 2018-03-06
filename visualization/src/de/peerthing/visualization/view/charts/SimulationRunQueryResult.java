package de.peerthing.visualization.view.charts;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the data that is a result of
 * the query for one simulation run.
 *
 * @author Michael Gottschalk
 *
 */
public class SimulationRunQueryResult {
    private List<String[]> data = new ArrayList<String[]>();
    private List<String> columnNames = new ArrayList<String>();
    private int runNumber = 0;
    private String dbName = "";

    /**
     * Creates a new instance of the class.
     *
     * @param runNumber The number of the simulation run in the database or
     * 				-1 to indicate that all aggregated within this object
     * @param dbName
     */
    public SimulationRunQueryResult(int runNumber, String dbName) {
        this.runNumber = runNumber;
        this.dbName = dbName;
    }

    public List<String[]> getData() {
        return data;
    }

    /**
     * Adds a name to the list of column names.
     *
     */
    public void addColumnName(String name) {
        columnNames.add(name);
    }

    public String getColumnName(int no) {
        return columnNames.get(no);
    }

    public int getNumberOfColumns() {
        return columnNames.size();
    }

    public void addRow(String[] row) {
        data.add(row);
    }

    public void addList(List<String[]> list) {
    	data.addAll(list);
    }

    public int getRunNumber() {
        return runNumber;
    }

    /**
     * Returns the name of this simulation run. This is the database name plus
     * the simulation run, or the database name plus ", all runs" to indicate
     * the all runs are aggregated here.
     *
     * @return
     */
    public String getName() {
    	if (runNumber != -1) {
    		return getDBName()+ ", Run "+ runNumber;
    	} else {
    		return getDBName() + ", all runs";
    	}
    }

    /**
     * Returns the name of the database in which this simulation run
     * is located. This is not the full file name, but the last part of the
     * file name without the file name ending.
     *
     * @return
     */
    public String getDBName() {
        return dbName;
    }

    public void print() {
        for (String[] row : data) {
            for (String value : row) {
                System.out.print(value+ "\t");
            }
            System.out.println();
        }
    }
}
