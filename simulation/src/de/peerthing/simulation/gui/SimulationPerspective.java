/*
 * Created on 05.12.2005
 *
 */
package de.peerthing.simulation.gui;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IViewLayout;

/**
 * Defines the perspective used for the GUI of the simulation.
 * 
 * @author Michael Gottschalk
 * @review Johannes Fischer
 */
public class SimulationPerspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		layout.addStandaloneView("de.peerthing.workbench.ResourceTreeView",
				true, IPageLayout.LEFT, 0.25f, editorArea);
		IViewLayout vl = layout
				.getViewLayout("de.peerthing.workbench.ResourceTreeView");
		vl.setCloseable(false);

		layout.addStandaloneView("de.peerthing.simulation.View", false,
				IPageLayout.RIGHT, 0.25f, "de.peerthing.workbench.ResourceTreeView");
		layout.addPlaceholder("de.peerthing.simulation.DebugView",
				IPageLayout.BOTTOM, 0.65f, "de.peerthing.simulation.View");
	}

}
