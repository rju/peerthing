package de.peerthing.systembehavioureditor.gefeditor.commands;

import org.eclipse.gef.commands.Command;

import de.peerthing.systembehavioureditor.PeerThingSystemBehaviourEditorPlugin;
import de.peerthing.systembehavioureditor.model.editor.Node;
import de.peerthing.systembehavioureditor.model.editor.State;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Task;
import de.peerthing.systembehavioureditor.model.editor.Transition;

/**
 * This class manages commands for the creation of Transitions including
 * undo and redo commands.
 * 
 * @author Peter Schwenkenberg
 * @review Michael, 2006-03-23
 */
public class TransitionCreateCommand extends Command {
	
    private State source;
    private State target;
    private Transition transition;

    public TransitionCreateCommand(State source, State target) {
        super("Create transition");
        this.source = source;
        this.target = target;
    }

    /**
     * Sets the target of the transition, since it is null on initial creation 
     * in the constructor.
     * @param newTarget
     */
    public void setTarget(State newTarget) {
        target = newTarget;
    }

    public boolean canExecute() {

    	try {
	    	if(source.isEndState()) {
	    		return false;
	    	}
	    	if(source instanceof Task && target.isEndState()) {
	    		return false;
	    	}
    	}
    	catch(Exception e) {};
    	
    	// transitions between tasks are prohibed
    	if (source instanceof Task && target instanceof Task) {
    		return false;
    	}
 
	    // We have a task that applies to be connected to a state of another node, that has ingoing
	    // or outgoing connections: Prohibe.
    	
	    try {
	    	if (source instanceof Task && !(target instanceof Task)) {
	    	
	    		if ((target.getTransitions().size() != 0) || (target.getTransitionsIncoming().size() != 0)) {
	    			if (!((Task) source).getNode().equals(target.getTask().getNode())){
	    				return false;
	    			}
	    		}
	    	}
	    }
	    catch(Exception e) {
	    }
    	
	    // Connection between a state and a task: Allow only, if the task is not already connected.
	    if(!(source instanceof Task) && (target instanceof Task)) {
	    	
	    	if (! source.getTask().getNode().equals( ((Task) target).getNode())) {
	    		// if the task already has any connections
	    		if ( !(target.getTransitions().size() == 0) || !(target.getTransitionsIncoming().size() == 0) ) {
	    			return false;
	    		}
	    	}
	    }
	    
    	try {
    		// We have two states that apply to be connected: Allow only connections
    		// between states of different tasks, if the target state is a fresh one
    		// (does not have any connections).
	    	if ( !(source instanceof Task) && !(target instanceof Task) &&
	    			!(source.getTask().equals(target.getTask())) && 
	    			(!(target.getTransitions().size() == 0) ||
	    			!(target.getTransitionsIncoming().size() == 0))
	    			) {
		    		//System.out.println("User Error: You cannot connect alien states.");
		    		return false;
	    	}
    	}
	    catch(Exception e) {}
	   
	    // If the source of the transition is the start task of a node, don't allow
	    // connections from states to that source.
	    if ( (target instanceof Task) && ((Task) target).getNode().getStartTask().equals(target) ) {
	    	
	    	//System.out.println("User Error: You cannot define connections from states to the start task.");
	    	return false;
	    }
	    
        return true;
    }

    public void execute() {
        transition = new Transition();

        // If the source is a task, the target must be a state.
        if (source instanceof Task) {
        	
        	// When from a task a second outgoing transition is to be set,
        	// delete the first one, because a task can only connect to one state.
        	try {
        		System.out.println("task's trans: " + ((State)((Task)source).getStartState()).getTransitionsIncoming());
	        	Transition oldTransition = Transition.getTransitionTaskToState((Task) source, ((State)((Task)source).getStartState()));
	        		//(Transition) ((State)((Task)source).getStartState()).getTransitionsIncoming().get(0);
	        	oldTransition.disconnect();
        	}
        	catch(Exception e) {
        		System.out.println("There seem not to be any further outgoing transitions from this task.");
        	}
        	
            transition.setState((Task) source);
            transition.setNextState((State) target);
            
            // remove the target from it's old task's vector
            target.getTask().removeState(target);
            
            // [Task] --> [State]: State belongs to new task
            target.setTask((Task) source);
        }

        else {
        	// source is a state and target is a task
            if (target instanceof Task) {
                transition.setState((State) source);
                transition.setNextState((Task) target);
                
                // remove the target from the old list of tasks of his node
                ((Task) target).getNode().getTasks().remove((Task) target);
                
                // set the node of the task to the new one (to one of the source)
                ((Task) target).setNode(source.getTask().getNode());
                
                // add the target to the new node's task-list
                ((Task) target).getNode().getTasks().add((Task) target);
              
            }
            // if source and target are states
            else {
                transition.setState((State) source);
                transition.setNextState((State) target);
                
                // handle end-states
                if (target.isEndState()) {
                	transition.setEndTask(true);
                }
            }
        }
        
        // transitions between two states
        if (!(source instanceof Task) && !(target instanceof Task)) {
        	//System.out.println("Examining connections between states.");
        	// remove the state from the list of it's current task...
        	try {
        		target.getTask().removeState(target);
        	}
        	catch(Exception e){System.out.println(e + " in execute()");}
        	
        	// ...and set the target-state to the source's task
        	target.setTask(source.getTask());
        	target.getTask().addState(target);
        }
        
        // set a better name
        if (transition.getState() != null) {
        	try {
        		transition.setEvent( ((Node) transition.getState().getTask().getNode()).findDistinctiveTransitionName() );
        	}
        	catch (Exception e) {
        	}
        }
        
        redo();
    }

    public void redo() {
        transition.connect();
        try {
        	PeerThingSystemBehaviourEditorPlugin.getDefault().getNavigationTree().refresh(((SystemBehaviour)(transition.getSystemBehaviour())).getEditor().getFiletypeReg().getFile(((SystemBehaviour)transition.getSystemBehaviour()).getEditor()));
        }
        catch (Exception e) {
        	System.out.println("Cannot get NavigationTree. If this plugin is run stand alone, this should be ok.");
        }
    }

    public void undo() {
        transition.disconnect();
        try {
        	PeerThingSystemBehaviourEditorPlugin.getDefault().getNavigationTree().refresh(((SystemBehaviour)(transition.getSystemBehaviour())).getEditor().getFiletypeReg().getFile(((SystemBehaviour)transition.getSystemBehaviour()).getEditor()));
        }
        catch (Exception e) {
        	System.out.println("Cannot get NavigationTree. If this plugin is run stand alone, this should be ok.");
        }
    }
}