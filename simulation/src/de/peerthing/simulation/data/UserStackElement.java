/*
 * Created on ??
 * Changed on 22.06.2006
 */
package de.peerthing.simulation.data;

import java.util.List;

import de.peerthing.scenarioeditor.interchange.ISICommand;
import de.peerthing.simulation.interfaces.IUserStackElement;

/**
 * implements IUserStackElement
 * 
 * @author Michael Gottschalk
 * 
 */
public class UserStackElement implements IUserStackElement {
	private List<ISICommand> commandList;

	private int position;

	private int loopCount;

	private int maxLoopCount;

	private String untilExpr;

	public UserStackElement(List<ISICommand> commandList, int maxLoopCount,
			String untilExpr) {
		super();
		this.commandList = commandList;
		this.position = 0;
		this.loopCount = 0;
		this.maxLoopCount = maxLoopCount;
		this.untilExpr = untilExpr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.interfaces.IUserStackElement#getNextCommand()
	 */
	public ISICommand getNextCommand() {
		if (this.position < this.commandList.size())
			return this.commandList.get(this.position++);
		else
			return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.interfaces.IUserStackElement#setNextCommand(int)
	 */
	public void setNextCommand(int position) {
		this.position = position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.interfaces.IUserStackElement#getLoopCount()
	 */
	public int getLoopCount() {
		return this.loopCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.interfaces.IUserStackElement#setLoopCount(int)
	 */
	public void setLoopCount(int loopCount) {
		this.loopCount = loopCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.interfaces.IUserStackElement#getMaxLoopCount()
	 */
	public int getMaxLoopCount() {
		return this.maxLoopCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.interfaces.IUserStackElement#setMaxLoopCount(int)
	 */
	public void setMaxLoopCount(int loopCount) {
		this.maxLoopCount = loopCount;
	}

	/**
	 * @return Returns the until expression.
	 */
	public String getUntilExpr() {
		return untilExpr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String ret = "";

		if (commandList.size() > position) {
			ret = "Next command: " + commandList.get(position).getCommandName()
					+ " (Position " + position + ")\n\nCommand list:\n";
		} else {
			ret = "At the end of the following command list:\n";
		}

		int i = 0;
		for (ISICommand cmd : commandList) {
			ret += i++ + ": " + cmd.getCommandName() + "\n";
		}

		return ret;
	}
}
