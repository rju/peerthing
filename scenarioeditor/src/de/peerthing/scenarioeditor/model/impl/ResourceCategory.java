package de.peerthing.scenarioeditor.model.impl;

import de.peerthing.scenarioeditor.model.IDistribution;
import de.peerthing.scenarioeditor.model.IResourceCategory;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.ScenarioFactory;

/**
 * Default implementation of IResourceCategory.
 * 
 * @author Michael Gottschalk, Patrik Schulz
 * @reviewer Hendrik Angenendt
 */
public class ResourceCategory implements IResourceCategory {

	private String name;

	private int popularity;

	private int diversity;

	private IDistribution size;

	private IScenario scenario;

	/**
	 * the standard constructor (which is used when the user add an element of
	 * this kind. The handed scenario is set so the object knows to which
	 * element this object belongs.
	 */
	public ResourceCategory(IScenario scenario) {
		this.scenario = scenario;
		this.size = ScenarioFactory.createDistribution(scenario);
		name = "NewCategory";
	}

	public ResourceCategory(IResourceCategory original, IScenario scenario) {
		this.scenario = scenario;
		this.name = original.getName();
		this.diversity = original.getDiversity();
		this.popularity = original.getPopularity();
		this.size = new Distribution(original.getSize(), scenario);
	}

	/**
	 * creates an copy of the handed original object. the copy object saves the
	 * old values of the original object so the undo operation can work
	 * 
	 * @param original
	 */
	public ResourceCategory(IResourceCategory original) {
		this.scenario = original.getScenario();
		this.popularity = original.getPopularity();
		this.diversity = original.getDiversity();
		this.name = original.getName();
		this.size = new Distribution(original.getSize());
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPopularity(int popularity) {
		this.popularity = popularity;

	}

	public int getPopularity() {
		return popularity;
	}

	public void setDiversity(int diversity) {
		this.diversity = diversity;
	}

	public int getDiversity() {
		return diversity;
	}

	public void setSize(IDistribution size) {
		this.size = size;
	}

	public IDistribution getSize() {
		return size;
	}

	public void setScenario(IScenario scenario) {
		this.scenario = scenario;
	}

	public IScenario getScenario() {
		return scenario;
	}

}
