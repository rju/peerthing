package de.peerthing.scenarioeditor.interchange;

/**
 * Represents the speed of a connection. Can be used
 * for specifying uplink- and downlink speeds in
 * a scenario.
 * 
 *
 * @author Michael Gottschalk
 * @review Boris
 */
public interface ISILinkSpeed {
    /**
     * Returns the delay of the link. This is the delay that is applied for
     * every sent message from the node to the internet (for upstream) or
     * from the internet to the node (for downstream).
     * 
     * @return the delay
     */
	public long getDelay();
	
    /**
     * Returns the speed of the connection in bytes per second.
     * 
     */
	public long getSpeed();
}
