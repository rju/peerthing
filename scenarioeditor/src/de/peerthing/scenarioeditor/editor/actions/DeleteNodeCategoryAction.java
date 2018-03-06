package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.impl.ExecuteDeletion;

/**
 * A class that allows to delete an existing node from a scenario.
 * @author Hendrik Angenendt, Patrik
 *
 */
public class DeleteNodeCategoryAction extends AbstractTreeAction {

    
    /**
     * This method deletes an existing node from a scenario. Called automatically if according menu point is 
     * selected in pop-up menu
     */
	public void run(IAction action) {
		if (firstSelectedObject != null) {
			INodeCategory node = (INodeCategory) firstSelectedObject;
            
			ExecuteDeletion.deleteNodeCategory(node);
		}
	}

}
