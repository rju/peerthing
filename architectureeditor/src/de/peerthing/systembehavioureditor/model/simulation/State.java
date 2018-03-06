package de.peerthing.systembehavioureditor.model.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.lang.String;

import de.peerthing.systembehavioureditor.interchange.EAIInitializeEvaluation;
import de.peerthing.systembehavioureditor.model.IAction;
import de.peerthing.systembehavioureditor.model.ICondition;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.ITransition;
import de.peerthing.systembehavioureditor.model.ITransitionContent;

/**
 * Default implementation of IState
 * 
 * @author Michael Gottschalk
 * @reviewer Hendrik Angenendt 
 */
public class State implements IState {
    private String name;

    // Default position.
    private int x = 50, y = 50;

    private boolean isStartState = false;

    private ITask task;

    private List<ITransitionContent> initializeActions;

    private EAIInitializeEvaluation eval;

    private List<ITransition> transitionsOutgoing = new Vector<ITransition>();

    private List<ICondition> conditions;

    private List<IAction> actions;

    public State() {
        initializeActions = new ArrayList<ITransitionContent>();
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }

    // //////////////////////////////
    // PeerThing specific
    // //////////////////////////////

    public List<ITransition> getTransitions() {
        return transitionsOutgoing;
    }

    public ITask getTask() {
        return task;
    }

    public void setTask(ITask task) {
        this.task = task;
    }

    public boolean getIsStartState() {
        return isStartState;
    }

    public void setIsStartState(Boolean bool) {
        this.isStartState = bool;
    }

    public void setContents(List<ITransitionContent> actions) {
        initializeActions = actions;
    }

    public List<ITransitionContent> getContents() {
        return initializeActions;
    }

    public void setInitializeEvaluation(EAIInitializeEvaluation eval) {
        this.eval = eval;
    }

    public EAIInitializeEvaluation getInitializeEvaluation() {
        return eval;
    }

    public List<IAction> getActions() {
        return actions;
    }

    public List<ICondition> getConditions() {
        return conditions;
    }

    public void setActions(List<IAction> actions) {
        this.actions = actions;
    }

    public void setConditions(List<ICondition> conditions) {
        this.conditions = conditions;
    }

    /*
     * TODO: The following methods are not really needed and should
     * be removed!
     * 
     */
    public void addTransitionIncoming(ITransition tran) {
    }

    public void addTransitionOutgoing(ITransition tran) {
    }

    public void setIsStartState(boolean b) {

    }

    /**
     * This method is not needed in the simulation classes.
     */
	public ISystemBehaviour getSystemBehaviour() {
		return null;
	}

}