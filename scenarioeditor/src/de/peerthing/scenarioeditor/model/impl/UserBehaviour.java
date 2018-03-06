package de.peerthing.scenarioeditor.model.impl;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.scenarioeditor.model.ICallUserBehaviour;
import de.peerthing.scenarioeditor.model.IDelay;
import de.peerthing.scenarioeditor.model.IListen;
import de.peerthing.scenarioeditor.model.ILoop;
import de.peerthing.scenarioeditor.model.IUserAction;
import de.peerthing.scenarioeditor.model.IUserBehaviour;
import de.peerthing.scenarioeditor.model.ICommand;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.IScenario;
/**
 * Default implementation of IBehaviour
 * 
 * @author Michael Gottschalk, Patrik Schulz
 * @reviewer Hendrik Angenendt
 */
public class UserBehaviour implements IUserBehaviour {
    private String name;

    private INodeCategory nodeCategory;

    private List<ICommand> commands;

    /**
     * the standard constructor (which is used when the user add an element
     * of this kind).     
     */
    public UserBehaviour() {
        this.name = "unknown";
        commands = new ArrayList<ICommand>();
    }
    
    /**
     * creates an copy of the handed original object. the copy object saves
     * the old values of the original object so the undo operation can
     * work
     * @param original
     */
    public UserBehaviour(IUserBehaviour ori) {
        this.name = ori.getName();
        commands = new ArrayList<ICommand>();
        this.commands = ori.getCommands();        
    }
    
    public UserBehaviour(IUserBehaviour original, IScenario scenario, INodeCategory nodeC) {
        this.name = original.getName();
        this.nodeCategory = nodeC; 
        commands = new ArrayList<ICommand>();
        for (ICommand com : original.getCommands()){
        	if (com instanceof ILoop){
        		ILoop copyOfLoop = new Loop((ILoop)com, scenario, this);
        		commands.add(copyOfLoop);
        	}
        	if (com instanceof IDelay){
        		IDelay copyOfDelay = new Delay((IDelay)com, scenario, this);
        		commands.add(copyOfDelay);
        	}
        	if (com instanceof IListen){
        		IListen copyOfListen = new Listen((IListen)com, scenario, this);
        		commands.add(copyOfListen);
        	}
        	// Here is a bug. Remenber!
        	
        	/*if (com instanceof IScenarioCondition){
        		IScenarioCondition copyOfCondition = new ScenarioCondition((IScenarioCondition)com, scenario, this);
        		commands.add(copyOfCondition);
        	}*/
        	if (com instanceof IUserAction){
        		IUserAction copyOfAction = new UserAction((IUserAction)com, scenario, this);
        		commands.add(copyOfAction);
        	}
        	
        	if (com instanceof ICallUserBehaviour){
        		ICallUserBehaviour copyOfUserBehaviour = new CallUserBehaviour((ICallUserBehaviour)com, scenario, this);
        		commands.add(copyOfUserBehaviour);
        	}
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNodeCategory(INodeCategory nodeCategory) {
        this.nodeCategory = nodeCategory;
    }

    public INodeCategory getNodeCategory() {
        return nodeCategory;
    }

    public List<ICommand> getCommands() {
        return commands;
    }

    public IScenario getScenario() {
        return nodeCategory.getScenario();
    }

}
