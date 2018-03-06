package de.peerthing.visualization.querymodel;

import de.peerthing.visualization.VisualizationPlugin;
import de.peerthing.visualization.querymodel.interfaces.IListWithParent;
import de.peerthing.visualization.querymodel.interfaces.IQuery;
import de.peerthing.visualization.querymodel.interfaces.IQueryDataModel;
import de.peerthing.visualization.querymodel.interfaces.IVisualizationData;
import de.peerthing.workbench.filetyperegistration.IUpDownMovable;

/**
 * Default implementation of IQuery. See the interface for a description.
 * 
 * @author Michael Gottschalk
 * 
 */
public class Query implements IQuery, IUpDownMovable {
    /**
     * The visualization that belong to this query
     */
    private IListWithParent<IVisualizationData> visualizationData;

    /**
     * The preparing sql query
     */
    private String query = "";

    /**
     * The name of the query
     */
    private String name = "";

    /**
     * The data model to which this query belongs
     */
    private IQueryDataModel parent;

    /**
     * Creates a new Query with the given parent
     * 
     * @param parent
     *            the data model to which this query belongs
     */
    public Query(IQueryDataModel parent) {
        this.parent = parent;
        visualizationData = new ListWithParent<IVisualizationData>(this,
                "Visualizations", parent);
    }

    /**
     * Copy contructor. Creates a new query with the values taken from copy. The
     * visualizations belonging to the query are deep-copied. The parent is set
     * to the given parent.
     * 
     * @param copy
     *            The object to copy
     * @param parent
     *            The new parent of the object
     */
    public Query(Query copy, IQueryDataModel parent) {
        this(parent);
        query = copy.query;
        name = copy.name;

        for (IVisualizationData vis : copy.getVisualizationData()) {
            visualizationData.add(new VisualizationData(
                    (VisualizationData) vis, this));
        }
    }

    public void addVisualizationData(IVisualizationData vd) {
        visualizationData.add(vd);
    }

    public void removeVisualizationData(IVisualizationData vd) {
        visualizationData.remove(vd);
    }

    public IListWithParent<IVisualizationData> getVisualizationData() {
        return visualizationData;
    }

    public IVisualizationData getVisualizationData(int n) {
        return visualizationData.get(n);
    }

    /**
     * Returns the name of the query (never null)
     */
    public String getName() {
        return (this.name == null) ? "" : this.name;
    }

    /**
     * Returns the name of the query
     */
    public String toString() {
        return getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public IQueryDataModel getQueryDataModel() {
        return parent;
    }

    public String getPreparingQueries() {
        return (query == null) ? "" : query;
    }

    public void setPreparingQueries(String sqlQueries) {
        query = sqlQueries;
    }

    /**
     * Moves this query one position up in the array of the parent
     * QueryDataModel. Refreshes the resource tree and marks the editor dirty.
     */
    public void moveUp() {
        int oldIndex = getQueryDataModel().getQueries().indexOf(this);
        getQueryDataModel().getQueries().remove(this);
        getQueryDataModel().getQueries().add(oldIndex - 1, this);
        VisualizationPlugin.getDefault().getNavigationTree().refresh(
                getQueryDataModel().getFile());
        VisualizationPlugin.getDefault().getQueryFiletypeRegistration()
                .getEditor(getQueryDataModel().getFile()).setDirty();
    }

    /**
     * Moves this query one position down in the array of the parent
     * QueryDataModel. Refreshes the resource tree and marks the editor dirty.
     */
    public void moveDown() {
        int oldIndex = getQueryDataModel().getQueries().indexOf(this);
        getQueryDataModel().getQueries().remove(this);
        getQueryDataModel().getQueries().add(oldIndex + 1, this);
        VisualizationPlugin.getDefault().getNavigationTree().refresh(
                getQueryDataModel().getFile());
        VisualizationPlugin.getDefault().getQueryFiletypeRegistration()
                .getEditor(getQueryDataModel().getFile()).setDirty();
    }

    public boolean canMoveUp() {
        return getQueryDataModel().getQueries().indexOf(this) != 0;
    }

    public boolean canMoveDown() {
        return getQueryDataModel().getQueries().indexOf(this) + 1 < getQueryDataModel()
                .getQueries().size();
    }

}
