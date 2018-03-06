package de.peerthing.visualization.simulationpersistence;


/**
 * This class is used for displaying information
 * about the simulation runs stored in a database
 * in the resource tree. It only includes the number
 * of the run and a reference to the whole database.
 * 
 * @author Michael Gottschalk
 *
 */
public class SimulationRunMetadata {
    private int runNumber;
    private LogFileMetadata logFile;
    
    /**
     * Creates a new LogFileSimulationRun
     * 
     * @param runNumber The number of the run
     * @param logFile The log file in which the run is stored
     */
    public SimulationRunMetadata(int runNumber, LogFileMetadata logFile) {
        this.runNumber = runNumber;
        this.logFile = logFile;
    }

    /**
     * Returns the number of the simulation run that
     * this class references.
     * 
     * @return
     */
    public int getRunNumber() {
        return runNumber;
    }
    
    
    /**
     * Returns metadata about the database 
     * that this class references
     * 
     * @return
     */
    public LogFileMetadata getLogFile() {
        return logFile;
    }
    
    /**
     * Returns "Simulation Run "+ runNumber
     * 
     */
    public String toString() {
        return "Simulation Run "+ runNumber;
    }
}
