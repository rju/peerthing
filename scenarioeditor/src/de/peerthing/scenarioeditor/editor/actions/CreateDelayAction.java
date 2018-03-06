package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.scenarioeditor.model.ICommandContainer;
import de.peerthing.scenarioeditor.model.IDelay;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.ScenarioFactory;
import de.peerthing.scenarioeditor.model.impl.ExecuteAddition;

/**
 * A class that allows to add a new delay to an existing behaviour, if according menu point 
 * is selected in pop-up menu
 * @author Hendrik Angenendt, Patrik
 * 
 */
public class CreateDelayAction extends AbstractTreeAction {

    /**
     * This method adds a new delay to an existing behaviour . Called automatically if according menu point is 
     * selected in pop-up menu
     */
    public void run(IAction action) {

        if (firstSelectedObject instanceof ICommandContainer) {
            ICommandContainer container = (ICommandContainer) firstSelectedObject;
            IScenario scenario = container.getScenario();
            IDelay delay = (IDelay) ScenarioFactory.createDelay(scenario);
            ExecuteAddition.addScenarioObjekt(container, delay);
            setDirty(scenario);
        }

    }

}
