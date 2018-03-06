package de.peerthing.scenarioeditor.interchange;

import de.peerthing.scenarioeditor.model.ILoop;

class SILoop extends SICommandContainer implements ISILoop {
	private ISICommandContainer container;

	private ILoop loop;

	private ISIDistribution distr;

	public SILoop(ILoop loop, ISICommandContainer container) {
        super(loop);
		this.container = container;
		this.loop = loop;
		if (loop.getDistribution()!=null)
			this.distr = new SIDistribution(loop.getDistribution());
		else
		   this.distr = null;
	}

	public String getUntilCondition() {
		return loop.getUntilCondition();
	}

	public ISIDistribution getDistribution() {
		return distr;
	}

	public ISICommandContainer getCommandContainer() {
		return container;
	}

	public String getCommandName() {
		String ret = "Loop";
		if (getUntilCondition() != null && !getUntilCondition().equals("")) {
			ret += "(until: "+ getUntilCondition() + ")";
		}
		return ret;
	}
}
