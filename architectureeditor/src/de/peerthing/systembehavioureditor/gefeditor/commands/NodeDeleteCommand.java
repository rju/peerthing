package de.peerthing.systembehavioureditor.gefeditor.commands;

import org.eclipse.gef.commands.Command;

import de.peerthing.systembehavioureditor.gefeditor.SysGraphicalEditor;
import de.peerthing.systembehavioureditor.model.editor.Node;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;

public class NodeDeleteCommand extends Command{
	
	private Node node;
	private SysGraphicalEditor theEditor;
	
	public NodeDeleteCommand (Node n) {
		
		super("Delete Node");
		this.node = n;
	}
	
	public void execute() {
		
		// don't allow to delete the last node
		if (node.getArchitecture().getNodes().size() == 1) {
			System.out.println("Deletion of node canceled, because this is the last node.");
			this.dispose();
			return;
		}
		redo();
		
	}
	
	public void redo() {
		
		((SystemBehaviour) node.getSystemBehaviour()).removeNode(node);
		
		// close the editor tab
		theEditor = ((SystemBehaviour) node.getSystemBehaviour()).getEditor();
		theEditor.deleteTab(node);
	}
	
	public void undo() {
		
		((SystemBehaviour) node.getSystemBehaviour()).addNode(node);
		// re-open the editor tab
		theEditor.addTab(node);
	}

}
