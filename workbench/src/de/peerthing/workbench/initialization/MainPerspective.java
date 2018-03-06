package de.peerthing.workbench.initialization;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IViewLayout;

/**
 * The perspective definition for the main view.
 * It includes the navigation view on the left side
 * and an editor area.
 * 
 *
 * @author Michael Gottschalk
 * @review Tjark
 *
 */
public class MainPerspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();	
		layout.addStandaloneView("de.peerthing.workbench.ResourceTreeView",  true, IPageLayout.LEFT, 0.25f, editorArea);
        IViewLayout vl = layout.getViewLayout("de.peerthing.workbench.ResourceTreeView");
        vl.setCloseable(false);
	}
}
