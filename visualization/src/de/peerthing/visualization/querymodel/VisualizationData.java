package de.peerthing.visualization.querymodel;

import de.peerthing.visualization.VisualizationPlugin;
import de.peerthing.visualization.querymodel.interfaces.IQuery;
import de.peerthing.visualization.querymodel.interfaces.IQueryDataModel;
import de.peerthing.visualization.querymodel.interfaces.IVisualizationData;
import de.peerthing.workbench.filetyperegistration.IUpDownMovable;

/**
 * Default implementation of IVisualizationData. See the interface for a
 * description.
 * 
 * @author Michael Gottschalk
 * 
 */
public class VisualizationData implements IVisualizationData, IUpDownMovable {
    private boolean normalized;

    private String dataQuery = "";

    Styles style;

    private String xLabel = "X-Axis";

    private String yLabel = "Y-Axis";

    private String name = "";

    private IQuery query;

    /**
     * Constructs a new VisualizationData with the given parent. The Style
     * defaults to Styles.line
     * 
     * @param parent
     */
    public VisualizationData(IQuery parent) {
        this.query = parent;
        style = Styles.line;
    }

    /**
     * Copy constructor. The parent query is set to the given query object.
     * 
     * @param copy
     *            the object to copy
     * @param parent
     *            the parent query
     */
    public VisualizationData(VisualizationData copy, IQuery parent) {
        normalized = copy.normalized;
        dataQuery = copy.dataQuery;
        style = copy.style;
        xLabel = copy.xLabel;
        yLabel = copy.yLabel;
        name = copy.name;
        query = parent;
    }

    public String getDataQuery() {
        return (dataQuery == null) ? "" : dataQuery;
    }

    public void setStyle(Styles s) {
        style = s;
    }

    public Styles getStyle() {
        return style;
    }

    public void setDataQuery(String dataQuery) {
        this.dataQuery = dataQuery;
    }

    public void setNormalized(boolean normalized) {
        this.normalized = normalized;
    }

    public boolean isNormalized() {
        return normalized;
    }

    public IQuery getQuery() {
        return query;
    }

    public IQueryDataModel getQueryDataModel() {
        return query.getQueryDataModel();
    }

    /**
     * Returns the name or, if the name is empty, the name of the selected
     * style.
     */
    public String toString() {
        return name.equals("") ? getNameOfStyle(style) : name;
    }

    /**
     * Returns the name of the chart type, e.g. "Bar chart" for type "bar"
     * 
     * @param style
     * @return
     */
    public static String getNameOfStyle(Styles style) {
        if (style == Styles.boxplot) {
            return "Box Plot";
        }
        if (style == Styles.bar) {
            return "Bar Chart";
        }
        if (style == Styles.scatter) {
            return "Scatter Plot";
        }
        if (style == Styles.line) {
            return "Line Chart";
        }
        if (style == Styles.multiline) {
            return "Multi Line Chart";
        }
        if (style == Styles.table) {
            return "Table";
        }

        return style.toString() + " diagram";
    }

    /**
     * Returns the file name of the icon for the given style, relative to the
     * icons-directory of this plug-in.
     * 
     */
    public static String getIconFileForStyle(Styles style) {
        if (style == Styles.line) {
            return "linediagram.gif";
        } else if (style == Styles.multiline) {
            return "multilinediagram.gif";
        } else if (style == Styles.boxplot) {
            return "boxplot.gif";
        } else if (style == Styles.scatter) {
            return "scatterdiagram.gif";
        } else if (style == Styles.bar) {
            return "barchart.gif";
        } else if (style == Styles.table) {
            return "table.gif";
        }

        return "";
    }

    public String getXAxisLabel() {
        return xLabel;
    }

    public void setXAxisLabel(String s) {
        xLabel = s;

    }

    public String getYAxisLabel() {
        return yLabel;
    }

    public void setYAxisLabel(String s) {
        this.yLabel = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            this.name = "";
        } else {
            this.name = name;
        }
    }

    /**
     * Moves this visualization one position up
     * in the array of the parent query.
     * Refreshes the resource tree and marks the
     * editor dirty.
     */
    public void moveUp() {
        int oldIndex = getQuery().getVisualizationData().indexOf(this);
        getQuery().getVisualizationData().remove(this);
        getQuery().getVisualizationData().add(oldIndex - 1, this);
        VisualizationPlugin.getDefault().getNavigationTree()
                .refresh(getQuery());
        VisualizationPlugin.getDefault().getQueryFiletypeRegistration()
                .getEditor(getQueryDataModel().getFile()).setDirty();
    }

    /**
     * Moves this visualization one position down
     * in the array of the parent query.
     * Refreshes the resource tree and marks the
     * editor dirty.
     */
    public void moveDown() {
        int oldIndex = getQuery().getVisualizationData().indexOf(this);
        getQuery().getVisualizationData().remove(this);
        getQuery().getVisualizationData().add(oldIndex + 1, this);
        VisualizationPlugin.getDefault().getNavigationTree()
                .refresh(getQuery());
        VisualizationPlugin.getDefault().getQueryFiletypeRegistration()
                .getEditor(getQueryDataModel().getFile()).setDirty();
    }

    public boolean canMoveUp() {
        return getQuery().getVisualizationData().indexOf(this) != 0;
    }

    public boolean canMoveDown() {
        return getQuery().getVisualizationData().indexOf(this) + 1 < getQuery()
                .getVisualizationData().size();
    }

}
