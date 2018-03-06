package de.peerthing.scenarioeditor.editor.forms;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;


import de.peerthing.scenarioeditor.editor.ScenarioEditor;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.workbench.filetyperegistration.INavigationTree;

/**
 * An abstract class which is used for setting the viewable forms
 * @author 
 *
 */
public abstract class AbstractScenarioEditorForm implements SelectionListener {
    /**
     * A instanced toolkit for creating SWT controls
     */
    protected FormToolkit toolkit;
    
    /**
     * A instance of a scrolled form
     */
    protected ScrolledForm form;
    
    /**
     * The current navigation tree
     */
    protected INavigationTree tree
    
    /**
     * The current scenario object
     */;
    protected IScenarioObject currentObject;
    
    /**
     * The current scenario editor object
     */
    protected ScenarioEditor scenarioEditor;

    /**
     * (not necessary now)
     */
    public void widgetDefaultSelected(SelectionEvent e) {
    }

    /**
     * (not necessary now)
     */
    public void widgetSelected(SelectionEvent e) {    	
    }

    /**
     * This method sets the current navigation tree
     * @param tree
     */
    public void setNavigationTree(INavigationTree tree) {
        this.tree = tree;
    }

    /**
     * This method is used to create  a current form
     * @param parent
     * @param editor
     */
    public void createForm(Composite parent, ScenarioEditor editor) {
        scenarioEditor = editor;
        toolkit = new FormToolkit(parent.getDisplay());
        form = toolkit.createScrolledForm(parent);
        toolkit.paintBordersFor(form.getBody());
    }

    public ScrolledForm getMainForm() {
        return form;
    }

    public abstract void update(IScenarioObject object);

    /**
     * This method is called when a different form is
     * about to be showed or if the input for this
     * form is about to change. Here, The user can be asked
     * if he wants to apply the changes he made to the form.
     *
     */
    public abstract boolean aboutToClose();

    /**
     * This method should apply all changes made
     * in the form to the underlying data model.
     * The method is called when the current file
     * should be saved, for example.
     *
     */
    public abstract void applyAllChanges();

    protected void refreshTree() {
        tree.refresh(currentObject);
    }

    public IScenarioObject getCurrentlyEditedObject() {
        return currentObject;
    }

    /**
     * Sets the dirty flag on the scenario editor to which
     * this form belongs.
     *
     */
    protected void setDirty() {
    	scenarioEditor.setDirty();
    }
}
