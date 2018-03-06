package de.peerthing.simulation.interfaces;

import de.peerthing.scenarioeditor.interchange.ISICommand;

/**
 * An element which is part of the scenario execution stack of a scenario node.
 * The stack is needed for the possibility of resuming the execution of the
 * scenario description at a random command.
 * 
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 * 
 */
public interface IUserStackElement {

	/**
	 * Returns the next command that should be executed.
	 * 
	 * @return
	 */
	public ISICommand getNextCommand();

	/**
	 * Set the address of the next command to be executed.
	 * 
	 * @param command
	 */
	public void setNextCommand(int position);

	/**
	 * If the command container is a loop, returns the current count of the
	 * loop.
	 * 
	 * @return
	 */
	public int getLoopCount();

	/**
	 * If the command container is a loop, the current count of the loop should
	 * be set here.
	 * 
	 * @param count
	 */
	public void setLoopCount(int loopCount);

	/**
	 * If the command container is a loop, returns the maximum number of
	 * iterations for this loop. This is a random value which is defined the
	 * first time a loop is entered.
	 * 
	 * @return
	 */
	public int getMaxLoopCount();

	/**
	 * If the command container is a loop, the maximum number of iterations for
	 * this loop should be set with this method the first time a loop is
	 * entered.
	 * 
	 * @param count
	 */
	public void setMaxLoopCount(int loopCount);

	/**
	 * 
	 * @return returns the until expression
	 */
	public String getUntilExpr();
}
