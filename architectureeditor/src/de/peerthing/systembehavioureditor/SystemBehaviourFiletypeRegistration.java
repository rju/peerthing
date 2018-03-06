package de.peerthing.systembehavioureditor;

import java.util.Hashtable;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;


import de.peerthing.systembehavioureditor.gefeditor.SysGraphicalEditor;
import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;
import de.peerthing.systembehavioureditor.model.ISystemBehaviourObject;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.editor.State;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;
import de.peerthing.workbench.filetyperegistration.IFileTypeRegistration;
import de.peerthing.workbench.filetyperegistration.INavigationTree;

/**
 * Class that registers the filetype for the architecture editor at the
 * PeerThing Workbench.
 *
 * @author Michael Gottschalk
 *
 */
public class SystemBehaviourFiletypeRegistration implements
        IFileTypeRegistration {

    private Hashtable<IFile, SysGraphicalEditor> editors = new Hashtable<IFile, SysGraphicalEditor>();

    private static INavigationTree tree;

    public SystemBehaviourFiletypeRegistration() {
        PeerThingSystemBehaviourEditorPlugin.getDefault()
                .setFiletypeRegistration(this);
    }

    public void editorDisposed(SysGraphicalEditor editor) {
        editors.remove(editor.getSystemBehaviour().getFile());
        tree.collapseToLevel(editor.getSystemBehaviour().getFile(), 1);
        tree.refresh(editor.getSystemBehaviour().getFile());
    }

    public String[] getFileNameEndings() {
        return new String[] { "arch" };
    }

    public boolean wantsToBeDefaultEditor() {
        return true;
    }

    public String getComponentName() {
        return "System Behaviour Editor";
    }

    public void showComponent(IFile[] inputFiles) {
        try {
            PlatformUI
                    .getWorkbench()
                    .showPerspective(
                            "de.peerthing.systembehavioureditor.SystemBehaviourPerspective",
                            PlatformUI.getWorkbench()
                                    .getActiveWorkbenchWindow());
        } catch (WorkbenchException e) {
            System.out.println("Could not show architecture perspective: ");
            e.printStackTrace();
        }

        for (IFile file : inputFiles) {
            showEditorForFile(file, true);
            tree.expandToLevel(file, 1);
        }

    }

    public Object[] getTreeElements(IFile file) {
        SysGraphicalEditor editor = showEditorForFile(file, false);

        SystemBehaviour architecture = editor.getSystemBehaviour();

        Object[] ret = new Object[architecture.getNodes().size() + 1];
        int i = 0;
        for (INodeType node : architecture.getNodes()) {
            ret[i++] = node;
        }
        ret[i] = new XMLNode(architecture);

        return ret;
    }

    class XMLNode implements ISystemBehaviourObject {
        private ISystemBehaviour sys;

        public XMLNode(ISystemBehaviour behaviour) {
            sys = behaviour;
        }

        public ISystemBehaviour getSystemBehaviour() {
            return sys;
        }

        public String toString() {
            return "View in XML-Editor";
        }
    }

    public ITreeContentProvider getSubtreeContentProvider() {

        return new ITreeContentProvider() {

            public Object[] getChildren(Object parentElement) {
                if (parentElement instanceof INodeType)
                    return ((INodeType) parentElement).getTasks().toArray();

                else if (parentElement instanceof ITask) {

                    /* exclude implicid endState */
                    Vector<IState> retArray = new Vector<IState>();
                    retArray.addAll(((ITask) parentElement).getStates());
                    for (IState s : ((ITask) parentElement).getStates()) {
                        if (((State) s).isEndState()) {
                            // exclude from retArray
                            retArray.remove(s);
                        }
                    }
                    return retArray.toArray();
                }

                else if (parentElement instanceof IState)
                    return ((IState) parentElement).getTransitions().toArray();

                else
                    return null;
            }

            public Object getParent(Object element) {
                return null;
            }

            public boolean hasChildren(Object element) {

                if (element instanceof INodeType)
                    return (((INodeType) element).getTasks().size() > 0);
                else if (element instanceof ITask)
                    return (((ITask) element).getStates().size() > 0);
                else if (element instanceof IState)
                    return (((IState) element).getTransitions().size() > 0);

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

            @Override
            public Image getImage(Object element) {
                Image img = PeerThingSystemBehaviourEditorPlugin.getDefault()
                        .getIconImage(
                                element.getClass().getSimpleName()
                                        .toLowerCase()
                                        + ".png");
                return img;
            }

            @Override
            public String getText(Object element) {
                return element.toString();
            }
        };

    }

    public boolean canHandleSubtreeObject(Object obj) {

        return (obj instanceof ISystemBehaviourObject);
    }

    public boolean hasSubTree(IFile file) {
        return true;
    }

    public void setNavigationTree(INavigationTree navigationTree) {
        tree = navigationTree;
        PeerThingSystemBehaviourEditorPlugin.getDefault().setNavigationTree(
                navigationTree);
    }

    public static INavigationTree getNavigationTree() {
        return tree;
    }

    public void subTreeElementSelected(Object subTreeElement) {
        if (subTreeElement instanceof ISystemBehaviourObject) {
            ISystemBehaviourObject sysObj = (ISystemBehaviourObject) subTreeElement;
            SysGraphicalEditor editor = showEditorForFile(sysObj
                    .getSystemBehaviour().getFile(), true);

            if (sysObj instanceof XMLNode) {
                editor.xmlEditForm.update(sysObj.getSystemBehaviour());
                editor.changeTopControlXML();
            } else {
                editor.changeTopControlGEF();
            }

            // Passing the selection to the gef editor does not work for now.
            if (subTreeElement instanceof IState) {
                State s = (State) subTreeElement;
                s.getEditPart().setSelected(1);
                System.out
                        .println("Passing selection to gef not implemented yet. "
                                + s);
            }
        }
    }

    public void subTreeElementDoubleClicked(Object subTreeElement) {
    }

    public String[] getNewFileDefinition() {
        return new String[] { "System behaviour", "arch" };
    }

    /**
     * Shows the editor for the specified file. If no editor is open for the
     * file, then open a new one.
     *
     * @param file
     *            The file to open
     * @param bringToTop
     *            If true, then the editor will be shown to the user.
     * @return
     */
    private SysGraphicalEditor showEditorForFile(IFile file, boolean bringToTop) {
        SysGraphicalEditor editor = null;

        if (file == null) {
            return null;
        }

        if (bringToTop) {
            try {
                PlatformUI
                        .getWorkbench()
                        .showPerspective(
                                "de.peerthing.systembehavioureditor.SystemBehaviourPerspective",
                                PlatformUI.getWorkbench()
                                        .getActiveWorkbenchWindow());
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }

        editor = editors.get(file);

        if (editor != null) {
            if (bringToTop) {
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                    .getActivePage().bringToTop(editor);
            }
        } else {

            try {
                editor = (SysGraphicalEditor) PlatformUI
                        .getWorkbench()
                        .getActiveWorkbenchWindow()
                        .getActivePage()
                        .openEditor(new MyEditorInput(file),
                                "de.peerthing.systembehavioureditor.GraphicalEditor");
                editor.setFiletypeRegistration(this);
                editor.getSystemBehaviour().setFile(file);
                editors.put(file, editor);

            } catch (PartInitException e) {
                System.out
                        .println("Could not initialize systembehaviour editor: ");
                e.printStackTrace();
            }
        }

        return editor;

    }

    public SysGraphicalEditor getEditor(ISystemBehaviour sysbehaviour) {
        return editors.get(sysbehaviour.getFile());

    }

    public IFile getFile(SysGraphicalEditor editor) {
        return (IFile) editors.get(editor);
    }

    public SystemBehaviour getSystemBehaviour(IFile file) {
        return editors.get(file).getSystemBehaviour();
    }

}
