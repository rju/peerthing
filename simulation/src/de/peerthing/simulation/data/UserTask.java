/**
 *
 */
package de.peerthing.simulation.data;

import java.util.List;
import java.util.Stack;

import de.peerthing.scenarioeditor.interchange.ISICommand;
import de.peerthing.simulation.interfaces.DataFactory;
import de.peerthing.simulation.interfaces.IUserStackElement;
import de.peerthing.simulation.interfaces.IUserTask;
import de.peerthing.simulation.interfaces.IXPathObject;

/**
 * @author prefec2
 *
 */
public class UserTask extends XPathObject implements IUserTask {
	private Stack<IUserStackElement> userBehaviourProcessingStack;

	private IUserStackElement register;

	private int id;

	private String listenMark;

	/**
	 * create new user task
	 *
	 * @param sessionId
	 */
	public UserTask(int id, List<ISICommand> commandList) {
		this.userBehaviourProcessingStack = new Stack<IUserStackElement>();
		this.register = DataFactory
				.createUserStackElement(commandList, 0, null);
		this.id = id;
		this.listenMark = null;
	}

	/**
	 * Copy constructor
	 * @param copy
	 */
	public UserTask(UserTask copy) {
		userBehaviourProcessingStack = copy.userBehaviourProcessingStack;
		id = copy.id;
		listenMark = copy.listenMark;
		register = copy.register;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IUserTask#pushProcessingEnvironment(de.peerthing.simulation.interfaces.IUserStackElement)
	 */
	public void pushProcessingEnvironment(IUserStackElement env) {
		this.userBehaviourProcessingStack.push(this.register);
		this.register = env;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IUserTask#popProcessingEnvironment()
	 */
	public boolean popProcessingEnvironment() {
		if (userBehaviourProcessingStack.size() > 0) {
			this.register = this.userBehaviourProcessingStack.pop();
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IUserTask#getNextCommand()
	 */
	public ISICommand getNextCommand() {
		return this.register.getNextCommand();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IUserTask#setNextCommand(int)
	 */
	public void setNextCommand(int position) {
		this.register.setNextCommand(position);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.data.XPathObject#getId()
	 */
	public int getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IUserTask#setListenMark(java.lang.String)
	 */
	public void setListenMark(String listenMark) {
		this.listenMark = listenMark;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IUserTask#getListenMark()
	 */
	public String getListenMark() {
		return this.listenMark;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IUserTask#repeatCommandSequence()
	 */
	public boolean repeatCommandSequence() {
		/* check if we have an until condition */
		if (register.getUntilExpr() != null
				&& !this.register.getUntilExpr().equals(""))
			return !this.evaluateCondition(this.register.getUntilExpr());
		/* check if we have a max loop count value */
		if (this.register.getMaxLoopCount() > 0) { /* limited loop */
			this.register.setLoopCount(this.register.getLoopCount() + 1);
			return (this.register.getLoopCount() < this.register
					.getMaxLoopCount());
		} else if (this.register.getMaxLoopCount() < 0) /* endless loop */
			return true;
		else
			/* is not a loop */
			return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IXPathObject#getSize()
	 */
	public int getSize() {
		return 4;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IXPathObject#getElementName()
	 */
	public String getElementName() {
		return "userTask";
	}

	public String getElementStringValue() {
		return register.toString();
	}

	public IXPathObject duplicate() {
		return new UserTask(this);
	}
	
	/**
	 * check for similarity
	 */
	public boolean isSimilarTo(IXPathObject object) {
		if (object instanceof UserTask) 
			return super.isSimilarTo(object);
		else
			return false;
	}
}
