package de.peerthing.scenarioeditor.model;

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
public interface ICase extends ICommandContainer, IScenarioObject {
    /**
     * Sets the condition that defines whether this case is taken or not. This
     * is an XPath expression.
     * 
     * @param expression
     *            the XPath expression.
     */
    public void setCondition(String expression);

    /**
     * Returns the condition that defines whether this case is taken or not (an
     * XPath expression).
     * 
     * @return
     */
    public String getCondition();

    /**
     * Sets the probability with which this case is taken.
     * 
     * @param probability
     *            A number in the range from 0 to 1.
     */
    public void setProbability(double probability);

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

    /**
     * Sets whether the condition is used or the probability is used in this
     * case.
     * 
     * @param value
     *            if true, then the condition is used, else the probability is
     *            used.
     */
    public void setConditionUsed(boolean value);
    
    /**
     * Sets the scenario to which this case belongs.
     * @param scenario
     */
    public void setScenario(IScenario scenario);
 
    /**
     * Returns the scenario to which this node category belongs (the
     * parent object).
     * 
     * @return
     */
    public IScenario getScenario();
    
    public IScenarioCondition getScenarioCondition();
}
