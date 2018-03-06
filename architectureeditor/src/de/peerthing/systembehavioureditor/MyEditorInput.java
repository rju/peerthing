package de.peerthing.systembehavioureditor;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * 
 * See: org.eclipse.ui.IEditorInput
 * IEditorInput is a light weight descriptor of editor input,
 * like a file name but more abstract. It is not a model. It is a
 * description of the model source for an IEditorPart. [..]
 *
*/

public class MyEditorInput implements IEditorInput {

	IFile file;

	public MyEditorInput(IFile file) {
		this.file = file;
	}

	public boolean exists() {
		return file.exists();
	}

	public ImageDescriptor getImageDescriptor() {
		return ImageDescriptor.getMissingImageDescriptor();
	}

	public String getName() {
		return file.getName();
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		return file.getFullPath().toString();
	}

	public Object getAdapter(Class adapter) {
		return file;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof MyEditorInput)) {
			return false;
		}
		return file.equals(((MyEditorInput) obj).file);
	}
}
