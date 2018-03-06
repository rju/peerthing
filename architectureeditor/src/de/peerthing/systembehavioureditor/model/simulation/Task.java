/*
 * Responsible for the architecture-editor ("archeditor"): Petra Beenken and Peter Schwenkenberg
 */
package de.peerthing.systembehavioureditor.model.simulation;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.IVariable;
/**
 * @author Peter Schwenkenberg, Petra Beenken
 * @reviewer Hendrik Angenendt
 * 
 */
public class Task implements ITask {

    /**
     * The position of the task to be drawn in the editor.
     */
    private int x = 10, y = 10;

    /**
     * The name of a task (has to be unique).
     */
    private String name = "";

    private List<IVariable> variables = new ArrayList<IVariable>();

    private List<IState> states;

    private INodeType node;

    /**
     * The first state of the task to be executed.
     */
    IState startState;

    public Task() {
        super();

        variables = new ArrayList<IVariable>();
        states = new ArrayList<IState>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public IState getStartState() {
        return startState;
    }

    /**
     * Sets the state in which this task starts running.
     * 
     * @param state
     *            the start state
     */

    /**
     * Adds a state to a task. Use this instead of adding the state to the field
     * manuelly because of side effects that need to be taken care of in the
     * editor.
     * 
     * @param state
     */
    public void addState(IState state) {
        this.states.add(state);
    }

    public void setStartState(IState state) {
        this.startState = state;
    }

    public List<IVariable> getVariables() {
        return variables;
    }

    public List<IState> getStates() {
        return states;
    }

    public INodeType getNode() {
        return node;
    }

    public void setNode(INodeType node) {
        this.node = node;
    }

    public void removeState(IState state) {

    }

    /**
     * This is not needed in the simulation classes.
     */
	public ISystemBehaviour getSystemBehaviour() {
		return null;
	}

	public void setVariables(List<IVariable> vars) {
		this.variables = vars;
		
	}

}
