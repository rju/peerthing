package de.peerthing.visualization.querymodel.interfaces;

/**
 * Includes the information for displaying a visualization from a database
 * query. This includes the query itself, the diagram type to display, the axis
 * labels, a name and information about aggregation.
 * 
 * 
 * @author Michael Gottschalk
 * 
 */
public interface IVisualizationData extends IQueryModelObject {
    /**
     * The possible diagram styles
     */
    public enum Styles {
        line, multiline, boxplot, scatter, table, bar
    }

    /**
     * Returns the X-Axis label of the diagram
     * 
     * @return the X-Axis-Label
     */
    public String getXAxisLabel();

    /**
     * Sets the Label for the X-Axis of the diagram
     * 
     * @param s
     */
    public void setXAxisLabel(String s);

    /**
     * Returns the Y-Axis label of the diagram
     * 
     * @return the Y-Axis-Label
     */
    public String getYAxisLabel();

    /**
     * sets the Label for the Y-Axis of the diagram
     * 
     * @param s
     *            the name of the y-axis
     */
    public void setYAxisLabel(String s);

    /**
     * Sets the style of the visualization (i.e. the diagram type)
     * 
     * @param style
     */
    public void setStyle(Styles style);

    /**
     * Returns the type of the visualization.
     * 
     * @return style
     * @see Styles
     */
    public Styles getStyle();

    /**
     * Returns the SQL-statement that is supposed to return a certain number of
     * columns needed for the selected style. Can be an empty String, but not
     * null.
     * 
     */
    public String getDataQuery();

    /**
     * Sets the SQL-statement needed for the visualization.
     * 
     * @param dq
     */
    public void setDataQuery(String dq);

    /**
     * Sets whether the data should be normalized or not.
     * 
     * @param normalized
     */
    public void setNormalized(boolean normalized);

    /**
     * Returns whether the data should be normalized or not.
     * 
     * @return true if normalized
     */
    public boolean isNormalized();

    /**
     * Returns the query to which this visualization information belongs.
     * 
     * @return query theIQuery-Parent-Object
     */
    public IQuery getQuery();

    /**
     * Returns the name of this visualization.
     * 
     * @return String, not null
     */
    public String getName();

    /**
     * Sets the name of this visualization.
     * 
     * @param name
     */
    public void setName(String name);
}
