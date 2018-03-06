/**
 * 
 */
package de.peerthing.actioncontributor.actions;

import java.util.Map;

import de.peerthing.simulation.interfaces.DataFactory;
import de.peerthing.simulation.interfaces.IActionExecutor;
import de.peerthing.simulation.interfaces.IActionSimulator;
import de.peerthing.simulation.interfaces.IDataStorage;
import de.peerthing.simulation.interfaces.INode;
import de.peerthing.simulation.interfaces.IResource;
import de.peerthing.simulation.interfaces.IResourceDefinition;
import de.peerthing.simulation.interfaces.ISystemTask;
import de.peerthing.simulation.interfaces.SimulationException;

/**
 * @author prefec2
 *
 */
public class NewResourceAction implements IActionExecutor {

	/* (non-Javadoc)
	 * @see de.peerthing.simulation.interfaces.IActionExecutor#getExecutionTime()
	 */
	public long getExecutionTime() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see de.peerthing.simulation.interfaces.IActionExecutor#executeAction(de.peerthing.simulation.interfaces.IActionSimulator, de.peerthing.simulation.interfaces.INode, de.peerthing.simulation.interfaces.ISystemTask, long, de.peerthing.simulation.interfaces.IDataStorage, java.util.Map)
	 */
	public Object executeAction(IActionSimulator simulator, INode contextNode,
			ISystemTask task, long time, IDataStorage dataStorage,
			Map<String, Object> parameters) {
		Object size = parameters.get("size");
		Object popularity = parameters.get("popularity");
		Object quality = parameters.get("quality");
		
		int sizeValue = 0;
		if (size instanceof Integer) {
			sizeValue = ((Integer)size).intValue();
		} else if (size instanceof String) {
			sizeValue = Integer.parseInt((String)size);
		} else
			throw new SimulationException("action newResource: Size must be an integer value.");
		
		int popularityValue = 0;
		if (popularity instanceof Integer) {
			popularityValue = ((Integer)popularity).intValue();
		} else if (popularity instanceof String) {
			popularityValue = Integer.parseInt((String)popularity);
		} else
			throw new SimulationException("action newResource: Popularity must be an integer value.");		
		
		if (!(quality instanceof String)) 
			throw new SimulationException("action newResource: Quality must be a string.");
		
		int l = dataStorage.getResourcePopularityInterval();
		IResourceDefinition definition = DataFactory.createResourceDefinition((int)simulator.getNextResourceId(),sizeValue,l,l+popularityValue);
		dataStorage.addResource(definition);
		dataStorage.setResourcePopularityInterval(l+1+popularityValue);
		
		/* add resource to local node */
		IResource resource = DataFactory.createResource(definition);
		contextNode.addResource(resource);
		/* implicit quality list definition */
		resource.setQuality(dataStorage.getQualityList());
		resource.insertSegment(DataFactory.createSegment(0,sizeValue,(String)quality));
		
		return null;
	}

}
