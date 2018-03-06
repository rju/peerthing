package de.peerthing.scenarioeditor.editor;

import java.util.Hashtable;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.EditorPart;

import de.peerthing.scenarioeditor.ScenarioEditorPlugin;
import de.peerthing.scenarioeditor.model.*;
import de.peerthing.scenarioeditor.model.impl.CallUserBehaviour;
import de.peerthing.scenarioeditor.model.impl.Case;
import de.peerthing.scenarioeditor.model.impl.ConnectionCategory;
import de.peerthing.scenarioeditor.model.impl.DefaultCase;
import de.peerthing.scenarioeditor.model.impl.Delay;
import de.peerthing.scenarioeditor.model.impl.ListWithParent;
import de.peerthing.scenarioeditor.model.impl.Listen;
import de.peerthing.scenarioeditor.model.impl.Loop;
import de.peerthing.scenarioeditor.model.impl.NodeCategory;
import de.peerthing.scenarioeditor.model.impl.NodeConnection;
import de.peerthing.scenarioeditor.model.impl.NodeResource;
import de.peerthing.scenarioeditor.model.impl.ResourceCategory;
import de.peerthing.scenarioeditor.model.impl.ScenarioCondition;
import de.peerthing.scenarioeditor.model.impl.ScenarioUndo;
import de.peerthing.scenarioeditor.model.impl.UserAction;
import de.peerthing.scenarioeditor.model.impl.UserBehaviour;
import de.peerthing.scenarioeditor.persistence.ScenarioXMLAdapter;
import de.peerthing.scenarioeditor.editor.actions.RedoAction;
import de.peerthing.scenarioeditor.editor.actions.UndoAction;
import de.peerthing.scenarioeditor.editor.forms.*;

/**
 * This class is the main class of the scenario editor. The navigation tree is
 * created in the file and the reaction of selecting an item is handeled in this
 * class (an according form is show the right side of the scenario editor as
 * reaction.)
 *
 * @author Hendrik, Patrik
 * @review Peter, 2006-03-23
 *
 */
public class ScenarioEditor extends EditorPart implements
        org.eclipse.ui.ISelectionListener {

    /**
     * A unique id 
     */
    public static final String ID = "PeerThing.scenarioView";

    /**
     * A scenario object
     */
    private IScenario scenario;

    /**
     * The file currently working on
     */
    private IFile currentFile;

    /**
     * A created toolkit for SWT
     */
    FormToolkit toolkit;

    /**
     * A scrolled form
     */
    ScrolledForm form;

    /**
     * A parent
     */
    Composite parent;

    /**
     * A formpage to manage page form
     */
    FormPage fp;

    /**
     * A container 
     */
    Composite container;

    /**
     * A stack layout
     */
    StackLayout layout2;

    /**
     * A second created toolkit
     */
    FormToolkit toolkit2;

    /**
     * A second scrolled form
     */
    ScrolledForm form2;

    /**
     * A scrolled form for nodes
     */
    ScrolledForm nodeForm;

    /**
     * Including xmlPanel
     */
    Composite xmlPanel;

    /**
     * The current input
     */
    IEditorInput currentInput;

    /**
     * The file type registration
     */
    private ScenarioFiletypeRegistration filetypeReg;

    /**
     * A Hashtable 
     */
    private Hashtable<FormIdentifier, AbstractScenarioEditorForm> forms = new Hashtable<FormIdentifier, AbstractScenarioEditorForm>();

    private Hashtable<FormIdentifier, Class> formClasses = new Hashtable<FormIdentifier, Class>();

    /**
     * The form that is currently shown in the editor
     */
    private AbstractScenarioEditorForm currentForm;
    
    /**
     * The actions that handle undo/redo
     */
    private Action undoAction, redoAction;

    /**
     * The default constructor for ScenarioEditor
     *
     */
    public ScenarioEditor() {
        super();

        // Assign forms to model elements...
        formClasses.put(new FormIdentifier(CallUserBehaviour.class),
                CallUserBehaviourForm.class);
        formClasses.put(new FormIdentifier(Case.class), CaseForm.class);
        formClasses.put(new FormIdentifier(ScenarioCondition.class),
                ConditionForm.class);
        formClasses.put(new FormIdentifier(ConnectionCategory.class),
                ConnectionCategoryForm.class);
        formClasses.put(
                new FormIdentifier(ListWithParent.class, "Connections"),
                ConnectionOverview.class);
        formClasses.put(new FormIdentifier(DefaultCase.class),
                DefaultCaseForm.class);
        formClasses.put(new FormIdentifier(Delay.class), DelayForm.class);
        formClasses.put(new FormIdentifier(Loop.class), LoopForm.class);
        formClasses.put(new FormIdentifier(ListWithParent.class,
                "Node Behaviours"), NodeBehaviourOverview.class);
        formClasses.put(new FormIdentifier(NodeCategory.class),
                NodeCategoryForm.class);
        formClasses.put(new FormIdentifier(ListWithParent.class,
                "Node Connections"), NodeConnectionOverview.class);
        formClasses.put(new FormIdentifier(NodeConnection.class),
                NodeConnectionForm.class);
        formClasses.put(new FormIdentifier(ListWithParent.class, "Node Categories"),
                NodeOverview.class);
        formClasses.put(new FormIdentifier(ListWithParent.class,
                "Node Resources"), NodeResourceOverview.class);
        formClasses.put(new FormIdentifier(NodeResource.class),
                NodeResourceForm.class);
        formClasses.put(new FormIdentifier(ResourceCategory.class),
                ResourceCategoryForm.class);
        formClasses.put(new FormIdentifier(ListWithParent.class, "Resources"),
                ResourceOverview.class);
        formClasses.put(new FormIdentifier(UserAction.class),
                UserActionForm.class);
        formClasses.put(new FormIdentifier(UserBehaviour.class),
                UserBehaviourForm.class);
        formClasses.put(new FormIdentifier(Listen.class),
                ListenForm.class);
        formClasses.put(new FormIdentifier(ListWithParent.class, "XML"),
        		XMLEditForm.class);

    }        


    /**
     * Creates a new instance of the given class and initializes the form.
     *
     * @param form
     */
    private AbstractScenarioEditorForm initForm(Class formClass) {
        AbstractScenarioEditorForm form = null;
        try {
            form = (AbstractScenarioEditorForm) formClass.newInstance();
        } catch (Exception e) {
            System.out.println("Could not create form: ");
            e.printStackTrace();
        }

        form.createForm(container, this);
        form.setNavigationTree(ScenarioEditorPlugin.getDefault()
                .getNavigationTree());                

        return form;
    }
        

    /**
     * This is a callback that will allow us to create the viewer and initialize
     * it.
     */
    public void createPartControl(final Composite parent) {

        this.parent = parent;
        // SashForm shift = new SashForm(parent, 0);
        // GridLayout gridL = new GridLayout(3, false);
        // shift.setLayout(gridL);
        layout2 = new StackLayout();

        scenario = null;
        try {
            // First look if the file is empty...
            // If it is, then create a new scenario.
            if (currentFile.getContents().read() == -1) {
                scenario = ScenarioFactory.createScenario(); 
            }
        } catch (Exception ex) {
        }
        
        // If no new scenario has been created, then try to
        // load the scenario from the file.
        if (scenario == null) { /* new scenario or empty scenario */
            scenario = ScenarioXMLAdapter.loadScenario(currentFile.getLocation()
                    .toString());
            
        }

        GridData gridD2 = new GridData();
        gridD2.horizontalSpan = 2;
        gridD2.minimumWidth = 200;
        // container = new Composite(shift, 0);
        container = new Composite(parent, 0);

        container.setLayoutData(gridD2);
        container.setLayout(layout2);
        // shift.setWeights(new int[] { 0, 100 });

        GridLayout layout = new GridLayout(2, true);
        toolkit = new FormToolkit(container.getDisplay());
        form = toolkit.createScrolledForm(container);
        form
                .setText("Please navigate in the tree and edit your scenario on the right side");
        form.getBody().setLayout(layout);
        toolkit.createLabel(form.getBody(), "", SWT.NONE);
        layout2.topControl = form;

        // Add the text editor to a new composite
        // which is completely filled and added
        // to the container with the stack layout.
        xmlPanel = new Composite(container, SWT.NONE);
        xmlPanel.setLayout(new FillLayout());
        
        
        //getSite().getPage().addSelectionListener(this);
        undoAction = new UndoAction();
        undoAction.setEnabled(ScenarioUndo.canUndo());
        redoAction = new RedoAction();
        redoAction.setEnabled(ScenarioUndo.canRedo());
        // Register Undo/Redo-Action
        getEditorSite().getActionBars().setGlobalActionHandler(ActionFactory.UNDO.getId(), undoAction);
        getEditorSite().getActionBars().setGlobalActionHandler(ActionFactory.REDO.getId(), redoAction);
    }
    
    public void dispose() {
        super.dispose();

        if (filetypeReg != null) {
            filetypeReg.editorDisposed(this);
        }
    }

    /**
     * This method sets the filetype registration
     * @param reg
     */
    public void setFiletypeRegistration(ScenarioFiletypeRegistration reg) {
        this.filetypeReg = reg;
    }

    /**
     * This method sets the current scenario
     * @param scenario
     */
    public void setScenario(IScenario scenario) {
    	IScenario oldScenario = this.scenario;
    	this.scenario = scenario;

    	if (oldScenario != null) {
    		filetypeReg.scenarioChanged(this, oldScenario, scenario);
    	}
    }

    /**
     * Returns the current scenario.
     *
     * @return
     */
    public IScenario getScenario() {
        return scenario;
    }
    /**
     * This method returns the current File
     * @return
     */
    public IFile getScenarioFile() {
        return currentFile;
    }

    /**
     * Shows the form for the specified object.
     *
     * @param obj
     */
    public void showFormFor(IScenarioObject obj) {
        AbstractScenarioEditorForm form = null;

        // Look in the cache whether the form is
        // already initialized
        FormIdentifier ident = null;
        if (obj instanceof IListWithParent) {
            ident = new FormIdentifier(obj.getClass(), obj.toString());
        } else {
            ident = new FormIdentifier(obj.getClass());
        }

        form = forms.get(ident);

        // Get the form that is resonsible for
        // this object type
        Class formClass = formClasses.get(ident);

        // Initialize the form if necessary
        if (form == null && formClass != null) {
            form = initForm(formClass);
            forms.put(ident, form);
        }

        if (form != null) {
            if (currentForm != null
                    && currentForm.getCurrentlyEditedObject() != null) {
                // Say to the current form that the input
                // will change or another form will be
                // on top soon...
                if (!currentForm.aboutToClose()){
                	return;
                }

                // test if the scenario changed as a result
                // of the former method call:
                if (scenario != obj.getScenario()) {
                	return;
                }
            }

            form.update(obj);

            layout2.topControl = form.getMainForm();
            if (currentForm != form) {
                currentForm = form;
                // Only re-layout the main
                // container if a different form
                // is on top now
                container.layout();
            }
        }

    }
    
    /**
     * This method returns the initialized form
     * @param obj
     * @return
     */
    public AbstractScenarioEditorForm getForm(IScenarioObject obj){

        AbstractScenarioEditorForm form = null;
        // Look in the cache whether the form is
        // already initialized
        FormIdentifier ident = null;
        if (obj instanceof IListWithParent) {
                ident = new FormIdentifier(obj.getClass(), obj.toString());
            } else {
                ident = new FormIdentifier(obj.getClass());
            }

            form = forms.get(ident);

            // Get the form that is resonsible for
            // this object type
            Class formClass = formClasses.get(ident);

            // Initialize the form if necessary
            if (form == null && formClass != null) {
                form = initForm(formClass);
                forms.put(ident, form);
                return form;
            }
            return form;
    }
    

    /**
     * Passing the focus request to the viewer's control.
     */
    public void setFocus() {
        if (currentForm == null) {
        	form.setFocus();
        } else {
        	if (currentForm.getMainForm() != null) {
        		currentForm.getMainForm().setFocus();
        	}
        }
    }

    @Override
    public void doSave(IProgressMonitor monitor) {
    	if (currentForm != null
                && currentForm.getCurrentlyEditedObject() != null) {
            currentForm.applyAllChanges();
        }

        isDirty = false;
        firePropertyChange(PROP_DIRTY);

        ScenarioXMLAdapter.saveScenario(scenario, (currentFile.getLocation()
                .toString()));
    }

    @Override
    public void doSaveAs() {
    }

    /**
     * Indicates that the given object was deleted in the model. If this object
     * is currently edited in a form, this form is closed.
     *
     * @param obj
     */
    public void objectDeleted(IScenarioObject obj) {
        if (currentForm != null) {
            if (currentForm.getCurrentlyEditedObject() == obj) {
                currentForm = null;
                layout2.topControl = form;
                container.layout();
            }
        }
    }

    @Override
    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        // super.init(site, input);

        setSite(site);
        setInput(input);

        currentInput = input;
        currentFile = (IFile) input.getAdapter(IResource.class);
        setPartName(currentFile.getName() /*
                                             * + " - " +
                                             * site.getRegisteredName()
                                             */);

        
        
        // Show the right perspective if this part is
        // shown in another perspective...
        getSite().getWorkbenchWindow().getPartService().addPartListener(
                new IPartListener() {
                    public void partActivated(IWorkbenchPart part) {
                    }

                    public void partBroughtToTop(IWorkbenchPart part) {
                        if (part == ScenarioEditor.this) {
                            try {
                                PlatformUI.getWorkbench().showPerspective(
                                        "de.peerthing.MainPerspective",
                                        PlatformUI.getWorkbench()
                                                .getActiveWorkbenchWindow());
                            } catch (Exception e) {
                                System.out
                                        .println("Could not show perspective:");
                                e.printStackTrace();
                            }
                        }
                    }

                    public void partClosed(IWorkbenchPart part) {
                    }

                    public void partDeactivated(IWorkbenchPart part) {
                    }

                    public void partOpened(IWorkbenchPart part) {
                    }

                });
    }

    boolean isDirty = false;

    @Override
    /**
     * return if the the user should be asked if he wants to save the file, when
     * he is about to close it.
     */
    public boolean isDirty() {
        return isDirty;
    }

    @Override
    public boolean isSaveAsAllowed() {
        return false;
    }


    /**
     * Sets this editor's state to dirty.
     *
     */
    public void setDirty() {
        undoAction.setEnabled(ScenarioUndo.canUndo());
        redoAction.setEnabled(ScenarioUndo.canRedo());
        
        if (!isDirty) {
            isDirty = true;
            firePropertyChange(PROP_DIRTY);
        }
    }

    public void selectionChanged(IWorkbenchPart part, ISelection selection) {    	    	
    	
    	//getEditorSite().getActionBars().setGlobalActionHandler(ActionFactory.UNDO.getId(), undoAction);
        //undoAction.setEnabled(ScenarioUndo.canUndo());
        //getEditorSite().getActionBars().updateActionBars();
    }

}
