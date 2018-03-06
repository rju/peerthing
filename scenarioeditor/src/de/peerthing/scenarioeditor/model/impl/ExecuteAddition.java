package de.peerthing.scenarioeditor.model.impl;

import de.peerthing.scenarioeditor.ScenarioEditorPlugin;
import de.peerthing.scenarioeditor.editor.ScenarioEditor;
import de.peerthing.scenarioeditor.model.ICommand;
import de.peerthing.scenarioeditor.model.ICommandContainer;
import de.peerthing.scenarioeditor.model.IConnectionCategory;
import de.peerthing.scenarioeditor.model.ILinkSpeed;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.IResourceCategory;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IScenarioCondition;
import de.peerthing.scenarioeditor.model.IUserBehaviour;
import de.peerthing.scenarioeditor.model.ScenarioFactory;
import de.peerthing.scenarioeditor.model.IConnectionCategory.DuplexOption;

/**
 * @author patriks
 * Often a command (listen, useraction, callbehaviour,... ) is added to 
 * a commandContainer (case, defaultcase, behaviour, loop). This class
 * was created so a static methed can always be called for that case.
 */
public class ExecuteAddition {
	
	/**
	 * Adds the command to the command container an add an entry to the undo
	 * list.
	 */
	public static void addScenarioObjekt(ICommandContainer container, ICommand command){
						
		container.getCommands().add(command);
		command.setCommandContainer(container);
		ScenarioEditorPlugin.getDefault().getUndoList().add(
		new ScenarioUndo(container,
		command, UndoOperationValues.addWasDone));
					
		//ScenarioEditorPlugin.getDefault().getEditor().showFormFor(command);		
		
		ScenarioEditorPlugin.getDefault().getNavigationTree()
				.refresh(container);
		
		ScenarioEditor scenarioEditor = ScenarioEditorPlugin.getDefault().getFiletypeRegistration().getEditor(
                container.getScenario());
		scenarioEditor.setDirty();
	}
	
	public static void addResourceCategory(IScenario scenario, IResourceCategory resource){
		scenario.getResourceCategories().add(resource);
		ScenarioEditorPlugin.getDefault().getUndoList().add(new ScenarioUndo(scenario, resource, UndoOperationValues.addWasDone));
		ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(scenario.getResourceCategories());
		
		ScenarioEditor scenarioEditor = ScenarioEditorPlugin.getDefault().getFiletypeRegistration().getEditor(
                scenario);
		scenarioEditor.setDirty();
	}
	
	/*public static void addConnectionCategory(IScenario scenario, IConnectionCategory connection){		
	}*/
	
	public static void addCase(IScenarioCondition scenarioCondition){
		
		ScenarioEditor scenarioEditor = ScenarioEditorPlugin.getDefault().getFiletypeRegistration().getEditor(
                scenarioCondition.getScenario());
		
		IScenario scenario = scenarioCondition.getScenario();
        Case case1 = (Case)ScenarioFactory.createCase(scenario);
        scenarioCondition.getCases().add(case1);
        case1.setScenarioCondition(scenarioCondition);
        
        ScenarioEditorPlugin.getDefault().getUndoList().add(
				new ScenarioUndo(scenarioCondition,
				case1, UndoOperationValues.addWasDone));
		
        ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(scenarioCondition);
                
        scenarioEditor.setDirty();
	}
	
	public static void addConnectionCategory(IConnectionCategory connectionCategory){
		
		IScenario scenario = connectionCategory.getScenario();
		ILinkSpeed initCon;		
		initCon = ScenarioFactory.createLinkSpeed(scenario);		
		initCon.setDelay(0);
		initCon.setSpeed(0);
		
		ILinkSpeed initCon2;		
		initCon2 = ScenarioFactory.createLinkSpeed(scenario);		
		initCon2.setDelay(0);
		initCon2.setSpeed(0);
		
		connectionCategory.setDuplex(DuplexOption.full);
		connectionCategory.setDownlinkSpeed(initCon);
		connectionCategory.setUplinkSpeed(initCon2);
		
		scenario.getConnectionCategories().add(connectionCategory);
		ScenarioEditorPlugin.getDefault().getUndoList().add(new ScenarioUndo(scenario, connectionCategory, UndoOperationValues.addWasDone));
		
		ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(scenario.getConnectionCategories());
		
		ScenarioEditor scenarioEditor = ScenarioEditorPlugin.getDefault().getFiletypeRegistration().getEditor(
                scenario);
		scenarioEditor.setDirty();
			
	}
	
	public static void addNodeCategory(INodeCategory newNode){
		
		IScenario scenario = newNode.getScenario();
		
		newNode.getBehaviours().add(ScenarioFactory.createBehaviour(newNode));
        newNode.setPrimaryBehaviour(newNode.getBehaviours().get(0));

        ScenarioEditorPlugin.getDefault().getUndoList().add(
                new ScenarioUndo(scenario, newNode,
                        UndoOperationValues.addWasDone));
        scenario.getNodeCategories().add(newNode);
        
        ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(scenario.getNodeCategories());
		
		ScenarioEditor scenarioEditor = ScenarioEditorPlugin.getDefault().getFiletypeRegistration().getEditor(
                scenario);
		scenarioEditor.setDirty();

	}
	
	public static void addBehaviour(IUserBehaviour behaviour, INodeCategory nodeCategory){
		
		
		ScenarioEditorPlugin.getDefault().getUndoList().add(
				new ScenarioUndo
				(nodeCategory, behaviour, UndoOperationValues.addWasDone));
						
		nodeCategory.getBehaviours().add(behaviour);
		
        ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(nodeCategory.getBehaviours());
        
        ScenarioEditor scenarioEditor = ScenarioEditorPlugin.getDefault().getFiletypeRegistration().getEditor(
                behaviour.getScenario());
		scenarioEditor.setDirty();

	}
	
}
