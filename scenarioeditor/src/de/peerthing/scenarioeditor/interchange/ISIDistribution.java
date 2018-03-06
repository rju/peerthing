package de.peerthing.scenarioeditor.interchange;

/**
 * Represents a distribution that can be of type normal
 * or uniform. It can be used in a scenario.
 * 
 *
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface ISIDistribution {
    /**
     * Defines the types that a distribution can have.
     */
	public enum DistributionType { uniform, normal}
	
    /**
     * Returns the type of the distribution
     * 
     * @return
     */
    public DistributionType getType();
	
    /**
     * Returns the minimal value of the distribution.
     * 
     * @return
     */
	public double getMin();
	
    /**
     * Returns the maximum value of the distribution.
     * 
     * @return
     */
	public double getMax();
	
    /**
     * Returns the mean value of the distribution (used for type "normal")
     * 
     * @return
     */
	public double getMean();
	

    /**
     * Returns the variance of the distribution (used for type "normal")
     */
	public double getVariance();
	
}
