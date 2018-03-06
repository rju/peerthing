package de.peerthing.visualization.queryeditor.forms;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;


import de.peerthing.visualization.queryeditor.QueryEditor;
import de.peerthing.visualization.querymodel.interfaces.IQueryModelObject;
import de.peerthing.workbench.filetyperegistration.INavigationTree;

/**
 * Superclass of all forms used in the query editor.
 * It includes common functions that are useful for
 * updating a form and synchronizing the form content
 * with the model and the resource view.
 * 
 * @author Michael Gottschalk
 *
 */
public abstract class AbstractQueryEditorForm implements SelectionListener {
    protected FormToolkit toolkit;
    protected ScrolledForm form;
    protected INavigationTree tree;
    protected IQueryModelObject currentObject;
    protected QueryEditor queryEditor;
    
    public void widgetDefaultSelected(SelectionEvent e) {
    }

    public void widgetSelected(SelectionEvent e) {
    }
    
    public void setNavigationTree(INavigationTree tree) {
        this.tree = tree;
    }

    /**
     * Creates the content of the form in the given
     * parent Composite.
     * 
     * @param parent The parent composite in which to create
     *                  elements
     * @param editor The editor to which this form belongs
     */
    public void createForm(Composite parent, QueryEditor editor) {
        queryEditor = editor;
        toolkit = new FormToolkit(parent.getDisplay());
        form = toolkit.createScrolledForm(parent);
        toolkit.paintBordersFor(form.getBody());
    }
    
    /**
     * Returns the form in which all other components
     * are included.
     * 
     * @return
     */
    public ScrolledForm getMainForm() {
        return form;
    }
    
    /**
     * Updates the controls in the form with the given
     * data model object.
     * 
     * @param object
     */
    public abstract void update(IQueryModelObject object);
    
    /**
     * This method is called when a different form is
     * about to be showed or if the input for this
     * form is about to change. Here, The user can be asked
     * if he wants to apply the changes he made to the form.
     *
     */
    public abstract void aboutToClose();
    
    /**
     * This method should apply all changes made
     * in the form to the underlying data model.
     * The method is called when the current file
     * should be saved, for example.
     *
     */
    public abstract void applyAllChanges();
    
    /**
     * Refreshes the currently edited element
     * in the resource tree
     *
     */
    protected void refreshTree() {
        tree.refresh(currentObject);
    }
    
    /**
     * Returns the currently edited object
     * @return
     */
    public IQueryModelObject getCurrentlyEditedObject() {
        return currentObject;
    }
}
