package de.peerthing.actioncontributor.functions;

import java.util.ArrayList;
import java.util.List;

import org.jaxen.Context;
import org.jaxen.Function;
import org.jaxen.FunctionCallException;

import de.peerthing.simulation.interfaces.IDataStorage;
import de.peerthing.simulation.interfaces.INode;
import de.peerthing.simulation.interfaces.IXPathObject;

/**
 * Chooses a node from the nodes in the current
 * simulation that has a type equal to the type given
 * as a parameter.
 * 
 * @author Michael Gottschalk
 *
 */
public class FindNodeFunction implements Function {

    public Object call(Context contextNode, List arguments)
            throws FunctionCallException {
        IDataStorage storage = (IDataStorage) ((IXPathObject) contextNode
                .getNodeSet().get(0)).getDocument().getChildAxis().get(0);
        List<INode> nodes = storage.getNodesWithType((String) arguments.get(0));
        
        if (nodes == null) {
            return new ArrayList();
        }

        return nodes.get((int) ((nodes.size() - 1) * Math.random()));
    }

}
