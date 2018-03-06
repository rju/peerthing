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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import de.peerthing.scenarioeditor.ScenarioEditorPlugin;
import de.peerthing.scenarioeditor.model.ICallUserBehaviour;
import de.peerthing.scenarioeditor.model.IDefaultCase;
import de.peerthing.scenarioeditor.model.IDelay;
import de.peerthing.scenarioeditor.model.IDistribution;
import de.peerthing.scenarioeditor.model.IListen;
import de.peerthing.scenarioeditor.model.ILoop;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IScenarioCondition;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.IUserAction;
import de.peerthing.scenarioeditor.model.IUserBehaviour;
import de.peerthing.scenarioeditor.model.IDistribution.DistributionType;
import de.peerthing.scenarioeditor.model.impl.ExecuteAddition;
import de.peerthing.scenarioeditor.model.impl.ExecuteDeletion;
import de.peerthing.scenarioeditor.model.impl.Loop;
import de.peerthing.scenarioeditor.model.impl.ProvideUserBehaviour;
import de.peerthing.scenarioeditor.model.impl.ScenarioUndo;
import de.peerthing.scenarioeditor.model.impl.UndoOperationValues;
import de.peerthing.scenarioeditor.model.ScenarioFactory;
import de.peerthing.scenarioeditor.editor.ScenarioEditor;

/**
 * This class manages a form with which you can manipulate the data of a loop
 *
 * @author Patrik, Petra (dynamic help)
 * @reviewer Petra 24.03.2006
 */
public class LoopForm extends AbstractScenarioEditorForm {

    /**
     * A label for "until" a loop repeats
     */
	Label untilLabel;
    
    /**
     * A textfield to enter condition "until a loop repeats 
     */
	Text untilText;
    
    /**
     * A distribution label
     */
	Label distributionLabel;
    
    /**
     * An empty label 
     */
	Label emptyLineLabel2;
    
    /**
     * A label for "Minimum number of execution" 
     */
	Label minNumberLabel;
    
    /**
     * A minimum number of execution spinner (0-maxint)
     */
	Spinner minSpinner;
    
    /**
     * A label for "Maximum number of execution"
     */
	Label maxNumberLabel;
    
    /**
     * A maximum number of execution spinner (0-maxint)
     */
	Spinner maxSpinner;
    
    /**
     * A empty label (placeholder)
     */
	Label emptyLabel;
    
    /**
     * A label for "Type of distribution
     */
	Label typLabel;
    
    /**
     * A combobox to select kind of distribution
     */
	Combo distributionCombo;
    
    /**
     * A label for "Mean value"
     */
	Label meanValueLabel;
    
    /**
     * A mean value spinner (0-maxint)
     */
	Spinner meanValueSpinner;
    
    /**
     * A label for "Variance"
     */
	Label varianceLabel;
    
    /**
     * A variance spinner (0-maxint)
     */
	Spinner varianceSpinner;
    
    /**
     * A new loop
     */
	ILoop loop1;
    
    /**
     * A button to create a new loop
     */
	Button createLoopButton;
    
    /**
     * A button to create a new delay
     */
	Button createDelayButton;
    
    /**
     * A button to create a new callBehaviour
     */
	Button createCallBehaviourButton;
    
    /**
     * A button to create a new user action
     */
	Button createActionButton;
    
    /**
     * A button to create a new condition
     */
	Button createConditionButton;
    
    /**
     * A button to create a new listen
     */
	Button createListenButton;
	
    /**
     * A button to delete current loop
     */
	Button deleteButton;
    
    /**
     * The current scenario
     */
	IScenario scenario;
    
    /**
     * Variable is events are modified or not
     */
	boolean noModifyEvents = false;
	
    /**
     * A warning label
     */
	Label warningLabel;
	
	Label emptyLineLabel;
	
	/**
	 * Gui is initialised in the constructor
	 *
	 * @param container
	 */
	public LoopForm() {
	}

	/**
	 * The types of distribution are inserted into the combo box
	 */
	public void distributionInit() {
		for (DistributionType distType : IDistribution.DistributionType
				.values()) {
			distributionCombo.add(distType.name());
		}
	}
	
    /**
     * This method adds an operation, made in this form, to the undoable list
     */
	public void newChangeUndo(){
    	ScenarioEditorPlugin.getDefault().getUndoList().add(
				new ScenarioUndo
				(loop1, new Loop(loop1), UndoOperationValues.valueChanged));
    }

	/**
	 * The actions of the user are handled here. The Data the user changed will
	 * be saved in the loop-Object.
	 */
	public void widgetSelected(SelectionEvent e) {		
		
		Object source = e.getSource();				
		
		warningLabel.setBackground(new Color( null, 255,255,255));
		warningLabel.setText("");	
		
		if (source==minSpinner){
			valuesOk("min");
		}
		if (source==maxSpinner){
			valuesOk("max");
		}
		
		if (source == meanValueSpinner){
			newChangeUndo();
			loop1.getDistribution().setMean(
				(double) meanValueSpinner.getSelection());
			scenarioEditor.setDirty();
		}
		if (source == varianceSpinner){
			newChangeUndo();
			loop1.getDistribution().setVariance(
				(double) varianceSpinner.getSelection());
			scenarioEditor.setDirty();
		}
		
		if (source == distributionCombo){
			newChangeUndo();
			loop1.getDistribution().setType(
				IDistribution.DistributionType.valueOf(distributionCombo
						.getText()));
			scenarioEditor.setDirty();
		}


		if (source == createLoopButton) {
			ILoop newLoop = ScenarioFactory.createLoop(scenario);
			ExecuteAddition.addScenarioObjekt(loop1, newLoop);			
			scenarioEditor.setDirty();
		}

		if (source == createDelayButton) {
			IDelay newDelay = ScenarioFactory.createDelay(scenario);
			ExecuteAddition.addScenarioObjekt(loop1, newDelay);
			scenarioEditor.setDirty();
		}
		
		if (source == createListenButton) {
			IListen newListen = ScenarioFactory.createListen(scenario);
			ExecuteAddition.addScenarioObjekt(loop1, newListen);
			scenarioEditor.setDirty();
		}

		if (source == createCallBehaviourButton) {
			IUserBehaviour behaviourToCall = ProvideUserBehaviour.provideBehaviour(loop1);
			ICallUserBehaviour newCallBehaviour = ScenarioFactory.createCallBehaviour(scenario, behaviourToCall);						
			ExecuteAddition.addScenarioObjekt(loop1, newCallBehaviour);
			scenarioEditor.setDirty();
		}

		if (source == createConditionButton) {
			IScenarioCondition newScenarioCondition = ScenarioFactory
					.createScenarioCondition(scenario);
			ExecuteAddition.addScenarioObjekt(loop1, newScenarioCondition);
			IDefaultCase ca = ScenarioFactory.createDefaultCase(scenario);
			newScenarioCondition.setDefaultCase(ca);			
			scenarioEditor.setDirty();
		}

		if (source == createActionButton) {
			IUserAction newAction = ScenarioFactory.createUserAction(scenario);
			ExecuteAddition.addScenarioObjekt(loop1, newAction);			
			scenarioEditor.setDirty();
		}

		if (source == deleteButton) {
			
			ExecuteDeletion.deleteCommand(loop1);
			
			/*ICommandContainer c1 = loop1.getCommandContainer();

			for (int i = 0; i < c1.getCommands().size(); i++) {
				if (c1.getCommands().get(i).equals(loop1)) {
					INodeConnection savesPosition = 
						new NodeConnection(ProvidesPositionOfCommand.providesPosition(loop1));
					ScenarioEditorPlugin.getDefault().getUndoList().add(
							new ScenarioUndo
							(loop1, savesPosition, UndoOperationValues.deleteWasDone));
					c1.getCommands().remove(i);
					ScenarioEditorPlugin.getDefault().getNavigationTree()
							.refresh(c1);
					scenarioEditor.objectDeleted(loop1);
					scenarioEditor.setDirty();
					ScenarioEditorPlugin.getDefault().getNavigationTree().select(c1);
				}
			}*/

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
     * This method checks if entered values are useable
     * @param minOrMax
     * @return
     */
	public boolean valuesOk(String minOrMax){
		boolean ok = true;
		if (minSpinner.getSelection()
			> maxSpinner.getSelection()){			

			ok = false;
		} else {

			if (minOrMax.equals("min")){
				newChangeUndo();
				loop1.getDistribution().setMin(
						(double) minSpinner.getSelection());
				scenarioEditor.setDirty();
			}
		}
		if (maxSpinner.getSelection()
				< minSpinner.getSelection()){

				ok = false;
		} else {
			if (minOrMax.equals("max")){

				newChangeUndo();
				loop1.getDistribution().setMax(
						(double) maxSpinner.getSelection());
				scenarioEditor.setDirty();
			}
		}										
		
		if (ok == false){
			warningLabel.setText("Minimal value has to be <= Maximal value! Values haven't been adopted!"

					);
			minSpinner.setSelection((int)(loop1.getDistribution().getMin()));
			maxSpinner.setSelection((int)(loop1.getDistribution().getMax()));
			warningLabel.setBackground(new Color( null, 255,0,0));
		} else {
			warningLabel.setBackground(new Color( null, 255,255,255));
			warningLabel.setText("");			
		}
		return true;
	}

    /**
     * This method visualizes the given input
     */
	@Override
	public void createForm(Composite parent, ScenarioEditor editor) {
		super.createForm(parent, editor);

		form.getBody().setLayout(new GridLayout(2, false));
		form.setText("Loop Information:");

		GridData gd12 = new GridData(GridData.FILL_HORIZONTAL);
		untilLabel = toolkit.createLabel(form.getBody(), "Until:", SWT.NONE);
		untilText = toolkit.createText(form.getBody(), "", SWT.NONE);
		untilText.setLayoutData(gd12);

		untilText.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		untilText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (!noModifyEvents){
					newChangeUndo();
					loop1.setUntilCondition(untilText.getText());
				}
			}
		});

		GridData gd12c = new GridData(GridData.FILL_HORIZONTAL);
		gd12c.horizontalSpan = 3;
		emptyLineLabel2 = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		emptyLineLabel2.setLayoutData(gd12c);

		distributionLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		distributionLabel.setLayoutData(gd12);

		GridData gb12b = new GridData(GridData.FILL_HORIZONTAL);
		emptyLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		emptyLabel.setLayoutData(gb12b);

		GridData gd13 = new GridData(GridData.FILL_HORIZONTAL);
		minNumberLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		minNumberLabel.setLayoutData(gd13);

		GridData gd14 = new GridData();
		minSpinner = new Spinner(form.getBody(), SWT.BORDER);
		minSpinner.setLayoutData(gd14);
		minSpinner.addSelectionListener(this);
		minSpinner.setMaximum(10000);

		GridData gd15 = new GridData(GridData.FILL_HORIZONTAL);
		maxNumberLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		maxNumberLabel.setLayoutData(gd15);

		GridData gd16 = new GridData();
		maxSpinner = new Spinner(form.getBody(), SWT.BORDER);
		maxSpinner.setLayoutData(gd16);
		maxSpinner.addSelectionListener(this);
		maxSpinner.setMaximum(10000);

		GridData gd17 = new GridData(GridData.FILL_HORIZONTAL);
		typLabel = toolkit.createLabel(form.getBody(), "", SWT.None);
		typLabel.setLayoutData(gd17);

		GridData gd18 = new GridData();
		distributionCombo = new Combo(form.getBody(), SWT.BORDER);
		distributionCombo.addSelectionListener(this);
		distributionCombo.setLayoutData(gd18);
		gd18.widthHint = 87;

		GridData gd19 = new GridData(GridData.FILL_HORIZONTAL);
		meanValueLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		meanValueLabel.setLayoutData(gd19);

		GridData gd20 = new GridData();
		meanValueSpinner = new Spinner(form.getBody(), SWT.BORDER);
		meanValueSpinner.setLayoutData(gd20);
		meanValueSpinner.addSelectionListener(this);
		meanValueSpinner.setMaximum(10000);

		GridData gd21 = new GridData(GridData.FILL_HORIZONTAL);
		varianceLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		varianceLabel.setLayoutData(gd21);

		GridData gd22 = new GridData();
		varianceSpinner = new Spinner(form.getBody(), SWT.BORDER);
		varianceSpinner.setLayoutData(gd22);
		varianceSpinner.addSelectionListener(this);
		varianceSpinner.setMaximum(10000);
		
		GridData span2 = new GridData(GridData.FILL_HORIZONTAL);
		span2.horizontalSpan = 2;
		warningLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		warningLabel.setLayoutData(span2);

		GridData sameWidth = new GridData();
		sameWidth.widthHint = 150;
		
		createLoopButton = toolkit.createButton(form.getBody(), "Create Loop",
				SWT.NONE);		
		createLoopButton.addSelectionListener(this);
		createLoopButton.setLayoutData(sameWidth);

		createDelayButton = toolkit.createButton(form.getBody(),
				"Create Delay", SWT.NONE);
		createDelayButton.addSelectionListener(this);
		createDelayButton.setLayoutData(sameWidth);
		
		createListenButton = toolkit.createButton(form.getBody(),
				"Create Listen", SWT.NONE);
		createListenButton.addSelectionListener(this);
		createListenButton.setLayoutData(sameWidth);

		createCallBehaviourButton = toolkit.createButton(form.getBody(),
				"Create CallBehaviour", SWT.NONE);
		createCallBehaviourButton.addSelectionListener(this);
		createCallBehaviourButton.setLayoutData(sameWidth);

		createActionButton = toolkit.createButton(form.getBody(),
				"Create Action", SWT.NONE);
		createActionButton.addSelectionListener(this);
		createActionButton.setLayoutData(sameWidth);

		createConditionButton = toolkit.createButton(form.getBody(),
				"Create Condition", SWT.NONE);
		createConditionButton.addSelectionListener(this);
		createConditionButton.setLayoutData(sameWidth);
		
		emptyLineLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		emptyLineLabel.setLayoutData(gd12c);

		deleteButton = toolkit.createButton(form.getBody(), "Delete This loop",
				SWT.DEL);
		deleteButton.setLayoutData(sameWidth);
		deleteButton.setBackground(new Color( null, 240,10,40));
		deleteButton.addSelectionListener(this);
		
		//dynamic help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(untilText, "de.peerthing.workbench.scenario_nodes");
		untilText.setToolTipText("Enter a name for the break condition.");		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(minSpinner, "de.peerthing.workbench.scenario_nodes");
		minSpinner.setToolTipText("Enter the minimal number of execution.");	
		PlatformUI.getWorkbench().getHelpSystem().setHelp(maxSpinner, "de.peerthing.workbench.scenario_nodes");
		maxSpinner.setToolTipText("Enter the macimal number of execution.");	
		PlatformUI.getWorkbench().getHelpSystem().setHelp(distributionCombo, "de.peerthing.workbench.scenario_nodes");
		distributionCombo.setToolTipText("Choose a uniform or normal distribution.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(meanValueSpinner, "de.peerthing.workbench.scenario_nodes");
		meanValueSpinner.setToolTipText("Choose a value for the mean value.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(varianceSpinner, "de.peerthing.workbench.scenario_nodes");
		varianceSpinner.setToolTipText("Choose a value for the variance.");
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
    
    /**
     * This method is used every time a change is made. The changes are assumed and the form
     * is refreshed.
     */
	@Override
	public void update(IScenarioObject object) {
		currentObject = object;
		this.scenario = object.getScenario();
		loop1 = (ILoop) object;
		
		warningLabel.setBackground(new Color( null, 255,255,255));
		warningLabel.setText("");	

		if (distributionCombo.getItemCount() == 0) {
			distributionInit();
		}
		for (int i = 0; i < distributionCombo.getItemCount(); i++) {
			if (loop1.getDistribution().getType().name().equals(
					distributionCombo.getItem(i))) {
				distributionCombo.select(i);

			}
		}
		noModifyEvents = true;
		if (loop1.getUntilCondition() != null) {
			untilText.setText(loop1.getUntilCondition());
		} else {
			untilText.setText("");
		}
		noModifyEvents = false;
		distributionLabel.setText("Distribution:");
		minNumberLabel.setText("Minimal number of execution:");
		maxNumberLabel.setText("Maximal number of execution:");
		typLabel.setText("Type of distribution:");
		meanValueLabel.setText("Mean value:");
		varianceLabel.setText("Variance:");		
		minSpinner.setSelection((int) loop1.getDistribution().getMin());
		maxSpinner.setSelection((int) loop1.getDistribution().getMax());
		meanValueSpinner.setSelection((int) loop1.getDistribution().getMean());
		varianceSpinner.setSelection((int) loop1.getDistribution()
				.getVariance());
	}

}
