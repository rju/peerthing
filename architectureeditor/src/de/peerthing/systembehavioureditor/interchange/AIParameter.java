package de.peerthing.systembehavioureditor.interchange;

import de.peerthing.systembehavioureditor.model.IParameter;

class AIParameter implements IAIParameter {
	private IParameter param;
	private IAIAction parent;

	public AIParameter(IParameter param, IAIAction parent) {
		this.param = param;
		this.parent = parent;
    }


    public String getName() {
        return param.getName();
    }

    public String getValue() {
        return param.getValue();
    }

    public String getExpression() {
        return param.getExpression();
    }

    public IAIAction getAction() {
        return parent;
    }

}
