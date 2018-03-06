package de.peerthing.scenarioeditor.model.impl;

import de.peerthing.scenarioeditor.ScenarioEditorPlugin;
import de.peerthing.scenarioeditor.model.ICommand;
import de.peerthing.scenarioeditor.model.ICommandContainer;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.workbench.filetyperegistration.IUpDownMovable;

/**
 * Default implementation of ICommand. The descriptions are available in
 * ICommand.
 * 
 * @author Michael Gottschalk, Patrik
 * @reviewer Hendrik Angenendt
 */
public abstract class Command implements ICommand, IUpDownMovable {
    private ICommandContainer commandContainer;

    private IScenario scenario;

    public Command(IScenario scenario) {
        this.scenario = scenario;
    }

    public ICommandContainer getCommandContainer() {
        return commandContainer;
    }

    public void setCommandContainer(ICommandContainer container) {
        this.commandContainer = container;
    }

    public void setScenario(IScenario scenario) {
        this.scenario = scenario;
    }

    public IScenario getScenario() {
        return scenario;
    }

    /**
     * Registers a move-Operation as an undoable operation, notifies the editor
     * of the change and updates the navigation tree.
     * 
     * @param undoOperation
     */
    private void registerMoved(byte undoOperation) {
        ScenarioEditorPlugin.getDefault().getUndoList().add(
                new ScenarioUndo(this, null, undoOperation));
        ScenarioEditorPlugin.getDefault().getFiletypeRegistration().getEditor(
                getScenario()).setDirty();
        ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(
                getCommandContainer());
    }

    /**
     * Moves this command up in the command container that
     * includes this command. Calls registerMoved after that.
     * 
     */
    public void moveUp() {
        int oldIndex = getCommandContainer().getCommands().indexOf(this);
        getCommandContainer().getCommands().remove(oldIndex);
        getCommandContainer().getCommands().add(oldIndex - 1, this);
        registerMoved(UndoOperationValues.wasMovedUp);
    }

    /**
     * Moves this command down in the command container that
     * includes this command. Calls registerMoved after that.
     * 
     */
    public void moveDown() {
        int oldIndex = getCommandContainer().getCommands().indexOf(this);
        getCommandContainer().getCommands().remove(oldIndex);
        getCommandContainer().getCommands().add(oldIndex + 1, this);
        registerMoved(UndoOperationValues.wasMovedDown);
    }

    public boolean canMoveDown() {
        return getCommandContainer().getCommands().indexOf(this) + 1 < getCommandContainer()
                .getCommands().size();

    }

    public boolean canMoveUp() {
        return getCommandContainer().getCommands().indexOf(this) > 0;
    }

}
