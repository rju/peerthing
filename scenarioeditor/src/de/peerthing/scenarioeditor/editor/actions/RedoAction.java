package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import de.peerthing.scenarioeditor.model.impl.ScenarioUndo;

/**
 * This class allows to redo a cancelled operation 
 * @author Patrik
 */

public class RedoAction extends Action implements IWorkbenchWindowActionDelegate {
    
    /**
     * A IWorkbench window
     */
	IWorkbenchWindow window;

	public void dispose() {
	}

    /**
     * This method initialize the IWorkbench window
     */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

    /**
     * This method executes the redo operation. Called automatically if according menu point is 
     * selected.
     */
	public void run(IAction action) {
		try {
			ScenarioUndo.executeUndo(false);			
			//window.getWorkbench().showPerspective("de.peerthing.SimulationPerspective", window);
		} catch (Exception e) {
			// TODO: Notify the user of the error.			
			System.out.println("Could execute redo: ");
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
        ScenarioUndo.executeRedo();
        //setEnabled(ScenarioUndo.canRedo());
    }

}