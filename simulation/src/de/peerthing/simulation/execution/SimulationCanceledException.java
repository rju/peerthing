package de.peerthing.simulation.execution;

/**
 * This exception is thrown when the user cancels the initialization of a
 * simulation. An exception is needed since clean-up work is needed after such
 * an interruption.
 * 
 * @author Michael Gottschalk
 * 
 */
public class SimulationCanceledException extends RuntimeException {
	private static final long serialVersionUID = 7917604589119004580L;

}
