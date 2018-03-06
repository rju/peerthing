package de.peerthing.systembehavioureditor.propertyeditor;

import org.eclipse.gef.editparts.AbstractEditPart;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;

import de.peerthing.systembehavioureditor.PeerThingSystemBehaviourEditorPlugin;
import de.peerthing.systembehavioureditor.gefeditor.SysGraphicalEditor;
import de.peerthing.systembehavioureditor.gefeditor.editparts.StateEditPart;
import de.peerthing.systembehavioureditor.model.IAction;
import de.peerthing.systembehavioureditor.model.ICaseArchitecture;
import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.IParameter;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.ISystemBehaviourObject;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.ITransition;
import de.peerthing.systembehavioureditor.model.IVariable;
import de.peerthing.systembehavioureditor.model.editor.Condition;
import de.peerthing.systembehavioureditor.model.editor.State;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Transition;
import de.peerthing.systembehavioureditor.propertyeditor.forms.ActionForm;
import de.peerthing.systembehavioureditor.propertyeditor.forms.CaseArchitectureForm;
import de.peerthing.systembehavioureditor.propertyeditor.forms.ConditionForm;
import de.peerthing.systembehavioureditor.propertyeditor.forms.NodeForm;
import de.peerthing.systembehavioureditor.propertyeditor.forms.ParameterForm;
import de.peerthing.systembehavioureditor.propertyeditor.forms.StateForm;
import de.peerthing.systembehavioureditor.propertyeditor.forms.TaskForm;
import de.peerthing.systembehavioureditor.propertyeditor.forms.TransitionForm;
import de.peerthing.systembehavioureditor.propertyeditor.forms.VarForm;

/**
 * 
 * Main Class for the PropertyEditor.
 * 
 * @author Tjark Bikker, Johannes Fischer, Sebastian Rohjans
 * @review Peter, 2006-03-23 This class is for editing the actions and
 *         conditions in the architecture.
 * @version 0.99
 * 
 */

public class PropertyEditor extends ViewPart implements ISelectionListener {
	private TreeViewer treeviewer;

	private FormToolkit toolkit;

	private Transition trans = new Transition();

	private Object CHOSEN = null;

	private MenuManager menuManager;

	int paracount = 0;

	Label testLabel; 

	Composite testComposite;

	ScrolledForm form;

	VarForm varForm;

	ActionForm actionForm;

	ConditionForm conditionForm;

	TransitionForm transitionForm;

	ParameterForm parameterForm;

	CaseArchitectureForm caseArchitectureForm;

	StateForm stateForm;

	TaskForm taskForm;

	Composite parent;

	FormPage fp;

	Composite container;

	StackLayout layout2;

	FormToolkit toolkit2;

	NodeForm nodeForm;

	Label renameNodeLabel;

	Text renameNodeText;

	Button renameNodeButton;

	Label numberOfNodesLabel;

	Label numberOfNodesValue;

	private SysGraphicalEditor graphed;

	/**
	 * Returns the Main treeviewer, which the Propertyeditor needs for nearly everything
	 * @return treeviewer
	 */
	public TreeViewer getTreeViewer() {
		return this.treeviewer;
	}

	/**
	 * Returns a SysGraphicalEditor Object which is used for recognition of changes in the editor.
	 * You can use graphed.isDirty() so that the program knows it has to save this File
	 * @return graphed
	 */
	public SysGraphicalEditor getGraphed() {
		return this.graphed;
	}

	public Object getChosen() {
		return CHOSEN;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 *      initializing the treeview
	 */
	/**
	 * This Method initializes the PropertyView
	 */
	public void createPartControl(Composite parent) {
		this.parent = parent;
		treeviewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER);
		SashForm shift = new SashForm(parent, 0);
		GridLayout gridL = new GridLayout(3, false);
		shift.setLayout(gridL);

		layout2 = new StackLayout();

		GridData gridD2 = new GridData();
		gridD2.horizontalSpan = 2;
		gridD2.minimumWidth = 200;
		container = new Composite(shift, 0);
		container.setLayoutData(gridD2);
		container.setLayout(layout2);
		initForms();

		treeviewer.setContentProvider(new ActionContentProvider());
		treeviewer.setInput(null);
		treeviewer.setLabelProvider(new ViewLabelProvider());

		menuManager = new MenuManager("#PopupMenu");
		menuManager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		Menu menu = menuManager.createContextMenu(treeviewer.getControl());
		treeviewer.getControl().setMenu(menu);
		// Register the popup menu with the workbench
		// so that other plug-ins can register extensions
		getSite().registerContextMenu(menuManager, treeviewer);

		/**
		 * Add's a SelectionListener, that listens to selections from the
		 * eclipse workbench.
		 */
		getSite().getPage().addSelectionListener((ISelectionListener) this);

		treeviewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				Object selected = ((IStructuredSelection) event.getSelection())
						.getFirstElement();
				CHOSEN = selected;
				updateForm(selected);
			}
		});	
		
		// dynamic help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(treeviewer.getControl(), "de.peerthing.workbench.sysbehavi_propertyview");
		PeerThingSystemBehaviourEditorPlugin.getDefault().setPropertyEditor(this);
	}


	/**
	 * Small Object needed e.g. for the ActionContenProvider
	 */
	class RootElement {
		private Object obj;

		public RootElement(Object obj) {
			this.obj = obj;
		}

		public Object getObject() {
			return obj;
		}
	}

	@Override
	public void setFocus() {
		treeviewer.getControl().setFocus();
	}


	public void initForms() {
		GridLayout layout = new GridLayout(2, true);
		toolkit = new FormToolkit(container.getDisplay());
		form = toolkit.createScrolledForm(container);
		form.setText("Click parts of the tree to get further information.");
		form.getBody().setLayout(layout);

		layout2.topControl = form;

		actionForm = new ActionForm(container);
		conditionForm = new ConditionForm(container);
		transitionForm = new TransitionForm(container);
		parameterForm = new ParameterForm(container);
		caseArchitectureForm = new CaseArchitectureForm(container);
		stateForm = new StateForm(container);
		taskForm = new TaskForm(container);
		nodeForm = new NodeForm(container);
		varForm = new VarForm(container);
	}

	public void updateForm(Object object) {
		if (object instanceof IAction) {
			actionForm.update(object, this);
			layout2.topControl = actionForm.getForm();
			container.layout();
			parent.redraw();
			PlatformUI.getWorkbench().getHelpSystem().setHelp(treeviewer.getControl(), "de.peerthing.workbench.propertyview_action");
		}
		if (object instanceof Condition) {
			conditionForm.update(object, this);
			layout2.topControl = conditionForm.getForm();
			container.layout();
			parent.redraw();
			PlatformUI.getWorkbench().getHelpSystem().setHelp(treeviewer.getControl(), "de.peerthing.workbench.propertyview_condition");
		}

		if (object instanceof ITransition) {
			if (((Transition)object).getState() instanceof ITask) {
				taskForm.update(((Transition)object).getState(), this);
				layout2.topControl = taskForm.getForm();
				container.layout();
				parent.redraw();
				PlatformUI.getWorkbench().getHelpSystem().setHelp(treeviewer.getControl(), "de.peerthing.workbench.propertyview_task");
			} else {
				transitionForm.update(object, this);
				layout2.topControl = transitionForm.getForm();
				container.layout();
				parent.redraw();
				PlatformUI.getWorkbench().getHelpSystem().setHelp(treeviewer.getControl(), "de.peerthing.workbench.propertyview_transition");
				}
			}

		if (object instanceof IParameter) {
			parameterForm.update(object, this);
			layout2.topControl = parameterForm.getForm();
			container.layout();
			parent.redraw();
			PlatformUI.getWorkbench().getHelpSystem().setHelp(treeviewer.getControl(), "de.peerthing.workbench.propertyview_parameter");
		}

		if (object instanceof ICaseArchitecture) {
			if (((ICaseArchitecture)object)== ((ICaseArchitecture)object).getCondition().getDefaultCase()) {
				
			} else {
			caseArchitectureForm.update(object, this);
			layout2.topControl = caseArchitectureForm.getForm();
			container.layout();
			parent.redraw();
			PlatformUI.getWorkbench().getHelpSystem().setHelp(treeviewer.getControl(), "de.peerthing.workbench.propertyview_case");
		}}
		if (object instanceof ITask) {
			taskForm.update(object, this);
			layout2.topControl = taskForm.getForm();
			container.layout();
			parent.redraw();
			PlatformUI.getWorkbench().getHelpSystem().setHelp(treeviewer.getControl(), "de.peerthing.workbench.propertyview_task");
		} 
		else if (object instanceof IState) {
			
			// Peter. If endState, do not show.
			if (object instanceof IState) {
				if (((State)object).isEndState()) {
					return;
				}
			}
			
			stateForm.update(object, this);
			layout2.topControl = stateForm.getForm();
			container.layout();
			parent.redraw();
			PlatformUI.getWorkbench().getHelpSystem().setHelp(treeviewer.getControl(), "de.peerthing.workbench.propertyview_state");
		}

		

		if (object instanceof INodeType) {
			nodeForm.update(object, this);
			layout2.topControl = nodeForm.getForm();
			container.layout();
			parent.redraw();
			PlatformUI.getWorkbench().getHelpSystem().setHelp(treeviewer.getControl(), "de.peerthing.workbench.propertyview_node");
		}

		if (object instanceof IVariable) {
			varForm.update(object, this);
			layout2.topControl = varForm.getForm();
			container.layout();
			parent.redraw();
			PlatformUI.getWorkbench().getHelpSystem().setHelp(treeviewer.getControl(), "de.peerthing.workbench.propertyview_variable");
		}

		if (object instanceof SystemBehaviour) {
			nodeForm.update(object, this);
			layout2.topControl = nodeForm.getForm();
			container.layout();
			parent.redraw();
			PlatformUI.getWorkbench().getHelpSystem().setHelp(treeviewer.getControl(), "de.peerthing.workbench.propertyview_node");
		}
	}

	public void nodeNameChanged(Object object) {
		treeviewer.update(new Object[] { object }, null);
		this.graphed.setDirty();
		PeerThingSystemBehaviourEditorPlugin.getDefault().getNavigationTree().refresh(object);
		parent.redraw();
	}

	public ITransition getTransition() {
		return trans;
	}

	/**
	 * Listens to selections done in the SystemBehaviourEditor (Peter). The only
	 * method that is needed to implement ISelectionListener.
	 * 
	 * @param part
	 * @param selection
	 */
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		StructuredSelection sel = (StructuredSelection) selection;
		Object obj = sel.getFirstElement();
		
		if (part instanceof SysGraphicalEditor) {
			
			if (this.graphed != (SysGraphicalEditor) part) {
				this.graphed = (SysGraphicalEditor) part;
				this.graphed.getTabFolder().addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						for (INodeType node:graphed.getSystemBehaviour().getNodes()) {
							TabItem titmp=((TabFolder)e.getSource()).getSelection()[0];
							if (titmp.getText().equals(node.getName())) { 
								treeviewer.setInput(new RootElement(node));
								updateForm(node);
								treeviewer.expandToLevel(2);
							}
					}			
				}
				});
			}
			
		}

		if (obj instanceof ISystemBehaviourObject) {
			treeviewer.setInput(new RootElement(obj));
			updateForm(obj);
			
		}
		else if (obj instanceof AbstractEditPart) {
			
			// just ignore end-states
			if (obj instanceof StateEditPart) {
				if (  ((State)((StateEditPart)obj).getModel()).isEndState() ) {
					return;
				}
			}
			
			treeviewer.setInput(new RootElement(((AbstractEditPart) obj)
					.getModel()));
			updateForm(((AbstractEditPart) obj).getModel());
		}
		treeviewer.expandToLevel(2);

	}
	public void updateTreeViewer(Object obj) {
		treeviewer.setInput(new RootElement(obj));
	}
}
