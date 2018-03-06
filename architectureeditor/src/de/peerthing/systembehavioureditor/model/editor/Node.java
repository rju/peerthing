package de.peerthing.systembehavioureditor.model.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.ITransition;
import de.peerthing.systembehavioureditor.model.IVariable;

/**
 * 
 * This class implements INode to model types of nodes within the editor.
 * The GEF-Editor needs a view, where the user can spedify different kinds of Nodes, like Peer, SuperPeer or SuperSuperPeer.
 * 
 * @review Sebastian Rohjans 27.03.2006
 * @author Peter Schwenkenberg
 *
 */

public class Node extends ModelObject implements INodeType{

	/**
	 * 
	 */
	private static final long serialVersionUID = 519827362214673776L;
	
	/**
	 * The color of elements of this node.
	 */
	private Color color = null;

	/**
	 * The name of a node, e.g. "Peer".
	 */
	private String name;
	
	private List<IVariable> variables;
	private List<ITask> tasks;
	private ITask startTask;
	private ISystemBehaviour architecture;
	
	public Node(String name) {
		this();
		this.name = name;
	}
	
	public Node() {

		variables = new ArrayList<IVariable>();
		tasks = new ArrayList<ITask>();
	}
	
	public Node(boolean fromStart) {
		
		variables = new ArrayList<IVariable>();
		tasks = new ArrayList<ITask>();
		
		Task mainTask = new Task();
    	mainTask.setNode(this);
    	mainTask.setName("main"); // create the main task
    	this.addTask(mainTask); // add it to the node
    	this.setStartTask(mainTask);
    	State startState = new State(); // every state chart must have a start state
    	startState.setName("StartState");
    	startState.setX(102);
    	startState.setY(186);
    	mainTask.addState(startState);
    	mainTask.setStartState(startState);
    	mainTask.setX(131);
    	mainTask.setY(247);
    	startState.setTask(mainTask);
    	// draw transition between mainTask and startState
    	Transition tran = new Transition();
    	tran.setStartTask(mainTask);
    	tran.setState((IState) mainTask);
    	tran.setNextState(startState);
    	tran.connect();
	}
	
	public List<IVariable> getVariables() {
		return variables;
	}

	public List<ITask> getTasks() {
		return tasks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		try {
			((SystemBehaviour)this.getSystemBehaviour()).getEditor().renameTab(this);
		}
		catch (Exception e) {
			// catch silently
		}
	}
	
	// not interfaced
	public void addTask(ITask task) {
		tasks.add(task);
		firePropertyChange("tasks", null, task);
	}
	
	public void removeTask(ITask task) {
		tasks.remove(task);
		firePropertyChange("tasks", task, null);
	}

	public void setArchitecture(ISystemBehaviour architecture) {
		this.architecture = architecture;
	}

	public ISystemBehaviour getArchitecture() {
		return architecture;
	}

	public void setStartTask(ITask startTask) {
		this.startTask = startTask;
	}

	public ITask getStartTask() {
		return startTask;
	}
	
	public String toString () {
		return this.getName();
	}
	
	public String findDistinctiveStateName() {
		
		int numberState = 1;
		
		// count states of that node
		for (ITask t : this.getTasks()) {
			numberState += t.getStates().size();
		}
		
		String proposedName = "SomeState"; // should never be the computed name
		
		for(int i = numberState + 1; i > 0; i--) {
			proposedName = "State_" + numberState; 
			
			// check whether this name is unique
			for (ITask t : this.getTasks()) {
				for (IState s : t.getStates()) {
					if (s.getName().equals(proposedName)) {
						numberState++;
						i = numberState; // reset outer loop
					}
				}
			}
		}
		
		return proposedName;
	}
	
	public String findDistinctiveTaskName() {
		
		
		int numberTask = this.getTasks().size();
		
		String proposedName = "SomeTask"; // should never be the computed name
		
		for(int i = numberTask + 1; i > 0; i--) {
			proposedName = "Task_" + numberTask; 
			
			// check whether this name is unique
			for (ITask t : this.getTasks()) {
				if (t.getName().equals(proposedName)) {
					numberTask++;
					i = numberTask; // reset outer loop
				}
			}
		}
		
		return proposedName;
	}
	
public String findDistinctiveTransitionName() {

		String proposedName = "SomeTransition"; // should never be the computed name
		int numberTran = 1;
		
		try {
		// count transitions
		for (ITask t : this.getTasks()) {
			for (IState s : t.getStates()) {
				numberTran += s.getTransitions().size();
			}
			
			if (t.getStartState() != null) {
				numberTran += 1;
			}
		}
		
		
		for(int i = numberTran + 1; i > 0; i--) {
			proposedName = "Transition_" + numberTran; 
			
			// check whether this name is unique
			for (ITask t : this.getTasks()) {
				for (IState s : t.getStates()) {
					for (ITransition tr : s.getTransitions()) {
						if (tr.getEvent().equals(proposedName)) {
							numberTran++;
							i = numberTran; // reset outer loop
						}
					}
				}
			}
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return proposedName;
	}

	public ISystemBehaviour getSystemBehaviour() {
		return architecture;
	}
	
	public void setColor(Color c) {
		this.color = c;
	}
	
	public Color getColor() {
		return this.color;
	}

	public void setVariables(List<IVariable> vars) {
		this.variables = vars;
	}
	
	public void calculateColor() {
		int indexOfNode =  getArchitecture().getNodes().indexOf(this);
		Color newColor = new Color(Display.getCurrent(), java.lang.Math.abs(
				(255-indexOfNode*60)%256), java.lang.Math.abs((252-indexOfNode*50)%256),
				java.lang.Math.abs((230-indexOfNode*1)%256));
		
		this.color = newColor;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return null;
	}

	@Override
	public Object getPropertyValue(Object id) {
		return null;
	}

	@Override
	public void setPropertyValue(Object id, Object value) {		
	}

}