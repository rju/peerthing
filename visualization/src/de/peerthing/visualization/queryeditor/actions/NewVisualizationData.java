package de.peerthing.visualization.queryeditor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.visualization.querymodel.VisualizationData;
import de.peerthing.visualization.querymodel.interfaces.IListWithParent;
import de.peerthing.visualization.querymodel.interfaces.IQuery;
import de.peerthing.visualization.querymodel.interfaces.IVisualizationData;

/**
 * Popup menu action that creates a new VisualizationData
 * object as a child of the currently selected query object.
 *
 * @author Michael Gottschalk
 *
 */
public class NewVisualizationData extends AbstractTreeAction {

    public void run(IAction action) {
        if (!(firstSelectedObject instanceof IQuery)) {
            return;
        }
        
        IListWithParent<IVisualizationData> list = ((IQuery) firstSelectedObject).getVisualizationData();
        
        IVisualizationData vis = new VisualizationData((IQuery) list.getParent());
        list.add(vis);
        
        getTree().refresh(firstSelectedObject);
        getTree().expandToLevel(firstSelectedObject, 1);
        modelChanged((IQuery) firstSelectedObject);
    }
}
