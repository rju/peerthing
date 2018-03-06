package de.peerthing.scenarioeditor.editor;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.peerthing.scenarioeditor.model.IUserBehaviour;
import de.peerthing.scenarioeditor.model.ICase;
import de.peerthing.scenarioeditor.model.ICommandContainer;
import de.peerthing.scenarioeditor.model.IConnectionCategory;
import de.peerthing.scenarioeditor.model.IDefaultCase;
import de.peerthing.scenarioeditor.model.IListWithParent;
import de.peerthing.scenarioeditor.model.ILoop;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.INodeConnection;
import de.peerthing.scenarioeditor.model.INodeResource;
import de.peerthing.scenarioeditor.model.IResourceCategory;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IScenarioCondition;
import de.peerthing.scenarioeditor.model.IUserAction;

/**
 * The structure of the tree is figured out here (by reading the scenario-data)
 * @author Hendrik Angenendt, Patrik Schulz
 */
public class ViewContentProvider implements IStructuredContentProvider,
        ITreeContentProvider {

    /**
     * The current scenario
     */
    IScenario scenario;

    /**
     * List of parent objects (node,connection,resources)
     */
    private Object[] mainParents = new Object[3];

    /**
     * This method controls if input is changed (not implemented yet)
     */
    public void inputChanged(Viewer v, Object oldInput, Object newInput) {
    }

    /**
     * This method sets the current scenario
     * @param scenario
     */
    public void setScenario(IScenario scenario) {
        this.scenario = scenario;
        mainParents[0] = scenario.getNodeCategories();
        mainParents[1] = scenario.getConnectionCategories();
        mainParents[2] = scenario.getResourceCategories();
    }

    public void dispose() {
    }

    /**
     * This method returns the scenario elements
     */
    public Object[] getElements(Object parent) {
        scenario = ((IScenario) parent);
        return mainParents;
    }

    /**
     * This method returns the main parents of a child object
     */
    public Object getParent(Object child) {

        if (child instanceof INodeCategory) {
            return mainParents[0];
        }

        if (child instanceof IConnectionCategory) {
            return mainParents[1];
        }

        if (child instanceof IResourceCategory) {
            return mainParents[2];
        }

        if (child instanceof INodeConnection) {
            INodeConnection childCast = (INodeConnection) child;
            return childCast.getNode();
        }

        if (child instanceof INodeResource) {
            INodeResource childCast = (INodeResource) child;
            return childCast.getNode();
        }

        return null;
    }

    /**
     * This method returns the chilren of a parent object
     */
    public Object[] getChildren(Object parent) {        

    	
        if (parent instanceof IListWithParent) {        	
            return ((IListWithParent) parent).toArray();
            
        } else if (parent instanceof IUserAction) {
            // IUserAction action = (IUserAction) parent;
            // TODO: action.getParameters(). ...
            // getParameters now returns a Hashtable
            // instead of Parameter objects (mg)
            return null;
        } else if (parent instanceof IScenarioCondition) {
            IScenarioCondition condition = (IScenarioCondition) parent;
            Object[] obj = new Object[condition.getCases().toArray().length + 1];
            for (int i = 0; i < condition.getCases().toArray().length; i++) {
                obj[i] = condition.getCases().get(i);
            }
            obj[condition.getCases().toArray().length] = condition
                    .getDefaultCase();
            return obj;
        } else if (parent instanceof ICommandContainer) {
            ICommandContainer container = (ICommandContainer) parent;
            return container.getCommands().toArray();
        } else if (parent instanceof INodeCategory) {
            INodeCategory nodeCat = (INodeCategory) parent;

            return new Object[] { nodeCat.getConnections(),
                    nodeCat.getBehaviours(), nodeCat.getResources() };
        }
        return null;
    }

    /**
     * This method returns if an parent object has children or not
     */
    public boolean hasChildren(Object parent) {    	    	
    	
        if (parent instanceof ILoop) {
            ILoop l1 = (ILoop) parent;
            return (l1.getCommands().size() != 0);
        } else if (parent instanceof ICase) {
            ICase c1 = (ICase) parent;
            return (c1.getCommands().size() != 0);
        } else if (parent instanceof IListWithParent) {
            IListWithParent p1 = (IListWithParent) parent;
            return (p1.size() != 0);
        } else if (parent instanceof IScenarioCondition) {
            return true;
        } else if (parent instanceof IDefaultCase) {
            IDefaultCase d1 = (IDefaultCase) parent;
            return (d1.getCommands().size() == 0);
        } else if (parent instanceof INodeCategory) {
            return true;
        } else if (parent instanceof IUserBehaviour) {
            IUserBehaviour b1 = (IUserBehaviour) parent;
            return (b1.getCommands().size() != 0);
        } else if (parent instanceof IUserAction) {
            return false;
        } else {
            return false;
        }
    }
}