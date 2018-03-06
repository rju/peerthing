package de.peerthing.systembehavioureditor.propertyeditor;

import org.eclipse.jface.viewers.LabelProvider;

import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.editor.Action;
import de.peerthing.systembehavioureditor.model.editor.CaseSystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Condition;
import de.peerthing.systembehavioureditor.model.editor.Parameter;
import de.peerthing.systembehavioureditor.model.editor.State;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Task;
import de.peerthing.systembehavioureditor.model.editor.Transition;
import de.peerthing.systembehavioureditor.model.editor.Variable;

public class ViewLabelProvider extends LabelProvider {

	/**
	 * sets the name of the items in the view
	 * @author jojo
	 */
	public String getText(Object obj) {
		if (obj instanceof ITask) { // INodeType
			return "Task(" + ((Task) obj).getName() + ")";
		} else if (obj instanceof INodeType) {
			return "Node(" + ((INodeType) obj).getName() + ")";
		} else if (obj instanceof State) {
			return "State(" + ((State) obj).getName() + ")";
		} else if (obj instanceof Transition) {
			return "Transition(" + ((Transition) obj).getEvent() + ")";
		} else if (obj instanceof Action) {
			return "Action(" + ((Action) obj).getName() + ")";
		} else if (obj instanceof Condition) {
			return "Condition(" + ((Condition) obj).getName() + ")";
		} else if (obj instanceof CaseSystemBehaviour) {
			if (((CaseSystemBehaviour) obj).getExpression() == null) {
				return "Case(default)";
			}
			return "Case(" + ((CaseSystemBehaviour) obj).getExpression() + ")";
		} else if (obj instanceof Parameter) {
			return "Parameter(" + ((Parameter) obj).getName() + ")";
		} else if (obj instanceof Variable) {
			return "Variable(" + ((Variable) obj).getName() + ")";
		} else if (obj instanceof SystemBehaviour) {
//			return "Node(" + ((SystemBehaviour) obj).getCurrentNode().getName() + ")";
			return "Node(" + ((SystemBehaviour) obj).getNodes().get(0).getName() + ")";
		}
			return "";
	}

}
