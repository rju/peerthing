/**
 *
 */
package de.peerthing.actioncontributor.functions;

import java.util.List;

import org.jaxen.Context;
import org.jaxen.Function;
import org.jaxen.FunctionCallException;


/**
 * This returns a random element from a list. You can specify one element
 * not to return.
 * 
 * @author prefec2
 *
 */
public class GetRandomElementFunction implements Function {

	/* (non-Javadoc)
	 * @see org.jaxen.Function#call(org.jaxen.Context, java.util.List)
	 */
	public Object call(Context context, List arg1) throws FunctionCallException {
		if (arg1.get(0) != null) {
			if (arg1.get(0) instanceof List) {
				List list = (List)arg1.get(0);
				if (list.size()>0) {
					if (arg1.size()>1) {
						if (arg1.get(1) != null) { /* exclude elements */
							if (arg1.get(1) instanceof List) {
								List exclude = (List)arg1.get(1);
								if (exclude.size()>0)
									/* to avoid warnings, instead of list.removeAll(exclude) */
									for (Object item : exclude) 
										list.remove(item);
							}
						}
					}
					return list.get((int) ((list.size() - 1) * Math.random()));
				}
			}
		}
		throw new RuntimeException("pt:getRandomElement expects a list of elements as parameter.");
	}
}
