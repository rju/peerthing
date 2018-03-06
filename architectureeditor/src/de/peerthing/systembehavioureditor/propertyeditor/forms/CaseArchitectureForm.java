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
import de.peerthing.systembehavioureditor.model.ICaseArchitecture;
import de.peerthing.systembehavioureditor.propertyeditor.PropertyEditor;

/**
 * This class manages a form with which you can manipulate the data of a case in
 * the propertyeditor
 * 
 * @author Sebastian
 * @Reviewer Hendrik Angenendt
 */
public class CaseArchitectureForm implements SelectionListener {

	/**
	 * A label for the expression of the case
	 */
	Label exprLabel;

	/**
	 * A textfield filled with the expression of the case
	 */
	Text exprText;

	/**
	 * The current form
	 */
	ScrolledForm form;

	/**
	 * The current case
	 */
	ICaseArchitecture case1;

	/**
	 * The current propertyview
	 */
	PropertyEditor actionview;

	/**
	 * Gui is initialised in the constructor
	 * 
	 * @param container
	 */
	public CaseArchitectureForm(Composite container) {

		FormToolkit toolkit = new FormToolkit(container.getDisplay());

		form = toolkit.createScrolledForm(container);
		form.getBody().setLayout(new GridLayout(2, false));
		form.setText("Case Information:");
		toolkit.paintBordersFor(form.getBody());

		exprLabel = toolkit
				.createLabel(form.getBody(), "Expression:", SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		exprText = toolkit.createText(form.getBody(), "", SWT.NONE);
		exprText.setLayoutData(gd);

		exprText.addSelectionListener(new SelectionAdapter() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		exprText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (!exprText.getText().equals(case1.getExpression())) {
					case1.setExpression(exprText.getText());
					actionview.getGraphed().setDirty();
					actionview.getTreeViewer().refresh();
					SystemBehaviourFiletypeRegistration.getNavigationTree()
							.update(case1);

				}
			}
		});

	}

	/**
	 * Every time the user selects a case in the editor the method will be
	 * called to adjust the form to the choosen object
	 * 
	 * @param object
	 * @param actionview
	 */
	public void update(Object object, PropertyEditor actionview) {
		this.actionview = actionview;
		if (object instanceof ICaseArchitecture) {
			case1 = (ICaseArchitecture) object;
			try {
				// the default expression returns a null-pointer-exception
				exprText.setText(case1.getExpression());
			} catch (Exception e) {
				exprText.setText("");
			}
		}
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
		if (case1.getExpression() != "Default") {
			case1.setExpression(exprText.getText());
			actionview.nodeNameChanged(case1);
		}
	}

	public void widgetDefaultSelected(SelectionEvent e) {
	}

}
