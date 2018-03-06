package de.peerthing.systembehavioureditor.gefeditor.commands;

import java.util.List;
import java.util.Vector;

import org.eclipse.gef.commands.Command;

import de.peerthing.systembehavioureditor.gefeditor.editparts.StateEditPart;
import de.peerthing.systembehavioureditor.model.ITransition;
import de.peerthing.systembehavioureditor.model.editor.State;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Transition;

/**
 * This class manages commands for deletion of a State
 * including undo and redo commands.
 * 
 * @author Peter Schwenkenberg
 * @review Michael, 2006-03-23
 * 
 */
public class StateDeleteCommand extends Command {

    private final State state;
    private final SystemBehaviour system;
    private Transition[] transitionsIncoming;
    private Transition[] transitionsOutgoing;
    
    public StateDeleteCommand(State state, SystemBehaviour system) {
        super("Delete state");
        this.state = state;
        this.system = system;
    }

    /**
     * When a State is deleted, disconnect all associated Transitions.
     */
    private void disconnect(Transition[] transitions) {
        for (int i = 0; i < transitions.length; i++) {
            transitions[i].disconnect();
        }
    }

    /**
     * When a State is created (through the undo-delete command),
     * reconnect all Transitions.
     */
    private void connect(Transition[] transitions) {
        for (int i = 0; i < transitions.length; i++) {
            transitions[i].connect();
        }
    }

    public void execute() {
        transitionsIncoming = (Transition[]) state.getTransitionsIncoming()
                .toArray(new Transition[state.getTransitionsIncoming().size()]);
        transitionsOutgoing = (Transition[]) state.getTransitions()
                .toArray(new Transition[state.getTransitions().size()]);
        redo();
    }

    public void redo() {
        disconnect(transitionsIncoming);
        disconnect(transitionsOutgoing);
       //state.getTask().getStates().remove(state);
        state.getTask().removeState(state);
        system.removeState(state);
        ((StateEditPart)state.getEditPart()).getFigure().setVisible(false);
        
    }

    public void undo() {
        system.addState(state);
        state.getTask().addState(state);
        ((StateEditPart)state.getEditPart()).getFigure().setVisible(true);
        connect(transitionsIncoming);
        connect(transitionsOutgoing);
        
        // funny bug
       List<Transition>  deleteTrans = new Vector<Transition>();
        
        for (ITransition t: state.getTransitionsIncoming()) {
        	Transition tr = (Transition) t;
        	deleteTrans.add(tr);
        	//state.removeTransitionIncoming(tr);
        	
        }
        for (Transition tr : deleteTrans) {
        	state.removeTransitionIncoming(tr);
        	//tr.getEditPart().refresh();
        }
        
    }
}