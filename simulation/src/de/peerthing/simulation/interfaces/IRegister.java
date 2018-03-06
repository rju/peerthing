/**
 * 
 */
package de.peerthing.simulation.interfaces;

/**
 * The register interface defines the interface to the send and receive
 * registers and transport stacks.
 * 
 * @author prefec2
 * 
 */
public interface IRegister {
	/**
	 * Add a transmission log to a message register
	 * 
	 * @param log
	 */
	public void addTransmission(ITransmissionLog log);

	/**
	 * Search for a specific transmission log with the id
	 * 
	 * @param logRef
	 *            reference to the log
	 * @return Returns the transmission log on success or null on failure
	 */
	public ITransmissionLog getTransmission(ITransmissionLog logRef);

	/**
	 * Search for a specific transmission log by message
	 * 
	 * @param message
	 *            a message in a transmission log
	 * @return Returns the transmission log on success or null on failure
	 */
	public ITransmissionLog getTransmission(IMessage message);

	/**
	 * Remove a transmission log specified by the id
	 * 
	 * @param logRef
	 *            reference to the log
	 */
	public void removeTransmission(ITransmissionLog logRef);

	/**
	 * Remove a transmission log from the register which contains message.
	 * 
	 * @param message
	 */
	public void removeTransmission(IMessage message);
}
