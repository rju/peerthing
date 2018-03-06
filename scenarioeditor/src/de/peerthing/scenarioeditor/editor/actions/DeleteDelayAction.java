package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.scenarioeditor.model.IDelay;
import de.peerthing.scenarioeditor.model.impl.ExecuteDeletion;
/**
 * A class that allows to delete a delay from a behaviour.
 * @author Hendrik Angenendt
 *
 */
public class DeleteDelayAction extends AbstractTreeAction {

    
    /**
     * This method deletes an existing delay from a scenario. Called automatically if according menu point is 
     * selected in pop-up menu
     */
    public void run(IAction action) {
        if (firstSelectedObject != null) {
        	IDelay delay = (IDelay) firstSelectedObject;
        	
        	ExecuteDeletion.deleteCommand(delay);
        }
    }

}
