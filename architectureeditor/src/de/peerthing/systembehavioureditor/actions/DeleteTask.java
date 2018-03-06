package de.peerthing.systembehavioureditor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import de.peerthing.systembehavioureditor.PeerThingSystemBehaviourEditorPlugin;
import de.peerthing.systembehavioureditor.gefeditor.commands.TaskDeleteCommand;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Task;

/**
 * This class manages the action to delete a Task. The action will be invoked
 * by the popup menu in the resource view.
 * 
 */
public class DeleteTask implements IObjectActionDelegate{
	
	protected Object firstSelectedObject;
	protected Object[] selectedObjects;
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	public void run(IAction action) {
		
		Task t = (Task) firstSelectedObject;
		PeerThingSystemBehaviourEditorPlugin.getDefault().getPropertyEditor().updateForm(t.getNode());
		PeerThingSystemBehaviourEditorPlugin.getDefault().getPropertyEditor().updateTreeViewer(t.getNode());
		((SystemBehaviour)t.getSystemBehaviour()).getEditor().setDirty();
		TaskDeleteCommand del = new TaskDeleteCommand(t, (SystemBehaviour) t.getSystemBehaviour());
		del.execute();
		
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			firstSelectedObject = ((IStructuredSelection) selection)
					.getFirstElement();
			selectedObjects = ((IStructuredSelection) selection).toArray();
		}
	}

}
