package de.peerthing.simulation.data;

import java.util.Hashtable;

import de.peerthing.simulation.interfaces.IEvent;
import de.peerthing.simulation.interfaces.IMessage;
import de.peerthing.simulation.interfaces.INode;
import de.peerthing.simulation.interfaces.IPort;
import de.peerthing.simulation.interfaces.ISession;
import de.peerthing.simulation.interfaces.ISystemTask;
import de.peerthing.simulation.interfaces.IXPathAttribute;
import de.peerthing.simulation.interfaces.IXPathObject;
import de.peerthing.systembehavioureditor.interchange.IAIState;
import de.peerthing.systembehavioureditor.interchange.IAITask;

/**
 * Implements a runtime task.
 *
 * @author prefec2
 * @review Patrik, 2006-03-25
 */
public class SystemTask extends VariableContainer implements ISystemTask {

	private int id; /* task id */

	private IAIState activeState; /* state to be processed */

	private IAITask taskImpl; /* architecture of the task */

	private long time; /* delta time of a task */

	private Hashtable<String, IEvent> timeoutEvents;

	/**
	 * @param id
	 *            of the task
	 * @param state
	 *            is the active state of the task
	 * @param taskImpl
	 *            is the implementation of a task from the architecture
	 *            description
	 */
	public SystemTask(int id, IAIState state, IAITask taskImpl) {
		super();
		this.id = id;
		this.activeState = state;
		this.taskImpl = taskImpl;
		addAttribute(new XPathAttribute("id", id));
		addAttribute(new XPathAttribute("activeState", state));

	}

	/**
	 * Copy constructor
	 *
	 * @param copy
	 */
	public SystemTask(SystemTask copy) {
		id = copy.id;
		activeState = copy.activeState;
		taskImpl = copy.taskImpl;
		time = copy.time;
		timeoutEvents = copy.timeoutEvents;

		for (IXPathAttribute attr: copy.getAttributeAxisList()) {
			addAttribute(attr);
		}

		for (IXPathObject elem : copy.getChildAxis()) {
			addElement(elem);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see simulation.ISimulation#getElementName()
	 */
	public String getElementName() {
		return "task";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see simulation.ISimulation#isElement()
	 */
	public boolean isElement() {
		return true;
	}

	/**
	 * @return Returns the id.
	 */
	public int getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.ISystemTask#getActiveState()
	 */
	public IAIState getActiveState() {
		return this.activeState;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.ISystemTask#setActiveState(de.peerThing.modeling.model.IState)
	 */
	public void setActiveState(IAIState state) {
		this.activeState = state;
		setAttribute("activeState", state);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.ISystemTask#getTaskDescription()
	 */
	public IAITask getTaskImplementation() {
		return this.taskImpl;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.ISystemTask#setTaskDescription(de.peerThing.modeling.model.ITask)
	 */
	public void setTaskImplementation(IAITask task) {
		this.taskImpl = task;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.ISystemTask#getNode()
	 */
	public INode getNode() {
		return (INode) this.getParent();
	}

	/*
	 *  (non-Javadoc)
	 * @see de.peerthing.simulation.interfaces.ISystemTask#addSession(int, de.peerthing.simulation.interfaces.IPort)
	 */
	public void addSession(long id, IPort port) {
		this.addElement(new Session(id, port));
	}

	/*
	 *  (non-Javadoc)
	 * @see de.peerthing.simulation.interfaces.ISystemTask#setMessage(de.peerthing.simulation.interfaces.IMessage)
	 */
	public void setMessage(IMessage message) {
		for (IXPathObject element : this.getChildAxis()) {
			if (element instanceof Message) {
				this.removeElement((XPathObject) element);
				break;
			}
		}
		this.addElement((XPathObject) message);
		/* only add a session handler if there is no handler for the given id */
		if (this.evaluateCondition("count(./session[@id="
				+ message.getSessionId() + "])=0"))
			this.addSession(message.getSessionId(), message.getSource());
	}
	
	/*
	 *  (non-Javadoc)
	 * @see de.peerthing.simulation.interfaces.ISystemTask#removeMessage()
	 */
	public void removeMessage() {
		for (IXPathObject element : this.getChildAxis()) {
			if (element instanceof Message) {
				this.removeElement((XPathObject) element);
				break;
			}
		}
	}

	/*
	 *  (non-Javadoc)
	 * @see de.peerthing.simulation.interfaces.ISystemTask#getMessage()
	 */
	public IMessage getMessage() {
		for (IXPathObject element : this.getChildAxis()) {
			if (element instanceof Message)
				return (IMessage) element;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.ISystemTask#setTaskTime(long)
	 */
	public void setTaskTime(long time) {
		this.time = time;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.ISystemTask#getTaskTime()
	 */
	public long getTaskTime() {
		return this.time;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.ISystemTask#advanceTaskTime(long)
	 */
	public void advanceTaskTime(long time) {
		this.time += time;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.ISystemTask#setTimeoutEvent(java.lang.String,
	 *      de.peerthing.simulation.interfaces.IEvent)
	 */
	public void setTimeoutEvent(String name, IEvent event) {
		if (timeoutEvents == null) {
			timeoutEvents = new Hashtable<String, IEvent>();
		}
		if (event != null) {
			timeoutEvents.put(name, event);
		} else {
			timeoutEvents.remove(name);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.ISystemTask#getTimoutEvent(java.lang.String)
	 */
	public IEvent getTimoutEvent(String name) {
		if (timeoutEvents != null) {
			return timeoutEvents.get(name);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.ISystemTask#getSessionByPort(de.peerthing.simulation.interfaces.IPort)
	 */
	public ISession getSessionByPort(IPort port) {
		for (IXPathObject obj : this.elementList) {
			if (obj instanceof Session)
				if (((Session) obj).getPort() == port)
					return (ISession) obj;
		}

		return null;
	}

	public IXPathObject duplicate() {
		return new SystemTask(this);
	}
	
	/**
	 * check for similarity
	 */
	public boolean isSimilarTo(IXPathObject object) {
		if (object instanceof SystemTask) 
			return super.isSimilarTo(object);
		else
			return false;
	}
	
	public void removeSession(long id) {
		for(IXPathObject obj : this.elementList) {
			if (obj instanceof ISession) {
				if (((ISession)obj).getSessionId() == id) {
					this.elementList.remove(obj);
					return;
				}
			}	
		}
	}
}
