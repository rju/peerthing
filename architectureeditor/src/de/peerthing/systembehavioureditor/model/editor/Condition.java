package de.peerthing.systembehavioureditor.model.editor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.peerthing.systembehavioureditor.model.ICaseArchitecture;
import de.peerthing.systembehavioureditor.model.ICondition;
import de.peerthing.systembehavioureditor.model.IContentContainer;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;

/**
 * @author Michael Gottschalk
 * @review Sebastian Rohjans 27.03.2006
 * 
 */

public class Condition implements ICondition, Serializable {

	private static final long serialVersionUID = 1L;

	List<ICaseArchitecture> cases;

	ICaseArchitecture defaultCase;

	IContentContainer container;

	private String name = "Condition";

	public Condition() {
		cases = new ArrayList<ICaseArchitecture>();
	}

	public Condition(CaseSystemBehaviour defaultCase) {
		cases = new ArrayList<ICaseArchitecture>();
		this.defaultCase = defaultCase;
	}

	public Condition(Condition copy) {
		cases = new ArrayList<ICaseArchitecture>();
		for (int key = 0; key <= copy.cases.size() - 1; key++) {
			cases.add(new CaseSystemBehaviour((CaseSystemBehaviour) copy
					.getCase(key)));
			cases.get(key).setCondition(this);
		}
		defaultCase = new CaseSystemBehaviour(
				(CaseSystemBehaviour) copy.defaultCase);
		defaultCase.setCondition(this);
		name = copy.name;

	}

	public List<ICaseArchitecture> getCases() {
		return cases;
	}

	public void setName(String newName) {
		
	        // avoid empty state names
	        if (newName.equals("")) {
	        	
	        		newName = "" + this.hashCode();
	        	
	        	System.out.println("Changed Condition name because a String must not be empty.");
	        }
	        
	        // avoid oversized names
	        if (newName.getBytes().length > 31) {
	        	newName = (String) newName.subSequence(0, 31);
	        	System.out.println("Changed Condition name because a String must not be too long.");
	        } 
		    name = newName;
		
	}

	public String getName() {
		return name;
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

	public int addCase(ICaseArchitecture caseArch) {
		cases.add(caseArch);
		return cases.indexOf(caseArch);
	}

	public CaseSystemBehaviour getCase(int indx) {
		return ((CaseSystemBehaviour) cases.get(indx));
	}

	public void removeCase(ICaseArchitecture caseA) {
		cases.remove(caseA);
	}

	public ISystemBehaviour getSystemBehaviour() {
		return container.getSystemBehaviour();
	}
}