package de.peerthing.systembehavioureditor.gefeditor.editparts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;

import de.peerthing.systembehavioureditor.PeerThingSystemBehaviourEditorPlugin;
import de.peerthing.systembehavioureditor.gefeditor.commands.TaskDeleteCommand;
import de.peerthing.systembehavioureditor.gefeditor.commands.TransitionCreateCommand;
import de.peerthing.systembehavioureditor.gefeditor.commands.TransitionReconnectCommand;
import de.peerthing.systembehavioureditor.model.editor.State;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Task;
import de.peerthing.systembehavioureditor.model.editor.Transition;

/**
 * This class is responsible for drawing a Task. Here we create the figure and 
 * define what can be done with the figure.
 * 
 * @author Petra Beenken
 * @author Peter Schwenkenberg
 * @review Michael, 2006-03-23
 */
public class TaskEditPart extends AbstractGraphicalEditPart implements
        PropertyChangeListener, NodeEditPart {

    /**
     * This variable stores the anchor of the target object. It's type causes that the end
     * of a connection is attached in a nice way (see class discription) to the target.
     */
    private ChopboxAnchor anchor;
    

    /**
     * Creates the initial figure
     * 
     * @return IFigure the figure (a Label)
     */
    protected IFigure createFigure() {
    	
        Label label = new Label();
        label.setOpaque(true);
        label.setForegroundColor(ColorConstants.black);
        label.setBorder(new MarginBorder(0));
        return label;
    }

    /**
     * 	Refreshes the visualization of a task after the name of the task, it's position
     * 	a.s.f. has been changed. 
     */
    protected void refreshVisuals() {
  
        ImageDescriptor id;
        
        Label label = (Label) getFigure();
        
        // If this task is the start task (often called "main task"), dislay it differently, 
        // because this task is implicit.
	    try {
	        if (getCastedModel().getNode().getStartTask().equals(getCastedModel())) {
	        	id = PeerThingSystemBehaviourEditorPlugin.getDefault().getIcon(
	            "bullet.png");
	        	label.setTextPlacement(PositionConstants.NONE);
	        }
	        
	        else {
	        	id = PeerThingSystemBehaviourEditorPlugin.getDefault().getIcon(
	            "task.png");
	        	label.setTextPlacement(PositionConstants.SOUTH);
	        }
        }
        catch (Exception e) {id = PeerThingSystemBehaviourEditorPlugin.getDefault().getIcon("task.png"); };
        Image img = id.createImage();
        
        if (getCastedModel().getTransitionsIncoming().size() == 0) {
        	label.setIcon(img);
        }
        else {
        	label.setIcon(null);
        }
        
        Dimension size = new Dimension(16,16);
        
        if (!(getCastedModel().getNode().getStartTask().equals(getCastedModel()))) {
        	
        	label.setText(getCastedModel().getName());
        	size = label.getPreferredSize();
        }
         
        Rectangle bounds = new Rectangle(new Point(getCastedModel().getX(),
                getCastedModel().getY()), size);
        /*
         * Update the layout constraint for the figure, which is maintained by
         * the parent editpart's layout manager.
         */
        ((GraphicalEditPart) getParent()).setLayoutConstraint(this, figure,
                bounds);
    }

    protected void createEditPolicies() {

        installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentEditPolicy() {        	
        	protected Command createDeleteCommand(
        			final GroupRequest deleteRequest) {
                return new TaskDeleteCommand(getCastedModel(),
                        (SystemBehaviour) getParent().getModel());
            }
        });
        
        /** Allow the creation of connections and
         *  the reconnection of connections between VisualElement instances
         */
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
                new GraphicalNodeEditPolicy() {

                    protected Command getConnectionCompleteCommand(
                            CreateConnectionRequest request) {
                        TransitionCreateCommand cmd = (TransitionCreateCommand) request
                                .getStartCommand();
                        cmd.setTarget((Task) getHost().getModel());
                        return cmd;
                    }

                    protected Command getConnectionCreateCommand(
                            CreateConnectionRequest request) {
                        request.setSourceEditPart(getHost());
                        TransitionCreateCommand cmd = new TransitionCreateCommand(
                                ((State) getHost().getModel()), null);
                        request.setStartCommand(cmd);
                        return cmd;
                    }

                    protected Command getReconnectSourceCommand(
                            ReconnectRequest request) {
                        return new TransitionReconnectCommand(
                                (Transition) request.getConnectionEditPart()
                                        .getModel(), (State) getHost()
                                        .getModel(), null);
                    }

                    protected Command getReconnectTargetCommand(
                            ReconnectRequest request) {
                        return new TransitionReconnectCommand(
                                (Transition) request.getConnectionEditPart()
                                        .getModel(), null, (State) getHost()
                                        .getModel());
                    }
                });
    }

    public void activate() {
    	
        super.activate();
        getCastedModel().addPropertyChangeListener(this);
    }

    public void deactivate() {

        getCastedModel().removePropertyChangeListener(this);
        super.deactivate();
    }

    private Task getCastedModel() {
        return (Task) getModel();
    }

    /**
     * Handels the changes of the model (property changes).
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("transitionsOutgoing".equals(evt.getPropertyName())) {
            refreshSourceConnections();
        } else if ("transitionsIncoming".equals(evt.getPropertyName())) {
            refreshTargetConnections();
        } else {
            refreshVisuals();
        }
    }

    public Object getAdapter(Class key) {
        if (key == IPropertySource.class) {
            return getCastedModel();
        }
        return super.getAdapter(key);
    }

    protected List getModelSourceConnections() {
        return getCastedModel().getTransitionsOutgoing();
    }

    protected List getModelTargetConnections() {
        return getCastedModel().getTransitionsIncoming();
    }

    /**
     * Get the old connection anchor or create a new one.
     * Without it, transitions don't connect.
     * @return the anchor of a connection
     */
    private ConnectionAnchor getAnchor() {
        if (anchor == null) {
            anchor = new ChopboxAnchor(getFigure());
        }
        return anchor;
    }

    public ConnectionAnchor getSourceConnectionAnchor(
            ConnectionEditPart connection) {
        return getAnchor();
    }

    public ConnectionAnchor getTargetConnectionAnchor(
            ConnectionEditPart connection) {
        return getAnchor();
    }

    public ConnectionAnchor getSourceConnectionAnchor(Request request) {
        return getAnchor();
    }

    public ConnectionAnchor getTargetConnectionAnchor(Request request) {
        return getAnchor();
    }
}
