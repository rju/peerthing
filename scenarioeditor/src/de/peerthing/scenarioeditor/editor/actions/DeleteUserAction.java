package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.scenarioeditor.model.IUserAction;
import de.peerthing.scenarioeditor.model.impl.ExecuteDeletion;
/**
 * A class that allows to delete a user action from a behaviour.
 * @author Hendrik Angenendt, Patrik
 *
 */
public class DeleteUserAction extends AbstractTreeAction{

    /**
     * This method deletes an existing action from a scenario. Called automatically if according menu point is 
     * selected in pop-up menu
     */
    public void run(IAction action) {
        if (firstSelectedObject != null) {
           IUserAction userAction = (IUserAction) firstSelectedObject;
           
           ExecuteDeletion.deleteCommand(userAction);
        }
        
    }

}
