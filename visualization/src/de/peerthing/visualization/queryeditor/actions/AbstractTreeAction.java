package de.peerthing.visualization.queryeditor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;


import de.peerthing.visualization.VisualizationPlugin;
import de.peerthing.visualization.queryeditor.QueryFiletypeRegistration;
import de.peerthing.visualization.querymodel.interfaces.IQueryModelObject;
import de.peerthing.workbench.filetyperegistration.INavigationTree;

/**
 * An Action that all popup menu actions defined 
 * for the query editor can use as a superclass. 
 * There are some helper methods defined here. 
 * 
 * @author Michael Gottschalk
 * 
 */
public abstract class AbstractTreeAction implements IObjectActionDelegate {
    protected Object firstSelectedObject;

    protected Object[] selectedObjects;

    public void selectionChanged(IAction action, ISelection selection) {
        if (selection instanceof IStructuredSelection) {
            firstSelectedObject = ((IStructuredSelection) selection)
                    .getFirstElement();
            selectedObjects = ((IStructuredSelection) selection).toArray();
        }
    }

    protected INavigationTree getTree() {
        return VisualizationPlugin.getDefault().getNavigationTree();
    }

    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
    }

    public QueryFiletypeRegistration getFiletypeRegistration() {
        return VisualizationPlugin.getDefault().getQueryFiletypeRegistration();
    }

    /**
     * This method should be called if something in an action changed the data
     * model so that saving the model to disk is required.
     * 
     */
    public void modelChanged(IQueryModelObject obj) {
        getFiletypeRegistration().getEditor(obj.getQueryDataModel().getFile())
                .setDirty();
    }
    
    /**
     * This method should be called if something in an action
     * deleted a model object so that a view in the editor
     * must possibly be updated and saving the model to 
     * disk is required.
     * 
     * @param obj
     */
    public void modelObjectDeleted(IQueryModelObject obj) {
        modelChanged(obj);
        getFiletypeRegistration().getEditor(obj.getQueryDataModel().getFile()).objectDeleted(
                obj);
    }

}
