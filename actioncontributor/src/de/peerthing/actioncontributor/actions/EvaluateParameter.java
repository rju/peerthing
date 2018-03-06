/**
 * 
 */
package de.peerthing.actioncontributor.actions;

import java.util.List;

import de.peerthing.simulation.interfaces.IXPathAttribute;
import de.peerthing.simulation.interfaces.IXPathContainer;
import de.peerthing.simulation.interfaces.IXPathObject;
import de.peerthing.simulation.interfaces.SimulationException;

/**
 * @author prefec2
 * 
 */
public class EvaluateParameter {
	public static int getInteger(String paramName, Object parameter) {
		if (parameter instanceof List) { /* could be a variable */
			int size = ((List) parameter).size();
			if (size == 1) {
				Object obj = ((List) parameter).get(0);
				if (obj instanceof IXPathContainer) {
					try {
						return (int)Double.parseDouble(((IXPathContainer) obj)
								.getContent());
					} catch (NumberFormatException e) {
						throw new SimulationException("Parameter '" + paramName
								+ "' expects an integer, but "
								+ ((IXPathContainer) obj).getContent()
								+ " is not a number.");
					}
				} else if (obj instanceof IXPathAttribute) {
					IXPathAttribute attr = (IXPathAttribute) obj;

					obj = attr.getValue();
					if (obj instanceof IXPathContainer) {
						try {
							return (int)Double.parseDouble(((IXPathContainer) obj)
									.getContent());
						} catch (NumberFormatException e) {
							throw new SimulationException("Parameter '"
									+ paramName + "' expects an integer, but "
									+ ((IXPathContainer) obj).getContent()
									+ " is not a number.");
						}
					} else if (obj instanceof String) {
						try {
							return (int)Double.parseDouble((String) obj);
						} catch (NumberFormatException e) {
							throw new SimulationException("Parameter '"
									+ paramName + "' expects an integer, but "
									+ (String) obj + " is not a number.");
						}
					} else if (obj instanceof Integer) {
						return ((Integer) obj).intValue();
					} else
						throw new SimulationException("Content of "
								+ ((IXPathObject) obj).getElementName()
								+ " is not applicable to parameter '"
								+ paramName + "'.");
				} else
					throw new SimulationException(" "
							+ ((IXPathObject) obj).getElementName());
			} else if (size == 0) { /* missing resource => error */
				throw new SimulationException("Expression of parameter '"
						+ paramName + "' has no value.");
			} else
				/* multiple objects is not allowed */
				throw new SimulationException("Expression of parameter '"
						+ paramName + "' returns multiple values.");
		} else if (parameter instanceof String) { /* try cast */
			try {
				return Integer.parseInt((String) parameter);
			} catch (NumberFormatException e) {
				throw new SimulationException("Parameter '" + paramName
						+ "' expects an integer, but " + (String) parameter
						+ " is not a number.");
			}
		} else if (parameter instanceof Integer)
			return ((Integer) parameter).intValue();
		else {
			throw new SimulationException("Parameter '" + paramName
					+ "' is not an integer value.");
		}
	}

	public static String getString(String paramName, Object parameter) {
		if (parameter instanceof List) { /* could be a variable */
			int size = ((List) parameter).size();
			if (size == 1) {
				Object obj = ((List) parameter).get(0);
				if (obj instanceof IXPathContainer) {

					return ((IXPathContainer) obj).getContent();
				} else if (obj instanceof IXPathAttribute) {
					IXPathAttribute attr = (IXPathAttribute) obj;

					obj = attr.getValue();
					if (obj instanceof IXPathContainer) {
						return ((IXPathContainer) obj).getContent();
					} else if (obj instanceof String) {
						return (String) obj;
					} else
						throw new SimulationException("Content of "
								+ ((IXPathObject) obj).getElementName()
								+ " is not applicable to parameter '"
								+ paramName + "'.");
				} else
					throw new SimulationException(" "
							+ ((IXPathObject) obj).getElementName());
			} else if (size == 0) { /* missing resource => error */
				throw new SimulationException("Expression of parameter '"
						+ paramName + "' has no value.");
			} else
				/* multiple objects is not allowed */
				throw new SimulationException("Expression of parameter '"
						+ paramName + "' returns multiple values.");
		} else if (parameter instanceof String) {
			return (String) parameter;
		} else {
			throw new SimulationException("Parameter '" + paramName
					+ "' is not a string value.");
		}
	}
}
