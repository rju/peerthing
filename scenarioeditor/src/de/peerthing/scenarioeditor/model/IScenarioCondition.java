package de.peerthing.scenarioeditor.model;

import java.util.List;

/**
 * Represents a command (condition) that can be used
 * in the behaviour of a node category in a scenario.
 * 
 *
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 *
 */
public interface IScenarioCondition extends ICommand, IScenarioObject {
    /**
     * Returns the cases that belong to this condition
     * (without the default case).
     * 
     * @return
     */
	public List<ICase> getCases();
    
    /**
     * Sets the default case that is executed if no other
     * case is evaluated to true.
     * 
     * @param defaultCase
     */
    public void setDefaultCase(ICase defaultCase);
    
    /**
     * Returns the default case that is executed if no other
     * case is evaluated to true.
     * 
     * @return
     */
    public ICase getDefaultCase();
}
