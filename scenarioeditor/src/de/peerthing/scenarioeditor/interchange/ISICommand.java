package de.peerthing.scenarioeditor.interchange;

/**
 * This is the super-interface for commands allowed in a behaviour. It should
 * not be implemented directly!
 *
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface ISICommand {
    /**
     * Sets the command-container to which this command belongs
     *
     * @return
     */
    public ISICommandContainer getCommandContainer();

    /**
     * Returns the name of this command.
     * @return
     */
    public String getCommandName();

}
