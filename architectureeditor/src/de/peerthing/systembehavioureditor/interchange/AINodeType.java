package de.peerthing.systembehavioureditor.interchange;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.IVariable;

class AINodeType implements IAINodeType {
	private List<IAIVariable> variables = new ArrayList<IAIVariable>();
	private List<IAITask> tasks = new ArrayList<IAITask>();
	private IAITask startTask;
	private INodeType node;
	private IAIArchitecture arch;

	public AINodeType(INodeType node, IAIArchitecture arch) {
		this.node = node;
		this.arch = arch;

		for (IVariable var : node.getVariables()) {
			variables.add(new AIVariable(var));
		}

		for (ITask task : node.getTasks()) {
			IAITask aitask = new AITask(task, this);
			tasks.add(aitask);

			if (task.getName().equals(node.getStartTask().getName())) {
				startTask = aitask;
			}
		}

		// Set transition targets in all transitions.
		// This must be done here since only now all states are
		// initialized.

		for (IAITask task : tasks) {
			for (IAIState state : task.getStates()) {
				for (IAITransition trans : state.getTransitions()) {
					AITransition t = (AITransition) trans;
					t.target = getTransitionTarget(t.trans.getNextState().getName());
				}
			}
		}

	}

	/**
	 * Returns the state or task with the given name, or null
	 * if no such state or task exists.
	 *
	 * @param name
	 * @return
	 */
	private IAITransitionTarget getTransitionTarget(String name) {
		for (IAITask task : tasks) {
			if (task.getName().equals(name)) {
				return task;
			}

			for (IAIState state : task.getStates()) {
				if (state.getName().equals(name)) {
					return state;
				}
			}
		}

		return null;
	}

	public List<IAIVariable> getVariables() {
		return variables;
	}

	public List<IAITask> getTasks() {
		return tasks;
	}

	public IAITask getStartTask() {
		return startTask;
	}

	public String getName() {
		return node.getName();
	}

	public IAIArchitecture getArchitecture() {
		return arch;
	}

}
