package de.peerthing.simulation.data;

import de.peerthing.simulation.interfaces.ISegment;
import de.peerthing.simulation.interfaces.IXPathAttribute;
import de.peerthing.simulation.interfaces.IXPathObject;

/**
 * Describes one data segment of an available resource in a node.
 *
 * @author prefec2
 * @review Patrik, 2006-03-25
 */

public class Segment extends XPathObject implements ISegment {
	private int end;

	private XPathAttribute quality;

	private int start;

	private XPathAttribute startAttr;

	private XPathAttribute endAttr;

	public Segment(int start, int end, String quality) {
		super();
		this.start = start;
		this.end = end;
		this.startAttr = new XPathAttribute("start", String.valueOf(start));
		addAttribute(this.startAttr);
		this.endAttr = new XPathAttribute("end", String.valueOf(end));
		addAttribute(this.endAttr);
		this.quality = new XPathAttribute("quality", quality);
		addAttribute(this.quality);
	}

	/**
	 * Copy constructor
	 *
	 * @param copy
	 */
	public Segment(Segment copy) {
		start = copy.start;
		end = copy.end;

		for (IXPathAttribute attr : copy.getAttributeAxisList()) {
			addAttribute(attr);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see simulation.ISimulation#getElementName()
	 */
	public String getElementName() {
		return "segment";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see simulation.ISimulation#isElement()
	 */
	public boolean isElement() {
		return true;
	}

	/**
	 * @return Returns the quality.
	 */
	public String getQuality() {
		return (String) this.quality.getValue();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.ISegment#setQuality(java.lang.String)
	 */
	public void setQuality(String quality) {
		this.quality.setValue(quality);
	}

	/**
	 * @return Returns the start.
	 */
	public int getStart() {
		return this.start;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.ISegment#setStart(int)
	 */
	public void setStart(int start) {
		this.start = start;
		this.startAttr.setValue(String.valueOf(start));
	}

	/**
	 * @return Returns the stop.
	 */
	public int getEnd() {
		return this.end;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.ISegment#setEnd(int)
	 */
	public void setEnd(int end) {
		this.end = end;
		this.endAttr.setValue(String.valueOf(end));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IXPathObject#getSize()
	 */
	public int getSize() {
		return 8 + this.getQuality().length();
	}

	public IXPathObject duplicate() {
		return new Segment(this);
	}
	
	/**
	 * check for similarity
	 */
	public boolean isSimilarTo(IXPathObject object) {
		if (object instanceof Segment) 
			return super.isSimilarTo(object);
		else
			return false;
	}

}
