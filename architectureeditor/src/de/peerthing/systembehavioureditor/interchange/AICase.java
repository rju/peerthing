package de.peerthing.systembehavioureditor.interchange;

import de.peerthing.systembehavioureditor.model.ICaseArchitecture;

class AICase extends AIContentContainer implements IAICase {
	private ICaseArchitecture caseA;
	private IAICondition condition;

	public AICase(ICaseArchitecture caseA, IAICondition condition) {
		super(caseA);
		this.caseA = caseA;
		this.condition = condition;
	}


	public IAICondition getCondition() {
		return condition;
	}

	public String getExpression() {
		return caseA.getExpression();
	}

}
