package de.peerthing.systembehavioureditor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import de.peerthing.systembehavioureditor.PeerThingSystemBehaviourEditorPlugin;
import de.peerthing.systembehavioureditor.gefeditor.commands.StateDeleteCommand;
import de.peerthing.systembehavioureditor.model.editor.State;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;

/**
 * This class manages the action to delete a State. The action will be invoked
 * by the popup menu in the resource view.
 * 
 */
public class DeleteState implements IObjectActionDelegate {
	
	protected Object firstSelectedObject;
	protected Object[] selectedObjects;
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {		
	}

	public void run(IAction action) {
		State s = (State) firstSelectedObject;
		PeerThingSystemBehaviourEditorPlugin.getDefault().getPropertyEditor().updateForm(s.getTask().getNode());
		PeerThingSystemBehaviourEditorPlugin.getDefault().getPropertyEditor().updateTreeViewer(s.getTask().getNode());
		StateDeleteCommand del = new StateDeleteCommand(s, (SystemBehaviour)s.getSystemBehaviour());
		del.execute();
		((SystemBehaviour)s.getSystemBehaviour()).getEditor().setDirty();
		PeerThingSystemBehaviourEditorPlugin.getDefault().getNavigationTree().refresh(((SystemBehaviour)s.getSystemBehaviour()).getEditor().getFiletypeReg().getFile(((SystemBehaviour)s.getSystemBehaviour()).getEditor()));
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			firstSelectedObject = ((IStructuredSelection) selection)
					.getFirstElement();
			selectedObjects = ((IStructuredSelection) selection).toArray();
		}
	}

}
