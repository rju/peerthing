package de.peerthing.systembehavioureditor.propertyeditor.popupactions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.internal.ObjectPluginAction;

import de.peerthing.systembehavioureditor.model.editor.CaseSystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Condition;

/**
 * @author jojo
 * This Class is for adding cases to transitions.
 */
public class AddCase extends AbstractTreeAction {
	
	public void run(IAction action) {
		chosen = ((StructuredSelection)((ObjectPluginAction) action).getSelection()).getFirstElement();
			((Condition) chosen).addCase(new CaseSystemBehaviour("",
					((Condition) chosen)));
		treeviewer.refresh();
		graphed.setDirty();
}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
