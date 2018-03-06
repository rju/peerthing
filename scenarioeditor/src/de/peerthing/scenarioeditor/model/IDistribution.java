package de.peerthing.scenarioeditor.model;

/**
 * Represents a distribution that can be of type normal
 * or uniform. It can be used in a scenario.
 * 
 *
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface IDistribution extends IScenarioObject {
    /**
     * Defines the types that a distribution can have.
     */
	public enum DistributionType { uniform, normal}
	
    /**
     * Sets the type of the distribution
     * @param type The type
     */
	public void setType(DistributionType type);
	
    /**
     * Returns the type of the distribution
     * 
     * @return
     */
    public DistributionType getType();
	
    /**
     * Sets the minimal value of the distribution.
     * 
     * @param min
     */
	public void setMin(double min);
    
    /**
     * Returns the minimal value of the distribution.
     * 
     * @return
     */
	public double getMin();
	
    /**
     * Sets the maximum value of the distribution.
     * @param max
     */
	public void setMax(double max);
    
    /**
     * Returns the maximum value of the distribution.
     * 
     * @return
     */
	public double getMax();
	
    /**
     * Sets the mean value of the distribution (used for type "normal")
     * @param mean
     */
	public void setMean(double mean);
    
    /**
     * Returns the mean value of the distribution (used for type "normal")
     * 
     * @return
     */
	public double getMean();
	
    /**
     * Sets the variance of the distribution (used for type "normal")
     * 
     * @param variance
     */
	public void setVariance(double variance);
    
    /**
     * Returns the variance of the distribution (used for type "normal")
     */
	public double getVariance();
    
    /**
     * Sets the scenario to which this distribution belongs.
     * 
     * @param scenario
     */
    public void setScenario(IScenario scenario); 
	
}
