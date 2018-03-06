package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.scenarioeditor.model.ILoop;
import de.peerthing.scenarioeditor.model.impl.ExecuteDeletion;
/**
 * A class that allows to delete an existing loop from a behaviour
 * @author Hendrik Angenendt
 *
 */
public class DeleteLoopAction extends AbstractTreeAction {

    
    /**
     * This method deletes an existing loop from a behaviour. Called automatically if according menu point is 
     * selected in pop-up menu
     */
    public void run(IAction action) {
        if (firstSelectedObject != null) {
          ILoop loop = (ILoop) firstSelectedObject;
          
          ExecuteDeletion.deleteCommand(loop);
        }
    }

}
