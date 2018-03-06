package de.peerthing.scenarioeditor.model.impl;

import java.util.Hashtable;
import java.util.Map;

import de.peerthing.scenarioeditor.model.ICommandContainer;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IUserAction;

/**
 * Default implementation of IUserAction.
 * 
 * @author Michael Gottschalk, Patrik Schulz
 * @reviewer Hendrik Angenendt
 */
public class UserAction extends Command implements IUserAction {
    private String name;

    private double probability;

    private Map<String, String> parameters;

    /**
     * the standard constructor (which is used when the user add an element
     * of this kind). The handed scenario is set so the object knows to
     * which element this object belongs.     
     */
    public UserAction(IScenario scenario) {
    	super(scenario);    	
        parameters = new Hashtable<String, String>();
        this.name = "unknown";
        this.probability = 100;
    }
    
    /**
     * creates an copy of the handed original object. the copy object saves
     * the old values of the original object so the undo operation can
     * work
     * @param original
     */
    public UserAction(IUserAction original) {
    	super(original.getScenario());
        this.name = original.getName();    	
    	this.parameters = new Hashtable<String, String>();    	
    	    	    	
		String[][] params = new String[original.getParameters()
					.size()][2];
		int i = 0;
		for (String key : original.getParameters().keySet()) {
			params[i][0] = key;
			params[i][1] = original.getParameters().get(key);
			this.parameters.put(params[i][0], params[i][1]);			
			i++;
		}    	    	
    	
    	this.probability = original.getProbability();
    }

    
    public UserAction(IUserAction original, IScenario scenario, Object container) {
    	super(scenario);    	
        this.name = original.getName();
        this.setCommandContainer((ICommandContainer)container);
    	parameters = new Hashtable<String, String>();
        
    	String[][] params = new String[original.getParameters()
    	     .size()][2];
        int i = 0;
    	for (String key : original.getParameters().keySet()) {
    	    params[i][0] = key;
    	    params[i][1] = original.getParameters().get(key);
    	    this.parameters.put(params[i][0], params[i][1]);			
    	    i++;
    	}
    	this.probability = original.getProbability();
    }           

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public double getProbability() {
        return probability;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }        

}
