/*
 * Created on 23.04.2006
 *
 */
package de.peerthing.workbench.resourcetreeview;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;

/**
 * Copies the selected files in the resource tree
 * view to the clipboard. If a database is selected,
 * the additional files that belong to this database
 * are also copied, since they are not shown in the
 * resource view.
 * 
 * @author Michael Gottschalk
 *
 */
public class CopyAction extends AbstractResourceTreeAction {

    public void run(IAction action) {
        List selectedItems = getSelectedItems();

        if (selectedItems != null) {
            Clipboard clp = new Clipboard(view.getSite().getShell()
                    .getDisplay());
            ArrayList<String> filenames = new ArrayList<String>();

            for (Object item : selectedItems) {
                if (item instanceof IResource) {
                    IResource res = (IResource) item;

                    filenames.add(res.getLocation().toString());

                    // If a user wants to copy a database,
                    // also copy all files that belong
                    // to the database, but are not displayed
                    // in the resource view:
                    if (res.getFileExtension() != null
                            && res.getFileExtension().equals("simdata")) {
                        IContainer parent = res.getParent();
                        String[] extensions = new String[] { "script",
                                "properties", "data" };
                        for (String ext : extensions) {
                            IResource r = parent.findMember(res.getName() + "."
                                    + ext);
                            if (r != null) {
                                filenames.add(r.getLocation().toString());
                            }
                        }

                    }
                }
            }

            FileTransfer trans = FileTransfer.getInstance();
            clp.setContents(new Object[] { filenames
                    .toArray(new String[filenames.size()]) },
                    new Transfer[] { trans });
            clp.dispose();
        }
    }

}
