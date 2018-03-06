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
import de.peerthing.scenarioeditor.model.ICallUserBehaviour;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IUserBehaviour;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.impl.CallUserBehaviour;
import de.peerthing.scenarioeditor.model.impl.ExecuteDeletion;
import de.peerthing.scenarioeditor.model.impl.ScenarioUndo;
import de.peerthing.scenarioeditor.model.impl.UndoOperationValues;
import de.peerthing.scenarioeditor.editor.ScenarioEditor;

/**
 * This class manages a form with which you can manipulate
 * the data of a CallBehaviour
 *
 * @author Patrik, Petra (dynamic help)
 * @reviewer Petra 24.03.2006
 */
public class CallUserBehaviourForm extends AbstractScenarioEditorForm {
    
    /**
     * A label for name of behaviour
     */
	Label nameLabel;
    
    /**
     * A combo box which allows to choose from different items included in
     */
	Combo callCombo;
    
    /**
     * A label for probability 
     */
	Label probabilityLabel;
    
    /**
     * A probability spinner (range 0-100)
     */
	Spinner probabilitySpinner;
	
	//Label isStartTaskLabel;
	
	Button isStartTaskCheckBox;
    
    /**
     * Initializing a new callBehaviour
     */
	ICallUserBehaviour callBehaviour;
    
	Label emptyLabel;
	
    /**
     * A button for deleting a behaviour
     */
	Button deleteButton;
	
	Label emptyLineLabel;
    
    /**
     * The current scenario
     */
    IScenario scenario;      
     
	/**
     * The default constructor
	 * Gui is initialised in the constructor
	 */
	public CallUserBehaviourForm () {

	}
    
    /**
     * This method adds an operation, made in this form, to the undoable list
     *
     */
    public void newChangeUndo(){
        ScenarioEditorPlugin.getDefault().getUndoList().add(
                new ScenarioUndo
                (callBehaviour, new CallUserBehaviour((CallUserBehaviour)callBehaviour), UndoOperationValues.valueChanged));
    }

    /**
     * This method searches all behaviours of the scenario and inserts them into
     * the combo box
     */
	public void comboInit(){
		callCombo.removeAll();
				
		for (IUserBehaviour behaviour : callBehaviour.getBehaviour().getNodeCategory().getBehaviours()){
				callCombo.add(behaviour.getName());			
		}
		callCombo.select(0);
		if (callBehaviour.getBehaviour()!=null){
			for (int i= 0; i< callCombo.getItemCount(); i++){
				if (callBehaviour.getBehaviour().getName().equals(callCombo.getItem(i))){
					callCombo.select(i);
				}
			}
		}
	}

	/**
	 * The actions of the user are handled here. The Data
	 * the user changed will be saved in the
	 * callBehaviour-Object.
	 */
	public void widgetSelected(SelectionEvent e) {
		Object source = e.getSource();
		if (source == deleteButton){
			
			ExecuteDeletion.deleteCommand(callBehaviour);
			/*ICommandContainer c1 = callBehaviour.getCommandContainer();
			for (int i = 0; i< c1.getCommands().size(); i++){
				if (c1.getCommands().get(i).equals(callBehaviour)){
					INodeConnection savesPosition = 
						new NodeConnection(ProvidesPositionOfCommand.providesPosition(callBehaviour));
					ScenarioEditorPlugin.getDefault().getUndoList().add(
						new ScenarioUndo
						(callBehaviour, savesPosition, UndoOperationValues.deleteWasDone));
					c1.getCommands().remove(i);
					scenarioEditor.setDirty();
					scenarioEditor.objectDeleted(callBehaviour);					
					ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(c1);
					ScenarioEditorPlugin.getDefault().getNavigationTree().select(c1);
				}
			}*/

		}
		if (source == callCombo){
			newChangeUndo();
			String behaviourName = callCombo.getItem(callCombo.getSelectionIndex());
			for (int i = 0; i< scenario.getNodeCategories().size(); i++){
				for (int j = 0; j< scenario.getNodeCategories().get(i).
				getBehaviours().size(); j++){
					if (behaviourName.equals(scenario.getNodeCategories().get(i).
							getBehaviours().get(j).getName())){
						if (!behaviourName.equals(callBehaviour.getBehaviour().getName())){
							scenarioEditor.setDirty();
						}
						callBehaviour.setBehaviour(scenario.getNodeCategories().get(i).
								getBehaviours().get(j));
					}
				}
			}
		}
		
		if (source == isStartTaskCheckBox){
			newChangeUndo();
			scenarioEditor.setDirty();
			callBehaviour.setStartTask(isStartTaskCheckBox.getSelection());		
		}
		
		if (source == probabilitySpinner){
			newChangeUndo();
			scenarioEditor.setDirty();
			callBehaviour.setProbability((double)probabilitySpinner.getSelection()/100);			
		}
	}

    /**
     * This method is used every time a change is made. The changes are assumed and the form
     * is refreshed.
     */
	@Override
	public void update(IScenarioObject object) {
		currentObject = object;
		callBehaviour = (ICallUserBehaviour) object;
		this.scenario = callBehaviour.getScenario();
		comboInit();
		probabilitySpinner.setSelection((int)(callBehaviour.getProbability()*100));
		isStartTaskCheckBox.setSelection(callBehaviour.isStartTask());
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
		form.getBody().setLayout(new GridLayout(2, false));

		form.setText("Operation: Call Behaviour");
		nameLabel= toolkit.createLabel(form.getBody(),"Trigger behavior:", SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		nameLabel.setLayoutData(gd);
        		 
		GridData gd4 = new GridData();
		callCombo = new Combo(form.getBody(), SWT.NONE);
		callCombo.setLayoutData(gd4);
		callCombo.addSelectionListener(this);

		gd4.widthHint = 240;
		probabilityLabel = toolkit.createLabel(form.getBody(),"Probability: (in %)", SWT.NONE);
		GridData gd3 = new GridData(GridData.FILL_HORIZONTAL);
		probabilityLabel.setLayoutData(gd3);
		probabilitySpinner = new Spinner(form.getBody(), SWT.BORDER);
		probabilitySpinner.addSelectionListener(this);

		isStartTaskCheckBox = toolkit.createButton(form.getBody(), "Is start task", SWT.CHECK);
		isStartTaskCheckBox.addSelectionListener(this);
		
		GridData gd1 = new GridData();
		gd1.horizontalSpan = 2;
		//emptyLabel = toolkit.createLabel(form.getBody(),"", SWT.NONE);		
		
		GridData sameWidth = new GridData();
		sameWidth.widthHint = 250;
		
		emptyLineLabel = toolkit.createLabel(form.getBody(),"", SWT.NONE);
		emptyLineLabel.setLayoutData(gd1);
		
		deleteButton = toolkit.createButton(form.getBody(), "Delete This CallBehaviour", SWT.NONE);
		deleteButton.setLayoutData(sameWidth);
		deleteButton.setBackground(new Color( null, 240,10,40));
		deleteButton.addSelectionListener(this);		

		//dynamic help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(callCombo, "de.peerthing.workbench.scenario_nodes");
		callCombo.setToolTipText("A combo box which allows to choose from different items included in.");	
		PlatformUI.getWorkbench().getHelpSystem().setHelp(probabilitySpinner, "de.peerthing.workbench.scenario_nodes");
		probabilitySpinner.setToolTipText("Choose a value for the probability in percent.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(isStartTaskCheckBox, "de.peerthing.workbench.scenario_nodes");
		isStartTaskCheckBox.setToolTipText("Check if this call behaviour is the start task.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(deleteButton, "de.peerthing.workbench.scenario_nodes");
		deleteButton.setToolTipText("Pushing will delete this call behaviour.");		
	}

}
