package de.peerthing.visualization.querymodel.interfaces;

import java.util.List;

/**
 * A List that has a name and a parent. This can be used 
 * if a list should be displayed as an element in a
 * tree viewer. This element then has a name and a
 * parent to which it belongs.
 *
 * @author Michael Gottschalk
 *
 */
public interface IListWithParent<E> extends List<E>, IQueryModelObject {
    /**
     * Returns the name of this list
     * @return
     */
    public String getName();
    
    
    /**
     * Returns the parent of this list, i.e. the object
     * to which this list belongs
     * 
     * @return
     */
    public Object getParent();
    
    /**
     * Sets the Query Data Model to which this list belongs.
     * @param dataModel
     */
    public void setQueryDataModel(IQueryDataModel dataModel);
}
