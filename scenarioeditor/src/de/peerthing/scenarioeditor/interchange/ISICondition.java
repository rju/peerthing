package de.peerthing.scenarioeditor.interchange;

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
public interface ISICondition extends ISICommand {
    /**
     * Returns the cases that belong to this condition
     * (without the default case).
     * 
     * @return
     */
	public List<ISICase> getCases();
    
    /**
     * Returns the default case that is executed if no other
     * case is evaluated to true.
     * 
     * @return
     */
    public ISICase getDefaultCase();
}
