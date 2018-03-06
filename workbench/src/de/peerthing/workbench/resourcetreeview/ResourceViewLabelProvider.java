package de.peerthing.workbench.resourcetreeview;

import java.util.ArrayList;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import de.peerthing.workbench.filetyperegistration.IFileTypeRegistration;



/**
 * Label provider for the resource tree viewer. The labels are resource names or
 * are provided by plug-ins over the filetype registration interface.
 * 
 * @author Michael Gottschalk
 * 
 */
public class ResourceViewLabelProvider extends LabelProvider {
    private ArrayList<IFileTypeRegistration> fileTypeRegistration;

    /**
     * Creates a new label provider.
     * 
     * @param fileTypeRegistration
     *            The filetype registrations provided by other plug-ins.
     */
    public ResourceViewLabelProvider(
            ArrayList<IFileTypeRegistration> fileTypeRegistration) {
        this.fileTypeRegistration = fileTypeRegistration;

    }

    public String getText(Object obj) {
        if (obj instanceof IProject) {
            return ((IProject) obj).getName();
        } else if (obj instanceof IResource) {
            return ((IResource) obj).getName();
        } else {
            // look if it is a part
            // of a subtree provided by different component
            for (IFileTypeRegistration reg : fileTypeRegistration) {
                if (reg.canHandleSubtreeObject(obj)) {
                    return reg.getSubtreeLabelProvider().getText(obj);
                }
            }
        }

        return obj.toString();
    }

    public Image getImage(Object obj) {
        String imageKey = ISharedImages.IMG_OBJ_FILE;
        Image img = null;

        if (obj instanceof IResource) {
            if (obj instanceof IProject || obj instanceof IFolder) {
                imageKey = ISharedImages.IMG_OBJ_FOLDER;
            } else if (PlatformUI.getWorkbench().getEditorRegistry()
                    .getDefaultEditor(((IResource) obj).getName()) != null) {
                img = PlatformUI.getWorkbench().getEditorRegistry()
                        .getDefaultEditor(((IResource) obj).getName())
                        .getImageDescriptor().createImage();
            }

            if (img == null) {
                img = PlatformUI.getWorkbench().getSharedImages().getImage(
                        imageKey);
            }

            return img;
        } else {
            // Look for a component providing a subtree
            // with this element and take the label provider
            // of this component.
            for (IFileTypeRegistration reg : fileTypeRegistration) {
                if (reg.canHandleSubtreeObject(obj)) {
                    return reg.getSubtreeLabelProvider().getImage(obj);
                }
            }
        }

        return null;

    }
}
