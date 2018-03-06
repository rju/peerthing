/*
 * Created on 12.07.2006
 *
 */
package de.peerthing.workbench.resourcetreeview;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import de.peerthing.workbench.filetyperegistration.IUpDownMovable;



/**
 * Action used in the toolbar of the resource view. Moves an item in the tree up
 * or down. Can only do this for items that implement IUpDownMovable. For other
 * items, the button is disabled.
 * 
 * @author Michael Gottschalk
 * 
 */
public class MoveItemAction implements IViewActionDelegate {
    IUpDownMovable selected = null;

    private static IAction moveUpAction = null;

    private static IAction moveDownAction = null;

    public void selectionChanged(IAction action, ISelection selection) {
        if (selection instanceof IStructuredSelection) {
            Object selected = ((IStructuredSelection) selection)
                    .getFirstElement();
            if (selected instanceof IUpDownMovable) {
                this.selected = (IUpDownMovable) selected;
                if (action.getId().equals(
                        "de.peerthing.workbench.MoveDownAction")) {
                    moveDownAction = action;
                    action
                            .setEnabled(((IUpDownMovable) selected)
                                    .canMoveDown());
                } else {
                    moveUpAction = action;
                    action.setEnabled(((IUpDownMovable) selected).canMoveUp());
                }
                return;
            }
        }
        action.setEnabled(false);
    }

    public void run(IAction action) {
        if (selected != null) {
            if (action.getId().equals("de.peerthing.workbench.MoveDownAction")) {
                if (selected.canMoveDown()) {
                    selected.moveDown();
                    action.setEnabled(selected.canMoveDown());
                    moveUpAction.setEnabled(selected.canMoveUp());
                }
            } else {
                if (selected.canMoveUp()) {
                    selected.moveUp();
                    action.setEnabled(selected.canMoveUp());
                    moveDownAction.setEnabled(selected.canMoveDown());
                }

            }
        }
    }

    public void init(IViewPart view) {
    }

}
