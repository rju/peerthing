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
import de.peerthing.simulation.interfaces.ISystemTask;
import de.peerthing.simulation.interfaces.IXPathAttribute;
import de.peerthing.simulation.interfaces.IXPathContainer;
import de.peerthing.simulation.interfaces.IXPathObject;
import de.peerthing.simulation.interfaces.SimulationException;


/**
 * @author prefec2
 * 
 */
public class SetValueAction implements IActionExecutor {

	private long time;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.peerthing.simulation.interfaces.IActionExecutor#executeAction(de.peerthing.simulation.interfaces.IActionNode,
	 *      de.peerthing.simulation.interfaces.IDataStorage, java.util.Map)
	 */
	public Object executeAction(IActionSimulator simulator, INode contextNode, ISystemTask task,
			long time, IDataStorage dataStorage, Map<String, Object> parameters) {
		Object source = parameters.get("source");
		Object destination = parameters.get("destination");
		IXPathContainer container = null; /* null is necessary to prevent compilation problems */
		
		/* get single element from destionation list */
		if (destination instanceof List) {
			if (((List)destination).size()>0) { 
				Object content = ((List)destination).get(0);
				if (content instanceof IXPathContainer) {
					/* can hold data */
					container=((IXPathContainer)content);
				} else 
					throw new SimulationException("action setValue: Destination is not an XPathContainer.");
			} else
				throw new SimulationException("action setValue: Destination is empty.");
		} else
			throw new SimulationException("action setValue: Destination is not an XPathObject.");
		if (source != null) {
			if (source instanceof List) {
				List list=(List)source;
				this.time = list.size();
				/* check list items */
				for(Object obj : list) {
					if (obj instanceof IXPathObject) { /* no boxing */
						if (obj instanceof IXPathAttribute) {
							container.removeAllElement();
							container.addElement(simulator.
									createVariable(((IXPathAttribute)obj).getAttributeName(),
											((IXPathAttribute)obj).getAttributeStringValue()));	
						} else { /* variable boxing deluxe */
							container.removeAllElement();
							container.addElement((IXPathObject)obj);
						}
					} else { /* do variable boxing */
						/* can this happen? */
						throw new SimulationException("action setValue: Fatal: Cannot handle source. Implementation insufficient.");
					}
				}
			} else {
				this.time = 1;
                container.setContent(source.toString());
			}
			return null;
		} else 
			throw new SimulationException("action setValue: Parameter source expected.");
	}

	public long getExecutionTime() {
		return this.time;
	}


}
