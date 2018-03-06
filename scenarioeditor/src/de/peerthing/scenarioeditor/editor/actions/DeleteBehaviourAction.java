package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;

import de.peerthing.scenarioeditor.model.IUserBehaviour;
import de.peerthing.scenarioeditor.model.impl.ExecuteDeletion;
/**
 * A class that allows to delete an exiting behaviour from a scenario.
 * @author Hendrik Angenendt, patrik
 *
 */
public class DeleteBehaviourAction extends AbstractTreeAction {

    /**
     * This method deletes an existing behaviour from a scenario. Called automatically if according menu point is 
     * selected in pop-up menu
     */
    public void run(IAction action) {
        if (firstSelectedObject != null) {
            IUserBehaviour behaviour = (IUserBehaviour) firstSelectedObject;
            
            ExecuteDeletion.deleteBehaviour(behaviour);
            
            /*INodeCategory nodeCategory = behaviour.getNodeCategory();
                                                
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
            getTree().refresh(nodeCategory.getBehaviours());
            objectDeleted(behaviour);
            ScenarioEditorPlugin.getDefault().getNavigationTree().select(nodeCategory.getBehaviours());*/
        }

    }

}
