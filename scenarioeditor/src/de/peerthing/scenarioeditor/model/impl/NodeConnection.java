package de.peerthing.scenarioeditor.model.impl;

import de.peerthing.scenarioeditor.model.IConnectionCategory;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.INodeConnection;
import de.peerthing.scenarioeditor.model.IScenario;

/**
 * Default implementation of INodeConnection.
 * 
 * @author Michael Gottschalk
 * @reviewer Hendrik Angenendt
 */
public class NodeConnection implements INodeConnection {
    private IConnectionCategory category;

    private int number;

    private INodeCategory node;

    /**
     * the standard constructor (which is used when the user add an element
     * of this kind.     
     */
    public NodeConnection() {
    }
    
    public NodeConnection(INodeConnection original, IScenario scenario, INodeCategory nodeCat) {
    	this.node = nodeCat;
    	this.number = original.getNumberOfNodes();
    	this.category = original.getCategory();
    }
    
    public NodeConnection(int position) {
    	this.number = position;
    }
    
    /**
     * creates an copy of the handed original object. the copy object saves
     * the old values of the original object so the undo operation can
     * work
     * @param original
     */
    public NodeConnection(INodeConnection original) {
    	this.number = original.getNumberOfNodes();
    }

    public void setCategory(IConnectionCategory category) {
        this.category = category;
    }

    public IConnectionCategory getCategory() {
        return category;
    }

    public void setNumberOfNodes(int number) {
        this.number = number;
    }

    public int getNumberOfNodes() {
        return number;
    }

    public void setNode(INodeCategory category) {
        this.node = category;
    }

    public INodeCategory getNode() {
        return node;
    }

    public IScenario getScenario() {
        return node.getScenario();
    }

}
