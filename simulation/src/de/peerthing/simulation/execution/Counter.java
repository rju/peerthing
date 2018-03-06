/**
 * 
 */
package de.peerthing.simulation.execution;

/**
 * @author prefec2
 * 
 */
public class Counter {

	private int value;

	/**
	 * 
	 */
	public Counter() {
		value = 0;
	}

	/**
	 * return next value
	 * 
	 * @return
	 */
	public int next() {
		value++;
		return value;
	}

}
