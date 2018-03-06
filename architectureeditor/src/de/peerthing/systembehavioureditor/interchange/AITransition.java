package de.peerthing.systembehavioureditor.interchange;

import de.peerthing.systembehavioureditor.model.ITransition;

class AITransition extends AIContentContainer implements IAITransition {
	ITransition trans;
	IAIState state;
	IAITransitionTarget target;

	public AITransition(ITransition trans, IAIState state) {
		super(trans);
		this.state = state;
		this.trans = trans;

		// The target can't be set here since not all states may be
		// initialized in this constructor. So this must be set
		// after initializing all states in AINodeType.
	}


	public IAITransitionTarget getNextState() {
		return target;
	}

	public boolean isEndTask() {
		return trans.isEndTask();
	}

	public String getEvent() {
		return trans.getEvent();
	}

	public IAIState getState() {
		return state;
	}

}
