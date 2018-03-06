package de.peerthing.visualization.simulationpersistence;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import de.peerthing.visualization.VisualizationPlugin;

/**
 * Popup menu action that exports the currently
 * selected database as CSV files.
 * 
 * @author Michael Gottschalk
 *
 */
public class ExportDatabaseAction implements IObjectActionDelegate {
    private DBinterface db = null;

    private int simRun = -1;

    /**
     * Delimiter of the CSV files
     */
    private String delim = ",";

    private Shell parent;

    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        parent = targetPart.getSite().getShell();
    }

    public void run(IAction action) {
        if (db == null) {
            return;
        }

        DirectoryDialog d = new DirectoryDialog(parent);
        d.setMessage("Select the directory in which"
                + " the CSV files should be saved.");
        String dir = d.open();
        if (dir == null) {
            return;
        }

        DBTools.exportDatabaseAsCSVFiles(db, dir, simRun, delim);
    }

    public void selectionChanged(IAction action, ISelection selection) {
        if (selection instanceof IStructuredSelection) {
            Object selected = ((IStructuredSelection) selection)
                    .getFirstElement();
            action.setEnabled(true);

            if (selected instanceof SimulationRunMetadata) {
                db = ((SimulationRunMetadata) selected).getLogFile().getDb();
                simRun = ((SimulationRunMetadata) selected).getRunNumber();
            } else if (selected instanceof IFile) {
                if (VisualizationPlugin.getDefault().isDatabaseSelected(
                        (IFile) selected)) {
                    db = VisualizationPlugin.getDefault().getDB(
                            (IFile) selected);
                    simRun = -1;
                } else {
                    action.setEnabled(false);
                }
            } else {
                db = null;
                simRun = -1;
            }
        }
    }

}
