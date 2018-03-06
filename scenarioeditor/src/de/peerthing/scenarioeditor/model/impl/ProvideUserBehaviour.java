package de.peerthing.scenarioeditor.model.impl;

import de.peerthing.scenarioeditor.model.ICase;
import de.peerthing.scenarioeditor.model.ICommandContainer;
import de.peerthing.scenarioeditor.model.ILoop;
import de.peerthing.scenarioeditor.model.IScenarioCondition;
import de.peerthing.scenarioeditor.model.IUserBehaviour;

/**
 * @author Patrik Schulz
 */

public class ProvideUserBehaviour {

	/**
	 * @param container
	 * @return the IUserbehaviour which is the ancestor of the handed container
	 */
	public static IUserBehaviour provideBehaviour(ICommandContainer container) {
		ICommandContainer commandC = null;
		if (container instanceof ILoop) {
			ILoop loop = (ILoop) container;
			commandC = loop.getCommandContainer();
		}
		if (container instanceof ICase) {
			ICase case1 = (ICase) container;
			IScenarioCondition condition1 = (IScenarioCondition) case1
					.getScenarioCondition();
			commandC = condition1.getCommandContainer();
		}
		if (container instanceof IUserBehaviour) {
			IUserBehaviour userBehaviour = (IUserBehaviour) container;
			commandC = userBehaviour;
		}
		if (commandC == null) {
			System.out.println("Error in ProvideUserBehaviour.java");
			return null;
		}

		while (!(commandC instanceof IUserBehaviour)) {
			if (commandC instanceof ILoop) {
				ILoop loop = (ILoop) commandC;
				commandC = loop.getCommandContainer();
			}
			if (commandC instanceof ICase) {
				ICase case1 = (ICase) commandC;
				IScenarioCondition condition1 = (IScenarioCondition) case1
						.getScenarioCondition();
				commandC = condition1.getCommandContainer();
			}
		}
		return (IUserBehaviour) commandC;
	}
}
