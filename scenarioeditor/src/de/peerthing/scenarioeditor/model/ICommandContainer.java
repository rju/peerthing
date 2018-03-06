package de.peerthing.scenarioeditor.model;

import java.util.List;

/**
 * This interface should not be implemented directly. It is intended as a
 * super-interface of Loop and Behaviour since both can include lists of
 * commands.
 * 
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 * 
 */
public interface ICommandContainer extends IScenarioObject {

    /**
     * Returns the commands that belong to this command container.
     * 
     * @return
     */
    public List<ICommand> getCommands();
}
