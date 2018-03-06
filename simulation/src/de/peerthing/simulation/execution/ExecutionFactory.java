/**
 * 
 */
package de.peerthing.simulation.execution;

import de.peerthing.simulation.interfaces.ILogger;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;

/**
 * @author prefec2
 * 
 */
public class ExecutionFactory {
	/**
	 * Creates a logger that can be used to log the data of a simulation run.
	 * The logger is created from a class provided by a different plug-in. The
	 * information is read from the extension point registry.
	 * 
	 * @param dbFile
	 * @return
	 */
	public static ILogger createLogger(IFile dbFile, String logType) {
		IExtension[] extensions = Platform.getExtensionRegistry()
				.getExtensionPoint("de.peerthing.simulation.logging")
				.getExtensions();

		ILogger logger = null;

		if (extensions.length > 0) {
			for (IExtension extension : extensions) {
				for (IConfigurationElement conf : extension
						.getConfigurationElements()) {
					if (conf.getAttribute("name").equals(logType)) {
						System.out.println("use logger: " + logType);
						try {
							logger = (ILogger) conf
									.createExecutableExtension("class");
							logger.startLog(dbFile);
						} catch (CoreException e) {
							System.out.println("Could not load logger: ");
							e.printStackTrace();
						}
					}
				}
			}

		}

		return logger;
	}
}
