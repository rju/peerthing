package de.peerthing.visualization.simulationpersistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.Vector;

import org.eclipse.core.runtime.Path;

import de.peerthing.visualization.VisualizationPlugin;


/**
 * Helper methods for the database: Exporting rows
 * as CSV files, creating tables, extracting table
 * names from a definition file.
 * 
 * @author Tjark
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 * 
 */
public class DBTools {

    private static String simtables = "simulationtables.sql";


    /**
     * This function creates the needed tables in the Database.
     * 
     */
    public static void createTables(DBinterface db) {
        String sql = loadTableDefinition(simtables);
        db.dbUpdate(sql);
    }

    /**
     * Reads the table definition 
     * file and extracts the names of the tables.
     * 
     * @return the tablenames in a Vector, in Uppercase
     */
    public static Vector<String> getUpperCaseTableNames() {
        Vector<String> ret = new Vector<String>();
        String tabdef = loadTableDefinition(simtables);
        String[] tmp = tabdef.split("CREATE TABLE");
        for (String s : tmp) {
            s = s.trim();
            if (!s.split("[ ]")[0].trim().equals("")) {
                ret.add(s.split("[ ]")[0].trim().toUpperCase());
            }
        }
        return ret;
    }

    /**
     * Exports the contents of the given database as CSV files (comma separated
     * values) into the given directory. If simRun is not -1, then only the
     * given simulation run is exported. The parameter separator is the char
     * with which the values should be separated (e.g. ",").
     * 
     * @param db
     *            The database to use
     * @param dir
     *            The directory in which to store the files. Existing files are
     *            overwritten.
     * @param simRun
     *            The simulation run to export or -1 if all runs should be
     *            exported.
     * @param separator
     *            The separator to be used in the CSV files.
     */
    public static void exportDatabaseAsCSVFiles(DBinterface db, String dir,
            int simRun, String separator) {
        String where = "";
        if (simRun != -1) {
            where = " where SimulationRun=" + simRun;
        }
        
        Vector<String> tables = getUpperCaseTableNames();
        for (String tableName : tables) {
            StringBuffer fileContent = new StringBuffer();

            ResultSet rs = db.dbQuery("select * from " + tableName + where);
            try {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    String colName = rs.getMetaData().getColumnName(i);
                    fileContent.append(colName);
                    if (i != rs.getMetaData().getColumnCount()) {
                        fileContent.append(separator);
                    }
                }
                fileContent.append("\n");

                while (rs.next()) {
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        fileContent.append(rs.getString(i));
                        if (i != rs.getMetaData().getColumnCount()) {
                            fileContent.append(separator);
                        }
                    }
                    fileContent.append("\n");
                }
                
                // Write the file to the selected directory...
                FileWriter writer = new FileWriter(dir + "/"+ tableName + ".csv");
                writer.write(fileContent.toString());
                writer.close();

            } catch (Exception e) {
                throw new RuntimeException("Could not read from Database: "
                        + e.getMessage(), e);
            } finally {
                try {
                    rs.close();
                } catch (Exception e) {
                    throw new RuntimeException("Could not close Result set: "
                            + e.getMessage(), e);
                }
            }
        }
    }

    /**
     * Returns the table definition stored in a text file in the plug-in
     * directory under tables/.
     * 
     * @param filename
     *            The name of the SQL-file
     * @return the SQL-Statements for TableCreation
     */
    private static String loadTableDefinition(String filename) {
        String ret = "";

        try {
            Path tmp = new Path("tables/" + filename);
            InputStream stream = null;
            try {
                if (VisualizationPlugin.getDefault() != null) {
                    stream = VisualizationPlugin.getDefault().openStream(tmp);
                } else {
                    stream = new FileInputStream(new File(tmp.toOSString()));
                }
            } catch (Exception e) {
                System.out.println("DBTools.loadTableDefinition(" + filename
                        + "):error opening Stream");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    stream));
            String line = null;
            while ((line = reader.readLine()) != null) {
                ret += line + "\n";
            }
            reader.close();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
