package de.peerthing.systembehavioureditor.propertyeditor.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import de.peerthing.systembehavioureditor.SystemBehaviourFiletypeRegistration;
import de.peerthing.systembehavioureditor.model.editor.Condition;
import de.peerthing.systembehavioureditor.propertyeditor.PropertyEditor;

/**
 * This class manages a form with which you can manipulate the data of a condition in
 * the propertyeditor
 * 
 * @author Sebastian
 * @Reviewer Hendrik Angenendt
 */
public class ConditionForm implements SelectionListener {

	/**
	 * A label for the name of the condition
	 */
	Label renameLabel;

	/**
	 * A textfield filled with the name of the case
	 */
	Text renameText;

	/**
	 * The current form
	 */
	ScrolledForm form;

	/**
	 * The current condition
	 */
	Condition con1;

	/**
	 * The current propertyview
	 */
	PropertyEditor actionview;

	/**
	 * Gui is initialised in the constructor
	 * 
	 * @param container
	 */
	public ConditionForm(Composite container) {

		FormToolkit toolkit = new FormToolkit(container.getDisplay());

		form = toolkit.createScrolledForm(container);
		form.getBody().setLayout(new GridLayout(2, false));
		form.setText("Condition Information:");
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
				if (!renameText.getText().equals(con1.getName())) {
					con1.setName(renameText.getText());
					actionview.getGraphed().setDirty();
					actionview.getTreeViewer().refresh();
					SystemBehaviourFiletypeRegistration.getNavigationTree()
							.update(con1);

				}
			}
		});
	}

	/**
	 * Every time the user selects a condition in the editor the method will be
	 * called to adjust the form to the choosen object
	 * 
	 * @param object
	 * @param actionview
	 */
	public void update(Object object, PropertyEditor actionview) {
		this.actionview = actionview;
		con1 = (Condition) object;
		renameText.setText(con1.getName());
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
		con1.setName(renameText.getText());
		actionview.nodeNameChanged(con1);
	}

	public void widgetDefaultSelected(SelectionEvent e) {
	}

}
