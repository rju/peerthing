package de.peerthing.systembehavioureditor.propertyeditor;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.IParameter;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.ITransition;
import de.peerthing.systembehavioureditor.model.IVariable;
import de.peerthing.systembehavioureditor.model.editor.Action;
import de.peerthing.systembehavioureditor.model.editor.CaseSystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Condition;
import de.peerthing.systembehavioureditor.model.editor.State;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.Task;
import de.peerthing.systembehavioureditor.model.editor.Transition;
import de.peerthing.systembehavioureditor.propertyeditor.PropertyEditor.RootElement;

/**
 * Fills the TreeViewer with the correct items
 * @author jojo
 */

public class ActionContentProvider implements ITreeContentProvider {

	public ActionContentProvider() {
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	/**
	 * returns depending on the selected item the children. this is for filling the left
	 * propertyview.
	 */
	public Object[] getChildren(Object parentElement) {
		Object[] returnvalue = null;
		if (parentElement instanceof Task) {
			returnvalue = ((Task) parentElement).getVariables().toArray();
		}else if (parentElement instanceof IParameter) {
			returnvalue = null;
		} else if (parentElement instanceof IVariable) {
			returnvalue = null;
		} else if (parentElement instanceof INodeType) {
			returnvalue = ((INodeType) parentElement).getVariables().toArray();
		} else if (parentElement instanceof Transition) {
			if (!(((Transition)parentElement).getState() instanceof ITask)) {
				returnvalue = ((Transition) parentElement).getContents().toArray();
			}
		} else if (parentElement instanceof Action) {
				returnvalue = ((Action) parentElement).getParameters().values()
						.toArray();
		} else if (parentElement instanceof Condition) {
			Object[] help = new Object[1];
			help[0] = ((Condition) parentElement).getDefaultCase();
			returnvalue = help;
			help[0] = ((Condition) parentElement).getDefaultCase();
			concat(returnvalue, help);
			if (!((Condition) parentElement).getCases().isEmpty()) {
				returnvalue = concat(returnvalue, ((Condition) parentElement)
						.getCases().toArray());
			}
		} else if (parentElement instanceof CaseSystemBehaviour) {
			returnvalue = ((CaseSystemBehaviour) parentElement).getContents()
					.toArray();
		} else if (parentElement instanceof State) {
			returnvalue = ((State) parentElement).getContents().toArray();
		} else if (parentElement instanceof SystemBehaviour) {
			if (!((SystemBehaviour)parentElement).getNodes().get(0).getVariables().isEmpty()) {
				returnvalue = ((SystemBehaviour)parentElement).getNodes().get(0).getVariables().toArray();
			}
		}
		return returnvalue;
	}

	/**
	 * not needed by the propertyeditor
	 */
	public Object getParent(Object child) {
		return null;
	}

	/**
	 * returns if the selected item has any children
	 */
	public boolean hasChildren(Object element) {
		boolean returnvalue = true;
		if (element instanceof Task) {
			returnvalue = !((Task) element).getVariables().isEmpty();
		}else if (element instanceof IParameter) {
			returnvalue = false;
		} else if (element instanceof IVariable) {
			returnvalue = false;
		} else if (element instanceof INodeType) {
			returnvalue = !((INodeType) element).getVariables().isEmpty();
		} else if (element instanceof Transition) {
			returnvalue = !((Transition) element).getContents().isEmpty();
		} else if (element instanceof Action) {
			returnvalue = !((Action) element).getParameters().isEmpty(); 

		} else if (element instanceof Condition) {
			returnvalue = true;
		} else if (element instanceof CaseSystemBehaviour) {
			returnvalue = !((CaseSystemBehaviour) element).getContents().isEmpty();
		} else if (element instanceof State) {
			returnvalue = !((State) element).getContents().isEmpty();
		} else if (element instanceof SystemBehaviour) {
			returnvalue = !((SystemBehaviour)element).getNodes().get(0).getVariables().isEmpty();
		}
		return returnvalue;
	}

	/**
	 * returns the object, which is shown in the propertyeditor
	 */
	public Object[] getElements(Object parentElement) {
		//System.out.println("jojojojojojo: " + ((RootElement)parentElement).getObject().toString());
		if (parentElement instanceof RootElement) {
			if (((RootElement)parentElement).getObject() instanceof ITransition){
				if ((((Transition)((RootElement)parentElement).getObject()).getState() instanceof ITask)) {
					return new Object[] { ((Transition)((RootElement)parentElement).getObject()).getState() };
				}
			} 
			return new Object[] { ((RootElement) parentElement).getObject() };
		} 
		return null;
	}

	/*
	 * function to concatinate two objects
	 * @author gom
	 */
	protected Object[] concat(Object[] object, Object[] more) {
		if (object == null)
			return more;
		else if (more == null)
			return object;
		else {
			Object[] both = new Object[object.length + more.length];
			System.arraycopy(object, 0, both, 0, object.length);
			System.arraycopy(more, 0, both, object.length, more.length);
			return both;
		}
	}

}
