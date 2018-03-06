package de.peerthing.systembehavioureditor.propertyeditor.popupactions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.internal.ObjectPluginAction;

import de.peerthing.systembehavioureditor.model.ITransitionContent;

/**
 * This Method will move the chosen Action or Condition one place upwards if possible
 * @author jojo 
 */
public class MoveUp extends AbstractTreeAction {

	public void run(IAction action) {
		chosen = ((StructuredSelection) ((ObjectPluginAction) action)
				.getSelection()).getFirstElement();
		int temp = ((ITransitionContent) chosen).getContainer().getContents()
				.indexOf(chosen);
		if (temp != 0) {
			ITransitionContent up = ((ITransitionContent) chosen)
					.getContainer().getContents().get(temp);
			((ITransitionContent) chosen).getContainer().getContents().add(
					temp - 1, up);
			((ITransitionContent) chosen).getContainer().getContents().remove(
					temp + 1);
			treeviewer.refresh();
			graphed.setDirty();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
