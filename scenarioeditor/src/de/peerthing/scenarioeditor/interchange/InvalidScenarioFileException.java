/*
 * Created on 09.07.2006
 *
 */
package de.peerthing.scenarioeditor.interchange;

/**
 * This exception is thrown if a file that should be loaded
 * is not a correct scenario file.
 * 
 *
 * @author Michael Gottschalk
 *
 */
public class InvalidScenarioFileException extends RuntimeException {
    private static final long serialVersionUID = 1480098018418843794L;

    public InvalidScenarioFileException(String msg, Exception cause) {
        super(msg, cause);
    }
    
    public InvalidScenarioFileException(String msg) {
        super(msg);
    }

}
