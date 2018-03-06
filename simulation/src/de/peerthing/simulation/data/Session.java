/**
 *
 */
package de.peerthing.simulation.data;

import de.peerthing.simulation.interfaces.IPort;
import de.peerthing.simulation.interfaces.ISession;
import de.peerthing.simulation.interfaces.IXPathObject;

/**
 * @author prefec2
 *
 */
public class Session extends XPathObject implements ISession {

	private IPort source;

	/**
	 *
	 */
	public Session(long id, IPort port) {
		super();
		this.source = port;
		this.attributeList.add(new XPathAttribute("id", id));
	}

	/**
	 * Copy constructor
	 *
	 * @param copy
	 */
	public Session(Session copy) {
		source = copy.source;
		addAttribute(new XPathAttribute("id", copy.getSessionId()));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see simulation.ISimulation#getElementName()
	 */
	public String getElementName() {
		return "session";
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
	 * @see de.peerthing.simulation.interfaces.ISession#getPort()
	 */
	public IPort getPort() {
		return this.source;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.ISession#setPort(de.peerthing.simulation.interfaces.IPort)
	 */
	public void setPort(IPort port) {
		this.source = port;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IXPathObject#getSize()
	 */
	public int getSize() {
		return 0;
	}

	/**
	 * Set the id for the session
	 */
	public void setSessionId(long sessionId) {
		this.attributeList.get(0).setValue(sessionId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.ISession#getSessionId()
	 */
	public long getSessionId() {
		return ((Long) this.attributeList.get(0).getValue()).intValue();
	}

	public IXPathObject duplicate() {
		return new Session(this);
	}
	
	/**
	 * check for similarity
	 */
	public boolean isSimilarTo(IXPathObject object) {
		if (object instanceof Session) 
			return super.isSimilarTo(object);
		else
			return false;
	}
}
