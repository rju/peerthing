package de.peerthing.scenarioeditor.interchange;

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
public interface ISICommandContainer {

    /**
     * Returns the commands that belong to this command container.
     * 
     * @return
     */
    public List<ISICommand> getCommands();
}
