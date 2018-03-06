package de.peerthing.systembehavioureditor.interchange;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.IVariable;

class AITask implements IAITask {
	private ITask task;

	private IAINodeType node;

	private List<IAIState> states = new ArrayList<IAIState>();

	private List<IAIVariable> variables = new ArrayList<IAIVariable>();

	private IAIState startState;

	public AITask(ITask task, IAINodeType node) {
		this.task = task;
		this.node = node;

		for (IState state : task.getStates()) {
			AIState aistate = new AIState(state, this);
			states.add(aistate);

			if (state.getName().equals(task.getStartState().getName())) {
				startState = aistate;
			}
		}

		for (IVariable var : task.getVariables()) {
			variables.add(new AIVariable(var));
		}
	}

	public IAIState getStartState() {
		return startState;
	}

	public List<IAIVariable> getVariables() {
		return variables;
	}

	public List<IAIState> getStates() {
		return states;
	}

	public IAINodeType getNode() {
		return node;
	}

	public String getName() {
		return task.getName();
	}

}
