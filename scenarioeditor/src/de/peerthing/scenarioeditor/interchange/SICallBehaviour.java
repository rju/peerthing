package de.peerthing.scenarioeditor.interchange;

import de.peerthing.scenarioeditor.model.ICallUserBehaviour;

class SICallBehaviour extends SICommand implements ISICallBehaviour {
	private ISIBehaviour behaviour;

	private ICallUserBehaviour callBehaviour;

	public SICallBehaviour(ICallUserBehaviour callBehaviour, ISICommandContainer container) {
		this.container = container;

		// The corresponding ISIBehaviour object cannot be found here
		// since it cannot be guaranteed that all ISIBehaviour objects
		// exist yet. So this is done in SINodeCategory after initializing
		// all behaviours.

		this.callBehaviour = callBehaviour;
	}

	protected void setBehaviour(ISIBehaviour behaviour) {
		this.behaviour = behaviour;
	}

	public ISIBehaviour getBehaviour() {
		return behaviour;
	}

	public String getBehaviourName() {
		return callBehaviour.getBehaviour().getName();
	}

	public double getProbability() {
		return callBehaviour.getProbability();
	}

	public boolean isStartTask() {
		return this.callBehaviour.isStartTask();
	}

	public String getCommandName() {
		return "CallBehaviour(" + getBehaviourName() + ")";
	}


}
