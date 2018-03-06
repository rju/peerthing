/**
 *
 */
package de.peerthing.simulation.data;

import de.peerthing.simulation.interfaces.IMessage;
import de.peerthing.simulation.interfaces.ITransmissionLog;
import de.peerthing.simulation.interfaces.IXPathObject;

/**
 * Remembers all neccessary information for a message transfer. It keeps track
 * of already transmitted and received data, depends on the usage of the class.
 *
 * @author prefec2
 *
 */
public class TransmissionLog extends XPathObject implements ITransmissionLog {
	private int size; /* size of the message */

	private int progress; /* progress of the transfer */

	private IMessage message; /* the message itself */

	public TransmissionLog(int size, IMessage message) {
		this.size = size;
		this.message = message;
		this.progress = 0;
	}

	/**
	 * Copy constructor
	 * @param copy
	 */
	public TransmissionLog(TransmissionLog copy) {
		size = copy.size;
		message = copy.message;
		progress = copy.progress;
	}

	/**
	 * @return Returns the progress.
	 */
	public int getProgress() {
		return progress;
	}

	/**
	 * @param progress
	 *            The progress to set.
	 */
	public void setProgress(int progress) {
		this.progress = progress;
	}

	/**
	 * @return Returns the message.
	 */
	public IMessage getMessage() {
		return message;
	}

	/**
	 * @return Returns the size.
	 */
	public int getSize() {
		return size;
	}

	public IXPathObject duplicate() {
		return new TransmissionLog(this);
	}
	
	/**
	 * check for similarity
	 */
	public boolean isSimilarTo(IXPathObject object) {
		if (object instanceof TransmissionLog) 
			return super.isSimilarTo(object);
		else
			return false;
	}
}
