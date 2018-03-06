/**
 *
 */
package de.peerthing.actioncontributor.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jaxen.Context;
import org.jaxen.Function;
import org.jaxen.FunctionCallException;
import org.jaxen.UnsupportedAxisException;

import de.peerthing.simulation.interfaces.IDataStorage;
import de.peerthing.simulation.interfaces.IResourceDefinition;
import de.peerthing.simulation.interfaces.IXPathObject;

/**
 * This function returns a resource from a list of resources.
 * The resource is selected from the list by the random_value
 * which is between 0 and 1. The popularity of a resource
 * is important for this function.
 * 
 * @author prefec2
 */
public class ChooseResourceFunction implements Function {

	/* (non-Javadoc)
	 * @see org.jaxen.Function#call(org.jaxen.Context, java.util.List)
	 */
	public Object call(Context arg0, List arg1) throws FunctionCallException {
		/* arg0 is the running context */
		/* arg1 should contain one element with a random number between 0:1 */
		/* check arg1 */
		double randomValue;

		if (arg1.get(0) != null) {
			if (arg1.get(0) instanceof Double) {
				randomValue=(Double)arg1.get(0);
				if ((randomValue<0) || (randomValue>1))
					throw new FunctionCallException("Random value must be in [0;1]");
			} else
				throw new FunctionCallException("Floating point value expected.");
		} else
			throw new FunctionCallException("Missing parameter in chooseResource");

		org.jaxen.Navigator nav = arg0.getNavigator();
		try {
			Iterator list = nav.getChildAxisIterator(nav.getDocumentNode(""));
			Object obj = list.next();
			if (obj instanceof IDataStorage) {
				IDataStorage sim=(IDataStorage)obj;
				if (sim != null) {
					Collection<IXPathObject> content = sim.getChildAxis();
					int selectionValue = (int)(randomValue*(double)sim.getResourcePopularityInterval());
					for(IXPathObject element : content) {
						if (element instanceof IResourceDefinition) {
							IResourceDefinition res = (IResourceDefinition)element;
							if ((res.getStart()<=selectionValue) &&
								(res.getEnd()>=selectionValue)) {
								/* found resource */
								List<IXPathObject> result = new ArrayList<IXPathObject>();
								result.add(res);
								return result;
							}
						}
					}
				} else
					throw new RuntimeException("Fatal Error: Simulation node not found.");
			} else
				throw new RuntimeException("Fatal Error: Simulation node not found.");
		} catch (UnsupportedAxisException e) {
			throw new RuntimeException("Fatal Error: " + e.getLocalizedMessage());
		}
		return new ArrayList();
	}

}
