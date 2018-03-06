package de.peerthing.scenarioeditor.model;

/**
 * Represents a connection category that is defined in
 * a scenario. It can be referenced by node categories.
 * 
 *
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface IConnectionCategory extends IScenarioObject{
    /**
     * Defines what kind of duplex options
     * are available.
     *
     */
	public enum DuplexOption { full, half }
	
	public void setName(String name);
	public String getName();
	
	/**
     * Sets if this connection is half duplex or full duplex.
     * @param duplex
	 */
	public void setDuplex(DuplexOption duplex);
    
    /**
     * Returns if this connection is half duplex or full duplex.
     * @return
     */
	public DuplexOption getDuplex();
	
    /**
     * Sets the uplink speed of this connection.
     * @param speed
     */
	public void setUplinkSpeed(ILinkSpeed speed);
    
    /**
     * Returns the uplink speed of this connection, that
     * means the speed from the node to the internet.
     * @return
     */
	public ILinkSpeed getUplinkSpeed();
	
    /**
     * Returns the downlink speed of this connection, that
     * means the speed from the internet to the node.
     */
	public void setDownlinkSpeed(ILinkSpeed speed);
    
    /**
     * Returns the downlink speed of this connection.
     * @return
     */
	public ILinkSpeed getDownlinkSpeed();
    /**
     * Sets the scenario to which this node category belongs (the
     * parent object).
     * 
     * @param scenario
     */
    public void setScenario(IScenario scenario);
    
    /**
     * Returns the scenario to which this node category belongs (the
     * parent object).
     * 
     * @return
     */
    public IScenario getScenario();
}
