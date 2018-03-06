/*
 * Created on 09.07.2006
 *
 */
package de.peerthing.workbench.resourcetools;

import java.util.Vector;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * This class can be used for a resource selection dialog as the content
 * provider.
 * 
 * @author Michael Gottschalk
 * 
 */
public class ResourceContentProvider implements ITreeContentProvider {

    public void inputChanged(Viewer v, Object oldInput, Object newInput) {
    }

    public void dispose() {
    }

    public Object[] getElements(Object parent) {
        IProject[] projects = ((IWorkspaceRoot) parent).getProjects();
        return projects;
    }

    public Object getParent(Object child) {
        return ((IResource) child).getParent();
    }

    public Object[] getChildren(Object parent) {
        if (parent instanceof IProject) {
            try {
                IResource[] resources = ((IProject) parent).members();
                Vector<IResource> visibleRes = new Vector<IResource>();
                for (int i = 0; i < resources.length; i++) {
                    if (!resources[i].getName().equals(".project")) {
                        visibleRes.add(resources[i]);
                    }
                }

                return visibleRes.toArray();
            } catch (CoreException e) {
                // ok...
            }
        }

        return null;
    }

    public boolean hasChildren(Object parent) {
        if (parent instanceof IProject || parent instanceof IFolder) {
            return true;
        } else {
            return false;
        }
    }

}
