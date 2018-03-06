package de.peerthing.visualization.simulationpersistence;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;


/**
 * This class includes metadata about a log file. This is the DBinterface object
 * opened for this file, the file in which the database is stored and the
 * simulation runs that are stored in the file. The class is only used for
 * displaying the simulation runs in the resource tree.
 * 
 * @author Michael Gottschalk
 * 
 */
public class LogFileMetadata {
    /**
     * The database interface that is referenced
     */
    private DBinterface db;

    /**
     * The file in which the database is stored
     */
    private IFile logFile;

    /**
     * The runs that the database includes
     */
    private List<SimulationRunMetadata> runs = new ArrayList<SimulationRunMetadata>();

    /**
     * Returns the file in which the database is stored.
     * 
     * @return
     */
    public IFile getLogFile() {
        return logFile;
    }

    /**
     * Sets the file in which the referenced database is stored.
     * 
     * @param logFile
     */
    public void setLogFile(IFile logFile) {
        this.logFile = logFile;
    }

    /**
     * Returns the database interface for the references database.
     * 
     * @return
     */
    public DBinterface getDb() {
        return db;
    }

    /**
     * Sets the database that is referenced.
     * 
     * @param db
     */
    public void setDb(DBinterface db) {
        this.db = db;
    }

    /**
     * Returns information about the runs that are stored in the database.
     * 
     * @return A List of the runs (never null)
     */
    public List<SimulationRunMetadata> getRuns() {
        return runs;
    }
}
