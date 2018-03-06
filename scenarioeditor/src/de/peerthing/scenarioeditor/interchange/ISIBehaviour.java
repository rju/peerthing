package de.peerthing.scenarioeditor.interchange;


/**
 * Represents a behaviour of a node category in
 * a scenario.
 *
 *
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface ISIBehaviour extends ISICommandContainer {
	public String getName();

    /**
     * Returns the node category to which this behaviour belongs
     * (the parent).
     *
     * @return
     */
    public ISINodeCategory getNodeCategory();
}
