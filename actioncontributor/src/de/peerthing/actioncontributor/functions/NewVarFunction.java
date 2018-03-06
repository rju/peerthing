/**
 *
 */
package de.peerthing.actioncontributor.functions;

import java.util.List;

import org.jaxen.Context;
import org.jaxen.Function;
import org.jaxen.FunctionCallException;

import de.peerthing.simulation.interfaces.DataFactory;
import de.peerthing.simulation.interfaces.IXPathAttribute;
import de.peerthing.simulation.interfaces.IXPathContainer;
import de.peerthing.simulation.interfaces.IXPathObject;
import de.peerthing.simulation.interfaces.SimulationException;

/**
 * Creates a new variable with a given name and content.
 * 
 * @author prefec2
 */
public class NewVarFunction implements Function {

    /*
     * (non-Javadoc)
     *
     * @see org.jaxen.Function#call(org.jaxen.Context, java.util.List)
     */
    public Object call(Context arg0, List arg1) throws FunctionCallException {
    	if (arg1.size() == 2) {
            Object obj = arg1.get(0);
            if (obj instanceof String) {
            	String name = (String)obj;
                IXPathContainer var = DataFactory.createVariable(name, "");
                obj = arg1.get(1);
                if (obj instanceof String) {
                    var.setContent((String) obj);
                } else if (obj instanceof List) {
                	List list = (List)obj;
                	if (list.size() == 1) {
	                    /* the list should contain only one element */
	                    Object item = list.get(0);
	                    if (item instanceof IXPathAttribute) {
	                        var.setContent(((IXPathAttribute) item)
	                                .getAttributeStringValue());
	                    } else if (item instanceof IXPathObject) {
	                    	IXPathObject object = (IXPathObject) item;
	                    	for (IXPathObject xobj : object.getChildAxis()) {
	                    		var.addElement(xobj);
	                    	}
	                    } else {
	                        throw new SimulationException(item.getClass().getName()
	                                + " are not allowed here.");
	                    }
                	} else if (list.size() == 0)
                		throw new SimulationException("pt:newVar: Value for new variable " + name + " is empty");
                	else
                		throw new SimulationException("pt:newVar: Value for new variable " + name + " is a list with items. This is not allowed.");
                } else if (obj instanceof Double) {
                    var.setContent(String.valueOf((Double)obj));
                } else
                    throw new RuntimeException("Missing code "
                            + obj.getClass().getName());
                return var;
            } else
                throw new SimulationException(
                        "pt:newVar expects a name as first parameter not "
                                + obj.getClass().getName());
        } else
            throw new SimulationException(
                    "pt:newVar expects two parameters");
    }

}
