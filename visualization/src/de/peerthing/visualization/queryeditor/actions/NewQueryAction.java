package de.peerthing.visualization.queryeditor.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.ui.PlatformUI;

import de.peerthing.visualization.querymodel.Query;
import de.peerthing.visualization.querymodel.interfaces.IQueryDataModel;

/**
 * Popup menu action that creates a new query.
 * The user is asked for a name in an InputDialog.
 * 
 * @author Michael Gottschalk
 *
 */
public class NewQueryAction extends AbstractTreeAction {

    public void run(IAction action) {
        if (firstSelectedObject instanceof IFile) {
            IQueryDataModel model = getFiletypeRegistration().loadDataModel((IFile) firstSelectedObject);
            if (model != null) {
                InputDialog dialog = new InputDialog(PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getShell(), "Create new Query",
                        "Enter the name of the query:", null, null);
                if (dialog.open() == InputDialog.CANCEL) {
                    return;
                }

                String queryName = dialog.getValue();
                Query q = new Query(model);
                q.setName(queryName);

                System.out.println(model.getQueries().size());
                model.getQueries().add(q);
                System.out.println(model.getQueries().size());
                getTree().refresh(firstSelectedObject);
                getTree().expandToLevel(firstSelectedObject, 1);

                modelChanged(model.getQueries());
            }
        }
    }

}
