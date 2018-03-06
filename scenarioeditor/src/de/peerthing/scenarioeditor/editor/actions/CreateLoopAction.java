package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.scenarioeditor.model.ICommandContainer;
import de.peerthing.scenarioeditor.model.ILoop;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.ScenarioFactory;
import de.peerthing.scenarioeditor.model.impl.ExecuteAddition;

/**
 * A class that allows to add  a new loop to an existing behaviour, if according menu point 
 * is selected in pop-up menu 
 * @author Hendrik Angenendt, Patrik
 * 
 */
public class CreateLoopAction extends AbstractTreeAction {

    /**
     * This method adds a new loop to an existing behaviour. Called automatically if according menu point is 
     * selected in pop-up menu
     */
    public void run(IAction action) {

        if (firstSelectedObject instanceof ICommandContainer) {
            ICommandContainer container = (ICommandContainer) firstSelectedObject;
            IScenario scenario = container.getScenario();
            ILoop loop = ScenarioFactory.createLoop(scenario);
            ExecuteAddition.addScenarioObjekt(container, loop);
            setDirty(scenario);
        }

    }

}
