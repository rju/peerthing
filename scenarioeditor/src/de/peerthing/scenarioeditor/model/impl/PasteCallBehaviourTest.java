package de.peerthing.scenarioeditor.model.impl;

import de.peerthing.scenarioeditor.model.ICallUserBehaviour;
import de.peerthing.scenarioeditor.model.ICommand;
import de.peerthing.scenarioeditor.model.ICommandContainer;
import de.peerthing.scenarioeditor.model.IUserBehaviour;

public class PasteCallBehaviourTest {
	public static boolean containsCallBehaviour(ICommandContainer container){
			for(ICommand command: container.getCommands()){
				if(command instanceof ICallUserBehaviour){
					return true;
				} else if (command instanceof ICommandContainer){
					if(containsCallBehaviour((ICommandContainer)command)){
						return true;
					}
				}
			}
		return false;
	}
	
	public static boolean containsCallBehaviour(IUserBehaviour container){
		for(ICommand command: container.getCommands()){
			if(command instanceof ICallUserBehaviour){
				return true;
			} else if (command instanceof ICommandContainer){
				if(containsCallBehaviour((ICommandContainer)command)){
					return true;
				}
			}
		}
	return false;
}
}
