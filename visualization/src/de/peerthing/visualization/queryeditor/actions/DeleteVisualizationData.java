package de.peerthing.visualization.queryeditor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.visualization.querymodel.interfaces.IQuery;
import de.peerthing.visualization.querymodel.interfaces.IVisualizationData;

/**
 * Popup menu action that deletes the currently 
 * selected IVisualizationData object.
 * 
 * @author Michael Gottschalk
 *
 */
public class DeleteVisualizationData extends AbstractTreeAction {

    public void run(IAction action) {
        if (!(firstSelectedObject instanceof IVisualizationData)) {
            return;
        }

        IQuery query = null;
        for (Object visObj : selectedObjects) {
            if (!(visObj instanceof IVisualizationData)) {
                continue;
            }

            IVisualizationData vis = (IVisualizationData) visObj;
            query = vis.getQuery();
            query.getVisualizationData().remove(vis);

            // Notify the editor that the object is deleted
            modelObjectDeleted(vis);
        }

        if (query != null) {
            getTree().refresh(query);
        }
    }

}
