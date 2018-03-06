package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.scenarioeditor.model.IScenarioCondition;
import de.peerthing.scenarioeditor.model.impl.ExecuteDeletion;
/**
 * A class that allows to delete a condition from a behaviour.
 * @author Hendrik Angenendt, Patrik
 *
 */
public class DeleteScenarioConditionAction extends AbstractTreeAction {

    /**
     * This method deletes an existing condition from a scenario. Called automatically if according menu point is 
     * selected in pop-up menu
     */
    public void run(IAction action) {
        if (firstSelectedObject != null) {
            IScenarioCondition scenarioCondition = (IScenarioCondition) firstSelectedObject;
            
            ExecuteDeletion.deleteCommand(scenarioCondition);
        }
    }

}
