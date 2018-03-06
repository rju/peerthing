package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import de.peerthing.scenarioeditor.model.impl.ScenarioUndo;

/**
 * This class allows to undo an operation.
 * @author Patrik
 */

public class UndoAction extends Action implements IWorkbenchWindowActionDelegate {
	
    /**
     * A IWorkbenchWindow
     */
    IWorkbenchWindow window;

	public void dispose() {
	}

    /**
     * This method initializes the IWorkbenchWindow
     */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

    /**
     * This method executes the undo operation. Called automatically if according menu point is 
     * selected. 
     */
	public void run(IAction action) {
		try {
			ScenarioUndo.executeUndo(true);
			//window.getWorkbench().showPerspective("de.peerthing.SimulationPerspective", window);
		} catch (Exception e) {			
			//System.out.println("Could not show Simulator perspective: ");
			e.printStackTrace();
		}
	}

    /**
     * Method run if some selection is made.(Not necessary in this class)
     */
	public void selectionChanged(IAction action, ISelection selection) {
	}

    @Override
    public void run() {
        ScenarioUndo.executeUndo(true);
        //setEnabled(ScenarioUndo.canUndo());
    }

}
