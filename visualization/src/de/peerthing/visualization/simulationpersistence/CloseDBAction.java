package de.peerthing.visualization.simulationpersistence;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import de.peerthing.visualization.VisualizationPlugin;

/**
 * Popup menu action defined for simdata-files. It closes the database included
 * in the selected file if it is currently opened.
 * 
 * @author Michael Gottschalk
 * 
 */
public class CloseDBAction implements IObjectActionDelegate {
    IFile selectedFile = null;

    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
    }

    public void run(IAction action) {
        if (selectedFile != null) {
            DBinterface db = VisualizationPlugin.getDefault().getDB(
                    selectedFile);
            db.shutdown();
            VisualizationPlugin.getDefault().removeDB(db);
            VisualizationPlugin.getDefault().getNavigationTree()
                    .collapseToLevel(selectedFile, 1);
            // Update the chart if one is shown
            if (VisualizationPlugin.getDefault().getChartView() != null) {
                VisualizationPlugin.getDefault().getChartView().updateAll();
            }
            // Update the database selection view
            if (VisualizationPlugin.getDefault().getSimDataSelView() != null) {
                VisualizationPlugin.getDefault().getSimDataSelView()
                        .selectedDatabasesChanged();
            }
        }
    }

    /**
     * The action is only enabled if the currently selected database is opened.
     * 
     */
    public void selectionChanged(IAction action, ISelection selection) {
        if (selection instanceof IStructuredSelection) {
            Object obj = ((IStructuredSelection) selection).getFirstElement();
            if (obj instanceof IFile) {
                IFile file = (IFile) obj;
                if (file.getFileExtension() != null
                        && file.getFileExtension().equals("simdata")) {
                    if (VisualizationPlugin.getDefault().isDatabaseSelected(
                            file)) {
                        selectedFile = file;
                        action.setEnabled(true);
                    } else {
                        action.setEnabled(false);
                    }
                }
            }
        }
    }

}
