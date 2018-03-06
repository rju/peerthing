/*
 * Created on 09.07.2006
 *
 */
package de.peerthing.systembehavioureditor.interchange;

/**
 * This Exception is thrown if a file was loaded that does
 * not include a correct system behaviour description.
 * 
 *
 * @author Michael Gottschalk
 *
 */
public class InvalidArchitectureFileException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 7169690939111240856L;

    public InvalidArchitectureFileException(String msg, Exception cause) {
        super(msg, cause);
    }
    
    public InvalidArchitectureFileException(String msg) {
        super(msg);
    }

}
