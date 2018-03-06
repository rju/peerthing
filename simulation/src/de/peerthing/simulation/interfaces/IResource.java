package de.peerthing.simulation.interfaces;

import java.util.ArrayList;

/**
 * Public interface for resources handled by the data storage compoenent.
 * 
 * @author prefec2
 * 
 */
public interface IResource extends IXPathContainer {

	/**
	 * set the list of possible qualities for this resource
	 * 
	 * @param qualityList
	 */
	public void setQuality(ArrayList<String> qualityList);

	/**
	 * this method inserts a segment into the list. To do so it tries to find
	 * either a hole in the list of segments or tries to recalculate the
	 * segments.
	 * 
	 * Reminder: all segments comply to the contrain start < stop
	 * 
	 * @param segment
	 */
	public void insertSegment(ISegment segment);

	public boolean isQualityValid(String quality);

	/**
	 * this method inserts a segment into the list. To do so it tries to find
	 * either a hole in the list of segments or tries to recalculate the
	 * segments.
	 * 
	 * Reminder: all segments comply to the contrain start < stop
	 * 
	 * @param segment
	 */
	public ISegment createSegment(int start, int end, String quality);

	/**
	 * 
	 * @return returns the id of the resource definition
	 */
	public int getId();

	/**
	 * compares two qualities
	 * 
	 * @param q1
	 * @param q2
	 * @return returns a value lower 0, greater 0 or 0 (equal quality)
	 */
	public int compare(String q1, String q2);
}
