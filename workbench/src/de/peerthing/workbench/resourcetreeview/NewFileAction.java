package de.peerthing.workbench.resourcetreeview;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;
import org.eclipse.ui.dialogs.ListDialog;

import de.peerthing.workbench.filetyperegistration.IFileTypeRegistration;
import de.peerthing.workbench.resourcetools.FileTools;



/**
 * An action that creates new files in the given folders. The description and
 * the endings of the files are defined in the given filetype registration. The
 * user is asked for a file name. Then he can choose a template
 * from a list. The available templates are taken from the 
 * extension point templateRegistration.
 * 
 * @author Michael Gottschalk
 * 
 */
public class NewFileAction extends Action implements ICheatSheetAction{
    private IFileTypeRegistration reg;

    private List<IContainer> containers;

    private Shell shell;
    
    private static HashMap<String, List<Template>> templates;

    public NewFileAction(IFileTypeRegistration reg, List<IContainer> containers) {
        this.reg = reg;
        this.containers = containers;
        shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        
        if (templates == null) {
            loadTemplates();
        }
    }
        
    /**
     * Loads the templates from the extension point registry
     */
    private void loadTemplates() {
        templates = new HashMap<String, List<Template>>();
        
        
        IExtension[] extensions = Platform.getExtensionRegistry()
        .getExtensionPoint(
                "de.peerthing.workbench.templateRegistration")
        .getExtensions();
        
        
        for (IExtension extension : extensions) {
            for (IConfigurationElement conf : extension
                    .getConfigurationElements()) {
                try {
                    List<Template> tplList = templates.get(conf.getAttribute("filenameEnding"));
                    if (tplList == null) {
                        tplList = new ArrayList<Template>();
                        templates.put(conf.getAttribute("filenameEnding"), tplList);
                        // Always add an empty template to the list as first element
                        Template empty = new Template();
                        empty.name = "Empty File";
                        tplList.add(empty);
                    }
                    
                    // Create a new Template object
                    Template tpl = new Template();
                    tplList.add(tpl);
                    
                    
                    // Read the file content
                    InputStream stream = Platform.getBundle(conf.getNamespaceIdentifier()).getEntry(conf.getAttribute("file")).openStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            stream));
                    StringBuffer content = new StringBuffer();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        content.append(line + "\n");
                    }
                    reader.close();
                    stream.close();
                    
                    tpl.fileContent = content.toString();
                    tpl.name = conf.getAttribute("name");                    
                } catch (Exception e) {
                    System.out
                            .println("Cannot read templates provided through an extension point:");
                    e.printStackTrace();
                }
            }
        }
        
        // Sort the templates after their name
        for (String key : templates.keySet()) {
            Collections.sort(templates.get(key));
        }
    }

    @Override
    public String getText() {
        return reg.getNewFileDefinition()[0];
    }

    @Override
    public void run() {
        InputDialog dialog = new InputDialog(shell, "Create new " + getText(),
                "Enter the name for the new " + getText() + ":", null, null);
        if (dialog.open() == InputDialog.CANCEL) {
            return;
        }
        String name = dialog.getValue();

        if (name == null || name.equals("")
                || FileTools.includesOnlyWhitespace(name)) {
            MessageDialog.openError(shell, "Could not create file",
                    "The name of the file must not be empty.");
            return;
        }
        if (FileTools.includesInvalidCharacters(name)) {
            MessageDialog.openError(shell, "Could not create file",
            "The name of the file must only include ASCII characters.");
            return;
        }

        if (!name.endsWith("." + reg.getNewFileDefinition()[1])) {
            name = name + "." + reg.getNewFileDefinition()[1];
        }
        
        List<Template> tplList = templates.get(reg.getNewFileDefinition()[1]);
        String templateContent = "";
        if (tplList != null && tplList.size() > 0) {
            ListDialog dlg = new ListDialog(shell);
            dlg.setContentProvider(new ArrayContentProvider());
            dlg.setLabelProvider(new LabelProvider());
            dlg.setInput(tplList);
            dlg.setInitialSelections(new Object[] {tplList.get(0)});
            dlg.setMessage("Select the template that should be used for the new file");
            dlg.setTitle("Template Selection");
            
            
            if (dlg.open() == ListDialog.CANCEL) {
                return;
            }
            
            if (dlg.getResult() != null && dlg.getResult().length > 0) {
                templateContent = ((Template) dlg.getResult()[0]).fileContent;
            }
        }

        for (IContainer container : containers) {
            try {
                IFile file = container.getFile(new Path(name));
                if (!file.exists()) {
                    ByteArrayInputStream stream = new ByteArrayInputStream(
                            templateContent.getBytes());
                    file.create(stream, true, null);
                } else {
                    MessageDialog.openError(shell, "Could not create file",
                            "A file with the same name already exists.");
                }
            } catch (Exception e) {
                MessageDialog.openError(shell, "Could not create file", e
                        .getMessage());
            }
        }
    }

	public void run(String[] params, ICheatSheetManager manager) {
	}

}


class Template implements Comparable<Template> {
    public String name = "";
    public String fileContent = "";
    
    public int compareTo(Template tpl) {
        // empty template should be at the top
        if (fileContent.equals("") && !tpl.fileContent.equals("")) {
            return -1;
        }
        if (!fileContent.equals("") && tpl.fileContent.equals("")) {
            return 1;
        }
        
        return name.compareTo(tpl.name);
    }
    
    public String toString() {
        return name;
    }
}
