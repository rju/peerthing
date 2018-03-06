package de.peerthing.visualization.queryeditor;

import java.util.Hashtable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import de.peerthing.visualization.VisualizationPlugin;
import de.peerthing.visualization.queryeditor.forms.AbstractQueryEditorForm;
import de.peerthing.visualization.queryeditor.forms.OverviewForm;
import de.peerthing.visualization.queryeditor.forms.QueryForm;
import de.peerthing.visualization.queryeditor.forms.VisualizationDataForm;
import de.peerthing.visualization.querymodel.Query;
import de.peerthing.visualization.querymodel.QueryDataModel;
import de.peerthing.visualization.querymodel.VisualizationData;
import de.peerthing.visualization.querymodel.interfaces.IQueryDataModel;
import de.peerthing.visualization.querymodel.interfaces.IQueryModelObject;
import de.peerthing.visualization.querypersistence.QueryPersistence;
import de.peerthing.visualization.view.selection.SelectionView;

/**
 * Main class defining the query editor.
 * 
 * @author Michael Gottschalk
 * 
 */
public class QueryEditor extends EditorPart {
    private IQueryDataModel data;

    private boolean dirty = false;

    private StackLayout stackLayout;

    private Composite mainContainer;

    /**
     * In this hashtable, the forms for the classes of each model object are
     * defined.
     */
    private Hashtable<Class, AbstractQueryEditorForm> forms = new Hashtable<Class, AbstractQueryEditorForm>();

    private Hashtable<Class, Class> formClasses = new Hashtable<Class, Class>();

    /**
     * The form that is currently shown in the editor
     */
    private AbstractQueryEditorForm currentForm = null;

    public QueryEditor() {
        // Set the forms that can handle different classes...
        formClasses.put(Query.class, QueryForm.class);
        formClasses.put(VisualizationData.class, VisualizationDataForm.class);
        formClasses.put(QueryDataModel.class, OverviewForm.class);
    }

    @Override
    public void doSave(IProgressMonitor monitor) {
        if (currentForm != null
                && currentForm.getCurrentlyEditedObject() != null) {
            currentForm.applyAllChanges();
        }

        dirty = false;
        firePropertyChange(PROP_DIRTY);

        QueryPersistence p = new QueryPersistence();
        p.saveToXml(data);

        // Notify the query view that the file has changed...
        SelectionView queryView = VisualizationPlugin.getDefault().getSelectionView();
        if (queryView != null) {
            queryView.updateQDM(data, false, false);
        }

    }

    /**
     * Save as is not supported.
     */
    @Override
    public void doSaveAs() {
    }

    @Override
    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        setSite(site);
        setInput(input);

        data = (IQueryDataModel) input.getAdapter(IQueryDataModel.class);

        setPartName(data.getFile().getName());

        // Show the right perspective if this part is
        // shown in another perspective...
        getSite().getWorkbenchWindow().getPartService().addPartListener(
                new IPartListener() {
                    public void partActivated(IWorkbenchPart part) {
                    }

                    public void partBroughtToTop(IWorkbenchPart part) {
                        if (part == QueryEditor.this) {
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

    @Override
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Save as is not allowed for this editor.
     */
    @Override
    public boolean isSaveAsAllowed() {
        return false;
    }

    /**
     * Creates a StackLayout to which the forms are added when needed.
     */
    @Override
    public void createPartControl(Composite parent) {
        parent.setLayout(new FillLayout());
        stackLayout = new StackLayout();
        mainContainer = new Composite(parent, SWT.NONE);
        mainContainer.setLayout(stackLayout);

        // Initially, show the overview form
        showViewFor(data);
    }

    /**
     * This method currently does nothing.
     */
    @Override
    public void setFocus() {
    }

    /**
     * Indicates that the given object was deleted in the model. If this object
     * is currently edited in a form, this form is closed.
     * 
     * @param obj
     */
    public void objectDeleted(IQueryModelObject obj) {
        if (currentForm != null) {
            if (currentForm.getCurrentlyEditedObject() == obj) {
                showViewFor(data);
            }
        }
    }

    /**
     * Shows the view for the specified object. The form is created if it does
     * not already exist.
     * 
     * @param obj
     *            The object to edit
     */
    public void showViewFor(IQueryModelObject obj) {
        AbstractQueryEditorForm form = null;

        // Look in the cache whether the form is
        // already initialized
        form = forms.get(obj.getClass());

        // Get the form that is resonsible for
        // this object type
        Class formClass = formClasses.get(obj.getClass());

        // Initialize the form if necessary
        if (form == null && formClass != null) {
            form = initForm(formClass);
            forms.put(obj.getClass(), form);
        }

        if (form != null) {
            if (currentForm != null
                    && currentForm.getCurrentlyEditedObject() != null) {
                // Say to the current form that the input
                // will change or another form will be
                // on top soon...
                currentForm.aboutToClose();
            }

            form.update(obj);

            stackLayout.topControl = form.getMainForm();
            if (currentForm != form) {
                currentForm = form;
                // Only re-layout the main
                // container if a different form
                // is on top now
                mainContainer.layout();
            }
        }

    }

    /**
     * Creates a new instance of the given class and initializes the form.
     * 
     * @param form
     */
    private AbstractQueryEditorForm initForm(Class formClass) {
        AbstractQueryEditorForm form = null;
        try {
            form = (AbstractQueryEditorForm) formClass.newInstance();
        } catch (Exception e) {
            System.out.println("Could not create form: ");
            e.printStackTrace();
        }

        form.createForm(mainContainer, this);
        form.setNavigationTree(VisualizationPlugin.getDefault()
                .getNavigationTree());

        return form;
    }

    /**
     * Notifies the filetype registration that the editor
     * is closed. That means that the tree in the resource
     * view must be closed, too.
     */
    @Override
    public void dispose() {
        VisualizationPlugin.getDefault().getQueryFiletypeRegistration()
                .editorClosed(data.getFile());
    }

    /**
     * Sets this editor's state to dirty.
     * 
     */
    public void setDirty() {
        if (!dirty) {
            dirty = true;
            firePropertyChange(PROP_DIRTY);
        }
    }

}
