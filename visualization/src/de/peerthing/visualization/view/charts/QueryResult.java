package de.peerthing.visualization.view.charts;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.resources.IFile;

/**
 * This holds the data that is the result of a query
 * for different simulation runs and database files.
 *
 * @author Michael Gottschalk
 *
 */
public class QueryResult {
    private Hashtable<IFile, DatabaseQueryResult> data =
        new Hashtable<IFile, DatabaseQueryResult>();
    private List<IFile> dbnames = new ArrayList<IFile>();

    private Double maxXValue, minXValue;
    
    public void addDatabaseData(IFile dbFile, DatabaseQueryResult data) {
        this.data.put(dbFile, data);
        dbnames.add(dbFile);
    }

    public List<IFile> getIncludedDatabases() {
        return dbnames;
    }

    public DatabaseQueryResult getDataForDB(IFile dbFile) {
        return data.get(dbFile);
    }

    public Double getMaxXValue() {
        return maxXValue;
    }

    public void setMaxXValue(Double maxXValue) {
        this.maxXValue = maxXValue;
    }

    public Double getMinXValue() {
        return minXValue;
    }

    public void setMinXValue(Double minXValue) {
        this.minXValue = minXValue;
    }
}
