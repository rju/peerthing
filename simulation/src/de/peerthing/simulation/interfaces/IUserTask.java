/**
 * 
 */
package de.peerthing.simulation.interfaces;

import de.peerthing.scenarioeditor.interchange.ISICommand;

/**
 * @author prefec2
 * 
 */
public interface IUserTask extends ITask {
	/**
	 * push the current processing environment onto a stack and load a new
	 * processing environment
	 * 
	 * @param env
	 *            the new processing environment
	 */
	public void pushProcessingEnvironment(IUserStackElement env);

	/**
	 * pop a previous stored processing environment from the stack
	 */
	public boolean popProcessingEnvironment();

	/**
	 * 
	 * @return returns the next command of the active command sequence
	 */
	public ISICommand getNextCommand();

	/**
	 * Set the execution pointer to position
	 * 
	 * @param position
	 */
	public void setNextCommand(int position);

	/**
	 * If the task listens for a special event, then listen mark is set. This
	 * method allows to set the listen mark.
	 * 
	 * @param listenMark
	 */
	public void setListenMark(String listenMark);

	/**
	 * To check if the task listens for a special event, the listenMark can be
	 * checked with this method.
	 * 
	 * @return null if no mark is set and a mark if listenMark is set.
	 */
	public String getListenMark();

	/**
	 * Evaluates the until expression of a loop and checkes whether the loop
	 * count has reached the loop count limit
	 * 
	 * @return Returns true if the command sequence shall be repeated else false
	 */
	public boolean repeatCommandSequence();
}
