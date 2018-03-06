package de.peerthing.scenarioeditor.model.impl;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.scenarioeditor.model.ICallUserBehaviour;
import de.peerthing.scenarioeditor.model.ICase;
import de.peerthing.scenarioeditor.model.ICommand;
import de.peerthing.scenarioeditor.model.IDelay;
import de.peerthing.scenarioeditor.model.IListen;
import de.peerthing.scenarioeditor.model.ILoop;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IScenarioCondition;
import de.peerthing.scenarioeditor.model.IUserAction;

/**
 * Default implementation of ICase. Descriptions are available in ICase.
 * 
 * @author Michael Gottschalk, Patrik Schulz
 * @reviewer Hendrik Angenendt
 * 
 */
public class Case implements ICase {
    private String condition;

    private double probability;

    private List<ICommand> commands;

    private boolean conditionUsed;

    private IScenario scenario;
    
    private IScenarioCondition scenarioCondition;

    /**
     * the standard constructor (which is used when the user add an element
     * of this kind. The handed scenario is set so the object knows to
     * which element this object belongs.     
     */
    public Case(IScenario scenario) {
        this.condition = "";
        commands = new ArrayList<ICommand>();
        this.scenario = scenario;
    }
    
    /**
     * creates an copy of the handed original object. the copy object saves
     * the old values of the original object so the undo operation can
     * work
     * @param original
     */
    public Case(Case original) {
        this.condition = original.getCondition();
        this.conditionUsed = original.isConditionUsed();
        this.probability = original.getProbability();        
        this.scenario = original.getScenario();
    }
    
    /**
     * the standard constructor (which is used when the user add an element
     * of this kind). The handed scenario is set so the object knows to
     * which element this object belongs.     
     */
    public Case(ICase original, IScenario scenario) {
        if (original.getCondition()!=null){
        	this.condition = original.getCondition();
        } else {
        	this.condition = "";
        }
        this.probability = original.getProbability();
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

    public void setCondition(String expression) {
        this.condition = expression;
    }

    public String getCondition() {
        return condition;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public double getProbability() {
        return probability;
    }

    public List<ICommand> getCommands() {
        return commands;
    }

    public boolean isConditionUsed() {
        return conditionUsed;
    }

    public void setConditionUsed(boolean value) {
        conditionUsed = value;
    }

    public IScenario getScenario() {
        return scenario;
    }

    public void setScenarioCondition(IScenarioCondition scenarioCondition){
    	this.scenarioCondition = scenarioCondition;
    	
    }        
    
    public IScenarioCondition getScenarioCondition(){
    	return scenarioCondition;
    }
    public void setScenario(IScenario scenario) {
        this.scenario = scenario;
    }

}
