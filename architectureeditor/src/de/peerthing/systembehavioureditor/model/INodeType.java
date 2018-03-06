package de.peerthing.systembehavioureditor.model;

import java.util.List;

/**
 * Represents a node type in the architectural model.
 * 
 * 
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface INodeType extends ISystemBehaviourObject {
    /**
     * Returns the list of variables that belong to this node.
     * 
     * @return the variables
     */
    public List<IVariable> getVariables();

    /**
     * Returns the tasks that belong to this node.
     * 
     * @return
     */
    public List<ITask> getTasks();

    /**
     * Sets the first task to be executed. The start state in this task is taken
     * as the start state at the beginning of a simulation run.
     * 
     * @param startTask
     */
    public void setStartTask(ITask startTask);

    /**
     * Returns the first task to be executed. The start state in this task is
     * taken as the start state at the beginning of a simulation run.
     * 
     * @return
     */
    public ITask getStartTask();

    /**
     * Returns the name of the node. This name is referenced in a scenario for
     * the definition of node categories.
     * 
     * @return the name
     */
    public String getName();

    /**
     * Sets the name of the node (e.g. "Peer" or "Server"). This name is
     * referenced in a scenario for the definition of node categories.
     * 
     * @param name
     *            the name
     */
    public void setName(String name);

    /**
     * Sets the SystemBehaviour to which this node belongs (the parent object).
     * 
     * @param architecture
     */
    public void setArchitecture(ISystemBehaviour architecture);

    /**
     * Returns the SystemBehaviour to which this node belongs (the parent object).
     * 
     * @return
     */
    public ISystemBehaviour getArchitecture();
    
    /**
     * Sets a List of Varaibles
     * 
     * @param vars
     */
	public void setVariables(List<IVariable> vars);

}
