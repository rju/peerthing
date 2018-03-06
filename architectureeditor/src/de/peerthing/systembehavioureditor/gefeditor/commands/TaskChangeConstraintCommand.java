package de.peerthing.systembehavioureditor.gefeditor.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import de.peerthing.systembehavioureditor.model.editor.Task;

/**
 * This class manages commands of changing the constraint (the position) of a Task
 * including undo and redo commands..
 * 
 * @author Peter Schwenkenberg
 * @review Michael, 2006-03-23
 * 
 */
public class TaskChangeConstraintCommand extends Command {

    private final Task task;
    private final Rectangle constraint;
    int oldX;
    int oldY;

    public TaskChangeConstraintCommand(Task task, Rectangle constraint) {
        super("Change Position");
        this.task = task;
        this.constraint = constraint;
    }

    public void execute() {
        oldX = task.getX();
        oldY = task.getY();
        redo();
    }

    public void redo() {
        task.setX(constraint.x);
        task.setY(constraint.y);
    }

    public void undo() {
        task.setX(oldX);
        task.setY(oldY);
    }
}
