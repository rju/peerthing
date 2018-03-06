package de.peerthing.systembehavioureditor.propertyeditor.popupactions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.internal.ObjectPluginAction;

import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Task;
import de.peerthing.systembehavioureditor.model.editor.Variable;

/**
 * 
 * @author jojo
 * This Class will generate and add a new Variable with the default name "name" and
 * adds it to a Task or Node
 */
public class AddVariable extends AbstractTreeAction {

	public void run(IAction action) {
		chosen = ((StructuredSelection)((ObjectPluginAction) action).getSelection()).getFirstElement();
		Variable var = new Variable();
		var.setName("name");
		var.setInitialValue("");
		if (chosen instanceof ITask){
			var.setTask(((Task)chosen));
			System.out.println("itask");
			((ITask)chosen).getVariables().add(var);
		}
		if (chosen instanceof INodeType){
			var.setNode(((INodeType)chosen));
			System.out.println("inodetype");
			((INodeType)chosen).getVariables().add(var);
		}
		if (chosen instanceof SystemBehaviour){
//			var.setNode(((SystemBehaviour)chosen).getCurrentNode());
			var.setNode(((SystemBehaviour)chosen).getNodes().get(0));
//			((SystemBehaviour)chosen).getCurrentNode().getVariables().add(var);
			((SystemBehaviour)chosen).getNodes().get(0).getVariables().add(var);
		}
		treeviewer.refresh();
		graphed.setDirty();
}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
