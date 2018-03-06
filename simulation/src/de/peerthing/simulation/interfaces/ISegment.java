/**
 * 
 */
package de.peerthing.simulation.interfaces;

/**
 * Interface for data segments.
 * 
 * @author prefec2
 * 
 */
public interface ISegment extends IXPathObject {
	/**
	 * @return Returns the quality of the segment
	 */
	public String getQuality();

	/**
	 * Set the quality for the segment
	 * 
	 * @param quality
	 */
	public void setQuality(String quality);

	/**
	 * @return Returns the start.
	 */
	public int getStart();

	/**
	 * Set the start of the segment
	 * 
	 * @param start
	 */
	public void setStart(int start);

	/**
	 * @return Returns the end of the segment.
	 */
	public int getEnd();

	/**
	 * Set the end of the segment
	 * 
	 * @param end
	 */
	public void setEnd(int end);
}
