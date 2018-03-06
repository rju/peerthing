package de.peerthing.workbench.resourcetreeview;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;

/**
 * Popup menu action that refreshes the resources 
 * under the currently selected folder in the resource tree.
 * 
 * @author Michael Gottschalk
 *
 */
public class RefreshAction extends AbstractResourceTreeAction {
    public void run(IAction action) {
        for (Object item : getSelectedItems()) {
            if (item instanceof IContainer) {
                try {
                    ((IContainer) item).refreshLocal(IContainer.DEPTH_INFINITE, null);
                } catch (CoreException e) {
                    System.out.println("Could refresh container " + item);
                    e.printStackTrace();
                }
            }
        }
    }
}
