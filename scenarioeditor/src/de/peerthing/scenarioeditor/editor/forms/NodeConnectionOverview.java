package de.peerthing.scenarioeditor.editor.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;

import de.peerthing.scenarioeditor.ScenarioEditorPlugin;
import de.peerthing.scenarioeditor.model.IConnectionCategory;
import de.peerthing.scenarioeditor.model.IListWithParent;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.INodeConnection;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.ScenarioFactory;
import de.peerthing.scenarioeditor.model.impl.NodeConnection;
import de.peerthing.scenarioeditor.model.impl.ProvidesPositionOfCommand;
import de.peerthing.scenarioeditor.model.impl.ScenarioUndo;
import de.peerthing.scenarioeditor.model.impl.UndoOperationValues;
import de.peerthing.scenarioeditor.editor.ScenarioEditor;

/**
 * This class manages a form with which you can manipulate
 * the data of node connections *
 *
 * @author lethe, patrik, Petra (dynamic help)
 * @reviewer Petra 24.03.2006
 */
public class NodeConnectionOverview extends AbstractScenarioEditorForm {
	Label nameLabel;
	Table connectionTable;
	TableColumn nameColumn;
	Label connectionNotice;
	TableColumn uplinkSpeedColumn;
	TableColumn uplinkDelayColumn;
	TableColumn downlinkSpeedColumn;
	TableColumn downlinkDelayColumn;
	TableColumn duplexColumn;
	TableItem[] connectionItems;
	IScenario scenario;
	INodeCategory nodeCategory;
	double roundValue = 1000;

	/**
	 * Gui is initialised in the constructor
	 * @param container
	 */
	public NodeConnectionOverview() {
	}
	
	/**
	 * Notes in the undo list that a nodeconnection has been added.
	 */
	public void newAddUndo(INodeConnection nodeConnection){
    	ScenarioEditorPlugin.getDefault().getUndoList().add(
				new ScenarioUndo
				(nodeCategory, nodeConnection, UndoOperationValues.addWasDone));
    }
	
	/**
	 * Notes in the undo list that a nodeconnection has been deleted.
	 */
	public void newDeleteUndo(INodeConnection nodeConnection){
    	INodeConnection savesPosition = 
			new NodeConnection(ProvidesPositionOfCommand.providesPosition(nodeConnection));
    	ScenarioEditorPlugin.getDefault().getUndoList().add(
				new ScenarioUndo
				(nodeConnection, savesPosition, UndoOperationValues.deleteWasDone));
    }
	
	public void updateConnections(){
		for (int i = 0; i < scenario.getConnectionCategories().size(); i++){
			if(connectionItems[i].getChecked()){
				addConnection(scenario.getConnectionCategories().get(i));
			} else {
				removeConnection(scenario.getConnectionCategories().get(i));
			}
		}
	}
	
	/**
	 * Adds the handed NodeConnection to the node Category. 	 
	 */
	public void addConnection(IConnectionCategory con){
		for (int i = 0; i < nodeCategory.getConnections().size(); i++){
			if (nodeCategory.getConnections().get(i).getCategory().equals(con)){
				return;
			}
		}				
		INodeConnection nodeConnection = ScenarioFactory.createNodeConnection();
		newAddUndo(nodeConnection);
		nodeConnection.setCategory(con);
		nodeCategory.getConnections().add(nodeConnection);
		nodeConnection.setNode(nodeCategory);
		nodeConnection.setNumberOfNodes(0);
		tree.refresh(nodeCategory.getConnections());
	}

	/**
	 * Removes the handed connectioncategory from the node Category. 	 
	 */
	public void removeConnection(IConnectionCategory con){
		for (int i = 0; i < nodeCategory.getConnections().size(); i++){
			if (nodeCategory.getConnections().get(i).getCategory().equals(con)){
				newDeleteUndo(nodeCategory.getConnections().get(i));
				nodeCategory.getConnections().remove(i);
				ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(nodeCategory.getConnections());
				return;
			}
		}
	}

	public void widgetSelected(SelectionEvent e) {		
		form.redraw();
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

		form.getBody().setLayout(new GridLayout(1, false));
		form.setText("Usage of Connection Categories: ");

		GridData gd = new GridData(GridData.FILL_BOTH);

		connectionTable = new Table(form.getBody(), SWT.CHECK);
		connectionTable.setLayoutData(gd);
		connectionTable.setHeaderVisible(true);
		connectionTable.addSelectionListener(this);

		nameColumn = new TableColumn(connectionTable, SWT.NONE);
		nameColumn.setText("Name:");
		nameColumn.setWidth(100);

		duplexColumn = new TableColumn(connectionTable, SWT.NONE);
		duplexColumn.setText("Duplex:");
		duplexColumn.setWidth(60);

		uplinkSpeedColumn = new TableColumn(connectionTable, SWT.NONE);
		uplinkSpeedColumn.setText("Uplink Speed:");
		uplinkSpeedColumn.setWidth(180);

		uplinkDelayColumn = new TableColumn(connectionTable, SWT.NONE);
		uplinkDelayColumn.setText("Uplink Delay:");
		uplinkDelayColumn.setWidth(140);

		downlinkSpeedColumn = new TableColumn(connectionTable, SWT.NONE);
		downlinkSpeedColumn.setText("Downlink Speed:");
		downlinkSpeedColumn.setWidth(190);

		downlinkDelayColumn = new TableColumn(connectionTable, SWT.NONE);
		downlinkDelayColumn.setText("Downlink Delay:");
		downlinkDelayColumn.setWidth(170);
		connectionNotice = toolkit.createLabel(form.getBody(), "Notice: The initial number of nodes of an added connection is 0", SWT.NONE);
		connectionTable.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event event) {
				if (event.detail == SWT.CHECK){
					scenarioEditor.setDirty();
					updateConnections();
				}
			}});

		//dynamic help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(connectionTable, "de.peerthing.workbench.scenario_nodes");
		connectionTable.setToolTipText("Check if you want to use the connection for this node.");
		
	}
	
	/**
     * This method is used every time a change is made. The changes are assumed and the form
     * is refreshed.
     */
	@Override
	public void update(IScenarioObject object) {
		
		currentObject = object;
		this.scenario = object.getScenario();
		this.nodeCategory = (INodeCategory) ((IListWithParent<?>) object).getParent();				
		
		ConnectionCategoryForm connectionForm = null;
		
		if (nodeCategory.getScenario().getConnectionCategories().size()!=0){
			connectionForm = (ConnectionCategoryForm) scenarioEditor.getForm(nodeCategory.getScenario().getConnectionCategories().get(0));
		}
		String delayString = " (in seconds):";
		String speedString = " (in kilobytes per second):";
		double delayUnitDivision = 1000;
		double speedUnitDivision = 1024;								
		
		if (connectionForm != null){						
			delayString = " (in ms):";
			switch (connectionForm.delayUnitChangerCombo.getSelectionIndex()){
				case 0: delayUnitDivision = 1;
						delayString = " (in ms):"; break;
				case 1: delayUnitDivision = 1000;
						delayString = " (in s):"; break;
				case 2: delayUnitDivision = 1000*60;
						delayString = " (in m):"; break;
				case 3: delayUnitDivision = 1000*60*60;
						delayString = " (in h):"; break;
				default: System.out.println("Error in NodeConnectionForm");
			}															
									
			switch (connectionForm.unitChangerCombo.getSelectionIndex()){			
				case 0: speedUnitDivision = 1;
					speedString = " (in b/sec):"; break;
				case 1: speedUnitDivision = 1024;
						speedString = " (in kb/sec):"; break;
				case 2: speedUnitDivision = 1024*1024;
						speedString = " (in mb/sec):"; break;
				case 3: speedUnitDivision = 1024*1024*1024;
						speedString = " (in gb/sec):"; break;
				default: System.out.println("Error 2 in NodeConnectionForm");
			}
		}
		
		uplinkSpeedColumn.setText("Uplink speed"+ speedString);
		uplinkDelayColumn.setText("Uplink delay" + delayString);
		downlinkSpeedColumn.setText("Downlink speed"+ speedString);
		downlinkDelayColumn.setText("Downlink delay" + delayString);								
		
		connectionTable.removeAll();				
		connectionItems = new TableItem[scenario.getConnectionCategories().size()];
		
        for(int i=0; i< scenario.getConnectionCategories().size(); i++){
        	
        	double downlinkD = Math.rint((scenario.getConnectionCategories().get(i).
    				getDownlinkSpeed().getDelay()/(double)delayUnitDivision)*roundValue)/roundValue;
    		
    		double uplinkD = Math.rint((scenario.getConnectionCategories().get(i).
    				getUplinkSpeed().getDelay()/(double)delayUnitDivision)*roundValue)/roundValue;				
    						
    		double downlinkS = Math.rint((scenario.getConnectionCategories().get(i)						
    				.getDownlinkSpeed().getSpeed()/speedUnitDivision)*roundValue)/roundValue;			
    		double uplinkS = Math.rint((scenario.getConnectionCategories().get(i)
    				.getUplinkSpeed().getSpeed()/speedUnitDivision)*roundValue)/roundValue;
        	
    		connectionItems[i] = new TableItem(connectionTable, SWT.NONE);
			connectionItems[i].setText(new String []{scenario.getConnectionCategories().get(i).getName(),			
					String.valueOf(scenario.getConnectionCategories().get(i).getDuplex()),						
			
			String.valueOf(uplinkS),
			String.valueOf(uplinkD),
			String.valueOf(downlinkS),
			String.valueOf(downlinkD)
			}

			);
			for (int j = 0; j<nodeCategory.getConnections().size(); j++){
				IConnectionCategory c2 = nodeCategory.getConnections().get(j).getCategory();
				if (scenario.getConnectionCategories().get(i).equals
						(c2)){
						connectionItems[i].setChecked(true);
				}
			}
		}        

	}
}