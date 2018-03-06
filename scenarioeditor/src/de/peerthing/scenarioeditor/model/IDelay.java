package de.peerthing.scenarioeditor.model;

/**
 * Represents a command that can be used in the behaviour
 * of a node category.
 * 
 *
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface IDelay extends ICommand, IScenarioObject{
    /**
     * Sets the amount of the delay in milliseconds. This
     * is expressed as a distribution so that the delay can
     * be a random value.
     * 
     * @param distribution
     */
	public void setDistribution(IDistribution distribution);
    
    /**
     * Returns the amount of the delay in milliseconds. This
     * is expressed as a distribution so that the delay can
     * be a random value.
     * 
     * @return
     */
	public IDistribution getDistribution();
}
