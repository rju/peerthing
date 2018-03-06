package de.peerthing.simulation.data;

import java.util.ArrayList;

import de.peerthing.simulation.interfaces.IResource;
import de.peerthing.simulation.interfaces.ISegment;
import de.peerthing.simulation.interfaces.IXPathObject;

/**
 * For each resource which is available at a node a resource object is used.
 * This class implements such a resource on base of XPathContainer handles its
 * content.
 *
 * @author prefec2
 * @review Patrik, 2006-03-25
 */
public class Resource extends XPathContainer implements IResource {

	private ArrayList<String> qualityList; /*
											 * list of quality names 0 = low
											 * priority
											 */

	private XPathAttribute idref;

	/**
	 *
	 * @param idref
	 *            id of an resource (used as a reference)
	 */
	public Resource(int idref) {
		super();
		this.idref = new XPathAttribute("id", String.valueOf(idref));
		this.attributeList.add(this.idref);
	}

	/**
	 * Copy constructor
	 * @param res
	 */
	public Resource(Resource res) {
		super();
		idref = res.idref;
		attributeList.add(idref);

		for (IXPathObject obj : getChildAxis()) {
			addElement(obj);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.data.XPathObject#getId()
	 */
	public int getId() {
		return Integer.parseInt(this.idref.getAttributeStringValue());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see simulation.ISimulation#getElementName()
	 */
	public String getElementName() {
		return "resource";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see simulation.ISimulation#isElement()
	 */
	public boolean isElement() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IResource#setQuality(java.util.ArrayList)
	 */
	public void setQuality(ArrayList<String> qualityList) {
		this.qualityList = qualityList;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IResource#compare(java.lang.String,
	 *      java.lang.String)
	 */
	public int compare(String q1, String q2) {
		return this.qualityLookup(q1) - this.qualityLookup(q2);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IResource#insertSegment(de.peerthing.simulation.interfaces.ISegment)
	 */
	public void insertSegment(ISegment newSegment) {
		/* for each existing segment do: */
		for (int i = 0; i < this.elementList.size(); i++) {
			IXPathObject element = this.elementList.get(i);
			if (element instanceof Segment) {
				Segment segment = ((Segment) element);
				int j = this.compare(newSegment.getQuality(), segment
						.getQuality());
				if ((newSegment.getStart() < segment.getStart())
						&& (newSegment.getEnd() > segment.getStart())
						&& (newSegment.getEnd() < segment.getEnd())) { /*
																		 * left
																		 * intersection
																		 */
					if (j > 0) { /* new quality is higher than old quality */
						/* cut old segment and continue */
						segment.setStart(newSegment.getEnd());
					} else if (j < 0) { /* new quality is lower than old quality */
						/* cut new segment and continue */
						newSegment.setEnd(segment.getStart());
					} else { /* same quality, merge */
						newSegment.setEnd(segment.getEnd());
						this.elementList.remove(i);
						i--; /*
								 * one back, so for can move us one forward
								 * again
								 */
					}
				} else if ((newSegment.getStart() < segment.getEnd())
						&& (newSegment.getEnd() > segment.getEnd())
						&& (newSegment.getStart() > segment.getStart())) { /*
																			 * right
																			 * intersection
																			 */
					if (j > 0) { /* new quality is higher than old quality */
						/* cut old segment and continue */
						segment.setEnd(newSegment.getStart());
					} else if (j < 0) { /* new quality is lower than old quality */
						/* cut new segment and continue */
						newSegment.setStart(segment.getEnd());
					} else { /* same quality, merge */
						newSegment.setStart(segment.getStart());
						this.elementList.remove(i);
						i--; /*
								 * one back, so for can move us one forward
								 * again
								 */
					}
				} else if ((newSegment.getStart() < segment.getStart())
						&& (newSegment.getEnd() > segment.getEnd())) { /* cover */
					if (j > 0) { /* new quality is higher than old quality */
						/* remove old segment and continue */
						this.elementList.remove(i);
						i--; /*
								 * one back, so for can move us one forward
								 * again
								 */
					} else { /*
								 * new quality is lower or equal than old
								 * quality
								 */
						/* new segment is obsolete, drop it */
						return;
					}
				} else if ((newSegment.getStart() > segment.getStart())
						&& (newSegment.getEnd() < segment.getEnd())) { /* split */
					if (j > 0) { /* new quality is higher than old quality */
						/* split old segment in two halfs */
						Segment additionalSegment = new Segment(newSegment
								.getEnd(), segment.getEnd(), segment
								.getQuality());
						segment.setEnd(newSegment.getStart());
						i++; /* goto next position */
						this.elementList.add(i, additionalSegment);
						break; /* no more operations are necessary */
					} else { /*
								 * new quality is lower or equal than old
								 * quality
								 */
						/* new segment is obsolete, drop it */
						return;
					}
				} /* no else because, no intersection => no operation */
			}
		}
		this.elementList.add(newSegment);
	}

	/**
	 *
	 * @param quality
	 * @return returns the numerical value for a quality
	 */
	private int qualityLookup(String quality) {
		return this.qualityList.indexOf(quality);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IResource#isQualityValid(java.lang.String)
	 */
	public boolean isQualityValid(String quality) {
		return (this.qualityLookup(quality) >= 0);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.peerthing.simulation.interfaces.IResource#createSegment(int, int,
	 *      java.lang.String)
	 */
	public ISegment createSegment(int start, int end, String quality) {
		return new Segment(start, end, quality);
	}

	public IXPathObject duplicate() {
		return new Resource(this);
	}

	/**
	 * check for similarity
	 */
	public boolean isSimilarTo(IXPathObject object) {
		if (object instanceof Resource) 
			return super.isSimilarTo(object);
		else
			return false;
	}
}
