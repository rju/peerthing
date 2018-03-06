package de.peerthing.scenarioeditor.model;

/**
 * Represents a category of resources defined in a scenario.
 * 
 * 
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 * 
 */
public interface IResourceCategory extends IScenarioObject{
    public void setName(String name);

    public String getName();

    /**
     * Sets the popularity of this resource. The higher the popularity, the more
     * searches for this resource are conducted during a simulation run.
     * 
     * @param popularity
     */
    public void setPopularity(int popularity);

    /**
     * Returns the popularity of this resource. The higher the popularity, the
     * more searches for this resource are conducted during a simulation run.
     * 
     * @return
     */
    public int getPopularity();

    /**
     * Sets the number of different files that exist with this resource
     * category.
     * 
     * @param diversity
     */
    public void setDiversity(int diversity);

    /**
     * Returns the number of different files that exist with this resource
     * category.
     * 
     * @return
     */
    public int getDiversity();

    /**
     * Sets the size of the resources. Since this can be a stochastic value, it
     * must be given as a distribution.
     * 
     * @param size
     */
    public void setSize(IDistribution size);

    /**
     * Returns the size of the resources. Since this can be a stochastic value,
     * it is given as a distribution.
     * 
     * @return
     */
    public IDistribution getSize();

    /**
     * Sets the scenario to which this resource definition belongs (the parent
     * object).
     * 
     * @param scenario
     */
    public void setScenario(IScenario scenario);

    /**
     * Returns the scenario to which this resource definition belongs (the
     * parent object).
     * 
     * @return
     */
    public IScenario getScenario();
}
