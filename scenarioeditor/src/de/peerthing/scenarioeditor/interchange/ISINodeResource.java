package de.peerthing.scenarioeditor.interchange;

/**
 * Represents resources of a specified category that nodes categories defined in
 * scenario can be assigned.
 * 
 * 
 * @author Michael Gottschalk
 * @review Boris
 */
public interface ISINodeResource {
    /**
     * Returns the resource category that this node resource references. The
     * resource categories are defined globally for a scenario and are only
     * referenced here.
     * 
     * @return
     */
    public ISIResourceCategory getCategory();

    /**
     * Returns the number of resources each node has. Since this can be a
     * stochastic value, this is given as a distribution.
     * 
     * @return
     */
    public ISIDistribution getNumberDistribution();

    /**
     * Returns the node category to which this resource definition belongs (the
     * parent object).
     * 
     * @return
     */
    public ISINodeCategory getNode();
}
