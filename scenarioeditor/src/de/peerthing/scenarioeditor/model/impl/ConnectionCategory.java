package de.peerthing.scenarioeditor.model.impl;

import de.peerthing.scenarioeditor.model.IConnectionCategory;
import de.peerthing.scenarioeditor.model.ILinkSpeed;
import de.peerthing.scenarioeditor.model.IScenario;

/**
 * Default implementation of IConnectionCategory.
 * 
 * @author Michael Gottschalk, Patrik Schulz
 * @reviewer Hendrik
 * 
 */
public class ConnectionCategory implements IConnectionCategory {
    private String name;

    private DuplexOption duplex;
    
    private IScenario scenario;
    
    private ILinkSpeed uplinkSpeed;

    private ILinkSpeed downlinkSpeed;

    /**
     * the standard constructor (which is used when the user add an element
     * of this kind. The handed scenario is set so the object knows to
     * which element this object belongs.     
     */
    public ConnectionCategory(IScenario scenario) {
    	this.scenario = scenario;
        this.name = "unknown";
        this.duplex = DuplexOption.full;
    }
    
    /**
     * creates an copy of the handed original object. the copy object saves
     * the old values of the original object so the undo operation can
     * work
     * @param original
     */
    public ConnectionCategory(IConnectionCategory original) {
    	this.scenario = original.getScenario();
        this.name = original.getName();
        this.duplex = original.getDuplex();
        this.downlinkSpeed = new LinkSpeed(original.getDownlinkSpeed());
        this.uplinkSpeed = new LinkSpeed(original.getUplinkSpeed());
    }    
    
    public ConnectionCategory(IConnectionCategory original, IScenario scenario) {
    	this.scenario = scenario;
        this.name = original.getName();
        this.duplex = original.getDuplex();
        this.downlinkSpeed = new LinkSpeed(original.getDownlinkSpeed(), scenario);
        this.uplinkSpeed = new LinkSpeed(original.getUplinkSpeed(), scenario);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDuplex(DuplexOption duplex) {
        this.duplex = duplex;
    }

    public DuplexOption getDuplex() {
        return duplex;
    }

    public void setUplinkSpeed(ILinkSpeed speed) {
        this.uplinkSpeed = speed;
    }

    public ILinkSpeed getUplinkSpeed() {
        return uplinkSpeed;
    }

    public void setDownlinkSpeed(ILinkSpeed speed) {
        this.downlinkSpeed = speed;
    }

    public ILinkSpeed getDownlinkSpeed() {
        return downlinkSpeed;
    }
    public void setScenario(IScenario scenario) {
        this.scenario = scenario;
    }

    public IScenario getScenario() {
        return scenario;
    }
}
