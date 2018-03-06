package de.peerthing.visualization;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IViewLayout;

/**
 * Defines the perspective for the visualization component, i.e. the arrangement
 * of the views. On the left side, there is the resource tree view, on the right
 * side the diagram view. Under the resource view, there is a selection view for
 * the queries. Under the diagram view, there is a placeholder for table views
 * that are created when a diagram is shown. This view is not used for the query
 * editor, which has its own view.
 * 
 * @author Tjark
 * @author Michael Gottschalk
 * @review Johannes Fischer
 * 
 */
public class VisualizationPerspective implements IPerspectiveFactory {
    /**
     * The ID for this perspective
     */
    public static final String ID = "de.peerthing.VisualizationPerspective";

    public void createInitialLayout(IPageLayout layout) {
        String editorArea = layout.getEditorArea();
        layout.setEditorAreaVisible(false);
        layout.addStandaloneView("de.peerthing.workbench.ResourceTreeView",
                true, IPageLayout.LEFT, 0.25F, editorArea);
        IViewLayout vl = layout
                .getViewLayout("de.peerthing.workbench.ResourceTreeView");
        vl.setCloseable(false);

        
        IFolderLayout selectionFolder = layout.createFolder("selection", IPageLayout.BOTTOM, 0.65F, "de.peerthing.workbench.ResourceTreeView");
        selectionFolder.addView("de.peerthing.visualization.SelectionView");
        selectionFolder.addView("de.peerthing.visualization.SimDataSelectionView");
        
        vl = layout.getViewLayout("de.peerthing.visualization.SelectionView");
        vl.setCloseable(false);
        vl = layout.getViewLayout("de.peerthing.visualization.SimDataSelectionView");
        vl.setCloseable(false);
        
        
        layout.addStandaloneView("de.peerthing.visualization.ChartView", false,
                IPageLayout.TOP, 0.65F,
                editorArea);
        
        IFolderLayout tableLayout = layout.createFolder("table",
                IPageLayout.BOTTOM, 0.65f,
                "de.peerthing.visualization.ChartView");
        
        tableLayout.addPlaceholder("de.peerthing.visualization.TableView");
    }

}
