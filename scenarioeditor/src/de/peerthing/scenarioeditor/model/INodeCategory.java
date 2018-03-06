package de.peerthing.scenarioeditor.model;


/**
 * Represents a node category that is used in
 * a scenario for defining nodes with different
 * behaviour or other different properties.
 * 
 *
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface INodeCategory extends IScenarioObject {
	public void setName(String name);
	public String getName();
	
    /**
     * Sets the architectural type of the node category. This
     * is defined in the description of an architecture.
     * 
     * @param nodeType
     */
	public void setNodeType(String nodeType);
    
    /**
     * Returns the architectural type of the node category. This
     * is defined in the description of an architecture.
     * 
     * @param nodeType
     */
	public String getNodeType();
	
    /**
     * Sets the behaviour that is executed at first, at the beginning
     * of a simulation run. 
     * 
     * @param behaviour
     */
	public void setPrimaryBehaviour(IUserBehaviour behaviour);
	
    /**
     * Returns the behaviour that is executed at first, at the beginning
     * of a simulation run. 
     * 
     * @return
     */
    public IUserBehaviour getPrimaryBehaviour();
	
    /**
     * Returns the connections that this node category has. From the
     * connections, it can also be concluded which is the overall number
     * of nodes in this category.
     * 
     * @return
     */
	public IListWithParent<INodeConnection> getConnections();
    
    /**
     * Returns the resources that each node in this category has.
     * 
     * @return
     */
	public IListWithParent<INodeResource> getResources();
    
    /**
     * Returns the behaviour of the nodes in this category. This
     * is a sequential list of commands.
     * 
     * @return
     */
	public IListWithParent<IUserBehaviour> getBehaviours();
	
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
