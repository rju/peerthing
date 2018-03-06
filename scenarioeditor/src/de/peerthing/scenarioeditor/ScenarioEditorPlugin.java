package de.peerthing.scenarioeditor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.BundleContext;


import de.peerthing.scenarioeditor.editor.ScenarioEditor;
import de.peerthing.scenarioeditor.editor.ScenarioFiletypeRegistration;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.impl.ListOfUndos;
import de.peerthing.scenarioeditor.model.impl.ScenarioUndo;
import de.peerthing.workbench.filetyperegistration.INavigationTree;

/**
 * The main plugin class to be used in the desktop.
 */
public class ScenarioEditorPlugin extends AbstractUIPlugin {
    
    /**
     * The current navigation tree
     */
	private INavigationTree tree;
    
    /**
     * The filetype registration
     */
	private ScenarioFiletypeRegistration filetypeReg;
    
    /**
     * The scenario editor plugin
     */
	private static ScenarioEditorPlugin plugin;
	
    /**
     * List of undoable operations
     */
	private List<ScenarioUndo> undoList = new ListOfUndos<ScenarioUndo>();
	
    /**
     * List of redoable operations
     */
	private List<ScenarioUndo> redoList = new ArrayList<ScenarioUndo>();
	
    /**
     * The current scenario editor
     */
	private ScenarioEditor editor;
	
    /**
     * Possibilty, if redo is possible
     */
	private boolean redoPossible;
	
	/**
     * The current scenario object
	 */
	private IScenarioObject scenarioObject;
    
    /**
     * Is object copied?
     */
	private boolean copy;
	
    /**
     * Icons that can be used in this plug-in.
     */
    private Hashtable<String, Image> icons = 
        new Hashtable<String, Image>();
    


	
	/**
	 * The constructor.
	 */
	public ScenarioEditorPlugin() {
		plugin = this;
	}

    /**
     * This method returns if object is copied or not
     * @return
     */
    public boolean isCopy(){
        return copy;
    }
    
    /**
     * This method returns the current scenario object
     * @return
     */
    public IScenarioObject getScenarioObject(){
        return this.scenarioObject;
    }
    
    /**
     * This method sets the current scenario object
     * @param object
     * @param copy
     */
    public void setScenarioObject(IScenarioObject object,
            boolean copy){
        this.scenarioObject = object;
        this.copy = copy;
        
    }
    
    /**
     * This method returns the current editor
     * @return
     */
    public ScenarioEditor getEditor(){
        return editor;
    }
    
    /**
     * This method sets the current editor
     * @param editor
     */
    public void setEditor(ScenarioEditor editor){
        this.editor = editor;
        
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
	public static ScenarioEditorPlugin getDefault() {
		return plugin;
	}
    
	/**
     * This method sets the current navigation tree
     * @param tree
	 */
	public void setNavigationTree(INavigationTree tree) {
		this.tree = tree;		
	}
	
    /**
     * This method returns the current navigation tree
     * @return
     */
	public INavigationTree getNavigationTree() {
		return tree;
	}
	
    /**
     * This method returns the filetype registration
     * @return
     */
	public ScenarioFiletypeRegistration getFiletypeRegistration() {
		return filetypeReg;
	}

    /**
     * This method sets the filetype registration
     * @param filetypeReg
     */
	public void setFiletypeRegistration(ScenarioFiletypeRegistration filetypeReg) {
		this.filetypeReg = filetypeReg;
	}
    
    /**
     * This method returns a list of the undoable operations
     * @return
     */
    public List <ScenarioUndo>getUndoList(){
        return undoList;
    }
    
    /**
     * This method returns a list of the redoable operations
     * @return
     */
    public List <ScenarioUndo>getRedoList(){
        return redoList;
    }

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin("de.peerthing.scenarioeditor", path);
	}
	
    /**
     * Returns the icon with the given filename
     * that must be located in the icons/ directory
     * of this plug-in. The icons are cached internally
     * in a hashtable and are disposed upon shutdown
     * of the plug-in.
     * 
     * @param name
     * @return
     */
    public Image getIcon(String name) {
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
     

     /**
      * This method returns, if redo is possible
      * @return
      */
	public boolean isRedoPossible() {
		return redoPossible;
	}

    /**
     * This method declares an object redoable
     * @param redoPosible
     */
	public void setRedoPosible(boolean redoPosible) {
		this.redoPossible = redoPosible;
	}
}
