/*
 * Created on 23.04.2006
 *
 */
package de.peerthing.workbench.resourcetreeview;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;

/**
 * Popup menu action that renames the currently selected file or folder in the
 * resource tree. The user can enter the new name in an InputDialog.
 * 
 * @author Michael Gottschalk
 * 
 */
public class RenameAction extends AbstractResourceTreeAction {

    public void run(IAction action) {
        for (Object item : getSelectedItems()) {
            if (!(item instanceof IResource)) {
                continue;
            }
            IResource res = (IResource) item;

            InputDialog dialog = new InputDialog(view.getViewSite().getShell(),
                    "Rename", "Enter the new name for the file:",
                    res.getName(), null);
            int result = dialog.open();
            if (result == Window.CANCEL) {
                continue;
            }

            try {
                res.move(new Path(dialog.getValue()), false, null);
            } catch (Exception e) {
                MessageDialog.openError(view.getViewSite().getShell(), "Error",
                        "The file could not be renamed: " + e.getMessage());
            }

        }
    }

}
