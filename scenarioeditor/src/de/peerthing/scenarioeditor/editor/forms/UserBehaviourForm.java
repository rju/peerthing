package de.peerthing.scenarioeditor.editor.forms;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import de.peerthing.scenarioeditor.ScenarioEditorPlugin;
import de.peerthing.scenarioeditor.model.IListen;
import de.peerthing.scenarioeditor.model.INodeConnection;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.IUserBehaviour;
import de.peerthing.scenarioeditor.model.ICallUserBehaviour;
import de.peerthing.scenarioeditor.model.IDefaultCase;
import de.peerthing.scenarioeditor.model.IDelay;
import de.peerthing.scenarioeditor.model.ILoop;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IScenarioCondition;
import de.peerthing.scenarioeditor.model.IUserAction;
import de.peerthing.scenarioeditor.model.ScenarioFactory;
import de.peerthing.scenarioeditor.model.impl.ExecuteAddition;
import de.peerthing.scenarioeditor.model.impl.ExecuteDeletion;
import de.peerthing.scenarioeditor.model.impl.NameTest;
import de.peerthing.scenarioeditor.model.impl.NodeConnection;
import de.peerthing.scenarioeditor.model.impl.ProvidesPositionOfCommand;
import de.peerthing.scenarioeditor.model.impl.ScenarioUndo;
import de.peerthing.scenarioeditor.model.impl.UndoOperationValues;
import de.peerthing.scenarioeditor.model.impl.UserBehaviour;
import de.peerthing.scenarioeditor.editor.ScenarioEditor;

/**
 * This class manages a form with which you can manipulate
 * the data of a behaviour
 *
 *  @author Patrik, Petra (dynamic help)
 *  @reviewer Petra 24.03.2006
 */
public class UserBehaviourForm extends AbstractScenarioEditorForm {
	Label nameLabel;
	Label nameValueLabel;
	Label probabilityLabel;
	Spinner probabilitySpinner;
	IUserBehaviour behaviour;
	Label emptyLineLabel;
	Button createLoopButton;
	Label emptyLabel1;
	Button createDelayButton;
	Label emptyLabel;
	Button createListenButton;
	Label emptyLabel2;
	Button createCallBehaviourButton;
	Label emptyLabel3;
	Button createActionButton;
	Label emptyLabel4;
	Button createConditionButton;
	Label emptyLabel5;
	Button deleteButton;
    IScenario scenario;
    Label renameLabel;
    Text renameText;
    Label emptyLineLabel2;
    Label warningLabel;
    Label emptyLabel6;
    Label emptyLineLabel3;
    Label emptyLineLabel4;
    
    boolean noModifyEvents = false;    

    /**
     * checks if the handed name is ok (or illegal) for a behaviour.
     */
	public boolean isNameOk(String newName){
		for (IUserBehaviour userBehaviour : behaviour.getNodeCategory().getBehaviours()){
    		if((!userBehaviour.equals(behaviour)) && userBehaviour.getName().equals(newName)){
   				renameText.setBackground(new Color( null, 255,0,0));
   				warningLabel.setBackground(new Color( null, 255, 255,0));
   				warningLabel.setText("The name you wanted to chose is already spoken for. The name has not been adopted. Please chose another name.");
   				return false;
   			}
   			renameText.setBackground(new Color( null, 255,255,255));
   			warningLabel.setBackground(new Color( null, 255,255,255));
   			warningLabel.setText("");
   		}
		if (!NameTest.isNameOk(newName)){
    		renameText.setBackground(new Color( null, 255, 0,0));
    		warningLabel.setBackground(new Color( null, 255, 255,0));
    		warningLabel.setText("The name you chose has not been adopted. The name has to contain a sign (other than space) and less than 256 signs.");
    		return false;
    	}
		return true;

    }
	
	/**
	 * adds an entry to the undo list so that it is noted, that a behaviour
	 * has been deleted.	
	 */
	public void newDeleteUndo(IUserBehaviour nodeBehaviour){
    	INodeConnection savesPosition = 
			new NodeConnection(ProvidesPositionOfCommand.providesPosition(nodeBehaviour));
    	ScenarioEditorPlugin.getDefault().getUndoList().add(
				new ScenarioUndo
				(nodeBehaviour, savesPosition, UndoOperationValues.deleteWasDone));
    }

	/**
	 * The actions of the user are handled here. The Data
	 * the user changed will be saved in the behaviour-Object.
	 */
	public void widgetSelected(SelectionEvent e) {
		Object source = e.getSource();

		if (source == createLoopButton){
			ILoop newLoop = ScenarioFactory.createLoop(scenario);
			ExecuteAddition.addScenarioObjekt(behaviour, newLoop);			
			scenarioEditor.setDirty();
		}

		if (source == createDelayButton){
			IDelay newDelay = ScenarioFactory.createDelay(scenario);
			ExecuteAddition.addScenarioObjekt(behaviour, newDelay);
			scenarioEditor.setDirty();
		}
		
		if (source == createListenButton){
			IListen newListen = ScenarioFactory.createListen(scenario);
			ExecuteAddition.addScenarioObjekt(behaviour, newListen);
			scenarioEditor.setDirty();
		}

		if (source == createCallBehaviourButton){
			ICallUserBehaviour newCallBehaviour = ScenarioFactory.createCallBehaviour(scenario, behaviour);			
			ExecuteAddition.addScenarioObjekt(behaviour, newCallBehaviour);
			scenarioEditor.setDirty();
		}

		if (source == createConditionButton){
			IScenarioCondition newScenarioCondition = ScenarioFactory.createScenarioCondition(scenario);
			ExecuteAddition.addScenarioObjekt(behaviour, newScenarioCondition);
			IDefaultCase ca = ScenarioFactory.createDefaultCase(scenario);
			newScenarioCondition.setDefaultCase(ca);			
			scenarioEditor.setDirty();
		}

		if (source == createActionButton){
			IUserAction newAction = ScenarioFactory.createUserAction(scenario);
			ExecuteAddition.addScenarioObjekt(behaviour, newAction);
			scenarioEditor.setDirty();
		}

		if (source == deleteButton){
			
			ExecuteDeletion.deleteBehaviour(behaviour);

		}

	}

	@Override
	public boolean aboutToClose() {
		return true;
	}

	@Override
	public void applyAllChanges() {
	}

	/**
	 * All elements of the form are created and placed here.
	 */
	@Override
	public void createForm(Composite parent, ScenarioEditor editor) {
		super.createForm(parent, editor);

		form.getBody().setLayout(new GridLayout(2, false));
		form.setText("Behaviour:");

		emptyLineLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		GridData gd3 = new GridData(GridData.FILL_HORIZONTAL);
		gd3.horizontalSpan = 2;
		emptyLineLabel.setLayoutData(gd3);
		GridData gd4 = new GridData(GridData.FILL_HORIZONTAL);
		gd4.minimumHeight = 20;

		renameLabel= toolkit.createLabel(form.getBody(),"Name:", SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		renameText = toolkit.createText(form.getBody(),"", SWT.NONE);
		renameText.setLayoutData(gd);
		
		warningLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		warningLabel.setLayoutData(gd3);

		renameText.addSelectionListener(new SelectionAdapter() {
			   public void widgetDefaultSelected(SelectionEvent e) {
			   }
			});
			renameText.addModifyListener(new ModifyListener() {
			   public void modifyText(ModifyEvent e) {
				   if (isNameOk(renameText.getText())&& !noModifyEvents){
					   ScenarioEditorPlugin.getDefault().getUndoList().add(
								new ScenarioUndo
								(behaviour, new UserBehaviour(behaviour), UndoOperationValues.valueChanged));
					   behaviour.setName(renameText.getText());
					   ScenarioEditorPlugin.getDefault().getNavigationTree().update(behaviour);					   
					   scenarioEditor.setDirty();
				   }
			   }
		});
			
		GridData sameWidth = new GridData();
		sameWidth.widthHint = 200;
		
		//emptyLineLabel3 = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		//emptyLineLabel3.setLayoutData(gd3);

		createLoopButton = toolkit.createButton(form.getBody(), "Create Loop", SWT.NONE);
		createLoopButton.setLayoutData(sameWidth);
		createLoopButton.addSelectionListener(this);

		emptyLabel1 = toolkit.createLabel(form.getBody(), "", SWT.NONE);

		createDelayButton = toolkit.createButton(form.getBody(), "Create Delay", SWT.NONE);
		createDelayButton.setLayoutData(sameWidth);
		createDelayButton.addSelectionListener(this);

		emptyLabel2 = toolkit.createLabel(form.getBody(), "", SWT.NONE);

		createListenButton = toolkit.createButton(form.getBody(), "Create Listen", SWT.NONE);
		createListenButton.setLayoutData(sameWidth);
		createListenButton.addSelectionListener(this);

		emptyLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);

		createCallBehaviourButton = toolkit.createButton(form.getBody(), "Create CallBehaviour", SWT.NONE);
		createCallBehaviourButton.setLayoutData(sameWidth);
		createCallBehaviourButton.addSelectionListener(this);

		emptyLabel3 = toolkit.createLabel(form.getBody(), "", SWT.NONE);

		createActionButton = toolkit.createButton(form.getBody(), "Create Action", SWT.NONE);
		createActionButton.setLayoutData(sameWidth);
		createActionButton.addSelectionListener(this);

		emptyLabel4 = toolkit.createLabel(form.getBody(), "", SWT.NONE);

		createConditionButton = toolkit.createButton(form.getBody(), "Create Condition", SWT.NONE);
		createConditionButton.setLayoutData(sameWidth);
		createConditionButton.addSelectionListener(this);
		
		emptyLabel6 = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		
		emptyLineLabel4 = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		emptyLineLabel4.setLayoutData(gd3);

		deleteButton = toolkit.createButton(form.getBody(), "Delete this Behaviour", SWT.NONE);
		deleteButton.setLayoutData(sameWidth);
		deleteButton.setBackground(new Color( null, 240,10,40));
		deleteButton.addSelectionListener(this);
		
		emptyLineLabel2 = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		emptyLineLabel2.setLayoutData(gd3);				
		
		//dynamic help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(renameText, "de.peerthing.workbench.scenario_nodes");
		renameText.setToolTipText("Here you can change the name of this behaviour.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(createLoopButton, "de.peerthing.workbench.scenario_nodes");
		createLoopButton.setToolTipText("Push this button to create a loop for this behaviour.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(createDelayButton, "de.peerthing.workbench.scenario_nodes");
		createDelayButton.setToolTipText("Push this button to create a delay for this behaviour.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(createListenButton, "de.peerthing.workbench.scenario_nodes");
		createListenButton.setToolTipText("Push this button to create a listen for this behaviour.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(createCallBehaviourButton, "de.peerthing.workbench.scenario_nodes");
		createCallBehaviourButton.setToolTipText("Push this button to create a call behaviour for this behaviour.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(createActionButton, "de.peerthing.workbench.scenario_nodes");
		createActionButton.setToolTipText("Push this button to create a action for this behaviour.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(createConditionButton, "de.peerthing.workbench.scenario_nodes");
		createConditionButton.setToolTipText("Push this button to create a condition for this behaviour.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(deleteButton, "de.peerthing.workbench.scenario_nodes");
		deleteButton.setToolTipText("Push this button to delete this behaviour.");		
	}

	/**
     * This method is used every time a change is made. The changes are assumed and the form
     * is refreshed.
     */
	@Override
	public void update(IScenarioObject object) {
		currentObject = object;
		this.scenario = object.getScenario();
		behaviour = (IUserBehaviour) object;
		noModifyEvents = true;
		renameText.setText(behaviour.getName());
		noModifyEvents = false;
	}
}
