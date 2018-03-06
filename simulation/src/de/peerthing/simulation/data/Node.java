package de.peerthing.simulation.data;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.scenarioeditor.interchange.ISIConnectionCategory;
import de.peerthing.simulation.interfaces.INode;

import de.peerthing.simulation.interfaces.IXPathContainer;
import de.peerthing.simulation.interfaces.IPort;
import de.peerthing.simulation.interfaces.IResource;
import de.peerthing.simulation.interfaces.ISystemTask;
import de.peerthing.simulation.interfaces.ITransmissionLog;
import de.peerthing.simulation.interfaces.IUserTask;
import de.peerthing.simulation.interfaces.IXPathAttribute;
import de.peerthing.simulation.interfaces.IXPathObject;

/**
 * The class represents a node in the simulation runtime system.
 *
 * @author prefec2
 * @review Patrik, 2006-03-25
 */
public class Node extends VariableContainer implements INode {

	private XPathAttribute systemBehaviourName;

	private XPathAttribute id;

	private XPathAttribute connectionState;

	private String userBehaviourName; /* name of the user behaviour */

	private long upstreamUsable; /* time when the upstream is free for use */

	private ArrayList<TimeFrame> downstreamUsable; /*
													 * time slots where the
													 * downstream is free
													 */

	private Register sendMessageRegister;

	private Register receiveMessageRegister;

	private ArrayList<SystemTask> taskList; // list of tasks

	private ArrayList<IUserTask> userTaskList; // list of user tasks

	private ArrayList<Resource> resourceList; // list of resources

	/* connectionCategory category */
	private ISIConnectionCategory connectionCategory;

	private List<IPort> portList;

	/**
	 * general constructor for nodes
	 *
	 * @param id
	 *            of the node
	 * @param connectionCategory
	 *            connection category definition for this node
	 */
	public Node(int id, String typeName, String userBehaviourName,
			ISIConnectionCategory connectionCategory) {
		super();
		this.upstreamUsable = 0;
		this.downstreamUsable = new ArrayList<TimeFrame>();

		this.downstreamUsable.add(new TimeFrame(0, Long.MAX_VALUE));

		this.sendMessageRegister = new Register();
		this.receiveMessageRegister = new Register();
		this.taskList = new ArrayList<SystemTask>();
		this.userTaskList = new ArrayList<IUserTask>();
		this.resourceList = new ArrayList<Resource>();
		this.connectionCategory = connectionCategory;
		this.userBehaviourName = userBehaviourName;
		this.portList = new ArrayList<IPort>();
		this.attributeList.add((this.connectionState = new XPathAttribute(
				"connection", "offline")));
		this.attributeList.add((this.systemBehaviourName = new XPathAttribute(
				"type", typeName)));
		this.attributeList.add((this.id = new XPathAttribute("id", id)));
	}

	/**
	 * Copy constructor. Does not make a very deep copy...
	 * @param copy
	 */
	public Node(Node copy) {
		super();
		this.upstreamUsable = copy.upstreamUsable;
		this.downstreamUsable = copy.downstreamUsable;

		this.downstreamUsable.add(new TimeFrame(0, Long.MAX_VALUE));

		this.sendMessageRegister = copy.sendMessageRegister;
		this.receiveMessageRegister = copy.receiveMessageRegister;
		this.receiveMessageRegister = new Register();
		this.taskList = new ArrayList<SystemTask>();
		this.userTaskList = new ArrayList<IUserTask>();
		this.resourceList = new ArrayList<Resource>();
		this.connectionCategory = copy.connectionCategory;
		this.userBehaviourName = copy.userBehaviourName;
		this.portList = new ArrayList<IPort>();
		this.attributeList.add((this.connectionState = new XPathAttribute(
				"connection", copy.connectionState.getValue())));
		this.attributeList.add((this.systemBehaviourName = new XPathAttribute(
				"type", copy.systemBehaviourName.getValue())));
		this.attributeList.add((this.id = new XPathAttribute("id", copy.getId())));
	
		setDocument(copy.getDocument());

		for (IXPathObject obj : copy.getChildAxis()) {
			IXPathObject item = obj.duplicate();
			if (item instanceof ISystemTask)
				this.addSystemTask((ISystemTask)item);
			else if (item instanceof IUserTask)
				this.addUserTask((IUserTask)item);
			else if (item instanceof IResource)
				this.addResource((IResource)item);
			else if (item instanceof IPort)
				this.addPort((IPort)item);
			else if (item instanceof IXPathContainer)
				this.addVariable((IXPathContainer)item);
			else
				addElement(item);
		}
		
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#addSystemTask(de.peerthing.simulation.interfaces.ISystemTask)
	 */
	public void addSystemTask(ISystemTask task) {
		this.taskList.add((SystemTask) task);
		this.addElement((SystemTask) task);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#removeSystemTask(de.peerthing.simulation.interfaces.ISystemTask)
	 */
	public void removeSystemTask(ISystemTask task) {
		this.taskList.remove(task);
		this.removeElement(task);
	}

	/**
	 * allocate a special ammount of downstream
	 *
	 * @param begin
	 * @param end
	 */
	private void allocateDownstream(long begin, long end) {
		/* find a frame, where both values lie in */
		for (int i = 0; i < this.downstreamUsable.size(); i++) {
			TimeFrame frame = this.downstreamUsable.get(i);
			if ((frame.isInside(begin)) && (frame.isInside(end))) {
				/* found a frame */
				this.downstreamUsable.add(i, new TimeFrame(frame.getBegin(),
						begin - 1));
				frame.setBegin(end + 1);
			}
		}
	}

	/**
	 * allocate some upstream
	 *
	 * @param end
	 */
	private void allocateUpstream(long end) {
		this.upstreamUsable = end;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#reserveDownstream(long,
	 *      long)
	 */
	public void reserveDownstream(long begin, long end) {
		this.allocateDownstream(begin, end);
		if (this.connectionCategory.getDuplex() == ISIConnectionCategory.DuplexOption.half)
			this.allocateUpstream(end);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#reserveUpstream(long)
	 */
	public void reserveUpstream(long begin, long end) {
		this.allocateUpstream(end);
		if (this.connectionCategory.getDuplex() == ISIConnectionCategory.DuplexOption.half)
			this.allocateDownstream(begin, end);
	}

	/**
	 * check if for a specific time, downstream is avaliable
	 *
	 * @param begin
	 * @param end
	 * @return
	 */
	private boolean checkDownstreamAvailability(long begin, long end) {
		for (TimeFrame frame : this.downstreamUsable) {
			if ((frame.isInside(begin)) && (frame.isInside(end)))
				return true;
		}

		return false;
	}

	/**
	 * check if for a specific time, upstream is avaliable
	 *
	 * @param begin
	 * @param end
	 * @return
	 */
	private boolean checkUpstreamAvailability(long begin, long end) {
		return ((begin >= this.upstreamUsable) && (end >= begin));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#isDownstreamAvailable(long,
	 *      long)
	 */
	public boolean isDownstreamAvailable(long begin, long end) {
		if (this.connectionCategory.getDuplex() == ISIConnectionCategory.DuplexOption.full)
			return this.checkDownstreamAvailability(begin, end);
		else { /* downstream has to be free too */
			return this.checkUpstreamAvailability(begin, end)
					&& this.checkDownstreamAvailability(begin, end);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#isUpstreamAvailable(long,
	 *      long)
	 */
	public boolean isUpstreamAvailable(long begin, long end) {
		if (this.connectionCategory.getDuplex() == ISIConnectionCategory.DuplexOption.full)
			return this.checkUpstreamAvailability(begin, end);
		else { /* downstream has to be free too */
			return this.checkUpstreamAvailability(begin, end)
					&& this.checkDownstreamAvailability(begin, end);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#addResource(de.peerthing.simulation.interfaces.IResourceDefinition)
	 */
	public void addResource(IResource resource) {
		this.resourceList.add((Resource) resource);
		this.addElement((Resource) resource);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see simulation.ISimulation#getElementName()
	 */
	public String getElementName() {
		return "node";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see simulation.ISimulation#isElement()
	 */
	public boolean isElement() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#setId(int)
	 */
	public void setId(int id) {
		this.id.setValue(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.data.XPathObject#getId()
	 */
	public int getId() {
		return (Integer) this.id.getAttributeValue();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#setConnectionCategory(de.peerthing.scenarioeditor.interchange.ISIConnectionCategory)
	 */
	public void setConnectionCategory(ISIConnectionCategory connectionCategory) {
		this.connectionCategory = connectionCategory;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#getConnectionCategory()
	 */
	public ISIConnectionCategory getConnectionCategory() {
		return this.connectionCategory;
	}

	/**
	 * search for a system task by id
	 *
	 * @param id
	 *            id of the task
	 * @return Returns a task on success or null when no such task exists
	 */
	public ISystemTask getSystemTask(int id) {
		for (ISystemTask task : this.taskList) {
			if (task.getId() == id)
				return task;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#getResource(int)
	 */
	public IResource getResource(int id) {
		for (Resource resource : this.resourceList) {
			if (resource.getId() == id)
				return resource;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#removeResource(int)
	 */
	public void removeResource(int id) {
		/*
		 * in order to avoid ConcurrentModificationException the java 1.5
		 * iterator for(Type x: iterator) cannot be used
		 */
		for (int i = 0; i < this.resourceList.size(); i++) {
			if (this.resourceList.get(i).getId() == id)
				this.resourceList.remove(i);
		}
	}

	/**
	 * @return Returns the receiveMessageRegister.
	 */
	public Register getReceiveMessageRegister() {
		return receiveMessageRegister;
	}

	/**
	 * @return Returns the sendMessageRegister.
	 */
	public Register getSendMessageRegister() {
		return sendMessageRegister;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#addUserTask(de.peerthing.simulation.interfaces.IUserTask)
	 */
	public void addUserTask(IUserTask task) {
		this.userTaskList.add(task);
		addElement(task);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#getUserTaskList()
	 */
	public ArrayList<IUserTask> getUserTaskList() {
		return this.userTaskList;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#getSystemBehaviourName()
	 */
	public String getSystemBehaviourName() {
		return (String) this.systemBehaviourName.getAttributeValue();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#getUserBevahiourName()
	 */
	public String getUserBevahiourName() {
		return this.userBehaviourName;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#getConnectionCategoryName()
	 */
	public String getConnectionCategoryName() {
		return this.connectionCategory.getName();
	}

	/**
	 * Get the port which points to a certain remote node from the given task
	 *
	 * @param task
	 * @param remoteNode
	 *
	 * @return returns the port on success or null if no such port exists
	 */
	private IPort getPortByReferenceTask(ISystemTask task, INode remoteNode) {
		for (IPort port : this.portList) {
			if (port.hasReference(task) && (port.getRemoteNode() == remoteNode))
				return port;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#sendMessage(java.lang.String,
	 *      int, long, int, de.peerthing.simulation.interfaces.ISystemTask,
	 *      de.peerthing.simulation.interfaces.INode, java.util.List)
	 */
	public ITransmissionLog sendMessage(String name, long session, long time,
			int size, ISystemTask sourceTask, INode destinationNode,
			List<IXPathContainer> parameterList) {
		/*
		 * 1. check if we have a connection to the destination node from the
		 * source task
		 */
		IPort localPort = this.getPortByReferenceTask(sourceTask,
				destinationNode);
		IPort remotePort;
		/* 2. if we have no connection, create one */
		if (localPort == null) {
			localPort = new Port(destinationNode);
			remotePort = new Port(this);
			this.addPort(localPort);
			destinationNode.addPort(remotePort);
			localPort.setRemotePort(remotePort);
			localPort.assignTask(sourceTask);
			remotePort.setRemotePort(localPort);
			remotePort.assignTask(destinationNode.getPrimarySystemTask());
		} else
			remotePort = localPort.getRemotePort();

		Message message = new Message(name, session, time, localPort,
				remotePort, parameterList);
		if (size != 0)
			message.setSize(size);
		else
			throw new RuntimeException("Message has no size.");
		ITransmissionLog transmission = new TransmissionLog(message.getSize(),
				(Message) message);
		this.sendMessageRegister.addTransmission(transmission);
		return transmission;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#sendMessage(java.lang.String,
	 *      int, long, int, de.peerthing.simulation.interfaces.ISystemTask,
	 *      de.peerthing.simulation.interfaces.IPort, java.util.List)
	 */
	public ITransmissionLog sendMessage(String name, long session, long time,
			int size, ISystemTask sourceTask, IPort remotePort,
			List<IXPathContainer> parameterList) {
		IPort localPort = remotePort.getRemotePort();
		Message message = new Message(name, session, time, localPort,
				remotePort, parameterList);
		if (size != 0)
			message.setSize(size);
		else
			throw new RuntimeException("Message has no size.");
		ITransmissionLog transmission = new TransmissionLog(message.getSize(),
				(Message) message);
		this.sendMessageRegister.addTransmission(transmission);
		return transmission;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#getPrimarySystemTask()
	 */
	public ISystemTask getPrimarySystemTask() {
		return this.taskList.get(0);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#getNextFreeUpstreamTime(long,
	 *      long)
	 */
	public long getNextFreeUpstreamTime(long time, long duration) {
		if (this.connectionCategory.getDuplex() == ISIConnectionCategory.DuplexOption.full)
			return this.upstreamUsable;
		else {
			long result = this.upstreamUsable;
			for (TimeFrame frame : this.downstreamUsable) {
				if ((frame.isInside(result))
						&& (frame.isInside(result + duration)))
					return result;
			}

			return result;
		}
	}

	/**
	 * Returns a time where a transport of a packet is possible or -1 on error
	 *
	 * returns a poitive time value or -1
	 */
	public long getNextFreeDownstreamTime(long time, long duration) {
		if (this.connectionCategory.getDuplex() == ISIConnectionCategory.DuplexOption.half)
			time = this.upstreamUsable;

		/*
		 * criteria: - skip all time frames which end before time - check if the
		 * 1. frame is long enough - if this fails check next frame - etc.
		 */
		TimeFrame frame = null;
		int i;
		for (i = 0; i < this.downstreamUsable.size(); i++) {
			frame = this.downstreamUsable.get(i);
			if (frame.getEnd() > time) /* 1. criteria matched */
				break;
		}

		if (frame != null) {
			if (!frame.isInside(time)) /* check if we have found a frame */
				time = frame.getBegin(); /* we haven't, take the last */

			while (i < this.downstreamUsable.size()) {
				if (frame.isInside(time) && frame.isInside(time + duration))
					return time;
				else {
					if ((i + 1) < this.downstreamUsable.size())
						frame = this.downstreamUsable.get(++i);
					else
						return -1;
					time = frame.getBegin();
				}
			}
			/* if we are here, we failed */
			return -1; /* no free slot */
		} else { /* serious error */
			return -1;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#getConnectionState()
	 */
	public String getConnectionState() {
		for (IXPathAttribute attr : this.attributeList) {
			if (attr.getAttributeName().equals("connection")) {
				return attr.getAttributeStringValue();
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#setConnectionState(java.lang.String)
	 */
	public void setConnectionState(String value) {
		this.connectionState.setValue(value);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#addPort(de.peerthing.simulation.interfaces.IPort)
	 */
	public void addPort(IPort port) {
		this.portList.add(port);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#removePort(de.peerthing.simulation.interfaces.IPort)
	 */
	public void removePort(IPort port) {
		this.portList.remove(port);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.INode#duplicatePortHandles(de.peerthing.simulation.interfaces.ISystemTask,
	 *      de.peerthing.simulation.interfaces.ISystemTask)
	 */
	public void duplicatePortHandles(ISystemTask oldTask, ISystemTask newTask) {
		for (IPort port : this.portList) {
			if (port.hasReference(oldTask))
				port.assignTask(newTask);
		}
	}

	public IXPathObject duplicate() {
		return new Node(this);
	}
	
	/**
	 * check for similarity
	 */
	public boolean isSimilarTo(IXPathObject object) {
		if (object instanceof Node) 
			return super.isSimilarTo(object);
		else
			return false;
	}
}
