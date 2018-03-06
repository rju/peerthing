package de.peerthing.workbench.initialization;

import org.eclipse.ui.plugin.*;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class PeerThingWorkbenchPlugin extends AbstractUIPlugin {

	//The shared instance.
	private static PeerThingWorkbenchPlugin plugin;

	/**
	 * The constructor.
	 */
	public PeerThingWorkbenchPlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);

		// Update the workspace...
		ResourcesPlugin.getWorkspace().getRoot().refreshLocal(1, null);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static PeerThingWorkbenchPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin("de.peerthing.Workbench", path);
	}
}
