package de.peerthing.simulation;

import de.peerthing.systembehavioureditor.interchange.IAIAction;
import de.peerthing.systembehavioureditor.interchange.IAIParameter;

public class TestParameter implements IAIParameter{

	String name;
	String value;
	String expression;
	
	public IAIAction getAction() {
		return null;
	}

	public void setExpression(String expression){
		this.expression = expression;
	}
	public String getExpression() {
		return this.expression;
	}

	public void setName(String name){
		this.name = name;
	}
	public String getName() {
		return this.name;
	}

	public void setValue(String value){
		this.value = value;
	}
	public String getValue() {
		return this.value;
	}
	

}
