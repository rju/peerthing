package de.peerthing.systembehavioureditor.propertyeditor.popupactions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;

import de.peerthing.systembehavioureditor.model.editor.Action;
import de.peerthing.systembehavioureditor.model.editor.Parameter;

/**
 * This Class will generate and add a new Parameter with a hash ID
 *         to a chosen Action.
 * @author jojo 
 */
public class AddParameter extends AbstractTreeAction {
	int paracount = hashCode();

	public void run(IAction action) {  
        chosen = getCurrentSelection();
        
		paracount++;
		Parameter para = new Parameter(""+ paracount,((Action) chosen));
		para.setAction(((Action) chosen));
		((Action) chosen).addParameter(para);
		treeviewer.refresh();
		graphed.setDirty();
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
