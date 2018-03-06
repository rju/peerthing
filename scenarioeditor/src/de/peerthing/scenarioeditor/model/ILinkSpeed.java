package de.peerthing.scenarioeditor.model;

/**
 * Represents the speed of a connection. Can be used
 * for specifying uplink- and downlink speeds in
 * a scenario.
 * 
 *
 * @author Michael Gottschalk
 * @review Boris
 */
public interface ILinkSpeed extends IScenarioObject{
	/**
     * Sets the delay of the link. This is the delay that is applied for
     * every sent message from the node to the internet (for upstream) or
     * from the internet to the node (for downstream).
     * 
     * @param milliseconds the delay
	 */
	public void setDelay(long milliseconds);
    
    /**
     * Returns the delay of the link. This is the delay that is applied for
     * every sent message from the node to the internet (for upstream) or
     * from the internet to the node (for downstream).
     * 
     * @return the delay
     */
	public long getDelay();
	
    /**
     * Sets the speed of the connection in bytes per second.
     * 
     * @param bytesPerSecond The speed of the connection.
     */
	public void setSpeed(long bytesPerSecond);
    
    /**
     * Returns the speed of the connection in bytes per second.
     * 
     */
	public long getSpeed();
    
    /**
     * Sets the scenario to which this LinkSpeed belongs.
     *
     */
    public void setScenario(IScenario scenario);
}
