package de.peerthing.systembehavioureditor.model.simulation;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.IVariable;

/**
 * Default implementation of INodeType
 * 
 * @author mg
 * @reviewer Hendrik Angenendt
 */

public class Node implements INodeType {
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

    /**
     * This is not needed for the simulation classes
     */
	public ISystemBehaviour getSystemBehaviour() {
		return null;
	}

	public void setVariables(List<IVariable> vars) {
		this.variables = vars;
	}

}
