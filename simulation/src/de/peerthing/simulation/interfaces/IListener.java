package de.peerthing.simulation.interfaces;

/**
 * Interface that can be implemented by classes that want to be informed about
 * progress during a simulation.
 * 
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface IListener {

	/**
	 * This method is called every time a message has been received during a
	 * simulation run.
	 * 
	 * @param message
	 *            The message that was received.
	 */
	public void messageReceived(IMessage message);

	/**
	 * This method is called every time a significant change in the progress has
	 * occured.
	 * 
	 * @param now
	 *            the present time
	 */
	public void progress(long now);
}
