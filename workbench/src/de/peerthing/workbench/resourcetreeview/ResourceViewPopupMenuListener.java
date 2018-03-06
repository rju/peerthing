package de.peerthing.workbench.resourcetreeview;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.actions.ActionFactory;

import de.peerthing.workbench.filetyperegistration.IFileTypeRegistration;



/**
 * The method menuAboutToShow is called when the popup menu in the resource view
 * is about to show. This class registers new menu entries depending on the
 * current context and the registered filetype registrations. Checks if the
 * selected elements are files and adds "open with..." entries to the popup menu
 * where appropriate. Furthermore, New File entries are added to a submenu.
 * 
 * @author Michael Gottschalk
 * 
 */
public class ResourceViewPopupMenuListener implements IMenuListener {
    private TreeViewer viewer;

    private ArrayList<IFileTypeRegistration> fileTypeRegistration;

    private Hashtable<String, ArrayList<IFileTypeRegistration>> filetypes;

    /**
     * Creates a new popup menu listener.
     * 
     * @param viewer The resource tree viewer
     * @param fileTypeRegistration The filetype registration
     * @param filetypes The filetype registrations ordered by
     *          filetype names.
     */
    public ResourceViewPopupMenuListener(TreeViewer viewer,
            ArrayList<IFileTypeRegistration> fileTypeRegistration,
            Hashtable<String, ArrayList<IFileTypeRegistration>> filetypes) {
        this.viewer = viewer;
        this.fileTypeRegistration = fileTypeRegistration;
        this.filetypes = filetypes;
    }

    public void menuAboutToShow(IMenuManager manager) {
        manager.add(new Separator(ActionFactory.NEW.getId()));
        manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

        IStructuredSelection sel = (IStructuredSelection) viewer.getSelection();
        if (sel == null) {
            return;
        }

        // For IContainers, create a New submenu...
        boolean isFolders = true;
        List<IContainer> containers = new ArrayList<IContainer>();
        for (Object selected : sel.toList()) {
            if (!(selected instanceof IContainer)) {
                isFolders = false;
                break;
            }
            containers.add((IContainer) selected);
        }
        if (isFolders) {
            MenuManager newMenu = new MenuManager("New",
                    "de.peerthing.workbench.NewMenu");
            newMenu.setParent(manager);
            manager.insertBefore(ActionFactory.NEW.getId(), newMenu);

            for (IFileTypeRegistration reg : fileTypeRegistration) {
                if (reg.getNewFileDefinition() != null) {
                    newMenu.add(new NewFileAction(reg, containers));
                }
            }
            return;
        }

        ArrayList<IFileTypeRegistration> allRegs = null;
        IFile[] files = new IFile[sel.size()];
        int fileNo = 0;

        // only if all selected elements are files,
        // the open with... entries must be shown
        for (Object selected : sel.toList()) {
            if (!(selected instanceof IFile)) {
                return;
            }
            IFile file = (IFile) selected;
            files[fileNo++] = file;

            ArrayList<IFileTypeRegistration> regs = filetypes.get(file
                    .getFileExtension());

            if (regs == null) {
                // If a file is not supported by any
                // component, then no component
                // can be used to open the files.
                allRegs = null;
                break;
            } else if (allRegs == null) {
                allRegs = new ArrayList<IFileTypeRegistration>(regs);
            } else {
                // check if all entries in allRegs are also
                // in regs. Remove all entries in allRegs
                // that are not in regs.
                for (int i = 0; i < allRegs.size(); i++) {
                    if (!regs.contains(allRegs.get(i))) {
                        allRegs.remove(i);
                    }
                }
            }
        }

        if (allRegs != null) {
            for (IFileTypeRegistration reg : allRegs) {
                manager.insertBefore(IWorkbenchActionConstants.MB_ADDITIONS,
                        new CallComponentAction(reg, files));
            }
        }
    }

}

/**
 * 
 * An action that calls the component given in the constructor in its run
 * action.
 * 
 * 
 */
class CallComponentAction extends Action {
    IFileTypeRegistration reg;

    IFile[] inputFiles;

    public CallComponentAction(IFileTypeRegistration reg, IFile[] inputfiles) {
        this.reg = reg;
        this.inputFiles = inputfiles;
    }

    @Override
    public String getText() {
        return "Open with " + reg.getComponentName();
    }

    @Override
    public void run() {
        reg.showComponent(inputFiles);
    }
}