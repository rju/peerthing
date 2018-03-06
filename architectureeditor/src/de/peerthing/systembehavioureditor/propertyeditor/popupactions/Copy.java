package de.peerthing.systembehavioureditor.propertyeditor.popupactions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.internal.ObjectPluginAction;

import de.peerthing.systembehavioureditor.PeerThingSystemBehaviourEditorPlugin;
/**
 * @author jojo
 * This Class is for copying Objects like Actions, Conditions, Variables, Cases,...
 */
public class Copy extends AbstractTreeAction {
	
	public void run(IAction action) {
		chosen = ((StructuredSelection)((ObjectPluginAction) action).getSelection()).getFirstElement();
		PeerThingSystemBehaviourEditorPlugin.getDefault().setCopy(chosen);
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
