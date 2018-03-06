package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.scenarioeditor.model.ICommandContainer;
import de.peerthing.scenarioeditor.model.IListen;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.ScenarioFactory;
import de.peerthing.scenarioeditor.model.impl.ExecuteAddition;

/**
 * A class that allows to add a new listen action to a behaviour
 * @author lethe, Patrik
 * 
 */
public class CreateListenAction extends AbstractTreeAction {
    /**
     * This method adds a new listen action to a behaviour. Called automatically if according menu point is 
     * selected in pop-up menu
     */
    public void run(IAction action) {

        if (firstSelectedObject instanceof ICommandContainer) {
            ICommandContainer container = (ICommandContainer) firstSelectedObject;
            IScenario scenario = container.getScenario();
            IListen listen = ScenarioFactory.createListen(scenario);
            ExecuteAddition.addScenarioObjekt(container, listen);
            setDirty(scenario);
        }

    }

}
