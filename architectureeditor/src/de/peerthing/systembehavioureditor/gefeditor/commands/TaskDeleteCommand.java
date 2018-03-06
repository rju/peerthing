package de.peerthing.systembehavioureditor.gefeditor.commands;

import java.util.Vector;

import org.eclipse.gef.commands.Command;

import de.peerthing.systembehavioureditor.PeerThingSystemBehaviourEditorPlugin;
import de.peerthing.systembehavioureditor.gefeditor.editparts.TaskEditPart;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.ITransition;
import de.peerthing.systembehavioureditor.model.editor.Node;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Task;
import de.peerthing.systembehavioureditor.model.editor.Transition;

/**
 * This class manages commands for deletion of a Task
 * including undo and redo commands.
 * 
 * @author Peter Schwenkenberg
 * @review Michael, 2006-03-23
 */
public class TaskDeleteCommand extends Command {

    private final Task task;
    private final SystemBehaviour system;

    public TaskDeleteCommand(Task task, SystemBehaviour system) {

        super("Delete task");
        this.task = task;
        this.system = system;
    }

    /**
     * When a Task is created (through the undo-delete command),
     * reconnect all Transitions.
     */
  
    public void execute() {
    	
        redo();
    }

    public void redo() {
    	
    	// deleting the start task: just don't do it!
    	if (task.getNode().getStartTask().equals(task)) {
    		
    		System.out.println("You are not allowed to delete the main task.");
    		return;
    	}
    	
    	// delete the outgoing transition
    	try {
    	Vector<ITransition> iterateList = new Vector<ITransition>();
    	iterateList.addAll(task.getTransitionsOutgoing());
    	
        for (ITransition t : iterateList) {
        	TransitionDeleteCommand del = new TransitionDeleteCommand((Transition)t);
        	del.execute();
        }
        
        // delete incoming transitions
        iterateList = new Vector<ITransition>();
    	iterateList.addAll(task.getTransitionsIncoming());
    	
        for (ITransition t : iterateList) {
        	TransitionDeleteCommand del = new TransitionDeleteCommand((Transition)t);
        	del.execute();
        }
    	
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	try {
    	// Set the states of that task to the main task, so they can be connected to those states.
    		
    	Vector<IState>iterateList = new Vector<IState>();
        iterateList.addAll(task.getStates());
    		
        for (IState s : iterateList) {
        	s.setTask(s.getTask().getNode().getStartTask());
        	s.getTask().getNode().getStartTask().addState(s);
        }

        system.removeTask(task);
        ((Node)task.getNode()).removeTask(task);
        
        // dirty work around
        ((TaskEditPart)task.getEditPart()).getFigure().setVisible(false);
        ((TaskEditPart)task.getEditPart()).setSelected(0);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
       
        try {
            PeerThingSystemBehaviourEditorPlugin.getDefault().getNavigationTree().refresh(((SystemBehaviour)task.getSystemBehaviour()).getEditor().getFiletypeReg().getFile(((SystemBehaviour)task.getSystemBehaviour()).getEditor()));
        }
        catch (Exception e) {
        	System.out.println("Cannot get NavigationTree. If this plugin is run stand alone, this should be ok.");
        }
       // ((SystemBehaviour)task.getSystemBehaviour()).getEditPart().refresh();
    }

    public void undo() {
    	try {
    		// Remove the old states from the task, because as a design decision, after deleting a task, it's states
    		// are belonging to the main task, so they can be reconnected with other tasks.
    		// Else, we would have the old states duplicated.
    		
    		
    		task.states.clear();
    		
    		//system.addTask(task);
    		((Node)task.getNode()).addTask(task);
    		((TaskEditPart)task.getEditPart()).getFigure().setVisible(true);
    		
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
        try {
            PeerThingSystemBehaviourEditorPlugin.getDefault().getNavigationTree().refresh(((SystemBehaviour)task.getSystemBehaviour()).getEditor().getFiletypeReg().getFile(((SystemBehaviour)task.getSystemBehaviour()).getEditor()));
           }
        catch (Exception e) {
        	System.out.println("Cannot get NavigationTree. If this plugin is run stand alone, this should be ok.");
        }
    }
}