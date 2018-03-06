package de.peerthing.systembehavioureditor.model.simulation;

import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.IVariable;

/**
 * Default implementation of IVariable
 * 
 * @author mg
 * @reviewer Hendrik Angenendt
 */
public class Variable implements IVariable {
    private String name;

    private String initialValue;

    public Variable() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


	public ISystemBehaviour getSystemBehaviour() {
		return null;
	}

	public ITask getTask() {
		return null;
	}

	public void setTask(ITask task) {		
	}

	public void setNode(INodeType node) {
	}

	public INodeType getNode() {
		return null;
    }

    public String getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(String value) {
        this.initialValue = value;
    }
}
