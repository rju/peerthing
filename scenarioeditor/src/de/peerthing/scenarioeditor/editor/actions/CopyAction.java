package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.scenarioeditor.ScenarioEditorPlugin;
import de.peerthing.scenarioeditor.model.IListWithParent;
import de.peerthing.scenarioeditor.model.IScenarioObject;

/**
 * A class, that allows to copy objects out of the tree.
 * @author Patrik
 */

public class CopyAction extends AbstractTreeAction {

    /**
     * This method allows to copy objects. Called automatically if according menu point is 
     * selected.
     */
    public void run(IAction action) {

        if (firstSelectedObject instanceof IScenarioObject
                && !(firstSelectedObject instanceof IListWithParent)) {
            ScenarioEditorPlugin.getDefault().setScenarioObject(
                    (IScenarioObject) firstSelectedObject, true);

        }
    }
}