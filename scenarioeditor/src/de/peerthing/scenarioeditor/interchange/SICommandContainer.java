package de.peerthing.scenarioeditor.interchange;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.scenarioeditor.model.ICallUserBehaviour;
import de.peerthing.scenarioeditor.model.ICommand;
import de.peerthing.scenarioeditor.model.ICommandContainer;
import de.peerthing.scenarioeditor.model.IDelay;
import de.peerthing.scenarioeditor.model.IListen;
import de.peerthing.scenarioeditor.model.ILoop;
import de.peerthing.scenarioeditor.model.IScenarioCondition;
import de.peerthing.scenarioeditor.model.IUserAction;

abstract class SICommandContainer implements ISICommandContainer {
	protected List<ISICommand> commands = new ArrayList<ISICommand>();

	/**
	 * Adds the commands from the given ICommandContainer to
	 * this container. This includes constructing new Objects
	 * of different types since Scenario Interchange objects
	 * must be made.
	 *
	 * @param container
	 */
	public SICommandContainer(ICommandContainer container) {
		for (ICommand comm : container.getCommands()) {
			ISICommand sicomm = null;
			if (comm instanceof IUserAction) {
				sicomm = new SIAction((IUserAction) comm, this);
			} else if (comm instanceof ICallUserBehaviour) {
				sicomm = new SICallBehaviour((ICallUserBehaviour) comm, this);
			} else if (comm instanceof IScenarioCondition) {
				sicomm = new SICondition(((IScenarioCondition) comm), this);
			} else if (comm instanceof IDelay) {
				sicomm = new SIDelay(((IDelay) comm), this);
			} else if (comm instanceof ILoop) {
				sicomm = new SILoop(((ILoop) comm), this);
			} else if (comm instanceof IListen) {
			    sicomm = new SIListen((IListen) comm, this);
            }

			commands.add(sicomm);
		}
	}

	public List<ISICommand> getCommands() {
		return commands;
	}

}
