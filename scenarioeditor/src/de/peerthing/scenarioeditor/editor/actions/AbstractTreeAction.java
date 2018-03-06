package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import de.peerthing.scenarioeditor.ScenarioEditorPlugin;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.workbench.filetyperegistration.INavigationTree;

/**
 * An abstract class which allows to manipulate selected objects by pop-up menus
 * @author Hendrik Angenendt
 */
public abstract class AbstractTreeAction implements IObjectActionDelegate {
    /**
     * The object which is selected
     */
    protected Object firstSelectedObject;

    /**
     * Multiple objects which are selected
     */
    protected Object[] selectedObjects;

    /**
     * Method run if a Object is selected. The selected object(s) are saved in variable/array.
     * Type: Object 
     */
    public void selectionChanged(IAction action, ISelection selection) {
        if (selection instanceof IStructuredSelection) {
            firstSelectedObject = ((IStructuredSelection) selection)
                    .getFirstElement();
            selectedObjects = ((IStructuredSelection) selection).toArray();
        }
    }
    /**
     * This method returns the current navigation tree
     * @return
     */
    protected INavigationTree getTree() {
        return ScenarioEditorPlugin.getDefault().getNavigationTree();
    }
    
    /**
     * This method sets the active part (not necessary here)
     */
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
    }

    /**
     * This method sets an object as deleted. SetDirty is used to set the editor state to dirty
     * @param obj
     */
    public void objectDeleted(IScenarioObject obj) {
        ScenarioEditorPlugin.getDefault().getFiletypeRegistration().getEditor(
                obj.getScenario()).objectDeleted(obj);
        setDirty(obj);
    }
    
    /**
     * This method is used to set the editor state to dirty
     * @param obj
     */
    public void setDirty(IScenarioObject obj) {
        ScenarioEditorPlugin.getDefault().getFiletypeRegistration().getEditor(
                obj.getScenario()).setDirty();
    }

}
