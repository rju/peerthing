package de.peerthing.scenarioeditor.interchange;

import de.peerthing.scenarioeditor.model.IDelay;

class SIDelay extends SICommand implements ISIDelay {
	private ISIDistribution distr;

	public SIDelay(IDelay delay, ISICommandContainer container) {
		this.distr = new SIDistribution(delay.getDistribution());
		this.container = container;
	}

	public ISIDistribution getDistribution() {
		return distr;
	}

	public String getCommandName() {
		return "Delay";
	}

}
