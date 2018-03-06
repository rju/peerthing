package de.peerthing.visualization.view.selection;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import de.peerthing.visualization.VisualizationPlugin;
import de.peerthing.visualization.simulationpersistence.DBinterface;

/**
 * Removes the current selection in the selection view from the list of selected
 * databases or simulation runs.
 * 
 * @author Michael Gottschalk
 * 
 */
public class RemoveFromSelectionAction implements IViewActionDelegate {
    SimDataSelectionView view = null;

    public void init(IViewPart view) {
        if (view instanceof SimDataSelectionView) {
            this.view = (SimDataSelectionView) view;
        }
    }

    public void run(IAction action) {
        Object[] selectedObjects = view.getCurrentSelection();

        if (selectedObjects != null) {
            for (Object selected : selectedObjects) {
                if (selected instanceof DBinterface) {
                    DBinterface db = (DBinterface) selected;
                    db.shutdown();

                    VisualizationPlugin.getDefault().removeDB(db);
                    VisualizationPlugin.getDefault().getNavigationTree()
                            .collapseToLevel(db.getDatabase(), 1);

                } else if (selected instanceof SelectedSimulationRun) {
                    DBinterface db = ((SelectedSimulationRun) selected).getDB();
                    int run = ((SelectedSimulationRun) selected).getRunNumber();
                    db.getSelectedRuns().remove((Object) new Integer(run));

                    if (db.getSelectedRuns().size() == 0) {
                        db.shutdown();
                        VisualizationPlugin.getDefault().removeDB(db);
                        VisualizationPlugin.getDefault().getNavigationTree()
                                .collapseToLevel(db.getDatabase(), 1);
                    }
                }
            }

            // Update the database selection view
            view.selectedDatabasesChanged();

            // Update the chart view
            if (VisualizationPlugin.getDefault().getChartView() != null) {
                VisualizationPlugin.getDefault().getChartView().updateAll();
            }

        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
        if (view != null) {
            if (view.getCurrentSelection() != null) {
                action.setEnabled(true);
                return;
            }
        }
        action.setEnabled(false);
    }

}
