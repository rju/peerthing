package de.peerthing.scenarioeditor.model;


/**
 * Represents a loop that can be used as a command
 * in the behaviour of a node category in a scenario.
 * A loop can contain a list of commands itself. A loop is
 * executed until the maximum number of iterations given
 * in the distribution is reached or until the until condition
 * evaluates to true (both are break conditions).
 * 
 *
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface ILoop extends ICommand, ICommandContainer, IScenarioObject {
    /**
     * Sets the until condition of the loop (XPath expression). The loop
     * is executed until this expression evaluates to true.
     * 
     * @param expression The XPath expression
     */
	public void setUntilCondition(String expression);
    
    /**
     * Returns the until condition of the loop (XPath expression). The loop
     * is executed until this expression evaluates to true.
     * 
     * @return the XPath expression
     */
	public String getUntilCondition();
	
    /**
     * Sets the number of iterations that a loop is executed. Since this can be
     * a stochastic value, it must be given as a distribution.
     * 
     * @param distribution
     */
	public void setDistribution(IDistribution distribution);
    
    /**
     * Returns the number of iterations that a loop is executed. Since this can be
     * a stochastic value, it is given as a distribution.
     * 
     * @return
     */
	public IDistribution getDistribution();
}
