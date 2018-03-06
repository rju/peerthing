package de.peerthing.scenarioeditor.model.impl;

/**
* @author Patrik
*/

/**
 * This class saves static variables which encode the operation
 * that was done in the scenario editor, so this can be saved in
 * undo-objects. 
 */
public class UndoOperationValues {
		
	final public static byte addWasDone = 1;
	final public static byte deleteWasDone = 2;
	final public static byte valueChanged = 3;
	final public static byte wasMovedUp = 4;
	final public static byte wasMovedDown = 5;
}
