/**
 * 
 */
package de.peerthing.actioncontributor.actions;

import java.util.List;
import java.util.Map;

import de.peerthing.simulation.interfaces.IActionExecutor;
import de.peerthing.simulation.interfaces.IActionSimulator;
import de.peerthing.simulation.interfaces.IDataStorage;
import de.peerthing.simulation.interfaces.INode;
import de.peerthing.simulation.interfaces.IResourceDefinition;
import de.peerthing.simulation.interfaces.ISystemTask;
import de.peerthing.simulation.interfaces.IXPathContainer;
import de.peerthing.simulation.interfaces.IXPathObject;
import de.peerthing.simulation.interfaces.SimulationException;

/**
 * @author prefec2
 *
 */
public class RemoveResourceAction implements IActionExecutor {

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
		Object element = parameters.get("element");
			
		time=0;
		if (element!=null) {
			if (element instanceof List) {
				for(Object obj : ((List)element)) {
					if (obj instanceof IResourceDefinition) {
						IResourceDefinition xpobj = (IResourceDefinition)obj;
						
						((IXPathContainer)xpobj.getParent()).removeElement(xpobj);
						/* reorder remaining ranges */
						int first = xpobj.getStart();
						for (IXPathObject xpres : xpobj.getFollowingAxisList()) {
							if (xpres instanceof IResourceDefinition) {
								IResourceDefinition res = (IResourceDefinition)xpres;
								int popularity = res.getEnd()-res.getStart()+1;
								res.setStart(first);
								res.setEnd(first+popularity);
								first=res.getEnd()+1;
							}
						}
					} else 
						throw new SimulationException("action removeResource: Fatal: Illegal object in XPath result " + obj.getClass().getName());
				}
			} else
				throw new SimulationException("action removeResource: Element is not an object");
		} else 
			throw new SimulationException("action removeResource: Missing element parameter");
		return null;
	}

}
