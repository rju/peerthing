package de.peerthing.systembehavioureditor.model.editor;

import java.io.Serializable;

import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.IVariable;

/**
 * Implementation of IVariable for the architecture editor.
 * 
 * @author mg
 * @review Sebastian Rohjans 27.03.2006
 */
public class Variable implements IVariable, Serializable {
	private static final long serialVersionUID = -2728960989988098069L;

	private ITask task = null;

	private String name;

	private String initialValue;

	private INodeType node = null;

	public Variable() {
	}

	public Variable(Variable copy) {
		this.name = copy.name;
		this.initialValue = copy.initialValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String newName) {
	        // avoid empty state names
	        if (newName.equals("")) {
	        	
	        		newName = "" + this.hashCode();
	        	
	        	System.out.println("Changed Variable name because a String must not be empty.");
	        }
	        
	        // avoid oversized names
	        if (newName.getBytes().length > 31) {
	        	newName = (String) newName.subSequence(0, 31);
	        	System.out.println("Changed Variable name because a String must not be too long.");
	        } 
		name = newName;
		
	}

	public ISystemBehaviour getSystemBehaviour() {
		
		return node.getSystemBehaviour();
	}

	public ITask getTask() {
		return this.task;
	}
	
	public void setTask(ITask obj){
		this.task = obj;
	}

	public INodeType getNode() {
		return this.node;
	}

	public void setNode(INodeType type2) {
		this.node = type2;
	}

    public String getInitialValue() {
    	if (this.initialValue == null)
    		this.initialValue = "";
        return initialValue;
    }

    public void setInitialValue(String value) {
        this.initialValue = value;
    }

}
