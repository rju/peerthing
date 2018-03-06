package de.peerthing.systembehavioureditor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.BundleContext;


import de.peerthing.systembehavioureditor.propertyeditor.PropertyEditor;
import de.peerthing.systembehavioureditor.propertyeditor.plugindatahandler.PlugInDataHandler;
import de.peerthing.workbench.filetyperegistration.INavigationTree;

/**
 * The main plugin class to be used in the desktop.
 */
public class PeerThingSystemBehaviourEditorPlugin extends AbstractUIPlugin {
    INavigationTree navigationTree;
    
	//The shared instance.
	private static PeerThingSystemBehaviourEditorPlugin plugin;
	
	private SystemBehaviourFiletypeRegistration filetypeRegistration;
	
	private INavigationTree tree;
	
	private PlugInDataHandler plugInDataHandler = new PlugInDataHandler();
    
    /**
     * Icons that can be used in this plug-in.
     */
    private Hashtable<String, Image> icons = 
        new Hashtable<String, Image>();
    

	private Object copyObject;

	private PropertyEditor propertyEditor;
	/**
	 * The constructor.
	 */
	public PeerThingSystemBehaviourEditorPlugin() {
		plugin = this;
	}


	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
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
	public static PeerThingSystemBehaviourEditorPlugin getDefault() {
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
		return AbstractUIPlugin.imageDescriptorFromPlugin("de.peerthing.systembehavioureditor", path);
	}
    
    public ImageDescriptor getIcon(String name) {
        String iconPath = "icons/";
        URL pluginUrl = getBundle().getEntry("/");
        try {
            return ImageDescriptor.createFromURL(new URL(pluginUrl, iconPath + name));
        } catch (MalformedURLException e) {
            return ImageDescriptor.getMissingImageDescriptor();
        }
    }
        
        
        public Image getIconImage(String name) {
            String iconPath = "icons/";
            URL pluginUrl = getBundle().getEntry("/");
            ImageDescriptor desc = null;
            try {
                desc = ImageDescriptor.createFromURL(new URL(pluginUrl, iconPath
                        + name));
            } catch (MalformedURLException e) {
                desc = ImageDescriptor.getMissingImageDescriptor();
            }
            
            if (icons.containsKey(name)) {
                return icons.get(name);
            } else {
                Image img = desc.createImage();
                icons.put(name, img);
                return img;
            }
    }
   

    public void log(String message, Exception ex) {
        System.out.println("Error: "+ message + " "+ ex);
    }

	public INavigationTree getNavigationTree() {
		return tree;
	}

	public void setNavigationTree(INavigationTree tree) {
		this.tree = tree;
	}

	public SystemBehaviourFiletypeRegistration getFiletypeRegistration() {
		return filetypeRegistration;
	}

	public void setFiletypeRegistration(
			SystemBehaviourFiletypeRegistration filetypeRegistration) {
		this.filetypeRegistration = filetypeRegistration;
	}

	public PlugInDataHandler getPlugInDataHandler() {
		return plugInDataHandler;
	}

	public void setPlugInDataHandler(PlugInDataHandler plugInDataHandler) {
		this.plugInDataHandler = plugInDataHandler;
	}

	public void setCopy(Object chosen) {
		this.copyObject = chosen;
	}

	public Object getCopy() {
		return copyObject;
	}


	public void setPropertyEditor(PropertyEditor editor) {
		this.propertyEditor = editor;
		
	}
	public PropertyEditor getPropertyEditor() {
		return this.propertyEditor;
	}
}
