package de.peerthing.systembehavioureditor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import de.peerthing.systembehavioureditor.PeerThingSystemBehaviourEditorPlugin;
import de.peerthing.systembehavioureditor.gefeditor.commands.NodeDeleteCommand;
import de.peerthing.systembehavioureditor.model.editor.Node;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;

/**
 * This class manages the action to delete a Node. The action will be invoked
 * by the popup menu in the resource view.
 * 
 */
public class DeleteNode implements IObjectActionDelegate {
	
	protected Object firstSelectedObject;
	protected Object[] selectedObjects;
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {		
	}

	public void run(IAction action) {
		Node n = (Node) firstSelectedObject;
		int pos = n.getArchitecture().getNodes().indexOf(n);
		if (pos > 0) {pos--;}
		else if (pos == 0) {pos = 1;}
		PeerThingSystemBehaviourEditorPlugin.getDefault().getPropertyEditor().updateForm(n.getArchitecture().getNodes().get(pos));
		PeerThingSystemBehaviourEditorPlugin.getDefault().getPropertyEditor().updateTreeViewer(n.getArchitecture().getNodes().get(pos));	
		NodeDeleteCommand del = new NodeDeleteCommand(n);
		del.execute();
		((SystemBehaviour)n.getSystemBehaviour()).getEditor().setDirty();
		PeerThingSystemBehaviourEditorPlugin.getDefault().getNavigationTree().refresh(((SystemBehaviour)n.getSystemBehaviour()).getEditor().getFiletypeReg().getFile(((SystemBehaviour)n.getSystemBehaviour()).getEditor()));
		
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			firstSelectedObject = ((IStructuredSelection) selection)
					.getFirstElement();
			selectedObjects = ((IStructuredSelection) selection).toArray();
		}
	}
}
