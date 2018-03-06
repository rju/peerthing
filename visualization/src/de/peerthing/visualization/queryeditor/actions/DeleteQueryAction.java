package de.peerthing.visualization.queryeditor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.visualization.querymodel.interfaces.IQuery;
import de.peerthing.visualization.querymodel.interfaces.IQueryDataModel;

/**
 * Popup menu action that deletes the currently selected
 * query object.
 * 
 * @author Michael Gottschalk
 *
 */
public class DeleteQueryAction extends AbstractTreeAction {

    public void run(IAction action) {
        if (!(firstSelectedObject instanceof IQuery)) {
            return;
        }

        IQueryDataModel model = null;
        for (Object queryObj : selectedObjects) {
            if (!(queryObj instanceof IQuery)) {
                continue;
            }

            IQuery query = (IQuery) queryObj;
            model = query.getQueryDataModel();
            query.getQueryDataModel().getQueries().remove(query);

            // Notify the editor that the object is deleted
            modelObjectDeleted(query);
        }

        if (model != null) {
            getTree().refresh(model.getFile());
        }

    }

}
