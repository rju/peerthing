package de.peerthing.simulation.interfaces;

import de.peerthing.scenarioeditor.interchange.ISIResourceCategory;

/**
 * A ResourceDefinition is something like a file that is present on one or more
 * nodes. Each resource can be searched. To select a resource, a equal
 * distributed random number is used. The resources occupy on the range of the
 * random numbers a specific interval specified by "start" and "end"
 * 
 * @author mg
 * @author prefec2
 * @review Boris, 2006-03-27
 * 
 */
public interface IResourceDefinition extends IXPathObject {

	/**
	 * Sets the ID of the resource, which is unique in a simulation.
	 * 
	 * @param id
	 */
	public void setId(int id);

	/**
	 * 
	 * @return returns the unique id of the resource definition
	 */
	public int getId();

	/**
	 * Sets the size of the resource in bytes.
	 * 
	 * @param size
	 */
	public void setSize(int size);

	/**
	 * @return return the size of the resource in bytes
	 */
	public int getSize();

	/**
	 * Set the start of the possibility interval
	 * 
	 * @param start
	 */
	public void setStart(int start);

	/**
	 * 
	 * @return retruns the start of the possibility interval
	 */
	public int getStart();

	/**
	 * Set the end of the possibility interval
	 * 
	 * @param end
	 */
	public void setEnd(int end);

	/**
	 * 
	 * @return retruns the start of the possibility interval
	 */
	public int getEnd();

	/**
	 * get the category for this resource definition
	 */
	public ISIResourceCategory getCategory();
}
