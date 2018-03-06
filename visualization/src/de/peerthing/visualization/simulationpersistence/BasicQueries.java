package de.peerthing.visualization.simulationpersistence;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * This class provides the basic queries that are needed for creating the charts
 * and tables.
 * 
 * @author Michael Gottschalk
 * 
 */
public class BasicQueries {

    /**
     * Returns the maximum simulation time of a simulation run in a given
     * database.
     * 
     */
    public static long getMaxSimulationTime(DBinterface db, int simRun) {
        String res = db
                .queryOneResult("select max(TimeReceived) from Message where SimulationRun = "
                        + simRun);
        return Long.parseLong(res);
    }

    /**
     * Returns the maximum simulation time in a db file over all simulation
     * runs.
     * 
     * @param db
     * @return
     */
    public static long getMaxSimulationTime(DBinterface db) {
        String res = db.queryOneResult("select max(TimeReceived) from Message");
        if (res == null) {
            return 0;
        }
        return Long.parseLong(res);
    }

    /**
     * Returns the number of simulation runs that are recorded in the given
     * database, based on the Nodes-Table.
     * 
     * @param db
     * @return
     */
    public static int getNumberOfSimulationRuns(DBinterface db) {
        if (db.tablesExist()) {
            return Integer
                    .valueOf(db
                            .queryOneResult("select count(distinct SimulationRun) from Node"));
        } else {
            return 0;
        }
    }

    /**
     * Returns the IDs of the simulation runs that are included in a database.
     * Since simulation runs can be deleted, these need not simply be numbered
     * from 0 to n-1!
     * 
     * @param db
     * @return The IDs of the simulation runs, ordered by ID.
     */
    public static int[] getIncludedSimulationRuns(DBinterface db) {
        ResultSet rs = db
                .dbQuery("select distinct SimulationRun from Node order by SimulationRun");

        ArrayList<Integer> numbers = new ArrayList<Integer>();
        try {
            while (rs.next()) {
                numbers.add(rs.getInt(1));
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        int[] res = new int[numbers.size()];
        for (int i = 0; i < numbers.size(); i++) {
            res[i] = numbers.get(i);
        }

        return res;
    }

    /**
     * Removes all data from the database that belongs to the specified run.
     * 
     * @param db
     *            The database to use
     * @param run
     *            The number of the run that should be deleted
     */
    public static void removeSimulationRun(DBinterface db, int run) {
        String[] tables = new String[] { "Node", "Session", "Message",
                "Resource", "ResourceChange", "NodeState", "UserLog" };

        for (String table : tables) {
            db.dbUpdate("delete from " + table + " where SimulationRun = "
                    + run);
        }
    }

}
