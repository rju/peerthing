package de.peerthing.simulation;

import java.util.Map;

import de.peerthing.systembehavioureditor.interchange.IAIAction;
import de.peerthing.systembehavioureditor.interchange.IAIContentContainer;
import de.peerthing.systembehavioureditor.interchange.IAIParameter;

public class TestAction implements IAIAction{

	String name;
	Map<String, IAIParameter> parameters;
	
	public void setName(String name){
		this.name = name; 
	}
	
	public String getName() {
		
		return this.name;
	}

	public void setParameters(Map<String, IAIParameter> parameters){
		this.parameters = parameters;
	}
	
	public Map<String, IAIParameter> getParameters() {
		return this.parameters;
	}

	public String getResult() {
		return null;
	}

	public IAIContentContainer getContainer() {
		return null;
	}
	

}
