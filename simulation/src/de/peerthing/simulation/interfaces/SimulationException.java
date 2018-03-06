/**
 * 
 */
package de.peerthing.simulation.interfaces;

/**
 * @author prefec2
 *
 */
public class SimulationException extends RuntimeException {
	static final long serialVersionUID = 0;
	
	public SimulationException (String message) {
		super(message);
	}
}
