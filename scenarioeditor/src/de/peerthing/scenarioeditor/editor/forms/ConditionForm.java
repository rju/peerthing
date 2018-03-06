package de.peerthing.scenarioeditor.editor.forms;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;

import de.peerthing.scenarioeditor.model.IScenarioCondition;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.impl.ExecuteAddition;
import de.peerthing.scenarioeditor.model.impl.ExecuteDeletion;
import de.peerthing.scenarioeditor.editor.ScenarioEditor;

/**
 * This class manages a form with which you can manipulate
 * the data of a condition
 *
 * @author Patrik, Petra (dynamic help)
 * @reviewer Petra 24.03.2006
 */
public class ConditionForm extends AbstractScenarioEditorForm {
    
    /**
     * A new condition
     */
	IScenarioCondition condition;
    
    /**
     * An empty label
     */
	Label emptyLineLabel;
    
    /**
     * A button to create a new case
     */
	Button createCaseButton;
    
    /**
     * A empty label
     */
	Label emptyLabel1;
    
    /**
     * A button to delete current condition
     */
	Button deleteButton;

	/**
	 * Gui is initialised in the constructor
	 * @param container
	 */
	public ConditionForm () {
	}

	/**
	 * The actions of the user are handled here. The Data
	 * the user changed will be saved in the condition-Object.
	 */
	public void widgetSelected(SelectionEvent e) {
		Object source = e.getSource();
        
		if (source == createCaseButton){
			
			ExecuteAddition.addCase(condition);
			
		}

		if (source == deleteButton){
			
			ExecuteDeletion.deleteCommand(condition);

		}

	}

    /**
     * This method is used every time a change is made. The changes are assumed and the form
     * will be refreshed.
     */
	@Override
	public void update(IScenarioObject object) {
		currentObject = object;
		condition = (IScenarioCondition) object;
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

		form.getBody().setLayout(new GridLayout(1, false));
		form.setText("Condition:");

		GridData sameWidth = new GridData();
		sameWidth.widthHint = 150;
		
		createCaseButton = toolkit.createButton(form.getBody(), "Create Case", SWT.NONE);
		createCaseButton.setLayoutData(sameWidth);
		createCaseButton.addSelectionListener(this);

		emptyLabel1 = toolkit.createLabel(form.getBody(), "", SWT.NONE);

		deleteButton = toolkit.createButton(form.getBody(), "Delete This Condition", SWT.NONE);
		deleteButton.setLayoutData(sameWidth);
		deleteButton.setBackground(new Color( null, 240,10,40));
		deleteButton.addSelectionListener(this);
		
		//dynamic help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(createCaseButton, "de.peerthing.workbench.scenario_nodes");
		createCaseButton.setToolTipText("This creates a new case for this condition.");		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(deleteButton, "de.peerthing.workbench.scenario_nodes");
		deleteButton.setToolTipText("By pushing this loop will be deleted.");	
		
	}
}
