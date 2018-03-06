package de.peerthing.systembehavioureditor.interchange;

import java.util.List;

/**
 * Represents a state that belongs to a task of a node type
 * in the architectural model.
 *
 *
 * @author Michael Gottschalk
 *
 */
public interface IAIState extends IAITransitionTarget, IAIContentContainer {
 
    /**
     * Returns the transitions that belong to this state.
     *
     * @return
     */
	public List<IAITransition> getTransitions();

	/**
	 * Returns the task to which this state belongs (the parent object).
	 *
	 * @return
	 */
	public IAITask getTask();

    /**
	 * Returns how the initialization shall be evaluated
	 * @return
	 */
	public EAIInitializeEvaluation getInitializeEvaluation();

}
