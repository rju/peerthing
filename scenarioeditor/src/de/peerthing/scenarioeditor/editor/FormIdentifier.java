package de.peerthing.scenarioeditor.editor;

/**
 * Class that is used to associate a class in the data model with a form that
 * should be shown in the editor. It can be used in a hashtable instead of Class
 * objects. The advantage is that also a name of the object can be given
 * additional to the class object.
 *
 * @author Michael Gottschalk
 *
 */
public class FormIdentifier {
    
    /**
     * The object class of a class
     */
	private Class objectClass;

    /**
     * The name of the identifier
     */
	private String name;

    /**
     * Default Constructor of class FormIdentifier, passing an object of type class and a name
     * @param objectClass
     * @param name
     */
	public FormIdentifier(Class objectClass, String name) {
		this.objectClass = objectClass;
		this.name = name;
	}

    /**
     * Default Constructor of class FormIdentifier, just passing an object of type class 
     * @param objectClass
     */
	public FormIdentifier(Class objectClass) {
		this.objectClass = objectClass;
		this.name = null;
	}

    /**
     * This method tests ,if just object from type class exist, or an object from type class inclusive name
     */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FormIdentifier) {
			if (name == null) {
				return ((FormIdentifier) obj).objectClass == objectClass;
			} else {
				return (((FormIdentifier) obj).objectClass == objectClass && name
						.equals(((FormIdentifier) obj).name));
			}
		}
		return false;
	}

    /**
     * This method returns a hashcode
     */
	@Override
	public int hashCode() {
		if (name == null) {
			return objectClass.hashCode();
		} else {
			return objectClass.hashCode() + name.hashCode();
		}
	}

}
