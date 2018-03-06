package de.peerthing.scenarioeditor.model.impl;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.scenarioeditor.model.ICallUserBehaviour;
import de.peerthing.scenarioeditor.model.ICommand;
import de.peerthing.scenarioeditor.model.IDefaultCase;
import de.peerthing.scenarioeditor.model.IDelay;
import de.peerthing.scenarioeditor.model.IListen;
import de.peerthing.scenarioeditor.model.ILoop;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IScenarioCondition;
import de.peerthing.scenarioeditor.model.IUserAction;

/**
 * Instances of this class are needed for the default case of a condition.
 * @author Patrik
 */
public class DefaultCase extends Case implements IDefaultCase {
	
	private List<ICommand> commands;    

    private IScenario scenario;

    /**
     * the standard constructor (which is used when the user add an element
     * of this kind. The handed scenario is set so the object knows to
     * which element this object belongs.     
     */
    public DefaultCase(IScenario scenario){
		super(scenario);
	}
	public DefaultCase(IDefaultCase original, IScenario scenario){
		super(scenario);
		this.scenario = scenario;
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
        	if (com instanceof IScenarioCondition){
        		IScenarioCondition copyOfCondition = new ScenarioCondition((IScenarioCondition)com, scenario, this);
        		commands.add(copyOfCondition);
        	}        	
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
}
