package de.peerthing.systembehavioureditor.model.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.ITransition;
import de.peerthing.systembehavioureditor.model.IVariable;

/**
 * This class models a Task
 * 
 * @author Peter Schwenkenberg, Petra Beenken
 * @review Sebastian Rohjans 27.03.2006
 *
 */

public class Task extends State implements ITask{

	private static final long serialVersionUID = 1L;

    private int x = 10, y = 10; // default values
    
    /**
     * The name of a task (has to be unique).
     */
    private String name = "";
    private List<IVariable> variables;
    public List<IState> states;
    private INodeType node;

    /**
     * Stores, whether this task has a decoration (at the incoming transition) 
     */
    public boolean hasDecoration = false;
    
    @SuppressWarnings("unchecked")
	private List<ITransition> transitionsOutgoing = new Vector();
    @SuppressWarnings("unchecked")
	private List<ITransition> transitionsIncoming = new Vector();
    
    /**
     * The first state of the task to be executed.
     */
	IState startState;
    
    private static transient IPropertyDescriptor[] propertyDescriptors;
    
    public Task () {
    	
    	variables = new ArrayList<IVariable>();
    	states = new ArrayList<IState>();
    	name = Integer.toString(this.hashCode());
    }
	
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void setX(int newX) {
    	
        int oldX = x;
        this.x = newX;
        firePropertyChange("x", oldX, newX);
    }

    public void setY(int newY) {
        int oldY = y;
        this.y = newY;
        firePropertyChange("y", oldY, newY);
    }
    
    public String getName() {
    	return name;
    }
    
    public String toString() {
    	return getName();
    }
    
    public void setName(String newName) {
    	
        // avoid empty task names
        if (newName.equals("")) {
        	if (getNode() != null) {
        		newName = ((Node) this.getNode()).findDistinctiveTaskName();
        	}
        	else {
        		newName = "Task_"  + this.hashCode();
        	}
        	System.out.println("Changed Task name because a String must not be empty.");
        }
        
        // avoid oversized names
        if (newName.getBytes().length > 31) {
        	newName = (String) newName.subSequence(0, 31);
        	System.out.println("Changed Task name because a String must not be too long.");
        } 
    	
    	 try {
 	       // avoid already existing task names (in context of the node)
    	   if (this.getNode() != null) {
	 		   for( ITask t : this.getNode().getTasks()) {
	 			   if (t.getName().equals(newName)) {
	 				   this.name = "" + this.hashCode();
	 				   newName = ((Node) this.getNode()).findDistinctiveTaskName();
	 				   System.out.println("Changed Task name because a String must be distinctive.");
	 			   }
	 		    }
    	   	}
 	     }
         
         catch (Exception e) {
         	e.printStackTrace();
         }
    	
    	String oldName = name;
        this.name = newName;
        firePropertyChange("name", oldName, newName);
    }
    
    
	public IPropertyDescriptor[] getPropertyDescriptors() {
        if (propertyDescriptors == null) {
            propertyDescriptors = new IPropertyDescriptor[] { new TextPropertyDescriptor("name", "Name") };
        }
        return propertyDescriptors;
	}

    public Object getPropertyValue(Object id) {
        if ("name".equals(id)) {
            return getName();
        }
        return null;
    }

    public void setPropertyValue(Object id, Object value) {
        if ("name".equals(id)) {
            setName((String) value);
        }
	}
    
    // copied from state. should be obsolete
    public void addTransitionOutgoing(ITransition transition) {
        transitionsOutgoing.add(transition);
        firePropertyChange("transitionsOutgoing", null, transition);
    }

    public void addTransitionIncoming(ITransition transition) {
        transitionsIncoming.add(transition);
        firePropertyChange("transitionsIncoming", null, transition);
    }

    public void removeTransitionOutgoing(Transition transition) {
        transitionsOutgoing.remove(transition);
        firePropertyChange("transitionsOutgoing", transition, null);
    }

    public void removeTransitionIncoming(Transition transition) {
        transitionsIncoming.remove(transition);
        firePropertyChange("transitionsIncoming", transition, null);
    }

    
	/**
	 * Adds a state to a task. Use this instead of adding the state to the field manuelly because of side effects that need to be taken care of in the editor.
	 * @param state
	 */
    public void addState(IState state) {
    	this.states.add((State)state);
    	firePropertyChange("states", null, state); // needed?
    }
    
    public void removeState(IState state) {
    	this.states.remove((State) state);
    	firePropertyChange("states", state, null);
    }
    
    public IState getStartState() {
		return startState;
	}

    /**
	 * Sets the state in which this task starts running.
	 * 
	 * @param state the start state
	 */
	public void setStartState(IState state) {
		this.startState = state;
	}

	public List<IVariable> getVariables() {
		return variables;
	}

	public List<IState> getStates() {
		return states;
	}

	public INodeType getNode() {
		return node;
	}

	public void setNode(INodeType newNode) {
		INodeType oldNode = this.node;
		this.node = newNode;
		firePropertyChange("node", oldNode, newNode);
	}
	
    public List<ITransition> getTransitionsIncoming() {
        return transitionsIncoming;
    }

    public List<ITransition> getTransitionsOutgoing() {
        return transitionsOutgoing;
    }

	@Override
	public ISystemBehaviour getSystemBehaviour() {
		return node.getSystemBehaviour();
	}

	public void setVariables(List<IVariable> vars) {
		this.variables = vars;
		
	}
    
}