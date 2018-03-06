package de.peerthing.scenarioeditor.interchange;

/**
 * Represents a case that can be used in a condition in the behaviour of a node
 * category in a scenario. If a case is taken or not depends on the condition
 * and on the probability. Only one of them may be defined, they are mutually
 * exclusive.
 * 
 * 
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface ISICase extends ISICommandContainer {
    /**
     * Returns the condition that defines whether this case is taken or not (an
     * XPath expression).
     * 
     * @return
     */
    public String getCondition();

    /**
     * Returns the probability with which this case is taken. This is a number
     * in the range from 0 to 1.
     * 
     * @return
     */
    public double getProbability();

    /**
     * Returns true if the condition is used and false if the probability is
     * used.
     * 
     * @return
     */
    public boolean isConditionUsed();

}
