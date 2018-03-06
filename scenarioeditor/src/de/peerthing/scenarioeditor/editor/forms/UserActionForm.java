package de.peerthing.scenarioeditor.editor.forms;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;

import de.peerthing.scenarioeditor.ScenarioEditorPlugin;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.IUserAction;
import de.peerthing.scenarioeditor.model.impl.ExecuteDeletion;
import de.peerthing.scenarioeditor.model.impl.NameTest;
import de.peerthing.scenarioeditor.model.impl.ScenarioUndo;
import de.peerthing.scenarioeditor.model.impl.UndoOperationValues;
import de.peerthing.scenarioeditor.model.impl.UserAction;
import de.peerthing.scenarioeditor.editor.ScenarioEditor;
import de.peerthing.scenarioeditor.editor.actions.AddParameterAction;
import de.peerthing.scenarioeditor.editor.actions.DeleteParameterAction;

/**
 * This class manages a form with which you can manipulate the data of a
 * UserAction (ScenarioAction)
 *
 * @author Patrik,Hendrik, Petra (dynamic help)
 * @reviewer Petra 24.03.2006
 */
public class UserActionForm extends AbstractScenarioEditorForm {

	Label nameLabel;

	Text nameText;

	Label probabilityLabel;

	Spinner probabilityValueSpinner;

	IUserAction action;

	Table table;

	TableViewer tableViewer;

	Label parameterNameLabel;

	Label parameterValueLabel;

	Text parameterNameText;

	Text parameterValueText;

	Button createParameterButton;
	
	Button changeParameterButton;

	Button deleteParameterButton;

	Button deleteButton;

	TableColumn parameterColumn;

	TableColumn valueColumn;

	TableItem[] items;

	INodeCategory n1;
	
	String selectedName;

	boolean noModifyEvents = false;

	/**
	 * add a parameter to the action
	 */
	public void addParameter() {
		action.getParameters().put("NewKey", "NewValue");
		tableViewer.refresh();
	}
	
	/**
	 * adds an entry in the undo list so that it is noted the the action has
	 * been changed. 
	 */
	public void newChangeUndo(){
    	ScenarioEditorPlugin.getDefault().getUndoList().add(
				new ScenarioUndo
				(action, new UserAction(action), UndoOperationValues.valueChanged));
    }
	
	/**
	 * delets the selected parameter from the table
	 */
	public void deleteSelectedParameter() {
		Object param = ((IStructuredSelection) tableViewer.getSelection())
				.getFirstElement();
		if (param != null && param instanceof String[]) {
			newChangeUndo();
			action.getParameters().remove(((String[]) param)[0]);
			parameterNameText.setText("");
			parameterValueText.setText("");
			tableViewer.refresh();
		}
	}

	/**
	 * The Parametertable gets (re)constructed very time you call this method
	 */
	public void refreshTable() {
		tableViewer.setInput(action);
	}

	/**
	 * The actions of the user are handled here. The Data the user changed will
	 * be saved in the action-Object.
	 */
	public void widgetSelected(SelectionEvent e) {
		Object source = e.getSource();
		scenarioEditor.setDirty();

		if (source == createParameterButton) {
			
			if (!NameTest.isNameOk(parameterNameText.getText())||
					!NameTest.isNameOk(parameterValueText.getText())){
				while(true){
					MessageBox cancelBox = new MessageBox(form.getShell(), SWT.OK
							|SWT.ICON_WARNING );
					cancelBox.setText("Illegal Name or value");
					cancelBox.setMessage("A parameter has to have a name and a value " +
							"containing a sign other than \"space\" and containing less than " +
							"256 signs.");
					if (cancelBox.open()== SWT.OK || cancelBox.open() == SWT.ABORT){
						break;
					}
				}
				return;																			
				
			}
			
			newChangeUndo();
			
			action.getParameters().put(parameterNameText.getText(),
					parameterValueText.getText());
			parameterNameText.setText("");
			parameterValueText.setText("");
			refreshTable();			
		}
		
		if (source == changeParameterButton) {
			if (table.getSelectionIndex()==-1){
				while(true){
					MessageBox cancelBox = new MessageBox(form.getShell(), SWT.OK
							|SWT.ICON_WARNING );
					cancelBox.setText("Error");
					cancelBox.setMessage("U have to select a table item you want to change.");
					if (cancelBox.open()== SWT.OK || cancelBox.open() == SWT.ABORT){
						break;
					}
				}
				return;
			}
			
			if (!NameTest.isNameOk(parameterNameText.getText())||
					!NameTest.isNameOk(parameterValueText.getText())){
				while(true){
					MessageBox cancelBox = new MessageBox(form.getShell(), SWT.OK
							|SWT.ICON_WARNING );
					cancelBox.setText("Illegal Name or value");
					cancelBox.setMessage("A parameter has to have a name and a value " +
							"containing a sign other than \"space\" and containing less than " +
							"256 signs.");
					if (cancelBox.open()== SWT.OK || cancelBox.open() == SWT.ABORT){
						break;
					}
				}
				return;																			
				
			}
			
			newChangeUndo();			
			
			Object param = ((IStructuredSelection) tableViewer.getSelection())
			.getFirstElement();
			action.getParameters().remove(((String[]) param)[0]);											
			
			action.getParameters().put(parameterNameText.getText(),
					parameterValueText.getText());			
			tableViewer.refresh();
		}

		if (source == deleteParameterButton) {
			deleteSelectedParameter();
		}

		if (source == deleteButton) {
			
			ExecuteDeletion.deleteCommand(action);
		}
		if (source == probabilityValueSpinner){
			newChangeUndo();
			action.setProbability((double) probabilityValueSpinner.getSelection() / 100);
			ScenarioEditorPlugin.getDefault().getNavigationTree().update(action);
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
	 * All elements of the form are created and placed here.
	 */
	@Override
	public void createForm(Composite parent, ScenarioEditor editor) {
		super.createForm(parent, editor);

		form.getBody().setLayout(new GridLayout(2, false));
		form.setText("Action:");

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		nameLabel = toolkit.createLabel(form.getBody(), "Name:", SWT.NONE);
		nameLabel.setLayoutData(gd);

		nameText = toolkit.createText(form.getBody(), "");
		nameText.setLayoutData(gd);

		probabilityLabel = toolkit.createLabel(form.getBody(),
				"Probability: (in %)", SWT.NONE);
		probabilityLabel.setLayoutData(gd);
		probabilityValueSpinner = new Spinner(form.getBody(), SWT.BORDER);
		probabilityValueSpinner.addSelectionListener(this);

		GridData gd3 = new GridData(GridData.FILL_BOTH);
		gd3.horizontalSpan = 2;
		table = new Table(form.getBody(), SWT.NONE);
		table.setLayoutData(gd3);
		table.setHeaderVisible(true);
		// table.addSelectionListener(this);
		parameterColumn = new TableColumn(table, SWT.NONE);
		parameterColumn.setText("Parameter Name:");
		parameterColumn.setWidth(250);
		valueColumn = new TableColumn(table, SWT.NONE);
		valueColumn.setText("Parameter Value:");
		valueColumn.setWidth(250);
		tableViewer = new TableViewer(table);
		tableViewer.setContentProvider(new IStructuredContentProvider() {
			public Object[] getElements(Object inputElement) {
				if (inputElement instanceof IUserAction) {
					IUserAction action = (IUserAction) inputElement;
					String[][] params = new String[action.getParameters()
							.size()][2];

					int i = 0;
					for (String key : action.getParameters().keySet()) {
						params[i][0] = key;
						params[i++][1] = action.getParameters().get(key);
					}

					return params;
				}
				return null;
			}

			public void dispose() {
			}

			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}
		});
		tableViewer.setLabelProvider(new ITableLabelProvider() {

			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}

			public String getColumnText(Object element, int columnIndex) {
				if (element instanceof String[]) {
					String[] item = (String[]) element;
					return item[columnIndex];
				}
				return null;
			}

			public void addListener(ILabelProviderListener listener) {
			}

			public void dispose() {
			}

			public boolean isLabelProperty(Object element, String property) {
				return false;
			}

			public void removeListener(ILabelProviderListener listener) {
			}
		});
		tableViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {						
					if (((IStructuredSelection) event
								.getSelection()).getFirstElement()==null){
						return;
					}
					Object selected = ((IStructuredSelection)event.getSelection()).getFirstElement();
					String [] string= (String[])selected; 
		            
					parameterNameText.setText(string[0]);						
					parameterValueText.setText(string[1]);
					
					}
				});
		
		tableViewer.setSorter(new ViewerSorter() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				if (e1 instanceof String[] && e2 instanceof String[] &&
						((String[]) e1).length > 0 && ((String[]) e2).length > 0) {
					return ((String[]) e1)[0].compareTo(((String[]) e2)[0]);
				}
				return 0;
			}
			
		});

		MenuManager menuManager = new MenuManager();
		menuManager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		// menuManager.add(new DeleteParamAction());
		menuManager.add(new DeleteParameterAction(this));
		menuManager.add(new AddParameterAction(this));
		tableViewer.getControl().setMenu(
				menuManager.createContextMenu(tableViewer.getControl()));

		parameterNameLabel = toolkit.createLabel(form.getBody(),
				"Parameter name", SWT.NONE);
		parameterValueLabel = toolkit.createLabel(form.getBody(),
				"Parameter value", SWT.NONE);
		parameterNameText = toolkit.createText(form.getBody(), "", SWT.NONE);
		parameterNameText.setLayoutData(gd);
		parameterValueText = toolkit.createText(form.getBody(), "", SWT.NONE);
		parameterValueText.setLayoutData(gd);

		GridData sameWidth = new GridData();
		sameWidth.widthHint = 250;
		
		createParameterButton = toolkit.createButton(form.getBody(),
				"Create new parameter and new value", SWT.NONE);
		createParameterButton.setLayoutData(sameWidth);
		createParameterButton.addSelectionListener(this);
		
		changeParameterButton = toolkit.createButton(form.getBody(),
				"Change parameter and value", SWT.NONE);
		changeParameterButton.setLayoutData(sameWidth);
		changeParameterButton.addSelectionListener(this);
		
		deleteParameterButton = toolkit.createButton(form.getBody(),
				"Delete parameter", SWT.NONE);
		deleteParameterButton.setLayoutData(sameWidth);
		deleteParameterButton.setBackground(new Color( null, 240,100,100));
		deleteParameterButton.addSelectionListener(this);

		deleteButton = toolkit.createButton(form.getBody(),
				"Delete this action", SWT.NONE);
		deleteButton.setLayoutData(sameWidth);
		deleteButton.setBackground(new Color( null, 240,10,40));
		deleteButton.addSelectionListener(this);

		nameText.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		nameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (!noModifyEvents){
					newChangeUndo();
					action.setName(nameText.getText());
					tree.update(action);
					scenarioEditor.setDirty();
				}
			}
		});
		
		//dynamic help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(nameText, "de.peerthing.workbench.scenario_nodes");
		nameText.setToolTipText("Here you can change the name of this behaviour.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(probabilityValueSpinner, "de.peerthing.workbench.scenario_nodes");
		probabilityValueSpinner.setToolTipText("Choose a value for the probability in percent.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parameterNameText, "de.peerthing.workbench.scenario_nodes");
		parameterNameText.setToolTipText("Choose a name for the parameter.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parameterValueText, "de.peerthing.workbench.scenario_nodes");
		parameterValueText.setToolTipText("Choose a value for the parameter.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(createParameterButton, "de.peerthing.workbench.scenario_nodes");
		createParameterButton.setToolTipText("Push to create a new parameter.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(deleteParameterButton, "de.peerthing.workbench.scenario_nodes");
		deleteParameterButton.setToolTipText("Push to delete the parameter.");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(deleteButton, "de.peerthing.workbench.scenario_nodes");
		deleteButton.setToolTipText("Push to delete the complete action.");		
		
	}

	/**
     * This method is used every time a change is made. The changes are assumed and the form
     * is refreshed.
     */
	@Override
	public void update(IScenarioObject object) {
		noModifyEvents = true;
		currentObject = object;
		action = (IUserAction) object;
		nameText.setText(action.getName());
		probabilityValueSpinner.setSelection((int) (action.getProbability() * 100));
		refreshTable();		
		noModifyEvents = false;
	}
	
	/**
	 * checks if the names in the text field for parameter and value are ok.
	 */
	public boolean nameOk(){
		if (parameterNameText.getText().equals("") || parameterValueText.getText().equals(""))
		{
			return false;
		}
		
		return true;
	}
}
