/*
 * Created on 09.07.2006
 *
 */
package de.peerthing.workbench.resourcetools;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * This class can be used for a resource selection dialog as
 * the label provider.
 * 
 * @author Michael Gottschalk
 */
public class ResourceLabelProvider extends LabelProvider {
    
    public String getText(Object obj) {
        if (obj instanceof IProject) {
            return ((IProject) obj).getName();
        } else if (obj instanceof IResource) {
            return ((IResource) obj).getName();
        }
        return obj.toString();
    }

    public Image getImage(Object obj) {
        String imageKey = ISharedImages.IMG_OBJ_FILE;
        Image img = null;

        if (obj instanceof IProject || obj instanceof IFolder) {
            imageKey = ISharedImages.IMG_OBJ_FOLDER;
        } else if (PlatformUI.getWorkbench().getEditorRegistry()
                .getDefaultEditor(((IResource) obj).getName()) != null) {
            img = PlatformUI.getWorkbench().getEditorRegistry()
                    .getDefaultEditor(((IResource) obj).getName())
                    .getImageDescriptor().createImage();
        }

        if (img == null) {
            img = PlatformUI.getWorkbench().getSharedImages()
                    .getImage(imageKey);
        }

        return img;
    }
}
