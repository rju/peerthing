/**
 *
 */
package de.peerthing.actioncontributor.functions;

import java.util.List;

import org.jaxen.Context;
import org.jaxen.Function;
import org.jaxen.FunctionCallException;

import de.peerthing.simulation.interfaces.DataFactory;

/**
 * @author prefec2
 *
 */
public class RandomFunction implements Function {

	/* (non-Javadoc)
	 * @see org.jaxen.Function#call(org.jaxen.Context, java.util.List)
	 */
	public Object call(Context arg0, List arg1) throws FunctionCallException {
		return DataFactory.getNextRandomNumber();
	}

}
