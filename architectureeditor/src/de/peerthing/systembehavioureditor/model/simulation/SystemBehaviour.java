package de.peerthing.systembehavioureditor.model.simulation;

import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IFile;

import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;

/**
 * Default implementation of ISystemBehaviour
 * 
 * @author mg
 * @reviewer Hendrik Angenendt
 */
public class SystemBehaviour implements ISystemBehaviour {

    /**
     * A list of nodes, that represent different types of peers. E.g.: [Peer,
     * SuperPeer, SuperSuperPeer].
     */
    private List<INodeType> listNodes;

    /**
     * Name of the architecture (e.g. "BitTorrent")
     */
    private String archName = "name";

    /**
     * This is the unique Start-State
     */
    private State startState;

    /**
     * Overriding the constructor in order to use our model. When changing the
     * constructor it may be usefull to delete the old arch-file.
     * 
     */
    public SystemBehaviour() {
        listNodes = new Vector<INodeType>();
    }

    public List<INodeType> getNodes() {
        return listNodes;
    }

    public String getName() {
        return archName;
    }

    public void setName(String name) {
        archName = name;

    }

    public State getStartState() {
        return startState;
    }

    /**
     * Don't know why there are two methods that
     * are nearly the same below... (mg). This
     * is from Peter
     * 
     * TODO: remove the following methods!
     * 
     * @param start
     */
    public void setStartState(State start) {
        this.startState = start;
    }

    public void setStartState(IState state) {
    }

	public void setFile(IFile file) {
	}

	public IFile getFile() {
		return null;
	}

    /**
     * This is not needed for the simulation classes.
     */
	public ISystemBehaviour getSystemBehaviour() {
		return null;
	}
}