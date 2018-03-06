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
import de.peerthing.scenarioeditor.model.IListWithParent;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.IUserBehaviour;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.ScenarioFactory;
import de.peerthing.scenarioeditor.model.impl.ExecuteAddition;
import de.peerthing.scenarioeditor.model.impl.NameTest;
import de.peerthing.scenarioeditor.model.impl.ScenarioUndo;
import de.peerthing.scenarioeditor.model.impl.UndoOperationValues;
import de.peerthing.scenarioeditor.editor.ScenarioEditor;

/**
 * This class manages a form with which you can manipulate the data of
 * behaviours
 *
 * @author Hendrik Angenendt, Patrik, Petra (dynamic help)
 * @reviewer Petra 24.03.2006
 */
public class NodeBehaviourOverview extends AbstractScenarioEditorForm {

    /**
     * A new node category
     */
	INodeCategory nodeCategory;

    /**
     * A label for "name" (of the node behaviour)
     */
	Label nameLabel;

    /**
     * A textfield to enter behaviour name or rename old one
     */
	Text newBehaviourText;

    /**
     * A button to create a new node behaviour
     */
	Button createBehaviourButton;

	/**
	 * Gui is initialised in the constructor
	 *
	 * @param container
	 */
	public NodeBehaviourOverview() {
	}

	/**
	 * The actions of the user are handled here. The Data the user changed will
	 * be saved in the node-Object.
	 */
	public void widgetSelected(SelectionEvent e) {
		Object source = e.getSource();
		if (source == createBehaviourButton) {
			if (isNameOk(newBehaviourText.getText())) {
				IUserBehaviour newBehaviour = ScenarioFactory
						.createBehaviour(nodeCategory);
				newBehaviour.setName(newBehaviourText.getText());
				
				ExecuteAddition.addBehaviour(newBehaviour, nodeCategory);
								
				newBehaviourText.setText("");				
			}
		}
	}

    /**
     * This method checks if new node behaviour name is allready in use
     * @param newName
     * @return
     */
	public boolean isNameOk(String newName) {
		for (IUserBehaviour userBehaviour : nodeCategory.getBehaviours()) {
			if (userBehaviour.getName().equals(newName)) {
				while (true) {
					MessageBox cancelBox = new MessageBox(form.getShell(), SWT.OK
							| SWT.ICON_WARNING);
					cancelBox.setText("Illegal Name");
					cancelBox.setMessage("The User Behaviour \"" + newName
							+ "\" "
							+ "already exists. Please choose another name.");
					if (cancelBox.open() == SWT.OK) {
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
     * This method visualises the given inputs
     */
	@Override
	public void createForm(Composite parent, ScenarioEditor editor) {
		super.createForm(parent, editor);

		form.getBody().setLayout(new GridLayout(3, false));
		form.setText("Node Behaviour Information Window:");

		nameLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		nameLabel.setText("Name:");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		newBehaviourText = toolkit.createText(form.getBody(), "", SWT.NONE);
		newBehaviourText.setLayoutData(gd);
		
		newBehaviourText.addSelectionListener(new SelectionAdapter() {
            public void widgetDefaultSelected(SelectionEvent e) {
            	if (isNameOk(newBehaviourText.getText())) {
    				IUserBehaviour newBehaviour = ScenarioFactory
    						.createBehaviour(nodeCategory);

    				ScenarioEditorPlugin.getDefault().getUndoList().add(
    						new ScenarioUndo
    						(nodeCategory, newBehaviour, UndoOperationValues.addWasDone));
    				
    				newBehaviour.setName(newBehaviourText.getText());
    				nodeCategory.getBehaviours().add(newBehaviour);
    				ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(
    						nodeCategory.getBehaviours());
    				newBehaviourText.setText("");
    				scenarioEditor.setDirty();
    			}
            }
        });
		
		createBehaviourButton = toolkit.createButton(form.getBody(),
				"Add Behaviour", SWT.NONE);
		createBehaviourButton.addSelectionListener(this);
        
		//dynamic help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(newBehaviourText, "de.peerthing.workbench.scenario_nodes");
		newBehaviourText.setToolTipText("Enter a behaviour name or rename an old one");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(createBehaviourButton, "de.peerthing.workbench.scenario_nodes");
		createBehaviourButton.setToolTipText("Push the button to create a new node behaviour.");		

	}

    /**
     * This method is used every time a change is made. The changes are assumed and the form
     * will be refreshed.
     */
	@Override
	public void update(IScenarioObject object) {
		currentObject = object;
		this.nodeCategory = (INodeCategory) ((IListWithParent<?>) object).getParent();
	}
}