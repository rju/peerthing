/*
 * Responsible for the architecture-editor ("archeditor"): Petra Beenken and Peter Schwenkenberg
 */
package de.peerthing.systembehavioureditor.model.editor;

import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import de.peerthing.systembehavioureditor.gefeditor.SysGraphicalEditor;
import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;
/**
 * SystemBehaviour is the main model-class of the GEF-Editor (the "canvas"). It contains all architectural information of one architecture since it implements ISystemBehaviour of the PeerThing-model..
 * @author Peter Schwenkenberg
 * @review Sebastian Rohjans 27.03.2006
 *
 */
public class SystemBehaviour extends ModelObject implements ISystemBehaviour {

    private static final long serialVersionUID = 8931907667293473808L;
    /**
	 * SystemBehaviour contains all the states, stored in a vector.
	 */
    private Vector<State> states = new Vector<State>();
    private Vector<Task> tasks = new Vector<Task>();
    private IFile file;
    private Node currentNode;
    
    /**
     * The Editor the system is associated with. 
     */
    private SysGraphicalEditor sysGraphicalEditor;
    
    /**
     * A list of nodes, that represent different types of peers. E.g.: [Peer, SuperPeer, SuperSuperPeer].
     */
    private List<INodeType> listNodes;
    
    /**
     * Name of the architecture (e.g. "Gnutella")
     */
    private String archName = "name";
    
    /**
     * This is the unique Start-State
     */
    private State startState;
    
    /**
     * Overriding the constructor in order to use our model.
     * When changing the constructor it may be usefull to delete the old arch-file.
     *
     */
    public SystemBehaviour () {
    	
    	listNodes = new Vector<INodeType>();
    }
    /*
     * This constructor is intended to create an architecture from start,
     * which includes creating a first node and the main task.
     */
    public SystemBehaviour (Boolean fromStart) {
    	
    	System.out.println("Create new system behaviour...");
    	this.setName("New_Architecture");
    	listNodes = new Vector<INodeType>();
    	Node node = new Node(true); // Create a new node from start (one that contains a main task and a start state for convenience).
    	node.setName("peer");
    	node.setArchitecture(this);
    	listNodes.add(node);
    }
    
    public List getStates() {
        return states;
    }
    
    /**
     * Returns a state by it's name.
     * @param The name of the State
     * @return The State matching the name
     */
    public State getState(String name) {
    	
    	for(Object stateIt : this.getStates()) {
    		State s = ((State) stateIt);
    		if (s.getName().equals(name))
    			return s;
    	}
    	
    	return null;
    }
    
    public List<Task> getTasks() {
    	return tasks;
    }

    public void addState(State state) {
        this.states.add(state);
        firePropertyChange("states", null, state);
    }

    public void removeState(State state) {
        this.states.remove(state);
        firePropertyChange("states", state, null);
    }
    
	public void addTask(Task task) {
        this.tasks.add(task);
        firePropertyChange("tasks", null, task);
	}
	
	public void removeTask(Task task) {
		this.tasks.remove(task);
		firePropertyChange("tasks", task, null);
	}

    public Object getPropertyValue(Object id) {
        return null;
    }

    public void setPropertyValue(Object id, Object value) {
    }

    public IPropertyDescriptor[] getPropertyDescriptors() {
        return new IPropertyDescriptor[0];
    }
    
	public List<INodeType> getNodes() {
		// return the nodes that this architecture contains
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
	
	public void setStartState(IState start) {
		
		this.startState = (State)start;
	}
    
    /**
     * Adds a node to a systembehaviour
     * @param n Node to add
     */
    public void addNode(Node n) {
    	listNodes.add(n);
    	firePropertyChange("nodes", null, n);
    }
    
    public void removeNode(Node n) {
    	listNodes.remove(n);
    	firePropertyChange("nodes", n, null);
    }
    
    public void setFile(IFile file){
    	this.file = file;
    }
    
    public IFile getFile() {
    	return file;
    }
	public ISystemBehaviour getSystemBehaviour() {
		return this;
	}
	public Node getCurrentNode() {
		return currentNode;
	}
	/**
	 * Set the Node that should be shown in the current tab.
	 * @param currentNode
	 */
	public void setCurrentNode(Node currentNode) {
		this.currentNode = currentNode;
	}
	
	public void firePropertyChangeForSystem(SystemBehaviour oldSys, SystemBehaviour system) {
		
		firePropertyChange("system", oldSys, system);
	}
	public void setEditor(SysGraphicalEditor editor) {
		
		sysGraphicalEditor = editor;
	}
	
	public SysGraphicalEditor getEditor () {
		return sysGraphicalEditor;
	}
	
	public String findDistinctiveNodeName() {
		
		String proposedName = "SomeNode"; // should never be the computed name
		
		try {
		int numberNode = this.getNodes().size();
		
		for(int i = numberNode + 1; i > 0; i--) {
			proposedName = "Node_" + numberNode; 
			
			// check whether this name is unique
			for (INodeType n : this.getNodes()) {
				if (n.getName().equals(proposedName)) {
					numberNode++;
					i = numberNode; // reset outer loop
				}
			}
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return proposedName;
	}		
	
}
