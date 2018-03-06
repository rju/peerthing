package de.peerthing.scenarioeditor.model.impl;

import de.peerthing.scenarioeditor.model.ICommandContainer;
import de.peerthing.scenarioeditor.model.IDelay;
import de.peerthing.scenarioeditor.model.IDistribution;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.ScenarioFactory;

/**
 * Default implementation of IDelay.
 * 
 * @author Michael Gottschalk, Patrik Schulz
 * @reviewer Hendrik Angenendt
 */
public class Delay extends Command implements IDelay {
    private IDistribution delay;

    /**
     * the standard constructor (which is used when the user add an element
     * of this kind. The handed scenario is set so the object knows to
     * which element this object belongs.     
     */
    public Delay(IScenario scenario) {
    	super(scenario);
        this.delay = ScenarioFactory.createDistribution(scenario);
    }
    
    /**
     * creates an copy of the handed original object. the copy object saves
     * the old values of the original object so the undo operation can
     * work
     * @param original
     */
    public Delay(IDelay original) {
    	super(original.getScenario());    	
        this.delay = new Distribution(original.getDistribution());
    }
    
    
    public Delay(IDelay original, IScenario scenario, Object container) {
    	super(scenario);
    	this.setCommandContainer((ICommandContainer)container);
        this.delay = new Distribution(original.getDistribution(), scenario);
    }

    public void setDistribution(IDistribution distribution) {
        delay = distribution;
    }

    public IDistribution getDistribution() {
        return delay;
    }        

}
