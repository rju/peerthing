package de.peerthing.scenarioeditor.interchange;

import de.peerthing.scenarioeditor.model.IUserBehaviour;

class SIBehaviour extends SICommandContainer implements ISIBehaviour{
	private IUserBehaviour behaviour;
	private ISINodeCategory category;

	public SIBehaviour(IUserBehaviour behaviour, ISINodeCategory category) {
		super(behaviour);
        this.behaviour = behaviour;
		this.category = category;
	}


	public String getName() {
		return behaviour.getName();
	}

	public ISINodeCategory getNodeCategory() {
		return category;
	}
}
