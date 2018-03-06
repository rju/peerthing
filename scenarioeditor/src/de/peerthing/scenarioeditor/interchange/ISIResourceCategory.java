package de.peerthing.scenarioeditor.interchange;

/**
 * Represents a category of resources defined in a scenario.
 * 
 * 
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 * 
 */
public interface ISIResourceCategory {

    public String getName();

    /**
     * Returns the popularity of this resource. The higher the popularity, the
     * more searches for this resource are conducted during a simulation run.
     * 
     * @return
     */
    public int getPopularity();

    /**
     * Returns the number of different files that exist with this resource
     * category.
     * 
     * @return
     */
    public int getDiversity();


    /**
     * Returns the size of the resources. Since this can be a stochastic value,
     * it is given as a distribution.
     * 
     * @return
     */
    public ISIDistribution getSize();


    /**
     * Returns the scenario to which this resource definition belongs (the
     * parent object).
     * 
     * @return
     */
    public ISIScenario getScenario();
}
