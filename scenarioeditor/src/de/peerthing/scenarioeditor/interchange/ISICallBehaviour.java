package de.peerthing.scenarioeditor.interchange;

/**
 * Represents a command that calls another behaviour.
 * It is used in the behaviour of a node category
 * in a scenario.
 * 
 *
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface ISICallBehaviour extends ISICommand {
    /**
     * Returns the behaviour that is called with this command.
     * @return
     */
    public ISIBehaviour getBehaviour();
	
    /**
     * Returns the probability with that the defined behaviour
     * is called. 1 means it is always called, 0 means it is
     * never called.
     * 
     * @return a number in the range from 0 to 1
     */
	public double getProbability();
	
	/**
	 * 
	 * @return Returns the name of the behaviour
	 */
	public String getBehaviourName();
	
	/**
	 * Returns true if the behaviour shall be executed in parallel.
	 * 
	 * @return
	 */
	public boolean isStartTask();
}
