/**
 * 
 */
package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.scenarioeditor.model.IListWithParent;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.IUserBehaviour;
import de.peerthing.scenarioeditor.model.ScenarioFactory;
import de.peerthing.scenarioeditor.model.impl.ExecuteAddition;

/**
 * A class that allows to add a new node behaviour to a scenario, if according menu point is selected in
 * pop-up menu.
 * 
 * @author Hendrik Angenendt
 * 
 */
public class AddNodeBehaviourAction extends AbstractTreeAction {

    /**
     * This method avoids to use double existing node behaviour names
     * @param node
     * @return
     */
    public String findDistinctiveBehaviourName(INodeCategory node) {

        int numberBehaviours = 1;
        numberBehaviours += node.getBehaviours().size();
        String proposedName = "";

        for (int i = numberBehaviours + 1; i > 0; i--) {
            proposedName = "newBehaviour_" + numberBehaviours;

            for (IUserBehaviour behaviour : node.getBehaviours()) {
                if (behaviour.getName().equals(proposedName)) {
                    numberBehaviours++;
                }
            }
        }

        return proposedName;
    }
    /**
     * This method adds a new node behaviour to a scenario. Called automatically if according menu point is 
     * selected in pop-up menu
     */
    public void run(IAction action) {
        IListWithParent nodeCategory = (IListWithParent) firstSelectedObject;
        INodeCategory nodeTemp = (INodeCategory) nodeCategory.getParent();
        IUserBehaviour behaviour = ScenarioFactory
                .createBehaviour((INodeCategory) nodeCategory.getParent());
        behaviour.setName(findDistinctiveBehaviourName(nodeTemp));

        ExecuteAddition.addBehaviour(behaviour, nodeTemp);
        
    }

}
