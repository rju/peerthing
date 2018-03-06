package de.peerthing.visualization.view.selection;

import de.peerthing.visualization.simulationpersistence.DBinterface;

/**
 * Represents a selected simulation run in
 * the selection view. This class is necessary
 * since the parent database for the run
 * needs to be known.
 * 
 * @author Michael Gottschalk
 *
 */
public class SelectedSimulationRun {
    private DBinterface db;
    private int runNumber;
    
    public SelectedSimulationRun(DBinterface db, int runNumber) {
        this.db = db;
        this.runNumber = runNumber;
    }
    
    public DBinterface getDB() {
        return db;
    }
    
    public int getRunNumber() {
        return runNumber;
    }
}
