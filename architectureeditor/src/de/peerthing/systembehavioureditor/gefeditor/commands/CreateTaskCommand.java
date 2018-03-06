package de.peerthing.systembehavioureditor.gefeditor.commands;

import org.eclipse.gef.commands.Command;

import de.peerthing.systembehavioureditor.PeerThingSystemBehaviourEditorPlugin;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.editor.State;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Task;
import de.peerthing.systembehavioureditor.model.editor.Node;
import de.peerthing.systembehavioureditor.model.editor.Transition;

/**
 * This class manages (create, redo, undo) commands for Tasks.
 * 
 * @author Peter Schwenkenberg
 * @author Petra Beenken
 * @review Michael, 2006-03-23
 * 
 */
public class CreateTaskCommand extends Command {
	
    int x,y;
    private final SystemBehaviour architecture;
    private final Task task;

    public CreateTaskCommand(SystemBehaviour architecture, Task task, int x, int y) {
        super("Create Task");
        this.architecture = architecture;
        this.task = task;
        this.x = x;
        this.y = y;
    }

    public void execute() {
        task.setX(x);
        task.setY(y);
        // task.setNode(architecture.getNodes().get(0)); // set the node of the task
        if (architecture.getCurrentNode() != null) {
        	task.setNode(architecture.getCurrentNode());
        }
        else {
        	task.setNode(architecture.getNodes().get(0));
        }
        // When using several editor instances for each node as planed, instead of simply selecting the first node
        // (getNodes().get(0)(),
        // something like 'getCurrentNode()' should be called.
        Node node = (Node) task.getNode();
        task.setName( node.findDistinctiveTaskName());
        
        // add an initial start state
    	State state = new State();
    	state.setTask(task);
    	state.setName("Start State of Task: " + task.getName());
    	task.addState((IState) state);
    	task.setStartState(state);
    	state.setX(Math.abs(x-29));
    	state.setY(y+61);
    	// and an implicit transition between them
    	Transition tran = new Transition();
    	tran.setState(task);
    	tran.setNextState(state);
    	tran.connect();
        
        redo();
    }

    public void redo() {
    	if (architecture.getCurrentNode() != null) 
    		architecture.getCurrentNode().getTasks().add(task);
    	else
    		architecture.getNodes().get(0).getTasks().add(task);
        architecture.addTask(task); // for the presentation, shall be made obsolete
    	//((SystemBehaviour)task.getSystemBehaviour()).getEditPart().refresh();
       
        
        try { 
            PeerThingSystemBehaviourEditorPlugin.getDefault().getNavigationTree().refresh(((SystemBehaviour)task.getSystemBehaviour()).getEditor().getFiletypeReg().getFile(((SystemBehaviour)task.getSystemBehaviour()).getEditor()));
           }
        catch (Exception e) {
        	System.out.println("Cannot get NavigationTree. If this plugin is run stand alone, this should be ok.");
        }
    }

    public void undo() {
        //architecture.getNodes().get(0).getTasks().remove(task);
        task.getNode().getTasks().remove(task);
        architecture.removeTask(task);
        try { 
            PeerThingSystemBehaviourEditorPlugin.getDefault().getNavigationTree().refresh(((SystemBehaviour)task.getSystemBehaviour()).getEditor().getFiletypeReg().getFile(((SystemBehaviour)task.getSystemBehaviour()).getEditor()));
           }
        catch (Exception e) {
        	System.out.println("Cannot get NavigationTree. If this plugin is run stand alone, this should be ok.");
        }
    }
}
