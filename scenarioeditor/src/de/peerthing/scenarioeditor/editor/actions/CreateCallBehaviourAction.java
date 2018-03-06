package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.scenarioeditor.model.ICallUserBehaviour;
import de.peerthing.scenarioeditor.model.ICommandContainer;
import de.peerthing.scenarioeditor.model.IUserBehaviour;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.ScenarioFactory;
import de.peerthing.scenarioeditor.model.impl.ExecuteAddition;
import de.peerthing.scenarioeditor.model.impl.ProvideUserBehaviour;

/**
 * A class that allows to add a new callbehaviour to an existing behaviour, if according menu point 
 * is selected in pop-up menu
 * @author Hendrik Angenendt
 * 
 */
public class CreateCallBehaviourAction extends AbstractTreeAction {

    /**
     * This method adds a new callbehaviour to a scenario. Called automatically if according menu point is 
     * selected in pop-up menu
     */
    public void run(IAction action) {

        if (firstSelectedObject instanceof ICommandContainer) {
            ICommandContainer container = (ICommandContainer) firstSelectedObject;
            IScenario scenario = container.getScenario();
            IUserBehaviour behaviourToCall = ProvideUserBehaviour
                    .provideBehaviour(container);
            ICallUserBehaviour newCallBehaviour = ScenarioFactory
                    .createCallBehaviour(scenario, behaviourToCall);
            ExecuteAddition.addScenarioObjekt(container, newCallBehaviour);
            setDirty(scenario);
        }

    }

}
