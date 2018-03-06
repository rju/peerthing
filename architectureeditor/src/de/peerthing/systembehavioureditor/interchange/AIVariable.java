package de.peerthing.systembehavioureditor.interchange;

import de.peerthing.systembehavioureditor.model.IVariable;

class AIVariable implements IAIVariable {
	private IVariable var;

	public AIVariable(IVariable var) {
		this.var = var;
	}


	public String getName() {
		return var.getName();
	}

	public String getInitialValue() {
		return var.getInitialValue();
	}

}
