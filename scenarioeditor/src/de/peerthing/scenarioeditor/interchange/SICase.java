package de.peerthing.scenarioeditor.interchange;

import de.peerthing.scenarioeditor.model.ICase;

class SICase extends SICommandContainer implements ISICase {
	ICase c;

	public SICase(ICase c) {
        super(c);
		this.c = c;
	}


	public String getCondition() {
		return c.getCondition();
	}

	public double getProbability() {
		return c.getProbability();
	}

	public boolean isConditionUsed() {
		return c.isConditionUsed();
	}

}
