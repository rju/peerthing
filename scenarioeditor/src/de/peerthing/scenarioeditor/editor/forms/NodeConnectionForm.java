package de.peerthing.scenarioeditor.editor.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.PlatformUI;

import de.peerthing.scenarioeditor.ScenarioEditorPlugin;
import de.peerthing.scenarioeditor.editor.ScenarioEditor;
import de.peerthing.scenarioeditor.model.INodeConnection;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.impl.NodeConnection;
import de.peerthing.scenarioeditor.model.impl.ScenarioUndo;
import de.peerthing.scenarioeditor.model.impl.UndoOperationValues;

/**
 * This class manages a form with which you can manipulate the data of a node
 * connection
 *
 * @author Patrik, Petra (dynamic help)
 * @reviewer Petra 24.03.2006
 */
public class NodeConnectionForm extends AbstractScenarioEditorForm {

    /**
     * A label for "name" (of node connection form)
     */
	Label nameLabel;
    
    /**
     * A label for current name value type
     */
	Label nameValueLabel;
    
    /**
     * A label for "duplex" (of node connection form)
     */
	Label duplexLabel;
    
    /**
     * A label for current duplex type
     */
	Label duplexValueLabel;        
	Label uplinkSpeedLabel;
	Label uplinkSpeedValueLabel;
	Label uplinkSpeedScaleLabel;
	Label uplinkDelayLabel;
	Label uplinkDelayValueLabel;
	Label uplinkDelayScaleLabel;
	Label downlinkSpeedLabel;
	Label downlinkSpeedValueLabel;
	Label downlinkSpeedScaleLabel;
	Label downlinkDelayLabel;
	Label downlinkDelayValueLabel;
	Label downlinkDelayScaleLabel;
	Label emptyLineLabel;
	Label numberOfNodesLabel;
	Spinner numberOfNodesSpinner;
	INodeConnection nodeConnection;
	double roundValue = 1000;
	
	/**
	 * Gui is initialised in the constructor
	 *
	 * @param container
	 */
	public NodeConnectionForm() {
	}

	/**
	 * Add a new entry in the undo list, so that is noted that this
	 * nodeConnection has been changed. 
	 *
	 */
	public void newChangeUndo(){
    	ScenarioEditorPlugin.getDefault().getUndoList().add(
				new ScenarioUndo
				(nodeConnection, new NodeConnection(nodeConnection), UndoOperationValues.valueChanged));
    }
	
	/**
	 * The actions of the user are handled here. The Data the user changed will
	 * be saved in the nodeConnection-Object.
	 */
	public void widgetSelected(SelectionEvent e) {		
		newChangeUndo();
		nodeConnection.setNumberOfNodes(numberOfNodesSpinner.getSelection());
		scenarioEditor.setDirty();
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
		form.setText("Connection Category Information:");

		nameLabel = toolkit.createLabel(form.getBody(), "Name:", SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		nameLabel.setLayoutData(gd);

		nameValueLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
		nameValueLabel.setLayoutData(gd2);

		//emptyLabel1 = toolkit.createLabel(form.getBody(), "", SWT.NONE);

		duplexLabel = toolkit.createLabel(form.getBody(), "Duplex:", SWT.NONE);
		GridData gd3 = new GridData(GridData.FILL_HORIZONTAL);
		nameLabel.setLayoutData(gd3);

		duplexValueLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		GridData gd4 = new GridData(GridData.FILL_HORIZONTAL);
		duplexValueLabel.setLayoutData(gd4);

		//emptyLabel2 = toolkit.createLabel(form.getBody(), "", SWT.NONE);

		uplinkSpeedLabel = toolkit.createLabel(form.getBody(), "Uplink speed (in kilobytes per second):",
				SWT.NONE);
		GridData gd5 = new GridData(GridData.FILL_HORIZONTAL);
		uplinkSpeedLabel.setLayoutData(gd5);

		uplinkSpeedValueLabel = toolkit.createLabel(form.getBody(), "",
				SWT.NONE);
		GridData gd6 = new GridData(GridData.FILL_HORIZONTAL);
		uplinkSpeedValueLabel.setLayoutData(gd6);

		/*uplinkSpeedScaleLabel = toolkit.createLabel(form.getBody(), "",
				SWT.NONE);
		GridData gd7 = new GridData(GridData.FILL_HORIZONTAL);
		uplinkSpeedScaleLabel.setLayoutData(gd7);*/

		uplinkDelayLabel = toolkit.createLabel(form.getBody(), "Uplink delay (in milliseconds):",
				SWT.NONE);
		GridData gd8 = new GridData(GridData.FILL_HORIZONTAL);
		uplinkDelayLabel.setLayoutData(gd8);

		uplinkDelayValueLabel = toolkit.createLabel(form.getBody(), "",
				SWT.NONE);
		GridData gd9 = new GridData(GridData.FILL_HORIZONTAL);
		uplinkDelayValueLabel.setLayoutData(gd9);

		/*uplinkDelayScaleLabel = toolkit.createLabel(form.getBody(), "",
				SWT.NONE);
		GridData gd10 = new GridData(GridData.FILL_HORIZONTAL);
		uplinkDelayScaleLabel.setLayoutData(gd10);*/

		downlinkSpeedLabel = toolkit.createLabel(form.getBody(),
				"Downlink speed (in kilobytes per second):", SWT.NONE);
		GridData gd11 = new GridData(GridData.FILL_HORIZONTAL);
		downlinkSpeedLabel.setLayoutData(gd11);

		downlinkSpeedValueLabel = toolkit.createLabel(form.getBody(), "",
				SWT.NONE);
		GridData gd12 = new GridData(GridData.FILL_HORIZONTAL);
		downlinkSpeedValueLabel.setLayoutData(gd12);

		/*downlinkSpeedScaleLabel = toolkit.createLabel(form.getBody(), "",
				SWT.NONE);
		GridData gd13 = new GridData(GridData.FILL_HORIZONTAL);
		downlinkSpeedScaleLabel.setLayoutData(gd13);*/

		downlinkDelayLabel = toolkit.createLabel(form.getBody(),
				"Downlink delay (in milliseconds):", SWT.NONE);
		GridData gd14 = new GridData(GridData.FILL_HORIZONTAL);
		downlinkDelayLabel.setLayoutData(gd14);

		downlinkDelayValueLabel = toolkit.createLabel(form.getBody(), "",
				SWT.NONE);
		GridData gd15 = new GridData(GridData.FILL_HORIZONTAL);
		downlinkDelayValueLabel.setLayoutData(gd15);

		/*downlinkDelayScaleLabel = toolkit.createLabel(form.getBody(), "",
				SWT.NONE);
		GridData gd16 = new GridData(GridData.FILL_HORIZONTAL);
		downlinkDelayScaleLabel.setLayoutData(gd16);*/

		//emptyLineLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		GridData gd16b = new GridData(GridData.FILL_HORIZONTAL);
		gd16b.horizontalSpan = 3;
		//emptyLineLabel.setLayoutData(gd16b);

		numberOfNodesLabel = toolkit.createLabel(form.getBody(), "", SWT.NONE);
		GridData gd17 = new GridData(GridData.FILL_HORIZONTAL);
		//gd17.horizontalSpan = 2;
		numberOfNodesLabel.setLayoutData(gd17);

		numberOfNodesSpinner = new Spinner(form.getBody(), SWT.BORDER);
		numberOfNodesSpinner.setMaximum(999999);
		numberOfNodesSpinner.addSelectionListener(this);

		//dynamic help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(numberOfNodesSpinner, "de.peerthing.workbench.scenario_nodes");
		numberOfNodesSpinner.setToolTipText("Choose the number of nodes with this connection");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(nameLabel, "de.peerthing.workbench.scenario_nodes");
		
	}

	/**
     * This method is used every time a change is made. The changes are assumed and the form
     * is refreshed.
     */
	@Override
	public void update(IScenarioObject object) {
		currentObject = object;
		nodeConnection = (INodeConnection) object;
		nameValueLabel.setText(nodeConnection.getCategory().getName());
		duplexValueLabel.setText(nodeConnection.getCategory().getDuplex()
				.toString());
		
		numberOfNodesLabel
				.setText("Number of " + nodeConnection.getNode().getName()
						+ " with this connection:");
		numberOfNodesSpinner.setSelection(nodeConnection.getNumberOfNodes());
		
		ConnectionCategoryForm connectionForm = (ConnectionCategoryForm) scenarioEditor.getForm(nodeConnection.getScenario().getConnectionCategories().get(0));
		String delayString = " (in seconds):";
		String speedString = " (in kilobytes per second):";
		double delayUnitDivision = 1000;
		double speedUnitDivision = 1024;
		
		if (connectionForm != null){						
			delayString = " (in milliseconds):";
			switch (connectionForm.delayUnitChangerCombo.getSelectionIndex()){
				case 0: delayUnitDivision = 1;
						delayString = " (in milliseconds):"; break;
				case 1: delayUnitDivision = 1000;
						delayString = " (in seconds):"; break;
				case 2: delayUnitDivision = 1000*60;
						delayString = " (in minutes):"; break;
				case 3: delayUnitDivision = 1000*60*60;
						delayString = " (in hours):"; break;
				default: System.out.println("Fehler in NodeConnectionForm");
			}															
									
			switch (connectionForm.unitChangerCombo.getSelectionIndex()){			
				case 0: speedUnitDivision = 1;
					speedString = " (in bytes per second):"; break;
				case 1: speedUnitDivision = 1024;
						speedString = " (in kilobytes per second):"; break;
				case 2: speedUnitDivision = 1024*1024;
						speedString = " (in megabytes per second):"; break;
				case 3: speedUnitDivision = 1024*1024*1024;
						speedString = " (in gigabytes per second):"; break;
				default: System.out.println("Fehler 2 in NodeConnectionForm");
			}
		}
						
		double downlinkD = Math.rint((nodeConnection
				.getCategory().getDownlinkSpeed().getDelay()/(double)delayUnitDivision)*roundValue)/roundValue;
		downlinkDelayValueLabel.setText(String.valueOf(downlinkD));
		double uplinkD = Math.rint((nodeConnection
				.getCategory().getUplinkSpeed().getDelay()/(double)delayUnitDivision)*roundValue)/roundValue;
		uplinkDelayValueLabel.setText(String.valueOf(uplinkD));
		
		
		double downlinkS = Math.rint((nodeConnection						
				.getCategory().getDownlinkSpeed().getSpeed()/speedUnitDivision)*roundValue)/roundValue;	
		downlinkSpeedValueLabel.setText(String.valueOf(downlinkS));
		double uplinkS = Math.rint((nodeConnection
				.getCategory().getUplinkSpeed().getSpeed()/speedUnitDivision)*roundValue)/roundValue;
		uplinkSpeedValueLabel.setText(String.valueOf(uplinkS));						
		
		downlinkSpeedLabel.setText("Downlink speed"+ speedString);
		uplinkSpeedLabel.setText("Uplink speed"+ speedString);
		
		downlinkDelayLabel.setText("Downlink delay"+ delayString);
		uplinkDelayLabel.setText("Uplink delay"+ delayString);
	}
}
