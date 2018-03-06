package de.peerthing.visualization.querymodel.interfaces;


/**
 * Represents a user-defined query that can include any
 * number of visualizations. This can also be considered
 * a query category and the visualizations can be seen
 * as concrete queries. The sql statements defined here
 * are only preparing queries that all visualizations
 * for this query need.
 * 
 * @author gom, Michael Gottschalk
 *
 */
public interface IQuery extends IQueryModelObject {
	
	/**
	 * returns SQL-Statements 
     * that prepare the data for visualization, e.g.
     * creating temporary tables or an empty String, never null
     * 
	 * @return String SQL-Statements
	 */
	public String getPreparingQueries();
	
    /**
     * Sets SQL-Statements 
     * that prepare the data for visualization, e.g.
     * creating temporary tables. Can be more than one
     * statement (separated with ";")
     * 
     * @param sqlQueries
     */
    public void setPreparingQueries(String sqlQueries);
	
    /**
     * Adds a visualization to the list of visualizations
     * for this query.
     * 
     * @param vd
     */
	public void addVisualizationData(IVisualizationData vd);
    
    /**
     * Removes the given visualization from the list of
     * visualizations for this query.
     * 
     * @param vd
     */
	public void removeVisualizationData(IVisualizationData vd);
    
    /**
     * Returns the list of visualizations for this query.
     * 
     * @return
     */
	public IListWithParent<IVisualizationData> getVisualizationData();
	
    /**
     * Returns the nth visualization for this query.
     * 
     * @param n
     * @return
     */
    public IVisualizationData getVisualizationData(int n);
	
	/**
	 * Returns the Name of the Query-Object or an 
     * empty String, never null
	 * @return the name
	 */
	public String getName();
    
    /**
     * Sets the name of the query.
     * @param string
     */
	public void setName(String string);
}
