package de.peerthing.scenarioeditor.interchange;

import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.persistence.ScenarioXMLAdapter;

/**
 * With this class, a scenario can be loaded and returned in the scenario
 * interchange format, which is intended for the simulation.
 *
 * @author Michael Gottschalk
 *
 */
public class ScenarioInterchange {
	public static ISIScenario loadScenario(String filename) {
		IScenario scenario = ScenarioXMLAdapter.loadScenario(filename);
		return new SIScenario(scenario);
	}
}
