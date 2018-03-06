package de.peerthing.scenarioeditor.model.impl;

import de.peerthing.scenarioeditor.model.IDistribution;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.INodeResource;
import de.peerthing.scenarioeditor.model.IResourceCategory;
import de.peerthing.scenarioeditor.model.IScenario;

/**
 * Default implementation of INodeResource.
 * 
 * @author Michael Gottschalk
 * @reviewer Hendrik Angenendt
 */
public class NodeResource implements INodeResource {
    private IResourceCategory category;

    private IDistribution numberDistribution;

    private INodeCategory node;

    /**
     * the standard constructor (which is used when the user add an element
     * of this kind.     
     */
    public NodeResource() {
    }
    
    public NodeResource(INodeResource original, IScenario scenario, INodeCategory nodeCategory) {
    	this.numberDistribution = new Distribution(original.getNumberDistribution(), scenario);
    	this.node = nodeCategory;
    	this.category = original.getCategory();
    }    
    
    /**
     * creates an copy of the handed original object. the copy object saves
     * the old values of the original object so the undo operation can
     * work
     * @param original
     */
    public NodeResource(INodeResource original) {
    	this.numberDistribution = new Distribution(original.getNumberDistribution());
    	this.node = original.getNode();
    	this.category = original.getCategory();
    }

    public void setCategory(IResourceCategory category) {
        this.category = category;
    }

    public IResourceCategory getCategory() {
        return category;
    }

    public void setNumberDistribution(IDistribution distribution) {
        this.numberDistribution = distribution;
    }

    public IDistribution getNumberDistribution() {
        return numberDistribution;
    }

    public void setNode(INodeCategory node) {
        this.node = node;
    }

    public INodeCategory getNode() {
        return node;
    }

    //TODO: unter umstaenden ist node noch nicht erstellt und dies gibt eine nullpointer.
    public IScenario getScenario() {
        return node.getScenario();
    }

}
