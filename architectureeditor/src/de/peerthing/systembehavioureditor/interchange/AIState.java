package de.peerthing.systembehavioureditor.interchange;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.ITransition;

class AIState extends AIContentContainer implements IAIState {
	private List<IAITransition> transitions = new ArrayList<IAITransition>();

	private IAITask task;

	private IState state;

	public AIState(IState state, IAITask task) {
		super(state);
		this.state = state;
		this.task = task;

		for (ITransition tran : state.getTransitions()) {
			transitions.add(new AITransition(tran, this));
		}
	}

	public List<IAITransition> getTransitions() {
		return transitions;
	}

	public IAITask getTask() {
		return task;
	}

	public EAIInitializeEvaluation getInitializeEvaluation() {
		return state.getInitializeEvaluation();
	}

	public String getName() {
		return state.getName();
	}

	@Override
	public String toString() {
		return getName();
	}

}
