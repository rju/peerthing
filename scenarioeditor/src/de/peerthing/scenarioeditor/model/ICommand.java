package de.peerthing.scenarioeditor.model;

import de.peerthing.workbench.filetyperegistration.IUpDownMovable;

/**
 * This is the super-interface for commands allowed in a behaviour. It should
 * not be implemented directly!
 * 
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface ICommand extends IScenarioObject, IUpDownMovable {
    /**
     * Gets the command-container to which this command belongs
     * 
     * @return
     */
    public ICommandContainer getCommandContainer();

    /**
     * Sets the command container to which this command belongs
     * @param container
     */
    public void setCommandContainer(ICommandContainer container);
    
    /**
     * Sets the scenario to which this command belongs.
     * 
     * @param scenario
     */
    public void setScenario(IScenario scenario);
}
