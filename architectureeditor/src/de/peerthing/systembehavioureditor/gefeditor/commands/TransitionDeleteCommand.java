package de.peerthing.systembehavioureditor.gefeditor.commands;

import java.util.List;
import java.util.Vector;

import org.eclipse.gef.commands.Command;

import de.peerthing.systembehavioureditor.PeerThingSystemBehaviourEditorPlugin;
import de.peerthing.systembehavioureditor.gefeditor.editparts.TransitionEditPart;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.ITransition;
import de.peerthing.systembehavioureditor.model.editor.State;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Task;
import de.peerthing.systembehavioureditor.model.editor.Transition;

/**
 * This class manages commands for deletion of a Transition
 * including undo and redo commands.
 * 
 * @author Peter Schwenkenberg
 * @review Michael, 2006-03-23
 */
public class TransitionDeleteCommand extends Command {
    private final Transition transition;

    public TransitionDeleteCommand(Transition transition) {
        super("Delete Transition");
        this.transition = transition;
    }

    public void execute() {
        redo();
    }

    public void redo() {
    	
    	// if you delete the decorative transition, delete also the transition starting the task
    	
    	if (transition.hasTaskDecoration) {
    		
    		Task theTask = (Task) transition.getNextState();
    		transition.hasTaskDecoration = false;
    		
    		if (theTask.getTransitionsIncoming().size() <2) {
    			theTask.hasDecoration = false;
    		}
    		
    		try {
    			theTask.getEditPart().refresh();
    		} catch (Exception e) {};
    	}
    	
    	// if the target of the transition is a task, delete also the decorative partner-transition
    	if (transition.getNextState() instanceof Task) {
    		
    		Task theTask = (Task)  transition.getNextState();
    		
    		List<TransitionDeleteCommand> listTranDc = new Vector<TransitionDeleteCommand>();
    		for (ITransition t :  theTask.getTransitionsOutgoing() ) {
    			Transition tran = (Transition) t;
    			
    			if (tran.isDeco && tran.getNextState().equals(transition.getState())) {
    				TransitionDeleteCommand tranDelC = new TransitionDeleteCommand(tran);
    				listTranDc.add(tranDelC);
    			}
    		}
    		for (TransitionDeleteCommand trandc : listTranDc) {
				((SystemBehaviour)transition.getSystemBehaviour()).getEditor().getTheCommandStack().execute(trandc);
				
			}
    		
    		// refresh the view of the other transitions going to the task, so there is a trunc again
    		for (ITransition t :  theTask.getTransitionsIncoming() ) {
    		
    			Transition tran = (Transition) t;
    			tran.getEditPart().refresh();
    		}
    		
    	}
    	
    	// if the target was an endState, delete it too
    	if(((State)transition.getNextState()).isEndState()) {
    		StateDeleteCommand del = new StateDeleteCommand((State)transition.getNextState(), (SystemBehaviour)transition.getSystemBehaviour());
    		del.execute();
    	}
    	
        transition.disconnect();
        if (!(transition.getState() instanceof ITask)) {
        	transition.getState().getTransitions().remove(transition);
        }
        else if (transition.getState() instanceof ITask) {
        	transition.getState().getTransitions().remove(transition);
        }
        
        // failsafe
        ((TransitionEditPart)transition.getEditPart()).getFigure().setVisible(false);
        
        try {
        	PeerThingSystemBehaviourEditorPlugin.getDefault().getNavigationTree().refresh(((SystemBehaviour)(transition.getSystemBehaviour())).getEditor().getFiletypeReg().getFile(((SystemBehaviour)transition.getSystemBehaviour()).getEditor()));
        }
        catch (Exception e) {
        	System.out.println("Cannot get NavigationTree. If this plugin is run stand alone, this should be ok.");
        }
    }

    public void undo() {
    	System.out.println("in undo");
    	
        try {
        	transition.connect();
	        if (!(transition.getState() instanceof ITask)) {
	        	// if ommited, it works :)
	        	//transition.getState().addTransitionOutgoing(transition);
	        }
	        else if (transition.getState() instanceof ITask) {
	        	transition.getState().addTransitionOutgoing(transition);
	        }
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
        
        try {
        	PeerThingSystemBehaviourEditorPlugin.getDefault().getNavigationTree().refresh(((SystemBehaviour)(transition.getSystemBehaviour())).getEditor().getFiletypeReg().getFile(((SystemBehaviour)transition.getSystemBehaviour()).getEditor()));
        }
        catch (Exception e) {
        	System.out.println("Cannot get NavigationTree. If this plugin is run stand alone, this should be ok.");
        }
    }
}