package de.peerthing.simulation.gui;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class EvaluateXPathAction implements IViewActionDelegate {
	DebugView view;

	private String lastExpression = "";

	public void init(IViewPart view) {
		if (view instanceof DebugView) {
			this.view = (DebugView) view;
		}
	}

	public void run(IAction action) {
		if (view != null) {
			InputDialog in = new InputDialog(view.getSite().getShell(),
					"Enter XPath-Expression",
					"Enter the XPath expression here:", lastExpression, null);
			if (in.open() == InputDialog.CANCEL) {
				return;
			}
			lastExpression = in.getValue();
			view.evaluateXPathExpression(in.getValue());
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
