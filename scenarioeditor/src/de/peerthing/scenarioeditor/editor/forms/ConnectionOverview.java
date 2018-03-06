/**
 *
 */
package de.peerthing.scenarioeditor.editor.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.PlatformUI;

import de.peerthing.scenarioeditor.model.IConnectionCategory;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.impl.ExecuteAddition;
import de.peerthing.scenarioeditor.model.impl.NameTest;
import de.peerthing.scenarioeditor.model.ScenarioFactory;
import de.peerthing.scenarioeditor.editor.ScenarioEditor;

/**
 *
 * This class manages a form with which you can manipulate
 * the data of a Connection
 *
 * @author Hendrik Angenendt, Patrik, Petra (dynamic help)
 * @reviewer Petra 24.03.2006
 */
public class ConnectionOverview extends AbstractScenarioEditorForm {
    
    /**
     * A label for "name"
     */
	Label nameLabel;
    
    /**
     * A textfield to enter connection name
     */
	Text nameText;
    
    /**
     * A button to add a new connection
     */
	Button addNewConnectionButton;        
    
    /**
     * A shell
     */
	Shell shell;
    
    /** 
     * A shell
     */
	Shell dialog;
    
    /**
     * A label for wrong name
     */
	Label wrongNameLabel;
    
    /**
     * The current scenario
     */
	IScenario scenario;

    
    /**
     * The default constructor
     *
     */
	public ConnectionOverview() {
    }

    /**
     * The actions of the user are handled here. The Data
     * the user changed will be saved in the scenario-Object.
     */
	public void widgetSelected(SelectionEvent e) {
		Object source = e.getSource();
		if (source == addNewConnectionButton){
			if (isNameOk(nameText.getText())){				
											
				IConnectionCategory c1 = ScenarioFactory.createConnectionCategory(scenario);
				c1.setName(nameText.getText());
				
				ExecuteAddition.addConnectionCategory(c1);				
				nameText.setText("");							
			} 
		}
	}

    /**
     * This method checks if name for a new connection category is allready in use
     * @param newName
     * @return
     */
	public boolean isNameOk(String newName){
    	for (IConnectionCategory con : scenario.getConnectionCategories()) {
   			if(con.getName().equals(newName)){
   				while(true){
					MessageBox cancelBox = new MessageBox(shell, SWT.OK
							|SWT.ICON_WARNING );
					cancelBox.setText("Illegal Name");
					cancelBox.setMessage("The Connection Category \"" + newName + "\" " +
							"already exists. Please choose another name.");
					if (cancelBox.open()== SWT.OK){
						break;
					}
				}
   				return false;
   			}
   		}
    	return NameTest.isNameOk(form.getShell(), newName);
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

	@Override
	public boolean aboutToClose() {
		return true;
	}

	@Override
	public void applyAllChanges() {
	}

	/**
	 * All elements of the form (buttons, labels,... are created
	 * and placed here.
	 */
	@Override
	public void createForm(Composite parent, ScenarioEditor editor) {
		super.createForm(parent, editor);

		shell = new Shell(parent.getDisplay());
		form.getBody().setLayout(new GridLayout(3, false));
		form.setText("Connection Overview:");
		nameLabel= toolkit.createLabel(form.getBody(),"", SWT.NONE);
		nameLabel.setText("Name:");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		nameText = toolkit.createText(form.getBody(),"", SWT.NONE);
		nameText.setLayoutData(gd);
		
		nameText.addSelectionListener(new SelectionAdapter() {
            public void widgetDefaultSelected(SelectionEvent e) {
            	if (isNameOk(nameText.getText())){
            		if (isNameOk(nameText.getText())){
        				
        				IConnectionCategory c1 = ScenarioFactory.createConnectionCategory(scenario);
        				c1.setName(nameText.getText());
        				
        				ExecuteAddition.addConnectionCategory(c1);
        				        				        				        				
        				nameText.setText("");        				
        			}
    			}
            }
        });
		
		addNewConnectionButton = toolkit.createButton(form.getBody(),"Add Connection", SWT.NONE);
		addNewConnectionButton.addSelectionListener(this);
		
		//dynamic help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(nameText, "de.peerthing.workbench.scenario_connections");
		nameText.setToolTipText("Enter a name for this connection");		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(addNewConnectionButton, "de.peerthing.workbench.scenario_connections");
		addNewConnectionButton.setToolTipText("Push to add a new connection.");	
	
	}
}
