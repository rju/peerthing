package de.peerthing.systembehavioureditor.interchange;

/**
 * Represents a variable that can be used in the declaration of nodes or tasks
 * in the architectural model.
 *
 *
 * @author Michael Gottschalk
 */
public interface IAIVariable {
    /**
     * Returns the name of the variable.
     *
     * @return
     */
    public String getName();

    /**
     * Returns the initial value of the variable.
     *
     * @return
     */
    public String getInitialValue();
    
}
