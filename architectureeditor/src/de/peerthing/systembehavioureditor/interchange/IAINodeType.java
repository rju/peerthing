package de.peerthing.systembehavioureditor.interchange;

import java.util.List;

/**
 * Represents a node type in the architectural model.
 *
 *
 * @author Michael Gottschalk
 */
public interface IAINodeType {
    /**
     * Returns the list of variables that belong to this node.
     *
     * @return the variables
     */
    public List<IAIVariable> getVariables();

    /**
     * Returns the tasks that belong to this node.
     *
     * @return
     */
    public List<IAITask> getTasks();


    /**
     * Returns the first task to be executed. The start state in this task is
     * taken as the start state at the beginning of a simulation run.
     *
     * @return
     */
    public IAITask getStartTask();

    /**
     * Returns the name of the node. This name is referenced in a scenario for
     * the definition of node categories.
     *
     * @return the name
     */
    public String getName();

    /**
     * Returns the SystemBehaviour to which this node belongs (the parent object).
     *
     * @return
     */
    public IAIArchitecture getArchitecture();
}
