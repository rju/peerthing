package de.peerthing.visualization.view.selection;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;

import de.peerthing.visualization.VisualizationPlugin;
import de.peerthing.visualization.simulationpersistence.DBinterface;

public class SimDataSelectionView extends ViewPart {
    TreeViewer treeviewer;

    public SimDataSelectionView() {
        VisualizationPlugin.getDefault().setSimDataSelView(this);
    }

    @Override
    public void createPartControl(Composite parent) {
        treeviewer = new TreeViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL
                | SWT.BORDER | SWT.MULTI);
        treeviewer.setContentProvider(new SimDataSelectionContentProvider());
        treeviewer.setLabelProvider(new SimDataSelectionLabelProvider());
        treeviewer.setInput(VisualizationPlugin.getDefault());
        treeviewer.expandAll();
        getSite().setSelectionProvider(treeviewer);
        MenuManager menuManager = new MenuManager();
        menuManager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
        Menu menu = menuManager.createContextMenu(treeviewer.getControl());
        treeviewer.getControl().setMenu(menu);
        getSite().registerContextMenu(menuManager, treeviewer);
    }

    /**
     * This method should be called when the user opened or closed a database.
     * 
     */
    public void selectedDatabasesChanged() {
        // There were exceptions in some cases,
        // so isDisposed() is queried here
        if (!treeviewer.getControl().isDisposed()) {
            treeviewer.refresh();
            treeviewer.expandToLevel(2);
        }
    }

    class SimDataSelectionContentProvider implements ITreeContentProvider {

        public Object[] getChildren(Object parentElement) {
            if (parentElement instanceof DBinterface) {
                DBinterface db = (DBinterface) parentElement;
                Object[] ret = new Object[db.getSelectedRuns().size()];
                
                for (int i=0; i<db.getSelectedRuns().size(); i++) {
                    SelectedSimulationRun selRun = new SelectedSimulationRun(db, db.getSelectedRuns().get(i));
                    ret[i] = selRun;
                }
                
                return ret;
            }

            return null;
        }

        public Object getParent(Object element) {
            return null;
        }

        public boolean hasChildren(Object element) {
            if (element instanceof DBinterface) {
                return true;
            }
            return false;
        }

        public Object[] getElements(Object inputElement) {
            if (inputElement instanceof VisualizationPlugin) {
                return ((VisualizationPlugin) inputElement)
                        .getCurrentlySelectedDatabases().toArray();
            }
            return null;
        }

        public void dispose() {
        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

    }

    class SimDataSelectionLabelProvider extends LabelProvider {
        @Override
        public String getText(Object element) {
            if (element instanceof DBinterface) {
                return ((DBinterface) element).getDatabase().getName();
            }
            if (element instanceof SelectedSimulationRun) {
                return "Simulation Run " + ((SelectedSimulationRun) element).getRunNumber();
            }
            return element.toString();
        }

    }

    @Override
    public void setFocus() {

    }

    /**
     * Returns the currently selected object in the tree viewer.
     * 
     * @return
     */
    public Object[] getCurrentSelection() {
        if (treeviewer.getSelection() != null) {
            return ((IStructuredSelection) treeviewer.getSelection())
                    .toArray();
        }
        return null;
    }

}
