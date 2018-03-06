package de.peerthing.scenarioeditor.model.impl;

import de.peerthing.scenarioeditor.model.ICase;
import de.peerthing.scenarioeditor.model.ILoop;
import de.peerthing.scenarioeditor.model.IScenarioCondition;
import de.peerthing.scenarioeditor.model.IUserBehaviour;
import de.peerthing.scenarioeditor.model.ICallUserBehaviour;
import de.peerthing.scenarioeditor.model.ICommandContainer;
import de.peerthing.scenarioeditor.model.IScenario;

/**
 * Default implementation of ICallBehaviour. descriptions 
 * available in the interface.
 * 
 * @author Michael Gottschalk, Patrik
 * @reviewer Hendrik Angenendt
 * 
 */
public class CallUserBehaviour extends Command implements ICallUserBehaviour {
    private IUserBehaviour behaviour;

    private double probability;
    
    private boolean startTask;

    /**
     * the standard constructor (which is used when the user add an element
     * of this kind. The handed scenario is set so the object knows to
     * which element this object belongs.     
     */
    public CallUserBehaviour(IScenario scenario, IUserBehaviour behaviour) {
    	super(scenario);
    	this.behaviour = behaviour;
    }
    
    /**
     * creates an copy of the handed original object. the copy object saves
     * the old values of the original object so the undo operation can
     * work
     * @param original
     */
    public CallUserBehaviour(CallUserBehaviour original) {
    	super(original.getScenario());
    	this.behaviour = original.getBehaviour();
    	this.probability = original.getProbability();
    	this.setCommandContainer(original.getCommandContainer());
    	this.setStartTask(original.isStartTask());
    	this.setScenario(original.getScenario());
    }
    
    /**
     * @deprecated
     * use isStartTask (look at Interface)
     * @return
     */
    public boolean getStartTask(){
    	return startTask;
    }
    
    
    public CallUserBehaviour(ICallUserBehaviour original, IScenario scenario, Object container) {
    	super(scenario);
    	this.probability = original.getProbability();
    	this.setCommandContainer((ICommandContainer)container);
    	ICommandContainer commandC = null;    	
    	//INodeCategory nodeC = (INodeCategory)scenario.getNodeCategories().get(0);
    	//this.behaviour = nodeC.getPrimaryBehaviour();    	    	
    	
    	if (container instanceof ILoop){
    		ILoop loop = (ILoop)container; 
    		commandC = loop.getCommandContainer();    		
    	}    	
    	if (container instanceof ICase){		
    		ICase case1 = (ICase)container;
    		IScenarioCondition condition1 = (IScenarioCondition)case1.getScenarioCondition();
    		commandC = condition1.getCommandContainer();    		
    	}    	
    	if (container instanceof IUserBehaviour){    		
    		IUserBehaviour userBehaviour = (IUserBehaviour)container;    		
    		commandC = userBehaviour;    		
    	}    	
    	while (!(commandC instanceof IUserBehaviour)){    
    		if (commandC instanceof ILoop){
        		ILoop loop = (ILoop)commandC; 
        		commandC = loop.getCommandContainer();    		
        	}
        	if (commandC instanceof ICase){    		
        		ICase case1 = (ICase)commandC;
        		IScenarioCondition condition1 = (IScenarioCondition)case1.getScenarioCondition();
        		commandC = condition1.getCommandContainer();    		
        	}
    	}
    	
    	IUserBehaviour callbehaviour = (IUserBehaviour)commandC;    	    	    
    	
    	IUserBehaviour originalBehaviour = original.getBehaviour();
    	
    	if (originalBehaviour.getNodeCategory().equals(callbehaviour.getNodeCategory())){
    		System.out.println(originalBehaviour.getNodeCategory().getName());
    		System.out.println(callbehaviour.getNodeCategory().getName());
    		this.behaviour = original.getBehaviour();
    	} else {
    		System.out.println("111" + originalBehaviour.getNodeCategory().getName());
    		System.out.println(callbehaviour.getNodeCategory().getName());
    		this.behaviour = callbehaviour;
    	}
    }

    public void setBehaviour(IUserBehaviour behaviour) {
        this.behaviour = behaviour;
    }

    public IUserBehaviour getBehaviour() {
        return behaviour;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public double getProbability() {
        return probability;
    }

	public void setStartTask(boolean startTask) {
		this.startTask = startTask;
	}

	public boolean isStartTask() {
		return this.startTask;
	}
	
}
