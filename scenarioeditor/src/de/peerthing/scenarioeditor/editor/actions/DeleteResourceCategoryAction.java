package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.scenarioeditor.model.IResourceCategory;
import de.peerthing.scenarioeditor.model.impl.ExecuteDeletion;

/**
 * A class that allows to delete an existing resource from a scenario.
 * @author Hendrik Angenendt, Patrik
 * 
 */
public class DeleteResourceCategoryAction extends AbstractTreeAction {

    /**
     * This method deletes an existing resource from a scenario. Called automatically if according menu point is 
     * selected in pop-up menu
     */
	public void run(IAction action) {

		if (firstSelectedObject != null) {
			IResourceCategory resource = (IResourceCategory) firstSelectedObject;
			ExecuteDeletion.deleteResourceCategory(resource);
			
		}
	}
}
