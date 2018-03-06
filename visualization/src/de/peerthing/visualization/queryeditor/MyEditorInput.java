package de.peerthing.visualization.queryeditor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import de.peerthing.visualization.querymodel.interfaces.IQueryDataModel;

/**
 * Input for the query editor class. The query editor
 * gets an IQueryDataModel object as input. This object
 * knows the file in which it is saved.
 * 
 * @author Michael Gottschalk
 *
 */
public class MyEditorInput implements IEditorInput {

	private IQueryDataModel dataModel;

	public MyEditorInput(IQueryDataModel dataModel) {
		this.dataModel = dataModel;
	}

	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return ImageDescriptor.getMissingImageDescriptor();
	}

	public String getName() {
		return dataModel.getFile().getName();
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		return dataModel.getFile().getFullPath().toString();
	}

	public Object getAdapter(Class adapter) {
		return dataModel;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof MyEditorInput)) {
			return false;
		}
		return dataModel.getFile().equals(((MyEditorInput) obj).dataModel.getFile());
	}
}
