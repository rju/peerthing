package de.peerthing.systembehavioureditor.model;

import java.util.List;

/**
 * This interface is intended as a super-interface of ITransition,
 * ICaseArchitecture and IState, since these can include TransitionContent objects.
 * 
 * @author Michael Gottschalk
 * @review Boris, 2006-03-27
 */
public interface IContentContainer extends ISystemBehaviourObject {
    /**
     * Returns the commands that belong to this container. This is
     * a sequential list of actions and conditions. If this container
     * is a state, then the commands are the initialize actions.
     * 
     * @return
     */
    public List<ITransitionContent> getContents();
    
    /**
     * Sets the commands that belong to this container. This is a
     * sequential list of actions and conditions.  If this container
     * is a state, then the commands are the initialize actions.
     * 
     * @param content
     */
    public void setContents(List<ITransitionContent> content);
}
