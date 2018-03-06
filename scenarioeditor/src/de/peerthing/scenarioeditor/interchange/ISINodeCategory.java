package de.peerthing.scenarioeditor.interchange;

import java.util.List;

/**
 * Represents a node category that is used in
 * a scenario for defining nodes with different
 * behaviour or other different properties.
 * 
 *
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface ISINodeCategory {
	public String getName();
	
    /**
     * Returns the architectural type of the node category. This
     * is defined in the description of an architecture.
     * 
     * @param nodeType
     */
	public String getNodeType();
	
    /**
     * Returns the behaviour that is executed at first, at the beginning
     * of a simulation run. 
     * 
     * @return
     */
    public ISIBehaviour getPrimaryBehaviour();
	
    /**
     * Returns the connections that this node category has. From the
     * connections, it can also be concluded which is the overall number
     * of nodes in this category.
     * 
     * @return
     */
	public List<ISINodeConnection> getConnections();
    
    /**
     * Returns the resources that each node in this category has.
     * 
     * @return
     */
	public List<ISINodeResource> getResources();
    
    /**
     * Returns the behaviour of the nodes in this category. This
     * is a sequential list of commands.
     * 
     * @return
     */
	public List<ISIBehaviour> getBehaviours();
	
    /**
     * Returns the scenario to which this node category belongs (the
     * parent object).
     * 
     * @return
     */
	public ISIScenario getScenario();

}
