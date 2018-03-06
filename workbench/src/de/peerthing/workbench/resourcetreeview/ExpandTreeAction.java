package de.peerthing.workbench.resourcetreeview;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

/**
 * Expands the tree in the resource tree viewer
 * from the currently selected object on for max. 10
 * levels.
 * 
 * @author Michael Gottschalk
 *
 */
public class ExpandTreeAction implements IViewActionDelegate {
    private TreeViewer viewer = null;
    private Object selectedObject = null;
    
    public void init(IViewPart view) {
        if (view instanceof NavigationView) {
            viewer = ((NavigationView) view).getTreeViewer();
        }
    }

    public void run(IAction action) {
        if (viewer != null && selectedObject != null) {
            viewer.expandToLevel(selectedObject, 10);
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
        if (selection instanceof IStructuredSelection) {
            selectedObject = ((IStructuredSelection) selection).getFirstElement();
        }
    }

}
