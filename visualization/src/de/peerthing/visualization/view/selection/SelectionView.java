package de.peerthing.visualization.view.selection;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;

import de.peerthing.visualization.VisualizationPlugin;
import de.peerthing.visualization.querymodel.VisualizationData;
import de.peerthing.visualization.querymodel.interfaces.IQuery;
import de.peerthing.visualization.querymodel.interfaces.IQueryDataModel;
import de.peerthing.visualization.querymodel.interfaces.IVisualizationData;
import de.peerthing.visualization.querymodel.interfaces.IVisualizationData.Styles;

/**
 * View for selecting a query/visualization to show in the chart view / in the
 * tables.
 * 
 * @author Michael Gottschalk
 * 
 */
public class SelectionView extends ViewPart {
    private TreeViewer treeviewer;

    /**
     * The queries that are currently shown in this view
     */
    private List<IQueryDataModel> queries = new ArrayList<IQueryDataModel>();

    /**
     * The constructor. Registers the selection view at the visualization
     * plug-in. Should never be used directly! This should only be used by the
     * workbench.
     * 
     */
    public SelectionView() {
        VisualizationPlugin.getDefault().setSelectionView(this);
    }

    /**
     * Adds a query data model to the list of currently displayed data models.
     * If the data model was already shown (the same filename), it is replaced
     * by the new one (useful e.g. when the file has changed).
     * 
     * @param qdm
     * @param addQDMIfNotExists
     *            If true, then this query is added if is does not already exist
     *            in the tree
     * @param setFocus
     *            If true, then the focus will be set to the tree viewer so that
     *            a selection event is generated.
     */
    public void updateQDM(IQueryDataModel qdm, boolean addQDMIfNotExists,
            boolean setFocus) {
        boolean replaced = false;
        for (int i = 0; i < queries.size(); i++) {
            if (queries.get(i).getFile().equals(qdm.getFile())) {
                replaced = true;
                queries.set(i, qdm);
                break;
            }
        }
        int oldQueryPos = 0;
        int oldVisDataPos = 0;
        if (replaced) {
            Object oldSel = ((IStructuredSelection) treeviewer.getSelection())
                    .getFirstElement();
            if (oldSel instanceof IVisualizationData) {
                IQuery query = ((IVisualizationData) oldSel).getQuery();
                // only use the old positions if the same qdm was selected
                // formerly
                if (query.getQueryDataModel().getFile().equals(qdm.getFile())) {
                    oldVisDataPos = query.getVisualizationData()
                            .indexOf(oldSel);
                    oldQueryPos = query.getQueryDataModel().getQueries()
                            .indexOf(query);
                }
            }
        } else if (addQDMIfNotExists) {
            queries.add(qdm);
        } else {
            // Nothing has changed and the query should not
            // be added: nothing has to be done!
            return;
        }

        treeviewer.refresh();
        treeviewer.expandAll();

        /*
         * Set the current selection to the same element as before - this is not
         * really easy, since the objects have changed. So the positions are
         * used if they still exist.
         */
        IVisualizationData visData = null;
        if (qdm.getQueries().size() > oldQueryPos) {
            IQuery query = qdm.getQueries().get(oldQueryPos);
            if (query.getVisualizationData().size() > oldVisDataPos) {
                visData = query.getVisualizationData(oldVisDataPos);
            } else if (query.getVisualizationData().size() > 0) {
                visData = query.getVisualizationData(0);
            }
        } else if (qdm.getQueries().size() > 0) {
            IQuery query = qdm.getQueries().get(0);
            if (query.getVisualizationData().size() > 0) {
                visData = query.getVisualizationData(0);
            }
        }

        if (visData != null && setFocus) {
            treeviewer.setSelection(new StructuredSelection(visData), true);
            setFocus();
        }
    }

    /**
     * Creates a tree viewer and creates a context menu for this tree viewer.
     */
    public void createPartControl(Composite parent) {
        treeviewer = new TreeViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL
                | SWT.BORDER);
        treeviewer.setContentProvider(new QDMContentProvider());
        treeviewer.setLabelProvider(new SelectionLabelProvider());
        treeviewer.setInput(queries);
        treeviewer.expandAll();
        getSite().setSelectionProvider(treeviewer);
        MenuManager menuManager = new MenuManager();
        menuManager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
        Menu menu = menuManager.createContextMenu(treeviewer.getControl());
        treeviewer.getControl().setMenu(menu);
        menuManager.setRemoveAllWhenShown(true);
        menuManager.addMenuListener(new MyMenuListener());

    }

    /**
     * Registers the "Remove query"-action in the
     * context menu of the tree viewer if a query object
     * is currently selected.
     * 
     * @author Michael Gottschalk
     *
     */
    class MyMenuListener implements IMenuListener {
        public void menuAboutToShow(IMenuManager manager) {
            Object selected = getCurrentSelection();
            if (selected instanceof IQueryDataModel) {
                manager.add(new RemoveQueriesAction());
            }
        }
    }

    /**
     * Returns the currently selected object in the tree viewer.
     * @return
     */
    private Object getCurrentSelection() {
        return ((IStructuredSelection) treeviewer.getSelection())
                .getFirstElement();
    }

    /**
     * This action removes the currently selected
     * query in the tree viewer from the view.
     * It does not delete the query in the query file!
     * 
     * @author Michael Gottschalk
     *
     */
    class RemoveQueriesAction extends Action {

        @Override
        public String getText() {
            return "&Remove";
        }

        @Override
        public void run() {
            Object selected = getCurrentSelection();
            if (selected instanceof IQueryDataModel) {
                queries.remove(selected);
                treeviewer.refresh();
                treeviewer.expandAll();
            }
        }

    }

    /**
     * Content provider for query data model objects for the tree viewer.
     * 
     * @author Michael
     * 
     */
    class QDMContentProvider implements ITreeContentProvider {
        public Object[] getChildren(Object parentElement) {
            if (parentElement instanceof IQueryDataModel) {
                return ((IQueryDataModel) parentElement).getQueries().toArray();
            } else if (parentElement instanceof IQuery) {
                return ((IQuery) parentElement).getVisualizationData()
                        .toArray();
            }

            return new Object[] {};
        }

        public Object getParent(Object element) {
            return null;
        }

        public boolean hasChildren(Object element) {
            if (element instanceof IQueryDataModel) {
                return ((IQueryDataModel) element).getQueries().size() > 0;
            } else if (element instanceof IQuery) {
                return ((IQuery) element).getVisualizationData().size() > 0;
            }

            return false;
        }

        public Object[] getElements(Object inputElement) {
            if (inputElement instanceof List) {
                return ((List) inputElement).toArray();
            }

            return null;
        }

        public void dispose() {
        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

    }

    /**
     * LabelProvider for the tree viewer
     * 
     * @author Gom
     * @author Michael Gottschalk
     * 
     */
    class SelectionLabelProvider extends LabelProvider {
        @Override
        public Image getImage(Object element) {
            VisualizationPlugin plugin = VisualizationPlugin.getDefault();

            if (element instanceof IVisualizationData) {
                Styles style = ((IVisualizationData) element).getStyle();
                return plugin.getIcon(VisualizationData
                        .getIconFileForStyle(style));
            }
            if (element instanceof IQuery) {
                return plugin.getIcon("query.gif");
            }
            if (element instanceof IQueryDataModel) {
                return plugin.getIcon("queryeditor.gif");
            }
            return null;
        }

        public String getText(Object element) {
            return element.toString();
        }
    }

    /**
     * Passing the focus request to the viewer's control.
     */
    public void setFocus() {
        treeviewer.getControl().setFocus();
    }

}