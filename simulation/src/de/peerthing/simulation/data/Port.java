/**
 *
 */
package de.peerthing.simulation.data;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.simulation.interfaces.INode;
import de.peerthing.simulation.interfaces.IPort;
import de.peerthing.simulation.interfaces.ISystemTask;
import de.peerthing.simulation.interfaces.IXPathObject;

/**
 * @author prefec2
 *
 */
public class Port extends XPathObject implements IPort {

	IPort remotePort;

	INode remoteNode;

	List<ISystemTask> references;

	/**
	 *
	 */
	public Port(INode remoteNode) {
		super();
		this.remoteNode = remoteNode;
		this.remotePort = null;
		this.references = new ArrayList<ISystemTask>();
		addAttribute(new XPathAttribute("id", remoteNode.getId()));
	}

	/**
	 * Copy constructor
	 * @param copy
	 */
	public Port(Port copy) {
		remoteNode = copy.remoteNode;
		remotePort = copy.remotePort;
		references = copy.references;
		addAttribute(new XPathAttribute("id", remoteNode.getId()));
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IPort#assignTask(de.peerthing.simulation.interfaces.ISystemTask)
	 */
	public void assignTask(ISystemTask task) {
		this.references.add(task);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IPort#resignTask(de.peerthing.simulation.interfaces.ISystemTask)
	 */
	public void resignTask(ISystemTask task) {
		this.references.remove(task);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IPort#getRefCount()
	 */
	public int getRefCount() {
		return this.references.size();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IPort#getRemoteNode()
	 */
	public INode getRemoteNode() {
		return this.remoteNode;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IPort#getRemotePort()
	 */
	public IPort getRemotePort() {
		return this.remotePort;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IPort#setRemotePort(de.peerthing.simulation.interfaces.IPort)
	 */
	public void setRemotePort(IPort port) {
		this.remotePort = port;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IPort#hasReference(de.peerthing.simulation.interfaces.ISystemTask)
	 */
	public boolean hasReference(ISystemTask task) {
		for (ISystemTask ref : this.references) {
			if (ref == task)
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IPort#getAllReferences()
	 */
	public List<ISystemTask> getAllReferences() {
		return this.references;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IXPathObject#getSize()
	 */
	public int getSize() {
		return 8;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IXPathObject#getElementName()
	 */
	public String getElementName() {
		return "port";
	}

	public IXPathObject duplicate() {
		return new Port(this);
	}

	/**
	 * check for similarity
	 */
	public boolean isSimilarTo(IXPathObject object) {
		if (object instanceof Port) 
			return super.isSimilarTo(object);
		else
			return false;
	}
}
