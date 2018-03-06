package de.peerthing.scenarioeditor.editor.actions;

import org.eclipse.jface.action.Action;

import de.peerthing.scenarioeditor.editor.forms.UserActionForm;

/**
 * 
 * @author lethe
 * 
 */
public class AddParameterAction extends Action {
    UserActionForm actionForm;

    public AddParameterAction(UserActionForm actionForm) {
        this.actionForm = actionForm;
    }

    @Override
    public String getText() {
        return "&add Parameter";
    }

    @Override
    public void run() {
        actionForm.addParameter();
    }
}