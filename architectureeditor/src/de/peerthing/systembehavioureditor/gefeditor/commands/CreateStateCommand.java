package de.peerthing.systembehavioureditor.gefeditor.commands;

import org.eclipse.gef.commands.Command;

import de.peerthing.systembehavioureditor.PeerThingSystemBehaviourEditorPlugin;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.editor.State;
import de.peerthing.systembehavioureditor.model.editor.Node;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;

/**
 * This class manages (create, redo, undo) Commands for States.
 * 
 * @author Peter Schwenkenberg
 * @review Michael, 2006-03-23
 * 
 */
public class CreateStateCommand extends Command {
	
    int x, y;
    private final SystemBehaviour system;
    private final State state;

    public CreateStateCommand(SystemBehaviour system, State state, int x, int y) {
        super("Create State");
        this.system = system;
        this.state = state;
        this.x = x;
        this.y = y;
    }

    public void execute() {
    	
        state.setX(x);
        state.setY(y);
        ITask myTask;
        try {
    	if (system.getCurrentNode() != null)
    		myTask = system.getCurrentNode().getTasks().get(0);
    	else
    		myTask = system.getNodes().get(0).getTasks().get(0);
       // ITask myTask = system.getCurrentNode().getTasks().get(0);
        state.setTask(myTask);
        
        	Node statesNode = (Node) state.getTask().getNode();
        	state.setName( statesNode.findDistinctiveStateName() );
        }
        catch(Exception e) {
        	System.out.println("Problem with computing name: " + e.getMessage());
        	e.printStackTrace();
        }
        redo();
    }

    public void redo() {

    	ITask myTask;
    	try {
	    	if (system.getCurrentNode() != null)
	    		myTask = system.getCurrentNode().getTasks().get(0);
	    	else
	    		myTask = system.getNodes().get(0).getTasks().get(0);
    	
	        myTask.addState(state);
	        state.setTask(myTask);
	
	        // The old place. Till now, we use both because I didn't integrate the
	        // new structur completely.
	        //system.addState(state);
	        
	        // If not used the line above, a new EditPart is created, when a task is added (?)
	        // Somewhere we have made something wrong.
	        system.getEditPart().refresh();
	        
	        try {
	        	PeerThingSystemBehaviourEditorPlugin.getDefault().getNavigationTree().refresh(((SystemBehaviour)state.getSystemBehaviour()).getEditor().getFiletypeReg().getFile(((SystemBehaviour)state.getSystemBehaviour()).getEditor()));
	        }
	        catch (Exception e) {
	        	System.out.println("Cannot get NavigationTree. If this plugin is run stand alone, this should be ok.");
	        }
	        
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
        
    }

    public void undo() {
        //state.getTask().getStates().remove(state);
        state.getTask().removeState(state);
        system.removeState(state);
        system.getEditPart().refresh();
       try { 
        PeerThingSystemBehaviourEditorPlugin.getDefault().getNavigationTree().refresh(((SystemBehaviour)state.getSystemBehaviour()).getEditor().getFiletypeReg().getFile(((SystemBehaviour)state.getSystemBehaviour()).getEditor()));
       }
       catch (Exception e) {
    	System.out.println("Cannot get NavigationTree. If this plugin is run stand alone, this should be ok.");
       }
    }
}