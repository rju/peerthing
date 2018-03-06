package de.peerthing.scenarioeditor.model.impl;

import java.util.ArrayList;

import de.peerthing.scenarioeditor.model.IListWithParent;
import de.peerthing.scenarioeditor.model.IScenario;

public class ListWithParent<E> extends ArrayList<E> implements IListWithParent<E> {
    /**
     * 
     */
    private static final long serialVersionUID = 8418606808677952854L;
    
    private Object parent;
    private String name;
    private IScenario scenario;
    
    public ListWithParent(Object parent, String name, IScenario scenario) {
        this(parent, name);
        this.scenario = scenario;
    }

    public ListWithParent(Object parent, String name) {
        this.parent = parent;
        this.name = name;
    }
    
    /*public ListWithParent(String name, Object parent, INodeCategory original){
    	if (name.equals("Node Connections")){
    		INodeCategory nodeCategory = (INodeCategory)parent;
    		for(INodeConnection nodeCon : original.getConnections()){
    			nodeCategory.getConnections().add(nodeCon);
    		}
    	}
    }*/
    
    public String getName() {
        return name;
    }


    public Object getParent() {
        return parent;
    }
    
    @Override
    public String toString() {
        return name;
    }

    public IScenario getScenario() {
        return scenario;
    }

    public void setScenario(IScenario scenario) {
        this.scenario = scenario;
    }

	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof ListWithParent) {
			return arg0 == this;
		}
		return false;		
	}
    
    

}
