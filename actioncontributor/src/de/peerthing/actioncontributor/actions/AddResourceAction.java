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
import de.peerthing.simulation.interfaces.IResourceDefinition;
import de.peerthing.simulation.interfaces.ISystemTask;
import de.peerthing.simulation.interfaces.SimulationException;

/**
 * @author prefec2
 *
 */
public class AddResourceAction implements IActionExecutor {

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
		Object obj = parameters.get("resource");
				
		if (obj != null) {
			if (obj instanceof IResourceDefinition) {
				/* add resource to local node */
				IResource resource = 
					DataFactory.createResource((IResourceDefinition)obj);
				contextNode.addResource(resource);
				/* implicit quality list definition */
				resource.setQuality(dataStorage.getQualityList());
			} else if (obj instanceof List) {
				if (((List)obj).size()>0) {
					Object resDef = ((List)obj).get(0);
					if (resDef instanceof IResourceDefinition) {
						/* add resource to local node */
						IResource resource = 
							DataFactory.createResource((IResourceDefinition)resDef);
						contextNode.addResource(resource);
						/* implicit quality list definition */
						resource.setQuality(dataStorage.getQualityList());
					} else
						throw new SimulationException("action addResource: No valid resource definition specified.");
				} else
					throw new SimulationException("action addResource: No valid resource definition specified.");
			} else
				throw new SimulationException("action addResource: No valid resource definition specified.");
		}
				
		return null;
	}

}
