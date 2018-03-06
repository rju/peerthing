package de.peerthing.visualization.querymodel;

import java.util.ArrayList;

import de.peerthing.visualization.querymodel.interfaces.IListWithParent;
import de.peerthing.visualization.querymodel.interfaces.IQueryDataModel;


/**
 * Default Implementation of IListWithParent. See
 * the interface for a description.
 *
 * @author Michael Gottschalk
 *
 */
public class ListWithParent<E> extends ArrayList<E> implements IListWithParent<E> {
    private static final long serialVersionUID = 8418606808677952854L;

    private Object parent;
    private String name;
    private IQueryDataModel model;
    
    /**
     * Creates a new ListWithParent with a given parent,
     * a name and a data model to which the list belongs.
     * 
     * @param parent The parent object
     * @param name the name of the list, e.g. to be displayed
     *              in a tree viewer
     * @param model The model to which this list belongs
     */
    public ListWithParent(Object parent, String name, IQueryDataModel model) {
        this(parent, name);
        this.model = model;
    }

    /**
     * Creates a new ListWithParent with a given parent and
     * a name. The data model to which the list belongs must
     * be set later with setQueryDataModel().
     * 
     * @param parent The parent object
     * @param name the name of the list, e.g. to be displayed
     *              in a tree viewer
     */
    public ListWithParent(Object parent, String name) {
        this.parent = parent;
        this.name = name;
    }
    
    public String getName() {
        return name;
    }


    public Object getParent() {
        return parent;
    }
    
    /**
     * Returns the name of the list
     */
    @Override
    public String toString() {
        return name;
    }

    public void setQueryDataModel(IQueryDataModel dataModel) {
        this.model = dataModel;
    }

    public IQueryDataModel getQueryDataModel() {
        return model;
    }

}
