package de.peerthing.workbench.filetyperegistration;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

/**
 * Interface that the main PeerThing components must define in order
 * to be recognized by the PeerThing workbench.
 *
 * @author Michael Gottschalk
 */
public interface IFileTypeRegistration {
    /**
     * Returns a list of filename endings that this component
     * can process. Example: new String[] { "txt", "log" }
     *
     * @return the list
     */
    public String[] getFileNameEndings();
    
    /**
     * Returns a definition a file type that a user should
     * be able to create. These files can then be created over 
     * a context menu of folders in the resource view. Files
     * are created with no content.
     * 
     * @return A String array with two elements: 1. Description of
     *         the file type; 2. The ending of the file name (e.g.
     *         "arch").
     */
    public String[] getNewFileDefinition();

    /**
     * This component should return true here if it wants to be
     * the default editor for the registered file types. This means
     * that double clicks on files with the registered names
     * will be handled by this component. If more than one component
     * wants to be the default editor for a file type, then the
     * default editor is chosen at random.
     *
     * @return true, if the component wants to be default editor.
     */
    public boolean wantsToBeDefaultEditor();

    /**
     * Returns the name of the component that is responsible for
     * the file types, e.g. "Scenario Editor"
     *
     * @return
     */
    public String getComponentName();

    /**
     * This method is called when one or more files with the appropriate
     * endings are double clicked by the user. Here, a certain perspective
     * can be shown and an editor or view can be opened.
     *
     * @param inputFiles The files that were double clicked with the input for
     *                     the component
     */
    public void showComponent(IFile[] inputFiles);

    /**
     * This method is called when the user wants to see the tree
     * structure of a file. This should return the elements that belong
     * to that file.
     *
     * @param file The file for which the subtree should be provided
     * @return The model objects that belong to the subtree
     */
    public Object[] getTreeElements(IFile file);

    /**
     * Returns the content provider that can handle elements of
     * a subtree for the given file.
     *
     * @return The content provider to use
     */
    public ITreeContentProvider getSubtreeContentProvider();

    /**
     * Returns the label provider that can generate labels of
     * a subtree for the given file.
     *
     * @return The label provider to use
     */
    public ILabelProvider getSubtreeLabelProvider();

    /**
     * This method is called whenever the navigation view finds an
     * element in the tree that it can't handle itself. Then all
     * registered components are asked if they can handle this object.
     * If they can, then the content provider and label provider of this
     * component are used. The test can easily be implemented with instanceof
     * if all subtree elements of a component implement a common interface.
     *
     * @param obj The object which should be tested
     *
     * @return True, if the component can handle this object
     */
    public boolean canHandleSubtreeObject(Object obj);

    /**
     * This method should return whether the given file has subtree-Elements
     * or not.
     *
     * @param file
     * @return
     */
    public boolean hasSubTree(IFile file);

    /**
     * Gives the control of the navigation tree viewer to the component.
     * This method is called only once when the navigation tree viewer
     * is created.
     *
     * @param navigationTree
     */
    public void setNavigationTree(INavigationTree navigationTree);

    /**
     * This method is called when the user clicks on a subtree element
     * of a file belonging to this component.
     *
     * @param subTreeElement The element that was selected
     */
    public void subTreeElementSelected(Object subTreeElement);

    /**
     * This method is called when the user double-clicks on a subtree element
     * of a file belonging to this component.
     *
     * @param subTreeElement
     */
    public void subTreeElementDoubleClicked(Object subTreeElement);

}
