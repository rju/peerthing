package de.peerthing.scenarioeditor.model;


/**
 * Represents a behaviour of a node category in
 * a scenario.
 * 
 *
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface IUserBehaviour extends ICommandContainer, IScenarioObject {
	public void setName(String name);
	
	public String getName();
       
    /**
     * Sets the node category to which this behaviour belongs
     * 
     * @param nodeCategory
     */
    public void setNodeCategory(INodeCategory nodeCategory);
    
    /**
     * Returns the node category to which this behaviour belongs
     * (the parent).
     * 
     * @return
     */
    public INodeCategory getNodeCategory();
}
