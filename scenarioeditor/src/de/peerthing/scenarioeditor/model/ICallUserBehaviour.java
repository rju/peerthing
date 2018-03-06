package de.peerthing.scenarioeditor.model;

/**
 * Represents a command that calls another behaviour.
 * It is used in the behaviour of a node category
 * in a scenario.
 * 
 *
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface ICallUserBehaviour extends ICommand, IScenarioObject {
    /**
     * Sets the behaviour that is called.
     * 
     * @param behaviour
     */
	public void setBehaviour(IUserBehaviour behaviour);
	
    /**
     * Returns the behaviour that is called with this command.
     * @return
     */
    public IUserBehaviour getBehaviour();
	
    /**
     * Sets the probability with that the defined behaviour 
     * is called. 1 means it is always called, 0 means it is
     * never called.
     * 
     * @param probability a number in the range from 0 to 1
     */
	public void setProbability(double probability);
    
    /**
     * Returns the probability with that the defined behaviour
     * is called. 1 means it is always called, 0 means it is
     * never called.
     * 
     * @return a number in the range from 0 to 1
     */
	public double getProbability();
	
	/**
	 * set or unset start task flag 
	 *
	 */
	public void setStartTask(boolean startTask);
	
	/**
	 * Returns true if the behaviour shall be executed in parallel.
	 * 
	 * @return
	 */
	public boolean isStartTask();
}
