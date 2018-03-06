package de.peerthing.systembehavioureditor.model.editor;

import java.io.Serializable;

import de.peerthing.systembehavioureditor.model.IAction;
import de.peerthing.systembehavioureditor.model.IParameter;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;

/**
 * Implementation of IParameter for the architecture editor.
 * 
 * @review Sebastian Rohjans 27.03.2006
 * @author mg
 * 
 */
public class Parameter implements IParameter, Serializable {
    private static final long serialVersionUID = 4678712695611977377L;

    private String name = "";

    private String value = "";

    private String expression = "";
    
    private IAction action;


    public Parameter() {
    }

    public Parameter(String s, IAction action) {
        this.action = action;
        this.name = s;
    }  
    
    public Parameter(Parameter copy) {
    	this.name = copy.name.toString();
    	this.value = copy.value.toString();
    	this.expression = copy.expression.toString();
    	this.action = copy.action;
    }
    public Parameter(Parameter copy, Action act) {
    	this.name = copy.name.toString();
    	this.value = copy.value.toString();
    	this.expression = copy.expression.toString();
    	this.action = act;
    }
    
    public void setName(String newName) {
	        // avoid empty state names
	        if (newName.equals("")) {
	        	
	        		newName = "" + this.hashCode();
	        	
	        	System.out.println("Changed Parameter name because a String must not be empty.");
	        }
	        
	        // avoid oversized names
	        if (newName.getBytes().length > 31) {
	        	newName = (String) newName.subSequence(0, 31);
	        	System.out.println("Changed Parameter name because a String must not be too long.");
	        } 
		name = newName;
		
    }

    public String getName() {
        return name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
    	if (this.value == null)
    		this.value = "";
        return value;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
    	if (this.expression == null)
    		this.expression = "";
        return expression;
    }

    public void setAction(IAction action) {
        this.action = action;
    }

    public IAction getAction() {
        return action;
    }


    public String toString() {
        return "Parameter (" + getName() + ")";
    }
    
 
	public ISystemBehaviour getSystemBehaviour() {
		return action.getSystemBehaviour();
	}
}