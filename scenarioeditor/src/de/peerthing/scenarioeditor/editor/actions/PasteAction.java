package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import de.peerthing.scenarioeditor.ScenarioEditorPlugin;
import de.peerthing.scenarioeditor.model.ICallUserBehaviour;
import de.peerthing.scenarioeditor.model.ICase;
import de.peerthing.scenarioeditor.model.ICommand;
import de.peerthing.scenarioeditor.model.ICommandContainer;
import de.peerthing.scenarioeditor.model.IConnectionCategory;
import de.peerthing.scenarioeditor.model.IDelay;
import de.peerthing.scenarioeditor.model.IListWithParent;
import de.peerthing.scenarioeditor.model.IListen;
import de.peerthing.scenarioeditor.model.ILoop;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.IResourceCategory;
import de.peerthing.scenarioeditor.model.IScenarioCondition;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.IUserAction;
import de.peerthing.scenarioeditor.model.IUserBehaviour;
import de.peerthing.scenarioeditor.model.impl.CallUserBehaviour;
import de.peerthing.scenarioeditor.model.impl.ConnectionCategory;
import de.peerthing.scenarioeditor.model.impl.Delay;
import de.peerthing.scenarioeditor.model.impl.ExecuteAddition;
import de.peerthing.scenarioeditor.model.impl.Listen;
import de.peerthing.scenarioeditor.model.impl.Loop;
import de.peerthing.scenarioeditor.model.impl.NodeCategory;
import de.peerthing.scenarioeditor.model.impl.PasteCallBehaviourTest;
import de.peerthing.scenarioeditor.model.impl.ResourceCategory;
import de.peerthing.scenarioeditor.model.impl.ScenarioCondition;
import de.peerthing.scenarioeditor.model.impl.ScenarioUndo;
import de.peerthing.scenarioeditor.model.impl.UndoOperationValues;
import de.peerthing.scenarioeditor.model.impl.UserAction;
import de.peerthing.scenarioeditor.model.impl.UserBehaviour;

/**
 * This class allows to paste copied elements somewhere else.
 * @author Patrik
 */

public class PasteAction extends AbstractTreeAction {
	
	/**
     * The scenario object which was copied or cut will be loaded in
     * the variable
	 */
	IScenarioObject scenarioObject;
    
    /**
     * An action object
     */
	IAction action;
	
    /**
     * This method runs the justPaste method. Called automatically if according menu point is 
     * selected.
     */
	public void run(IAction action) {
        this.action = action;
		 scenarioObject = ScenarioEditorPlugin.getDefault().getScenarioObject();
		
		 justPaste();		
    }
	
    /**
     * This method allows to paste copied objects elsewhere in the tree
     *
     */
	public void justPaste(){
		setDirty(scenarioObject.getScenario());
		
		if (scenarioObject instanceof IUserBehaviour){ 
    		if(PasteCallBehaviourTest.containsCallBehaviour((IUserBehaviour)scenarioObject)){
    			while(true){
					MessageBox cancelBox = new MessageBox(ScenarioEditorPlugin.getDefault().getEditor().getForm((IScenarioObject)firstSelectedObject).getMainForm().getShell(), SWT.OK
							|SWT.ICON_WARNING);
					cancelBox.setText("Paste Warning");
					cancelBox.setMessage("You are pasting an element which contains a " +
							"\"Call Behaviour\" element. If the behaviour that is triggered, " +
							"doesn't exist, where you use the paste operation another " +
							"behaviour will be picked automaticly.");
					if (cancelBox.open()== SWT.OK || cancelBox.open() == SWT.ABORT){
						break;
					}
				}            			
    		}
    	}		
		
		if (firstSelectedObject instanceof ICommandContainer){        	
        	ICommandContainer commandContainer = (ICommandContainer) firstSelectedObject;
        	if (scenarioObject instanceof ICommand){
            	
        		ICommand com = (ICommand)scenarioObject;
        		com.setCommandContainer(commandContainer);
        		
        		if (scenarioObject instanceof ILoop){            		
            		ILoop original = (ILoop) scenarioObject;
            		ILoop copyOfLoop = new Loop(original, original.getScenario(), firstSelectedObject); 
            		ExecuteAddition.addScenarioObjekt(commandContainer, copyOfLoop);            		
            	}
            	if (scenarioObject instanceof IScenarioCondition){            		
            		IScenarioCondition original = (IScenarioCondition) scenarioObject;
            		IScenarioCondition copyOfContition = new ScenarioCondition(original, original.getScenario(), firstSelectedObject); 
            		ExecuteAddition.addScenarioObjekt(commandContainer, copyOfContition);            		
            	}
            	if (scenarioObject instanceof ICallUserBehaviour){            		
            		ICallUserBehaviour original = (ICallUserBehaviour) scenarioObject;
            		ICallUserBehaviour copyOfCallUserBehaviour = new CallUserBehaviour(original, original.getScenario(), firstSelectedObject); 
            		ExecuteAddition.addScenarioObjekt(commandContainer, copyOfCallUserBehaviour);
            	}
            	if (scenarioObject instanceof IDelay){            		
            		IDelay original = (IDelay) scenarioObject;
            		IDelay copyOfDelay = new Delay(original, original.getScenario(), firstSelectedObject); 
            		ExecuteAddition.addScenarioObjekt(commandContainer, copyOfDelay);
            	}
            	if (scenarioObject instanceof IListen){            		
            		IListen original = (IListen) scenarioObject;
            		IListen copyOfListen = new Listen(original, original.getScenario(), firstSelectedObject); 
            		ExecuteAddition.addScenarioObjekt(commandContainer, copyOfListen);
            	}
            	if (scenarioObject instanceof IUserAction){            		
            		IUserAction original = (IUserAction) scenarioObject;
            		IUserAction copyOfAction = new UserAction(original, original.getScenario(), firstSelectedObject); 
            		ExecuteAddition.addScenarioObjekt(commandContainer, copyOfAction);
            	}            	            	
            	if (scenarioObject instanceof ICommandContainer){ 
            		if(PasteCallBehaviourTest.containsCallBehaviour((ICommandContainer)scenarioObject)){
            			while(true){
        					MessageBox cancelBox = new MessageBox(ScenarioEditorPlugin.getDefault().getEditor().getForm((IScenarioObject)firstSelectedObject).getMainForm().getShell(), SWT.OK
        							|SWT.ICON_WARNING);
        					cancelBox.setText("Paste Warning");
        					cancelBox.setMessage("You are pasting an element which contains a " +
        							"\"Call Behaviour\" element. If the behaviour that is triggered, " +
        							"doesn't exist, where you use the paste operation another " +
        							"behaviour will be picked automaticly.");
        					if (cancelBox.open()== SWT.OK || cancelBox.open() == SWT.ABORT){
        						break;
        					}
        				}            			
            		}
            	}                                  	
        	}
        } else if (firstSelectedObject instanceof IListWithParent){
			IListWithParent parent = (IListWithParent) firstSelectedObject;
        	if (parent.getName().equals("Node Categories")){
        		if (scenarioObject instanceof INodeCategory){
        				        			        			
        			INodeCategory original = (INodeCategory)scenarioObject;
        			INodeCategory copyOfNodeCategory =
        				(INodeCategory) new NodeCategory(original, parent.getScenario());
        			
        			if (!original.getScenario().equals(parent.getScenario())){
	        			while(true){
	    					MessageBox cancelBox = new MessageBox(ScenarioEditorPlugin.getDefault().getEditor().getForm((IScenarioObject)firstSelectedObject).getMainForm().getShell(), SWT.OK
	    							|SWT.ICON_WARNING);
	    					cancelBox.setText("Paste Warning");
	    					cancelBox.setMessage("You are pasting a node category in another scenario " +
	    							"you copied it from. Note that the connections for the node and the " +
	    							"resources for the node have to be picked new, because the new scenario " +
	    							"might have other resource categories and connection categories");
	    					if (cancelBox.open()== SWT.OK || cancelBox.open() == SWT.ABORT){
	    						break;
	    					}
	    				}
        			}
        			
        			for (INodeCategory nodeCat : parent.getScenario().getNodeCategories()){        			
        				if (nodeCat.getName().equals(copyOfNodeCategory.getName())){
            				copyOfNodeCategory.setName(copyOfNodeCategory.getName()+
            				parent.getScenario().getNodeCategories().size());
        				}
        			}
        			
        			parent.getScenario().getNodeCategories().add(copyOfNodeCategory);
        			ScenarioEditorPlugin.getDefault().getUndoList().add(
        					new ScenarioUndo(parent.getScenario(), copyOfNodeCategory
        							, UndoOperationValues.addWasDone));
        			//parent.getScenario().getNodeCategories().add(original);
        			getTree().refresh(parent);
        		}
        	}
        
        	if (parent.getName().equals("Connections")){
        		if (scenarioObject instanceof IConnectionCategory){
        			IConnectionCategory original = (IConnectionCategory)scenarioObject;
        			IConnectionCategory copyOfConnectionCategory = 
        				(IConnectionCategory) new ConnectionCategory(original, parent.getScenario());
        			
        			for (IConnectionCategory conCat : parent.getScenario().getConnectionCategories()){        			
        				if (conCat.getName().equals(copyOfConnectionCategory.getName())){
            				copyOfConnectionCategory.setName(copyOfConnectionCategory.getName()+
            				parent.getScenario().getConnectionCategories().size());
        				}
        			}
        			
        			parent.getScenario().getConnectionCategories().add(copyOfConnectionCategory);
        			ScenarioEditorPlugin.getDefault().getUndoList().add(
        					new ScenarioUndo(parent.getScenario(), copyOfConnectionCategory
        							, UndoOperationValues.addWasDone));
        			getTree().refresh(parent);
        		}
        	}        
        	if (parent.getName().equals("Resources")){
        		if (scenarioObject instanceof IResourceCategory){
        			IResourceCategory original = (IResourceCategory) scenarioObject;
        			IResourceCategory copyOfResourceCategory = 
        				(IResourceCategory) new ResourceCategory(original, parent.getScenario());
        			
        			for (IResourceCategory resCat : parent.getScenario().getResourceCategories()){        			
        				if (resCat.getName().equals(copyOfResourceCategory.getName())){
            				copyOfResourceCategory.setName(copyOfResourceCategory.getName()+
            				parent.getScenario().getResourceCategories().size());
        				}
        			}
        			
        			parent.getScenario().getResourceCategories().add(copyOfResourceCategory);
        			ScenarioEditorPlugin.getDefault().getUndoList().add(
        					new ScenarioUndo(parent.getScenario(), copyOfResourceCategory
        							, UndoOperationValues.addWasDone));
        			getTree().refresh(parent);
        		}
        	}
        	if (parent.getName().equals("Node Behaviours")){        		
        		if (scenarioObject instanceof IUserBehaviour){        			
        			INodeCategory nodeC = (INodeCategory)parent.getParent();
        			IUserBehaviour original = (IUserBehaviour) scenarioObject;
        			IUserBehaviour copyOfBehaviour = 
        				(IUserBehaviour) new UserBehaviour(original, parent.getScenario(), nodeC);
        			
        			for (IUserBehaviour beh : nodeC.getBehaviours()){        			
        				if (beh.getName().equals(copyOfBehaviour.getName())){
            				copyOfBehaviour.setName(copyOfBehaviour.getName()+
            				nodeC.getBehaviours().size());
        				}
        			}
        			
        			nodeC.getBehaviours().add(copyOfBehaviour);
        			ScenarioEditorPlugin.getDefault().getUndoList().add(
        					new ScenarioUndo(nodeC, copyOfBehaviour
        							, UndoOperationValues.addWasDone));
        			getTree().refresh(parent);
        		}
        	}
        } 		
	}
	
    /**
     * Targeted method if selection is changed somewhere in editor 
     */
    @Override
	public void selectionChanged(IAction action, ISelection selection) {
		
		action.setEnabled(true);
		
		if (selection instanceof IStructuredSelection) {
			firstSelectedObject = ((IStructuredSelection) selection)
					.getFirstElement();			
			scenarioObject = ScenarioEditorPlugin.getDefault().getScenarioObject();			
			if (scenarioObject == null){
				action.setEnabled(false);
				return;
			}			
			if (
			firstSelectedObject instanceof ILoop ||
			firstSelectedObject instanceof ICase ||
			firstSelectedObject instanceof IUserBehaviour
			){
				if (!(scenarioObject instanceof ICommand)){
					action.setEnabled(false);
				}
			}
			if (firstSelectedObject instanceof IListWithParent){
				IListWithParent listWP = (IListWithParent)firstSelectedObject;
				if (listWP.getName().equals("Connections")){
					if (!(scenarioObject instanceof IConnectionCategory)){
						action.setEnabled(false);
					}
				}
				if (listWP.getName().equals("Resources")){
					if (!(scenarioObject instanceof IResourceCategory)){
						action.setEnabled(false);
					}
				}
				if (listWP.getName().equals("Nodes")){
					if (!(scenarioObject instanceof INodeCategory)){
						action.setEnabled(false);
					}
				}
				if (listWP.getName().equals("Node Connections")
				||	listWP.getName().equals("Node Resources")){					
					action.setEnabled(false);					
				}
				if (listWP.getName().equals("Node Behaviours")){
					if (!(scenarioObject instanceof IUserBehaviour)){
						action.setEnabled(false);
					}
				}
			}
		}
	}
		
}