package de.peerthing.scenarioeditor.interchange;

import java.util.Map;

import de.peerthing.scenarioeditor.model.IUserAction;

class SIAction implements ISIAction {
	private IUserAction action;

	private ISICommandContainer container;

	public SIAction(IUserAction action, ISICommandContainer container) {
		this.action = action;
		this.container = container;
	}

	public String getName() {
		return action.getName();
	}

	public double getProbability() {
		return action.getProbability();
	}

	public Map<String, String> getParameters() {
		return action.getParameters();
	}

	public ISICommandContainer getCommandContainer() {
		return container;
	}

	public String getCommandName() {
		return "Action(" + getName() + ")";
	}

}
