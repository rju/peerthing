package de.peerthing.systembehavioureditor.gefeditor.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import de.peerthing.systembehavioureditor.model.editor.State;

/**
 * This class manages commands of changing the constraint (the position) of a State
 * including undo and redo commands.
 * 
 * @author Peter Schwenkenberg
 * @review Michael, 2006-03-23
 * 
 */
public class StateChangeConstraintCommand extends Command {

    private final State state;
    private final Rectangle constraint;
    int oldX;
    int oldY;

    public StateChangeConstraintCommand(State state, Rectangle constraint) {
        super("Change Position");
        this.state = state;
        this.constraint = constraint;
    }

    public void execute() {
        oldX = state.getX();
        oldY = state.getY();
        redo();
    }

    public void redo() {
        state.setX(constraint.x);
        state.setY(constraint.y);
    }

    public void undo() {
        state.setX(oldX);
        state.setY(oldY);
    }
}