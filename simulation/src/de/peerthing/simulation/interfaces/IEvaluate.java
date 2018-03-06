/**
 * 
 */
package de.peerthing.simulation.interfaces;

/**
 * Interface for all classes which need XPath-evaluation support.
 * 
 * @author prefec2
 * 
 */
public interface IEvaluate {
	/**
	 * Evaluate an XPath expression against the XPathObject context. If the
	 * expression evaluates to an XPath string, number, or boolean type, then
	 * the equivalent Java object type is returned. Otherwise, if the result is
	 * a node-set, then the returned value is a ArrayList of ??.
	 * 
	 * @param expression
	 * @return Boolean, Double, String, ArrayList, null
	 */
	public Object evaluate(String expression);

	/**
	 * Evaluate an XPath expression against the XPathObject context. Unlike
	 * "evaluate" "evaulteCondition" returns allways a boolean value. The method
	 * maps non boolean results to true and false according to the following
	 * rules.
	 * 
	 * Boolean(true,false) => true,false Double(positive number, 0, negative
	 * number) => true, false, false String(non empty, empty) => true, false
	 * ArrayList(non empty, empty) => true, false null => false
	 * 
	 * @param expression
	 * @return boolean
	 */
	public boolean evaluateCondition(String expression);
}
