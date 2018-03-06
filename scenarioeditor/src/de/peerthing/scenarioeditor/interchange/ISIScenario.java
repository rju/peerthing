package de.peerthing.scenarioeditor.interchange;

import java.util.List;

/**
 * Represents a scenario. It consists of resource definitions, connection
 * definitions, and node categories with their connections, resources, and
 * behaviours.
 * 
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface ISIScenario {
    /**
     * Returns the resource categories that are defined globally and referenced
     * by the node categories.
     * 
     * @return
     */
    public List<ISIResourceCategory> getResourceCategories();

    /**
     * Returns the connection categories that are defined globally and
     * referenced by the node categories.
     * 
     * @return
     */
    public List<ISIConnectionCategory> getConnectionCategories();

    /**
     * Returns the node categories with their individual connections, resources,
     * and behaviours.
     * 
     * @return
     */
    public List<ISINodeCategory> getNodeCategories();


    public String getName();
}
