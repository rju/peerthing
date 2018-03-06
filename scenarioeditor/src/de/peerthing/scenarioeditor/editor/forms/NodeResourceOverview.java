package de.peerthing.scenarioeditor.editor.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import de.peerthing.scenarioeditor.ScenarioEditorPlugin;
import de.peerthing.scenarioeditor.model.IDistribution;
import de.peerthing.scenarioeditor.model.IListWithParent;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.INodeConnection;
import de.peerthing.scenarioeditor.model.INodeResource;
import de.peerthing.scenarioeditor.model.IResourceCategory;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.ScenarioFactory;
import de.peerthing.scenarioeditor.model.impl.NodeConnection;
import de.peerthing.scenarioeditor.model.impl.ProvidesPositionOfCommand;
import de.peerthing.scenarioeditor.model.impl.ScenarioUndo;
import de.peerthing.scenarioeditor.model.impl.UndoOperationValues;
import de.peerthing.scenarioeditor.editor.ScenarioEditor;

/**
 * This class manages a form with which you can manipulate the data of node
 * resources
 *
 * @author Patrik, Petra (dynamic help)
 * @reviewer Petra 24.03.2006
 */
public class NodeResourceOverview extends AbstractScenarioEditorForm {
    Label nameLabel;
    Text newNodeText;
    Button newNodeButton;
    Table table;
    IScenario scenario;
    TableColumn nameColumn;
    TableColumn diversityColumn;
    TableColumn popularityColumn;
    TableItem[] items;
    INodeCategory n1;

    /**
     * Adds an entry in the undo list, so that it is noted that a the
     * handed nodeResource was added to the nodecategory.
     */
    public void newAddUndo(INodeResource nodeResource){
    	ScenarioEditorPlugin.getDefault().getUndoList().add(
				new ScenarioUndo
				(n1, nodeResource, UndoOperationValues.addWasDone));
    }
    
    /**
     * Adds an entry in the undo list, so that it is noted that a the
     * handed nodeResource was removed from the nodecategory.
     */
    public void newDeleteUndo(INodeResource nodeResouce){
    	INodeConnection savesPosition = 
			new NodeConnection(ProvidesPositionOfCommand.providesPosition(nodeResouce));
    	ScenarioEditorPlugin.getDefault().getUndoList().add(
				new ScenarioUndo
				(nodeResouce, savesPosition, UndoOperationValues.deleteWasDone));
    }
    
    /**
     * Add the handed Resource Category the the node category.
     */
    public void addResource(IResourceCategory res) {
        for (int i = 0; i < n1.getResources().size(); i++) {
            if (n1.getResources().get(i).getCategory().equals(res)) {
                return;
            }
        }
        INodeResource nodeR = ScenarioFactory.createNodeResource();
        newAddUndo(nodeR);
        nodeR.setCategory(res);
        n1.getResources().add(nodeR);
        nodeR.setNode(n1);
        nodeR.setNumberDistribution(ScenarioFactory.createDistribution(scenario));
        nodeR.getNumberDistribution().setType(
                IDistribution.DistributionType.uniform);
        ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(n1.getResources());

    }

    /**
     * Remove the handed Resource Category from the node category.
     */
    public void removeResource(IResourceCategory res) {
        for (int i = 0; i < n1.getResources().size(); i++) {
            if (n1.getResources().get(i).getCategory().equals(res)) {
            	newDeleteUndo(n1.getResources().get(i));
                n1.getResources().remove(i);
                ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(n1.getResources());
                return;
            }
        }
    }

    /**
     * This method is used every time a change is made. The changes are assumed and the form
     * is refreshed.
     */
    public void updateResources() {        
        for (int k = 0; k < scenario.getResourceCategories().size(); k++) {
            if (items[k].getChecked()) {
                addResource(scenario.getResourceCategories().get(k));
            } else {
                removeResource(scenario.getResourceCategories().get(k));
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

        form.getBody().setLayout(new GridLayout());
        form.setText("Useage of Resource Categories:");

        GridData gd = new GridData(GridData.FILL_BOTH);
        table = new Table(form.getBody(), SWT.CHECK);
        table.setLayoutData(gd);
        table.setHeaderVisible(true);
        table.addSelectionListener(this);

        nameColumn = new TableColumn(table, SWT.NONE);
        nameColumn.setText("Name:");
        nameColumn.setWidth(150);
        diversityColumn = new TableColumn(table, SWT.NONE);
        diversityColumn.setText("Diversity:");
        diversityColumn.setWidth(150);
        popularityColumn = new TableColumn(table, SWT.NONE);
        popularityColumn.setText("Popularity (in relationchip to other resourcecategories):");
        popularityColumn.setWidth(150);

        table.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {

                if (event.detail == SWT.CHECK) {
                	scenarioEditor.setDirty();
                	updateResources();                    
                }
            }
        });
        
		//dynamic help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(table, "de.peerthing.workbench.scenario_nodes");
		table.setToolTipText("Select the resources you want to use.");		

	}

	/**
     * This method is used every time a change is made. The changes are assumed and the form
     * is refreshed.
     */
	@Override
	public void update(IScenarioObject object) {
		currentObject = object;
        this.scenario = object.getScenario();
        n1 = (INodeCategory) ((IListWithParent<?>) object).getParent();

        table.removeAll();
        items = new TableItem[scenario.getResourceCategories().size()];
        for (int i = 0; i < scenario.getResourceCategories().size(); i++) {
            items[i] = new TableItem(table, SWT.NONE);
            items[i].setText(new String[] {
                    scenario.getResourceCategories().get(i).getName(),
                    String.valueOf(scenario.getResourceCategories().get(i)
                            .getDiversity()),
                    String.valueOf(scenario.getResourceCategories().get(i)
                            .getPopularity()) });
            for (int j = 0; j < n1.getResources().size(); j++) {
                IResourceCategory r2 = n1.getResources().get(j).getCategory();
                if (scenario.getResourceCategories().get(i).equals(r2)) {
                    items[i].setChecked(true);
                }
            }
        }
	}

}