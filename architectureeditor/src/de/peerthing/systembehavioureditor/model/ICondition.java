package de.peerthing.systembehavioureditor.model;

import java.util.List;

/**
 * Represents a condition for a transition in the architectural model.
 * 
 * 
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface ICondition extends ITransitionContent, ISystemBehaviourObject {
    /**
     * Returns the cases that belong to this condition (without the default
     * case).
     * 
     * @return
     */
    public List<ICaseArchitecture> getCases();

    /**
     * Sets the default case.
     * 
     * @param defaultCase
     */
    public void setDefaultCase(ICaseArchitecture defaultCase);

    /**
     * Returns the default case.
     * 
     * @return
     */
    public ICaseArchitecture getDefaultCase();

    /**
     * Removes the given case from the list of cases.
     * 
     * @param caseA the case to remove
     */
    public void removeCase(ICaseArchitecture caseA);
}
