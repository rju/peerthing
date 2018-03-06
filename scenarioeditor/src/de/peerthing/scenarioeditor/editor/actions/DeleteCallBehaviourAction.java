package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.scenarioeditor.model.ICallUserBehaviour;
import de.peerthing.scenarioeditor.model.impl.ExecuteDeletion;
/**
 * A class that allows to delete a callbehaviour from a behaviour.
 * @author Hendrik Angenendt
 *
 */

public class DeleteCallBehaviourAction extends AbstractTreeAction {

    /**
     * This method deletes an existing callbehaviour from a scenario. Called automatically if according menu point is 
     * selected in pop-up menu
     */
    public void run(IAction action) {
        if (firstSelectedObject != null) {
        	ICallUserBehaviour callBehaviour = (ICallUserBehaviour) firstSelectedObject;
        	
        	ExecuteDeletion.deleteCommand(callBehaviour);
        }
    }

}
