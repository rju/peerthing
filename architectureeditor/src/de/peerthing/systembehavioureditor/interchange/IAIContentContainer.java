package de.peerthing.systembehavioureditor.interchange;

import java.util.List;

/**
 * This interface is intended as a super-interface of ITransition,
 * ICaseArchitecture and IState, since these can include TransitionContent objects.
 *
 * @author Michael Gottschalk
 */
public interface IAIContentContainer {
    /**
     * Returns the commands that belong to this container. This is
     * a sequential list of actions and conditions. If this container
     * is a state, then the commands are the initialize actions.
     *
     * @return
     */
    public List<IAITransitionContent> getContents();
}
