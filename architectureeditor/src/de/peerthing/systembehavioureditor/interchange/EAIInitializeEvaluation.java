/**
 * 
 */
package de.peerthing.systembehavioureditor.interchange;

/**
 * Defines the different types of treating the initialize actions
 * of this state: "once" means that the initialize actions are executed
 * only if the state is entered from another state; "each" means that 
 * the initialize actions are executed after self loops, too. 
 *
 * @author prefec2
 *
 */
public enum EAIInitializeEvaluation {
	once, each
}

