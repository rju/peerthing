package de.peerthing.systembehavioureditor.propertyeditor.forms;

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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import de.peerthing.systembehavioureditor.SystemBehaviourFiletypeRegistration;
import de.peerthing.systembehavioureditor.model.ITransition;
import de.peerthing.systembehavioureditor.propertyeditor.PropertyEditor;

/**
 * This class manages a form with which you can manipulate the data of a
 * transition in the propertyeditor
 * 
 * @author Sebastian
 * @Reviewer Hendrik Angenendt
 */
public class TransitionForm implements SelectionListener {

	/**
	 * A label which is visible if a name is not valid
	 */
	Label checkLabel;

	/**
	 * A textfield filled with the error message
	 */
	Text errorText;

	/**
	 * A label for the name of the transition
	 */
	Label renameLabel;

	/**
	 * A textfield filled with the name of the transition
	 */
	Text renameText;

	/**
	 * The current form
	 */
	ScrolledForm form;

	/**
	 * The current transition
	 */
	ITransition tran1;

	/**
	 * The current propertyview
	 */
	PropertyEditor actionview;

	/**
	 * Gui is initialised in the constructor
	 * 
	 * @param container
	 */
	public TransitionForm(Composite container) {

		FormToolkit toolkit = new FormToolkit(container.getDisplay());

		form = toolkit.createScrolledForm(container);
		form.getBody().setLayout(new GridLayout(2, false));
		form.setText("Transition Information:");
		toolkit.paintBordersFor(form.getBody());

		renameLabel = toolkit.createLabel(form.getBody(), "Event:", SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		renameText = toolkit.createText(form.getBody(), "", SWT.NONE);
		renameText.setTextLimit(32);
		renameText.setLayoutData(gd);

		checkLabel = toolkit.createLabel(form.getBody(), "ERROR:", SWT.NONE);
		checkLabel.setForeground(new Color(null, 255, 0, 0));
		checkLabel.setVisible(false);
		errorText = toolkit.createText(form.getBody(),
				"The chosen name already exists.", SWT.NONE);
		errorText.setBackground(new Color(null, 255, 0, 0));
		errorText.setLayoutData(gd);
		errorText.setEditable(false);
		errorText.setVisible(false);

		renameText.addSelectionListener(new SelectionAdapter() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		renameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (nameCheck(renameText.getText())) {
					checkLabel.setVisible(false);
					errorText.setVisible(false);
					if (!renameText.getText().equals(tran1.getEvent())) {

						tran1.setEvent(renameText.getText());
						actionview.getGraphed().setDirty();
						actionview.getTreeViewer().refresh();
						SystemBehaviourFiletypeRegistration.getNavigationTree()
								.update(tran1);
					}
				} else {
					checkLabel.setVisible(true);
					errorText.setVisible(true);
				}
			}
		});
	}

	/**
	 * Every time the user selects a transition in the editor the method will be
	 * called to adjust the form to the choosen object
	 * 
	 * @param object
	 * @param actionview
	 */
	public void update(Object object, PropertyEditor actionview) {
		this.actionview = actionview;
		tran1 = (ITransition) object;
		renameText.setText(tran1.getEvent());
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
		actionview.getGraphed().setDirty();
		actionview.nodeNameChanged(tran1);
	}

	/**
	 * This method checks the name of the transition to be unique
	 * 
	 * @param name
	 * @return boolean
	 */
	public boolean nameCheck(String name) {
		List<ITransition> transen = tran1.getState().getTransitions();
		for (int i = 0; i < transen.size(); i++) {
			if (transen.get(i).getEvent().equals(name)
					&& !transen.get(i).getEvent().equals(tran1.getEvent())) {
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
