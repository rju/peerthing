/**
 * 
 */
package de.peerthing.simulation.execution;

/**
 * @author prefec2
 * 
 */
public class SimProfile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Simulator sim = new Simulator();

		sim.initialize("examples/simple.arch", "examples/simple-1.scen", null);
		while ((sim.getSimulationTime() < 1000 * 1000) && sim.step())
			;
		sim.end();
	}

}
