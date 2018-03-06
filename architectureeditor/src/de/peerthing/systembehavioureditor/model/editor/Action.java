package de.peerthing.systembehavioureditor.model.editor;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import de.peerthing.systembehavioureditor.model.IAction;
import de.peerthing.systembehavioureditor.model.IContentContainer;
import de.peerthing.systembehavioureditor.model.IParameter;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;


/**
* Represents an action that is performed as part of a transition.
* 
* @author Peter Schwenkenberg, Michael Gottschalk (Interface) 
* @review Sebastian Rohjans 27.03.2006
*
*/
public class Action implements IAction, Serializable {
	
	private static final long serialVersionUID = 1L;
	Map<String, IParameter> parameters;
	String name = "";
	String result="";
	
	IContentContainer container;
	
	public Action() {
		parameters = new Hashtable<String, IParameter>();		
	}
	
	public Action(Action copy) {
		name = copy.name;
		result = copy.result;
		parameters = new Hashtable<String, IParameter>();
		
		for (String key : copy.parameters.keySet()) {
			parameters.put(key, new Parameter((Parameter) copy.parameters.get(key), this));
		}
	}
    
    public Action(String nam){
        this.name = nam;
    }
	/**
	 * Returns the parameters for this action.
	 * 
	 * @return
	 */
	public Map<String, IParameter> getParameters() {
		return parameters;
	}

    public void setParameters(Map<String, IParameter> parameters){
        this.parameters = parameters;
    }

	/**
	 * Returns the name of the action, which should be the name of a function.
	 * 
	 * @return the name of the action
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the action, which should be the name of a function.
	 * 
	 * @param name the name of the action
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns an expression which should reference a variable to which
	 * the result of the action is to be saved.
	 * 
	 * @return an expression pointing to a variable
	 */
	public String getResult() {
		return result;
	}

	/**
	 * Sets an expression which should reference a variable to which
	 * the result of the action is to be saved.
	 *
	 * @param expression the expression pointing to a variable
	 */
	public void setResult(String expression) {
		this.result = expression;
	}

	public void setContainer(IContentContainer container) {
		this.container = container;
	}

	public IContentContainer getContainer() {
		return container;
	}
	
    public void addParameter(Parameter parameter) {
        parameters.put(parameter.getName() ,parameter);
    }
    
    public void removeParameter(String key){
    	parameters.remove(key);
    }
        
    
	public ISystemBehaviour getSystemBehaviour() {
		return container.getSystemBehaviour();
	}
}