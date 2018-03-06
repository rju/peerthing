package de.peerthing.scenarioeditor.editor;

import java.util.Hashtable;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;


import de.peerthing.scenarioeditor.ScenarioEditorPlugin;
import de.peerthing.scenarioeditor.model.IListWithParent;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.impl.ListWithParent;
import de.peerthing.workbench.filetyperegistration.IFileTypeRegistration;
import de.peerthing.workbench.filetyperegistration.INavigationTree;

/**
 * Class that registers the filetype for the scenario editor at the PeerThing
 * Workbench.
 * 
 * @author Michael Gottschalk
 * 
 */
public class ScenarioFiletypeRegistration implements IFileTypeRegistration {
    ILabelProvider labelProvider;

    private ViewContentProvider contentProvider;

    private INavigationTree tree;

    private Hashtable<IFile, ScenarioEditor> editors = new Hashtable<IFile, ScenarioEditor>();

    private Hashtable<IScenario, ScenarioEditor> scenarios = new Hashtable<IScenario, ScenarioEditor>();

    public ScenarioFiletypeRegistration() {
        labelProvider = new ViewLabelProvider();
        contentProvider = new ViewContentProvider();

        ScenarioEditorPlugin.getDefault().setFiletypeRegistration(this);
    }

    public void editorDisposed(ScenarioEditor editor) {
        editors.remove(editor.getScenarioFile());
        scenarios.remove(editor.getScenario());
        tree.collapseToLevel(editor.getScenarioFile(), 1);
        tree.refresh(editor.getScenarioFile());
    }

    public void scenarioChanged(ScenarioEditor editor, IScenario oldScenario,
            IScenario newScenario) {
        newScenario.setFile(editor.getScenarioFile());
        scenarios.remove(oldScenario);
        scenarios.put(newScenario, editor);
        tree.refresh(editor.getScenarioFile());
    }

    public String[] getFileNameEndings() {
        return new String[] { "scen" };
    }

    public boolean wantsToBeDefaultEditor() {
        return true;
    }

    public String getComponentName() {
        return "Scenario Editor";
    }

    public void showComponent(IFile[] inputFiles) {
        showEditorForFile(inputFiles[0], true);
        tree.expandToLevel(inputFiles[0], 2);
    }

    private ScenarioEditor showEditorForFile(IFile file, boolean bringToTop) {
        ScenarioEditor editor = editors.get(file);

        if (bringToTop) {
            try {
                PlatformUI.getWorkbench().showPerspective(
                        "de.peerthing.MainPerspective",
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow());
            } catch (WorkbenchException e) {
                System.out.println("Could not show scenario perspective: ");
                e.printStackTrace();
            }
        }

        if (editor != null) {
            if (bringToTop) {
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().bringToTop(editor);
            }
        } else {

            try {
                editor = (ScenarioEditor) PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getActivePage().openEditor(
                                new MyEditorInput(file, "tree"),
                                "de.peerthing.scenarioeditor");
                editor.setFiletypeRegistration(this);
                editor.getScenario().setFile(file);
                editors.put(file, editor);
                scenarios.put(editor.getScenario(), editor);
                ScenarioEditorPlugin.getDefault().setEditor(editor);

            } catch (PartInitException e) {
                System.out.println("Could not initialize scenario editor: ");
                e.printStackTrace();
            }
        }

        return editor;

    }

    public ScenarioEditor getEditor(IScenario scenario) {
        return editors.get(scenario.getFile());
    }

    public Object[] getTreeElements(IFile file) {
        IScenario scenario = showEditorForFile(file, false).getScenario();

        ListWithParent xml = new ListWithParent(scenario, "XML");
        xml.setScenario(scenario);
        scenario.setName(file.getName());
        return new IListWithParent[] { scenario.getNodeCategories(),
                scenario.getResourceCategories(),
                scenario.getConnectionCategories(), xml };

    }

    public ITreeContentProvider getSubtreeContentProvider() {
        return contentProvider;
    }

    public ILabelProvider getSubtreeLabelProvider() {
        return new LabelProvider() {

            @Override
            public Image getImage(Object element) {

                if (element instanceof IListWithParent) {
                    IListWithParent<?> label = (IListWithParent) element;
                    String tempString = label.getName().replaceAll(" +", "");
                    Image img = ScenarioEditorPlugin.getDefault().getIcon(
                            tempString.toLowerCase() + ".png");

                    return img;
                } else {
                    Image img = ScenarioEditorPlugin.getDefault().getIcon(
                            element.getClass().getSimpleName().toLowerCase()
                                    + ".png");
                    return img;
                }

            }

            @Override
            public String getText(Object element) {
                return labelProvider.getText(element);
            }
        };

    }

    public boolean canHandleSubtreeObject(Object obj) {
        return obj instanceof IScenarioObject;
    }

    public boolean hasSubTree(IFile file) {
        return true;
    }

    public void setNavigationTree(INavigationTree navigationTree) {
        this.tree = navigationTree;
        ScenarioEditorPlugin.getDefault().setNavigationTree(navigationTree);
    }

    public void subTreeElementSelected(Object subTreeElement) {
        if (subTreeElement instanceof IScenarioObject) {
            IScenario scen = ((IScenarioObject) subTreeElement).getScenario();
            ScenarioEditor editor = showEditorForFile(scen.getFile(), true);

            // look if the scenario for the editor changed...
            if (editor.getScenario() != ((IScenarioObject) subTreeElement)
                    .getScenario()) {
                tree.refresh(scen.getFile());
                return;
            }

            editor.showFormFor((IScenarioObject) subTreeElement);
        }
    }

    public void subTreeElementDoubleClicked(Object subTreeElement) {
    }

    public String[] getNewFileDefinition() {
        return new String[] { "Scenario", "scen" };
    }

}