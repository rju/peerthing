package de.peerthing.visualization.queryeditor;

import java.util.Hashtable;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;


import de.peerthing.visualization.VisualizationPlugin;
import de.peerthing.visualization.querymodel.VisualizationData;
import de.peerthing.visualization.querymodel.interfaces.IListWithParent;
import de.peerthing.visualization.querymodel.interfaces.IQuery;
import de.peerthing.visualization.querymodel.interfaces.IQueryDataModel;
import de.peerthing.visualization.querymodel.interfaces.IQueryModelObject;
import de.peerthing.visualization.querymodel.interfaces.IVisualizationData;
import de.peerthing.visualization.querymodel.interfaces.IVisualizationData.Styles;
import de.peerthing.visualization.querypersistence.QueryPersistence;
import de.peerthing.workbench.filetyperegistration.IFileTypeRegistration;
import de.peerthing.workbench.filetyperegistration.INavigationTree;

/**
 * Registers the filetype ".qdef" at the peerthing
 * workbench. The tree to show below these files is
 * defined here and the commands needed to show the
 * query editor.
 *
 * @author Michael Gottschalk
 *
 */
public class QueryFiletypeRegistration implements IFileTypeRegistration {
    private INavigationTree tree;

    /**
     * The data model objects that are currently loaded.
     */
    private Hashtable<IFile, IQueryDataModel> dataModels;

    /**
     * Assigns the editors that are currently shown 
     * to the filenames of the files they edit.
     */
    private Hashtable<IFile, QueryEditor> editors;

    public QueryFiletypeRegistration() {
        VisualizationPlugin.getDefault().setQueryFiletypeRegistration(this);
        dataModels = new Hashtable<IFile, IQueryDataModel>();
        editors = new Hashtable<IFile, QueryEditor>();
    }

    /**
     * Should be called by the editor if it is disposed.
     * Removes the editor and the datamodel from the list
     * of opened editors and files.
     * 
     */
    public void editorClosed(IFile file) {
        editors.remove(file);
        dataModels.remove(file);
        tree.collapseToLevel(file, 1);
        tree.refresh(file);
    }

    /**
     * Returns the ending "qdef" since the 
     * editor can edit these files.
     */
    public String[] getFileNameEndings() {
        return new String[] { "qdef" };
    }

    /**
     * Returns true since the query editor
     * wants to be the default editor for
     * the supported file type.
     */
    public boolean wantsToBeDefaultEditor() {
        return true;
    }

    public String getComponentName() {
        return "Query Editor";
    }

    /**
     * Shows the perspective for the query editor
     * (The main perspective defined in PeerThing workbench)
     * 
     */
    private void showPerspective() {
        try {
            PlatformUI.getWorkbench().showPerspective(
                    "de.peerthing.MainPerspective",
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow());
        } catch (WorkbenchException e) {
            System.out.println("Could not show default perspective: ");
            e.printStackTrace();
        }
    }

    public void showComponent(IFile[] inputFiles) {
        showPerspective();

        for (IFile file : inputFiles) {
            getEditor(file);
        }
    }

    public Object[] getTreeElements(IFile file) {
        IQueryDataModel model = loadDataModel(file);
        return model.getQueries().toArray();
    }

    /**
     * Load the data model from the given file, or, if it is already loaded,
     * returns it from from the hashtable.
     * 
     * @return
     */
    public IQueryDataModel loadDataModel(IFile file) {
        IQueryDataModel model = dataModels.get(file);
        if (model == null) {
            QueryPersistence p = new QueryPersistence();
            model = p.loadQueries(file);
            dataModels.put(file, model);
        }
        return model;
    }

    /**
     * Returns the editor for the given file. If it is not already shown, then
     * it is created.
     * 
     * @param file
     */
    public QueryEditor getEditor(IFile file) {
        QueryEditor editor = editors.get(file);
        if (editor == null) {
            try {
                editor = (QueryEditor) PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getActivePage().openEditor(
                                new MyEditorInput(loadDataModel(file)),
                                "de.peerthing.visualization.QueryEditor");
            } catch (Exception e) {
                System.out.println("Could not create editor: ");
                e.printStackTrace();
            }
            editors.put(file, editor);
            tree.expandToLevel(file, 1);
        }

        return editor;
    }

    /**
     * Returns the data model object that belongs
     * to the given file, if this file is currently
     * opened. Returns null otherwise
     * 
     * @param file
     * @return the data model object or null if the given
     *          file is not currently opened
     */
    public IQueryDataModel getDataModel(IFile file) {
        return dataModels.get(file);
    }

    public ITreeContentProvider getSubtreeContentProvider() {
        return new ITreeContentProvider() {

            public Object[] getChildren(Object parentElement) {
                if (parentElement instanceof IQuery) {
                    return ((IQuery) parentElement).getVisualizationData()
                            .toArray();
                } else if (parentElement instanceof IListWithParent) {
                    return ((IListWithParent) parentElement).toArray();
                }

                return null;
            }

            public Object getParent(Object element) {
                return null;
            }

            public boolean hasChildren(Object element) {
                if (element instanceof IListWithParent) {
                    return ((IListWithParent) element).size() > 0;
                }
                if (element instanceof IQuery) {
                    return ((IQuery) element).getVisualizationData().size() > 0;
                }
                if (element instanceof IVisualizationData) {
                    return false;
                }

                return false;
            }

            public Object[] getElements(Object inputElement) {
                return null;
            }

            public void dispose() {
            }

            public void inputChanged(Viewer viewer, Object oldInput,
                    Object newInput) {
            }

        };
    }

    public ILabelProvider getSubtreeLabelProvider() {
        return new LabelProvider() {
            VisualizationPlugin plugin = VisualizationPlugin.getDefault();

            @Override
            public Image getImage(Object element) {
                if (element instanceof IVisualizationData) {
                    Styles style = ((IVisualizationData) element).getStyle();
                    return plugin.getIcon(VisualizationData
                            .getIconFileForStyle(style));
                }
                if (element instanceof IQuery) {
                    return plugin.getIcon("query.gif");
                }
                return null;
            }

            @Override
            public String getText(Object element) {
                return element.toString();
            }

        };
    }

    public boolean canHandleSubtreeObject(Object obj) {
        return (obj instanceof IQueryModelObject);
    }

    public boolean hasSubTree(IFile file) {
        return true;
    }

    public void setNavigationTree(INavigationTree navigationTree) {
        tree = navigationTree;
        VisualizationPlugin.getDefault().setNavigationTree(navigationTree);
    }

    /**
     * Shows the editor and updates it with the selected 
     * element.
     */
    public void subTreeElementSelected(Object subTreeElement) {
        if (!(subTreeElement instanceof IQueryModelObject)) {
            return;
        }
        showPerspective();

        IQueryModelObject obj = (IQueryModelObject) subTreeElement;
        QueryEditor editor = getEditor(obj.getQueryDataModel().getFile());
        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                .bringToTop(editor);
        editor.showViewFor(obj);
    }

    /**
     * No action defined for double clicks
     */
    public void subTreeElementDoubleClicked(Object subTreeElement) {
    }

    public String[] getNewFileDefinition() {
        return new String[] { "Query definition", "qdef" };
    }

}
