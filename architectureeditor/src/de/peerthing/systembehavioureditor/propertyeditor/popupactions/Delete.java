package de.peerthing.systembehavioureditor.propertyeditor.popupactions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.internal.ObjectPluginAction;

import de.peerthing.systembehavioureditor.model.IParameter;
import de.peerthing.systembehavioureditor.model.IVariable;
import de.peerthing.systembehavioureditor.model.editor.CaseSystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Parameter;
import de.peerthing.systembehavioureditor.model.editor.Variable;

public class Delete extends AbstractTreeAction {
/**
 * This Method will delete the chosen Object
 * @author jojo
 */
	public void run(IAction action) {
		chosen = ((StructuredSelection) ((ObjectPluginAction) action)
				.getSelection()).getFirstElement();
		if (chosen instanceof IParameter){
			prop.updateForm(((Parameter) chosen).getAction());
			((Parameter) chosen).getAction().removeParameter(
					((Parameter) chosen).getName());			
		}else if (chosen instanceof CaseSystemBehaviour) {
			prop.updateForm(((CaseSystemBehaviour) chosen).getCondition());
				((CaseSystemBehaviour) chosen).getCondition().removeCase(
						((CaseSystemBehaviour) chosen));
		} else if (chosen instanceof Variable) {
			if (((Variable) chosen).getNode() != null) {
				prop.updateForm(((IVariable) chosen).getNode());
				((Variable) chosen).getNode().getVariables().remove(
						(IVariable) chosen);
			}
			if (((Variable) chosen).getTask() != null) {
				prop.updateForm(((IVariable) chosen).getTask());
				((Variable) chosen).getTask().getVariables().remove(
						(IVariable) chosen);
			}
		} else {
			prop.updateForm(((de.peerthing.systembehavioureditor.model.ITransitionContent) chosen).getContainer());
			((de.peerthing.systembehavioureditor.model.ITransitionContent) chosen)
			.getContainer().getContents().remove(chosen);
		}

		treeviewer.refresh();
		graphed.setDirty();
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
