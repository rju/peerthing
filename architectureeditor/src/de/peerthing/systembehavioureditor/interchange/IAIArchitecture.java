package de.peerthing.systembehavioureditor.interchange;

import java.util.List;

/**
 * Represents an architecture.
 *
 *
 * @author Michael Gottschalk
 */
public interface IAIArchitecture {

	/**
	 * Returns a list of nodes that belong to this architecture.
	 *
	 * @return the list of nodes
	 */
	public List<IAINodeType> getNodes();

	/**
	 * Return a node implementation from the architecture file
	 * specified by the node type name
	 * 
	 * @param name name of the node type
	 * 
	 * @return Returns a node type from the architecture or null if no such node type exists
	 */
	public IAINodeType getNodeImplementationByName(String name);
	
	/**
	 * Returns the name of the architecture.
	 *
	 * @return the name
	 */
	public String getName();

}
