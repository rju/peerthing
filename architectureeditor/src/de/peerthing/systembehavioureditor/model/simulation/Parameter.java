package de.peerthing.systembehavioureditor.model.simulation;

import de.peerthing.systembehavioureditor.model.IAction;
import de.peerthing.systembehavioureditor.model.IParameter;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;

/**
 * Default implementation of IParameter
 * 
 * @author mg
 * @reviewer Hendrik Angenendt
 * 
 */
public class Parameter implements IParameter {
    private String name = "";

    private String value = "";

    private String expression = "";

    private IAction action;

    private String key;

    public Parameter(String s, IAction action, String key) {
        this.action = action;
        this.name = s;
        this.key = key;
        // this.action=action;
    }

    public Parameter() {
    }

    public void setAction(IAction action) {
        this.action = action;
    }

    public IAction getAction() {
        return action;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

//    public String toString() {
//        return "Parameter(" + name + ")";
//    }

    public String getKey() {
        return key;
    }

    /**
     * This is not needed for the simulation classes
     */
	public ISystemBehaviour getSystemBehaviour() {
		return null;
	}
}
