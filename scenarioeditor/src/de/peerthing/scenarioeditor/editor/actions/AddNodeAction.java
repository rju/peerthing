package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.scenarioeditor.model.IListWithParent;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.ScenarioFactory;
import de.peerthing.scenarioeditor.model.impl.ExecuteAddition;

/**
 * A class that allows to add a new node to scenario, if according menu point is selected in
 * pop-up menu.
 * @author Hendrik Angenendt
 * 
 */

public class AddNodeAction extends AbstractTreeAction {

    /**
     * This method avoids to use double existing node names
     * @param scenario
     * @return
     */
    public String findDistinctiveNodeName(IScenario scenario) {

        int numberNodes = 1;
        numberNodes += scenario.getNodeCategories().size();
        System.out.println(numberNodes);
        String proposedName = "";

        for (int i = numberNodes + 1; i > 0; i--) {
            proposedName = "newNode_" + numberNodes;

            for (INodeCategory node : scenario.getNodeCategories()) {
                if (node.getName().equals(proposedName)) {
                    numberNodes++;
                }
            }
        }

        return proposedName;
    }

    /**
     * This method adds a new node to a scenario. Called automatically if according menu point is 
     * selected in pop-up menu
     */
    public void run(IAction action) {
        IListWithParent nodeCategory = (IListWithParent) firstSelectedObject;
        IScenario scenario = nodeCategory.getScenario();
        INodeCategory newNode = ScenarioFactory.createNodeCategory(scenario);                        
        newNode.setName(findDistinctiveNodeName(scenario));
                
        ExecuteAddition.addNodeCategory(newNode);
        
    }
}
