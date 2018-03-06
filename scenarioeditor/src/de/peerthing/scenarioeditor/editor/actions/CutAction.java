package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import de.peerthing.scenarioeditor.ScenarioEditorPlugin;
import de.peerthing.scenarioeditor.model.ICommand;
import de.peerthing.scenarioeditor.model.ICommandContainer;
import de.peerthing.scenarioeditor.model.IConnectionCategory;
import de.peerthing.scenarioeditor.model.IListWithParent;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.INodeConnection;
import de.peerthing.scenarioeditor.model.IResourceCategory;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.IUserBehaviour;
import de.peerthing.scenarioeditor.model.impl.NodeConnection;
import de.peerthing.scenarioeditor.model.impl.ProvidesPositionOfCommand;
import de.peerthing.scenarioeditor.model.impl.ScenarioUndo;
import de.peerthing.scenarioeditor.model.impl.UndoOperationValues;

/**
 * A class that allows to cut an object, if according menu point 
 * is selected.
 * @author Patrik
 */

public class CutAction extends AbstractTreeAction{
    /**
     * This method allows to cut an object. Called automatically if according menu point is 
     * selected in pop-up menu
     */
	public void run(IAction action) {
        if (
        firstSelectedObject instanceof IScenarioObject &&
        !(firstSelectedObject instanceof IListWithParent)
        ){        	
        	ScenarioEditorPlugin.getDefault().setScenarioObject(
        			(IScenarioObject)firstSelectedObject, false);
        	
        	
        	IScenarioObject scenarioObject = (IScenarioObject)firstSelectedObject;
        	setDirty(scenarioObject.getScenario());
        	
        	if (firstSelectedObject instanceof INodeCategory){
        		INodeCategory nodeC = (INodeCategory) firstSelectedObject;
        		IScenario scen = nodeC.getScenario();
        		
        		INodeConnection savesPosition = 
					new NodeConnection(ProvidesPositionOfCommand.providesPosition(nodeC));
				ScenarioEditorPlugin.getDefault().getUndoList().add(
						new ScenarioUndo
						(nodeC, savesPosition, UndoOperationValues.deleteWasDone));
        		
        		scen.getNodeCategories().remove(nodeC);        	
        		getTree().refresh(scen.getNodeCategories());
        	}
        	if (firstSelectedObject instanceof IConnectionCategory){
        		IConnectionCategory connectionC = (IConnectionCategory) firstSelectedObject;
        		IScenario scen = connectionC.getScenario();
        		
        		INodeConnection savesPosition = 
					new NodeConnection(ProvidesPositionOfCommand.providesPosition(connectionC));
				ScenarioEditorPlugin.getDefault().getUndoList().add(
						new ScenarioUndo
						(connectionC, savesPosition, UndoOperationValues.deleteWasDone));
        		
        		scen.getConnectionCategories().remove(connectionC);        	
        		getTree().refresh(scen.getConnectionCategories());
        	}
        	if (firstSelectedObject instanceof IResourceCategory){
        		IResourceCategory resourceC = (IResourceCategory) firstSelectedObject;
        		IScenario scen = resourceC.getScenario();
        		
        		INodeConnection savesPosition = 
					new NodeConnection(ProvidesPositionOfCommand.providesPosition(resourceC));
				ScenarioEditorPlugin.getDefault().getUndoList().add(
						new ScenarioUndo
						(resourceC, savesPosition, UndoOperationValues.deleteWasDone));
        		
        		scen.getResourceCategories().remove(resourceC);        	
        		getTree().refresh(scen.getResourceCategories());
        	}
        	if (firstSelectedObject instanceof ICommand){
        		ICommand command = (ICommand) firstSelectedObject;
        		ICommandContainer container = command.getCommandContainer();
        		
        		INodeConnection savesPosition = 
					new NodeConnection(ProvidesPositionOfCommand.providesPosition(command));
				ScenarioEditorPlugin.getDefault().getUndoList().add(
						new ScenarioUndo
						(command, savesPosition, UndoOperationValues.deleteWasDone));
        		
        		container.getCommands().remove(command);        		
        		getTree().refresh(container);        		
        	}
        	if (firstSelectedObject instanceof IUserBehaviour){
        		IUserBehaviour behaviour = (IUserBehaviour) firstSelectedObject;
        		INodeCategory nodeC = behaviour.getNodeCategory();
        		
        		if (nodeC.getBehaviours().size()==1){
    				while(true){
    					MessageBox cancelBox = new MessageBox(ScenarioEditorPlugin.getDefault().getEditor().
    							getForm(nodeC).getMainForm().getShell(), SWT.OK
    							|SWT.ICON_WARNING );
    					cancelBox.setText("Cutting not possible");
    					cancelBox.setMessage("Very node needs a primary " +
    							"behaviour. You can't cut the last " +
    							"behaviour of a node. If you want to, you can " +
    							"copy it or change it.");
    					if (cancelBox.open()== SWT.OK || cancelBox.open() == SWT.ABORT){
    						return;						
    					}
    				}
    			}
                
                if (nodeC.getPrimaryBehaviour().equals(behaviour)){
                	MessageBox cancelBox = new MessageBox(ScenarioEditorPlugin.getDefault().getEditor().
    						getForm(nodeC).getMainForm().getShell(), SWT.OK
    						|SWT.ICON_WARNING |SWT.CANCEL);
                	cancelBox.setText("Warning");
    				cancelBox.setMessage("This behaviour is picked as primary " +
    						"behaviour. If you cut it, another primary " +
    						"will be picked automaticly. Do you wish to " +
    						"proceed?");
                	while(true){					
    					if (cancelBox.open()== SWT.OK){
    						if (nodeC.getBehaviours().get(0).equals(behaviour)){
    							nodeC.setPrimaryBehaviour(nodeC.getBehaviours().get(1));
							} else{
								nodeC.setPrimaryBehaviour(nodeC.getBehaviours().get(0));
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
        		
        		nodeC.getBehaviours().remove(behaviour);        	
        		getTree().refresh(nodeC.getBehaviours());
        	}
        }
    }
}
