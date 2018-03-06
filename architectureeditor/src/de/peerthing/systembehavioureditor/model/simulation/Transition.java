/*
 * Responsible for the architecture-editor ("archeditor"): Petra Beenken and Peter Schwenkenberg
 */
package de.peerthing.systembehavioureditor.model.simulation;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.systembehavioureditor.model.IAction;
import de.peerthing.systembehavioureditor.model.ICondition;
import de.peerthing.systembehavioureditor.model.IContentContainer;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.ITransition;
import de.peerthing.systembehavioureditor.model.ITransitionContent;
import de.peerthing.systembehavioureditor.model.ITransitionTarget;
/**
 * 
 * @author Peter Schwenkenberg, Petra Beenken
 * @reviewer Hendrik Angenendt
 * 
 */
public class Transition implements ITransition {
    private IState fromState;

    private ITransitionTarget toState;

    private String event;

    private boolean isEndTask = false;

    private List<ITransitionContent> content;

    private ITask startTask;

    private IContentContainer container;

    private List<IAction> actions;

    private List<ICondition> conditions;

    public Transition() {
        content = new ArrayList<ITransitionContent>();
    }

    // //////////////////////////////
    // Interface implementation
    // //////////////////////////////

    public ITransitionTarget getNextState() {
        return toState;
    }

    public void setNextState(ITransitionTarget target) {
        toState = target;
    }

    public boolean isEndTask() {
        return isEndTask;
    }

    public void setEndTask(boolean endTask) {
        isEndTask = endTask;
    }

    public List<ITransitionContent> getContents() {
        return content;
    }

    /**
     * Returns the state to which this transition belongs.
     * 
     * @return the state
     */
    public IState getState() {
        return fromState;
    }

    /**
     * Sets the state to which this transition belongs.
     * 
     * @param state
     *            the state
     */
    public void setState(IState state) {
        this.fromState = state;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String newName) {
        this.event = newName;
    }

    /**
     * Sets the task to be started.
     * 
     * @param task
     */
    public void setStartTask(ITask task) {
        this.startTask = task;
    }

    /**
     * Sets the state to which this transition belongs.
     * 
     * @param state
     *            the state
     */
    public ITask getStartTask() {
        return startTask;
    }

    /**
     * Returns the name of the transistion target.
     * 
     * @return the name
     */
    public String getName() {
        return toState.getName();
    }

    /**
     * Sets the name of the transition target. This must be unique among all
     * transition targets since it is used as in identifier in the XML
     * representation.
     * 
     * setNextState() must be executed bevore this, or nullpointerexception will be caused
     * 
     * @param name
     *            the name
     */
    public void setName(String name) {
        toState.setName(name);
    }

    /**
     * Sets the container to which this transition content belongs (either
     * ITransition or ICaseArchitecture)
     * 
     * @param transition
     */
    public void setContainer(IContentContainer container) {
        this.container = container;
    }

    /**
     * Returns the container to which this transition content belongs (either
     * ITransition or ICaseArchitecture)
     * 
     * @return
     */
    public IContentContainer getContainer() {
        return container;
    }

    public void setContents(List<ITransitionContent> contents) {
        this.content = contents;
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

    /**
     * The next two methods are not needed 
     * in the simulation classes.
     */
    public void connect() {
    }

	public ISystemBehaviour getSystemBehaviour() {
		return null;
	}
}