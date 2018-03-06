package de.peerthing.systembehavioureditor.interchange;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;

class AIArchitecture implements IAIArchitecture {
    private List<IAINodeType> nodes = new ArrayList<IAINodeType>();
    private ISystemBehaviour arch;

    public AIArchitecture(ISystemBehaviour arch) {
    	this.arch = arch;

    	for (INodeType node : arch.getNodes()) {
    		nodes.add(new AINodeType(node, this));
    	}
    }

    public List<IAINodeType> getNodes() {
        return nodes;
    }

    public IAINodeType getNodeImplementationByName(String name) {
    	for (IAINodeType node : this.nodes) {
    		if (node.getName().equals(name))
    			return node;
    	}
    	
    	return null;
    }
    
    public String getName() {
        return arch.getName();
    }
    
}

