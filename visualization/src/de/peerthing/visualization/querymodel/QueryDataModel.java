package de.peerthing.visualization.querymodel;

import org.eclipse.core.resources.IFile;

import de.peerthing.visualization.querymodel.interfaces.IListWithParent;
import de.peerthing.visualization.querymodel.interfaces.IQuery;
import de.peerthing.visualization.querymodel.interfaces.IQueryDataModel;

/**
 * Default implementation of IQueryDataModel.
 * See the interface for a description.
 *
 * @author Michael Gottschalk
 *
 */
public class QueryDataModel implements IQueryDataModel {
    /**
     * The queries that belong to this data model
     */
    private IListWithParent<IQuery> queries;
    
    /**
     * The file in which this data model was saved.
     */
    private IFile file;

    public QueryDataModel() {
        queries = new ListWithParent<IQuery>(this, "Queries", this);
    }


    public IListWithParent<IQuery> getQueries() {
        return queries;
    }

    public void setFile(IFile file) {
        this.file = file;
    }

    public IFile getFile() {
        return file;
    }


    public IQueryDataModel getQueryDataModel() {
        return this;
    }

    /**
     * Returns the name of the file in which this
     * data model is saved or "[not saved]" if no
     * file is set.
     * 
     */
    public String toString() {
        if (file != null) {
            return file.getName();
        }
        return "[not saved]";
    }

}
