package de.peerthing.systembehavioureditor.model;

import java.util.List;

/**
 * Represents a task that consists of states and is part of a node type in the
 * architectural model.
 * 
 * 
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 * 
 */
public interface ITask extends ITransitionTarget {
    /**
     * Sets the state in which this task starts running.
     * 
     * @return
     */
    public IState getStartState();

    /**
     * Sets the state in which this task starts running.
     * 
     * @param state
     *            the start state
     */
    public void setStartState(IState state);

    /**
     * Returns the variables defined at the task level.
     * 
     * @return
     */
    public List<IVariable> getVariables();

    /**
     * Returns the states that belong to this task (including the start state).
     * 
     * @return
     */
    public List<IState> getStates();

    /**
     * Returns the node to which this task belongs (the parent object).
     * 
     * @return
     */
    public INodeType getNode();

    /**
     * Sets the node to which this task belongs (the parent object).
     * 
     * @param node
     */
    public void setNode(INodeType node);

    /**
     * Adds a state to a task. Use this instead of adding the state to the field
     * manuelly because of side effects that need to be taken care of in the
     * editor.
     * 
     * @param state
     */
    public void addState(IState state);

    /**
     * Sets the x coordinate of this task. This is only used in the state chart
     * editor.
     * 
     * @param x
     */
    public void setX(int x);

    /**
     * Sets the y coordinate of this task. This is only used in the state chart
     * editor.
     * 
     * @param y
     */
    public void setY(int y);

    /**
     * Returns the x coordinate of this task. This is only used in the state
     * chart editor.
     * 
     * @return
     */
    public int getX();

    /**
     * Returns the y coordinate of this task. This is only used in the state
     * chart editor.
     * 
     * @return
     */
    public int getY();

    /**
     * Removes the given state from this task.
     * 
     * @param state
     */
    public void removeState(IState state);

    /**
     * Sets a List of Varaibles
     * 
     * @param vars
     */
	public void setVariables(List<IVariable> vars);

}
