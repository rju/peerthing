package de.peerthing.visualization.view.selection;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import de.peerthing.visualization.VisualizationPlugin;

public class ShowTablesToggleAction implements IViewActionDelegate {

    public void init(IViewPart view) {
    }

    public void run(IAction action) {
        VisualizationPlugin.getDefault().setShowTables(!action.isChecked());
    }

    public void selectionChanged(IAction action, ISelection selection) {
    }

}
