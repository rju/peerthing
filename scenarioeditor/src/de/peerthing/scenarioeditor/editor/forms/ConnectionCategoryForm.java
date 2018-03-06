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
import de.peerthing.scenarioeditor.model.IConnectionCategory;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.impl.ConnectionCategory;
import de.peerthing.scenarioeditor.model.impl.ExecuteDeletion;
import de.peerthing.scenarioeditor.model.impl.NameTest;
import de.peerthing.scenarioeditor.model.impl.ScenarioUndo;
import de.peerthing.scenarioeditor.model.impl.UndoOperationValues;
import de.peerthing.scenarioeditor.editor.ScenarioEditor;

/**
 * /**
 * This class manages a form with which you can manipulate the data of a
 * ConnectionCategory (ScenarioAction)
 *
 * @author Hendrik Angenendt, Patrik, , Petra (dynamic help)
 * @reviewer Petra 24.03.2006
 */
public class ConnectionCategoryForm extends AbstractScenarioEditorForm {

    /**
     * A label for name
     */
	Label connectionLabel;
    
    /**
     * A textfield to enter connection text
     */
    Text connectionText;
    
    /**
     * A radiobutton to select duplex mode half
     */
    Button duplexButtonHalf;
    
    /**
     * A radiobutton to select duplex mode full
     */
    Button duplexButtonFull;
    
    /**
     * The duplex group
     */
    Composite duplexGroup;
    
    /**
     * A label for duplex
     */
    Label duplexLabel;
    
    /**
     * A label for duplex value
     */
    Label empty;
    
    /**
     * A label for downlink speed
     */
    Label downlinkSpeed;
    
    /**
     * A label for uplink speed
     */
    Label uplinkSpeed;
    
    /**
     * A label for downlink delay
     */
    Label downlinkDelay;
    
    /**
     * A label for uplink delay
     */
    Label uplinkDelay;
    
    /**
     * A spinner to set downlink speed
     */
    Spinner valueDownlinkSpeed;
    
    /**
     * A spinner to set uplink speed
     */
    Spinner valueUplinkSpeed;
    
    /**
     * A spinner to set downlink delay 
     */
    Spinner valueDownlinkDelay;
    
    /**
     * A spinner to set uplink delay
     */
    Spinner valueUplinkDelay;
    
    /**
     * A new connection category
     */
    IConnectionCategory connectionCategory1;
    
    /**
     * A button to delete current connection category
     */
    Button deleteButton;
    
    /**
     * A string to present speed unit 
     */
    String speedUnit = "";
    
    /**
     * A string to present delay unit
     */
    String delayUnit = "";
    
    /**
     * An empty label
     */
    Label emptyLabel;
    
    /**
     * A variable to check if something is modified
     */
    boolean noModifyEvents = false;
    
    
    /**
     * A label to present different units
     */
    Label unitLabel;
    
    /**
     * A combobox to select kind of speed
     */
    Combo unitChangerCombo;
    
    /**
     * A string to represent the kind of unit
     */
    String unitString;   
    
    /**
     * A string to represent the kind of delay
     */
    String delayUnitString;
    
    /**
     * An empty label
     */
    Label emptyLabel2;
    
    /**
     * A label for "delay is shown in"
     */
    Label delayUnitLabel;
    
    /**
     * A combobox to select kind of delay 
     */
    Combo delayUnitChangerCombo;
    
    /** 
     * An empty label
     */
    Label emptyLineLabel;
    
    /**
     * A warning label
     */
    Label warningLabel2;
    
    /** 
     * A warning label
     */
    Label warningLabel;
    
    Label emptyLabel3;
    
    Label emptyLineLabel2;
    
    /**
     * Gui is initialised in the constructor
     * @param container
     */
    public ConnectionCategoryForm() {
    }

    
    /**
     * This method checks if name for a new connection category is allready in use
     * @param newName
     * @return
     */
    public boolean nameOk(String newName){
    	for (IConnectionCategory con : connectionCategory1.getScenario().getConnectionCategories()) {
   			if((!con.equals(connectionCategory1)) && con.getName().equals(newName)){
   				connectionText.setBackground(new Color( null, 255,0,0));
   				warningLabel2.setText("The name you wanted to chose is already spoken for. The name has not been adopted. Please chose another name.");
   				warningLabel2.setBackground(new Color( null, 255,255,0));
   				return false;
   			}
   			warningLabel2.setText("");
   			warningLabel2.setBackground(new Color( null, 255,255,255));
   			connectionText.setBackground(new Color( null, 255,255,255));
   		}
    	if (!NameTest.isNameOk(newName)){
    		warningLabel2.setBackground(new Color( null, 255,255,0));
    		warningLabel2.setText("The name you chose has not been adopted. The name has to contain a sign (other than space) and less than 256 signs.");
    		connectionText.setBackground(new Color( null, 255, 0,0));
    		return false;
    	}
    	return true;
    }

    /**
     * This method sets the text of connection type
     * @param connectionType
     */
    public void setConnectionType(String connectionType) {
        connectionLabel.setText(connectionType);
    }

    /**
     * This method gets the text of a connection type
     * @return
     */
    public String getConnectionType() {
        return connectionLabel.getText();
    }

    /**
     * This method sets the name of a connection
     * @param name
     */
    public void setName(String name) {
        connectionText.setText(name);
    }

    /**
     * This method returns the name of a connection
     * @return
     */
    public String getName() {
        return connectionText.getText();
    }

    /**
     * This method initializes the duplex-type
     * @param duplexType
     */
    public void initDuplexType(IConnectionCategory duplexType) {
        if (duplexType.getDuplex() == IConnectionCategory.DuplexOption.full) {
            duplexButtonFull.setSelection(true);
            duplexButtonHalf.setSelection(false);
        } else {

            duplexButtonHalf.setSelection(true);
            duplexButtonFull.setSelection(false);
        }
    }       
    
    /**
     * This method adds an operation, made in this form, to the undoable list
     */
    public void newChangeUndo(){
    	ScenarioEditorPlugin.getDefault().getUndoList().add(
				new ScenarioUndo
				(connectionCategory1, new ConnectionCategory(connectionCategory1), UndoOperationValues.valueChanged));
    }
    
    /**
     * The actions of the user are handled here. The Data
     * the user changed will be saved in the
     * connectionCategory-Object.
     */
    public void widgetSelected(SelectionEvent e) {
        
    	Object source = e.getSource();
            	
    	warningLabel.setText("");
		warningLabel.setBackground(new Color( null, 255,255,255));    	
    	
        if (source == duplexButtonFull) {
        	newChangeUndo();
        	scenarioEditor.setDirty();
            if (duplexButtonFull.getSelection()) {
                connectionCategory1.setDuplex(IConnectionCategory.DuplexOption.full);
            } else {
                connectionCategory1.setDuplex(IConnectionCategory.DuplexOption.half);
            }
        }
        
        int unitValue = 1;
		unitString = "(in bytes per second):";
		if (unitChangerCombo.getSelectionIndex() ==1){
			unitString = "(in kilobytes per second):";
			unitValue = 1024;
		}
		if (unitChangerCombo.getSelectionIndex() ==2){
			unitString = "(in megabytes per second):";
			unitValue = 1024*1024;
		}
		if (unitChangerCombo.getSelectionIndex() ==3){
			unitString = "(in gigabytes per second):";
			unitValue = 1024*1024*1024;
		}
        
		int delayUnitValue = 1;
		delayUnitString = "(in milliseconds):";
		if (delayUnitChangerCombo.getSelectionIndex() ==1){
			delayUnitString = "(in seconds):";
			delayUnitValue = 1000;
		}
		if (delayUnitChangerCombo.getSelectionIndex() ==2){
			delayUnitString = "(in minutes):";
			delayUnitValue = 1000*60;
		}
		if (delayUnitChangerCombo.getSelectionIndex() ==3){
			delayUnitString = "(in hours):";
			delayUnitValue = 1000*60*60;
		}
		
        if (source == unitChangerCombo){			
			valueDownlinkSpeed.setSelection((int)((double)
					connectionCategory1.getDownlinkSpeed().getSpeed()/(double)unitValue));			
			
			valueUplinkSpeed.setSelection((int)((double)connectionCategory1.
					getUplinkSpeed().getSpeed()/(double)unitValue));
			
			downlinkSpeed.setText("Downlink speed " + unitString);
			uplinkSpeed.setText("Uplink speed " + unitString);			
		}
        
        if (source == delayUnitChangerCombo){
			valueDownlinkDelay.setSelection((int)((double)
					connectionCategory1.getDownlinkSpeed().getDelay()/(double)delayUnitValue));
			
			valueUplinkDelay.setSelection((int)((double)connectionCategory1.
					getUplinkSpeed().getDelay()/(double)delayUnitValue));
			System.out.println((double)connectionCategory1.
					getUplinkSpeed().getDelay());
			uplinkDelay.setText("Uplink delay " + delayUnitString);
			downlinkDelay.setText("Downlink delay " + delayUnitString);
		}

        if (source == valueUplinkSpeed) {
        	newChangeUndo();
        	connectionCategory1.getUplinkSpeed().setSpeed((long)valueUplinkSpeed.getSelection()*unitValue);
        	scenarioEditor.setDirty();        	
        }
        if (source == valueUplinkDelay) {
        	newChangeUndo();
	        connectionCategory1.getUplinkSpeed().setDelay((long)valueUplinkDelay.getSelection()*delayUnitValue);
	        scenarioEditor.setDirty();        	
        }
        if (source == valueDownlinkSpeed) {
        	newChangeUndo();
	        connectionCategory1.getDownlinkSpeed().setSpeed((long)valueDownlinkSpeed.getSelection()*unitValue);
	        scenarioEditor.setDirty();
        	
        }
        if (source == valueDownlinkDelay) {
        	newChangeUndo();
	            connectionCategory1.getDownlinkSpeed().setDelay((long)valueDownlinkDelay.getSelection()*delayUnitValue);
	            scenarioEditor.setDirty();        
        }
        if (source == deleteButton) {
        	ExecuteDeletion.deleteConnectionCategory(connectionCategory1);
//            for (int i = 0; i < connectionCategory1.getScenario().getConnectionCategories().size(); i++) {
//                if (connectionCategory1.equals(connectionCategory1.getScenario().getConnectionCategories().get(i))) {
//                	INodeConnection savesPosition = 
//						new NodeConnection(ProvidesPositionOfCommand.providesPosition(connectionCategory1));
//                	ScenarioEditorPlugin.getDefault().getUndoList().add(
//    						new ScenarioUndo
//    						(connectionCategory1, savesPosition, UndoOperationValues.deleteWasDone));
//                	connectionCategory1.getScenario().getConnectionCategories().remove(i);
//                	ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(connectionCategory1.getScenario().getConnectionCategories());
//                	scenarioEditor.objectDeleted(connectionCategory1);
//                    scenarioEditor.setDirty();
//                    ScenarioEditorPlugin.getDefault().getNavigationTree().select(connectionCategory1.getScenario().getConnectionCategories());
//                }
//            }
//            for (int i = 0; i < connectionCategory1.getScenario().getNodeCategories().size(); i++) {
//                INodeCategory nconnectionCategory1 = connectionCategory1.getScenario().getNodeCategories().get(i);
//                for (int j = 0; j < nconnectionCategory1.getConnections().size(); j++) {
//                    if (connectionCategory1.equals(nconnectionCategory1.getConnections().get(j).getCategory())) {
//                        nconnectionCategory1.getConnections().remove(j);
//                        ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(nconnectionCategory1.getConnections());
//                        scenarioEditor.setDirty();
//                    }
//                }
//            }
//            ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(connectionCategory1.getScenario().getConnectionCategories());            
        }
        if (((double)connectionCategory1.getDownlinkSpeed().getDelay()/(double)delayUnitValue)
    			> 999999999||
    			 ((double)connectionCategory1.getUplinkSpeed().getDelay()/(double)delayUnitValue)
    			> 999999999||
    			 ((double)connectionCategory1.getDownlinkSpeed().getSpeed()/(double)unitValue)
    			> 999999999||
    			 ((double)connectionCategory1.getUplinkSpeed().getSpeed()/(double)unitValue)
    			> 999999999){
    				warningLabel.setText("Values are to big to be shown in this unit. Please choose another unit.");
    				warningLabel.setBackground(new Color( null, 255,255,0));					
    	} 
    }
    
    
    /**
     * This method is used every time a change is made. The changes are assumed and the form
     * will be refreshed.
     */
	@Override
	public void update(IScenarioObject object) {		
		currentObject = object;        
		connectionCategory1 = (IConnectionCategory) object;
		noModifyEvents = true;
        connectionText.setText(connectionCategory1.getName());
        noModifyEvents = false;
        if (connectionCategory1.getDuplex() == IConnectionCategory.DuplexOption.full) {
            duplexButtonFull.setSelection(true);
            duplexButtonHalf.setSelection(false);
        } else {
            duplexButtonHalf.setSelection(true);
            duplexButtonFull.setSelection(false);
        }
                
        
        int unitValue = 1;
		unitString = "(in bytes per second):";
		if (unitChangerCombo.getSelectionIndex() ==1){
			unitString = "(in kilobytes per second):";
			unitValue = 1024;
		}
		if (unitChangerCombo.getSelectionIndex() ==2){
			unitString = "(in megabytes per second):";
			unitValue = 1024*1024;
		}
		if (unitChangerCombo.getSelectionIndex() ==3){
			unitString = "(in gigabytes per second):";
			unitValue = 1024*1024*1024;
		}
		
		int delayUnitValue = 1;
		delayUnitString = "(in milliseconds):";
		if (delayUnitChangerCombo.getSelectionIndex() ==1){
			delayUnitString = "(in seconds):";
			delayUnitValue = 1000;
		}
		if (delayUnitChangerCombo.getSelectionIndex() ==2){
			delayUnitString = "(in minutes):";
			delayUnitValue = 1000*60;
		}
		if (delayUnitChangerCombo.getSelectionIndex() ==3){
			delayUnitString = "(in hours):";
			delayUnitValue = 1000*60*60;
		}
		
		warningLabel.setBackground(new Color( null, 255,255,255));
		warningLabel.setText("");
		if (((double)connectionCategory1.getDownlinkSpeed().getDelay()/(double)delayUnitValue)
    			> 999999999||
    			 ((double)connectionCategory1.getUplinkSpeed().getDelay()/(double)delayUnitValue)
    			> 999999999||
    			 ((double)connectionCategory1.getDownlinkSpeed().getSpeed()/(double)unitValue)
    			> 999999999||
    			 ((double)connectionCategory1.getDownlinkSpeed().getSpeed()/(double)unitValue)
    			> 999999999){
    			warningLabel.setText("Values are to big to be shown in this unit. Please choose another unit.");
    			warningLabel.setBackground(new Color( null, 255,255,0));					
    	}

        valueUplinkSpeed.setSelection((int)(((double)connectionCategory1.getUplinkSpeed().getSpeed())/(double)unitValue));
        valueUplinkDelay.setSelection((int)(((double)connectionCategory1.getUplinkSpeed().getDelay())/(double)delayUnitValue));
        valueDownlinkSpeed.setSelection((int)(((double)connectionCategory1.getDownlinkSpeed().getSpeed())/(double)unitValue));
        valueDownlinkDelay.setSelection((int)(((double)connectionCategory1.getDownlinkSpeed().getDelay())/(double)delayUnitValue));
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
		
		speedUnit = "(in kilobytes per second):";
		delayUnit = "(in milliseconds):";		

		form.getBody().setLayout(new GridLayout(3, false));
        form.setText("Connection Category Information:");
        connectionLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
        connectionLabel.setText("Name:");
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        GridData gd4 = new GridData(GridData.FILL_HORIZONTAL);
        connectionText = toolkit.createText(form.getBody(), "", SWT.NONE);
        gd4.horizontalSpan = 2;
        connectionText.setLayoutData(gd4);
        
        GridData gd5 = new GridData(GridData.FILL_HORIZONTAL);
        gd5.horizontalSpan = 3;
        
        warningLabel2 = toolkit.createLabel(form.getBody(), "", SWT.NONE);
        warningLabel2.setLayoutData(gd5);
        
        duplexLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
        duplexLabel.setText("Duplex:");
        duplexButtonHalf = toolkit.createButton(form.getBody(), "Half",
                SWT.RADIO);
        duplexButtonHalf.addSelectionListener(this);
        duplexButtonFull = toolkit.createButton(form.getBody(), "Full",
                SWT.RADIO);
        duplexButtonFull.addSelectionListener(this);
        duplexButtonHalf.setLayoutData(gd);
        duplexButtonFull.setLayoutData(gd);
        uplinkSpeed = toolkit.createLabel(form.getBody(),
                "Uplink speed " + speedUnit + "    ", SWT.NONE);
        valueUplinkSpeed = new Spinner(form.getBody(), SWT.BORDER);
        valueUplinkSpeed.setMaximum(999999999);
        valueUplinkSpeed.addSelectionListener(this);
        empty = toolkit.createLabel(form.getBody(), "", SWT.NONE);
        uplinkDelay = toolkit.createLabel(form.getBody(),
                "Uplink delay " + delayUnit , SWT.NONE);
        valueUplinkDelay = new Spinner(form.getBody(), SWT.BORDER);
        valueUplinkDelay.setMaximum(999999999);
        valueUplinkDelay.addSelectionListener(this);
        empty = toolkit.createLabel(form.getBody(), "", SWT.NONE);
        downlinkSpeed = toolkit.createLabel(form.getBody(),
                "Downlink speed " + speedUnit + "    ", SWT.NONE);
        valueDownlinkSpeed = new Spinner(form.getBody(), SWT.BORDER);
        valueDownlinkSpeed.setMaximum(999999999);
        valueDownlinkSpeed.addSelectionListener(this);
        empty = toolkit.createLabel(form.getBody(), "", SWT.NONE);
        downlinkDelay = toolkit.createLabel(form.getBody(),
                "Downlink delay " + delayUnit , SWT.NONE);
        valueDownlinkDelay = new Spinner(form.getBody(), SWT.BORDER);
        valueDownlinkDelay.setMaximum(999999999);
        valueDownlinkDelay.addSelectionListener(this);
        GridData gdB = new GridData();
        gdB.horizontalSpan = 2;
        emptyLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
        unitLabel = toolkit.createLabel(form.getBody(), "Speed is shown in:", SWT.NONE);        
        unitChangerCombo = new Combo(form.getBody(), SWT.BORDER);
        unitChangerCombo.add("bytes per second");
		unitChangerCombo.add("kilobytes per second");
		unitChangerCombo.add("megabytes per second");
		unitChangerCombo.add("gigabytes per second");		
		unitChangerCombo.select(1);
		unitChangerCombo.addSelectionListener(this);
		
		emptyLabel2 = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		delayUnitLabel = toolkit.createLabel(form.getBody(), "Delay is shown in:", SWT.NONE);
		delayUnitChangerCombo = new Combo(form.getBody(), SWT.BORDER);		
		delayUnitChangerCombo.add("in milliseconds");
		delayUnitChangerCombo.add("in seconds");
		delayUnitChangerCombo.add("in minutes");
		delayUnitChangerCombo.add("in hours");
		delayUnitChangerCombo.select(0);
		delayUnitChangerCombo.addSelectionListener(this);
		
		GridData sameWidth = new GridData();
		sameWidth.widthHint = 250;		
		
		emptyLabel3 = toolkit.createLabel(form.getBody(), "", SWT.NONE);			
		
		//emptyLineLabel2 = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		//emptyLineLabel2.setLayoutData(gd5);                
        
        warningLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
        warningLabel.setLayoutData(gd5);                
		
		deleteButton = toolkit.createButton(form.getBody(),
                "Delete this ConnectionCategory", SWT.NONE);
		deleteButton.setLayoutData(sameWidth);
        deleteButton.setBackground(new Color( null, 240,10,40));
        deleteButton.addSelectionListener(this);                       
        
        //emptyLineLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
        //emptyLineLabel.setLayoutData(gd5);                

        connectionText.addSelectionListener(new SelectionAdapter() {
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });
        connectionText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
	            if (nameOk(connectionText.getText())&& !noModifyEvents){
	            	newChangeUndo();
	            	connectionCategory1.setName(connectionText.getText());
		            ScenarioEditorPlugin.getDefault().getNavigationTree().update(connectionCategory1);
		            setDirty();
	            }
            }
        });
		//dynamic help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(connectionText, "de.peerthing.workbench.scenario_connections");
		connectionText.setToolTipText("Enter a name for the connections.");	
		PlatformUI.getWorkbench().getHelpSystem().setHelp(duplexButtonHalf, "de.peerthing.workbench.scenario_connections");
		duplexButtonHalf.setToolTipText("Choose half or full duplex for this connection.");		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(valueUplinkSpeed, "de.peerthing.workbench.scenario_connections");
		valueUplinkSpeed.setToolTipText("Choose a value for the uplink speed.");			
		PlatformUI.getWorkbench().getHelpSystem().setHelp(valueDownlinkSpeed, "de.peerthing.workbench.scenario_connections");
		valueDownlinkSpeed.setToolTipText("Choose a value for the uplink speed.");	        
		PlatformUI.getWorkbench().getHelpSystem().setHelp(valueUplinkDelay, "de.peerthing.workbench.scenario_connections");
		valueUplinkDelay.setToolTipText("Choose a value for the uplink delay.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(valueDownlinkDelay, "de.peerthing.workbench.scenario_connections");
		valueDownlinkDelay.setToolTipText("Choose a value for the uplink delay.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(delayUnitChangerCombo, "de.peerthing.workbench.scenario_connections");
		delayUnitChangerCombo.setToolTipText("Change the unit of the delay.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(unitChangerCombo, "de.peerthing.workbench.scenario_connections");
		unitChangerCombo.setToolTipText("Change the unit of the speed.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(deleteButton, "de.peerthing.workbench.scenario_connections");
		deleteButton.setToolTipText("Pushing will delete this connection.");		
	}
}
