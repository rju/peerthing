package de.peerthing.simulation.data;

import java.util.List;

import de.peerthing.simulation.interfaces.IMessage;
import de.peerthing.simulation.interfaces.IXPathAttribute;
import de.peerthing.simulation.interfaces.IXPathContainer;
import de.peerthing.simulation.interfaces.IPort;
import de.peerthing.simulation.interfaces.IXPathObject;

/**
 * Implements a message in the simulation runtime system.
 *
 * @author prefec2
 * @review Patrik, 2006-03-25
 */
public class Message extends XPathContainer implements IMessage {
	private String name; /* message name */

	private long timeSent;

	private long timeReceived;

	private int id;

	private int size;

	public Message(String name, long sessionId, long time, IPort source,
			IPort destination, List<IXPathContainer> parameterList) {
		super();

		this.name = name;
		this.timeSent = time;
		this.attributeList.add(new XPathAttribute("source", source));
		this.attributeList.add(new XPathAttribute("destination", destination));
		this.attributeList.add(new XPathAttribute("session", sessionId));
		// also add the source to the parameter list:
		Parameter sourceParam = new Parameter("source");
		if (destination!=null)
			sourceParam.setContent("" + destination.getRemoteNode().getId());
		else
			sourceParam.setContent("user");

		addParameter(sourceParam);

		if (parameterList != null) {
			for (IXPathContainer param : parameterList) {
				this.addParameter(param);
			}
        }
	}

    /**
     * Copy constructor
     * @param message
     */
    public Message(Message message) {
        name = message.name;
        timeSent = message.timeSent;
        timeReceived = message.timeReceived;
        for (IXPathAttribute attr : message.attributeList) {
            addAttribute(attr);
        }

        for (IXPathObject element : message.elementList) {
            elementList.add(element.duplicate());
        }
    }

	/*
	 * (non-Javadoc)
	 *
	 * @see simulation.ISimulation#getElementName()
	 */
	public String getElementName() {
		return "message";
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
	 * @see de.peerthing.simulation.interfaces.IMessage#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IMessage#getName()
	 */
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IMessage#getTimeSent()
	 */
	public long getTimeSent() {
		return this.timeSent;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IMessage#setTimeSent(long)
	 */
	public void setTimeSent(long time) {
		this.timeSent = time;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IMessage#getTimeReceived()
	 */
	public long getTimeReceived() {
		return this.timeReceived;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IMessage#setTimeReceived(long)
	 */
	public void setTimeReceived(long time) {
		this.timeReceived = time;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IMessage#setId(int)
	 */
	public void setId(int id) {
		this.id = id;
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
	 * @see de.peerthing.simulation.interfaces.IMessage#setSize(int)
	 */
	public void setSize(int bytes) {
		this.size = bytes;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IXPathObject#getSize()
	 */
	public int getSize() {
		return this.size;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IMessage#setSessionId(int)
	 */
	public void setSessionId(long sessionId) {
		this.attributeList.get(2).setValue(sessionId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IMessage#getSessionId()
	 */
	public long getSessionId() {
		return ((Long) this.attributeList.get(2).getValue()).intValue();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IMessage#getSource()
	 */
	public IPort getSource() {
		return (IPort) this.attributeList.get(0).getValue();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IMessage#setSource(de.peerthing.simulation.interfaces.IPort)
	 */
	public void setSource(IPort port) {
		this.attributeList.get(0).setValue(port);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IMessage#setDestinationNode(de.peerthing.simulation.interfaces.IPort)
	 */
	public void setDestinationNode(IPort port) {
		this.attributeList.get(1).setValue(port);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IMessage#getDestination()
	 */
	public IPort getDestination() {
		return (IPort) this.attributeList.get(1).getValue();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IMessage#getParameter(java.lang.String)
	 */
	public IXPathContainer getParameter(String name) {
		for (IXPathObject element : this.getChildAxis()) {
			if (element.getElementName().equals(name))
				if (element instanceof Parameter)
					return (Parameter) element;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IMessage#addParameter(de.peerthing.simulation.interfaces.IXPathContainer)
	 */
	public void addParameter(IXPathContainer parameter) {
		this.addElement((Parameter) parameter);
	}

    public IXPathObject duplicate() {
        return new Message(this);
    }
    
    /**
	 * check for similarity
	 */
	public boolean isSimilarTo(IXPathObject object) {
		if (object instanceof Message) 
			return super.isSimilarTo(object);
		else
			return false;
	}
}
