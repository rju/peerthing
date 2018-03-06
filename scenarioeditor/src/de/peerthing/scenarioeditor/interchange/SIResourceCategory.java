package de.peerthing.scenarioeditor.interchange;

import de.peerthing.scenarioeditor.model.IResourceCategory;

class SIResourceCategory implements ISIResourceCategory {
	private IResourceCategory resCategory;
	private ISIScenario scenario;
	private ISIDistribution size;

	/**
	 * Constructs a new SIResourceCategory from an IResourceCategory.
	 * A Scenario must be given as a parent.
	 *
	 * @param resCategory
	 * @param scenario
	 */
	public SIResourceCategory(IResourceCategory resCategory, ISIScenario scenario) {
		this.resCategory = resCategory;
		this.scenario = scenario;

		size = new SIDistribution(resCategory.getSize());
	}

	public String getName() {
		return resCategory.getName();
	}

	public int getPopularity() {
		return resCategory.getPopularity();
	}

	public int getDiversity() {
		return resCategory.getDiversity();
	}

	public ISIDistribution getSize() {
		return size;
	}

	public ISIScenario getScenario() {
		return scenario;
	}

}
