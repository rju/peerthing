package de.peerthing.scenarioeditor.interchange;

import de.peerthing.scenarioeditor.model.IDistribution;

class SIDistribution implements ISIDistribution {
	private IDistribution distribution;

	public SIDistribution(IDistribution distr) {
		if (distr == null) {
			throw new RuntimeException("IDistribution not set.");
		}
		this.distribution = distr;
	}

	public DistributionType getType() {
		return DistributionType.valueOf(distribution.getType().name());
	}

	public double getMin() {
		return distribution.getMin();
	}

	public double getMax() {
		return distribution.getMax();
	}

	public double getMean() {
		return distribution.getMean();
	}

	public double getVariance() {
		return distribution.getVariance();
	}

}
