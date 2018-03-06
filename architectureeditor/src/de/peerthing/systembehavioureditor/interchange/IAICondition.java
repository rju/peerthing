package de.peerthing.systembehavioureditor.interchange;

import java.util.List;

/**
 * Represents a condition for a transition in the architectural model.
 *
 *
 * @author Michael Gottschalk
 */
public interface IAICondition extends IAITransitionContent {
    /**
     * Returns the cases that belong to this condition (without the default
     * case).
     *
     * @return
     */
    public List<IAICase> getCases();

    /**
     * Returns the default case.
     *
     * @return
     */
    public IAICase getDefaultCase();
}
