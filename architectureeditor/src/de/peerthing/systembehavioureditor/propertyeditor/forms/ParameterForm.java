package de.peerthing.systembehavioureditor.propertyeditor.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import de.peerthing.systembehavioureditor.PeerThingSystemBehaviourEditorPlugin;
import de.peerthing.systembehavioureditor.model.IParameter;
import de.peerthing.systembehavioureditor.propertyeditor.PropertyEditor;
import de.peerthing.systembehavioureditor.propertyeditor.plugindatahandler.PlugInAction;
import de.peerthing.systembehavioureditor.propertyeditor.plugindatahandler.PlugInParameter;

/**
 * This class manages a form with which you can manipulate the data of a
 * parameter in the propertyeditor
 * 
 * @author Sebastian
 * @Reviewer Hendrik Angenendt
 */
public class ParameterForm implements SelectionListener {
	/**
	 * A container for different forms used for different cases
	 */
	Composite container;

	/**
	 * A label for the name of the parameter
	 */
	Label renameLabel;

	private boolean valueSelected = true;

	/**
	 * A textfield filled with the name of the parameter
	 */
	Text renameText;

	/**
	 * A textfield filled with the value of the parameter
	 */
	Text valueText;

	/**
	 * A textfield filled with the expression of the parameter
	 */
	Text exprText;

	/**
	 * A label for the value of the parameter
	 */
	Label valueLabel;

	/**
	 * A label for the expression of the parameter
	 */
	Label exprLabel;

	/**
	 * A label for the description of the parameter
	 */
	Label paradescriptionLabel;

	/**
	 * A textfield filled with the description of the parameter
	 */
	Text paradescriptionText;

	/**
	 * Another label for the value of the parameter
	 */
	Label value2Label;

	/**
	 * A label for the description of the value
	 */
	Label valuedescriptionLabel;

	/**
	 * A textfield filled with the description of the value
	 */
	Text valuedescriptionText;

	/**
	 * The current form
	 */
	ScrolledForm form;

	/**
	 * The current parameter
	 */
	IParameter para1;

	/**
	 * The current propertyview
	 */
	PropertyEditor actionview;

	/**
	 * A variable to mark a parameter as selfmade
	 */
	private boolean selfmade = false;

	/**
	 * A variable to set if an expression for a parameter is allowed
	 */
	private boolean expressionAllowed = true;

	/**
	 * A variable to set if a value for a parameter is allowed
	 */
	private boolean valueAllowed = true;

	/**
	 * The description of a parameter
	 */
	private String paraDescription;

	/**
	 * The description of a value
	 */
	private String valueDescription;

	private Text expressionValue;

	/**
	 * Gui is initialised in the constructor
	 * 
	 * @param container
	 */
	public ParameterForm(Composite container) {
		this.container = container;
		selfmade = false;
		createGUI();
	}

	/**
	 * This part is for editing the name of a selfmade parameter. If the
	 * parameter is chosen from the plugin you cannot change the name. Also the
	 * description of the parameter has to be part of the initialsation for a
	 * helping tooltip.
	 * 
	 * @param toolkit
	 * @param des
	 */
	private void nameEditPart(FormToolkit toolkit, String des) {
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		renameLabel = toolkit.createLabel(form.getBody(), "Name:", SWT.NONE);
		if (selfmade) {
			renameText = toolkit.createText(form.getBody(), "", SWT.NONE);
			renameText.setToolTipText(des);
			renameText.setLayoutData(gd);
			renameText.addSelectionListener(new SelectionAdapter() {
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});
			renameText.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					para1.setName(renameText.getText());
					actionview.nodeNameChanged(para1);
				}
			});
		} else {
			renameText = toolkit.createText(form.getBody(), "", SWT.NONE);
			renameText.setLayoutData(gd);
			renameText.setToolTipText(des);
			renameText.setEditable(false);
		}
		if (para1 != null) {
			renameText.setText(para1.getName());
		}
	}

	private void exprandvalPart(FormToolkit tk, String val) {
		if (para1 != null) {
			PlugInAction patmp = PeerThingSystemBehaviourEditorPlugin
					.getDefault().getPlugInDataHandler().getPluginAction(
							para1.getAction().getName());
			PlugInParameter pptmp = patmp.getPluginparameter(para1.getName());
			if (pptmp != null) {
				if (!pptmp.isReferenceAllowed() && pptmp.isValueAllowed()) {
					valueSelected = true;
				} else if (pptmp.isReferenceAllowed()
						&& !pptmp.isValueAllowed()) {
					valueSelected = false;
				}
			}
			GridData gd1 = new GridData(GridData.FILL_HORIZONTAL);
			GridData gd2 = new GridData(GridData.FILL_BOTH);
			if (true) {
				Combo nameCombo = new Combo(form.getBody(), SWT.MULTI
						| SWT.WRAP);
				nameCombo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				if (pptmp == null || pptmp.isValueAllowed()) {
					nameCombo.add("Value");
				}
				if (pptmp == null || pptmp.isReferenceAllowed()) {
					nameCombo.add("Expression");
				}
				nameCombo.addSelectionListener(new SelectionListener() {
					public void widgetDefaultSelected(SelectionEvent e) {
					}

					public void widgetSelected(SelectionEvent e) {
						Combo src = ((Combo) e.widget);
						int indx = src.getSelectionIndex();
						if (src.getItem(indx).equals("Value")) {
							valueSelected = true;
							actionview.updateForm(para1);
						} else {
							valueSelected = false;
							actionview.updateForm(para1);
						}
					}
				});
				if (valueSelected) {
					for (int x = 0; x < nameCombo.getItems().length; x++) {
						if (nameCombo.getItem(x).equals("Value")) {
							nameCombo.select(x);
						}
					}
					final Combo valCombo = new Combo(form.getBody(), SWT.NONE);
					valueInit(valCombo, pptmp);
					valCombo.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							int indx = valCombo.getSelectionIndex();
							System.out.println(indx);
							para1.setValue(valCombo.getItem(indx));
							para1.setExpression("");
							actionview.nodeNameChanged(para1);
						}
					});
					valCombo.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent e) {

							para1.setValue(valCombo.getText());
							para1.setExpression("");
							actionview.nodeNameChanged(para1);
						}
					});
					valCombo.setLayoutData(gd1);
				} else if (pptmp == null || pptmp.isReferenceAllowed()) {
					for (int x = 0; x < nameCombo.getItems().length; x++) {
						if (nameCombo.getItem(x).equals("Expression")) {
							nameCombo.select(x);
						}
					}
					nameCombo.select(1);
					expressionValue = tk.createText(form.getBody(), "",
							SWT.MULTI | SWT.WRAP);
					expressionInit(expressionValue);
					expressionValue.setLayoutData(gd2);
					expressionValue.addModifyListener(new ModifyListener() {

						public void modifyText(ModifyEvent e) {
							para1.setExpression(expressionValue.getText());
							para1.setValue("");
							actionview.nodeNameChanged(para1);
						}
					});
				}
			} else {
			}
		}
	}

	/**
	 * This method initialises the form if the parameter should have an expression
	 * 
	 * @param expVal
	 */
	private void expressionInit(Text expVal) {
		if (para1 != null) {
			expVal.setText(para1.getExpression());
		}
	}

	/**
	 * This part creates the GUI. It makes differences between selfmade and
	 * plugin-parameter. It chooses the right GUI for every case.
	 */
	private void createGUI() {
		try {
			FormToolkit toolkit = new FormToolkit(container.getDisplay());
			if (form != null) {
				form.dispose();
			}
			form = toolkit.createScrolledForm(container);
			form.getBody().setLayout(new GridLayout(2, false));
			form.setText("Parameter Information:");
			toolkit.paintBordersFor(form.getBody());
			nameEditPart(toolkit, paraDescription);
			exprandvalPart(toolkit, valueDescription);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.getForm().redraw();
	}

	/**
	 * This method searches all values defined for an action in the plugin and
	 * inserts them into the combo box
	 * 
	 * @param pptmp
	 * @param valcombo
	 */
	public void valueInit(Combo valcombo, PlugInParameter pptmp) {
		try {
			valcombo.removeAll();
			if (para1.getValue().trim().equals("")) {
				valcombo.add("<new Entry>");
			} else {
				valcombo.add(para1.getValue());
			}
			if (pptmp != null) {
				for (String s : pptmp.getPossibleValues().keySet()) {
					if (s != null && !s.equals(para1.getName())) {
						valcombo.add(s);
					}
				}
			}
			for (int x = 0; x < valcombo.getItemCount(); x++) {
				if (valcombo.getItem(x).equals(para1.getValue())) {
					valcombo.select(x);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Every time the user selects a parameter in the editor the method will be
	 * called to adjust the form to the choosen object
	 * 
	 * @param object
	 * @param actionview
	 */
	public void update(Object object, PropertyEditor actionview) {
		try {
			this.actionview = actionview;
			para1 = (IParameter) object;
			PlugInAction patmp = PeerThingSystemBehaviourEditorPlugin
					.getDefault().getPlugInDataHandler().getPluginAction(
							para1.getAction().getName());
			PlugInParameter pptmp = null;
			if (patmp != null) {
				pptmp = patmp.getPluginparameter(para1.getName());
				if (pptmp != null) {
					expressionAllowed = pptmp.isReferenceAllowed();
					valueAllowed = pptmp.isValueAllowed();
					paraDescription = pptmp.getDescription();
					selfmade = false;
				} else {
					selfmade = true;
				}
			}
			createGUI();
		} catch (Exception e) {
			e.printStackTrace();
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
		try {
			PlugInAction patmp = PeerThingSystemBehaviourEditorPlugin
					.getDefault().getPlugInDataHandler().getPluginAction(
							para1.getAction().getName());
			PlugInParameter pptmp = null;
			if (patmp != null) {
				pptmp = patmp.getPluginparameter(para1.getName());
			}
			para1.setName(renameText.getText());
			if (valueAllowed && selfmade) {

				para1.setValue(valueText.getText());
				if (pptmp != null) {
					valueText.setToolTipText(pptmp
							.getValueDescription(valueText.getText()));
				}
			} else if (valueAllowed && !selfmade) {

				para1.setValue(valueText.getText());
				if (pptmp != null) {
					valueText.setToolTipText(pptmp
							.getValueDescription(valueText.getText()));
				}
			}
			if (expressionAllowed) {
				para1.setExpression(exprText.getText());
			}
			actionview.nodeNameChanged(para1);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void widgetDefaultSelected(SelectionEvent e) {
	}
}
