package de.peerthing.scenarioeditor.interchange;

abstract class SICommand implements ISICommand {
	protected ISICommandContainer container;

	public ISICommandContainer getCommandContainer() {
		return container;
	}

}
