/*
 * Created on 23.04.2006
 *
 */
package de.peerthing.workbench.resourcetreeview;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TransferData;

/**
 * Popup menu action that pastes files from the
 * clipboard to the currently selected folder in
 * the resource view.
 * 
 * @author Michael Gottschalk
 *
 */
public class PasteAction extends AbstractResourceTreeAction {

    public void run(IAction action) {
        List selectedItems = getSelectedItems();
        if (selectedItems == null || selectedItems.size() == 0) {
            return;
        }
        IResource res = (IResource) selectedItems.get(0);
        IContainer container = res.getParent();
        if (res instanceof IContainer) {
            container = (IContainer) res;
        }

        Clipboard clipboard = null;

        try {
            clipboard = new Clipboard(view.getSite().getShell().getDisplay());
            Object content = clipboard.getContents(FileTransfer.getInstance());
            if (content != null) {
                String[] filenames = (String[]) content;
                for (String filename : filenames) {
                    File file = new File(filename);

                    if (file.isDirectory()) {
                        MessageDialog
                                .openError(view.getSite().getShell(), "Error",
                                        "Pasting directories is currently not supported!");
                        return;
                    }

                    IFile f = container.getFile(new Path(file.getName()));
                    int number = 1;
                    // If the file exists, try creating a file
                    // with a number after the name...
                    while (f.exists()) {
                        String oldName = file.getName();
                        String newName = oldName.substring(0, oldName.length()
                                - f.getFileExtension().length() - 1);
                        f = container.getFile(new Path(newName + " ("
                                + number++ + ")." + f.getFileExtension()));
                    }

                    try {
                        f.create(new FileInputStream(file), true, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } finally {
            if (clipboard != null) {
                clipboard.dispose();
            }
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
        if (view != null) {
            Clipboard clipboard = new Clipboard(view.getSite().getShell()
                    .getDisplay());
            boolean enabled = false;
            for (TransferData data : clipboard.getAvailableTypes()) {
                if (FileTransfer.getInstance().isSupportedType(data)) {
                    enabled = true;
                }
            }
            clipboard.dispose();

            action.setEnabled(enabled);
        }
    }

}
