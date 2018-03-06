package de.peerthing.simulation.gui;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * This class implements an action which shows the simulation perspective. It is
 * used by the menu and toolbar entry for the simulation defined in the
 * plugin.xml.
 * 
 * @author Michael Gotschalk
 * @review Johannes Fischer
 * 
 */
public class ShowSimulationAction implements IWorkbenchWindowActionDelegate {
	IWorkbenchWindow window;

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	public void run(IAction action) {
		try {
			window.getWorkbench().showPerspective(
					"de.peerthing.SimulationPerspective", window);
		} catch (Exception e) {
			System.out.println("Could not show Simulator perspective: ");
			e.printStackTrace();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
