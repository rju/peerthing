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
import org.eclipse.ui.forms.widgets.ScrolledForm;

import de.peerthing.scenarioeditor.ScenarioEditorPlugin;
import de.peerthing.scenarioeditor.model.ICallUserBehaviour;
import de.peerthing.scenarioeditor.model.ICase;
import de.peerthing.scenarioeditor.model.IDefaultCase;
import de.peerthing.scenarioeditor.model.IDelay;
import de.peerthing.scenarioeditor.model.IListen;
import de.peerthing.scenarioeditor.model.ILoop;
import de.peerthing.scenarioeditor.model.IScenarioCondition;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.IUserAction;
import de.peerthing.scenarioeditor.model.IUserBehaviour;
import de.peerthing.scenarioeditor.model.ScenarioFactory;
import de.peerthing.scenarioeditor.editor.ScenarioEditor;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.impl.Case;
import de.peerthing.scenarioeditor.model.impl.ExecuteAddition;
import de.peerthing.scenarioeditor.model.impl.ExecuteDeletion;
import de.peerthing.scenarioeditor.model.impl.ProvideUserBehaviour;
import de.peerthing.scenarioeditor.model.impl.ScenarioUndo;
import de.peerthing.scenarioeditor.model.impl.UndoOperationValues;

/**
 * This Class manages a Form with which you can manipulate
 * the Data of a Case
 *
 * @author Hendrik Angenendt, Patrik, , Petra (dynamic help)
 * @reviewer Petra 24.04.2006
 */
public class CaseForm extends AbstractScenarioEditorForm {

    /**
     * A label for expression
     */
	Label expressionLabel;
    
    /**
     * A textfield to enter expression text
     */
	Text expressionText;
    
    /**
     * A button to check if to use expression or not
     */
	Button expressionCheck;
    
    /**
     * A label for probability
     */
	Label probabilityLabel;
    
    /**
     * A label for in %
     */
	Label inPercent;
    
    /**
     * A button to check if to use for probability or not
     */
	Button probabilityCheck;
    
    /**
     * A probability spinner (0-100%)
     */
	Spinner probabilityValueSpinner;
    
    /**
     * A new Case
     */
	ICase iCase;
    
    /**
     * An empty label
     */
	Label emptyLineLabel;
    
    /**
     * A button to create a new loop
     */
	Button createLoopButton;
    
    /**
     * A button to create a new delay
     */
	Button createDelayButton;
    
    /**
     * A button to create a new listen
     */
	Button createListenButton;
    
    /**
     * A button to create a new call behaviour
     */
	Button createCallBehaviourButton;
    
    /**
     * A button to create a new action
     */
	Button createActionButton;
    
    /**
     * A button to create a new condition
     */
	Button createConditionButton;
    
    /**
     * A button to delete current case
     */
	Button deleteButton;
    
    /**
     * The current scenario
     */
	IScenario scenario;
	
	Label emptyLineLabel2;
    
    /**
     * A variable to check if something is modified
     */
	boolean noModifyEvents = false;

	/**
     * The default constructor
	 * Gui is initialised in the constructor
	 * @param container
	 */
	public CaseForm () {
	}

    /**
     * This method returns the current form
     * @return
     */
	public ScrolledForm getForm(){
		return form;
	}
    
    /**
     * This method adds an operation, made in this form, to the undoable list
     */
	public void newChangeUndo(){
    	ScenarioEditorPlugin.getDefault().getUndoList().add(
				new ScenarioUndo
				(iCase, new Case((Case)iCase), UndoOperationValues.valueChanged));
    }

	/**
	 * The actions of the user are handled here. The Data
	 * the user changed will be saved in the case-Object.
	 */
	public void widgetSelected(SelectionEvent e) {		
		Object source = e.getSource();
		scenarioEditor.setDirty();
		if (source == expressionCheck){
			newChangeUndo();
			if (expressionCheck.getSelection()){
				probabilityValueSpinner.setEnabled(false);
				probabilityCheck.setSelection(false);
				expressionText.setEnabled(true);
				iCase.setConditionUsed(true);				
				return;
			} else {
				expressionCheck.setSelection(true);
				probabilityCheck.setSelection(false);
				probabilityValueSpinner.setEnabled(false);
				expressionText.setEnabled(true);
				return;
			}
		}
		if (source == probabilityCheck){
			newChangeUndo();
			if (probabilityCheck.getSelection()){
				expressionText.setEnabled(false);
				expressionCheck.setSelection(false);
				probabilityValueSpinner.setEnabled(true);
				iCase.setConditionUsed(false);
				return;
			} else {
				probabilityCheck.setSelection(true);
				expressionCheck.setSelection(false);
				expressionText.setEnabled(false);
				probabilityValueSpinner.setEnabled(true);
				return;
			}
		}

		if (source == createLoopButton){
			ILoop newLoop = ScenarioFactory.createLoop(scenario);
			ExecuteAddition.addScenarioObjekt(iCase, newLoop);
		}
		
		if (source == deleteButton){
			
			ExecuteDeletion.deleteCase(iCase);
			
			/*INodeConnection savesPosition = 
				new NodeConnection(ProvidesPositionOfCommand.providesPosition((Case)iCase));
			ScenarioEditorPlugin.getDefault().getUndoList().add(
					new ScenarioUndo
					(iCase, savesPosition, UndoOperationValues.deleteWasDone));
			iCase.getScenarioCondition().getCases().remove(iCase);
			ScenarioEditorPlugin.getDefault().getNavigationTree()
					.refresh(iCase.getScenarioCondition());
			scenarioEditor.objectDeleted(iCase);
			ScenarioEditorPlugin.getDefault().getNavigationTree().select(iCase.getScenarioCondition());
			scenarioEditor.setDirty();*/
		}

		if (source == createDelayButton){
			IDelay newDelay = ScenarioFactory.createDelay(scenario);
			ExecuteAddition.addScenarioObjekt(iCase, newDelay);            
		}
		
		if (source == createListenButton){
			IListen newListen = ScenarioFactory.createListen(scenario);
			ExecuteAddition.addScenarioObjekt(iCase, newListen);            
		}

		if (source == createCallBehaviourButton){
			IUserBehaviour behaviourToCall = ProvideUserBehaviour.provideBehaviour(iCase);
			ICallUserBehaviour newCallBehaviour = ScenarioFactory.createCallBehaviour(scenario, behaviourToCall);			
			ExecuteAddition.addScenarioObjekt(iCase, newCallBehaviour);
		}

		if (source == createConditionButton){
			IScenarioCondition newScenarioCondition = ScenarioFactory.createScenarioCondition(scenario);
			ExecuteAddition.addScenarioObjekt(iCase, newScenarioCondition);
			IDefaultCase ca = ScenarioFactory.createDefaultCase(scenario);
			newScenarioCondition.setDefaultCase(ca);			
		}

		if (source == createActionButton){
			IUserAction newAction = ScenarioFactory.createUserAction(scenario);
			ExecuteAddition.addScenarioObjekt(iCase, newAction);
		}
		if (source == probabilityValueSpinner){
			newChangeUndo();
			iCase.setProbability((double)probabilityValueSpinner.getSelection() / 100);			
		}
		ScenarioEditorPlugin.getDefault().getNavigationTree().update(iCase);

	}


    /**
     * This method is used every time a change is made. The changes are assumed and the form
     * will be refreshed.
     */
	@Override
	public void update(IScenarioObject object) {				
		
		probabilityValueSpinner.setEnabled(true);
		expressionText.setEnabled(true);
		currentObject = object;
		iCase = (ICase) object;
		
		noModifyEvents = true;
        if (iCase.getCondition()!=null){        	
        	expressionText.setText(iCase.getCondition());
        } else {
        	expressionText.setText("");
        }
        noModifyEvents = false;
		
		scenario = iCase.getScenario();
		double temp=iCase.getProbability()*100;
		probabilityValueSpinner.setSelection((int)temp);
				
		if (iCase.isConditionUsed()){
			expressionCheck.setSelection(true);
			probabilityCheck.setSelection(false);
			probabilityValueSpinner.setEnabled(false);
		} else {
			expressionCheck.setSelection(false);
			probabilityCheck.setSelection(true);
			expressionText.setEnabled(false);
		}
		form.redraw();
	}

	@Override
	public boolean aboutToClose() {
		return true;
	}

	@Override
	public void applyAllChanges() {
	}
    
    
     /**
     * This method visualises the given inputs
     */
	@Override
	public void createForm(Composite parent, ScenarioEditor editor) {
		super.createForm(parent, editor);

		form.getBody().setLayout(new GridLayout(3, false));
		form.setText("Case:");

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		expressionLabel = toolkit.createLabel(form.getBody(),"Expression:", SWT.NONE);
		expressionLabel.setLayoutData(gd);
		expressionText = toolkit.createText(form.getBody(), "", SWT.BORDER);
		expressionText.setLayoutData(gd);
		
		expressionText.addSelectionListener(new SelectionAdapter() {
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });
        expressionText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
	            if (!noModifyEvents){
	            	newChangeUndo();
	            	iCase.setCondition(expressionText.getText());		            
		            setDirty();
	            }
            }
        });
		
		
		expressionCheck = toolkit.createButton(form.getBody(), "Use Expression", SWT.CHECK);
		expressionCheck.addSelectionListener(this);

		probabilityLabel = toolkit.createLabel(form.getBody(),"Probability: (in %)", SWT.NONE);
		probabilityLabel.setLayoutData(gd);
		probabilityValueSpinner = new Spinner(form.getBody(), SWT.BORDER);
	 	probabilityValueSpinner.addSelectionListener(this);
	 	probabilityCheck = toolkit.createButton(form.getBody(), "Use Probability", SWT.CHECK);
	 	probabilityCheck.addSelectionListener(this);

	 	emptyLineLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
	 	GridData gd3 = new GridData(GridData.FILL_HORIZONTAL);
		gd3.horizontalSpan = 3;
		emptyLineLabel.setLayoutData(gd3);
		
		GridData sameWidth = new GridData();
		sameWidth.widthHint = 150;

	 	createLoopButton = toolkit.createButton(form.getBody(), "Create Loop", SWT.NONE);
	 	createLoopButton.setLayoutData(sameWidth);
		createLoopButton.addSelectionListener(this);

		createDelayButton = toolkit.createButton(form.getBody(), "Create Delay", SWT.NONE);
		createDelayButton.setLayoutData(sameWidth);
		createDelayButton.addSelectionListener(this);

		createListenButton = toolkit.createButton(form.getBody(), "Create Listen", SWT.NONE);
		createListenButton.setLayoutData(sameWidth);
		createListenButton.addSelectionListener(this);
		
		createCallBehaviourButton = toolkit.createButton(form.getBody(), "Create CallBehaviour", SWT.NONE);
		createCallBehaviourButton.setLayoutData(sameWidth);
		createCallBehaviourButton.addSelectionListener(this);

		createActionButton = toolkit.createButton(form.getBody(), "Create Action", SWT.NONE);
		createActionButton.setLayoutData(sameWidth);
		createActionButton.addSelectionListener(this);

		createConditionButton = toolkit.createButton(form.getBody(), "Create Condition", SWT.NONE);
		createConditionButton.setLayoutData(sameWidth);
		createConditionButton.addSelectionListener(this);
		
		emptyLineLabel2 = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		emptyLineLabel2.setLayoutData(gd3);
		
		deleteButton = toolkit.createButton(form.getBody(), "Delete Case", SWT.NONE);
		deleteButton.setLayoutData(sameWidth);
		deleteButton.setBackground(new Color( null, 240,10,40));
		deleteButton.addSelectionListener(this);
		
		//dynamic help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(expressionText, "de.peerthing.workbench.scenario_nodes");
		expressionText.setToolTipText("Enter a name for the expression.");	
		PlatformUI.getWorkbench().getHelpSystem().setHelp(expressionCheck, "de.peerthing.workbench.scenario_nodes");
		expressionCheck.setToolTipText("Check to use this expression.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(probabilityCheck, "de.peerthing.workbench.scenario_nodes");
		probabilityCheck.setToolTipText("Check to use this probability..");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(probabilityValueSpinner, "de.peerthing.workbench.scenario_nodes");
		probabilityValueSpinner.setToolTipText("Choose a value for the probability in percent.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(createLoopButton, "de.peerthing.workbench.scenario_nodes");
		createLoopButton.setToolTipText("Push to create a loop for this loop");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(createDelayButton, "de.peerthing.workbench.scenario_nodes");
		createDelayButton.setToolTipText("Push to create a delay for this loop");		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(createCallBehaviourButton, "de.peerthing.workbench.scenario_nodes");
		createCallBehaviourButton.setToolTipText("Push to create a call behaviour for this loop");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(createActionButton, "de.peerthing.workbench.scenario_nodes");
		createActionButton.setToolTipText("Push to create a action for this loop");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(createConditionButton, "de.peerthing.workbench.scenario_nodes");
		createConditionButton.setToolTipText("Push to create a condition for this loop");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(createListenButton, "de.peerthing.workbench.scenario_nodes");
		createListenButton.setToolTipText("Push to create a listen for this loop");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(deleteButton, "de.peerthing.workbench.scenario_nodes");
		deleteButton.setToolTipText("By pushing this loop will be deleted.");
		
	}
}

