/**
 * 
 */
package de.peerthing.actioncontributor.actions;

import java.util.List;
import java.util.Map;

import de.peerthing.simulation.interfaces.DataFactory;
import de.peerthing.simulation.interfaces.IActionExecutor;
import de.peerthing.simulation.interfaces.IActionSimulator;
import de.peerthing.simulation.interfaces.IDataStorage;
import de.peerthing.simulation.interfaces.INode;
import de.peerthing.simulation.interfaces.IResource;
import de.peerthing.simulation.interfaces.ISystemTask;
import de.peerthing.simulation.interfaces.IXPathObject;
import de.peerthing.simulation.interfaces.SimulationException;

/**
 * @author prefec2
 *
 */
public class AddSegmentAction implements IActionExecutor {

	private long time;
	
	/* (non-Javadoc)
	 * @see de.peerthing.simulation.interfaces.IActionExecutor#getExecutionTime()
	 */
	public long getExecutionTime() {
		return time;
	}

	/* (non-Javadoc)
	 * @see de.peerthing.simulation.interfaces.IActionExecutor#executeAction(de.peerthing.simulation.interfaces.IActionSimulator, de.peerthing.simulation.interfaces.INode, de.peerthing.simulation.interfaces.ISystemTask, long, de.peerthing.simulation.interfaces.IDataStorage, java.util.Map)
	 */
	public Object executeAction(IActionSimulator simulator, INode contextNode,
			ISystemTask task, long time, IDataStorage dataStorage,
			Map<String, Object> parameters) {
		Object p1resource = parameters.get("resource");
		Object p2start = parameters.get("start");
		Object p3end = parameters.get("end");
		Object p4quality = parameters.get("quality");
		
		/* check for presence of parameters */
		if (p1resource == null)
			throw new SimulationException("action addSegment: Missing parameter 'resource'.");
		if (p2start == null)
			throw new SimulationException("action addSegment: Missing parameter 'begin'.");
		if (p3end == null)
			throw new SimulationException("action addSegment: Missing parameter 'end'.");
		if (p4quality == null)
			throw new SimulationException("action addSegment: Missing parameter 'quality'.");
		
		/* ok all necessary parameters given */
		
		/* check for correct content of the given parameters */
		
		/* check resource */
		IResource resource = null;
		if (p1resource instanceof List) {
			int size=((List)p1resource).size();
			if (size == 1) { 
				Object obj = ((List)p1resource).get(0);
				if (obj instanceof IResource)
					resource = (IResource)obj;
				else
					throw new SimulationException("action addSegment: Resource expression doesn't not locate a resource but a " + ((IXPathObject)obj).getElementName());
			} else if (size == 0) { /* missing resource => error */
				throw new SimulationException("action addSegment: Resource expression does not locate any resource.");				
			} else /* multiple objects is not allowed */
				throw new SimulationException("action addSegment: Resource expression locates more than one resource.");	
		}
		
		/* check start */
		int start = EvaluateParameter.getInteger("start",p2start);
				
		/* check end */
		int end = EvaluateParameter.getInteger("end",p3end);
				
		/* check quality */
		String quality = EvaluateParameter.getString("quality",p4quality);
		
		/* check if start < end */
		if (start >= end)
			throw new SimulationException("action addSegment: Parameter start is bigger or equal end.");
		
		/* check if segment is inside resource boundaries */
		int size = dataStorage.getResource(resource.getId()).getSize();
		if (start < 0)
			throw new SimulationException("action addSegment: Parameter start is negative.");
		if (start > size)
			throw new SimulationException("action addSegment: Parameter start is bigger than the specified resource.");
		if (end < 0)
			throw new SimulationException("action addSegment: Parameter end is negative.");
		if (end > size)
			throw new SimulationException("action addSegment: Parameter end is bigger than the specified resource.");
		
		/* check if quality is a valid quality */
		if (!resource.isQualityValid(quality))
			throw new SimulationException("action addSegment: The given quality '" + quality + "' is not supported by this model.");
		
		resource.insertSegment(DataFactory.createSegment(start, end, quality));
		
		return null;
	}

}
