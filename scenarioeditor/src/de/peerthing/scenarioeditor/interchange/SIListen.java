package de.peerthing.scenarioeditor.interchange;

import de.peerthing.scenarioeditor.model.IListen;

public class SIListen extends SICommand implements ISIListen {
    private IListen listen;

    public SIListen(IListen listen, ISICommandContainer container) {
        this.listen = listen;
        this.container = container;
    }

    public ISIDistribution getDistribution() {
        return new SIDistribution(listen.getDistribution());
    }

    public String getEvent() {
        return listen.getEvent();
    }

	public String getCommandName() {
		return "Listen("+ getEvent()+ ")";
	}

}
