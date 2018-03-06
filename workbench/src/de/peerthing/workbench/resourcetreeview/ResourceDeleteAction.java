package de.peerthing.workbench.resourcetreeview;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Deletes the selected resources in the resource tree. Also deletes additional
 * files of a hsql database since these are not shown in the resource view.
 * 
 * @author Michael Gottschalk
 * 
 */
public class ResourceDeleteAction extends AbstractResourceTreeAction {
    public void run(IAction action) {
        if (!MessageDialog.openConfirm(view.getSite().getShell(),
                "Confirm Deletion", "Do you really want to delete all "
                        + "selected resources?")) {
            return;
        }

        for (Object item : getSelectedItems()) {
            if (item instanceof IResource) {
                try {
                    IResource res = (IResource) item;

                    // If a user wants to delete a database,
                    // also delete all files that belong
                    // to the database, but are not displayed
                    // in the resource view:
                    if (res.getFileExtension() != null
                            && res.getFileExtension().equals("simdata")) {
                        IContainer parent = res.getParent();
                        String[] extensions = new String[] { "script",
                                "properties", "log", "lck", "data", "backup" };
                        for (String ext : extensions) {
                            IResource r = parent.findMember(res.getName() + "."
                                    + ext);
                            if (r != null) {
                                r.delete(true, null);
                            }
                        }

                    }

                    res.delete(true, null);
                } catch (CoreException e) {
                    MessageDialog.openError(view.getSite().getShell(), "Error", "Could not delete resource " + item
                            + ": " + e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}
