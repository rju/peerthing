
package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.scenarioeditor.model.IListen;
import de.peerthing.scenarioeditor.model.impl.ExecuteDeletion;

/**
 * @author Hendrik Angenendt
 *
 */
public class DeleteListenAction extends AbstractTreeAction {
    /**
     * This method deletes an existing action from a scenario. Called automatically if according menu point is 
     * selected in pop-up menu
     */
    public void run(IAction action) {
        if (firstSelectedObject != null) {
           IListen listen = (IListen) firstSelectedObject;
           
           ExecuteDeletion.deleteCommand(listen);
        }
        
    }

}


