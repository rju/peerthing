package de.peerthing.systembehavioureditor.model;

import java.util.List;

import org.eclipse.core.resources.IFile;

/**
 * Represents an architecture.
 * 
 *
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface ISystemBehaviour extends ISystemBehaviourObject {
	
	/**
	 * Returns a list of nodes that belong to this architecture.
	 * 
	 * @return the list of nodes
	 */
	public List<INodeType> getNodes();
	
	/**
	 * Returns the name of the architecture.
	 * 
	 * @return the name
	 */
	public String getName();
	
	/**
	 * Sets the name of the architecture.
	 *
	 * @param name the name
	 */
	public void setName(String name);

	/**
     * TODO: remove this method, an architecture does not
     *          have a start state!!
     *          
     * @deprecated
     * @param state
	 */
	public void setStartState(IState state);
	
    public void setFile(IFile file);
    
    public IFile getFile();
}
