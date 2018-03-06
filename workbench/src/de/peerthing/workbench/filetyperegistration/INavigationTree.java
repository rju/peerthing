package de.peerthing.workbench.filetyperegistration;


public interface INavigationTree {

	/**
	 * Refreshes the tree viewer with new information from the model
	 * beginning at the given object downwards (including children).
	 *
	 * @param obj The object to refresh
	 */
	public void refresh(Object elem);

	/**
	 * Updates the tree viewer with new information from the model. Only
	 * the given element is updated (no children).
	 *
	 * @param obj The object to update
	 */
	public void update(Object elem);

	/**
	 * Collapses the subtree located at the given element to the given level
	 *
	 * @param elem The element that is the parent of the subtree
	 * @param level The level to which the subtree is to be collapsed
	 */
	public void collapseToLevel(Object elem, int level);

	/**
	 * Expands the tree so that the given element comes into view and
	 * expands the subtree located at the given element to the given level.
	 *
	 */
	public void expandToLevel(Object elem, int level);

    /**
     * Selects the given object in the tree.
     * 
     * @param elem
     */
    public void select(Object elem);

}
