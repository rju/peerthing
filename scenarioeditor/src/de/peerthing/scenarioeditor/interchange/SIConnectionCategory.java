package de.peerthing.scenarioeditor.interchange;

import de.peerthing.scenarioeditor.model.IConnectionCategory;


class SIConnectionCategory implements ISIConnectionCategory {
	private IConnectionCategory cat;
	private ISILinkSpeed uplink, downlink;

	public SIConnectionCategory(IConnectionCategory cat) {
		this.cat = cat;
		uplink = new SILinkSpeed(cat.getUplinkSpeed());
		downlink = new SILinkSpeed(cat.getDownlinkSpeed());
	}


	public String getName() {
		return cat.getName();
	}

	public DuplexOption getDuplex() {
		return DuplexOption.valueOf(cat.getDuplex().name());
	}

	public ISILinkSpeed getUplinkSpeed() {
		return uplink;
	}

	public ISILinkSpeed getDownlinkSpeed() {
		return downlink;
	}

}
