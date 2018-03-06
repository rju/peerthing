/**
 * 
 */
package de.peerthing.actioncontributor.functions;

import java.util.ArrayList;
import java.util.List;

import org.jaxen.Context;
import org.jaxen.Function;
import org.jaxen.FunctionCallException;

import de.peerthing.simulation.interfaces.IDataStorage;
import de.peerthing.simulation.interfaces.IResource;
import de.peerthing.simulation.interfaces.IResourceDefinition;
import de.peerthing.simulation.interfaces.ISegment;
import de.peerthing.simulation.interfaces.IXPathObject;

/**
 * Return a list of segments, which describe the missing pieces of a resource
 * depending on a given quality.
 * 
 * @author prefec2
 *
 */
public class GetSegmentByMissingQualityFunction implements Function {

	/**
	 * find the next segment from the limit.
	 * @param list list of segments (but could contain non-segment objects, which will be ignored)
	 * @param segStart the start value for the next segment. initial value, should be file size
	 * @param limit the limit where the search begins.
	 * 
	 * @return returns the next segment or null
	 */
	private ISegment findNextSegment(List<IXPathObject> list, int segStart, int limit, String quality, IResource res) {
		ISegment found = null;
				
		for(IXPathObject child : list) {
			if (child instanceof ISegment) {
				ISegment segment = (ISegment)child;
				if ((limit < segment.getStart()) &&
					(segment.getStart() < segStart) &&
					(res.compare(segment.getQuality(),quality)>=0)) {
					found = segment;
					segStart = segment.getStart();
				}
			}
		}
		
		return found;
	}
	
	/* (non-Javadoc)
	 * @see org.jaxen.Function#call(org.jaxen.Context, java.util.List)
	 */
	public Object call(Context arg0, List arg1) throws FunctionCallException {
		Object param1 = arg1.get(0);
		Object param2 = arg1.get(1);
		String quality;
		IResource resource;
		
		if (param1 instanceof List) {
			List list=(List)param1;
			if (list.size()>0)
				if (list.get(0) instanceof IResource)
					resource=(IResource)list.get(0);
				else
					throw new FunctionCallException("First parameter of getSegmentByMissingQuality must contain a resource.");
			else
				throw new FunctionCallException("First parameter of getSegmentByMissingQuality must contain a resource.");
		} else
			throw new FunctionCallException("First parameter of getSegmentByMissingQuality must be a node-set.");
		
		if (param2 instanceof String)
			quality=(String)param2;
		else
			throw new FunctionCallException("Second parameter of getSegmentByMissingQuality must be a string.");
		
		/* we assume, we have nothing so, we start with one segment with 
		 * the full length.
		 */
		List<IXPathObject> list = resource.getDocument().getChildAxis();
		for (IXPathObject item : list) {
			if (item instanceof IDataStorage) {
				int id = resource.getId();
				IResourceDefinition resDef = ((IDataStorage)item).getResource(id);
										
				List<ISegment> result = new ArrayList<ISegment>();
				/* scan the segment list for the first segment */
				int limit = 0;
				int segEnd = 0;
				int end = resDef.getSize();
				ISegment found;
				while ((found = findNextSegment(resource.getChildAxis(),end,limit,quality,resource))!=null) {
					/* generate missing segment form segEnd to found.getStart() */
					if (segEnd<found.getStart()) {
						result.add(resource.createSegment(segEnd,found.getStart(),quality));
						segEnd=found.getEnd();
					}
				}
				/* generate final segment */
				if (segEnd<end) {
					result.add(resource.createSegment(segEnd,end,quality));
				}
				
				return result;
			}
		}
		return null;
	}

}
