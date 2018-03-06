package de.peerthing.workbench.resourcetreeview;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;

import de.peerthing.workbench.resourcetools.FileTools;




/**
 * This action is executed when the user clicks on new project in the file menu.
 * It displays a dialog for typing in a project name.
 * 
 * @author Michael Gottschalk
 * @review Tjark
 * 
 */
public class NewProjectAction extends Action implements IWorkbenchWindowActionDelegate, ICheatSheetAction {
    private IWorkbenchWindow window;

    public void dispose() {

    }

    public void init(IWorkbenchWindow window) {
        this.window = window;
    }

    /**
     * Creates a new project based on an input dialog for the project name.
     * 
     */
    public void run(IAction action) {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot myWorkspaceRoot = workspace.getRoot();

        InputDialog dialog = new InputDialog(window.getShell(),
                "Create new project", "Enter the name for the new project:",
                null, null);
        int res = dialog.open();
        if (res == Window.CANCEL) {
            return;
        }

        String projectName = dialog.getValue();
        if (projectName == null || projectName.equals("")
                || FileTools.includesOnlyWhitespace(projectName)) {
            MessageDialog.openError(window.getShell(),
                    "Could not create project",
                    "The name of the project must not be empty.");
            return;
        }
        
        if (FileTools.includesInvalidCharacters(projectName)) {
            MessageDialog.openError(window.getShell(), "Could not create project",
            "The name of the project must only include ASCII characters.");
            return;
        }

        try {
            IProject project = myWorkspaceRoot.getProject(projectName);
            if (!project.exists() && !project.isOpen()) {
                project.create(null);
                project.open(null);
            } else {
                MessageDialog.openError(window.getShell(),
                        "Could not create project",
                        "A project with the same name already exists.");
            }
        } catch (Exception e) {
            MessageDialog.openError(window.getShell(),
                    "Could not create project", e.getMessage());
        }

    }

    public void selectionChanged(IAction action, ISelection selection) {
    }
    
    @Override
    public void run() {
    	run(null, null);
    }

	public void run(String[] params, ICheatSheetManager manager) {
		window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		run(null);
	}

}
