package de.peerthing.systembehavioureditor.model.simulation;

import java.util.Hashtable;
import java.util.Map;

import de.peerthing.systembehavioureditor.model.IAction;
import de.peerthing.systembehavioureditor.model.ICaseArchitecture;
import de.peerthing.systembehavioureditor.model.IContentContainer;
import de.peerthing.systembehavioureditor.model.IParameter;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;

/**
 * Represents an action that is performed as part of a transition.
 * 
 * @author Michael Gottschalk
 * @reviewer Hendrik Angenendt
 */
public class Action implements IAction {
    Map<String, IParameter> parameters;

    String name;

    String result;

    IContentContainer container;

    public Action() {
        parameters = new Hashtable<String, IParameter>();

    }

    public void removeParameter(String name) {
        parameters.remove(name);
    }

    /**
     * Returns the parameters for this action.
     * 
     * @return
     */
    public Map<String, IParameter> getParameters() {
        return parameters;
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
     * @param name
     *            the name of the action
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns an expression which should reference a variable to which the
     * result of the action is to be saved.
     * 
     * @return an expression pointing to a variable
     */
    public String getResult() {
        return result;
    }

    /**
     * Sets an expression which should reference a variable to which the result
     * of the action is to be saved.
     * 
     * @param expression
     *            the expression pointing to a variable
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

    public void setIsCase(boolean help) {

    }

    public boolean isCase() {
        return true;
    }

    public void setCase(ICaseArchitecture helpCase) {

    }

    public ICaseArchitecture getCase() {
        return null;
    }

    /**
     * Is not needed in the simulation classes
     */
	public ISystemBehaviour getSystemBehaviour() {
		return null;
	}

}
