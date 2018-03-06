package de.peerthing.systembehavioureditor.gefeditor.commands;


import org.eclipse.gef.commands.Command;

import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Node;
import de.peerthing.systembehavioureditor.model.editor.Task;
import de.peerthing.systembehavioureditor.model.editor.State;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;

/**
 * This class manages the creation of nodes.
 * 
 * @author Peter Schwenkenberg
 * 
 */
public class CreateNodeCommand extends Command{
	
	int x,y;
    private final SystemBehaviour architecture;
    private final Node node;
    
    public CreateNodeCommand(SystemBehaviour architecture, Node node, int x, int y) {
        super("Create Node");
        System.out.println("in createNodeCommand");
        this.architecture = architecture;
        this.node = node;
        this.x = x;
        this.y = y;
    }

    /*
     * Create a new main Task for the new Node.
     * Remeber: Nodes itself are not shown. The definition of
     * a new main Task has the meaning of creating a new node.
     * @see org.eclipse.gef.commands.Command#execute()
     */
    public void execute() {
    	System.out.println("in CreateNodeCommand()::execute()");
    	Task task = (Task) node.getStartTask();
    	task.setX(x);
    	task.setY(y);
    	task.setName("Main task of node " + node.getName() );
    	task.setNode(node);
    	//node.addTask((ITask) task); // to redo?
    	
    	State state = (State) task.getStartState();
    	state.setTask(task);
    	//state.setName("Start State of Task: " + task.getName());
    	//task.addState((IState) state);
    	state.setX(Math.abs(x-29));
    	state.setY(java.lang.Math.abs(y-61));
    	
    	//task.setStartState(state);
    	
    	node.setArchitecture((ISystemBehaviour)architecture);
    	redo();
    }

    public void redo() {
    	architecture.addNode(node);
    	
    }

    public void undo() {
    	architecture.removeNode(node);
    }
	

}
