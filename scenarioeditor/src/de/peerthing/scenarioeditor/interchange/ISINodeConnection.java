package de.peerthing.scenarioeditor.interchange;

/**
 * Represents the connection of a node defined in a
 * node category in a scenario. 
 * 
 *
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface ISINodeConnection {
    /**
     * Returns the connection category for this connection.
     * The connection categories are defined globally for
     * a scenario whereas the NodeConnections are defined
     * for each node category and only reference a connection
     * category.
     * 
     * @return
     */
	public ISIConnectionCategory getCategory();
	
    /**
     * Returns the number of nodes in this node category that
     * have this connection category. This setting also defines
     * the overall number of nodes in the scenario.
     * 
     * @param number
     */    
	public int getNumberOfNodes();	
	
    /**
     * Returns the node category to which this connection definition
     * belongs (the parent object).
     * 
     * @return
     */
	public ISINodeCategory getNode();
}
