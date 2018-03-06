/**
 * 
 */
package de.peerthing.simulation.data;

/**
 * Describes periods where the network interface is not used. Initial all Nodes
 * have at least one time frame from NOW to END of simulation.
 * 
 * @author prefec2
 * 
 */
public class TimeFrame {

	private long begin;

	private long end;

	/**
	 * 
	 */
	public TimeFrame(long begin, long end) {
		this.begin = begin;
		this.end = end;
	}

	/**
	 * @return Returns the begin.
	 */
	public long getBegin() {
		return begin;
	}

	/**
	 * @return Returns the end.
	 */
	public long getEnd() {
		return end;
	}

	/**
	 * Sets the begin of an time frame
	 * 
	 * @param begin
	 *            The begin to set.
	 */
	public void setBegin(long begin) {
		this.begin = begin;
	}

	/**
	 * Sets the end of an time frame
	 * 
	 * @param end
	 *            The end to set.
	 */
	public void setEnd(long end) {
		this.end = end;
	}

	/**
	 * checks if a given value lies in the time frame
	 * 
	 * @param value
	 *            to be checked
	 * 
	 * @return Returns true if the value lies between begin and end else false
	 */
	public boolean isInside(long value) {
		return ((begin <= value) && (end >= value));
	}
}
