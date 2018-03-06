package de.peerthing.systembehavioureditor.model.editor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.peerthing.systembehavioureditor.model.ICaseArchitecture;
import de.peerthing.systembehavioureditor.model.ICondition;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;
import de.peerthing.systembehavioureditor.model.ITransitionContent;


/**
 * @author Michael Gottschalk
 * @review Sebastian Rohjans 27.03.2006
 */

public class CaseSystemBehaviour implements ICaseArchitecture, Serializable {
	
	private static final long serialVersionUID = 1L;
	List<ITransitionContent> content = new ArrayList<ITransitionContent>();
	ICondition condition;
	String expression;
	
	public CaseSystemBehaviour() {
		content = new ArrayList<ITransitionContent>();
	}

	public CaseSystemBehaviour(String string, ICondition con) {
        expression = string;
        this.condition = con;
        }

    public CaseSystemBehaviour(CaseSystemBehaviour copy) {
    	expression = copy.expression;
		content = new ArrayList<ITransitionContent>();

		for (int key=0; key <= copy.content.size()-1; key++){
			if (copy.content.get(key) instanceof Action) {
				content.add(new Action((Action)copy.content.get(key)));
				content.get(key).setContainer(copy);
			} else if (copy.content.get(key) instanceof ICondition) {
				content.add(new Condition((Condition)copy.content.get(key)));
				content.get(key).setContainer(copy);
			} 
		}
	}

    public void setCondition(ICondition condition) {
		this.condition = condition;
	}

	public ICondition getCondition() {
		return condition;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getExpression() {
		return expression;
	}

	public List<ITransitionContent> getContents() {
		return content;
	}
	
	public void setContents(List<ITransitionContent> content) {
		this.content = content;
	}
	
	public ISystemBehaviour getSystemBehaviour() {
		return condition.getSystemBehaviour();
	}
    
}