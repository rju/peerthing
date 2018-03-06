package de.peerthing.systembehavioureditor.interchange;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.systembehavioureditor.model.ICaseArchitecture;
import de.peerthing.systembehavioureditor.model.ICondition;

class AICondition implements IAICondition {
    private IAIContentContainer container;
    private List<IAICase> cases = new ArrayList<IAICase>();
    private IAICase defCase;

    public AICondition(ICondition condition, IAIContentContainer container) {
        this.container = container;

        for (ICaseArchitecture c : condition.getCases()) {
        	cases.add(new AICase(c, this));
        }

        defCase = new AICase(condition.getDefaultCase(), this);

    }


    public List<IAICase> getCases() {
        return cases;
    }

    public IAICase getDefaultCase() {
        return defCase;
    }

    public IAIContentContainer getContainer() {
        return container;
    }

}
