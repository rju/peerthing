package de.peerthing.scenarioeditor.interchange;

/**
 * Represents a connection category that is defined in
 * a scenario. It can be referenced by node categories.
 * 
 *
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface ISIConnectionCategory {
    /**
     * Defines what kind of duplex options
     * are available.
     *
     */
	public enum DuplexOption { full, half }
	
	public String getName();
	
    
    /**
     * Returns if this connection is half duplex or full duplex.
     * @return
     */
	public DuplexOption getDuplex();
	
    
    /**
     * Returns the uplink speed of this connection, that
     * means the speed from the node to the internet.
     * @return
     */
	public ISILinkSpeed getUplinkSpeed();
	
    
    /**
     * Returns the downlink speed of this connection.
     * @return
     */
	public ISILinkSpeed getDownlinkSpeed();
}
