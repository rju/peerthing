package de.peerthing.scenarioeditor.model.impl;

import de.peerthing.scenarioeditor.model.ILinkSpeed;
import de.peerthing.scenarioeditor.model.IScenario;

/**
 * Default implementation of ILinkSpeed.
 * 
 * @author Michael Gottschalk, Patrik
 * @reviewer Hendrik Angenendt
 */

public class LinkSpeed implements ILinkSpeed {
    
	private long delay;
    private long speed;
    
    private IScenario scenario;

    /**
     * the standard constructor (which is used when the user add an element
     * of this kind. The handed scenario is set so the object knows to
     * which element this object belongs.     
     */
    public LinkSpeed(IScenario scenario) {
    	this.scenario = scenario;
    }
    
    public LinkSpeed(ILinkSpeed original, IScenario scenario) {
    	this.speed = original.getSpeed();
    	this.delay = original.getDelay();
    	this.scenario = scenario;
    }
    
    /**
     * creates an copy of the handed original object. the copy object saves
     * the old values of the original object so the undo operation can
     * work
     * @param original
     */
    public LinkSpeed(ILinkSpeed original) {
    	this.speed = original.getSpeed();
    	this.delay = original.getDelay();
    	this.scenario = original.getScenario();
    }

    public void setDelay(long milliseconds) {
        this.delay = milliseconds;
    }

    public long getDelay() {
        return delay;
    }

    public void setSpeed(long bytesPerSecond) {
        this.speed = bytesPerSecond;
    }

    public long getSpeed() {
        return speed;
    }

    public void setScenario(IScenario scenario) {
        this.scenario = scenario;
    }

    public IScenario getScenario() {
        return scenario;
    }

}
