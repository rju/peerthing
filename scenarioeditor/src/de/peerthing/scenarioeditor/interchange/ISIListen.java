package de.peerthing.scenarioeditor.interchange;

/**
 * Represents a command that can be used in the behaviour
 * of a node category.
 * 
 *
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface ISIListen extends ISICommand {
    /**
     * Returns the amount of the delay in milliseconds. This
     * is expressed as a distribution so that the delay can
     * be a random value.
     * 
     * @return
     */
	public ISIDistribution getDistribution();
	
	public String getEvent();
}
