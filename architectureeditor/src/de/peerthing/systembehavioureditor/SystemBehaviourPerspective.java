package de.peerthing.systembehavioureditor;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IViewLayout;

/**
 * Defines the main perspective for the modeling of an architecture.
 *
 * @author Michael Gottschalk
 * @review Johannes Fischer
 *
 */
public class SystemBehaviourPerspective implements IPerspectiveFactory {

    public void createInitialLayout(IPageLayout layout) {
        String editorArea = layout.getEditorArea();
        layout.setEditorAreaVisible(false);

		layout.addStandaloneView("de.peerthing.workbench.ResourceTreeView",  true, IPageLayout.LEFT, 0.25f, editorArea);
        IViewLayout vl = layout.getViewLayout("de.peerthing.workbench.ResourceTreeView");
        vl.setCloseable(false);
        
        layout.addStandaloneView("de.peerthing.systembehavioureditor.PropertyEditor", false, IPageLayout.BOTTOM, 0.7f, editorArea);
    }

}
