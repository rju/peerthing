package de.peerthing.scenarioeditor.model.impl;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.scenarioeditor.model.ICase;
import de.peerthing.scenarioeditor.model.ICommandContainer;
import de.peerthing.scenarioeditor.model.IDefaultCase;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IScenarioCondition;
import de.peerthing.scenarioeditor.model.ScenarioFactory;

/**
 * Default implementation of IScenarioCondition
 * 
 * @author Michael Gottschalk, Patrik Schulz
 * @reviewer Hendrik Angenendt
 */
public class ScenarioCondition extends Command implements IScenarioCondition {
    private ICase defaultCase;

    private List<ICase> cases;

    /**
     * the standard constructor (which is used when the user add an element
     * of this kind). The handed scenario is set so the object knows to
     * which element this object belongs.     
     */
    public ScenarioCondition(IScenario scenario) {
    	super(scenario);
        defaultCase = ScenarioFactory.createCase(scenario);
        cases = new ArrayList<ICase>();
    }
    
    public ScenarioCondition(IScenarioCondition original, IScenario scenario, Object container) {
    	super(scenario);
        defaultCase = new DefaultCase((IDefaultCase)original.getDefaultCase(), scenario);
        cases = new ArrayList<ICase>();
        this.setCommandContainer((ICommandContainer)container);
	    if (original.getCases()!=null){
	      	for (ICase oriCase : original.getCases()){
	      		Case newCase = new Case(oriCase, scenario);
	       		newCase.setScenarioCondition(this);
	       		cases.add(newCase);	       	
	       	}
	    }
    }

    public void setDefaultCase(ICase defaultCase) {
        this.defaultCase = defaultCase;
    }

    public ICase getDefaultCase() {
        return defaultCase;
    }

    public List<ICase> getCases() {
        return cases;
    }
    
}
