package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.scenarioeditor.model.IDefaultCase;
import de.peerthing.scenarioeditor.model.impl.Case;
import de.peerthing.scenarioeditor.model.impl.ExecuteDeletion;
/**
 * A class that allows to delete a case from a behaviour.
 * @author Hendrik Angenendt
 *
 */
public class DeleteCaseAction extends AbstractTreeAction {

    /**
     * This method deletes an existing case from a scenario. Called automatically if according menu point is 
     * selected in pop-up menu
     */
	public void run(IAction action) {
		if(firstSelectedObject instanceof IDefaultCase){
			return;
		}
		if (firstSelectedObject instanceof Case){
			
			Case iCase = (Case)firstSelectedObject;
			
			ExecuteDeletion.deleteCase(iCase);		
		}
	}
}
