package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.scenarioeditor.model.IScenarioCondition;
import de.peerthing.scenarioeditor.model.impl.ExecuteAddition;

/**
 * A class that allows to add a new case to an existing condition.
 * @author Hendrik Angenendt, Patrik
 *
 */
public class CreateCaseAction extends AbstractTreeAction {

    
    /**
     * This method adds a new case to an existing condition. Called automatically if according menu point is 
     * selected in pop-up menu
     */
    public void run(IAction action) {
        
    	IScenarioCondition scenarioCondition = (IScenarioCondition) firstSelectedObject;    	
    	
    	ExecuteAddition.addCase(scenarioCondition);
    	
    	/*IScenario scenario = scenarioCondition.getScenario();
        Case case1 = (Case)ScenarioFactory.createCase(scenario);
        scenarioCondition.getCases().add(case1);
        case1.setScenarioCondition(scenarioCondition);
        
        ScenarioEditorPlugin.getDefault().getUndoList().add(
				new ScenarioUndo(scenarioCondition,
				case1, UndoOperationValues.addWasDone));
		
        
        getTree().refresh(scenarioCondition);
        setDirty(scenario);*/

    }

}
