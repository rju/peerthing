package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.Action;

import de.peerthing.scenarioeditor.editor.forms.UserActionForm;
/**
 * 
 * @author lethe
 *
 */
public class DeleteParameterAction extends Action {
    /**
     * A action form
     */
    UserActionForm actionForm;
    
    public DeleteParameterAction(UserActionForm actionForm) {
        this.actionForm = actionForm;
    }
    
    
    @Override
    public String getText() {
        return "&Delete";
    }

    @Override
    public void run() {        
        actionForm.deleteSelectedParameter();
    }

    
}
