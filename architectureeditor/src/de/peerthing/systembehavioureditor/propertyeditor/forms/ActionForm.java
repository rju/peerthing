package de.peerthing.systembehavioureditor.propertyeditor.forms;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.*;

import org.eclipse.swt.SWT;
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
import de.peerthing.systembehavioureditor.model.editor.Action;
import de.peerthing.systembehavioureditor.model.editor.Parameter;
import de.peerthing.systembehavioureditor.propertyeditor.PropertyEditor;
import de.peerthing.systembehavioureditor.propertyeditor.plugindatahandler.PlugInAction;
import de.peerthing.systembehavioureditor.propertyeditor.plugindatahandler.PlugInParameter;

/**
 * This class manages a form with which you can manipulate the data of an action
 * in the propertyeditor
 * 
 * @author Sebastian
 * @Reviewer Hendrik Angenendt
 */
public class ActionForm implements SelectionListener {

    /**
     * A label for the action to chosse
     */
    Label chooseLabel;

    /**
     * A combo box which allows to choose to action from the plugin
     */
    Combo callCombo;

    /**
     * A label for adding paramater
     */
    Label paraLabel;

    /**
     * A combo box which allows to choose parameter for the action
     */
    Combo paraCombo;

    /**
     * A label for the description of the action
     */
    Label descriptionLabel;

    /**
     * A textfield filled with the description of the action
     */
    Text descriptionText;

    /**
     * The current form
     */
    ScrolledForm form;

    /**
     * The current action
     */
    Action a1;

    /**
     * The current propertyview
     */
    PropertyEditor actionview;

    /**
     * Gui is initialised in the constructor
     * 
     * @param container
     */
    public ActionForm(Composite container) {

        FormToolkit toolkit = new FormToolkit(container.getDisplay());

        form = toolkit.createScrolledForm(container);
        form.getBody().setLayout(new GridLayout(2, false));
        form.setText("Choose a defined action: ");
        toolkit.paintBordersFor(form.getBody());

        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        chooseLabel = toolkit.createLabel(form.getBody(), "Choose action:",
                SWT.NONE);
        callCombo = new Combo(form.getBody(), SWT.NONE);
        callCombo.setLayoutData(gd);
        callCombo.addSelectionListener(this);

        gd = new GridData(GridData.FILL_BOTH);
        descriptionLabel = toolkit.createLabel(form.getBody(), "Description:",
                SWT.NONE);
        descriptionText = new Text(form.getBody(), SWT.MULTI | SWT.WRAP
                | SWT.V_SCROLL);
        descriptionText.setEditable(false);
        descriptionText.setLayoutData(gd);
        descriptionText.addSelectionListener(this);

        gd = new GridData(GridData.FILL_HORIZONTAL);
        paraLabel = toolkit.createLabel(form.getBody(), "Choose parameter:",
                SWT.NONE);
        paraCombo = new Combo(form.getBody(), SWT.NONE);
        paraCombo.setLayoutData(gd);
		paraCombo.addSelectionListener(this);

	}

	/**
	 * This method searches all actions defined in th plugin and inserts them
	 * into the combo box
	 */
	public void callInit() {

		List<String> actions = new ArrayList<String>();

		for (PlugInAction pa : PeerThingSystemBehaviourEditorPlugin
				.getDefault().getPlugInDataHandler().getPluginActions()) {
			actions.add(pa.getName());
		}
		Collections.sort(actions);

		for (int i = 0; i < actions.size(); i++) {
			callCombo.add(actions.get(i));
		}
	}

	/**
	 * This method searches all parameter of the action and inserts them into
	 * the combo box
	 */
	public void paraInit() {
		if (callCombo.getSelectionIndex() >= 0) {
			for (PlugInParameter pp : PeerThingSystemBehaviourEditorPlugin
					.getDefault().getPlugInDataHandler().getPluginAction(
							callCombo.getItem(callCombo.getSelectionIndex()))
                    .getPluginparameter()) {
                if (!pp.isRequired())
                    paraCombo.add(pp.getName());
            }
        }

    }

    /**
     * Every time the user selects an action in the editor the method will be
     * called to adjust the form to the choosen object
     * 
     * @param object
     * @param actionview
     */
    public void update(Object object, PropertyEditor actionview) {
        this.actionview = actionview;
        a1 = (Action) object;

        if (callCombo.getItemCount() == 0) {
            callInit();
        }

        boolean validAction = false;
        for (int x = 0; x < callCombo.getItems().length; x++) {
            if (callCombo.getItem(x).equals(a1.getName())) {
                validAction = true;
                callCombo.select(x);
                descriptionText
                        .setText(PeerThingSystemBehaviourEditorPlugin
                                .getDefault().getPlugInDataHandler()
                                .getPluginAction(
                                        callCombo.getItem(callCombo
                                                .getSelectionIndex()))
                                .getDescription());
            }
        }
        if (!validAction) {
            callCombo.deselectAll();
            descriptionText.setText("");
        } else {
            if (paraCombo.getItemCount() == 0) {
                paraInit();
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
    public void widgetSelected(SelectionEvent event) {
        if (event.getSource().equals(callCombo)) {
            updateAction();
        } else if (event.getSource().equals(paraCombo)) {
            Combo c = (Combo) event.getSource();
            Parameter tmp = new Parameter(c.getItem(c.getSelectionIndex()), a1);
            a1.addParameter(tmp);
            actionview.nodeNameChanged(a1);
            actionview.getTreeViewer().refresh();
        }
    }

    /**
     * Updates the action with the value currently selected in the call combo
     * box
     * 
     */
    private void updateAction() {
        Map<String, IParameter> paras = new Hashtable<String, IParameter>();
        a1.setParameters(paras);
        paraCombo.removeAll();
        paraInit();
        for (PlugInParameter pp : PeerThingSystemBehaviourEditorPlugin
                .getDefault().getPlugInDataHandler().getPluginAction(
                        callCombo.getItem(callCombo.getSelectionIndex()))
                .getPluginparameter()) {
            Parameter tmp = new Parameter(pp.getName(), a1);
            if (pp.isRequired())
                a1.addParameter(tmp);
        }
        a1.setName(callCombo.getItem(callCombo.getSelectionIndex()));
        descriptionText.setText(PeerThingSystemBehaviourEditorPlugin
                .getDefault().getPlugInDataHandler().getPluginAction(
                        callCombo.getItem(callCombo.getSelectionIndex()))
                .getDescription());

        actionview.nodeNameChanged(a1);
        actionview.getTreeViewer().refresh();
        actionview.getTreeViewer().expandToLevel(a1, 1);
    }

    public void widgetDefaultSelected(SelectionEvent event) {
    }

}
