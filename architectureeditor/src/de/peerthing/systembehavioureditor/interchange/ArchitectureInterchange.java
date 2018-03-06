package de.peerthing.systembehavioureditor.interchange;

import de.peerthing.systembehavioureditor.model.ISystemBehaviour;
import de.peerthing.systembehavioureditor.model.SystemBehaviourModelFactory;
import de.peerthing.systembehavioureditor.persistence.SystemBehaviourXMLAdapter;

/**
 * With this class, an architecture file can be loaded and given back in the
 * SystemBehaviour Interchange format.
 * 
 * @author Michael Gottschalk
 * 
 */
public class ArchitectureInterchange {
    public static IAIArchitecture loadArchitecture(String filename) {
        SystemBehaviourModelFactory fac = new SystemBehaviourModelFactory();
        SystemBehaviourXMLAdapter xml = new SystemBehaviourXMLAdapter(fac);

        ISystemBehaviour sb = null;
        try {
            sb = xml.loadArchitecture(filename);
        } catch (Exception e) {
            throw new InvalidArchitectureFileException(
                    "Invalid system behaviour file", e);
        }
        if (sb == null) {
            throw new InvalidArchitectureFileException(
                    "Invalid system behaviour file");
        }

        AIArchitecture arch = new AIArchitecture(sb);
        return arch;
    }
}
