package de.peerthing.simulation.data;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.jaxen.BaseXPath;
import org.jaxen.Function;
import org.jaxen.JaxenException;
import org.jaxen.SimpleFunctionContext;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.SimpleVariableContext;
import org.jaxen.XPath;
import org.jaxen.XPathFunctionContext;

import de.peerthing.simulation.interfaces.IXPathObject;

/**
 * Document root of the XPath environment of the simulation runtime system.
 *
 * @author prefec2
 * @review Patrik, 2006-03-25
 *
 */
public class Document extends XPathContainer {
	private Navigator navigator;

	private SimpleFunctionContext functionContext;

	private SimpleNamespaceContext namespaceContext;

	private SimpleVariableContext variableContext;

	public Document(Simulation sim) {
		super();
		this.setDocument(this);
		this.setParent(null);
		this.addElement(sim);

		navigator = new Navigator(this);
		functionContext = new XPathFunctionContext();

		IExtension[] extensions = Platform.getExtensionRegistry()
				.getExtensionPoint(
						"de.peerthing.simulation.functionRegistration")
				.getExtensions();

		for (IExtension extension : extensions) {
			for (IConfigurationElement elem : extension
					.getConfigurationElements()) {
				String funcName = elem.getAttribute("name");
				try {
					Function func = (Function) elem
							.createExecutableExtension("class");
					functionContext.registerFunction("http://exslt.org/math",
							funcName, func);
				} catch (CoreException e) {
					throw new RuntimeException("An XPath function declared"
							+ " in the simulation extension point cannot be"
							+ " instantiated: " + e.getMessage(), e);
				}
			}
		}

		namespaceContext = new SimpleNamespaceContext();
		namespaceContext.addNamespace("pt", "http://exslt.org/math");

		variableContext = new SimpleVariableContext();
	}

	/**
	 * Copy constructor
	 *
	 * @param copy
	 */
	public Document(Document copy) {
		this((Simulation) copy.getChildAxis().get(0));
	}

	/**
	 * Evaluate an expression in the given context
	 *
	 * @param context
	 * @param expression
	 *
	 * @return returns the result fo the expression
	 */
	public Object evaluate(IXPathObject context, String expression) {
		try {
			XPath path = new BaseXPath(expression, navigator);
			path.setFunctionContext(functionContext);
			path.setNamespaceContext(namespaceContext);

			// set the context variable to the context node.
			// This is useful if the context is needed in a
			// condition.
			variableContext.setVariableValue("context", context);

			path.setVariableContext(variableContext);
			Object result = path.evaluate(context);
			return result;
		} catch (JaxenException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see simulation.ISimulation#getParentNode()
	 */
	public XPathObject getParent() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see simulation.ISimulation#isDocument()
	 */
	public boolean isDocument() {
		return true;
	}

	/*
	 *  (non-Javadoc)
	 * @see de.peerthing.simulation.interfaces.IXPathObject#duplicate()
	 */
	public IXPathObject duplicate() {
		return new Document(this);
	}

	/**
	 * check for similarity
	 */
	public boolean isSimilarTo(IXPathObject object) {
		if (object instanceof Document) 
			return super.isSimilarTo(object);
		else
			return false;
	}
}
