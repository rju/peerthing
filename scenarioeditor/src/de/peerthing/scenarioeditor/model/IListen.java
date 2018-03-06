package de.peerthing.scenarioeditor.model;

public interface IListen extends ICommand, IScenarioObject{

	/**
	 * @author Patrik
     */
	public void setDistribution(IDistribution distribution);
    
    /**
     * gets the distribution that says how long the nodes have to listen
     */
	public IDistribution getDistribution();
	
	/**
	 * gets a string that discribes the event that is listened for	 
	 */	
	public String getEvent();
	
	/**
	 * set the event that is listened for by the handed string	 
	 */
	public void setEvent(String event);
}
