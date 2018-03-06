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

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import de.peerthing.systembehavioureditor.model.editor.State;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Task;
import de.peerthing.systembehavioureditor.model.editor.Transition;

/**
 * This class creates appropriate Editparts for model interactions.
 * 
 * @author Peter Schwenkenberg
 */
public class MyEditPartFactory implements EditPartFactory {

    public EditPart createEditPart(EditPart context, Object model) {

        EditPart result = null;
        if (model instanceof SystemBehaviour) {
            result = new SystemBehaviourEditPart();
        	((SystemBehaviour)model).setEditPart(result);
    	}
        else if (model instanceof Task) {
            result = new TaskEditPart();
            ((Task)model).setEditPart(result);
        }
        else if (model instanceof State) {
            result = new StateEditPart();
            ((State)model).setEditPart(result);
        }
        else if (model instanceof Transition) {
            result = new TransitionEditPart();
            ((Transition)model).setEditPart(result);
        }

        if (result != null) {
            result.setModel(model);
        }
        return result;
    }
}