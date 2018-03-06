package de.peerthing.visualization.querymodel.interfaces;

/**
 * All classes that belong to the query data model
 * must implement this interface. A common super-interface
 * is needed for displaying model objects in the resource
 * tree viewer.
 * 
 * @author Michael Gottschalk
 *
 */
public interface IQueryModelObject {
    /**
     * Returns the data model to which this
     * model object belongs. This is the root
     * of the tree created by the model objects.
     * 
     * @return
     */
    public IQueryDataModel getQueryDataModel();
}
