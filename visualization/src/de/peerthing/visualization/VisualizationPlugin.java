package de.peerthing.visualization;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.*;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.BundleContext;


import de.peerthing.visualization.queryeditor.QueryFiletypeRegistration;
import de.peerthing.visualization.simulationpersistence.DBinterface;
import de.peerthing.visualization.view.charts.ChartView;
import de.peerthing.visualization.view.selection.SelectionView;
import de.peerthing.visualization.view.selection.SimDataSelectionView;
import de.peerthing.workbench.filetyperegistration.INavigationTree;

/**
 * The main class for the Visualization plugin. This is a singleton, so it is
 * used whenever a component needs global variables in the plug-in.
 *
 *
 */
public class VisualizationPlugin extends AbstractUIPlugin {
    /**
     * The selection view. There exists exactly one instance of this view in the
     * plug-in.
     */
    private SelectionView selview;

    /**
     * The resource tree from the resource view.
     */
    private INavigationTree navigationTree;
    
    /**
     * The chart view. There should be exactly one instance
     * of the chart view.
     */
    private ChartView chartView;
    
    /**
     * Specifies if tables additional to the charts
     * should be shown or not.
     */
    private boolean showTables = true;

    /**
     * The simulation data selection view. There exists exactly
     * one instance of this view in the plug-in.
     */
    private SimDataSelectionView simDataSelView;
    
    /**
     * The filetype registration registered at the PeerThing Workbench.
     *
     */
    private QueryFiletypeRegistration filetypeReg;

    /**
     * DB connections that are currently open.
     */
    private List<DBinterface> selectedDatabases = new ArrayList<DBinterface>();

    /**
     * Icons that can be used in this plug-in.
     */
    private Hashtable<String, Image> icons = new Hashtable<String, Image>();

    /**
     * A clipboard of objects that were copied in the query editor and can later
     * be pasted into it.
     *
     */
    private List<Object> copiedObjects = new ArrayList<Object>();

    /**
     * The singleton instance of this plug-in
     */
    private static VisualizationPlugin plugin;

    /**
     * The constructor. This should only be called by the eclipse framework. Use
     * getDefault() instead!
     *
     */
    public VisualizationPlugin() {
        plugin = this;
    }

    /**
     * Returns the database that are currently open.
     *
     * @return
     */
    public List<DBinterface> getCurrentlySelectedDatabases() {
        return selectedDatabases;
    }

    /**
     * Returns if a database with the given filename is currently selected (i.e.
     * open).
     *
     * @param filename
     * @return
     */
    public boolean isDatabaseSelected(IFile filename) {
        for (DBinterface db : selectedDatabases) {
            if (db.getDatabase().equals(filename)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the DB interface that belongs to the given filename. If this
     * object already exists, it is returned. Else, a new DBinterface object is
     * created and appended to the list of currently selected databases.
     *
     * @param filename
     * @return
     */
    public DBinterface getDB(IFile filename) {
        for (DBinterface db : selectedDatabases) {
            if (db.getDatabase().equals(filename)) {
                return db;
            }
        }

        DBinterface res = new DBinterface(filename);
        selectedDatabases.add(res);

        return res;
    }

    /**
     * Removes the given database from the list of selected databases.
     *
     * @param db
     */
    public void removeDB(DBinterface db) {
        selectedDatabases.remove(db);
    }

    /**
     * This method is called upon plug-in activation
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
    }

    /**
     * This method is called when the plug-in is stopped. Shuts down currently
     * open databases and disposes of icons that were formerly opened.
     *
     */
    public void stop(BundleContext context) throws Exception {
        // shut down current databases...
        for (DBinterface db : selectedDatabases) {
            if (!db.isShutDown()) {
                db.shutdown();
            }
        }

        // dispose of the icons
        for (Image icon : icons.values()) {
            icon.dispose();
        }

        // Inform the workspace of changes in the db files
        ResourcesPlugin.getWorkspace().getRoot().refreshLocal(2, null);

        super.stop(context);
        plugin = null;
    }

    /**
     * Returns the shared instance.
     */
    public static VisualizationPlugin getDefault() {
        return plugin;
    }

    /**
     * Returns a reference to the resource tree viewer.
     *
     * @return
     */
    public INavigationTree getNavigationTree() {
        return navigationTree;
    }

    /**
     * Sets the resource tree viewer.
     *
     * @param tree
     */
    public void setNavigationTree(INavigationTree tree) {
        navigationTree = tree;
    }

    /**
     * Sets the filetype registration that is registered at the peerthing
     * workbench.
     *
     * @param filetypereg
     */
    public void setQueryFiletypeRegistration(
            QueryFiletypeRegistration filetypereg) {
        this.filetypeReg = filetypereg;
    }

    /**
     * Returns the filetype registration that is registered at the peerthing
     * workbench.
     *
     * @return
     */
    public QueryFiletypeRegistration getQueryFiletypeRegistration() {
        return filetypeReg;
    }

    /**
     * Returns a reference to the selection view that was created by the
     * workbench.
     *
     * @return
     */
    public SelectionView getSelectionView() {
        return selview;
    }

    /**
     * Sets the reference to the selection view that was created by the
     * workbench.
     *
     * @return
     */
    public void setSelectionView(SelectionView selview) {
        this.selview = selview;
    }

    /**
     * Returns the icon with the given filename that must be located in the
     * icons/ directory of this plug-in. The icons are cached internally in a
     * hashtable and are disposed upon shutdown of the plug-in.
     *
     * @param name
     * @return
     */
    public Image getIcon(String name) {
        String iconPath = "icons/";
        URL pluginUrl = getBundle().getEntry("/");
        ImageDescriptor desc = null;
        try {
            desc = ImageDescriptor.createFromURL(new URL(pluginUrl, iconPath
                    + name));
        } catch (MalformedURLException e) {
            desc = ImageDescriptor.getMissingImageDescriptor();
        }

        if (icons.containsKey(name)) {
            return icons.get(name);
        } else {
            Image img = desc.createImage();
            icons.put(name, img);
            return img;
        }
    }

    /**
     * Returns the list of objects that were copied to the "clipboard" of the
     * query editor.
     *
     * @return the list of copied objects, never null
     */
    public List<Object> getCopiedObjects() {
        return copiedObjects;
    }

    /**
     * Returns the simulation data selection view
     * @return
     */
    public SimDataSelectionView getSimDataSelView() {
        return simDataSelView;
    }

    /**
     * Sets the simulation data selection view
     * @param simDataSelView
     */
    public void setSimDataSelView(SimDataSelectionView simDataSelView) {
        this.simDataSelView = simDataSelView;
    }

    /**
     * Returns the chart view.
     * @return
     */
    public ChartView getChartView() {
        return chartView;
    }

    /**
     * Sets the chart view.
     * @param chartView
     */
    public void setChartView(ChartView chartView) {
        this.chartView = chartView;
    }

    /**
     * Returns if tables should be shown additional to
     * the charts.
     * 
     * @return
     */
    public boolean isShowTables() {
        return showTables;
    }

    public void setShowTables(boolean showTables) {
        this.showTables = showTables;
    }
}
