package de.peerthing.scenarioeditor.model;

/**
 * Represents resources of a specified category that nodes categories defined in
 * scenario can be assigned.
 * 
 * 
 * @author Michael Gottschalk
 * @review Boris
 */
public interface INodeResource extends IScenarioObject {

    /**
     * Sets the resource category that this node resource references. The
     * resource categories are defined globally for a scenario and are only
     * referenced here.
     * 
     * @param category
     */
    public void setCategory(IResourceCategory category);

    /**
     * Returns the resource category that this node resource references. The
     * resource categories are defined globally for a scenario and are only
     * referenced here.
     * 
     * @return
     */
    public IResourceCategory getCategory();

    /**
     * Sets the number of resources each node has. This is done via a
     * distribution, i.e. a minimum and maximum number of files can be set.
     * 
     * @param distribution
     */
    public void setNumberDistribution(IDistribution distribution);

    /**
     * Returns the number of resources each node has. Since this can be a
     * stochastic value, this is given as a distribution.
     * 
     * @return
     */
    public IDistribution getNumberDistribution();

    /**
     * Sets the node category to which this resource definition belongs (the
     * parent object).
     * 
     * @param node
     */
    public void setNode(INodeCategory node);

    /**
     * Returns the node category to which this resource definition belongs (the
     * parent object).
     * 
     * @return
     */
    public INodeCategory getNode();
}
