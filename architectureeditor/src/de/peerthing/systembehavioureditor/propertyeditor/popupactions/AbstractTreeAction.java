/**
 * 
 */
package de.peerthing.systembehavioureditor.propertyeditor.popupactions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.ObjectPluginAction;


import de.peerthing.systembehavioureditor.PeerThingSystemBehaviourEditorPlugin;
import de.peerthing.systembehavioureditor.SystemBehaviourFiletypeRegistration;
import de.peerthing.systembehavioureditor.gefeditor.SysGraphicalEditor;
import de.peerthing.systembehavioureditor.model.ICaseArchitecture;
import de.peerthing.systembehavioureditor.model.editor.Action;
import de.peerthing.systembehavioureditor.model.editor.Parameter;
import de.peerthing.systembehavioureditor.propertyeditor.PropertyEditor;
import de.peerthing.systembehavioureditor.propertyeditor.plugindatahandler.PlugInAction;
import de.peerthing.systembehavioureditor.propertyeditor.plugindatahandler.PlugInParameter;
import de.peerthing.workbench.filetyperegistration.INavigationTree;

/**
 * @author jojo This Class is for every popup-menu the property-editor uses.
 *         Here, generall Variables will be initiatet. Every
 *         Property-Editor-Popup-Class should extend this Class.
 * 
 * 
 */

public abstract class AbstractTreeAction implements IObjectActionDelegate {

    protected Object chosen = null;

    protected TreeViewer treeviewer = null;

    protected SysGraphicalEditor graphed = null;

	PropertyEditor prop;
    protected INavigationTree getTree() {
        return SystemBehaviourFiletypeRegistration.getNavigationTree();
    }

    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        if (!(targetPart instanceof PropertyEditor)) {
            action.setEnabled(false);
            return;
        }
        prop = (PropertyEditor) targetPart;
        action.setEnabled(true);
        setTreeViewer(((PropertyEditor) targetPart).getTreeViewer());
        setCHOSEN(((StructuredSelection) ((ObjectPluginAction) action)
                .getSelection()).getFirstElement());
        setGraphed(((PropertyEditor) targetPart).getGraphed());
        try {
            if (targetPart instanceof PropertyEditor) {
                if (((PropertyEditor) targetPart).getChosen() instanceof Action) {
                    Action tmp = (Action) ((PropertyEditor) targetPart)
                            .getChosen();
                    if (action.getId().equals("AddParameter")) {
                        PlugInAction plugtmp = PeerThingSystemBehaviourEditorPlugin
                                .getDefault().getPlugInDataHandler()
                                .getPluginAction(tmp.getName());
                        if (plugtmp != null
                                && !plugtmp.isCustomParametersAllowed()) {
                            action.setEnabled(false);
                        }
                    }
                } else if (((PropertyEditor) targetPart).getChosen() instanceof Parameter) {
                    Parameter tmp = (Parameter) ((PropertyEditor) targetPart)
                            .getChosen();
                    try {
                        PlugInParameter pptmp = PeerThingSystemBehaviourEditorPlugin
                                .getDefault().getPlugInDataHandler()
                                .getPluginAction(tmp.getAction().getName())
                                .getPluginparameter(tmp.getName());
                        if (pptmp != null && pptmp.isRequired()
                                && action.getId().equals("Delete")) {
                            action.setEnabled(false);
                        } else {
                            System.out.println(action.getId());
                        }
                    } catch (Exception e) {
                        if (e.toString().equals(
                                "java.lang.NullPointerException"))
                            System.out.print("unwichtig: ");
                        System.out.println(e.toString());
                    }

                } else if (((PropertyEditor) targetPart).getChosen() instanceof ICaseArchitecture) {
                    if (((ICaseArchitecture) ((PropertyEditor) targetPart)
                            .getChosen()) == (((ICaseArchitecture) ((PropertyEditor) targetPart)
                            .getChosen()).getCondition().getDefaultCase())
                            && action.getId().equals("Delete")) {
                        action.setEnabled(false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This Object is needed to set the graph dirty, so that the
     * autosafefunction works
     * 
     * @param sge
     */
    public void setGraphed(SysGraphicalEditor sge) {
        this.graphed = sge;
    }

    /**
     * chosen is the current selected Object
     * 
     * @param obj
     */
    public void setCHOSEN(Object obj) {
        this.chosen = obj;
    }

    /**
     * The treeviewer-object is needed to update the left view of the
     * propertyeditor
     * 
     * @param tv
     */
    public void setTreeViewer(TreeViewer tv) {
        this.treeviewer = tv;
    }

    public Object getCurrentSelection() {
        if (treeviewer.getSelection() == null) {
            return null;
        }
        return ((IStructuredSelection) treeviewer.getSelection())
                .getFirstElement();
    }

    public void selectionChanged(IAction action, ISelection selection) {
    }
}
