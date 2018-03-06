package de.peerthing.scenarioeditor.model.impl;

import org.eclipse.core.resources.IFile;

import de.peerthing.scenarioeditor.model.IConnectionCategory;
import de.peerthing.scenarioeditor.model.IListWithParent;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.IResourceCategory;
import de.peerthing.scenarioeditor.model.IScenario;

/**
 * Default implementation of IScenario
 * 
 * @author Michael Gottschalk
 * @reviewer Hendrik Angenendt
 */
public class Scenario implements IScenario {
	private IListWithParent<IResourceCategory> resourceCategories;

	private IListWithParent<IConnectionCategory> connectionCategories;

	private IListWithParent<INodeCategory> nodeCategories;

	private String name;

	private IFile file;

	/**
	 * the standard constructor creates a scenario including 3 lists which shell
	 * contain the node categories, resource categories and connection
	 * categories
	 */
	public Scenario() {
		this.name = "unknown";
		nodeCategories = new ListWithParent<INodeCategory>(this,
				"Node Categories", this);
		resourceCategories = new ListWithParent<IResourceCategory>(this,
				"Resources", this);
		connectionCategories = new ListWithParent<IConnectionCategory>(this,
				"Connections", this);
	}

	public IListWithParent<IResourceCategory> getResourceCategories() {
		return resourceCategories;
	}

	public IListWithParent<IConnectionCategory> getConnectionCategories() {
		return connectionCategories;
	}

	public IListWithParent<INodeCategory> getNodeCategories() {
		return nodeCategories;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public IScenario getScenario() {
		return this;
	}

	public void setFile(IFile file) {
		this.file = file;
	}

	public IFile getFile() {
		return file;
	}

}
