package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.scenarioeditor.model.IConnectionCategory;
import de.peerthing.scenarioeditor.model.IListWithParent;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.ScenarioFactory;
import de.peerthing.scenarioeditor.model.impl.ExecuteAddition;

/**
 * A class that allows to add a new connection to scenario, if according menu point 
 * is selected in pop-up menu.
 * 
 * @author Hendrik Angenendt
 * 
 */
public class AddConnectionAction extends AbstractTreeAction {

    /**
     * This method avoids to use double existing connection names
     * @param scenario
     * @return
     */
    public String findDistinctiveConnectionName(IScenario scenario) {

        int numberConnections = 1;
        numberConnections += scenario.getConnectionCategories().size();
        String proposedName = "";

        for (int i = numberConnections + 1; i > 0; i--) {
            proposedName = "newConnection_" + numberConnections;

            for (IConnectionCategory connection : scenario
                    .getConnectionCategories()) {
                if (connection.getName().equals(proposedName)) {
                    numberConnections++;
                }
            }
        }

        return proposedName;
    }

    /**
     * This method adds a new connection to scenario. Called automatically if according menu point is 
     * selected in pop-up menu.
     */
    public void run(IAction action) {
        IListWithParent connectionCategory = (IListWithParent) firstSelectedObject;
                        
        IScenario scenario = connectionCategory.getScenario();
        
        IConnectionCategory newConnection = ScenarioFactory
        .createConnectionCategory(scenario);
        
        newConnection.setName(findDistinctiveConnectionName(scenario));
        
        ExecuteAddition.addConnectionCategory(newConnection);        
    }

}
