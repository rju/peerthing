package de.peerthing.simulation.data;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Collection;
import java.util.List;

import de.peerthing.scenarioeditor.interchange.ISIResourceCategory;
import de.peerthing.simulation.interfaces.IResourceDefinition;
import de.peerthing.simulation.interfaces.IDataStorage;
import de.peerthing.simulation.interfaces.INode;
import de.peerthing.simulation.interfaces.IXPathAttribute;
import de.peerthing.simulation.interfaces.IXPathObject;

/**
 * Implements the main XPath object for the simulation runtime system. Could
 * possibly merged with de.peerThing.simulation.execution.Simulation
 *
 * @author prefec2
 * @review Patrik, 2006-03-25
 */

public class Simulation extends XPathContainer implements IDataStorage {

	private long endTime; /* time to end simulation */

	private long time; /* current simulation time */

	private Hashtable<Integer, INode> nodeLookup; // lookup hashtable for peer

	// nodes

	private Hashtable<Integer, IResourceDefinition> resourceLookup; // lookup

	// hashtable

	// for peer nodes

	/**
	 * Includes the nodes sorted by types.
	 */
	private Hashtable<String, List<INode>> nodeTypes = new Hashtable<String, List<INode>>();

	private int intervalMax;

	private ArrayList<String> qualityList;

	/**
	 * initialize the simulation
	 *
	 */
	public Simulation(long endTime) {
		super();
		this.time = 0;
		this.endTime = endTime;
		this.intervalMax = 0;
		this.nodeLookup = new Hashtable<Integer, INode>();
		this.resourceLookup = new Hashtable<Integer, IResourceDefinition>();

		/* hard coded quality list */
		this.qualityList = new ArrayList<String>();
		this.qualityList.add("present");
		this.qualityList.add("validating");
		this.qualityList.add("valid");
	}

	/**
	 * Copy constructor
	 *
	 * @param copy
	 */
	public Simulation(Simulation copy) {
		time = copy.time;
		endTime = copy.time;
		intervalMax = copy.intervalMax;
		nodeLookup = copy.nodeLookup;
		resourceLookup = copy.resourceLookup;
		qualityList = copy.qualityList;

		for (IXPathAttribute attr : copy.getAttributeAxisList()) {
			addAttribute(attr);
		}

		for (IXPathObject obj : copy.getChildAxis()) {
			addElement(obj);
		}
	}

	public Simulation() {
		this(0);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IDataStorage#getQualityList()
	 */
	public ArrayList<String> getQualityList() {
		return this.qualityList;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see simulation.ISimulation#getElementName()
	 */
	public String getElementName() {
		return "simulation";
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
	 * Add a node element
	 *
	 * @param element
	 */
	public void addNode(INode node) {
		this.nodeLookup.put(node.getId(), (Node) node);
		this.addElement((Node) node);
		List<INode> nodesWithThisType = nodeTypes.get(node
				.getSystemBehaviourName());
		if (nodesWithThisType == null) {
			nodesWithThisType = new ArrayList<INode>();
			nodeTypes.put(node.getSystemBehaviourName(), nodesWithThisType);
		}
		nodesWithThisType.add(node);
	}

	/**
	 * Remove a node form the simulation
	 */
	public void removeNode(int id) {
		INode thisnode = nodeLookup.get(id);
		removeElement(thisnode);
		this.nodeLookup.remove(id);

		List<INode> nodesWithThisType = nodeTypes.get(thisnode
				.getSystemBehaviourName());
		if (nodesWithThisType != null) {
			nodesWithThisType.remove(thisnode);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IDataStorage#getNode(int)
	 */
	public INode getNode(int id) {
		return this.nodeLookup.get(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IDataStorage#getAllNodes()
	 */
	public Collection<INode> getAllNodes() {
		return this.nodeLookup.values();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IDataStorage#addResource(de.peerthing.simulation.interfaces.IResourceDefinition)
	 */
	public void addResource(IResourceDefinition resource) {
		this.resourceLookup
				.put(resource.getId(), (ResourceDefinition) resource);
		this.addElement((ResourceDefinition) resource);
	}

	/**
	 * Remove a resource form the simulation
	 */
	public void removeResource(int id) {
		removeElement((Node) this.nodeLookup.get(id));
		this.resourceLookup.remove(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IDataStorage#getResource(int)
	 */
	public IResourceDefinition getResource(int id) {
		return this.resourceLookup.get(id);
	}

	/**
	 * @return Returns the endTime.
	 */
	public long getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            The endTime to set.
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return Returns the time.
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time
	 *            The time to set.
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IDataStorage#getResourceDefinitionListByCategory(de.peerthing.scenarioeditor.interchange.ISIResourceCategory)
	 */
	public ArrayList<IResourceDefinition> getResourceDefinitionListByCategory(
			ISIResourceCategory category) {
		ArrayList<IResourceDefinition> resList = new ArrayList<IResourceDefinition>();

		for (IResourceDefinition item : this.resourceLookup.values()) {
			if (item.getCategory() == category) /*
												 * found resource definition
												 * from the right category
												 */
				resList.add(item);
		}

		return resList;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IDataStorage#setResourcePopularityInterval(int)
	 */
	public void setResourcePopularityInterval(int intervalLength) {
		this.intervalMax = intervalLength;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IDataStorage#getResourcePopularityInterval()
	 */
	public int getResourcePopularityInterval() {
		return this.intervalMax;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IDataStorage#getNodesWithType(java.lang.String)
	 */
	public List<INode> getNodesWithType(String type) {
		return nodeTypes.get(type);
	}

	public IXPathObject duplicate() {
		return new Simulation(this);
	}
	
	/**
	 * check for similarity
	 */
	public boolean isSimilarTo(IXPathObject object) {
		if (object instanceof Simulation) 
			return super.isSimilarTo(object);
		else
			return false;
	}
}
