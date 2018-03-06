/**
 * 
 */
package de.peerthing.simulation.data;

import java.util.ArrayList;

import de.peerthing.simulation.interfaces.IMessage;
import de.peerthing.simulation.interfaces.IRegister;
import de.peerthing.simulation.interfaces.ITransmissionLog;

/**
 * This class implements the outgoing and incoming register for messages.
 * 
 * @author prefec2
 * 
 */
public class Register implements IRegister {
	private ArrayList<ITransmissionLog> transmissionList;

	public Register() {
		this.transmissionList = new ArrayList<ITransmissionLog>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.interfaces.IRegister#addTransmission(de.peerthing.simulation.interfaces.ITransmissionLog)
	 */
	public void addTransmission(ITransmissionLog log) {
		this.transmissionList.add(log);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.interfaces.IRegister#getTransmission(de.peerthing.simulation.interfaces.ITransmissionLog)
	 */
	public ITransmissionLog getTransmission(ITransmissionLog logRef) {
		for (ITransmissionLog log : this.transmissionList) {
			if (log == logRef)
				return log;
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.interfaces.IRegister#removeTransmission(de.peerthing.simulation.interfaces.ITransmissionLog)
	 */
	public void removeTransmission(ITransmissionLog logRef) {
		this.transmissionList.remove(logRef);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.interfaces.IRegister#getTransmission(de.peerthing.simulation.interfaces.IMessage)
	 */
	public ITransmissionLog getTransmission(IMessage message) {
		for (ITransmissionLog log : this.transmissionList) {
			if (log.getMessage().equals(message))
				return log;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.interfaces.IRegister#removeTransmission(de.peerthing.simulation.interfaces.IMessage)
	 */
	public void removeTransmission(IMessage message) {
		for (ITransmissionLog log : this.transmissionList) {
			if (log.getMessage().equals(message)) {
				this.transmissionList.remove(log);
				return;
			}
		}
	}
}
