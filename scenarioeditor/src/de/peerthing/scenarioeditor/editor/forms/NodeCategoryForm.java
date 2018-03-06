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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import de.peerthing.scenarioeditor.ScenarioEditorPlugin;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.impl.ExecuteDeletion;
import de.peerthing.scenarioeditor.model.impl.NameTest;
import de.peerthing.scenarioeditor.model.impl.NodeCategory;
import de.peerthing.scenarioeditor.model.impl.ScenarioUndo;
import de.peerthing.scenarioeditor.model.impl.UndoOperationValues;
import de.peerthing.scenarioeditor.editor.ScenarioEditor;

/**
 * This class manages a form with which you can manipulate the data of a node
 * category
 *
 * @author Patrik, Petra (dynamic help)
 * @reviewer Petra 24.03.2006
 */
public class NodeCategoryForm extends AbstractScenarioEditorForm {
	
    /**
     * A label for "name" (of node)
     */
    Label renameNodeLabel;
    
    /**
     * A textfield to enter node name
     */
	Text renameNodeText;
    
    /**
     * A label for "number of nodes" 
     */
	Label numberOfNodesLabel;

    /**
     * A label for "number of nodes value" 
     */
	Label numberOfNodesValue;

    /**
     * A new node category
     */
	INodeCategory nodeCategory;

    /**
     * A label for "system behaviour node"
     */
	Label architecturNodeLabel;

    /**
     * A textfield to enter name of used architecture node
     */
	Text architecturNodeText;

    /**
     * A combobox to choose behaviour
     */
	Combo callCombo;

    /**
     * A label for "primary behaviour"
     */
	Label primaryBehaviourLabel;

    /**
     * The current scenario
     */
	IScenario scenario;
	
    /**
     * A button to delete current node category
     */
	Button deleteButton;

    /**
     * Variable is events are modified or not
     */
	boolean noModifyEvents = false;
	    
	Label emptyLineLabel;
	    
	Label warningLabel;
	
	Label emptyLineLabel2;
	
	Label warningLabel2;

	/**
	 * Gui is initialised in the constructor
	 *
	 * @param container
	 */
	public NodeCategoryForm() {
	}
	
    /**
     * This method adds an operation, made in this form, to the undoable list
     */
	public void newChangeUndo(){
    	ScenarioEditorPlugin.getDefault().getUndoList().add(
				new ScenarioUndo
				(nodeCategory, new NodeCategory(nodeCategory), UndoOperationValues.valueChanged));
    }

    /**
     * This method checks if  if name for a new node category is 
     * allready in use
     * @param newName
     * @return
     */
	public boolean nameOk(String newName) {
		for (INodeCategory nodeCat2 : nodeCategory.getScenario()
				.getNodeCategories()) {
			if ((!nodeCat2.equals(nodeCategory))
					&& nodeCat2.getName().equals(newName)) {
				renameNodeText.setBackground(new Color(null, 255, 0, 0));
				warningLabel.setText("The name you wanted to chose is already spoken for. The name has not been adopted. Please chose another name.");
				warningLabel.setBackground(new Color(null, 255, 255, 0));
				return false;
			}
			warningLabel.setText("");
			warningLabel.setBackground(new Color(null, 255, 255, 255));
			renameNodeText.setBackground(new Color(null, 255, 255, 255));
		}
		if (!NameTest.isNameOk(newName)){
			warningLabel.setText("The name you chose has not been adopted. The name has to contain a sign (other than space) and less than 256 signs.");
			warningLabel.setBackground(new Color(null, 255, 255, 0));
			renameNodeText.setBackground(new Color(null, 255, 0, 0));
			return false;
		}
		return true;
	}
	
    /**
     * This method checks if architecture name is useable 
     * @param newName
     * @return
     */
	public boolean isArchitecturNameOk(String newName) {
		
		architecturNodeText.setBackground(new Color(null, 255, 255, 255));		
		warningLabel2.setText("");
		warningLabel2.setBackground(new Color(null, 255, 255, 255));
		
		if (!NameTest.isNameOk(newName)) {
			architecturNodeText.setBackground(new Color(null, 255, 0, 0));
			warningLabel2.setBackground(new Color(null, 255, 255, 0));
			warningLabel2.setText("The name has not been adopted. A system behaviour has to contain a sign (other than space) and less than 256 signs.");
			return false;
		}
		return true;
	}

	/**
	 * Inserts the Behaviours of the Scenario into the combo box so the user can
	 * pick a primary behaviour for a nodeCategory
	 */
	public void comboInit() {
		callCombo.removeAll();
		for (int j = 0; j < nodeCategory.getBehaviours().size(); j++) {
			callCombo.add(nodeCategory.getBehaviours().get(j).getName());
		}
		callCombo.select(0);
		if (nodeCategory.getPrimaryBehaviour() != null) {
			for (int i = 0; i < callCombo.getItemCount(); i++) {
				if (nodeCategory.getPrimaryBehaviour().getName().equals(
						callCombo.getItem(i))) {
					callCombo.select(i);
				}
			}
		}
	}


    /**
     * This method sets the number of nodes of the current node category
     * @param number
     */
	public void setNumberOfNodes(int number) {
		numberOfNodesValue.setText(String.valueOf(number));
	}

    /**
     * This method sets the node label of the current node category
     * @param nodeType
     */
	public void setName(String name) {
		renameNodeLabel.setText(name);
	}
    
    /**
     * This method returns the node label of the current node category
     * @return
     */
	public String getName() {
		return renameNodeLabel.getText();
	}

	/**
	 * The actions of the user are handled here. The Data the user changed will
	 * be saved in the nodeCategory-Object.
	 */
	public void widgetSelected(SelectionEvent e) {
		
		Object source = e.getSource();
		
		if (source == callCombo){		
			String behaviour = callCombo.getItem(callCombo.getSelectionIndex());
			for (int i = 0; i < scenario.getNodeCategories().size(); i++) {
				for (int j = 0; j < scenario.getNodeCategories().get(i)
						.getBehaviours().size(); j++) {
					if (behaviour.equals(scenario.getNodeCategories().get(i)
							.getBehaviours().get(j).getName())) {
						newChangeUndo();
						nodeCategory.setPrimaryBehaviour(scenario
								.getNodeCategories().get(i).getBehaviours().get(j));
						scenarioEditor.setDirty();
					}
				}
			}
		}
		
		if (source == deleteButton){
			
			ExecuteDeletion.deleteNodeCategory(nodeCategory);
			
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
     * This method visualises the given inputs
     */
	@Override
	public void createForm(Composite parent, ScenarioEditor editor) {
		super.createForm(parent, editor);

		form.getBody().setLayout(new GridLayout(2, false));
		form.setText("Scenario Node Information:");
		renameNodeLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		renameNodeLabel.setText("Name:");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		renameNodeText = toolkit.createText(form.getBody(), "", SWT.NONE);
		renameNodeText.setLayoutData(gd);
		
		GridData gd6 = new GridData(GridData.FILL_HORIZONTAL);
		gd6.horizontalSpan = 2;
		
		warningLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		warningLabel.setLayoutData(gd6);
		
		architecturNodeLabel = toolkit.createLabel(form.getBody(),
				"System behaviour node:", SWT.NONE);
		architecturNodeText = toolkit.createText(form.getBody(), "", SWT.NONE);
		architecturNodeText.setLayoutData(gd);

		renameNodeText.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		renameNodeText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (nameOk(renameNodeText.getText()) && !noModifyEvents) {
					newChangeUndo();
					nodeCategory.setName(renameNodeText.getText());
					tree.update(nodeCategory);
					setDirty();
				}
			}
		});
		
		
		architecturNodeText.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		architecturNodeText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (isArchitecturNameOk(architecturNodeText.getText()) && !noModifyEvents) {
					newChangeUndo();
					nodeCategory.setNodeType(architecturNodeText.getText());
					tree.update(nodeCategory);
					setDirty();
				}
			}
		});
		
		warningLabel2 = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		warningLabel2.setLayoutData(gd6);
		
		numberOfNodesLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		numberOfNodesLabel.setText("Number of nodes:");
		GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
		numberOfNodesValue = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		numberOfNodesValue.setLayoutData(gd2);
		primaryBehaviourLabel = toolkit.createLabel(form.getBody(),
				"Primary behaviour:", SWT.NONE);
		GridData gd4 = new GridData();
		callCombo = new Combo(form.getBody(), SWT.NONE);
		gd4.widthHint = 150;
		callCombo.setLayoutData(gd4);
		callCombo.addSelectionListener(this);
		GridData sameWidth = new GridData();
		sameWidth.widthHint = 150;				
		
		emptyLineLabel2 = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		emptyLineLabel2.setLayoutData(gd6);
		
		deleteButton = toolkit.createButton(form.getBody(), "Delete this node",

				SWT.NONE);
		deleteButton.setLayoutData(sameWidth);
		deleteButton.setBackground(new Color( null, 240,10,40));
		deleteButton.addSelectionListener(this);
				
		emptyLineLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		emptyLineLabel.setLayoutData(gd6);				
		
		//dynamic help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(renameNodeText, "de.peerthing.workbench.scenario_nodes");
		renameNodeText.setToolTipText("Enter a a node name");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(architecturNodeText, "de.peerthing.workbench.scenario_nodes");
		architecturNodeText.setToolTipText("Enter a name of used architecture node.");	
		PlatformUI.getWorkbench().getHelpSystem().setHelp(callCombo, "de.peerthing.workbench.scenario_nodes");
		callCombo.setToolTipText("A combobox to choose behaviour.");	
		PlatformUI.getWorkbench().getHelpSystem().setHelp(deleteButton, "de.peerthing.workbench.scenario_nodes");
		deleteButton.setToolTipText("Push this button to delete this node.");			
	}

    /**
     * This method is used every time a change is made. The changes are assumed and the form
     * will be refreshed.
     */
	@Override
	public void update(IScenarioObject object) {
		currentObject = object;
		this.scenario = object.getScenario();
		nodeCategory = (INodeCategory) object;

		noModifyEvents = true;
		comboInit();
		int number = 0;
		for (int i = 0; i < nodeCategory.getConnections().size(); i++) {
			number = number
					+ (nodeCategory.getConnections().get(i).getNumberOfNodes());
		}
		numberOfNodesValue.setText(String.valueOf(number));
		renameNodeText.setText(nodeCategory.getName());
		architecturNodeText.setText(nodeCategory.getNodeType());
		noModifyEvents = false;
	}

}
