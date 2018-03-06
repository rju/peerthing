package de.peerthing.scenarioeditor.interchange;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.scenarioeditor.model.IConnectionCategory;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.IResourceCategory;
import de.peerthing.scenarioeditor.model.IScenario;

class SIScenario implements ISIScenario {
	private IScenario scenario;

	private List<ISIResourceCategory> resources = new ArrayList<ISIResourceCategory>();

	private List<ISIConnectionCategory> connections = new ArrayList<ISIConnectionCategory>();

	private List<ISINodeCategory> nodes = new ArrayList<ISINodeCategory>();

	/**
	 * Constructs a new scenario interchange with the given scenario
	 *
	 * @param scenario
	 */
	public SIScenario(IScenario scenario) {
		this.scenario = scenario;

		for (IResourceCategory cat : scenario.getResourceCategories()) {
			resources.add(new SIResourceCategory(cat, this));
		}
        
        for (IConnectionCategory conn : scenario.getConnectionCategories()) {
            connections.add(new SIConnectionCategory(conn));
        }

		for (INodeCategory cat : scenario.getNodeCategories()) {
			nodes.add(new SINodeCategory(cat, this));
		}


	}

	public List<ISIResourceCategory> getResourceCategories() {
		return resources;
	}

	public List<ISIConnectionCategory> getConnectionCategories() {
		return connections;
	}

	public List<ISINodeCategory> getNodeCategories() {
		return nodes;
	}

	public String getName() {
		return scenario.getName();
	}

}
