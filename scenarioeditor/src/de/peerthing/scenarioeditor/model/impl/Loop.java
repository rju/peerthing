package de.peerthing.scenarioeditor.model.impl;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.scenarioeditor.model.ICallUserBehaviour;
import de.peerthing.scenarioeditor.model.ICommand;
import de.peerthing.scenarioeditor.model.ICommandContainer;
import de.peerthing.scenarioeditor.model.IDelay;
import de.peerthing.scenarioeditor.model.IDistribution;
import de.peerthing.scenarioeditor.model.IListen;
import de.peerthing.scenarioeditor.model.ILoop;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IScenarioCondition;
import de.peerthing.scenarioeditor.model.IUserAction;
import de.peerthing.scenarioeditor.model.ScenarioFactory;

/**
 * Default implementation of ILoop.
 * 
 * @author Michael Gottschalk, Patrik Schulz
 * @reviewer Hendrik Angenendt
 */
public class Loop extends Command implements ILoop {
    private String untilCondition;

    private IDistribution distribution;

    private List<ICommand> commands;

    /**
     * the standard constructor (which is used when the user add an element
     * of this kind. The handed scenario is set so the object knows to
     * which element this object belongs.     
     */
    public Loop(IScenario scenario) {
    	super(scenario);
        commands = new ArrayList<ICommand>();
        distribution = ScenarioFactory.createDistribution(scenario);
    }
        
    /**
     * creates an copy of the handed original object. the copy object saves
     * the old values of the original object so the undo operation can
     * work
     * @param original
     */
    public Loop(ILoop original) {
    	super(original.getScenario());        
        this.distribution = new Distribution(original.getDistribution());
        this.untilCondition = original.getUntilCondition();
    }
    
    public Loop(ILoop original, IScenario scenario, Object container) {
    	super(scenario);
        commands = new ArrayList<ICommand>();
        this.untilCondition = original.getUntilCondition();
        this.distribution = new Distribution(original.getDistribution(), scenario);
        this.setCommandContainer((ICommandContainer)container);
        
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

    public void setUntilCondition(String expression) {
        this.untilCondition = expression;
    }

    public String getUntilCondition() {
        return untilCondition;
    }

    public void setDistribution(IDistribution distribution) {
        this.distribution = distribution;
    }

    public IDistribution getDistribution() {
        return distribution;
    }

    public List<ICommand> getCommands() {
        return commands;
    }
        
}
