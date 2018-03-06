package de.peerthing.systembehavioureditor.model.simulation;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.systembehavioureditor.model.ICaseArchitecture;
import de.peerthing.systembehavioureditor.model.ICondition;
import de.peerthing.systembehavioureditor.model.IContentContainer;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;

/**
 * Default implementation of ICondition
 * 
 * @author mg
 * @reviewer Hendrik Angenendt
 */
public class Condition implements ICondition {
    List<ICaseArchitecture> cases;

    ICaseArchitecture defaultCase;

    IContentContainer container;

    ICaseArchitecture helpCase;

    boolean help;

    public Condition() {
        cases = new ArrayList<ICaseArchitecture>();
    }

    public List<ICaseArchitecture> getCases() {
        return cases;
    }

    public void setDefaultCase(ICaseArchitecture defaultCase) {
        this.defaultCase = defaultCase;
    }

    public ICaseArchitecture getDefaultCase() {
        return defaultCase;
    }

    public void setContainer(IContentContainer container) {
        this.container = container;
    }

    public IContentContainer getContainer() {
        return container;
    }

    public void removeCase(ICaseArchitecture caseA) {
        cases.remove(caseA);
    }

    public boolean isCase() {
        if (help) {
            return help;
        } else {
            return help;
        }
    }

    public void setIsCase(boolean help) {

        this.help = help;
    }

    public void setCase(ICaseArchitecture helpCase) {

        this.helpCase = helpCase;
    }

    public ICaseArchitecture getCase() {

        return helpCase;

    }

    /**
     * This is not needed in the simulation classes.
     */
	public ISystemBehaviour getSystemBehaviour() {
		return null;
	}
}
