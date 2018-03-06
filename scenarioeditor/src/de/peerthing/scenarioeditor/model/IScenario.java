package de.peerthing.scenarioeditor.model;

import org.eclipse.core.resources.IFile;

/**
 * Represents a scenario. It consists of resource definitions, connection
 * definitions, and node categories with their connections, resources, and
 * behaviours.
 * 
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface IScenario extends IScenarioObject{
    /**
     * Returns the resource categories that are defined globally and referenced
     * by the node categories.
     * 
     * @return
     */
    public IListWithParent<IResourceCategory> getResourceCategories();

    /**
     * Returns the connection categories that are defined globally and
     * referenced by the node categories.
     * 
     * @return
     */
    public IListWithParent<IConnectionCategory> getConnectionCategories();

    /**
     * Returns the node categories with their individual connections, resources,
     * and behaviours.
     * 
     * @return
     */
    public IListWithParent<INodeCategory> getNodeCategories();

    /**
     * sets the name of the scenario
     */
    public void setName(String name);

    /**
     * gets the name of the scenario
     */
    public String getName();
    
    public void setFile(IFile file);
    
    public IFile getFile();
}
