package de.peerthing.scenarioeditor.interchange;

import de.peerthing.scenarioeditor.model.INodeResource;

class SINodeResource implements ISINodeResource {
	private ISIResourceCategory category;
	private ISIDistribution distr;
	private ISINodeCategory node;

	public SINodeResource(INodeResource res, ISINodeCategory node) {
		this.node = node;
		distr = new SIDistribution(res.getNumberDistribution());
		for (ISIResourceCategory cat : node.getScenario().getResourceCategories()) {
			if (cat.getName().equals(res.getCategory().getName())) {
				category = cat;
			}
		}
	}


	public ISIResourceCategory getCategory() {
		return category;
	}

	public ISIDistribution getNumberDistribution() {
		return distr;
	}

	public ISINodeCategory getNode() {
		return node;
	}

}
