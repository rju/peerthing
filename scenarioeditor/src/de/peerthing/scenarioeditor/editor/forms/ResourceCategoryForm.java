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
import de.peerthing.scenarioeditor.model.IDistribution;
import de.peerthing.scenarioeditor.model.IResourceCategory;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.IDistribution.DistributionType;
import de.peerthing.scenarioeditor.model.impl.NameTest;
import de.peerthing.scenarioeditor.model.impl.ResourceCategory;
import de.peerthing.scenarioeditor.model.impl.ScenarioUndo;
import de.peerthing.scenarioeditor.model.impl.UndoOperationValues;
import de.peerthing.scenarioeditor.editor.ScenarioEditor;
import de.peerthing.scenarioeditor.model.impl.ExecuteDeletion;

/**
 * This class manages a form with which you can manipulate
 * the data of a resource category
 *
 * @author Patrik, Petra (dynamic help)
 * @reviewer Petra 24.03.2006
 */
public class ResourceCategoryForm extends AbstractScenarioEditorForm {
	Label renameLabel;
	Text renameText;
	Label emptyLabel2;
	Label diversityLabel;
	Spinner diversitySpinner;
	Label emptyLabel1;
	Label popularityLabel;
	Spinner popularitySpinner;
	IResourceCategory resourceCategory;
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
	Button deleteButton;
    IScenario scenario;
    String sizeUnit = "(in kb):";
    boolean noModifyEvents;    
    Label unitChangerLabel;	
	Combo unitChanger;	
	Label warningLabel;
	Label warningLabel2;
	
	Label emptyLineLabel3;
	Label warningLabel3;

	/**
	 * checks if the handed name is ok for the resource category 
	 */
	public boolean nameOk(String newName){
    	for (IResourceCategory res : resourceCategory.getScenario().getResourceCategories()) {
   			if((!res.equals(resourceCategory)) && res.getName().equals(newName)){
   				renameText.setBackground(new Color( null, 255,0,0));
   				warningLabel3.setText("The name you wanted to chose is already spoken for. The name has not been adopted. Please chose another name.");
				warningLabel3.setBackground(new Color(null, 255, 255, 0));
   				return false;
   			}
   			renameText.setBackground(new Color( null, 255,255,255));
   			warningLabel3.setText("");
			warningLabel3.setBackground(new Color(null, 255, 255, 255));
   		}
    	if (!NameTest.isNameOk(newName)){
    		renameText.setBackground(new Color( null, 255, 0,0));
    		warningLabel3.setText("The name you chose has not been adopted. The name has to contain a sign (other than space) and less than 256 signs.");
			warningLabel3.setBackground(new Color(null, 255, 255, 0));
    		return false;
    	}
    	return true;
    }
	
	/**
	 * Adds a entry to the undo list so that it is noted that a resource
	 * category is added to the scenario 
	 */
	public void newChangeUndo(){
    	ScenarioEditorPlugin.getDefault().getUndoList().add(
				new ScenarioUndo
				(resourceCategory, new ResourceCategory(resourceCategory), UndoOperationValues.valueChanged));
    }
	
    /**
     * The types of distribution are inserted into the combo box
     */
	public void distributionInit(){
		for (DistributionType distType : IDistribution.DistributionType.values()) {
			distributionCombo.add(distType.name());
		}
	}

	/**
	 * The actions of the user are handled here. The Data
	 * the user changed will be saved in the
	 * resource-category-Object.
	 */
	public void widgetSelected(SelectionEvent e) {

		Object source = e.getSource();
		
		warningLabel.setBackground(new Color( null, 255,255,255));
		warningLabel.setText("");
		warningLabel2.setBackground(new Color( null, 255,255,255));
		warningLabel2.setText("");
		
		String unitString;		
		int unitValue = 1;		
		unitString = "(in bytes):";
		if (unitChanger.getSelectionIndex() ==1){
			unitString = "(in kilobytes):";
			unitValue = 1024;
		}
		if (unitChanger.getSelectionIndex() ==2){
			unitString = "(in megabytes):";
			unitValue = 1024*1024;
		}
		if (unitChanger.getSelectionIndex() ==3){
			unitString = "(in gigabytes):";
			unitValue = 1024*1024*1024;
		}
		
		if (source == unitChanger){			
			minSpinner.setSelection((int)((double)resourceCategory.
					getSize().getMin()/(double)unitValue));
			maxSpinner.setSelection((int)((double)resourceCategory.
					getSize().getMax()/(double)unitValue));
			meanValueSpinner.setSelection((int)((double)resourceCategory.
					getSize().getMean()/(double)unitValue));
			varianceSpinner.setSelection((int)((double)resourceCategory.
					getSize().getVariance()/(double)unitValue));
			minNumberLabel.setText("Minimal size "+ unitString);
			maxNumberLabel.setText("Maximal size "+ unitString);
			meanValueLabel.setText("Mean value " + unitString);
			varianceLabel.setText("Variance " + unitString);			
		}
		
		if (source == distributionCombo){
			newChangeUndo();			
			resourceCategory.getSize().setType(IDistribution.DistributionType.valueOf(distributionCombo
					.getText()));
			scenarioEditor.setDirty();
		}

        if (source == meanValueSpinner){
        	newChangeUndo();
        	resourceCategory.getSize().setMean((double)meanValueSpinner.getSelection()*unitValue);
        	scenarioEditor.setDirty();
        }        
		if (source == varianceSpinner){
			newChangeUndo();
			resourceCategory.getSize().setVariance((double)varianceSpinner.getSelection()*unitValue);
			scenarioEditor.setDirty();
		}
		
		if (source == diversitySpinner){
			newChangeUndo();
			resourceCategory.setDiversity(diversitySpinner.getSelection());
			scenarioEditor.setDirty();
		}
		if (source == popularitySpinner){
			newChangeUndo();
			resourceCategory.setPopularity(popularitySpinner.getSelection());
			scenarioEditor.setDirty();
		}
		
		if (source == minSpinner){
			if (valuesOk(unitValue, "min")){
			}	
		}
		
		if (source == maxSpinner){
			if (valuesOk(unitValue, "max")){
			}	
		}								

		if (source == deleteButton){
			ExecuteDeletion.deleteResourceCategory(resourceCategory);
			/*for (int i = 0; i< scenario.getResourceCategories().size(); i++){
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
			}*/
		}
		
		if (((double)resourceCategory.getSize().getMin()/(double)unitValue)
			> 999999999||
			 ((double)resourceCategory.getSize().getMax()/(double)unitValue)
			> 999999999||
			 ((double)resourceCategory.getSize().getMean()/(double)unitValue)
			> 999999999||
			 ((double)resourceCategory.getSize().getVariance()/(double)unitValue)
			> 999999999){
				warningLabel2.setText("Values are to big to be shown in this unit. Please choose another unit.");
				warningLabel2.setBackground(new Color( null, 255,255,0));					
		} else {
			//warningLabel.setText("");
			//warningLabel.setBackground(new Color( null, 255,255,255));
		}

	}

	/**
	 * checks if the min and max values are ok or illegal
	 */
	public boolean valuesOk(int unitValue, String minOrMax){
		boolean ok = true;
		if (minSpinner.getSelection()
			> maxSpinner.getSelection()){			
			//minDelaySpinner.setBackground(new Color( null, 255,0,0));
			ok = false;
		} else {
			if (minOrMax.equals("min")){
				newChangeUndo();
				resourceCategory.getSize().setMin(
						(double) minSpinner.getSelection()*unitValue);
				scenarioEditor.setDirty();
			}
		}
		if (maxSpinner.getSelection()
				< minSpinner.getSelection()){
				ok = false;
		} else {
			if (minOrMax.equals("max")){
				newChangeUndo();
				resourceCategory.getSize().setMax(
						(double) maxSpinner.getSelection()*unitValue);
				scenarioEditor.setDirty();
			}
		}										
		
		if (ok == false){
			warningLabel.setText("Minimal value has to be <= Maximal value! Values haven't been adopted!"
			// + " Current min.: " + delay1.getDistribution().getMin()/(double)unitValue +
			//"   Current max.: " + delay1.getDistribution().getMax()/(double)unitValue
					);
			warningLabel.setBackground(new Color( null, 255,0,0));
			minSpinner.setSelection((int)(resourceCategory.getSize().getMin()/(double)unitValue));
			maxSpinner.setSelection((int)(resourceCategory.getSize().getMax()/(double)unitValue));
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
	 * All elements of the form are created and placed here.
	 */
	@Override
	public void createForm(Composite parent, ScenarioEditor editor) {
		super.createForm(parent, editor);

		form.getBody().setLayout(new GridLayout(2, false));
		form.setText("Resource Category Information:");

		renameLabel= toolkit.createLabel(form.getBody(),"Name:", SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		renameText = toolkit.createText(form.getBody(),"", SWT.NONE);
		renameText.setLayoutData(gd);
		
		GridData gd23 = new GridData(GridData.FILL_HORIZONTAL);
		gd23.horizontalSpan = 2;
		
		warningLabel3 = toolkit.createLabel(form.getBody(),
				"", SWT.NONE);
		warningLabel3.setLayoutData(gd23);		

		renameText.addSelectionListener(new SelectionAdapter() {
			   public void widgetDefaultSelected(SelectionEvent e) {
			   }
			});
			renameText.addModifyListener(new ModifyListener() {
			   public void modifyText(ModifyEvent e) {
				   if (nameOk(renameText.getText())&& !noModifyEvents){
					   newChangeUndo();
					   resourceCategory.setName(renameText.getText());
					   ScenarioEditorPlugin.getDefault().getNavigationTree().update(resourceCategory);
					   setDirty();
			   		}
			   }
		});
		GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);

		//emptyLabel2 = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		//emptyLabel2.setLayoutData(gd2);

		diversityLabel = toolkit.createLabel(form.getBody(),"Diversity:", SWT.NONE);

		diversityLabel.setLayoutData(gd2);

		diversitySpinner = new Spinner(form.getBody(), SWT.BORDER);
		diversitySpinner.setMaximum(99999);
		diversitySpinner.addSelectionListener(this);

		//emptyLabel1 = toolkit.createLabel(form.getBody(),"", SWT.NONE);

		popularityLabel = toolkit.createLabel(form.getBody(),"Popularity (in relationchip to other resourcecategories): ", SWT.NONE);
		GridData gd3 = new GridData(GridData.FILL_HORIZONTAL);
		popularityLabel.setLayoutData(gd3);

		popularitySpinner = new Spinner(form.getBody(), SWT.BORDER);
		popularitySpinner.setMaximum(99999);		
		popularitySpinner.addSelectionListener(this);

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
		minSpinner.setMaximum(999999999);

		GridData gd15 = new GridData(GridData.FILL_HORIZONTAL);
		//gd15.horizontalSpan = 2;
		maxNumberLabel = toolkit.createLabel(form.getBody(),"", SWT.NONE);
		maxNumberLabel.setLayoutData(gd15);

		GridData gd16 = new GridData();
		maxSpinner = new Spinner(form.getBody(), SWT.BORDER);
		maxSpinner.setLayoutData(gd16);
		maxSpinner.addSelectionListener(this);
		maxSpinner.setMaximum(999999999);

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
		meanValueSpinner.setMaximum(999999999);

		GridData gd21 = new GridData(GridData.FILL_HORIZONTAL);
		varianceLabel = toolkit.createLabel(form.getBody(),"", SWT.NONE);
		//gd21.horizontalSpan = 2;
		varianceLabel.setLayoutData(gd21);

		GridData gd22 = new GridData();
		varianceSpinner = new Spinner(form.getBody(), SWT.BORDER);
		varianceSpinner.setLayoutData(gd22);
		varianceSpinner.addSelectionListener(this);
		varianceSpinner.setMaximum(999999999);
							
		
		unitChangerLabel = toolkit.createLabel(form.getBody(),
				"Size of files are shown in:", SWT.NONE);		
		unitChanger = new Combo(form.getBody(), SWT.BORDER);
		unitChanger.add("bytes");
		unitChanger.add("kilobytes");
		unitChanger.add("megabytes");
		unitChanger.add("gigabytes");
		unitChanger.select(1);
		unitChanger.addSelectionListener(this);
		
		warningLabel = toolkit.createLabel(form.getBody(),
				"", SWT.NONE);
		warningLabel.setLayoutData(gd23);
		
		//emptyLineLabel3 = toolkit.createLabel(form.getBody(),"", SWT.NONE);
		//emptyLineLabel3.setLayoutData(gd11b);

		warningLabel2 = toolkit.createLabel(form.getBody(),
				"", SWT.NONE);
		warningLabel2.setLayoutData(gd23);				
				
		
		GridData sameWidth = new GridData();
		sameWidth.widthHint = 250;

		deleteButton = toolkit.createButton(form.getBody(),"Delete this Resource Category", SWT.NONE);
		deleteButton.setLayoutData(sameWidth);
		deleteButton.setBackground(new Color( null, 240,10,40));
		deleteButton.addSelectionListener(this);
		
		
		
		//dynamic help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(renameLabel, "de.peerthing.workbench.scenario_resources");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(renameText, "de.peerthing.workbench.scenario_resources");
		renameText.setToolTipText("Here you can rename the resource category..");	
		PlatformUI.getWorkbench().getHelpSystem().setHelp(diversitySpinner, "de.peerthing.workbench.scenario_resources");
		diversitySpinner.setToolTipText("Choose a value for the diversity.");	
		PlatformUI.getWorkbench().getHelpSystem().setHelp(popularitySpinner, "de.peerthing.workbench.scenario_resources");
		popularitySpinner.setToolTipText("Choose a value for the popularity.");	
		PlatformUI.getWorkbench().getHelpSystem().setHelp(minSpinner, "de.peerthing.workbench.scenario_resources");
		minSpinner.setToolTipText("Choose a minimal size of a file.");	
		PlatformUI.getWorkbench().getHelpSystem().setHelp(maxSpinner, "de.peerthing.workbench.scenario_resources");
		maxSpinner.setToolTipText("Choose a macimal size of a file.");	
		PlatformUI.getWorkbench().getHelpSystem().setHelp(distributionCombo, "de.peerthing.workbench.scenario_resources");
		distributionCombo.setToolTipText("Choose a uniform or a normal distribution.");	
		PlatformUI.getWorkbench().getHelpSystem().setHelp(meanValueSpinner, "de.peerthing.workbench.scenario_resources");
		meanValueSpinner.setToolTipText("Choose a value for the mean value.");	
		PlatformUI.getWorkbench().getHelpSystem().setHelp(varianceSpinner, "de.peerthing.workbench.scenario_resources");
		varianceSpinner.setToolTipText("Choose a value for the variance.");	
		PlatformUI.getWorkbench().getHelpSystem().setHelp(unitChanger, "de.peerthing.workbench.scenario_resources");
		unitChanger.setToolTipText("Here you can choose in which size the values should be shown.");			
		
	}


	/**
     * This method is used every time a change is made. The changes are assumed and the form
     * is refreshed.
     */	
	@Override
	public void update(IScenarioObject object) {
		currentObject = object;
		this.scenario = object.getScenario();
		resourceCategory = (IResourceCategory) object;			
		
		if (distributionCombo.getItemCount()==0){
			distributionInit();
		}
		for (int i= 0; i< distributionCombo.getItemCount(); i++){
			if (resourceCategory.getSize().getType().name().equals
					(distributionCombo.getItem(i))){
				distributionCombo.select(i);
			}
		}

		noModifyEvents = true;
		renameText.setText(resourceCategory.getName());
		noModifyEvents = false;
		diversitySpinner.setSelection(resourceCategory.getDiversity());
		popularitySpinner.setSelection(resourceCategory.getPopularity());

		int unitValue = 1;
		if (unitChanger.getSelectionIndex() ==1){
			unitValue = 1024;
		}
		if (unitChanger.getSelectionIndex() ==2){
			unitValue = 1024*1024;
		}
		if (unitChanger.getSelectionIndex() ==3){
			unitValue = 1024*1024*1024;
		}
			
		warningLabel2.setBackground(new Color( null, 255,255,255));
		warningLabel2.setText("");
		
		if (((double)resourceCategory.getSize().getMin()/(double)unitValue)
				> 999999999||
				 ((double)resourceCategory.getSize().getMax()/(double)unitValue)
				> 999999999||
				 ((double)resourceCategory.getSize().getMean()/(double)unitValue)
				> 999999999||
				 ((double)resourceCategory.getSize().getVariance()/(double)unitValue)
				> 999999999){
					warningLabel2.setText("Values are to big to be shown in this unit. Please choose another unit.");
					warningLabel2.setBackground(new Color( null, 255,255,0));					
		}

		
		minSpinner.setSelection((int)(resourceCategory.getSize().getMin()/(double) unitValue));
		maxSpinner.setSelection((int)(resourceCategory.getSize().getMax()/(double) unitValue));
		distributionLabel.setText("Distribution:");
		minNumberLabel.setText("Minimal size of a file " + sizeUnit);
		maxNumberLabel.setText("Maximal size of a file " + sizeUnit);
		typLabel.setText("Type of distribution");

		meanValueLabel.setText("Mean Value:");
		meanValueSpinner.setSelection((int)(resourceCategory.getSize().getMean()/(double) unitValue));
		varianceLabel.setText("Variance:");
		varianceSpinner.setSelection((int)(resourceCategory.getSize().getVariance()/(double) unitValue));
	}
}
