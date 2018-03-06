package de.peerthing.scenarioeditor.editor.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.PlatformUI;

import de.peerthing.scenarioeditor.ScenarioEditorPlugin;
import de.peerthing.scenarioeditor.model.IDelay;
import de.peerthing.scenarioeditor.model.IDistribution;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.IDistribution.DistributionType;
import de.peerthing.scenarioeditor.model.impl.Delay;
import de.peerthing.scenarioeditor.model.impl.ExecuteDeletion;
import de.peerthing.scenarioeditor.model.impl.ScenarioUndo;
import de.peerthing.scenarioeditor.model.impl.UndoOperationValues;
import de.peerthing.scenarioeditor.editor.ScenarioEditor;

/**
 *
 * This class manages a form with which you can manipulate the data of a Delay.
 *
 * @author Hendrik Angenendt, Patrik, Petra (dynamic help)
 * @reviewer Petra 24.03.2006
 */

public class DelayForm extends AbstractScenarioEditorForm {

    /**
     * A label for minmal delay
     */
	Label minDelayLabel;

    /**
     * A label for maximum delay
     */
	Label maxDelayLabel;

    /**
     * A label for mean delay
     */
	Label meanDelayLabel;

    /**
     * A label for variance
     */
	Label varianceDelayLabel;	

    /**
     * A label for distribution
     */
	Label distributionLabel;

    /**
     * A spinner to set minmum delay
     */
	Spinner minDelaySpinner;

    /**
     * A spinner to set maximum delay
     */
	Spinner maxDelaySpinner;

    /**
     * A spinner to set mean delay
     */
	Spinner meanDelaySpinner;

    /**
     * A spinner to set variance of delay
     */
	Spinner varianceDelaySpinner;

    /**
     * A combobox to select kind of distribution
     */
	Combo distributionChoiceCombo;

    /**
     * A new delay
     */
	IDelay delay1;

    /**
     * A new distribution
     */
	IDistribution temp;

    /**
     * A button to delete current delay
     */
	Button deleteButton;
	
    /**
     * A string for choosen unit
     */
	String unitString;
	
    /**
     * A label for delay unit change
     */
	Label unitChangerLabel;
	
    /**
     * A combobox to select delay unit
     */
	Combo unitChanger;
	
    /**
     * The maximum value of a spinner 
     */
	int spinnerHigh = 999999999;
	
    /**
     * A warning label
     */
	Label warningLabel;
	
    /**
     * A warning label
     */
	Label warningLabel2;

	/**
	 * Constructor for creating viewable form
	 *
	 * @param container
	 */
	public DelayForm() {
	}

	/**
	 *
	 * initialize the distributionType
	 */
	public void distributionInit() {
		for (DistributionType distType : IDistribution.DistributionType
				.values()) {
			distributionChoiceCombo.add(distType.name());
		}
	}
	
    /**
     * This method adds an operation, made in this form, to the undoable list
     */
	public void newChangeUndo(){
    	ScenarioEditorPlugin.getDefault().getUndoList().add(
				new ScenarioUndo
				(delay1, new Delay(delay1), UndoOperationValues.valueChanged));
    }

	/**
	 * The actions of the user are handled here. The Data the user changed will
	 * be saved in the delay-Object.
	 */

	public void widgetSelected(SelectionEvent e) {
		Object source = e.getSource();
		
		warningLabel.setText("");
		warningLabel.setBackground(new Color( null, 255,255,255));
		
		int unitValue = 1;		
		unitString = "(in milliseconds):";
		if (unitChanger.getSelectionIndex() ==1){
			unitString = "(in seconds):";
			unitValue = 1000;
		}
		if (unitChanger.getSelectionIndex() ==2){
			unitString = "(in minutes):";
			unitValue = 60000;
		}
		if (unitChanger.getSelectionIndex() ==3){
			unitString = "(in hours):";
			unitValue = 3600000;
		}
		
		if (source == unitChanger){			
			minDelaySpinner.setSelection((int)((double)delay1.
					getDistribution().getMin()/(double)unitValue));
			maxDelaySpinner.setSelection((int)((double)delay1.
					getDistribution().getMax()/(double)unitValue));
			meanDelaySpinner.setSelection((int)((double)delay1.
					getDistribution().getMean()/(double)unitValue));
			varianceDelaySpinner.setSelection((int)((double)delay1.
					getDistribution().getVariance()/(double)unitValue));
			minDelayLabel.setText("Minimal delay "+ unitString);
			maxDelayLabel.setText("Maximal delay "+ unitString);
			meanDelayLabel.setText("Mean value " + unitString);
			varianceDelayLabel.setText("Variance " + unitString);
		}				

		if (source == minDelaySpinner) {			
			if (valuesOk(unitValue, "min")){
			}
		}
		if (source == maxDelaySpinner) {
			if (valuesOk(unitValue, "max")){			
			}
		}
		if (source == meanDelaySpinner) {
			newChangeUndo();
			delay1.getDistribution().setMean(
					(double) meanDelaySpinner.getSelection()*unitValue);
			scenarioEditor.setDirty();			
		}
		if (source == varianceDelaySpinner) {
			newChangeUndo();
			delay1.getDistribution().setVariance(
					(double) varianceDelaySpinner.getSelection()*unitValue);
			scenarioEditor.setDirty();
		}

		if (source == distributionChoiceCombo) {
			newChangeUndo();
			delay1.getDistribution().setType(
					IDistribution.DistributionType
							.valueOf(distributionChoiceCombo.getText()));
			scenarioEditor.setDirty();
		}
		if (source == deleteButton) {
												
			ExecuteDeletion.deleteCommand(delay1);
			

		}
		if (((double)delay1.getDistribution().getMin()/(double)unitValue)
				>= 1000000000||
				 ((double)delay1.getDistribution().getMax()/(double)unitValue)
				>= 1000000000||
				 ((double)delay1.getDistribution().getMean()/(double)unitValue)
				>= 1000000000||
				 ((double)delay1.getDistribution().getMin()/(double)unitValue)
				>= 1000000000){
					warningLabel2.setText("Values are to big to be shown in this unit. Please choose another unit.");
					warningLabel2.setBackground(new Color( null, 255,255,0));					
			} else {
				warningLabel2.setText("");
				warningLabel2.setBackground(new Color( null, 255,255,255));
			}

	}
	
    /**
     * This method checks if entered values are accepted.
     * @param unitValue
     * @param minOrMax
     * @return
     */
	public boolean valuesOk(int unitValue, String minOrMax){
		boolean ok = true;
		if (minDelaySpinner.getSelection()
			> maxDelaySpinner.getSelection()){			
			//minDelaySpinner.setBackground(new Color( null, 255,0,0));
			ok = false;
		} else {
			//minDelaySpinner.setBackground(new Color( null, 255,255,255));
			if (minOrMax.equals("min")){
				newChangeUndo();
				delay1.getDistribution().setMin(
						(double) minDelaySpinner.getSelection()*unitValue);
				scenarioEditor.setDirty();
			}
		}
		if (maxDelaySpinner.getSelection()
				< minDelaySpinner.getSelection()){
				//maxDelaySpinner.setBackground(new Color( null, 255,0,0));
				ok = false;
		} else {
			// maxDelaySpinner.setBackground(new Color( null, 255,255,255));
			if (minOrMax.equals("max")){
				newChangeUndo();
				delay1.getDistribution().setMax(
						(double) maxDelaySpinner.getSelection()*unitValue);
				scenarioEditor.setDirty();
			}
		}										
		
		if (ok == false){
			warningLabel.setText("Minimal value has to be <= Maximal value! Values haven't been adopted!"
			// + " Current min.: " + delay1.getDistribution().getMin()/(double)unitValue +
			//"   Current max.: " + delay1.getDistribution().getMax()/(double)unitValue
					);
			warningLabel.setBackground(new Color( null, 255,0,0));
			minDelaySpinner.setSelection((int)(delay1.getDistribution().getMin()/(double)unitValue));
			maxDelaySpinner.setSelection((int)(delay1.getDistribution().getMax()/(double)unitValue));
		} else {
			warningLabel.setBackground(new Color( null, 255,255,255));
			warningLabel.setText("");			
		}
		return true;
	}

	@Override
	public boolean aboutToClose() {
		return true;
	}

	@Override
	public void applyAllChanges() {
	}

    /**
     * This method visualizes the given inputs
     */
	@Override
	public void createForm(Composite parent, ScenarioEditor editor) {
		super.createForm(parent, editor);

		form.getBody().setLayout(new GridLayout(2, false));
		form.setText("Delay Information:");
		
		unitString = "(in seconds):";

		minDelayLabel = toolkit.createLabel(form.getBody(), "Minimum delay "+ unitString,
				SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		minDelayLabel.setLayoutData(gd);

		minDelaySpinner = new Spinner(form.getBody(), SWT.BORDER);
		minDelaySpinner.setMaximum(spinnerHigh);
		minDelaySpinner.addSelectionListener(this);		

		maxDelayLabel = toolkit.createLabel(form.getBody(), "Maximum delay "+ unitString,
				SWT.NONE);
		maxDelayLabel.setLayoutData(gd);

		maxDelaySpinner = new Spinner(form.getBody(), SWT.BORDER);
		maxDelaySpinner.setMaximum(spinnerHigh);
		maxDelaySpinner.addSelectionListener(this);

		distributionLabel = toolkit.createLabel(form.getBody(),
				"Distribution : ", SWT.NONE);
		distributionLabel.setLayoutData(gd);

		GridData gd2 = new GridData();
		distributionChoiceCombo = new Combo(form.getBody(), SWT.BORDER);
		distributionChoiceCombo.addSelectionListener(this);
		distributionChoiceCombo.setLayoutData(gd2);
		gd2.widthHint = 87;
		
		meanDelayLabel = toolkit.createLabel(form.getBody(), "Mean value " + unitString,
				SWT.NONE);
		meanDelayLabel.setLayoutData(gd);

		meanDelaySpinner = new Spinner(form.getBody(), SWT.BORDER);
		meanDelaySpinner.setMaximum(spinnerHigh);
		meanDelaySpinner.addSelectionListener(this);

		varianceDelayLabel = toolkit.createLabel(form.getBody(),
				"Variance " + unitString, SWT.NONE);
		varianceDelayLabel.setLayoutData(gd);

		varianceDelaySpinner = new Spinner(form.getBody(), SWT.BORDER);
		varianceDelaySpinner.setMaximum(spinnerHigh);
		varianceDelaySpinner.addSelectionListener(this);
		
		unitChangerLabel = toolkit.createLabel(form.getBody(),
				"Delay is shown in:", SWT.NONE);
		
		unitChanger = new Combo(form.getBody(), SWT.BORDER);
		unitChanger.add("milliseconds");
		unitChanger.add("seconds");
		unitChanger.add("minutes");
		unitChanger.add("hours");
		unitChanger.select(1);
		unitChanger.addSelectionListener(this);
		
		GridData gd3 = new GridData(GridData.FILL_HORIZONTAL);
		gd3.horizontalSpan = 2;		
		warningLabel = toolkit.createLabel(form.getBody(),
				"", SWT.NONE);
		warningLabel.setLayoutData(gd3);
		
		GridData sameWidth = new GridData();
		sameWidth.widthHint = 150;
		
		deleteButton = toolkit.createButton(form.getBody(),
				"Delete this Delay", SWT.NONE);
		deleteButton.setLayoutData(sameWidth);
		deleteButton.setBackground(new Color( null, 240,10,40));
		deleteButton.addSelectionListener(this);

		GridData gd4 = new GridData(GridData.FILL_HORIZONTAL);
		gd4.horizontalSpan = 2;
		warningLabel2 = toolkit.createLabel(form.getBody(),
				"", SWT.NONE);
		warningLabel2.setLayoutData(gd4);
		
		//dynamic help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(minDelaySpinner, "de.peerthing.workbench.scenario_nodes");
		minDelaySpinner.setToolTipText("Choose a value for the minimum time of delay.");			
		PlatformUI.getWorkbench().getHelpSystem().setHelp(maxDelaySpinner, "de.peerthing.workbench.scenario_nodes");
		maxDelaySpinner.setToolTipText("Choose a value for the maximum time of delay.");					
		PlatformUI.getWorkbench().getHelpSystem().setHelp(meanDelaySpinner, "de.peerthing.workbench.scenario_nodes");
		meanDelaySpinner.setToolTipText("Choose the mean value for time of delay.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(varianceDelaySpinner, "de.peerthing.workbench.scenario_nodes");
		varianceDelaySpinner.setToolTipText("Choose the variance for the delay.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(distributionChoiceCombo, "de.peerthing.workbench.scenario_nodes");
		distributionChoiceCombo.setToolTipText("Select uniform or normal type of distribution.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(unitChanger, "de.peerthing.workbench.scenario_nodes");
		unitChanger.setToolTipText("Here the unit of the time can be changed.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(deleteButton, "de.peerthing.workbench.scenario_nodes");
		deleteButton.setToolTipText("Pushing will delete this delay.");
	}
    
    /**
     * This method is used every time a change is made. The changes are assumed and the form
     * is refreshed.
     */
	@Override
	public void update(IScenarioObject object) {
		currentObject = object;
		delay1 = (IDelay) object;
		
		warningLabel.setBackground(new Color( null, 255,255,255));
		warningLabel.setText("");
		
		int unitValue = 1;
		if (unitChanger.getSelectionIndex() ==1){
			unitValue = 1000;
		}
		if (unitChanger.getSelectionIndex() ==2){
			unitValue = 60000;
		}
		if (unitChanger.getSelectionIndex() ==3){
			unitValue = 3600000;
		}
		
		if (((double)delay1.getDistribution().getMin()/(double)unitValue)
				>= 1000000000||
				 ((double)delay1.getDistribution().getMax()/(double)unitValue)
				>= 1000000000||
				 ((double)delay1.getDistribution().getMean()/(double)unitValue)
				>= 1000000000||
				 ((double)delay1.getDistribution().getMin()/(double)unitValue)
				>= 1000000000){
					warningLabel2.setText("Values are to big to be shown in this unit. Please choose another unit.");
					warningLabel2.setBackground(new Color( null, 255,255,0));					
			} else {
				warningLabel2.setText("");
				warningLabel2.setBackground(new Color( null, 255,255,255));
		}
		
		
		minDelaySpinner.setSelection((int) ((double)delay1.getDistribution().getMin()/(double)unitValue));
		maxDelaySpinner.setSelection((int) ((double)delay1.getDistribution().getMax()/(double)unitValue));
		meanDelaySpinner.setSelection((int) ((double)delay1.getDistribution().getMean()/(double)unitValue));
		varianceDelaySpinner.setSelection((int) ((double)delay1.getDistribution()
				.getVariance()/(double)unitValue));
		if (distributionChoiceCombo.getItemCount() == 0) {
			distributionInit();
		}
		
		for (int i = 0; i < distributionChoiceCombo.getItemCount(); i++) {
			if (delay1.getDistribution().getType().name().equals(
					distributionChoiceCombo.getItem(i))) {
				distributionChoiceCombo.select(i);
			}
		}
	}
}
