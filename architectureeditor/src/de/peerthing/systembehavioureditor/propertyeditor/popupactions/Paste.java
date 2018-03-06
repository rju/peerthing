package de.peerthing.systembehavioureditor.propertyeditor.popupactions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.internal.ObjectPluginAction;

import de.peerthing.systembehavioureditor.PeerThingSystemBehaviourEditorPlugin;
import de.peerthing.systembehavioureditor.interchange.EAIInitializeEvaluation;
import de.peerthing.systembehavioureditor.model.ICaseArchitecture;
import de.peerthing.systembehavioureditor.model.ICondition;
import de.peerthing.systembehavioureditor.model.IContentContainer;
import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.ITransition;
import de.peerthing.systembehavioureditor.model.ITransitionContent;
import de.peerthing.systembehavioureditor.model.ITransitionTarget;
import de.peerthing.systembehavioureditor.model.IVariable;
import de.peerthing.systembehavioureditor.model.editor.Action;
import de.peerthing.systembehavioureditor.model.editor.CaseSystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Condition;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Task;
import de.peerthing.systembehavioureditor.model.editor.Variable;
/**
 * @author jojo
 * This Class is for pasting copied Objects like Actions, Conditions, Variables, Cases,...
 */
public class Paste extends AbstractTreeAction {
	
	public void run(IAction action) {
		chosen = ((StructuredSelection)((ObjectPluginAction) action).getSelection()).getFirstElement();
		if (PeerThingSystemBehaviourEditorPlugin.getDefault().getCopy() instanceof Action){
			Action act = (Action)PeerThingSystemBehaviourEditorPlugin.getDefault().getCopy();
			Action tmp = new Action(act);
			if (chosen instanceof ITransition){
				tmp.setContainer(((IContentContainer) chosen));
				((ITransition) chosen).getContents().add(tmp);
			}
			if (chosen instanceof IState){
				tmp.setContainer(((IContentContainer) chosen));
				((IState) chosen).getContents().add(tmp);
				if (((IState)chosen).getInitializeEvaluation()==null)
				((IState)chosen).setInitializeEvaluation(EAIInitializeEvaluation.once);
				}
			if (chosen instanceof CaseSystemBehaviour){
				tmp.setContainer(((IContentContainer) chosen));
				((CaseSystemBehaviour) chosen).getContents().add(tmp);
			}
			
		}
		
		if (PeerThingSystemBehaviourEditorPlugin.getDefault().getCopy() instanceof Condition){
			Condition cond =(Condition)PeerThingSystemBehaviourEditorPlugin.getDefault().getCopy();
			Condition tmp = new Condition(cond);
			if (chosen instanceof ITransition){
				tmp.setContainer(((IContentContainer) chosen));
				((ITransition) chosen).getContents().add(tmp);
			}
			if (chosen instanceof IState){
				tmp.setContainer(((IContentContainer) chosen));
				((IState) chosen).getContents().add(tmp);
				if (((IState)chosen).getInitializeEvaluation()==null)
					((IState)chosen).setInitializeEvaluation(EAIInitializeEvaluation.once);
			} if (chosen instanceof CaseSystemBehaviour){
				tmp.setContainer(((IContentContainer) chosen));
				((CaseSystemBehaviour) chosen).getContents().add(tmp);
			}
		}
		
		if (PeerThingSystemBehaviourEditorPlugin.getDefault().getCopy() instanceof ICaseArchitecture){
			CaseSystemBehaviour csb = (CaseSystemBehaviour)PeerThingSystemBehaviourEditorPlugin.getDefault().getCopy();
			CaseSystemBehaviour tmp = new CaseSystemBehaviour(csb);
			tmp.setCondition((Condition)chosen);
			((Condition) chosen).addCase(tmp);
		}
		if (PeerThingSystemBehaviourEditorPlugin.getDefault().getCopy() instanceof IVariable) {
			Variable var = (Variable) PeerThingSystemBehaviourEditorPlugin
					.getDefault().getCopy();
			Variable tmp = new Variable(var);
			if (chosen instanceof ITask) {
				tmp.setTask(((Task) chosen));
				System.out.println("itask");
				((ITask) chosen).getVariables().add(tmp);
			}
			if (chosen instanceof INodeType) {
				tmp.setNode(((INodeType) chosen));
				System.out.println("inodetype");
				((INodeType) chosen).getVariables().add(tmp);
			}
			if (chosen instanceof SystemBehaviour){
				tmp.setNode(((SystemBehaviour)chosen).getNodes().get(0));
				((SystemBehaviour)chosen).getNodes().get(0).getVariables().add(tmp);
			}
		}
		treeviewer.refresh();
		graphed.setDirty();
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection == null || PeerThingSystemBehaviourEditorPlugin.getDefault().getCopy() == null) {
			action.setEnabled(false);
			return;
		}
		
		chosen = ((StructuredSelection)selection).getFirstElement();
//		if (chosen.getClass() != PeerThingSystemBehaviourEditorPlugin.getDefault().getCopy().getClass()){
		if ((chosen instanceof IContentContainer && PeerThingSystemBehaviourEditorPlugin.getDefault().getCopy() instanceof ITransitionContent)){
			action.setEnabled(true);
		} else if ((chosen instanceof ICondition && PeerThingSystemBehaviourEditorPlugin.getDefault().getCopy() instanceof ICaseArchitecture)){
			action.setEnabled(true);	
		} else if ((chosen instanceof ITransitionTarget && PeerThingSystemBehaviourEditorPlugin.getDefault().getCopy() instanceof IVariable)) {
			action.setEnabled(true);
		} else if ((chosen instanceof ISystemBehaviour && PeerThingSystemBehaviourEditorPlugin.getDefault().getCopy() instanceof IVariable)) {
			action.setEnabled(true);
		} else {
			action.setEnabled(false);
		}
	}

}
