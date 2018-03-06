package de.peerthing.systembehavioureditor.interchange;

import java.util.Hashtable;
import java.util.Map;

import de.peerthing.systembehavioureditor.model.IAction;

class AIAction implements IAIAction {
	Map<String, IAIParameter> params = new Hashtable<String, IAIParameter>();
	IAction action;
	IAIContentContainer container;

	public AIAction(IAction action, IAIContentContainer container) {
		this.container = container;
		this.action = action;

		for (String key : action.getParameters().keySet()) {
			params.put(key, new AIParameter(action.getParameters().get(key), this));
		}
	}

	public Map<String, IAIParameter> getParameters() {
		return params;
	}

	public String getName() {
		return action.getName();
	}

	public String getResult() {
		return action.getResult();
	}

	public IAIContentContainer getContainer() {
		return container;
	}

}
