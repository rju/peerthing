package de.peerthing.visualization.querymodel.interfaces;

import org.eclipse.core.resources.IFile;

/**
 * Includes a list of user-defined queries. A query data model can be saved as
 * one xml file. The file in which this data model is saved can be set in this
 * class, too.
 * 
 * @author Michael Gottschalk
 * 
 */
public interface IQueryDataModel extends IQueryModelObject {
    /**
     * Returns a List of IQuerys
     * 
     * @return
     */
    public IListWithParent<IQuery> getQueries();

    /**
     * Sets the file in which the DataModel is stored
     * 
     * @param filename
     */
    public void setFile(IFile file);

    /**
     * Returns the File in which the DataModel is stored.
     * 
     * @return the IFile in which the DataModel is stored
     */
    public IFile getFile();
}
