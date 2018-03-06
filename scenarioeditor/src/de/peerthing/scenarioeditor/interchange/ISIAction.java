package de.peerthing.scenarioeditor.interchange;

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
public interface ISIAction extends ISICommand {

    /**
     * Returns the name of the action. This must be the name of an event that
     * is handled somewhere in the architectural model.
     * 
     * @return
     */
	public String getName();
	
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
