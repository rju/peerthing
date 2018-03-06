package de.peerthing.systembehavioureditor.propertyeditor.popupactions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.internal.ObjectPluginAction;

import de.peerthing.systembehavioureditor.model.ITransitionContent;

/**
 * This Method will move the chosen Action or Condition one place downwards if possible
 * @author jojo 
 */
public class MoveDown extends AbstractTreeAction {

	public void run(IAction action) {
		chosen = ((StructuredSelection) ((ObjectPluginAction) action)
				.getSelection()).getFirstElement();
		int temp = ((ITransitionContent) chosen).getContainer().getContents()
				.indexOf(chosen);
		if (temp != ((ITransitionContent) chosen).getContainer().getContents()
				.size() - 1) {
			ITransitionContent up = ((ITransitionContent) chosen)
					.getContainer().getContents().get(temp + 1);
			((ITransitionContent) chosen).getContainer().getContents().add(
					temp, up);
			((ITransitionContent) chosen).getContainer().getContents().remove(
					temp + 2);
			treeviewer.refresh();
			graphed.setDirty();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
