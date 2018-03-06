/*
 * Created on 12.02.2006
 *
 */
package de.peerthing.simulation.interfaces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.peerthing.scenarioeditor.interchange.ISIResourceCategory;

/**
 * Interface to the implementation of a storage for data used during a
 * simulation run.
 * 
 * 
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface IDataStorage extends IXPathObject {
	/**
	 * search for a Node with the given id
	 * 
	 * @param id
	 *            the id of the node
	 * @return the INode or null
	 */
	public INode getNode(int id);

	/**
	 * Returns all nodes with a given node type. This is implemented with
	 * hashtables, so this is faster than searching all nodes.
	 * 
	 * @param type
	 *            The type name of the node
	 * @return the node list or null, if there are no nodes with this type.
	 */
	public List<INode> getNodesWithType(String type);

	/**
	 * Add a node to the simulation
	 * 
	 * @param node
	 *            the node to be added
	 */
	public void addNode(INode node);

	/**
	 * Remove a node from the simulation
	 * 
	 * @param id
	 *            the id of the node
	 */
	public void removeNode(int id);

	/**
	 * Return all nodes from the simulation
	 * 
	 * @return ArrayList of all nodes
	 */
	public Collection<INode> getAllNodes();

	/**
	 * Get a resource definition by id
	 * 
	 * @param id
	 *            the id of the resource
	 * @return A ISimuationResource or null
	 */
	public IResourceDefinition getResource(int id);

	/**
	 * Generate a list with all resources of a specific category
	 * 
	 * @param category
	 *            resource category
	 * 
	 * @return Returns a list of resource definitions
	 */
	public List<IResourceDefinition> getResourceDefinitionListByCategory(
			ISIResourceCategory category);

	/**
	 * Add a resource definition to the simulation
	 * 
	 * @param resource
	 *            the resource definition to be added
	 */
	public void addResource(IResourceDefinition resource);

	/**
	 * Set the total length of the popularity interval.
	 * 
	 * @param intervalLength
	 */
	public void setResourcePopularityInterval(int intervalLength);

	/**
	 * Get the total length of the popularity interval.
	 * 
	 * @return returns the value
	 */
	public int getResourcePopularityInterval();

	/**
	 * return implicit defined list of legal qualities
	 * 
	 * @return returns the list of all legal quality values.
	 */
	public ArrayList<String> getQualityList();
}
