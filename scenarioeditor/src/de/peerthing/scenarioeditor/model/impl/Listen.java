package de.peerthing.scenarioeditor.model.impl;

import de.peerthing.scenarioeditor.model.ICommandContainer;
import de.peerthing.scenarioeditor.model.IDistribution;
import de.peerthing.scenarioeditor.model.IListen;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.ScenarioFactory;

/**
 * 
 * @author Patrik Schulz
 */


public class Listen	extends Command implements IListen {
	    private IDistribution listen;
	    private String event;

	    /**
	     * the standard constructor (which is used when the user add an element
	     * of this kind. The handed scenario is set so the object knows to
	     * which element this object belongs.     
	     */
	    public Listen(IScenario scenario) {
	    	super(scenario);
	        this.listen = ScenarioFactory.createDistribution(scenario);
	    }
	    
	    public Listen(IListen original, IScenario scenario, Object container) {
	    	super(scenario);
	    	this.setCommandContainer((ICommandContainer)container);
	    	this.event = original.getEvent();
	        this.listen = new Distribution(original.getDistribution(), scenario);
	    }
	    	    
	    /**
	     * creates an copy of the handed original object. the copy object saves
	     * the old values of the original object so the undo operation can
	     * work
	     * @param original
	     */
	    public Listen(IListen original) {
	    	super(original.getScenario());
	    	this.setCommandContainer(original.getCommandContainer());
	    	this.event = original.getEvent();
	        this.listen = new Distribution(original.getDistribution());
	    }

	    public void setDistribution(IDistribution distribution) {
	        listen = distribution;
	    }

	    public IDistribution getDistribution() {
	        return listen;
	    }	    	    

		public String getEvent() {
			return event;
		}

		public void setEvent(String event) {
			this.event = event;
		}
		


}
