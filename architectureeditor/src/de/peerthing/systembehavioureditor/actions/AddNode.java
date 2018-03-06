package de.peerthing.systembehavioureditor.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.ObjectPluginAction;

import de.peerthing.systembehavioureditor.PeerThingSystemBehaviourEditorPlugin;
import de.peerthing.systembehavioureditor.gefeditor.commands.*;
import de.peerthing.systembehavioureditor.model.editor.Node;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;


/**
 * This action adds a node to an architecture.
 * This handles the popupmenu of the resource view for an architecture.
 * 
 * @author Petra
 */

public class AddNode implements IObjectActionDelegate {

	protected Object firstSelectedObject;
	protected Object[] selectedObjects;
	
	public void run(IAction action) {
		try {
			Node newNode = new Node(true);
        	
        	// selection returns the selected children (here: an IFile)
            Object selection = ((StructuredSelection)((ObjectPluginAction) action).getSelection()).getFirstElement();
            IFile file = (IFile) selection;

            SystemBehaviour system = PeerThingSystemBehaviourEditorPlugin.getDefault().getFiletypeRegistration().getSystemBehaviour(file);
            newNode.setArchitecture(system);
            newNode.setName( ((SystemBehaviour) newNode.getArchitecture()).findDistinctiveNodeName() );
            //newNode.setName("node "  + (system.getNodes().toArray().length+1));
            
            // Call the CreateNodeCommand outside the GEF-Framework
			CreateNodeCommand nodeCommand = new CreateNodeCommand(system, newNode, newNode.getStartTask().getX(), newNode.getStartTask().getY());
			nodeCommand.execute();
			
			newNode.calculateColor();
			
			system.getEditor().setDirty();
			PeerThingSystemBehaviourEditorPlugin.getDefault().getNavigationTree().refresh(system.getEditor().getFiletypeReg().getFile(system.getEditor()));
			
			// Add a tab to the editor's tabfolder.
			system.getEditor().addTab(newNode);

    		}
    	
    	catch (Exception e) {
    		System.out.println("Cannot create new Node");
    		e.printStackTrace();
    	}
		
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			firstSelectedObject = ((IStructuredSelection) selection)
					.getFirstElement();
			selectedObjects = ((IStructuredSelection) selection).toArray();
		}
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}
}
