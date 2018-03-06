package de.peerthing.scenarioeditor.editor.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import de.peerthing.scenarioeditor.ScenarioEditorPlugin;
import de.peerthing.scenarioeditor.model.IResourceCategory;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.ScenarioFactory;
import de.peerthing.scenarioeditor.model.impl.ExecuteAddition;
import de.peerthing.scenarioeditor.model.impl.NameTest;
import de.peerthing.scenarioeditor.model.impl.ScenarioUndo;
import de.peerthing.scenarioeditor.model.impl.UndoOperationValues;
import de.peerthing.scenarioeditor.editor.ScenarioEditor;

/**
 * This class manages a form with which you can manipulate
 * the Data of resource categories 
 *
 * @author lethe, Patrik, Petra (dynamic help)
 * @reviewer Petra 24.03.2006
 */
public class ResourceOverview extends AbstractScenarioEditorForm {
	Label nameLabel;
	Text nameText;
	Button addButton;
    IScenario scenario;

	/**
	 * The actions of the user are handled here. The Data
	 * the user changed will be saved in the
	 * scenario-Object.
	 */
	public void widgetSelected(SelectionEvent e) {
		Object source = e.getSource();
		if (source == addButton){
			if (isNameOk(nameText.getText())){
				IResourceCategory r1 = ScenarioFactory.createResourceCategory(scenario);
				r1.setName(nameText.getText());
								
				ExecuteAddition.addResourceCategory(scenario, r1);
				/*scenario.getResourceCategories().add(r1);
				ScenarioEditorPlugin.getDefault().getUndoList().add(new ScenarioUndo(scenario, r1, UndoOperationValues.addWasDone));
				ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(scenario.getResourceCategories());*/				
				
				nameText.setText("");
				scenarioEditor.setDirty();
			}
		}
	}

	/**
	 * checks if the handed name is ok for a resource category.	
	 */
	public boolean isNameOk(String newName){
    	for (IResourceCategory resourceCategory : scenario.getResourceCategories()) {
   			if(resourceCategory.getName().equals(newName)){
   				while(true){
					MessageBox cancelBox = new MessageBox(form.getShell(), SWT.OK
							|SWT.ICON_WARNING );
					cancelBox.setText("Illegal name");
					cancelBox.setMessage("The resource category \"" + newName + "\" " +
							"already exists. Please chose another name.");
					if (cancelBox.open()== SWT.OK){
						break;
					}
				}
   				return false;
   			}
   		}
    	return NameTest.isNameOk(form.getShell(), newName);
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

		form.getBody().setLayout(new GridLayout(3, false));
		form.setText("Resource Overview:");
		nameLabel= toolkit.createLabel(form.getBody(),"", SWT.NONE);
		nameLabel.setText("Name:");

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		nameText = toolkit.createText(form.getBody(),"", SWT.NONE);
		nameText.setLayoutData(gd);
		
		nameText.addSelectionListener(new SelectionAdapter() {
            public void widgetDefaultSelected(SelectionEvent e) {
            	if (isNameOk(nameText.getText())){
    				IResourceCategory r1 = ScenarioFactory.createResourceCategory(scenario);
    				r1.setName(nameText.getText());
    				scenario.getResourceCategories().add(r1);				
    				ScenarioEditorPlugin.getDefault().getUndoList().add(new ScenarioUndo(scenario, r1, UndoOperationValues.addWasDone));
    				ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(scenario.getResourceCategories());
    				nameText.setText("");
    				scenarioEditor.setDirty();
    			}
            }
        });		
		
		addButton = toolkit.createButton(form.getBody(),"Add Resource", SWT.NONE);
		addButton.addSelectionListener(this);
		
		//dynamic help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(nameLabel, "de.peerthing.workbench.scenario_resources");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(nameText, "de.peerthing.workbench.scenario_resources");
		nameText.setToolTipText("Enter a name for the new resource.");	
		PlatformUI.getWorkbench().getHelpSystem().setHelp(addButton, "de.peerthing.workbench.scenario_resources");
		addButton.setToolTipText("Push this button to add a new resource.");			
	}

	/**
     * This method is used every time a change is made. The changes are assumed and the form
     * is refreshed.
     */
	@Override
	public void update(IScenarioObject object) {
		currentObject = object;
		this.scenario = object.getScenario();
	}

}
