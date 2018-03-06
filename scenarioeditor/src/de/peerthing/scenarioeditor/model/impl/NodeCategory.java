package de.peerthing.scenarioeditor.model.impl;

import de.peerthing.scenarioeditor.model.IUserBehaviour;
import de.peerthing.scenarioeditor.model.IListWithParent;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.INodeConnection;
import de.peerthing.scenarioeditor.model.INodeResource;
import de.peerthing.scenarioeditor.model.IScenario;

/**
 * Default implementation of INodeCategory.
 * 
 * @author Michael Gottschalk, lethe, Patrik 
 * @reviewer Hendrik Angenendt
 */
public class NodeCategory implements INodeCategory {
    private String name;

    private String nodeType;

    private IUserBehaviour primaryBehaviour;

    private IScenario scenario;

    private IListWithParent<INodeConnection> connections;

    private IListWithParent<IUserBehaviour> behaviours;

    private IListWithParent<INodeResource> resources;

    /**
     * the standard constructor (which is used when the user add an element
     * of this kind. The handed scenario is set so the object knows to
     * which element this object belongs.     
     */
    public NodeCategory(IScenario scenario) {
    	this.scenario = scenario;
        this.name = "unknown";
        this.nodeType = "unknown";
        connections = new ListWithParent<INodeConnection>(this, "Node Connections", scenario);
        behaviours = new ListWithParent<IUserBehaviour>(this, "Node Behaviours", scenario);
        resources = new ListWithParent<INodeResource>(this, "Node Resources", scenario);
    }
    
    
    /**
     * creates an copy of the handed original object. the copy object saves
     * the old values of the original object so the undo operation can
     * work
     * @param original
     */
    public NodeCategory(INodeCategory original) {
    	this.scenario = original.getScenario();
        this.name = original.getName();
        this.nodeType = original.getNodeType();
        /*connections = original.getConnections();
        behaviours = original.getBehaviours();
        resources = original.getResources();*/
    }
    
    public NodeCategory(INodeCategory original, IScenario scenario) {
    	this.scenario = scenario;
        this.name = original.getName();
        this.nodeType = original.getNodeType();
        this.primaryBehaviour = original.getPrimaryBehaviour();        
        connections = new ListWithParent<INodeConnection>(this, "Node Connections", scenario);
        behaviours = new ListWithParent<IUserBehaviour>(this, "Node Behaviours", scenario);
        resources = new ListWithParent<INodeResource>(this, "Node Resources", scenario);
        for (IUserBehaviour beh: original.getBehaviours()){
        	behaviours.add(new UserBehaviour(beh, scenario, this));
        }
        if (original.getScenario().equals(scenario)){
        	for (INodeConnection con: original.getConnections()){
        		connections.add(new NodeConnection(con, scenario, this));
        	}
        	for (INodeResource res: original.getResources()){
        		resources.add(new NodeResource(res, scenario, this));
        	}
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setPrimaryBehaviour(IUserBehaviour behaviour) {
        this.primaryBehaviour = behaviour;
    }

    public IUserBehaviour getPrimaryBehaviour() {
        return primaryBehaviour;
    }

    public IListWithParent<INodeConnection> getConnections() {
        return connections;
    }

    public IListWithParent<INodeResource> getResources() {
        return resources;
    }

    public IListWithParent<IUserBehaviour> getBehaviours() {
        return behaviours;
    }

    public void setScenario(IScenario scenario) {
        this.scenario = scenario;
        connections.setScenario(scenario);
        behaviours.setScenario(scenario);
        resources.setScenario(scenario);
    }

    public IScenario getScenario() {
        return scenario;
    }

}
