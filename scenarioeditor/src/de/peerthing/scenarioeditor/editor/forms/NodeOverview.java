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
//dynamic help
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;

import de.peerthing.scenarioeditor.ScenarioEditorPlugin;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.ScenarioFactory;
import de.peerthing.scenarioeditor.model.impl.ExecuteAddition;
import de.peerthing.scenarioeditor.model.impl.NameTest;
import de.peerthing.scenarioeditor.model.impl.ScenarioUndo;
import de.peerthing.scenarioeditor.model.impl.UndoOperationValues;
import de.peerthing.scenarioeditor.editor.ScenarioEditor;

/**
 *  This class manages a form with which you can add new nodes
 *
 * @author Patrik, Petra (dynamic help)
 * @reviewer Petra 24.03.2006
 */
public class NodeOverview extends AbstractScenarioEditorForm {
	Label nameLabel;
	Text text;
	Button newNodeButton;
   
	IScenario scenario;

	/**
	 * The actions of the user are handled here. The Data
	 * the user changed will be saved in the
	 * scenario-Object.
	 */
	public void widgetSelected(SelectionEvent e) {
		if (isNameOk(text.getText())){
			INodeCategory nc1 = ScenarioFactory.createNodeCategory(scenario);
			nc1.setName(text.getText());
			
			ExecuteAddition.addNodeCategory(nc1);
						
			text.setText("");
			form.redraw();			
		}
     
	}

	/**
	 * Checks if the name of the node category is illegal.  
	 */
	public boolean isNameOk(String newName){
    	for (INodeCategory nodeCategory : scenario.getNodeCategories()) {
   			if(nodeCategory.getName().equals(newName)){
   				while(true){
					MessageBox cancelBox = new MessageBox(form.getShell(), SWT.OK
							|SWT.ICON_WARNING );
					cancelBox.setText("Illegal Name");
					cancelBox.setMessage("The Node Category \"" + newName + "\" " +
							"already exists. Please choose another name.");
					if (cancelBox.open()== SWT.OK || cancelBox.open() == SWT.ABORT){
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
		form.setText("Node Overview:");

		nameLabel= toolkit.createLabel(form.getBody(),"Name:", SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		text = toolkit.createText(form.getBody(), "", SWT.NONE);
		text.setLayoutData(gd);
		
		text.addSelectionListener(new SelectionAdapter() {
            public void widgetDefaultSelected(SelectionEvent e) {
            	if (isNameOk(text.getText())){
        			INodeCategory nc1 = ScenarioFactory.createNodeCategory(scenario);
        			nc1.setName(text.getText());
        			nc1.getBehaviours().add(ScenarioFactory.createBehaviour(nc1));
        			nc1.setPrimaryBehaviour(nc1.getBehaviours().get(0));
        			ScenarioEditorPlugin.getDefault().getUndoList().add(new ScenarioUndo(scenario, nc1, UndoOperationValues.addWasDone));
        			scenario.getNodeCategories().add(nc1);
        			ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(scenario.getNodeCategories());
        			text.setText("");
        			form.redraw();
        			scenarioEditor.setDirty();
        		}
            }
        });

		newNodeButton = toolkit.createButton(form.getBody(), "Add Node Category", SWT.NONE);
		newNodeButton.addSelectionListener(this);
        
        //dynamic help 
        IWorkbench workbench =  PlatformUI.getWorkbench();
        IWorkbenchHelpSystem helpsystem = workbench.getHelpSystem();
        
        helpsystem.setHelp( newNodeButton, "de.peerthing.workbench.scenario_nodes");
        // a bit shorter
        PlatformUI.getWorkbench().getHelpSystem().setHelp(text, "de.peerthing.workbench.scenario_nodes");
        //helpsystem.setHelp( text, "de.peerthing.workbench.scenario_create");
        
        newNodeButton.setToolTipText("Creates a new node. Do not forget to enter a name.");
        nameLabel.setToolTipText("This is only a label.");           
        text.setToolTipText("Enter a name for the new node.");
		
	}

	/**
     * This method is used every time a change is made. The changes are assumed and the form
     * is refreshed.
     */
	@Override
	public void update(IScenarioObject object) {
		currentObject = object;
		scenario = object.getScenario();
	}

}
