package de.peerthing.scenarioeditor.model;

import java.util.List;

public interface IListWithParent<E> extends List<E>, IScenarioObject {
    /**
     * Returns the name of this list
     * @return
     */
    public String getName();
    
    
    /**
     * Returns the parent of this list, i.e. the object
     * to which this list belongs
     * 
     * @return
     */
    public Object getParent();
    
    /**
     * sets the scenario this IListWithParent belongs to
     */
    public void setScenario(IScenario scenario);
}
