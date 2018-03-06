package de.peerthing.workbench.resourcetreeview;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import de.peerthing.workbench.filetyperegistration.IFileTypeRegistration;
import de.peerthing.workbench.filetyperegistration.INavigationTree;



/**
 * This view displays the projects and files using the Eclipse Resources
 * framework.
 * 
 * @author Michael Gottschalk
 * 
 */
public class NavigationView extends ViewPart {
    public static final String ID = "de.peerthing.workbench.resourcetreeview.NavigationView";

    private TreeViewer viewer;

    private ArrayList<IFileTypeRegistration> fileTypeRegistration = new ArrayList<IFileTypeRegistration>();

    private Hashtable<String, ArrayList<IFileTypeRegistration>> filetypes;

    private MenuManager menuManager;


    /**
     * Listens for double-clicks in the tree viewer. On
     * double-clicks, a file is opened with the matching
     * editor and on other elements, the tree is expanded
     * or collapsed.
     * 
     * @author Michael Gottschalk
     *
     */
    class DblClickListener implements IDoubleClickListener {
        TreeViewer viewer;

        public DblClickListener(TreeViewer viewer) {
            this.viewer = viewer;
        }

        public void doubleClick(DoubleClickEvent event) {
            ISelection sel = viewer.getSelection();
            Object firstSelectedElement = null;
            if (sel instanceof IStructuredSelection) {
                firstSelectedElement = ((IStructuredSelection) sel)
                        .getFirstElement();
            }

            if (firstSelectedElement instanceof IResource) {
                IResource res = (IResource) firstSelectedElement;

                if (res instanceof IContainer) {
                    if (viewer.getExpandedState(res)) {
                        viewer.collapseToLevel(res, 1);
                    } else {
                        viewer.expandToLevel(res, 1);
                    }
                    return;
                } else if (res instanceof IFile) {
                    String ending = res.getFileExtension();
                    if (ending != null) {
                        // look for the default editor of the file
                        // and show that editor
                        ArrayList<IFileTypeRegistration> regs = filetypes
                                .get(ending);
                        if (regs != null) {
                            for (IFileTypeRegistration reg : regs) {
                                if (reg.wantsToBeDefaultEditor()) {
                                    reg
                                            .showComponent(new IFile[] { (IFile) res });
                                    break;
                                }
                            }
                        }
                    }
                }
            } else {
                // Always expand an element on double click:
                if (viewer.getExpandedState(firstSelectedElement)) {
                    viewer.collapseToLevel(firstSelectedElement, 1);
                } else {
                    viewer.expandToLevel(firstSelectedElement, 1);
                }

                // If the selected element is part of a subtree
                // provided by another component, then
                // notify that component of the double click
                for (IFileTypeRegistration reg : fileTypeRegistration) {
                    if (reg.canHandleSubtreeObject(firstSelectedElement)) {
                        reg.subTreeElementDoubleClicked(firstSelectedElement);
                    }
                }
            }

        }
    }

    /**
     * Listens for selections in the resource view and
     * propagates them to the registered editors.
     * 
     * @author Michael Gottschalk
     *
     */
    class MySelectionChangedListener implements ISelectionChangedListener {
        public void selectionChanged(SelectionChangedEvent event) {
            for (Object item : getSelectedItems()) {
                for (IFileTypeRegistration reg : fileTypeRegistration) {
                    if (reg.canHandleSubtreeObject(item)) {
                        reg.subTreeElementSelected(item);
                    }
                }
            }
        }
    }

    /**
     * Creates the tree viewer.
     */
    public void createPartControl(Composite parent) {
        viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL
                | SWT.BORDER);
        
        readFiletypeRegistration();
        
        viewer.setContentProvider(new ResourceViewContentProvider(
                fileTypeRegistration, filetypes));
        viewer.setLabelProvider(new ResourceViewLabelProvider(fileTypeRegistration));

        // register viewer at the workbench selection
        // service so that selection events can be
        // received by other parts in the workbench.
        getSite().setSelectionProvider(viewer);

        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot myWorkspaceRoot = workspace.getRoot();
        viewer.setInput(myWorkspaceRoot);
        workspace.addResourceChangeListener(new IResourceChangeListener() {
            public void resourceChanged(IResourceChangeEvent event) {
                if (!viewer.getControl().isDisposed()) {
                    viewer.refresh();
                }
            }
        });

        viewer.addSelectionChangedListener(new MySelectionChangedListener());

        DblClickListener listener = new DblClickListener(viewer);
        viewer.addDoubleClickListener(listener);

        /**
         * Create a menu mananger for popup menus in
         * the tree viewer
         */
        menuManager = new MenuManager("#PopupMenu");
        menuManager.addMenuListener(new ResourceViewPopupMenuListener(viewer, fileTypeRegistration, filetypes));
        menuManager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
        Menu menu = menuManager.createContextMenu(viewer.getControl());

        menuManager.setRemoveAllWhenShown(true);
        viewer.getControl().setMenu(menu);

        // Register the popup menu with the workbench
        // so that other plug-ins can register extensions
        getSite().registerContextMenu(menuManager, viewer);

    }

    /**
     * Read the data from other plug-ins and extract the registered filetype
     * information.
     * 
     */
    public void readFiletypeRegistration() {
        IExtension[] extensions = Platform.getExtensionRegistry()
                .getExtensionPoint(
                        "de.peerthing.workbench.filetypeRegistration")
                .getExtensions();

        INavigationTree navTree = new INavigationTree() {
            public void refresh(Object obj) {
                viewer.refresh(obj);
            }

            public void update(Object obj) {
                viewer.update(obj, null);
            }

            public void collapseToLevel(Object elem, int level) {
                viewer.collapseToLevel(elem, level);
            }

            public void expandToLevel(Object elem, int level) {
                viewer.expandToLevel(elem, level);
            }

            public void select(Object elem) {
                viewer.setSelection(new StructuredSelection(elem));
            }
        };

        // Get declared filetypes from the extension point registry
        // and call the method setNavigationTree on all of them
        // so that they can initialize their popup menus etc.
        for (IExtension extension : extensions) {
            for (IConfigurationElement conf : extension
                    .getConfigurationElements()) {
                try {
                    IFileTypeRegistration reg = (IFileTypeRegistration) conf
                            .createExecutableExtension("class");
                    fileTypeRegistration.add(reg);

                    reg.setNavigationTree(navTree);
                } catch (Exception e) {
                    System.out
                            .println("Cannot instantiate class of a dependent component:");
                    e.printStackTrace();
                }
            }
        }

        // sort the registered components into a hashtable with the
        // filetypes as keys
        filetypes = new Hashtable<String, ArrayList<IFileTypeRegistration>>();
        for (IFileTypeRegistration reg : fileTypeRegistration) {
            for (String filetype : reg.getFileNameEndings()) {
                ArrayList<IFileTypeRegistration> types = filetypes
                        .get(filetype);
                if (types == null) {
                    types = new ArrayList<IFileTypeRegistration>();
                    filetypes.put(filetype, types);
                }
                types.add(reg);
            }
        }
    }

    /**
     * Returns a list of the currently selected items in the tree viewer. This
     * list can be empty, but never null.
     * 
     * @return
     */
    public List getSelectedItems() {
        IStructuredSelection sel = (IStructuredSelection) viewer.getSelection();
        if (sel != null) {
            return sel.toList();
        } else {
            return new ArrayList();
        }
    }
    
    /**
     * Returns the tree viewer used in this view.
     * @return
     */
    public TreeViewer getTreeViewer() {
        return viewer;
    }

    /**
     * Passing the focus request to the viewer's control.
     */
    public void setFocus() {
        viewer.getControl().setFocus();
    }

    /**
     * Sets "Resources" as the part name.
     */
    @Override
    public void init(IViewSite site) throws PartInitException {
        super.init(site);
        setPartName("Resources");
    }
}