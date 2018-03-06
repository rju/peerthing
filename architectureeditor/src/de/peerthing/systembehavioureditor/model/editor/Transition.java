package de.peerthing.systembehavioureditor.model.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import de.peerthing.systembehavioureditor.gefeditor.editparts.TaskEditPart;
import de.peerthing.systembehavioureditor.model.IAction;
import de.peerthing.systembehavioureditor.model.ICondition;
import de.peerthing.systembehavioureditor.model.IContentContainer;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.ITransition;
import de.peerthing.systembehavioureditor.model.ITransitionContent;
import de.peerthing.systembehavioureditor.model.ITransitionTarget;

/**
 * This class represents the model of a Transistion.
 * 
 * @author Peter Schwenkenberg, Petra Beenken
 * @review Sebastian Rohjans 27.03.2006
 */

public class Transition extends ModelObject implements ITransition {
	private static final long serialVersionUID = -2241663084879708166L;

	private IState fromState;

    private ITransitionTarget toState;
    
    private String event = "genericEvent";
    
    public boolean  hasTaskDecoration = false;
    
    private boolean isEndTask = false;
    private List<ITransitionContent> content;
    private ITask startTask;
    
    public boolean isDeco = false;
   
    /**
     * The container to which this transition content belongs
	 * (either ITransition or ICaseArchitecture)
     */
	private IContentContainer container;

	public Transition() {
		content = new ArrayList<ITransitionContent>();
		
		
		event ="Tran_" + this.hashCode();
		
	}
	
    public String toString(){
    	return getEvent();
        //return "Transition(" + event + ")";
    }
	
    /**
     * Informs the target and the sources
     * of a transition about there incoming and outgoing transitions.
     * Also checks, whether connections are allowed.
     * (E.g. transitions between tasks are not.)
     */
    public void connect() {
    	
        if (fromState != null) {
        	if(fromState instanceof Task) {
        		if(toState instanceof Task) {
        			return; // failure: transitions cannot exist between tasks
        		}
        		
        		// Treat decorative Transitions (from the task back to the state that starts this task) different
        		if ((!this.isDeco)) {
	        			
	        		fromState.getTransitions().clear(); // A task can have only one outgoing transition.
	        		((Task)fromState).addTransitionOutgoing(this);
	        		
	        	    ((Task)fromState).setStartState((IState)toState);
	        		
	        		// set the target state to the task of the source
	        		// (copy it from the old task's vector of states)
	        		
	        		try {
	        			((State)toState).getTask().removeState((IState)toState);
	        		}
	        		catch(Exception e) {e.printStackTrace();}
	        		
	        		((State)toState).setTask((ITask)fromState);
	        		((Task)fromState).addState((IState)toState);
        		}
        	}
        	else {
        		((State) fromState).addTransitionOutgoing(this);
        	}
        }
        if (toState != null) {
        	if(toState instanceof Task) {
        		((Task) toState).addTransitionIncoming(this);
        	}
        	else {
        		((State) toState).addTransitionIncoming(this);
        	}
        }

        
        // Is the target of a Transition a Task, draw an transition as illustration back to the same source.
        if (toState instanceof Task && !(fromState instanceof Task) && (fromState != null) && !(this.isEndTask)) {
        	try {
        		
	        	Transition tBack = new Transition();
	        	tBack.setState((Task)toState);
	        	tBack.setNextState((State)fromState);
	        	System.out.println("nextState: " + fromState);
	        	tBack.isDeco = true; // flag, that this transition is only decoration
	        	
	        	// connect tBack
	        	((Task)toState).addTransitionOutgoing(tBack);
    			((State)fromState).addTransitionIncoming(tBack);
    			return;
	        	
        	}
        	catch (Exception e) {
        		e.printStackTrace();
        	}
        }
        
    }

    public void disconnect() {
        if (fromState != null) {
           ((State) getState()).removeTransitionOutgoing(this);
           if (fromState instanceof Task) {
           // ((Task) fromState).setStartState(null); // the source (task) that loses his only transition to a state has no more start state
           }
          
        }
        if (toState != null) {
            ((State) getNextState()).removeTransitionIncoming(this);
            
            // refresh the visuals of the task (show icon or not depending on incoming transitions)
            if (toState instanceof Task) {
         	   try {
         		   ((TaskEditPart)((Task) toState).getEditPart()).refresh();
         	   } catch (Exception e) {e.printStackTrace();}
            }
        }
    }

    public Object getPropertyValue(Object id) {
    	   if ("event".equals(id)) {
               return getEvent();
           }
           return null;
    }

    public void setPropertyValue(Object id, Object value) {
    	 if ("event".equals(id)) {
             setEvent((String) value);
         }
    }

    private static transient IPropertyDescriptor[] propertyDescriptors;
    
    public IPropertyDescriptor[] getPropertyDescriptors() {
    	
        if (propertyDescriptors == null) {
        	
            propertyDescriptors = new IPropertyDescriptor[] { new TextPropertyDescriptor("event", "Event")};
        }
        return propertyDescriptors;
    }

	public ITransitionTarget getNextState() {
		return toState;
	}

	public void setNextState(ITransitionTarget target) {
		Object oldValue = this.toState;
        this.toState = target;
        firePropertyChange("toState", oldValue, target);
	}

	public boolean isEndTask() {
		return isEndTask;
	}

	public void setEndTask(boolean endTask) {
		isEndTask = endTask;
	}

	public List<ITransitionContent> getContents() {
		return content;
	}

	/**
	 * Returns the state to which this transition belongs.
	 * 
	 * @return the state
	 */
	public IState getState() {
		return fromState;
	}

	/**
	 * Sets the state to which this transition belongs.
	 * 
	 * @param state the state
	 */
	public void setState(IState state) {
		this.fromState = state;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String newName) {
		String oldName = event;
		
		// delete spaces -> NMTOKENS
		newName = newName.replace(" ", "");
		newName = newName.replace("\\n", "");
		
		
		
        this.event = newName;
        firePropertyChange("event", oldName, newName);
	}
	
	/**
	 * Sets the task to be started.
	 * @param task
	 */
	public void setStartTask(ITask task) {
		this.startTask = task;
	}
	
	/**
	 * .
	 * 
	 * @param 
	 */
	public ITask getStartTask() {
		return startTask;
	}

	/**
	 * Returns the name of the transistion target.
	 * 
	 * @return the name
	 */
	public String getName() {
		return toState.getName();
	}

	/**
	 * Sets the name of the transition target. This must be unique among all
	 * transition targets since it is used as in identifier in the XML representation.
	 *
	 * @param name the name
	 */
	public void setName(String name) {
		toState.setName(name);
	}
	
	/**
	 * Sets the container to which this transition content belongs
	 * (either ITransition or ICaseArchitecture)
	 * 
	 * @param transition
	 */
	public void setContainer(IContentContainer container) {
		this.container = container;
	}

	/**
	 * Returns the container to which this transition content belongs
	 * (either ITransition or ICaseArchitecture)
	 * 
	 * @return
	 */
	public IContentContainer getContainer() {
		return container;
	}

	public void setContents(List<ITransitionContent> contents) {
		this.content = contents;
	}
	
	public List<IAction> getActions(){
		List<IAction> r=new Vector<IAction>();
		for (ITransitionContent a:content) {
			if (a instanceof IAction) {
				r.add((IAction)a);				
			}
		}
		return r;
	}
	
    public List<ICondition> getConditions(){
    	List<ICondition> r=new Vector<ICondition>();
		for (ITransitionContent a:content) {
			if (a instanceof ICondition) {
				r.add((ICondition)a);				
			}
		}
		return r;
    }
 	
	public void setActions(List <IAction> actions){
		for (IAction a:actions) {
			if (!content.contains(a)) {
				content.add(a);
			}
		}
	}
	
    public void setConditions(List <ICondition> conditions){
    	for (ICondition a:conditions) {
			if (!content.contains(a)) {
				content.add(a);
			}
		}
    }
    
    /**
     * Helper method to get the transition object between a task and it's start state.
     * @param source Task The Source
     * @param target State The start state as the target
     * @return Transition The transition's object between these objects.
     */
    public static Transition getTransitionTaskToState(Task source, State target) {
    	
    	// Iterate over all incoming transitions of the state.
    	for (ITransition t : ((State)((Task)source).getStartState()).getTransitionsIncoming())  {
    		if (t.getState().equals(source)){
    			return (Transition) t;
    		}
    	}
    	return null;
    }

	public ISystemBehaviour getSystemBehaviour() {
		return fromState.getSystemBehaviour();
	}
    
}