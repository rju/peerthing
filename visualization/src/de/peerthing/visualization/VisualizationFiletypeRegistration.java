package de.peerthing.visualization;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;


import de.peerthing.visualization.querypersistence.QueryPersistence;
import de.peerthing.visualization.simulationpersistence.BasicQueries;
import de.peerthing.visualization.simulationpersistence.DBinterface;
import de.peerthing.visualization.simulationpersistence.LogFileMetadata;
import de.peerthing.visualization.simulationpersistence.SimulationRunMetadata;
import de.peerthing.visualization.view.selection.SelectionView;
import de.peerthing.workbench.filetyperegistration.IFileTypeRegistration;
import de.peerthing.workbench.filetyperegistration.INavigationTree;

/**
 * Registers the filetypes simdata and qdef for the simulation view at the
 * peerting workbench.
 * 
 * @author Michael Gottschalk
 * 
 */
public class VisualizationFiletypeRegistration implements IFileTypeRegistration {
    private INavigationTree tree;

    public String[] getFileNameEndings() {
        return new String[] { "simdata", "qdef" };
    }

    public boolean wantsToBeDefaultEditor() {
        return true;
    }

    public String getComponentName() {
        return "Visualization";
    }

    /**
     * Shows the simulation perspective and opens the input files.
     * 
     */
    public void showComponent(IFile[] inputFiles) {
        try {
            PlatformUI.getWorkbench().showPerspective(
                    "de.peerthing.VisualizationPerspective",
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow());
        } catch (Exception e) {
            System.out.println("Could not show visualization perspective: ");
            e.printStackTrace();
        }

        for (final IFile file : inputFiles) {
            if (file.getFileExtension().equals("simdata")) {
                try {
                    IProgressService progressService = PlatformUI.getWorkbench()
                    .getProgressService();
                    try {
                        progressService.busyCursorWhile(new IRunnableWithProgress() {
                            public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                                monitor.beginTask("Opening Database", IProgressMonitor.UNKNOWN);
                                VisualizationPlugin.getDefault().getDB(file);
                            }
                        });
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e.getCause());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e.getCause());
                    }
                    
                } catch (RuntimeException e) {
                    MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
                            "Error opening the database", 
                            "The database could not be opened. It is probably already open in a " +
                            "different instance of the program:\n"+ e.getMessage());
                    e.printStackTrace();
                    return;
                }
                tree.refresh(file);
                tree.expandToLevel(file, 1);

                // Update the chart view
                if (VisualizationPlugin.getDefault().getChartView() != null) {
                    VisualizationPlugin.getDefault().getChartView()
                            .updateAll();
                }
                if (VisualizationPlugin.getDefault().getSimDataSelView() != null) {
                    VisualizationPlugin.getDefault().getSimDataSelView().selectedDatabasesChanged();
                }
            } else if (file.getFileExtension().equals("qdef")) {
                QueryPersistence wp = new QueryPersistence();
                SelectionView selview = VisualizationPlugin.getDefault()
                        .getSelectionView();
                if (selview != null) {
                    selview.updateQDM(wp.loadQueries(file), true, true);
                }
            }
        }
    }

    public void setNavigationTree(INavigationTree navigationTree) {
        this.tree = navigationTree;
    }

    /**
     * not used
     */
    public void subTreeElementSelected(Object subTreeElement) {
    }

    /**
     * not used
     */
    public void subTreeElementDoubleClicked(Object subTreeElement) {
    }

    /**
     * Opens the database included in the given file and creates a
     * LogFileMetadata object with the information in the database.
     * 
     * @param file
     *            the file in which the database is stored
     * @return Metadata about the database
     */
    private LogFileMetadata getLogFile(IFile file) {
        LogFileMetadata logFile = null;

        if (logFile == null) {
            logFile = new LogFileMetadata();
            logFile.setLogFile(file);
            DBinterface db = VisualizationPlugin.getDefault().getDB(file);

            logFile.setDb(db);
            int runNumber = BasicQueries.getNumberOfSimulationRuns(db);

            if (runNumber > 0) {
                int[] runs = BasicQueries.getIncludedSimulationRuns(db);
                
                db.getSelectedRuns().clear();
                
                for (int runNo : runs) {
                    SimulationRunMetadata run = new SimulationRunMetadata(
                            runNo, logFile);
                    
                    logFile.getRuns().add(run);
                    
                    // Set all runs as selected at first...
                    db.getSelectedRuns().add(runNo);
                }
            } else {
                System.out.println("No tables exist!");
            }
            
            // Update the selection view
            if (VisualizationPlugin.getDefault().getSimDataSelView() != null) {
                VisualizationPlugin.getDefault().getSimDataSelView().selectedDatabasesChanged();
            }
        }

        return logFile;
    }

    /**
     * For a simdata file, returns an array of SimulationRunMetadata objects
     * that can be displayed in the resource tree.
     * 
     */
    public Object[] getTreeElements(IFile file) {
        return getLogFile(file).getRuns().toArray();
    }

    /**
     * Returns a content provider for simdata-files that does not return
     * anything since only one level of objects has to be shown in the tree, and
     * these are returned by getTreeElements().
     */
    public ITreeContentProvider getSubtreeContentProvider() {
        return new ITreeContentProvider() {

            public Object[] getChildren(Object parentElement) {
                return null;
            }

            public Object getParent(Object element) {
                return null;
            }

            public boolean hasChildren(Object element) {
                return false;
            }

            public Object[] getElements(Object inputElement) {
                return null;
            }

            public void dispose() {
            }

            public void inputChanged(Viewer viewer, Object oldInput,
                    Object newInput) {
            }
        };
    }

    /**
     * Returns a simple label provider that only calls the toString method on
     * all objects.
     */
    public ILabelProvider getSubtreeLabelProvider() {
        return new LabelProvider() {
            @Override
            public String getText(Object element) {
                return element.toString();
            }
        };
    }

    public boolean canHandleSubtreeObject(Object obj) {
        return (obj instanceof SimulationRunMetadata);
    }

    /**
     * Returns true for simdata files.
     */
    public boolean hasSubTree(IFile file) {
        if (file.getFileExtension() != null
                && file.getFileExtension().equals("simdata")) {
            return true;
        }
        return false;
    }

    /**
     * No new files can be created by the simulation (only for the query editor,
     * which has its own filetype registration).
     */
    public String[] getNewFileDefinition() {
        return null;
    }

}
