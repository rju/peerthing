package de.peerthing.scenarioeditor.model.impl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import de.peerthing.scenarioeditor.ScenarioEditorPlugin;
import de.peerthing.scenarioeditor.editor.ScenarioEditor;
import de.peerthing.scenarioeditor.model.ICase;
import de.peerthing.scenarioeditor.model.ICommand;
import de.peerthing.scenarioeditor.model.ICommandContainer;
import de.peerthing.scenarioeditor.model.IConnectionCategory;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.INodeConnection;
import de.peerthing.scenarioeditor.model.IResourceCategory;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IUserBehaviour;

public class ExecuteDeletion {
			
	public static void deleteNodeCategory(INodeCategory nodeCategory){
		IScenario scenario = nodeCategory.getScenario();
		INodeConnection savesPosition = 
			new NodeConnection(ProvidesPositionOfCommand.providesPosition(nodeCategory));
		ScenarioEditorPlugin.getDefault().getUndoList().add(
				new ScenarioUndo
				(nodeCategory, savesPosition, UndoOperationValues.deleteWasDone));
        scenario.getNodeCategories().remove(nodeCategory);
        ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(scenario.getNodeCategories());        
		
        ScenarioEditorPlugin.getDefault().getFiletypeRegistration().getEditor(
	               scenario).objectDeleted(nodeCategory);
		ScenarioEditorPlugin.getDefault().getFiletypeRegistration().getEditor(
	               scenario).setDirty();
		ScenarioEditorPlugin.getDefault().getNavigationTree().select(scenario.getNodeCategories());
	}
	
	public static void deleteResourceCategory(IResourceCategory resourceCategory){
		IScenario scenario = resourceCategory.getScenario();
		ScenarioEditor scenarioEditor = ScenarioEditorPlugin.getDefault().getFiletypeRegistration().getEditor(
                resourceCategory.getScenario());
		for (int i = 0; i< scenario.getResourceCategories().size(); i++){
			if (resourceCategory.equals(scenario.getResourceCategories().get(i))){
				
				INodeConnection savesPosition = 
					new NodeConnection(ProvidesPositionOfCommand.providesPosition(resourceCategory));
			    ScenarioEditorPlugin.getDefault().getUndoList().add(
					new ScenarioUndo
					(resourceCategory, savesPosition, UndoOperationValues.deleteWasDone));				    
				scenario.getResourceCategories().remove(i);															       
		        
				scenarioEditor.objectDeleted(resourceCategory);
				ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(scenario.getResourceCategories());
				scenarioEditor.setDirty();
				ScenarioEditorPlugin.getDefault().getNavigationTree().select(scenario.getResourceCategories());
			}
		}
		for (int i = 0; i < scenario.getNodeCategories().size(); i++){
			INodeCategory nc1 = scenario.getNodeCategories().get(i);
			for (int j = 0; j < nc1.getResources().size(); j++){
				if (resourceCategory.equals(nc1.getResources().get(j).getCategory())){
					nc1.getResources().remove(j);
					ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(nc1.getResources());
					scenarioEditor.setDirty();
				}
			}
		}
	}
	
	public static void deleteConnectionCategory(IConnectionCategory connectionCategory){
		IScenario scenario = connectionCategory.getScenario();
		ScenarioEditor scenarioEditor = ScenarioEditorPlugin.getDefault().getFiletypeRegistration().getEditor(
                connectionCategory.getScenario());
		for (int i = 0; i < scenario.getConnectionCategories().size(); i++) {
            if (connectionCategory.equals(scenario.getConnectionCategories().get(i))) {
            	INodeConnection savesPosition = 
					new NodeConnection(ProvidesPositionOfCommand.providesPosition(connectionCategory));
            	ScenarioEditorPlugin.getDefault().getUndoList().add(
						new ScenarioUndo
						(connectionCategory, savesPosition, UndoOperationValues.deleteWasDone));
            	connectionCategory.getScenario().getConnectionCategories().remove(i);
            	ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(connectionCategory.getScenario().getConnectionCategories());
            	scenarioEditor.objectDeleted(connectionCategory);
                scenarioEditor.setDirty();
                ScenarioEditorPlugin.getDefault().getNavigationTree().select(connectionCategory.getScenario().getConnectionCategories());
            }
        }
        for (int i = 0; i < connectionCategory.getScenario().getNodeCategories().size(); i++) {
            INodeCategory nconnectionCategory = scenario.getNodeCategories().get(i);
            for (int j = 0; j < nconnectionCategory.getConnections().size(); j++) {
                if (connectionCategory.equals(nconnectionCategory.getConnections().get(j).getCategory())) {
                    nconnectionCategory.getConnections().remove(j);
                    ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(nconnectionCategory.getConnections());
                    scenarioEditor.setDirty();
                }
            }
        }
        ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(connectionCategory.getScenario().getConnectionCategories());
	}
	
	
	public static void deleteCommand(ICommand command){
		ICommandContainer c1 = command.getCommandContainer();
		
		INodeConnection savesPosition = 
			new NodeConnection(ProvidesPositionOfCommand.providesPosition(command));
		ScenarioEditorPlugin.getDefault().getUndoList().add(
				new ScenarioUndo
				(command, savesPosition, UndoOperationValues.deleteWasDone));
		c1.getCommands().remove(command);
		ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(c1);
           
		ScenarioEditorPlugin.getDefault().getFiletypeRegistration().getEditor(
	               command.getScenario()).objectDeleted(command);
		ScenarioEditorPlugin.getDefault().getFiletypeRegistration().getEditor(
	               command.getScenario()).setDirty();			
           //scenarioEditor.objectDeleted(listen);
           //scenarioEditor.setDirty();
        ScenarioEditorPlugin.getDefault().getNavigationTree().select(c1);
	}
	
	public static void deleteCase(ICase iCase){
		
		IScenario scenario = iCase.getScenario();
		INodeConnection savesPosition = 
			new NodeConnection(ProvidesPositionOfCommand.providesPosition((Case)iCase));
		
		ScenarioEditorPlugin.getDefault().getUndoList().add(
				new ScenarioUndo
				(iCase, savesPosition, UndoOperationValues.deleteWasDone));
		
		iCase.getScenarioCondition().getCases().remove(iCase);
		
		ScenarioEditorPlugin.getDefault().getNavigationTree()
				.refresh(iCase.getScenarioCondition());
		ScenarioEditorPlugin.getDefault().getFiletypeRegistration().getEditor(
	               scenario).setDirty();
		ScenarioEditorPlugin.getDefault().getNavigationTree().select(iCase.getScenarioCondition());
	}
	
	public static void deleteBehaviour(IUserBehaviour behaviour){
		INodeCategory nodeCategory = behaviour.getNodeCategory();
        
		ScenarioEditor scenarioEditor = ScenarioEditorPlugin.getDefault().getFiletypeRegistration().getEditor(
                behaviour.getScenario());

		
        if (nodeCategory.getBehaviours().size()==1){
			while(true){
				MessageBox cancelBox = new MessageBox(ScenarioEditorPlugin.getDefault().getEditor().
						getForm(nodeCategory).getMainForm().getShell(), SWT.OK
						|SWT.ICON_WARNING );
				cancelBox.setText("Deletion not possible");
				cancelBox.setMessage("Very node needs a primary " +
						"behaviour. You can't delete the last " +
						"behaviour of a node. If you want to, change " +
						"the behaviour.");
				if (cancelBox.open()== SWT.OK || cancelBox.open() == SWT.ABORT){
					return;						
				}
			}
		}
        
        if (nodeCategory.getPrimaryBehaviour().equals(behaviour)){
        	MessageBox cancelBox = new MessageBox(ScenarioEditorPlugin.getDefault().getEditor().
					getForm(nodeCategory).getMainForm().getShell(), SWT.OK
					|SWT.ICON_WARNING |SWT.CANCEL);
        	cancelBox.setText("Deletion warning");
			cancelBox.setMessage("This behaviour is picked as primary " +
					"behaviour. If you delete it, another primary " +
					"will be picked automaticly. Do you wish to " +
					"proceed?");
        	while(true){					
				if (cancelBox.open()== SWT.OK){
					if (nodeCategory.getBehaviours().get(0).equals(behaviour)){
						nodeCategory.setPrimaryBehaviour(nodeCategory.getBehaviours().get(1));
					} else{
						nodeCategory.setPrimaryBehaviour(nodeCategory.getBehaviours().get(0));
					}						
					break;
				} else {
					return;
				}
			}
		}
        
        INodeConnection savesPosition = 
			new NodeConnection(ProvidesPositionOfCommand.providesPosition(behaviour));
		ScenarioEditorPlugin.getDefault().getUndoList().add(
				new ScenarioUndo
				(behaviour, savesPosition, UndoOperationValues.deleteWasDone));
		
        nodeCategory.getBehaviours().remove(behaviour);
        
                
        ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(nodeCategory.getBehaviours());
    	scenarioEditor.objectDeleted(behaviour);
        scenarioEditor.setDirty();
        
        /*getTree().refresh(nodeCategory.getBehaviours());
        objectDeleted(behaviour);*/
        ScenarioEditorPlugin.getDefault().getNavigationTree().select(nodeCategory.getBehaviours());
    
	}
}
