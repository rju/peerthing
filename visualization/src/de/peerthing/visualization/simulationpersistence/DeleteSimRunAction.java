package de.peerthing.visualization.simulationpersistence;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import de.peerthing.visualization.VisualizationPlugin;

/**
 * Popup menu action that deletes the currently selected 
 * simulation run from the database to which it belongs.
 * 
 * @author Michael Gottschalk
 *
 */
public class DeleteSimRunAction implements IObjectActionDelegate {
    List<SimulationRunMetadata> simRuns = new ArrayList<SimulationRunMetadata>();

    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
    }

    public void run(IAction action) {
        for (SimulationRunMetadata simRun : simRuns) {
            DBinterface db = simRun.getLogFile().getDb();

            if (VisualizationPlugin.getDefault().isDatabaseSelected(
                    db.getDatabase())) {

                BasicQueries.removeSimulationRun(db, simRun.getRunNumber());

                VisualizationPlugin.getDefault().getNavigationTree().refresh(
                        simRun.getLogFile().getLogFile());
            }
        }

        // Update the chart if one is shown
        if (VisualizationPlugin.getDefault().getChartView() != null) {
            VisualizationPlugin.getDefault().getChartView().updateAll();
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
        if (selection instanceof IStructuredSelection) {
            simRuns.clear();
            for (Object obj : ((IStructuredSelection) selection).toList()) {
                if (obj instanceof SimulationRunMetadata) {
                    simRuns.add((SimulationRunMetadata) obj);
                }
            }
        }
    }

}
