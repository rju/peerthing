package de.peerthing.scenarioeditor.interchange;

import de.peerthing.scenarioeditor.model.ILinkSpeed;

class SILinkSpeed implements ISILinkSpeed {
	private ILinkSpeed speed;

	public SILinkSpeed(ILinkSpeed speed) {
		this.speed = speed;
	}

	public long getDelay() {
		return speed.getDelay();
	}

	public long getSpeed() {
		return speed.getSpeed();
	}

}
