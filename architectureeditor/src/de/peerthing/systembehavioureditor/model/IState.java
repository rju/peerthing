package de.peerthing.systembehavioureditor.model;

import java.util.List;

import de.peerthing.systembehavioureditor.interchange.EAIInitializeEvaluation;

/**
 * Represents a state that belongs to a task of a node type
 * in the architectural model.
 * 
 *
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 *
 */
public interface IState extends ITransitionTarget, IContentContainer {
    
	
    /**
     * Returns the transitions that belong to this state.
     * 
     * @return
     */
	public List<ITransition> getTransitions();
	
	/**
	 * Returns the task to which this state belongs (the parent object).
	 * 
	 * @return
	 */
	public ITask getTask();
	
	/**
	 * Sets the task to which this state belongs (the parent object).
	 * 
	 * @param task
	 */
	public void setTask(ITask task);
	
	/**
	 * Returns the x coordinate of the state in the editor's context.
	 *
	 * @return the x coordinate
	 */
	public int getX();
	
	/**
	 * Returns the y coordinate of the state in the editor's context.
	 *
	 * @return the y coordinate
	 */
	public int getY();
	
	/**
	 * Set the x coordinate of the state in the editor's context.
	 * @param x the x coordinate
	 */
	public void setX(int x);
	
	/**
	 * Set the y coordinate of the state in the editor's context.
	 * @param y the y coordinate
	 */
	public void setY(int y);
	
	
	/**
	 * Sets how the initialization should be evaluated.
	 * @param eval
	 */
	public void setInitializeEvaluation(EAIInitializeEvaluation eval);
	
    /**
	 * Returns how the initialization shall be evaluated
	 * @return
	 */
	public EAIInitializeEvaluation getInitializeEvaluation();
	
	/**
	 * Adds an incoming Transition.
	 *
	 */
	public void addTransitionIncoming(ITransition tran);
	
    /**
     * Adds a Transition to a State.
     */
	public void addTransitionOutgoing(ITransition tran);
}
