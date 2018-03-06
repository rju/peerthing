package de.peerthing.systembehavioureditor.propertyeditor.forms;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import de.peerthing.systembehavioureditor.SystemBehaviourFiletypeRegistration;
import de.peerthing.systembehavioureditor.interchange.EAIInitializeEvaluation;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;
import de.peerthing.systembehavioureditor.propertyeditor.PropertyEditor;

/**
 * *This class manages a form with which you can manipulate the data of a state
 * in the propertyeditor
 * 
 * @author Sebastian
 * @Reviewer Hendrik Angenendt
 */
public class StateForm implements SelectionListener {

	/**
	 * A label which is visible if a name is not valid
	 */
	Label checkLabel;

	/**
	 * A textfield filled with the error message
	 */
	Text errorText;

	/**
	 * A label for the name of the state
	 */
	Label renameLabel;

	/**
	 * A textfield filled with the name of the state
	 */
	Text renameText;

	/**
	 * The current form
	 */
	ScrolledForm form;

	/**
	 * The current state
	 */
	IState st1;

	/**
	 * A label for the value of the state which decides when its context has to
	 * be evaluated
	 */
	Label iniLabel;

	/**
	 * A combo box which allows to choose from different items for the state?s
	 * evaluation-value
	 */
	Combo iniCombo;

	/**
	 * The current propertyview
	 */
	PropertyEditor actionview;

	/**
	 * Gui is initialised in the constructor
	 * 
	 * @param container
	 */
	public StateForm(Composite container) {

		FormToolkit toolkit = new FormToolkit(container.getDisplay());

		form = toolkit.createScrolledForm(container);
		form.getBody().setLayout(new GridLayout(2, false));
		form.setText("State Information:");
		toolkit.paintBordersFor(form.getBody());

		renameLabel = toolkit.createLabel(form.getBody(), "Name:", SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		renameText = toolkit.createText(form.getBody(), "", SWT.NONE);
		renameText.setTextLimit(32);
		renameText.setLayoutData(gd);

		renameText.addSelectionListener(new SelectionAdapter() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		renameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (nameCheck(renameText.getText())) {
					checkLabel.setVisible(false);
					errorText.setVisible(false);
					if (!renameText.getText().equals(st1.getName())) {
						st1.setName(renameText.getText());
						actionview.getGraphed().setDirty();
						actionview.getTreeViewer().refresh();
						SystemBehaviourFiletypeRegistration.getNavigationTree()
								.update(st1);

					}
				} else {
					checkLabel.setVisible(true);
					errorText.setVisible(true);
				}
			}
		});

		GridData gd1 = new GridData(GridData.FILL_HORIZONTAL);
		iniLabel = toolkit.createLabel(form.getBody(), "Initialize:", SWT.NONE);
		iniCombo = new Combo(form.getBody(), SWT.NONE);
		iniCombo.setLayoutData(gd1);
		iniCombo.addSelectionListener(this);
		
		checkLabel = toolkit.createLabel(form.getBody(), "ERROR:", SWT.NONE);
		checkLabel.setForeground(new Color(null, 255, 0, 0));
		checkLabel.setVisible(false);
		errorText = toolkit.createText(form.getBody(),
				"The chosen name already exists.", SWT.NONE);
		errorText.setBackground(new Color(null, 255, 0, 0));
		errorText.setLayoutData(gd);
		errorText.setEditable(false);
		errorText.setVisible(false);
	}

	/**
	 * Every time the user selects a state in the editor the method will be
	 * called to adjust the form to the choosen object
	 * 
	 * @param object
	 * @param actionview
	 */
	public void update(Object object, PropertyEditor actionview) {
		this.actionview = actionview;
		st1 = (IState) object;
		renameText.setText(st1.getName());
		if (iniCombo.getItemCount() == 0) {
			iniInit();
		}
		if (st1.getInitializeEvaluation() == EAIInitializeEvaluation.once) {
			iniCombo.select(0);
		} else if (st1.getInitializeEvaluation() == EAIInitializeEvaluation.each) {
			iniCombo.select(1);
		}
	}

	/**
	 * This method fills the combo box with the values "once" ans "each"
	 */
	public void iniInit() {
		iniCombo.add("once");
		iniCombo.add("each");
	}

	/**
	 * Returns the form.
	 * 
	 * @return the form
	 */
	public ScrolledForm getForm() {
		return form;
	}

	/**
	 * The actions of the user are handled here.
	 * 
	 * @param event
	 */
	public void widgetSelected(SelectionEvent e) {
		st1.setName(renameText.getText());

		
		((SystemBehaviour) (st1.getTask().getNode().getArchitecture()))
				.getEditor().setDirty(); // (Peter)

		if (iniCombo.getItem(iniCombo.getSelectionIndex()).toString().equals(
				"once")) {
			st1.setInitializeEvaluation(EAIInitializeEvaluation.once);
		} else if (iniCombo.getItem(iniCombo.getSelectionIndex()).toString()
				.equals("each")) {
			st1.setInitializeEvaluation(EAIInitializeEvaluation.each);
		}
		actionview.nodeNameChanged(st1);
	}

	/**
	 * This method checks the name of the state to be unique
	 * 
	 * @param name
	 * @return boolean
	 */
	public boolean nameCheck(String name) {
		List<ITask> tasks = st1.getTask().getNode().getTasks();
		List<IState> states = new ArrayList<IState>();
		for(int i=0; i<tasks.size(); i++){
			states.addAll(tasks.get(i).getStates());
		}
		for (int i = 0; i < states.size(); i++) {
			if (states.get(i).getName().equals(name)
					&& !states.get(i).getName().equals(st1.getName())) {
				renameText.setBackground(new Color(null, 255, 0, 0));
				return false;
			} else if (name.equals("")) {
				renameText.setBackground(new Color(null, 255, 0, 0));
				return false;
			}
		}
		renameText.setBackground(new Color(null, 255, 255, 255));
		return true;
	}

	public void widgetDefaultSelected(SelectionEvent e) {

	}
}
