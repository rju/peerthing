package de.peerthing.systembehavioureditor.propertyeditor.popupactions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.internal.ObjectPluginAction;

import de.peerthing.systembehavioureditor.interchange.EAIInitializeEvaluation;
import de.peerthing.systembehavioureditor.model.IContentContainer;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.ITransition;
import de.peerthing.systembehavioureditor.model.editor.CaseSystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Condition;
/**
 * @author jojo
 * This Class is for adding Conditions to Transitions, States and Cases. It will generate 
 * a new Condition with a default Expression and the default name "Condition".
 * remark: every condition needs a default Case.
 */

public class AddCondition extends AbstractTreeAction {

	public void run(IAction action) {
		chosen = ((StructuredSelection)((ObjectPluginAction) action).getSelection()).getFirstElement();
		Condition cond = new Condition();
		cond.setName("Condition");
		CaseSystemBehaviour csb = new CaseSystemBehaviour("default",cond);
		csb.setCondition(cond);
		cond.setDefaultCase(csb);
		cond.setContainer(((IContentContainer) chosen));
		if (chosen instanceof ITransition)
			((ITransition) chosen).getContents().add(cond);
		if (chosen instanceof IState){
			((IState) chosen).getContents().add(cond);
			if (((IState)chosen).getInitializeEvaluation()==null)
				((IState)chosen).setInitializeEvaluation(EAIInitializeEvaluation.once);
		} if (chosen instanceof CaseSystemBehaviour){
			((CaseSystemBehaviour) chosen).getContents().add(cond);
		}
		treeviewer.refresh();

		treeviewer.refresh();
		graphed.setDirty();
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
