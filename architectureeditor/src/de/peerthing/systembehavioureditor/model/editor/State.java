package de.peerthing.systembehavioureditor.model.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.lang.String;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import de.peerthing.systembehavioureditor.interchange.EAIInitializeEvaluation;
import de.peerthing.systembehavioureditor.model.IAction;
import de.peerthing.systembehavioureditor.model.ICondition;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.ITransition;
import de.peerthing.systembehavioureditor.model.ITransitionContent;

/**
 * This class represents the model of a state.
 * 
 * @author Peter Schwenkenberg, Petra Beenken 
 * @review Sebastian Rohjans 27.03.2006
 */


public class State extends ModelObject implements IState {

	private static final long serialVersionUID = 1L;
    private String name = ""; // Avoid null values.
    private int x = 50, y = 50;  // Default position.
    private ITask task;
    private List<ITransitionContent> initializeActions;
    private EAIInitializeEvaluation eval;
    private List<IAction> actions = new ArrayList<IAction>(); 
	private List<ICondition> conditions = new ArrayList<ICondition>();
    @SuppressWarnings("unchecked")
	public List<ITransition> transitionsOutgoing = new Vector();
    @SuppressWarnings("unchecked")
	public List<ITransition> transitionsIncoming = new Vector();
    private static transient IPropertyDescriptor[] propertyDescriptors;
    
    private boolean isSelected;
    /*
     * Sets whether this state shall be displayed as an end-state
     */
    private boolean endState = false; 
    
    public boolean isEndState() {
    	return endState;
	}
    
    public void setEndState(boolean endState) {
    	this.endState = endState;
    }

    
    public State() {
    	
    	initializeActions = new ArrayList<ITransitionContent>();
    	
    	// Postfix the last numbers of the object id to the string's name, so the default names are unique.
    	name = "State_" + this.hashCode();
    }
    
    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Sets the name of a State. This methods avoids null strings,
     * oversized strings and existing names by guessing a new name.
     */
    public void setName(String newName) {
    	
    	 // leave, if there is nothing to rename
    	if (this.name.equals(newName)) {
    		return;
    	}
    	
        String oldName = name;
        
        // avoid empty state names
        if (newName.equals("")) {
        	if (this.getTask()!= null) {
        		newName = ((Node) this.getTask().getNode()).findDistinctiveStateName();
        	}
        	else {
        		newName = "State"  + this.hashCode();
        	}
        	System.out.println("Changed State name because a String must not be empty.");
        }
        
        // avoid oversized names
        if (newName.getBytes().length > 31) {
        	newName = (String) newName.subSequence(0, 31);
        	System.out.println("Changed State name because a String must not be too long.");
        } 
        
        this.name = newName;
        firePropertyChange("name", oldName, newName);
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

    public List<ITransition> getTransitionsIncoming() {
        return transitionsIncoming;
    }

    /**
     * 
     * @deprecated Use getTransitions instead, it is yet the same!
     */
    public List<ITransition> getTransitionsOutgoing() {
        return transitionsOutgoing;
    }
    
	public List<ITransition> getTransitions() {
		return transitionsOutgoing;
	}

	public ITask getTask() {
		return task;
	}

	public void setTask(ITask newTask) {
		ITask oldTask = this.task;
		this.task = newTask;
		firePropertyChange("task", oldTask, newTask);
	}

	public void setContents(List<ITransitionContent> actions) {
		initializeActions = actions;
	}

	public List<ITransitionContent> getContents() {
		return initializeActions;
	}

	public void setInitializeEvaluation(EAIInitializeEvaluation eval) {
		this.eval = eval;
	}

	public EAIInitializeEvaluation getInitializeEvaluation() {
		return eval;
	}
	
	public List<IAction> getActions(){
		return actions;
	}
	
	public List<ICondition> getConditions(){
		return conditions;
	}
	
	public void setActions(List <IAction> actions){
		this.actions = actions;
	}
	
	public void setConditions(List <ICondition> conditions){
		this.conditions = conditions;
	}

	public int addAction(Action action) {
        actions.add(action);
		return actions.indexOf(action);
	}

	public void addCondition(Condition tmp) {
		conditions.add(tmp);		
	}
	
	public String toString() {
		return getName();
	}

	public ISystemBehaviour getSystemBehaviour() {
		return task.getSystemBehaviour();
	}
	
	public void setIsSelected(boolean sel) {
		boolean oldSel = this.isSelected;
		isSelected = sel;
		firePropertyChange("sel", oldSel, isSelected);
	}
	
	public boolean isSelected() {
		return isSelected;
	}
}