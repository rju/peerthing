package de.peerthing.systembehavioureditor.model;

/**
 * Represents a variable that can be used in the declaration of nodes or tasks
 * in the architectural model.
 * 
 * 
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface IVariable extends ISystemBehaviourObject {
    /**
     * Returns the name of the variable.
     * 
     * @return
     */
    public String getName();

    /**
     * Sets the name of the variable.
     * 
     * @return
     */
    public void setName(String name);


    /**
     * Returns the initial value of this variable
     * @return
     */
    public String getInitialValue();
    
    /**
     * Sets the initial value of this variable.
     *
     * @param value
     */
    public void setInitialValue(String value);

    /**
     * returns the Task of this Variable
     * 
     * @return task
     *
     */
	public ITask getTask();

	public void setTask(ITask task);

	public void setNode(INodeType node);

	public INodeType getNode();
}
