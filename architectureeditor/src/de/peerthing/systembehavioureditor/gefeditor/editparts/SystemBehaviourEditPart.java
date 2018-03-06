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
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import de.peerthing.systembehavioureditor.gefeditor.commands.CreateNodeCommand;
import de.peerthing.systembehavioureditor.gefeditor.commands.CreateStateCommand;
import de.peerthing.systembehavioureditor.gefeditor.commands.CreateTaskCommand;
import de.peerthing.systembehavioureditor.gefeditor.commands.StateChangeConstraintCommand;
import de.peerthing.systembehavioureditor.gefeditor.commands.TaskChangeConstraintCommand;
import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.editor.Node;
import de.peerthing.systembehavioureditor.model.editor.State;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Task;

/**
 * This is the Editpart of the root model object. It manages the editor's canvas and the
 * collaboration with other model objects (model-children) by creating commands from requests.
 * 
 * @author Petra Beenken
 * @author Peter Schwenkenberg
 * @review Michael, 2006-03-23
 */

public class SystemBehaviourEditPart extends AbstractGraphicalEditPart implements
        EditPart, PropertyChangeListener {

    public void activate() {
        super.activate();
        getCastedModel().addPropertyChangeListener(this);
    }

    public void deactivate() {
        getCastedModel().removePropertyChangeListener(this);
        super.deactivate();
    }

    /**
     * Creates the base-figure (canvas) of the editor. Sets
     * the layout manager.
     * 
     */
    protected IFigure createFigure() {
    	
        Figure f = new Figure();
        f.setOpaque(true);
        f.setBackgroundColor(new Color(Display.getCurrent(), 242, 248, 255)); // soft baby blue
        f.setLayoutManager(new XYLayout());
        
        return f;
    }

    protected void createEditPolicies() {
        // disallows the removal of this edit part from its parent
        installEditPolicy(EditPolicy.COMPONENT_ROLE,
                new RootComponentEditPolicy());
        // handles constraint changes (e.g. moving and/or resizing) of model
        // elements and creation of new model elements
        installEditPolicy(EditPolicy.LAYOUT_ROLE, new MyLayoutPolicy(
                (XYLayout) getFigure().getLayoutManager()));
        // disable selection feedback for this edit part
        installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, null);
    }

    /**
     * Manages the layout policy.
     * 
     */
    private class MyLayoutPolicy extends XYLayoutEditPolicy {
        MyLayoutPolicy(XYLayout layout) {
            super();
            setXyLayout(layout);
        }

        protected Command createAddCommand(EditPart child, Object constraint) {
            return null;
        }

        protected Command createChangeConstraintCommand(final EditPart child,
                final Object constraint) {

            /*
             * IMPORTANT: Since tasks are extended from states, in such
             * type-checks allways check tasks first and then do break!
             * 
             */
            if (child.getModel() instanceof Task) {
                return new TaskChangeConstraintCommand((Task) child.getModel(),
                        (Rectangle) constraint);
            }

            if (child.getModel() instanceof State) {
                return new StateChangeConstraintCommand((State) child
                        .getModel(), (Rectangle) constraint);
            }

            return null;
        }

        protected Command getCreateCommand(final CreateRequest request) {

            if (request.getNewObject() instanceof Task) {

                return new CreateTaskCommand(getCastedModel(), (Task) request
                        .getNewObject(), request.getLocation().x, request
                        .getLocation().y);
            }

            else if (request.getNewObject() instanceof State) {

                return new CreateStateCommand(getCastedModel(), (State) request
                        .getNewObject(), request.getLocation().x, request
                        .getLocation().y);
            }
            
            else if (request.getNewObject() instanceof Node) {
            	
            	return new CreateNodeCommand(getCastedModel(), (Node) request
            			.getNewObject(), request.getLocation().x, request.getLocation().y );
            }

            return null;
        }

        protected Command getDeleteDependantCommand(Request request) {
            return null;
        }
    }

    protected List getModelChildren() {

       /* Attach states and task to the set of model children,
    	* since they are drawn on the canvas directly.
    	*/
        Vector<Object> result = new Vector<Object>();
        try {

            for (INodeType n : getCastedModel().getNodes()) {
                for (ITask t : n.getTasks()) {
                    result.add((ITask) t); // tasks and...
                    for (IState s : t.getStates()) {
                        result.add((State) s); // ...states are the model
                        // children of the root model
                    }
                }
            }
            for (Object s : getCastedModel().getStates()) {
                result.add((State) s);
            }
        } catch (Exception e) {
            System.out.println("" + e);
        }

        if (result != null)
            return result;
        else
            return Collections.EMPTY_LIST;

    }

    /*
     * Get architecture objects by casting the architecture edit parts.
     */
    private SystemBehaviour getCastedModel() {
        return (SystemBehaviour) getModel();
    }

    /**
     * Notify model children when placing new model children. Without it, new states or tasks
     * cannot be set on the canvas.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        refreshChildren();
    }
}