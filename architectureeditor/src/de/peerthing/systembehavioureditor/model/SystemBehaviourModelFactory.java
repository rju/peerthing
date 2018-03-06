package de.peerthing.systembehavioureditor.model;


import de.peerthing.systembehavioureditor.model.IAction;
import de.peerthing.systembehavioureditor.model.ICaseArchitecture;
import de.peerthing.systembehavioureditor.model.ICondition;
import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.IParameter;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.ITransition;
import de.peerthing.systembehavioureditor.model.IVariable;
import de.peerthing.systembehavioureditor.model.simulation.*;


/**
 * Factory class with which concrete instances of the Achitecture
 * model interfaces can be created.
 *
 * @author Michael
 * @review Johannes Fischer
 *
 */
public class SystemBehaviourModelFactory {
	public ISystemBehaviour createArchitecture() {
		return new SystemBehaviour();
	}

	public IState createState() {
		return new State();
	}

	public IAction createAction() {
		return new Action();
	}

	public ICondition createCondition() {
		return new Condition();
	}

	public INodeType createNode() {
		return new Node();
	}

	public ITask createTask() {
		return new Task();
	}

	public ITransition createTransition() {
		return new Transition();
	}

	public IVariable createVariable() {
		return new Variable();
	}
	
	public IParameter createParameter() {
		return new Parameter();
	}
	
	public ICaseArchitecture createCaseArchitecture() {
		return new CaseSystemBehaviour();
	}
}
