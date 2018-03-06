/*
 * Created on 12.07.2006
 *
 */
package de.peerthing.visualization.queryeditor.actions;

import java.util.List;

import org.eclipse.jface.action.IAction;

import de.peerthing.visualization.VisualizationPlugin;

/**
 * Copies the currently selected elements to a "clipboard"
 * that is only used within the visualization component.
 *
 * @author Michael Gottschalk
 *
 */
public class CopyAction extends AbstractTreeAction {

    public void run(IAction action) {
        List<Object> copyList = VisualizationPlugin.getDefault().getCopiedObjects();
        copyList.clear();
        
        for (Object obj : selectedObjects) {
            copyList.add(obj);
        }
    }

}
