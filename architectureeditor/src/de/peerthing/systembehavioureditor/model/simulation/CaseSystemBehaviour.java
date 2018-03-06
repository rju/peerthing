package de.peerthing.systembehavioureditor.model.simulation;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.systembehavioureditor.model.IAction;
import de.peerthing.systembehavioureditor.model.ICaseArchitecture;
import de.peerthing.systembehavioureditor.model.ICondition;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;
import de.peerthing.systembehavioureditor.model.ITransitionContent;

/**
 * Default implementation of ICaseArchitecture
 * 
 * @author mg
 * @reviewer Hendrik Angenendt
 */
public class CaseSystemBehaviour implements ICaseArchitecture {
    List<ITransitionContent> content;

    ICondition condition;

    String expression;

    private List<IAction> actions;

    private List<ICondition> conditions;

    public CaseSystemBehaviour() {
        content = new ArrayList<ITransitionContent>();
        actions = new ArrayList<IAction>();
    }

    public void setCondition(ICondition condition) {
        this.condition = condition;
    }

    public ICondition getCondition() {
        return condition;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public List<ITransitionContent> getContents() {
        return content;
    }

    public void setContents(List<ITransitionContent> content) {
        this.content = content;
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

    public void removeAction(IAction action) {
        actions.remove(action);
    }

    public void removeCondition(ICondition con) {
        conditions.remove(con);
    }

    /**
     * This is not needed in the simulation classes
     */
	public ISystemBehaviour getSystemBehaviour() {
		return null;
	}

}
