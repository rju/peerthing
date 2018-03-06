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
import de.peerthing.scenarioeditor.editor.ScenarioEditor;
import de.peerthing.scenarioeditor.model.IDistribution;
import de.peerthing.scenarioeditor.model.INodeResource;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.IDistribution.DistributionType;
import de.peerthing.scenarioeditor.model.impl.NodeResource;
import de.peerthing.scenarioeditor.model.impl.ScenarioUndo;
import de.peerthing.scenarioeditor.model.impl.UndoOperationValues;

/**
 * This class manages a form with which you can manipulate
 * the data of a node resource
 *
 * @author Patrik, Petra (dynamic help)
 * @reviewer Petra 24.03.2006
 */
public class NodeResourceForm extends AbstractScenarioEditorForm {

	Label nameLabel;
	Label nameValue;
	Label emptyLabel;
	Label diversityLabel;
	Label diversityValue;
	Label emptyLabel2;
	Label popularityLabel;
	Label popularityValue;
	Label emptyLabel3;
	Label useageLabel;
	Button useageButton;
	Label emptyLineLabel;
	Label distributionLabel;
	Label emptyLineLabel2;
	Label emptyLabel4;
	Label minNumberLabel;
	Spinner minSpinner;
	Label maxNumberLabel;
	Spinner maxSpinner;
	Label typLabel;
	Combo distributionCombo;
	Label meanValueLabel;
	Spinner meanValueSpinner;
	Label varianceLabel;
	Spinner varianceSpinner;
	INodeResource nodeRessource;
	Label warningLabel;

    /**
     * The types of distribution are inserted into the combo box
     */
	public void distributionInit(){
		for (DistributionType distType : IDistribution.DistributionType.values()) {
			distributionCombo.add(distType.name());
		}
	}
	
	public void newChangeUndo(){
    	ScenarioEditorPlugin.getDefault().getUndoList().add(
				new ScenarioUndo
				(nodeRessource, new NodeResource(nodeRessource), UndoOperationValues.valueChanged));
    }

	/**
	 * The actions of the user are handled here. The Data
	 * the user changed will be saved in the
	 * node resource object.
	 */
	public void widgetSelected(SelectionEvent e) {
		
		warningLabel.setText("");
		warningLabel.setBackground(new Color( null, 255,255,255));
		
		Object source = e.getSource();
				
		if (source == minSpinner){
			valuesOk("min");
		}
		if (source == maxSpinner){
			valuesOk("max");
		}
		if (source == meanValueSpinner){
			newChangeUndo();
			nodeRessource.getNumberDistribution().setMean((double)meanValueSpinner.getSelection());
			scenarioEditor.setDirty();
		}
		if (source == varianceSpinner){
			newChangeUndo();
			nodeRessource.getNumberDistribution().setVariance((double)varianceSpinner.getSelection());
			scenarioEditor.setDirty();
		}
		if (source == distributionCombo){
			newChangeUndo();
			nodeRessource.getNumberDistribution().setType(IDistribution.DistributionType.valueOf(distributionCombo.getText()));
			scenarioEditor.setDirty();
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
	 * Checks if min and max values are ok or illegal.
	 */
	public boolean valuesOk(String minOrMax){
		boolean ok = true;
		if (minSpinner.getSelection()
			> maxSpinner.getSelection()){			
			ok = false;
		} else {
			if (minOrMax.equals("min")){
				newChangeUndo();
				nodeRessource.getNumberDistribution().setMin(
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
				nodeRessource.getNumberDistribution().setMax(
						(double) maxSpinner.getSelection());
				scenarioEditor.setDirty();
			}
		}
		if (ok == false){
			warningLabel.setText("Minimal value has to be <= Maximal value! Values havn't been adopted!"					);
			warningLabel.setBackground(new Color( null, 255,0,0));
			minSpinner.setSelection((int)(nodeRessource.getNumberDistribution().getMin()));
			maxSpinner.setSelection((int)(nodeRessource.getNumberDistribution().getMax()));
		} else {
			warningLabel.setBackground(new Color( null, 255,255,255));
			warningLabel.setText("");			
		}
		return true;
	}
	
	/**
	 * All elements of the form are created and placed here.
	 */
	@Override
	public void createForm(Composite parent, ScenarioEditor editor) {
		super.createForm(parent, editor);

		form.getBody().setLayout(new GridLayout(2, false));
		form.setText("Resource of a Scenario Node:");

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		nameLabel = toolkit.createLabel(form.getBody(),"", SWT.NONE);
		nameLabel.setLayoutData(gd);

		GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
		nameValue = toolkit.createLabel(form.getBody(),"", SWT.NONE);
		nameValue.setLayoutData(gd2);

		//emptyLabel = toolkit.createLabel(form.getBody(),"", SWT.NONE);

		GridData gd4 = new GridData(GridData.FILL_HORIZONTAL);
		diversityLabel = toolkit.createLabel(form.getBody(),"", SWT.NONE);
		diversityLabel.setLayoutData(gd4);

		GridData gd5 = new GridData(GridData.FILL_HORIZONTAL);
		diversityValue = toolkit.createLabel(form.getBody(),"", SWT.NONE);
		diversityValue.setLayoutData(gd5);

		//emptyLabel2 = toolkit.createLabel(form.getBody(),"", SWT.NONE);

		GridData gd7 = new GridData(GridData.FILL_HORIZONTAL);
		popularityLabel = toolkit.createLabel(form.getBody(),"", SWT.NONE);
		popularityLabel.setLayoutData(gd7);

		GridData gd8 = new GridData(GridData.FILL_HORIZONTAL);
		popularityValue = toolkit.createLabel(form.getBody(),"", SWT.NONE);
		popularityValue.setLayoutData(gd8);

		//emptyLabel3 = toolkit.createLabel(form.getBody(),"", SWT.NONE);

		GridData gd11b = new GridData(GridData.FILL_HORIZONTAL);
		gd11b.horizontalSpan = 3;
		emptyLineLabel = toolkit.createLabel(form.getBody(),"", SWT.NONE);
		emptyLineLabel.setLayoutData(gd11b);

		GridData gd12 = new GridData(GridData.FILL_HORIZONTAL);
		distributionLabel = toolkit.createLabel(form.getBody(),"", SWT.NONE);
		distributionLabel.setLayoutData(gd12);

		GridData gb12b = new GridData(GridData.FILL_HORIZONTAL);
		gb12b.horizontalSpan = 1;
		emptyLabel4 = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		emptyLabel4.setLayoutData(gb12b);

		GridData gd13 = new GridData(GridData.FILL_HORIZONTAL);
		//gd13.horizontalSpan = 2;
		minNumberLabel = toolkit.createLabel(form.getBody(),"", SWT.NONE);
		minNumberLabel.setLayoutData(gd13);

		GridData gd14 = new GridData();
		minSpinner = new Spinner(form.getBody(), SWT.BORDER);
		minSpinner.setLayoutData(gd14);
		minSpinner.addSelectionListener(this);
		minSpinner.setMaximum(10000000);

		GridData gd15 = new GridData(GridData.FILL_HORIZONTAL);
		//gd15.horizontalSpan = 2;
		maxNumberLabel = toolkit.createLabel(form.getBody(),"", SWT.NONE);
		maxNumberLabel.setLayoutData(gd15);

		GridData gd16 = new GridData();
		maxSpinner = new Spinner(form.getBody(), SWT.BORDER);
		maxSpinner.setLayoutData(gd16);
		maxSpinner.addSelectionListener(this);
		maxSpinner.setMaximum(10000000);

		GridData gd17 = new GridData(GridData.FILL_HORIZONTAL);
		typLabel = toolkit.createLabel(form.getBody(),"", SWT.None);
		//gd17.horizontalSpan = 2;
		typLabel.setLayoutData(gd17);

		GridData gd18 = new GridData();
		distributionCombo = new Combo(form.getBody(),SWT.BORDER);
		distributionCombo.addSelectionListener(this);
		distributionCombo.setLayoutData(gd18);
		gd18.widthHint = 87;

		GridData gd19 = new GridData(GridData.FILL_HORIZONTAL);
		meanValueLabel = toolkit.createLabel(form.getBody(),"", SWT.NONE);
		//gd19.horizontalSpan = 2;
		meanValueLabel.setLayoutData(gd19);

		GridData gd20 = new GridData();
		meanValueSpinner = new Spinner(form.getBody(), SWT.BORDER);
		meanValueSpinner.setLayoutData(gd20);
		meanValueSpinner.addSelectionListener(this);
		meanValueSpinner.setMaximum(10000000);

		GridData gd21 = new GridData(GridData.FILL_HORIZONTAL);
		varianceLabel = toolkit.createLabel(form.getBody(),"", SWT.NONE);
		//gd21.horizontalSpan = 2;
		varianceLabel.setLayoutData(gd21);

		GridData gd22 = new GridData();
		varianceSpinner = new Spinner(form.getBody(), SWT.BORDER);
		varianceSpinner.setLayoutData(gd22);
		varianceSpinner.addSelectionListener(this);
		varianceSpinner.setMaximum(10000000);
		
		GridData gd25 = new GridData(GridData.FILL_HORIZONTAL);
		gd25.horizontalSpan = 2;		
		warningLabel = toolkit.createLabel(form.getBody(),
				"", SWT.NONE);
		warningLabel.setLayoutData(gd25);
		
		//dynamic help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(nameLabel, "de.peerthing.workbench.scenario_nodes");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(minSpinner, "de.peerthing.workbench.scenario_nodes");
		minSpinner.setToolTipText("Sets the minimum number of nodes.");		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(maxSpinner, "de.peerthing.workbench.scenario_nodes");
		maxSpinner.setToolTipText("Sets the maximum number of nodes.");		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(distributionCombo, "de.peerthing.workbench.scenario_nodes");
		distributionCombo.setToolTipText("Sets the type of distribution.");		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(meanValueSpinner, "de.peerthing.workbench.scenario_nodes");
		meanValueSpinner.setToolTipText("Sets the mean value.");				
		PlatformUI.getWorkbench().getHelpSystem().setHelp(varianceSpinner, "de.peerthing.workbench.scenario_nodes");
		varianceSpinner.setToolTipText("Sets the variance.");
	}

	/**
     * This method is used every time a change is made. The changes are assumed and the form
     * is refreshed.
     */
	@Override
	public void update(IScenarioObject object) {
		currentObject = object;
		nodeRessource = (INodeResource) object;
		warningLabel.setText("");
		warningLabel.setBackground(new Color( null, 255,255,255));
		
		if (distributionCombo.getItemCount()==0){
			distributionInit();
		}

		for (int i= 0; i< distributionCombo.getItemCount(); i++){
			if (nodeRessource.getNumberDistribution().getType().name().equals
					(distributionCombo.getItem(i))){
				distributionCombo.select(i);

			}
		}
		nameLabel.setText("Name:");
		nameValue.setText(nodeRessource.getCategory().getName());		
		diversityLabel.setText("Diversity:");
		diversityValue.setText(String.valueOf(nodeRessource.getCategory().getDiversity()));
		popularityLabel.setText("Popularity (in relationchip to other resourcecategories):");
		popularityValue.setText(String.valueOf(nodeRessource.getCategory().getPopularity()));
		minSpinner.setSelection((int)nodeRessource.getNumberDistribution().getMin());
		maxSpinner.setSelection((int)nodeRessource.getNumberDistribution().getMax());
		distributionLabel.setText("Distribution:");
		minNumberLabel.setText("Minimun number of resources per node:");
		maxNumberLabel.setText("Maximum number of resources per node:");
		typLabel.setText("Type of distribution");

		meanValueLabel.setText("Mean value:");
		meanValueSpinner.setSelection((int)nodeRessource.getNumberDistribution().getMean());
		varianceLabel.setText("Variance:");
		varianceSpinner.setSelection((int)nodeRessource.getNumberDistribution().getVariance());

		nameValue.setText(nodeRessource.getCategory().getName());
	}
}
