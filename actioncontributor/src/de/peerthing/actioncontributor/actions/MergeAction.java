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
public class MergeAction implements IActionExecutor {

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
		IXPathContainer container;
		
		/* get single element from destionation list */
		if (destination instanceof List) {
			Object content = ((List)destination).get(0);
			if (content instanceof IXPathContainer) {
				/* can hold data */
				container=((IXPathContainer)content);
			} else 
				throw new SimulationException("action merge: Destination is not an XPathContainer.");
		} else
			throw new SimulationException("action merge: Destination is not an XPathObject.");
		if (source != null) {
			if (source instanceof List) {
				List list=(List)source;
				if (list.size()>0) { /* a real list */
					this.time = list.size();
					/* check list items */
					for(Object obj : list) {
						if (obj instanceof IXPathAttribute) {
							Object attr = ((IXPathAttribute)obj).getValue();
							if (attr instanceof IXPathObject) 
								if (!container.contains((IXPathObject)attr))
									container.addElement((IXPathObject)attr);
							else if (attr instanceof String) { 
								container.appendContent((String)attr);
							} else if (attr instanceof Number) { 
								container.appendContent(String.valueOf(attr));
							} else 
								throw new SimulationException("[" + contextNode.getId() + ":" + task.getId() + "] merge: Attribute content " + attr.getClass().getSimpleName() + " cannot be appended to the content of a container.");
						} else if (obj instanceof IXPathObject) { /* all other XPathObjects can be inserted as is */
							if (!container.contains((IXPathObject)obj))
								container.addElement((IXPathObject)obj);
						} else if (obj instanceof String) { 
							container.appendContent((String)obj);
						} else if (obj instanceof Number) { 
							container.appendContent(String.valueOf(obj));
						} else 
							throw new SimulationException("action merge: XPath result element " + obj.getClass().getSimpleName() + " cannot be appended to the content of a container.");
					}
				}
				/* empty list, nothing to do */
			} else {
				this.time = 1;
				if (source instanceof String) { 
					container.appendContent((String)source);
				} else if (source instanceof Number) { 
					container.appendContent(String.valueOf(source));
				} else 
					throw new SimulationException("action merge: XPath result " + source.getClass().getSimpleName() + " cannot be appended to the content of a container.");
			}
			return null;
		} else 
			throw new SimulationException("action merge: Parameter source expected.");
	}

	/*
	 *  (non-Javadoc)
	 * @see de.peerthing.simulation.interfaces.IActionExecutor#getExecutionTime()
	 */
	public long getExecutionTime() {
		return this.time;
	}

}
