/**
 * 
 */
package de.peerthing.actioncontributor.functions;

import java.util.ArrayList;
import java.util.List;

import org.jaxen.Context;
import org.jaxen.Function;
import org.jaxen.FunctionCallException;

import de.peerthing.simulation.interfaces.IResource;
import de.peerthing.simulation.interfaces.ISegment;
import de.peerthing.simulation.interfaces.IXPathObject;

/**
 * Returns a list of segments with a given quality.
 * 
 * @author prefec2
 */
public class GetSegmentByQualityfunction implements Function {

	/* (non-Javadoc)
	 * @see org.jaxen.Function#call(org.jaxen.Context, java.util.List)
	 */
	public Object call(Context arg0, List arg1) throws FunctionCallException {
		Object param1 = arg1.get(0);
		String quality;
		if (param1 instanceof String)
			quality=(String)param1;
		else
			throw new FunctionCallException("Parameter must be a string in getSegmentByQuality.");
		
		for(Object obj : arg0.getNodeSet()) {
			if (obj instanceof IResource) {
				IResource res = (IResource)obj;
				List<ISegment> result = new ArrayList<ISegment>();
				for(IXPathObject xpseg : res.getChildAxis()) {
					if (xpseg instanceof ISegment) {
						ISegment seg = (ISegment)xpseg;
						if (seg.getQuality().equals(quality))
							result.add(seg);
					}
				}
				
				return result;
			}
		}
		return null;
	}

}
