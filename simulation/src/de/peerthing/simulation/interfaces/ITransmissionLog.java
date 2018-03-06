/**
 * 
 */
package de.peerthing.simulation.interfaces;

/**
 * @author prefec2
 * 
 */
public interface ITransmissionLog extends IXPathObject {
	/**
	 * @return Returns the progress of the transmission
	 */
	public int getProgress();

	/**
	 * @param progress
	 *            The progress to set.
	 */
	public void setProgress(int progress);

	/**
	 * @return Returns the message.
	 */
	public IMessage getMessage();

	/**
	 * @return Returns the size.
	 */
	public int getSize();
}
