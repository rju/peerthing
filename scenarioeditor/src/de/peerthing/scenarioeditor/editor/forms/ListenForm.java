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
import de.peerthing.scenarioeditor.model.IListen;
import de.peerthing.scenarioeditor.model.IDistribution;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.IDistribution.DistributionType;
import de.peerthing.scenarioeditor.model.impl.ExecuteDeletion;
import de.peerthing.scenarioeditor.model.impl.Listen;
import de.peerthing.scenarioeditor.model.impl.ScenarioUndo;
import de.peerthing.scenarioeditor.model.impl.UndoOperationValues;
import de.peerthing.scenarioeditor.editor.ScenarioEditor;

/**
 * This class manages a form with which you can manipulate the data of a Listen.
 *
 * @author Patrik, Petra (dynamic help)
 */

public class ListenForm extends AbstractScenarioEditorForm {

    /**
     * A label for "Minimum time" a listen takes place
     */
	Label minListenLabel;
    
    /**
     * A label for "Maximum time" a listen takes place
     */
	Label maxListenLabel;

    /**
     * A label for "Mean value" time that a listen takes place
     */
	Label meanListenLabel;

    /**
     * A label for the "Variance"
     */
	Label varianceListenLabel;
    
    /**
     * A label for the "Distribution"
     */
	Label distributionLabel;

    /**
     * A minimum listen time spinner (0-maxint)
     */
	Spinner minListenSpinner;
    
    /**
     * A maximum listen time spinner (0-maxint)
     */
	Spinner maxListenSpinner;

    /**
     * A mean listen time spinner (0-maxint)
     */
	Spinner meanListenSpinner;

    /**
     * A variance spinner (0-maxint)
     */
	Spinner varianceListenSpinner;

    /**
     * A combobox to select distribution type
     */
	Combo distributionChoiceCombo;

    /**
     * A new listen
     */
	IListen listen1;

    /**
     * A button to delete current listen
     */
	Button deleteButton;
	
    /**
     * A label for "even" (listen is waiting for)
     */
	Label eventLabel;
	
    /**
     * A textfield to enter action where listen is waiting for
     */
	Text eventText;
	
    /**
     * A label for unit change
     */
	Label unitChangerLabel;
	
    /**
     * A warning label
     */
	Label warningLabel;
	
    /**
     * A warning label
     */
	Label warningLabel2;
	
    
    /**
     * A combobox to select time units
     */
	Combo unitChanger;
	
    /**
     * The selected time unit as string
     */
	String unitString;
	
	Label emptyLineLabel;
	
    /**
     * Variable to select if events are changed or not
     */
	boolean noModifyEvents = false;

	/**
	 * Constructor for creating viewable form
	 *
	 * @param container
	 */
	public ListenForm() {
	}
	
	public void newChangeUndo(){
    	ScenarioEditorPlugin.getDefault().getUndoList().add(
				new ScenarioUndo
				(listen1, new Listen(listen1), UndoOperationValues.valueChanged));
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
	 * The actions of the user are handled here. The Data the user changed will
	 * be saved in the listen-Object.
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
			
			minListenSpinner.setSelection((int)((double)listen1.
					getDistribution().getMin()/(double)unitValue));
			maxListenSpinner.setSelection((int)((double)listen1.
					getDistribution().getMax()/(double)unitValue));
			meanListenSpinner.setSelection((int)((double)listen1.
					getDistribution().getMean()/(double)unitValue));
			varianceListenSpinner.setSelection((int)((double)listen1.
					getDistribution().getVariance()/(double)unitValue));
			minListenLabel.setText("Minimal listening time "+ unitString);
			maxListenLabel.setText("Maximal listening time "+ unitString);
			meanListenLabel.setText("Mean value " + unitString);
			varianceListenLabel.setText("Variance " + unitString);
		}
		
		

		if (source == minListenSpinner) {			
			if (valuesOk(unitValue, "min")){
			}			
		}
		if (source == maxListenSpinner) {
			if (valuesOk(unitValue, "max")){
			}			
		}
		
		
		if (source == meanListenSpinner) {
			newChangeUndo();
			listen1.getDistribution().setMean(
					(double) meanListenSpinner.getSelection()*unitValue);
			scenarioEditor.setDirty();			
		}
		if (source == varianceListenSpinner) {
			newChangeUndo();
			listen1.getDistribution().setVariance(
					(double) varianceListenSpinner.getSelection()*unitValue);
			scenarioEditor.setDirty();
		}
		
		if (source == distributionChoiceCombo) {
			newChangeUndo();
			listen1.getDistribution().setType(
					IDistribution.DistributionType
							.valueOf(distributionChoiceCombo.getText()));
			scenarioEditor.setDirty();
		}
		if (source == deleteButton) {
			
			ExecuteDeletion.deleteCommand(listen1);

		}
		
		if (((double)listen1.getDistribution().getMin()/(double)unitValue)
			>= 1000000000||
			 ((double)listen1.getDistribution().getMax()/(double)unitValue)
			>= 1000000000||
			 ((double)listen1.getDistribution().getMean()/(double)unitValue)
			>= 1000000000||
			 ((double)listen1.getDistribution().getVariance()/(double)unitValue)
			>= 1000000000){
				warningLabel2.setText("Values are to big to be shown in this unit. Please choose another unit.");
				warningLabel2.setBackground(new Color( null, 255,255,0));					
		} else {
			warningLabel2.setText("");
			warningLabel2.setBackground(new Color( null, 255,255,255));
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
	 * Texts if min and max values are illegal
	 */
	public boolean valuesOk(int unitValue, String minOrMax){
		boolean ok = true;
		if (minListenSpinner.getSelection()
			> maxListenSpinner.getSelection()){			
			//minListenSpinner.setBackground(new Color( null, 255,0,0));
			ok = false;
		} else {
			//minListenSpinner.setBackground(new Color( null, 255,255,255));
			if (minOrMax.equals("min")){
				newChangeUndo();
				listen1.getDistribution().setMin(
						(double) minListenSpinner.getSelection()*unitValue);
				scenarioEditor.setDirty();
			}
		}
		if (maxListenSpinner.getSelection()
				< minListenSpinner.getSelection()){
				//maxListenSpinner.setBackground(new Color( null, 255,0,0));
				ok = false;
		} else {
			if (minOrMax.equals("max")){
			//maxListenSpinner.setBackground(new Color( null, 255,255,255));
				newChangeUndo();
				listen1.getDistribution().setMax(
						(double) maxListenSpinner.getSelection()*unitValue);
				scenarioEditor.setDirty();
			}
		}										
		
		if (ok == false){
			warningLabel.setText("Minimum value has to be <= Maximum value! Values haven't been adopted!"
			// +" Current min.: " + listen1.getDistribution().getMin()/(double)unitValue +
			//"   Current max.: " + listen1.getDistribution().getMax()/(double)unitValue
					);
			minListenSpinner.setSelection((int)(listen1.getDistribution().getMin()/(double)unitValue));
			maxListenSpinner.setSelection((int)(listen1.getDistribution().getMax()/(double)unitValue));
			warningLabel.setBackground(new Color( null, 255,0,0));
		} else {
			warningLabel.setBackground(new Color( null, 255,255,255));
			warningLabel.setText("");			
		}
		return true;
	}


    /**
     * This method visualizes the given inputs
     */
	@Override
	public void createForm(Composite parent, ScenarioEditor editor) {
		super.createForm(parent, editor);

		form.getBody().setLayout(new GridLayout(2, false));
		form.setText("Listen Information:");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		
		unitString = " (in seconds):";
		
		eventLabel = toolkit.createLabel(form.getBody(), "Event:",
				SWT.NONE);
		
		eventText = toolkit.createText(form.getBody(), "", SWT.NONE);
		eventText.setLayoutData(gd);
		
		eventText.addSelectionListener(new SelectionAdapter() {
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });
        eventText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
	            if (!noModifyEvents){
	            	newChangeUndo();
	            	listen1.setEvent(eventText.getText());
	            	tree.update(listen1);	            		            	
		            setDirty();
	            }
            }
        });
		
		
		minListenLabel = toolkit.createLabel(form.getBody(), "Minimum time" + unitString ,
				SWT.NONE);
		
		minListenLabel.setLayoutData(gd);

		minListenSpinner = new Spinner(form.getBody(), SWT.BORDER);
		minListenSpinner.setMaximum(999999999);
		minListenSpinner.addSelectionListener(this);		

		maxListenLabel = toolkit.createLabel(form.getBody(), "Maximum time" + unitString,
				SWT.NONE);
		maxListenLabel.setLayoutData(gd);

		maxListenSpinner = new Spinner(form.getBody(), SWT.BORDER);
		maxListenSpinner.setMaximum(999999999);
		maxListenSpinner.addSelectionListener(this);
		
		distributionLabel = toolkit.createLabel(form.getBody(),
				"Distribution : ", SWT.NONE);
		distributionLabel.setLayoutData(gd);

		GridData gd2 = new GridData();
		distributionChoiceCombo = new Combo(form.getBody(), SWT.BORDER);
		distributionChoiceCombo.addSelectionListener(this);
		distributionChoiceCombo.setLayoutData(gd2);
		gd2.widthHint = 87;

		meanListenLabel = toolkit.createLabel(form.getBody(), "Mean value "+ unitString,
				SWT.NONE);
		meanListenLabel.setLayoutData(gd);

		meanListenSpinner = new Spinner(form.getBody(), SWT.BORDER);
		meanListenSpinner.setMaximum(999999999);
		meanListenSpinner.addSelectionListener(this);		

		varianceListenLabel = toolkit.createLabel(form.getBody(),
				"Variance " + unitString, SWT.NONE);
		varianceListenLabel.setLayoutData(gd);

		varianceListenSpinner = new Spinner(form.getBody(), SWT.BORDER);
		varianceListenSpinner.setMaximum(999999999);
		varianceListenSpinner.addSelectionListener(this);
		
		GridData gd4 = new GridData(GridData.FILL_HORIZONTAL);
		gd4.horizontalSpan = 2;						
		
		unitChangerLabel = toolkit.createLabel(form.getBody(),
				"Listening time is shown in:", SWT.NONE);
		
		unitChanger = new Combo(form.getBody(), SWT.BORDER);
		unitChanger.add("milliseconds");
		unitChanger.add("seconds");
		unitChanger.add("minutes");
		unitChanger.add("hours");
		unitChanger.select(1);
		unitChanger.addSelectionListener(this);
		
		warningLabel = toolkit.createLabel(form.getBody(),
				"", SWT.NONE);
		warningLabel.setLayoutData(gd4);
		
		warningLabel2 = toolkit.createLabel(form.getBody(),
				"", SWT.NONE);
		warningLabel2.setLayoutData(gd4);
		
		//final Image image = new Image(parent.getDisplay(), "delete.png");
		GridData sameWidth = new GridData();
		sameWidth.widthHint = 150;		
		
		deleteButton = toolkit.createButton(form.getBody(),
				"Delete this listen", SWT.NONE);
		deleteButton.setLayoutData(sameWidth);
		deleteButton.setBackground(new Color( null, 240,10,40));
		deleteButton.addSelectionListener(this);

		//deleteButton.setImage(image);		
		
		
		
		//dynamic help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(minListenSpinner, "de.peerthing.workbench.scenario_nodes");
		minListenSpinner.setToolTipText("Choose the minimum time to listen.");			
		PlatformUI.getWorkbench().getHelpSystem().setHelp(maxListenSpinner, "de.peerthing.workbench.scenario_nodes");
		maxListenSpinner.setToolTipText("Choose the maximum time to listen.");			
		PlatformUI.getWorkbench().getHelpSystem().setHelp(meanListenSpinner, "de.peerthing.workbench.scenario_nodes");
		meanListenSpinner.setToolTipText("Choose the mean value of time to listen.");	
		PlatformUI.getWorkbench().getHelpSystem().setHelp(varianceListenSpinner, "de.peerthing.workbench.scenario_nodes");
		varianceListenSpinner.setToolTipText("Choose the variance of time to listen.");	
		PlatformUI.getWorkbench().getHelpSystem().setHelp(distributionChoiceCombo, "de.peerthing.workbench.scenario_nodes");
		distributionChoiceCombo.setToolTipText("Choose uniform or normal type of distribution.");	
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(eventText, "de.peerthing.workbench.scenario_nodes");
		eventText.setToolTipText("Choose a name for the event to listen.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(unitChanger, "de.peerthing.workbench.scenario_nodes");
		unitChanger.setToolTipText("Here you can choose the unit of time to be shown.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(deleteButton, "de.peerthing.workbench.scenario_nodes");
		deleteButton.setToolTipText("Pushing will delete this listen.");		

	}

    /**
     * This method is used every time a change is made. The changes are assumed and the form
     * is refreshed.
     */
	@Override
	public void update(IScenarioObject object) {
		currentObject = object;
		listen1 = (IListen) object;
				
		noModifyEvents = true;
        if (listen1.getEvent()!=null){
        	eventText.setText(listen1.getEvent());
        } else {
        	eventText.setText("");
        }
        noModifyEvents = false;
        
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
		
		if (((double)listen1.getDistribution().getMin()/(double)unitValue)
				>= 1000000000||
				 ((double)listen1.getDistribution().getMax()/(double)unitValue)
				>= 1000000000||
				 ((double)listen1.getDistribution().getMean()/(double)unitValue)
				>= 1000000000||
				 ((double)listen1.getDistribution().getVariance()/(double)unitValue)
				>= 1000000000){
					warningLabel2.setText("Values are to big to be shown in this unit. Please choose another unit.");
					warningLabel2.setBackground(new Color( null, 255,255,0));					
			} else {
				warningLabel2.setText("");
				warningLabel2.setBackground(new Color( null, 255,255,255));
			}
		
		minListenSpinner.setSelection((int) (listen1.getDistribution().getMin()/(double)unitValue));
		maxListenSpinner.setSelection((int) (listen1.getDistribution().getMax()/(double)unitValue));
		meanListenSpinner.setSelection((int) (listen1.getDistribution().getMean()/(double)unitValue));
		varianceListenSpinner.setSelection((int) (listen1.getDistribution()
				.getVariance()/(double)unitValue));
		if (distributionChoiceCombo.getItemCount() == 0) {
			distributionInit();
		}

		for (int i = 0; i < distributionChoiceCombo.getItemCount(); i++) {
			if (listen1.getDistribution().getType().name().equals(
					distributionChoiceCombo.getItem(i))) {
				distributionChoiceCombo.select(i);
			}
		}
	}
}
