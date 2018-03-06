package de.peerthing.workbench.filetyperegistration;

/**
 * This interface should be implemented by
 * classes that are displayed in the resource
 * tree view and should be moved by the up/down-
 * buttons in the resource view.
 * 
 * @author Michael Gottschalk
 *
 */
public interface IUpDownMovable {
    /**
     * Moves the object up over
     * the preceding sibling. After
     * moving, the object must notify the
     * tree viewer of the change in the model.
     * 
     */
    public void moveUp();
    
    /**
     * Moves the object down below
     * the following sibling. After
     * moving, the object must notify the
     * tree viewer of the change in the model.
     * 
     */
    public void moveDown();
    
    /**
     * Returns true if the object can be moved up,
     * i.e. the object has a preceding sibling.
     * 
     * @return
     */
    public boolean canMoveUp();
    
    /**
     * Returns true if the object can be moved down,
     * i.e. the object has a following sibling.
     * 
     * @return
     */    
    public boolean canMoveDown();
}
