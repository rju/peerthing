package de.peerthing.scenarioeditor.editor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class MyEditorInput implements IEditorInput {

	IFile file;
	String editor;

	public MyEditorInput(IFile file, String editor) {
		this.file = file;
		this.editor = editor;
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
		if (adapter == IResource.class || adapter == IFile.class) {
			return file;
		}

		return null;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof MyEditorInput)) {
			return false;
		}
		MyEditorInput other = (MyEditorInput) obj;

		return file.equals(other.file) && editor.equals(other.editor);
	}
}
