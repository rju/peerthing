/*
 * Created on 13.04.2006
 *
 */
package de.peerthing.workbench.resourcetreeview;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Popup menu actions that work on the resource tree can use this class as a
 * superclass.
 * 
 * @author Michael Gottschalk
 * 
 */
public abstract class AbstractResourceTreeAction implements
        IObjectActionDelegate {
    protected NavigationView view;

    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        if (targetPart instanceof NavigationView) {
            view = (NavigationView) targetPart;
        }
    }

    /**
     * Returns a list of the currently selected items in the resource tree view.
     * Return an empty list if nothing is selected, but never null.
     * 
     * @return
     */
    protected List getSelectedItems() {
        if (view == null) {
            return new ArrayList();
        } else {
            return view.getSelectedItems();
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
    }
}
