package de.peerthing.scenarioeditor.model;

import java.util.Map;

/**
 * Represents an action that can be defined as 
 * a command in the behaviour of a node category
 * in a scenario.
 * 
 *
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 *
 */
public interface IUserAction extends ICommand, IScenarioObject{
    /**
     * Sets the name of the action. This must be the name of an event that
     * is handled somewhere in the architectural model.
     * 
     * @param name
     */
	public void setName(String name);
    
    /**
     * Returns the name of the action. This must be the name of an event that
     * is handled somewhere in the architectural model.
     * 
     * @return
     */
	public String getName();
	
    /**
     * Sets the probability with which the action is executed. 
     * 
     * @param probability A number in the range from 0 to 1
     */
	public void setProbability(double probability);
    
    /**
     * Returns the probability with which the action is executed. 
     * 
     * @return A number in the range from 0 to 1
     */    
	public double getProbability();
	
	/**
	 * Returns the parameters for this action. The key is the
     * name of the parameter, the value is the parameter's value.
     * 
	 * @return
	 */
	public Map<String, String> getParameters();
}
