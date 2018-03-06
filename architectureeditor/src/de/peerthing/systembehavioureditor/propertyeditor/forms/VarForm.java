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
import de.peerthing.systembehavioureditor.model.IVariable;
import de.peerthing.systembehavioureditor.propertyeditor.PropertyEditor;

/**
 * This class manages a form with which you can manipulate
 * the data of a variable in the propertyeditor
 * 
 * @author Sebastian
 * @Reviewer
 */
public class VarForm implements SelectionListener {

	/**
	 * A label for the name of the variable
	 */
	Label renameLabel;

	/**
	 * A textfield filled with the name of the variable
	 */
	Text renameText;

	/**
	 * A label for the value of the variable
	 */
	Label typeLabel;

	/**
	 * A textfield filled with the value of the variable
	 */
	Text initialValueText;

	/**
	 * The current form
	 */
	ScrolledForm form;

	/**
	 * The current variable
	 */
	IVariable v1;

	/**
	 * The current propertyview
	 */
	PropertyEditor actionview;

	/**
	 * Gui is initialised in the constructor
	 * 
	 * @param container
	 */
	public VarForm(Composite container) {

		FormToolkit toolkit = new FormToolkit(container.getDisplay());

		form = toolkit.createScrolledForm(container);
		form.getBody().setLayout(new GridLayout(2, false));
		form.setText("Variable Information:");
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
				if (!renameText.getText().equals(v1.getName())) {
					v1.setName(renameText.getText());
					actionview.getGraphed().setDirty();
					actionview.getTreeViewer().refresh();
					SystemBehaviourFiletypeRegistration.getNavigationTree()
							.update(v1);

				}
			}
		});

		typeLabel = toolkit.createLabel(form.getBody(), "Initial Value:",
				SWT.NONE);
		GridData gd1 = new GridData(GridData.FILL_HORIZONTAL);

		initialValueText = toolkit.createText(form.getBody(), "", SWT.NONE);
		initialValueText.setLayoutData(gd1);

		initialValueText.addSelectionListener(new SelectionAdapter() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		initialValueText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (!initialValueText.getText().equals(v1.getInitialValue())) {
					v1.setInitialValue(initialValueText.getText());
					actionview.getGraphed().setDirty();
					actionview.getTreeViewer().refresh();
					SystemBehaviourFiletypeRegistration.getNavigationTree()
							.update(v1);

				}
			}
		});

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
		v1.setName(renameText.getText());
		v1.setInitialValue(initialValueText.getText());
		actionview.nodeNameChanged(v1);

	}

	public void widgetDefaultSelected(SelectionEvent e) {

	}

	/**
	 * Every time the user selects a variable in the editor the method will be
	 * called to adjust the form to the choosen object
	 * 
	 * @param object
	 * @param actionview
	 */
	public void update(Object object, PropertyEditor actionview) {
		this.actionview = actionview;
		v1 = (IVariable) object;
		renameText.setText(v1.getName());
		initialValueText.setText(v1.getInitialValue());
	}

}
