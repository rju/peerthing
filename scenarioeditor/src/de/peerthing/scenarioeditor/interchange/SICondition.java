package de.peerthing.scenarioeditor.interchange;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.scenarioeditor.model.ICase;
import de.peerthing.scenarioeditor.model.IScenarioCondition;

class SICondition extends SICommand implements ISICondition {
	List<ISICase> cases = new ArrayList<ISICase>();
	ISICase defCase;

	public SICondition(IScenarioCondition condition, ISICommandContainer container) {
		this.container = container;

		for (ICase c : condition.getCases()) {
			cases.add(new SICase(c));
		}

		defCase = new SICase(condition.getDefaultCase());
	}

	public List<ISICase> getCases() {
		return cases;
	}

	public ISICase getDefaultCase() {
		return defCase;
	}

	public String getCommandName() {
		return "Condition";
	}


}
