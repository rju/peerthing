package de.peerthing.systembehavioureditor.gefeditor.commands;

import org.eclipse.gef.commands.Command;

import de.peerthing.systembehavioureditor.model.editor.State;
import de.peerthing.systembehavioureditor.model.editor.Task;
import de.peerthing.systembehavioureditor.model.editor.Transition;

/**
 * This class manages commands for the reconnection of a Transition
 * when changing the source or target of an existing Transition,
 * including undo and redo commands.
 * 
 * @author Peter Schwenkenberg
 * @review Michael, 2006-03-23
 */
public class TransitionReconnectCommand extends Command {

    private Transition transition;
    private State newSource;
    private State newTarget;
    private State oldSource;
    private State oldTarget;

    public TransitionReconnectCommand(Transition newTransition,
            State newSource, State newTarget) {
        super("Reconnect Transition");
        this.transition = newTransition;
        oldSource = (State) transition.getState();
        oldTarget = (State) transition.getNextState();
        this.newSource = newSource == null ? oldSource : newSource;
        this.newTarget = newTarget == null ? oldTarget : newTarget;
    }

    /**
     * Here might be defined restrictions for the reconnection of a Transition.
     */
    public boolean canExecute() {
    	
    	// transitions between tasks are prohibed
    	if (newSource instanceof Task && newTarget instanceof Task) {
    		return false;
    	}
    	
    	try {
    		// We have two states that apply to be connected: Allow only connections
    		// between states of different tasks, if the target state is a fresh one
    		// (does not have any connections).
	    	if ( !(newSource instanceof Task) && !(newTarget instanceof Task) &&
	    			!(newSource.getTask().equals(newTarget.getTask())) && 
	    			(!(newTarget.getTransitions().size() == 0) ||
	    			!(newTarget.getTransitionsIncoming().size() == 0))
	    			) {
	    		return false;
	    	}
    	}
	    catch(Exception e) {System.out.println( e + "Error testing connectability in canExecute()");}
    	
	    // If the source of the transition is the start task of a node, don't allow
	    // connections from states to that source.
	    if ( (newTarget instanceof Task) && ((Task) newTarget).getNode().getStartTask().equals(newTarget) ) {
	    	
	    	return false;
	    }
	    
        return true;
    }

    public void execute() {
        redo();
    }

    public void redo() {
        transition.disconnect();
        transition.setState(newSource);
        transition.setNextState(newTarget);
        transition.connect();
    }

    public void undo() {
        transition.disconnect();
        transition.setState(oldSource);
        transition.setNextState(oldTarget);
        transition.connect();
    }
}