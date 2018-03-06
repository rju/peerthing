package de.peerthing.systembehavioureditor.model.editor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import org.eclipse.gef.EditPart;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * This class provides simple notification management.
 * 
 * @review Sebastian Rohjans 27.03.2006
 * @author Peter Schwenkenberg, Petra Beenken 
 */
abstract public class ModelObject implements Serializable, IPropertySource {

    // Helper object for change notification. This will not be persisted.
    private transient PropertyChangeSupport propertyChangeSupport;

    private PropertyChangeSupport getPropertyChangeSupport() {
        if (propertyChangeSupport == null) {
            propertyChangeSupport = new PropertyChangeSupport(this);
        }
        return propertyChangeSupport;
    }
    
    private EditPart editPart; 

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        getPropertyChangeSupport().addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        getPropertyChangeSupport().removePropertyChangeListener(listener);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        getPropertyChangeSupport().firePropertyChange(propertyName, oldValue, newValue);
    }

    protected void firePropertyChange(String propertyName, int oldValue, int newValue) {
    	
        getPropertyChangeSupport().firePropertyChange(propertyName, oldValue, newValue);
    }

    public Object getEditableValue() {
        return "";
    }

    abstract public IPropertyDescriptor[] getPropertyDescriptors();

    abstract public Object getPropertyValue(Object id);

    public boolean isPropertySet(Object id) {
        return true;
    }

    public void resetPropertyValue(Object id) {
    }

    abstract public void setPropertyValue(Object id, Object value);
    
    public void setEditPart(EditPart ep) {
    	editPart = ep;
    }
    
    public EditPart getEditPart() {
    	return editPart;
    }
}