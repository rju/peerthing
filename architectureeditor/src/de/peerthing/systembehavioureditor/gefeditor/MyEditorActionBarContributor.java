package de.peerthing.systembehavioureditor.gefeditor;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.EditorActionBarContributor;

/**
 * This class manages the integration with the Eclipse RCP. Additional tool bars like ones
 * for zooming support may be added here.
 *  
 * @author Peter Schwenkenberg
 * @review Johannes Fischer
 *
 */
public class MyEditorActionBarContributor extends EditorActionBarContributor {

    public void setActiveEditor(IEditorPart targetEditor) {
        if (targetEditor instanceof SysGraphicalEditor) {
            SysGraphicalEditor editor = (SysGraphicalEditor) targetEditor;
            setActionHandler(editor, ActionFactory.UNDO.getId());
            setActionHandler(editor, ActionFactory.REDO.getId());
            setActionHandler(editor, ActionFactory.DELETE.getId());
            setActionHandler(editor, ActionFactory.SELECT_ALL.getId());
        }
    }

    private void setActionHandler(SysGraphicalEditor editor, String id) {
        getActionBars().setGlobalActionHandler(id, editor.getMyActionRegistry().getAction(id));
    }
}