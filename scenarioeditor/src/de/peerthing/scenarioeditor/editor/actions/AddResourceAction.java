package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.scenarioeditor.model.IListWithParent;
import de.peerthing.scenarioeditor.model.IResourceCategory;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.ScenarioFactory;
import de.peerthing.scenarioeditor.model.impl.ExecuteAddition;

/**
 * A class that allows to add a new resource to a scenario, if according menu point 
 * is selected in pop-up menu.
 * @author lethe
 * 
 */
public class AddResourceAction extends AbstractTreeAction {

    /**
     * This method avoids to use double existing node names
     * @param scenario
     * @return
     */
    public String findDistinctiveResourceName(IScenario scenario) {

        int numberResources = 1;
        numberResources += scenario.getResourceCategories().size();
        String proposedName = "";

        for (int i = numberResources + 1; i > 0; i--) {
            proposedName = "newResource_" + numberResources;

            for (IResourceCategory resource : scenario.getResourceCategories()) {
                if (resource.getName().equals(proposedName)) {
                    numberResources++;
                }
            }
        }

        return proposedName;
    }
    
    /**
     * This method adds a new ressource to a scenario. Called automatically if according menu point is 
     * selected in pop-up menu
     */
    public void run(IAction action) {
        IListWithParent resource = (IListWithParent) firstSelectedObject;
        IScenario scenario = resource.getScenario();

        IResourceCategory newResource = ScenarioFactory
                .createResourceCategory(scenario);
        newResource.setName(findDistinctiveResourceName(scenario));

        ExecuteAddition.addResourceCategory(scenario, newResource);
        /*scenario.getResourceCategories().add(newResource);
        ScenarioEditorPlugin.getDefault().getUndoList().add(
                new ScenarioUndo(scenario, newResource,
                        UndoOperationValues.addWasDone));*/        

        getTree().refresh(resource);
        setDirty(scenario);
    }

}
