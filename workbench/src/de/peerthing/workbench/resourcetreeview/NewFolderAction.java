/*
 * Created on 13.04.2006
 *
 */
package de.peerthing.workbench.resourcetreeview;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.ui.PlatformUI;

public class NewFolderAction extends AbstractResourceTreeAction {

    public void run(IAction action) {
        InputDialog dialog = new InputDialog(PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getShell(), "Create new folder",
                "Enter the name for the new folder:", null, null);
        if (dialog.open() == InputDialog.CANCEL) {
            return;
        }

        String folderName = dialog.getValue();

        for (Object item : getSelectedItems()) {
            if (item instanceof IContainer) {
                IFolder folder = ((IContainer) item).getFolder(new Path(
                        folderName));
                try {
                    folder.create(false, true, null);
                } catch (CoreException e) {
                    System.out.println("Could not create folder "+ folder);
                    e.printStackTrace();
                }
            }
        }
    }

}
