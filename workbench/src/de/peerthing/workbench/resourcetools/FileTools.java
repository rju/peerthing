/*
 * Created on 09.07.2006
 *
 */
package de.peerthing.workbench.resourcetools;

/**
 * This class includes helper functions for handling files.
 * 
 * @author Michael Gottschalk
 * 
 */
public class FileTools {
    /**
     * This function tests if a String includes only whitespace. Is useful for
     * testing if a file name is correct.
     * 
     * @return true if the given String includes only whitespace
     */
    public static boolean includesOnlyWhitespace(String str) {
        for (char chr : str.toCharArray()) {
            if (chr != ' ') {
                return false;
            }
        }
        return true;
    }

    /**
     * In filenames, only ascii-Characters are allowed since there can be
     * problem with other characters. This method returns if there are only
     * ascii-characters in the string or not.
     */
    public static boolean includesInvalidCharacters(String str) {
        for (char ch : str.toCharArray()) {
            if (ch < 32 || ch > 127) {
                return true;
            }
        }
        return false;
    }

}
