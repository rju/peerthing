package de.peerthing.systembehavioureditor.model.editor;

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
import de.peerthing.systembehavioureditor.model.SystemBehaviourModelFactory;

/**
 * Factory that produces models for the architecture.
 * 
 * @author: Michael Gottschalk
 * @review Sebastian Rohjans 27.03.2006
 */

public class SystemBehaviourGEFModelFactory extends SystemBehaviourModelFactory {

	//@Override
	public IAction createAction() {
		return new Action();
	}

	//@Override
	public ISystemBehaviour createArchitecture() {
		return new SystemBehaviour();
	}

	//@Override
	public ICaseArchitecture createCaseArchitecture() {
		return new CaseSystemBehaviour();
	}

	//@Override
	public ICondition createCondition() {
		return new Condition();
	}

	//@Override
	public INodeType createNode() {
		return new Node();
	}

	//@Override
	public IParameter createParameter() {
		return new Parameter();
	}

	//@Override
	public IState createState() {
		return new State();
	}

	//@Override
	public ITask createTask() {
		return new Task();
	}

	//@Override
	public ITransition createTransition() {
		return new Transition();
	}

	//@Override
	public IVariable createVariable() {
		return new Variable();
	}
}