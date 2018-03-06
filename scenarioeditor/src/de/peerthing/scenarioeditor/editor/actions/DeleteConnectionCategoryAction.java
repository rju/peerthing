package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.scenarioeditor.model.IConnectionCategory;
import de.peerthing.scenarioeditor.model.impl.ExecuteDeletion;

/**
 * A class that allows to delete a connection from a scenario.
 * @author Hendrik Angenendt, Patrik
 *
 */
public class DeleteConnectionCategoryAction extends AbstractTreeAction {

    /**
     * This method deletes an existing connection from a scenario. Called automatically if according menu point is 
     * selected in pop-up menu
     */
    public void run(IAction action) {
        if (firstSelectedObject != null) {
            IConnectionCategory connection = (IConnectionCategory) firstSelectedObject;
            
            ExecuteDeletion.deleteConnectionCategory(connection);
                        
        }
    }
}
