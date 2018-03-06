package de.peerthing.systembehavioureditor.propertyeditor.popupactions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.internal.ObjectPluginAction;

import de.peerthing.systembehavioureditor.interchange.EAIInitializeEvaluation;
import de.peerthing.systembehavioureditor.model.IContentContainer;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.ITransition;
import de.peerthing.systembehavioureditor.model.editor.Action;
import de.peerthing.systembehavioureditor.model.editor.CaseSystemBehaviour;
/**
 * @author jojo
 * This Class is for adding actions to Transitions, States and Cases. It will generate 
 * a new Action with the default name "Action" and then adds this Action to the correct Object..
 */
public class AddAction extends AbstractTreeAction {
	
	public void run(IAction action) {
		chosen = ((StructuredSelection)((ObjectPluginAction) action).getSelection()).getFirstElement();
		Action tmp = new Action();
		tmp.setName("Action");
		tmp.setResult("");
		tmp.setContainer(((IContentContainer) chosen));
		if (chosen instanceof ITransition)
			((ITransition) chosen).getContents().add(tmp);
		if (chosen instanceof IState){
			((IState) chosen).getContents().add(tmp);
			if (((IState)chosen).getInitializeEvaluation()==null)
			((IState)chosen).setInitializeEvaluation(EAIInitializeEvaluation.once);
			}
		if (chosen instanceof CaseSystemBehaviour){
			((CaseSystemBehaviour) chosen).getContents().add(tmp);
		}
		treeviewer.refresh();
        treeviewer.expandToLevel(chosen, 1);
		graphed.setDirty();
}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
