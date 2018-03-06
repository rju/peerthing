/*******************************************************************************
 * Copyright (c) 2004 Boris Bokowski, Frank Gerhardt All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/org/documents/epl-v10.html
 * 
 * Contributors: Boris Bokowski (bokowski@acm.org) Frank Gerhardt
 * (fg@frankgerhardt.com)
 ******************************************************************************/

package de.peerthing.systembehavioureditor.gefeditor.editparts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.RoundedRectangle;
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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.IPropertySource;

import de.peerthing.systembehavioureditor.PeerThingSystemBehaviourEditorPlugin;
import de.peerthing.systembehavioureditor.gefeditor.commands.StateDeleteCommand;
import de.peerthing.systembehavioureditor.gefeditor.commands.TransitionCreateCommand;
import de.peerthing.systembehavioureditor.gefeditor.commands.TransitionReconnectCommand;
import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.editor.Node;
import de.peerthing.systembehavioureditor.model.editor.State;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Transition;

/**
 * This class is responsible for drawing a State. Here we create the figure and 
 * define what can be done with the figure.
 * 
 * @author Petra Beenken
 * @author Peter Schwenkenberg
 * @review Michael, 2006-03-23
 * 
 */
public class StateEditPart extends AbstractGraphicalEditPart implements
        PropertyChangeListener, NodeEditPart {

    private ChopboxAnchor anchor;
    public Figure rr = new Figure();
    
    /**
     * Draws a State initially. Changes on the presentation during runtime must be specified
     * in refreshFigure().
     */
    protected IFigure createFigure() {
        rr = new RoundedRectangle();
        rr.setOpaque(true);
        rr.setBackgroundColor(new Color(Display.getCurrent(), 255, 252, 230));
       
        Label label = new Label();
        label.setOpaque(false);
        label.setBackgroundColor(ColorConstants.white);
        label.setForegroundColor(ColorConstants.black);
        label.setFont(new Font(Display.getCurrent(), "Arial", 12, 0));
        label.setFocusTraversable(false);
        rr.add(label);
        MarginBorder mb = new MarginBorder(3);
        label.setBorder(mb);
        rr.setForegroundColor(ColorConstants.darkBlue); // actually this is the
                                                        // color of the border
        return rr;
    }

    protected void refreshVisuals() {

        // Get a reference to the figure created once in createFigure().
        Figure rr = (RoundedRectangle) getFigure();

        Label label = (Label) rr.getChildren().get(0); // access to the label
        // Include the state's task (for testing purposes. Catch: Initially, a task might not exitst.
        try {
        	 label.setText(getCastedModel().getName());
        }

        // The old (and intended) way.
        catch (Exception e) {label.setText(getCastedModel().getName());};
        
        try {
        	
        	if (((Node) (this.getCastedModel().getTask().getNode())).getColor() == null) {
	        	// for each node another color :-)
	        	INodeType statesNode = getCastedModel().getTask().getNode();
	        	int indexOfNode = getCastedModel().getTask().getNode().getArchitecture().getNodes().indexOf(statesNode);
	        	// display the states of the n-th node (n x constant)-color-values darker
	        	Color newColor = new Color(Display.getCurrent(), java.lang.Math.abs((255-indexOfNode*60)%256), java.lang.Math.abs((252-indexOfNode*50)%256), java.lang.Math.abs((230-indexOfNode*1)%256));
	        	((Node) (statesNode)).setColor(newColor);
        	}
        	rr.setBackgroundColor( ((Node) (this.getCastedModel().getTask().getNode())).getColor());
        	
        }
        catch(Exception e) {
        	System.out.println("Error in refreshVisuals()" );
        	e.printStackTrace();
        } 
        
        // Display an end-state differently
    	if (getCastedModel().isEndState()){
    		
    		Label l = new Label();
    		ImageDescriptor id = PeerThingSystemBehaviourEditorPlugin.getDefault().getIcon(
            "endState.png");
    		Image img = id.createImage();
    		l.setIcon(img);
    		l.setOpaque(true);
    		l.setText("");
    		rr.add(l);
    		rr.setOpaque(false);
    		rr.setBackgroundColor(new Color(Display.getCurrent(), 242, 248, 255));
    		
    		Dimension size = l.getPreferredSize();
            Rectangle bounds = new Rectangle(new Point(getCastedModel().getX(),
                    getCastedModel().getY()), size);
            l.setSize(size);
            ((GraphicalEditPart) getParent()).setLayoutConstraint(this, figure,
                    bounds);
    	}
    	
    	else {
	        Dimension size = label.getPreferredSize();
	        Rectangle bounds = new Rectangle(new Point(getCastedModel().getX(),
	                getCastedModel().getY()), size);
	        label.setSize(size);
	        ((GraphicalEditPart) getParent()).setLayoutConstraint(this, figure,
	                bounds);
    	}
    }

    protected void createEditPolicies() {


        installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentEditPolicy() {
            protected Command createDeleteCommand(
                    final GroupRequest deleteRequest) {
                return new StateDeleteCommand(getCastedModel(),
                        (SystemBehaviour) getParent().getModel());
            }
        });
        // allow the creation of connections and
        // the reconnection of connections between VisualElement instances
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
                new GraphicalNodeEditPolicy() {

                    protected Command getConnectionCompleteCommand(
                            CreateConnectionRequest request) {
                        TransitionCreateCommand cmd = (TransitionCreateCommand) request
                                .getStartCommand();
                        cmd.setTarget((State) getHost().getModel());
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

    /**
     * Work in progress for direct cell editing support.
     */

    public void performRequest(Request request) {
        /*
         * if (request.getType() == RequestConstants.REQ_DIRECT_EDIT) {
         * StateLabelEditManager manager; if (manager == null) { manager = new
         * StateLabelEditManager(this, TextCellEditor.class, new
         * LabelCellEditorLocator((Label)getFigure())); manager.show(); } }
         */
    }

    public void activate() {
        super.activate();
        getCastedModel().addPropertyChangeListener(this);
    }

    public void deactivate() {
        getCastedModel().removePropertyChangeListener(this);
        super.deactivate();
    }

    private State getCastedModel() {
        return (State) getModel();
    }

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
        return getCastedModel().getTransitions();
    }

    protected List getModelTargetConnections() {
        return getCastedModel().getTransitionsIncoming();
    }

    /**
     * Get the old connection anchor or create a new one.
     * Without it, transitions don't connect.
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
