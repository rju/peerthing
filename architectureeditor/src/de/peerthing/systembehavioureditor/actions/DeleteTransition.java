package de.peerthing.systembehavioureditor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import de.peerthing.systembehavioureditor.PeerThingSystemBehaviourEditorPlugin;
import de.peerthing.systembehavioureditor.gefeditor.commands.TransitionDeleteCommand;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Transition;

/**
 * This class manages the action to delete a Transition. The action will be invoked
 * by the popup menu in the resource view.
 * 
 */
public class DeleteTransition implements IObjectActionDelegate{
	
	protected Object firstSelectedObject;
	protected Object[] selectedObjects;
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	public void run(IAction action) {
		
		Transition t = (Transition) firstSelectedObject;
		PeerThingSystemBehaviourEditorPlugin.getDefault().getPropertyEditor().updateForm(((IState)t.getNextState()).getTask().getNode());
		PeerThingSystemBehaviourEditorPlugin.getDefault().getPropertyEditor().updateTreeViewer(((IState)t.getNextState()).getTask().getNode());
		TransitionDeleteCommand del = new TransitionDeleteCommand(t);
		del.execute();
		((SystemBehaviour)t.getSystemBehaviour()).getEditor().setDirty();
		PeerThingSystemBehaviourEditorPlugin.getDefault().getNavigationTree().refresh(((SystemBehaviour)(t.getSystemBehaviour())).getEditor().getFiletypeReg().getFile(((SystemBehaviour)t.getSystemBehaviour()).getEditor()));
		
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			firstSelectedObject = ((IStructuredSelection) selection)
					.getFirstElement();
			selectedObjects = ((IStructuredSelection) selection).toArray();
		}
	}

}
