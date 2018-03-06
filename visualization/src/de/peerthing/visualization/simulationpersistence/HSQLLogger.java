/*
 * Created on 22.03.2006
 * Changed on 22.03.2006
 */
package de.peerthing.visualization.simulationpersistence;

import org.eclipse.core.resources.IFile;


import de.peerthing.simulation.interfaces.ILogger;
import de.peerthing.visualization.VisualizationPlugin;
import de.peerthing.workbench.filetyperegistration.INavigationTree;

/**
 * Logs the results of a simulation run
 * into an HSQL-Database.
 * 
 * @author gom
 * @author prefec2
 * @author Michael Gottschalk
 * 
 */
public class HSQLLogger implements ILogger {

    private DBinterface db;

    private boolean connectionClosed = false;

    private boolean dbWasOpenBeforeLogging = false;

    /**
     * The simulation run which is currently logged.
     */
    private int run;

    /**
     * Counter for the message IDs
     */
    private int nextMessageId = 0;

    public void addMessage(int sourceId, int destinationId, int sessionId,
            int size, long timeSent, long timeReceived, String name,
            boolean success) {
        db.dbUpdate("INSERT INTO Message VALUES(" + nextMessageId + ","
                + sourceId + "," + destinationId + "," + sessionId + "," + size
                + "," + timeSent + "," + timeReceived + ",'" + name + "', "
                + (success? 1 : 0) + ", "
                + this.run + ")");

        nextMessageId++;
    }

    public void addSessionInformation(long sessionId, long startTime,
            long endTime) {
        db.dbUpdate("INSERT INTO Session" + " VALUES(" + sessionId + ","
                + startTime + "," + endTime + "," + this.run + ")");
    }
    
    public void changeSessionEndTime(long sessionId, long endTime) {
    	db.dbUpdate("UPDATE Session" + " SET EndTime= " + endTime + 
    			" WHERE SessionId=" + sessionId + " AND SimulationRun=" + 
    			this.run);
    }

    public void addResource(int resourceId, int sizeInBytes) {
        db.dbUpdate("INSERT INTO Resource(ResourceId, Size, "
                + "SimulationRun) VALUES (" + resourceId + ", " + sizeInBytes
                + ", " + run + ")");
    }

    public void addResourceChange(int nodeId, int resourceId,
            double fractionAvailable, long time, int quality) {
        db.dbUpdate("INSERT INTO ResourceChange(NodeId, ResourceId, "
                + "FractionAvailable, Time, "
                + "Quality, SimulationRun) VALUES (" + nodeId + ", "
                + resourceId + "," + fractionAvailable + "," + +time + ", "
                + quality + ", " + run + ")");
    }

    public void addNodeStateChange(int nodeId, long time, int state) {
        db.dbUpdate("INSERT INTO NodeState "
                + "(NodeId, Time, StateChange, SimulationRun) VALUES(" + nodeId
                + "," + time + "," + state + "," + run + ")");
    }

    public void addNodeInformation(int nodeId, long uploadSpeed,
            long downloadSpeed, long uploadDelay, long downloadDelay,
            String categoryName, String nodeType) {
        db.dbUpdate("INSERT INTO Node (NodeId, UploadSpeed, DownloadSpeed,"
                + "UploadDelay, DownloadDelay, CategoryName,"
                + "NodeType, SimulationRun) VALUES(" + nodeId + ","
                + uploadSpeed + "," + downloadSpeed + "," + uploadDelay + ","
                + downloadDelay + ",'" + categoryName + "', '" + nodeType
                + "', " + this.run + ")");
    }

    public void debug(String s) {
        System.out.println("Debug: " + s);
    }

    public void startLog(IFile file) {
        dbWasOpenBeforeLogging = (VisualizationPlugin.getDefault()
                .isDatabaseSelected(file));

        db = VisualizationPlugin.getDefault().getDB(file);

        if (!db.tablesExist()) {
            DBTools.createTables(db);
            run = 0;
        } else {
            try {
                run = Integer.valueOf(db
                        .queryOneResult("select max(SimulationRun) from Node")) + 1;
            } catch (NumberFormatException nfe) {
                run = 0;
            }
        }

        connectionClosed = false;
        nextMessageId = 0;
    }

    public void endLog() {
        if (!connectionClosed && !dbWasOpenBeforeLogging) {
            db.shutdown();
            VisualizationPlugin.getDefault().removeDB(db);
            connectionClosed = true;
        }
        final INavigationTree tree = VisualizationPlugin.getDefault()
                .getNavigationTree();

        if (tree != null) {
            // this must be executed with asyncExec
            // since the method can be called from
            // another Thread.
            VisualizationPlugin.getDefault().getWorkbench().getDisplay()
                    .asyncExec(new Runnable() {
                        public void run() {
                            tree.refresh(db.getDatabase());
                        }
                    });
        }
    }

    public void addUserLogInformation(int nodeId, long time, String name,
            String value) {
        db.dbUpdate("INSERT INTO UserLog "
                + "(NodeId, Time, Name, Value, SimulationRun) VALUES(" + nodeId
                + "," + time + ", '" + name + "', '" + value + "', " + run + ")");
    }

    public void discardLog() {
        BasicQueries.removeSimulationRun(db, run);
        endLog();
    }

}
