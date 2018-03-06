package de.peerthing.simulation.gui;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class RefreshDebugViewAction implements IViewActionDelegate {
	DebugView view;

	public void init(IViewPart view) {
		if (view instanceof DebugView) {
			this.view = (DebugView) view;
		}
	}

	public void run(IAction action) {
		if (view != null) {
			view.refreshCurrentlyDisplayedObject();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
