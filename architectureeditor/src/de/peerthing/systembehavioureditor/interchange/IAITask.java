package de.peerthing.systembehavioureditor.interchange;

import java.util.List;

/**
 * Represents a task that consists of states and is part of a node type in the
 * architectural model.
 *
 *
 * @author Michael Gottschalk
 *
 */
public interface IAITask extends IAITransitionTarget {
    /**
     * Sets the state in which this task starts running.
     *
     * @return
     */
    public IAIState getStartState();


    /**
     * Returns the variables defined at the task level.
     *
     * @return
     */
    public List<IAIVariable> getVariables();

    /**
     * Returns the states that belong to this task (including the start state).
     *
     * @return
     */
    public List<IAIState> getStates();

    /**
     * Returns the node to which this task belongs (the parent object).
     *
     * @return
     */
    public IAINodeType getNode();

}
