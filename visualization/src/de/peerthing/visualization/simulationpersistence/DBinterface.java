package de.peerthing.visualization.simulationpersistence;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import org.eclipse.core.resources.IFile;

/**
 * An Interface to a database. Different databases can be used internally, but
 * only HSQLDB is really tested.
 * 
 * dbmanager: 0=mysql, not supported yet 1=hsqlfile, created in the
 * userdirectory 2=hsqlmem, for testing purposes
 * 
 * @author Tjark Bikker
 * @review Johannes Fischer
 * @author prefec2
 * @author Michael Gottschalk
 * 
 */

public class DBinterface {
    /**
     * Switch that defines which database to use.
     */
    private static DBMTYPES DBMANAGER = DBMTYPES.HSQLFILE;

    private static enum DBMTYPES {
        MYSQL, HSQLFILE, HSQLMEM
    }

    private String classname = "";

    private String connectionstring = "";

    private String connectionuser = "";

    private String connectionpw = "";

    /**
     * File in which the database is stored.
     */
    private IFile dbfile;

    private Connection connection;

    public static final boolean DEBUG = true;

    private boolean shutDown = false;

    /**
     * The runs in this database that are currently selected
     * by the user.
     */
    private ArrayList<Integer> selectedRuns = new ArrayList<Integer>();

    /**
     * returns the DatabaseMetaData for the actual
     * 
     * @see java.sql.Connection
     * @return DatabaseMetadata if exists, null else
     */
    protected DatabaseMetaData getDatabaseMetaData() {
        try {
            return connection.getMetaData();
        } catch (SQLException e) {
            return null;
        }

    }

    public DBinterface() {
        connect(null);
    }

    public DBinterface(IFile dbfilename) {
        connect(dbfilename);
    }

    /**
     * loads the jdbcDriver, according to the chosen dbmanager
     */
    private void loadClass() {
        if (dbfile == null) {
            DBMANAGER = DBMTYPES.HSQLMEM;
        }
        switch (DBMANAGER) {
        case MYSQL:
            classname = "com.mysql.jdbc.Driver";
            connectionstring = "jdbc:mysql:not:supported";
            break;
        case HSQLFILE:
            classname = "org.hsqldb.jdbcDriver";
            connectionstring = "jdbc:hsqldb:file:"
                    + dbfile.getLocation().toString();
            //System.out.println(connectionstring);
            connectionuser = "sa";
            connectionpw = "";
            break;
        case HSQLMEM:
            classname = "org.hsqldb.jdbcDriver";
            connectionstring = "jdbc:hsqldb:mem:test";
            connectionuser = "sa";
            connectionpw = "";
            break;
        default:
            System.out.println("ERROR: Wrong dbmanager set");
        }
        try {
            Class.forName(classname);
        } catch (ClassNotFoundException e1) {
            System.out
                    .println("ERROR: failed to load JDBC driver:" + classname);
            e1.printStackTrace();
        }
    }

    /**
     * Checks if the needed tables (MESSAGE; NODE; NODESTATE; RESOURCE;
     * RESOURCECHANGE; SESSION) exist
     * 
     * @return true if all tables exist, false otherwise
     */
    public boolean tablesExist() {
        boolean exist = true;
        ResultSet rs = null;
        try {
            // MESSAGE
            Vector<String> tablenames = DBTools.getUpperCaseTableNames();
            for (String s : tablenames) {
                rs = connection.getMetaData().getTables(null, null, s, null);
                exist = exist && rs.first();
            }
        } catch (SQLException e) {
            System.out.println("Could not determine available tables.");
            e.printStackTrace();
            throw new RuntimeException();
        }

        return exist;
    }

    /**
     * opens the connection to the chosen Database
     * 
     * @param dbfilename
     *            the chosen database, if null an inmemory-DB is used;
     */
    private void connect(IFile dbfilename) {
        this.dbfile = dbfilename;
        loadClass();
        try {
            connection = DriverManager.getConnection(connectionstring,
                    connectionuser, connectionpw);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Executes an update statement on the database.
     * 
     * @param argument
     *            the SQL statement
     * @return the number of affected rows
     */
    public int dbUpdate(String argument) {
        int returnvalue = -1;
        try {
            returnvalue = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE).executeUpdate(argument);
        } catch (SQLException e) {
            System.out.println("dbUpdate(" + argument + ")");
            e.printStackTrace();
            throw new RuntimeException();
        }
        return returnvalue;
    }

    /**
     * Executes the given SQL query and returns the 
     * resulting java.sql.ResultSet
     * 
     * @param argument
     * @return java.sql.ResultSet
     */
    public ResultSet dbQuery(String argument) {
        ResultSet returnValue = null;

        try {
            Statement stmt = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            returnValue = stmt.executeQuery(argument);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return returnValue;
    }

    /**
     * Returns the first result returned by the given query. May return null.
     * 
     * @param sqlQuery
     * @return the Result as a String, may be null
     */
    public String queryOneResult(String sqlQuery) {
        String ret = null;

        ResultSet res = dbQuery(sqlQuery);
        try {
            if (res.next()) {
                ret = res.getString(1);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        return ret;
    }

    /**
     * Returns the Database file.
     * 
     * @return String name of the Database
     */
    public IFile getDatabase() {
        return dbfile;
    }

    /**
     * Shuts down the current database. Other methods may no longer be used
     * after a call to this method.
     * 
     */
    public void shutdown() {
        if (!shutDown) {
            shutDown = true;
            dbQuery("SHUTDOWN");
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Connection could not be closed: "+ e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns if this database is already shut down
     * 
     * @return true if already shutdown, false if not
     */
    public boolean isShutDown() {
        return shutDown;
    }


    /**
     * Checks if all Tables are Empty
     * 
     * @return true if no table has content, false if there is content in at
     *         least one table
     */
    public boolean tablesEmpty() {
        boolean hascontent = true;
        try {
            hascontent = !dbQuery("SELECT * FROM MESSAGE").last()
                    && !dbQuery("SELECT * FROM NODE").last()
                    && !dbQuery("SELECT * FROM NODESTATE").last()
                    && !dbQuery("SELECT * FROM RESOURCE").last()
                    && !dbQuery("SELECT * FROM RESOURCECHANGE").last()
                    && !dbQuery("SELECT * FROM SESSION").last();
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return hascontent;
    }

    /**
     * Two DBinterface instances are asserted equal if the filenames of the
     * databases are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DBinterface) {
            return ((DBinterface) obj).getDatabase().equals(getDatabase());
        }
        return false;
    }
    
    public ArrayList<Integer> getSelectedRuns() {
        return selectedRuns;
    }

}
