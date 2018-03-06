/*
 * Created on 12.07.2006
 *
 */
package de.peerthing.visualization.queryeditor.actions;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;

import de.peerthing.visualization.VisualizationPlugin;
import de.peerthing.visualization.querymodel.Query;
import de.peerthing.visualization.querymodel.VisualizationData;
import de.peerthing.visualization.querymodel.interfaces.IQuery;
import de.peerthing.visualization.querymodel.interfaces.IQueryDataModel;
import de.peerthing.visualization.querymodel.interfaces.IVisualizationData;

/**
 * Pastes copies of objects that were currently copied to the local clipboard
 * into the current selection. Depending on the selection, only the fitting
 * objects are inserted.
 * 
 * @author Michael Gottschalk
 * 
 */
public class PasteAction extends AbstractTreeAction {

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        super.selectionChanged(action, selection);

        // Enable for the insertion of IVisualizationData objects
        boolean enabled = false;
        if ((firstSelectedObject instanceof IVisualizationData || firstSelectedObject instanceof IQuery)) {
            List<Object> copiedObjects = VisualizationPlugin.getDefault()
                    .getCopiedObjects();
            for (Object obj : copiedObjects) {
                if (obj instanceof IVisualizationData) {
                    enabled = true;
                }
            }
        }

        // Enable for the insertion of query objects...
        if (!enabled
                && (firstSelectedObject instanceof IQuery || firstSelectedObject instanceof IFile)) {
            List<Object> copiedObjects = VisualizationPlugin.getDefault()
                    .getCopiedObjects();
            for (Object obj : copiedObjects) {
                if (obj instanceof IQuery) {
                    enabled = true;
                }
            }
        }

        action.setEnabled(enabled);
    }

    public void run(IAction action) {
        List<Object> copiedObjects = VisualizationPlugin.getDefault()
                .getCopiedObjects();
        for (Object cObj : copiedObjects) {
            if ((firstSelectedObject instanceof IQuery || firstSelectedObject instanceof IVisualizationData)
                    && cObj instanceof IVisualizationData) {
                IQuery parent = null;
                if (firstSelectedObject instanceof IQuery) {
                    parent = (IQuery) firstSelectedObject;
                } else {
                    parent = ((IVisualizationData) firstSelectedObject)
                            .getQuery();
                }

                IVisualizationData newVis = new VisualizationData(
                        (VisualizationData) cObj, parent);
                parent.addVisualizationData(newVis);
                getTree().refresh(parent);
                modelChanged(newVis);
            } else if (cObj instanceof IQuery
                    && (firstSelectedObject instanceof IQuery || firstSelectedObject instanceof IFile)) {
                IQueryDataModel parent = null;
                
                // Get the parent for the pasted object
                // from the current selection
                if (firstSelectedObject instanceof IQuery) {
                    parent = ((IQuery) firstSelectedObject).getQueryDataModel();
                } else {
                    parent = getFiletypeRegistration().getDataModel((IFile) firstSelectedObject);
                }
                if (parent == null) {
                    continue;
                }
                
                IQuery newQuery = new Query((Query) cObj, parent);
                parent.getQueries().add(newQuery);
                getTree().refresh(parent.getFile());
                modelChanged(newQuery);
            }
        }
    }

}
