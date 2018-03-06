package de.peerthing.visualization.queryeditor.forms;

import java.util.Hashtable;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import de.peerthing.visualization.queryeditor.QueryEditor;
import de.peerthing.visualization.queryeditor.SQLSourceViewerConfig;
import de.peerthing.visualization.querymodel.VisualizationData;
import de.peerthing.visualization.querymodel.interfaces.IQueryModelObject;
import de.peerthing.visualization.querymodel.interfaces.IVisualizationData;
import de.peerthing.visualization.querymodel.interfaces.IVisualizationData.Styles;

/**
 * This form is used for editing IVisualizationData
 * objects.
 * 
 * @author Michael Gottschalk, Petra (dynamic help)
 *
 */
public class VisualizationDataForm extends AbstractQueryEditorForm implements
        ModifyListener {

    /**
     * The text viewer in which the SQL commands can be edited
     */
    private SourceViewer textViewer;

    /**
     * The button with which a user can apply changes in the
     * sql text viewer.
     */
    private Button applyButton;

    /**
     * If this variable is true, no
     * modification events in the text fields 
     * are processed. This is useful when text
     * should be programmatically changed.
     * 
     */
    private boolean noModifyEvents = true;

    /**
     * Includes Radio Buttons for all styles that are 
     * defined for IVisualizationData objects.
     */
    private Hashtable<IVisualizationData.Styles, Button> styleButtons = new Hashtable<IVisualizationData.Styles, Button>();

    /**
     * A radio button to control normalization
     */
    private Button normalizeButton;

    /**
     * Text field for the name of the x axis of
     * diagrams
     */
    private Text xAxisText;

    /**
     * Text field for the name of the y axis of
     * diagrams
     */
    private Text yAxisText;
    
    /**
     * Text field for the name of the edited visualization
     */
    private Text nameText;

    @Override
    public void createForm(Composite parent, QueryEditor editor) {
        super.createForm(parent, editor);

        form.getBody().setLayout(new GridLayout(2, false));
        form.setText("Visualization");
        
        toolkit.createLabel(form.getBody(), "Name: ");
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        nameText = toolkit.createText(form.getBody(), "");
        nameText.setLayoutData(gd);
        nameText.addModifyListener(this);
        

        gd = new GridData(GridData.FILL_HORIZONTAL);
        toolkit.createLabel(form.getBody(), "Diagram type: ");
        
        Composite styleComp = toolkit.createComposite(form.getBody());
        styleComp.setLayoutData(gd);
        styleComp.setLayout(new RowLayout());

        for (IVisualizationData.Styles style : IVisualizationData.Styles
                .values()) {
            Button b = toolkit.createButton(styleComp, VisualizationData.getNameOfStyle(style),
                    SWT.RADIO);
            b.setData(style);
            b.addSelectionListener(this);
            styleButtons.put(style, b);
        }

        toolkit.createLabel(form.getBody(), "Normalization");
        normalizeButton = toolkit.createButton(form.getBody(), "normalize",
                SWT.CHECK);
        normalizeButton.addSelectionListener(this);

        toolkit.createLabel(form.getBody(), "X-Axis label: ");
        gd = new GridData(GridData.FILL_HORIZONTAL);
        xAxisText = toolkit.createText(form.getBody(), "");
        xAxisText.setLayoutData(gd);
        xAxisText.addModifyListener(this);

        toolkit.createLabel(form.getBody(), "Y-Axis label: ");
        yAxisText = toolkit.createText(form.getBody(), "");
        yAxisText.setLayoutData(gd);
        yAxisText.addModifyListener(this);

        gd = new GridData();
        gd.horizontalSpan = 2;
        gd.verticalIndent = 20;
        Label description = toolkit.createLabel(form.getBody(),
                "SQL statement that returns the data for the"
                        + " chosen diagram type:");
        description.setLayoutData(gd);
        gd = new GridData();
        gd.horizontalSpan = 2;
        Label hintLabel = toolkit.createLabel(form.getBody(),
                "Hint: Use $RUN$ as a variable indicating the current simulation run and \n$STARTTIME$ / $ENDTIME$ as variables for restricting the simulation time.");
        
        hintLabel.setLayoutData(gd);

        gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 2;

        textViewer = new SourceViewer(form.getBody(), null, SWT.BORDER
                | SWT.V_SCROLL);
        toolkit.adapt(textViewer.getControl(), true, true);
        textViewer.getControl().setLayoutData(gd);
        textViewer.configure(new SQLSourceViewerConfig());
        textViewer.setDocument(new Document());

        gd = new GridData();
        gd.horizontalAlignment = SWT.CENTER;
        gd.horizontalSpan = 2;

        applyButton = toolkit.createButton(form.getBody(), "Apply Changes",
                SWT.PUSH);
        applyButton.setLayoutData(gd);
        applyButton.addSelectionListener(this);

        //dynamic help	
		PlatformUI.getWorkbench().getHelpSystem().setHelp(applyButton, "de.peerthing.workbench.queryeditor");
		applyButton.setToolTipText("Pushing will save the changes.");     
		PlatformUI.getWorkbench().getHelpSystem().setHelp( textViewer.getControl(), "de.peerthing.workbench.visualization");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(normalizeButton, "de.peerthing.workbench.visualization");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(normalizeButton, "de.peerthing.workbench.visualization");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(xAxisText, "de.peerthing.workbench.visualization");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(yAxisText, "de.peerthing.workbench.visualization");		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(nameText, "de.peerthing.workbench.visualization");			
		
    }

    @Override
    public void update(IQueryModelObject object) {
        currentObject = object;
        IVisualizationData vis = (IVisualizationData) object;

        form.setText("Visualization for " + vis.getQuery().getName());

        noModifyEvents = true;
        textViewer.getDocument().set(vis.getDataQuery());
        for (IVisualizationData.Styles style : styleButtons.keySet()) {
            styleButtons.get(style).setSelection(style == vis.getStyle());
        }
        normalizeButton.setSelection(vis.isNormalized());
        
        xAxisText.setText(vis.getXAxisLabel());
        yAxisText.setText(vis.getYAxisLabel());
        nameText.setText(vis.getName());
        
        // Boxplots don't need x-Axis labels:
        xAxisText.setEnabled(vis.getStyle() != Styles.boxplot);
        
        noModifyEvents = false;
    }

    @Override
    public void aboutToClose() {
        IVisualizationData vis = (IVisualizationData) currentObject;
        if (!vis.getDataQuery().equals(textViewer.getDocument().get())) {
            if (MessageDialog.openQuestion(form.getShell(), "Query changed",
                    "Do you want to apply the changes?")) {
                applyAllChanges();
                queryEditor.setDirty();
            }
        }
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        IVisualizationData vis = (IVisualizationData) currentObject;

        if (noModifyEvents) {
            return;
        }

        if (e.getSource() == applyButton) {
            vis.setDataQuery(textViewer.getDocument().get());
            queryEditor.setDirty();
        } else if (e.getSource() instanceof Button) {
            Button b = (Button) e.getSource();
            if (b == normalizeButton) {
                if (vis.isNormalized() != b.getSelection()) {
                    vis.setNormalized(b.getSelection());
                    queryEditor.setDirty();
                }
            } else if (b.getData() != null
                    && b.getData() instanceof IVisualizationData.Styles) {
                IVisualizationData.Styles style = (IVisualizationData.Styles) b
                        .getData();
                if (style != vis.getStyle()) {
                    vis.setStyle(style);
                    tree.update(currentObject);
                    queryEditor.setDirty();
                    xAxisText.setEnabled(style != Styles.boxplot);
                }
            }
        }
    }

    @Override
    public void applyAllChanges() {
        ((IVisualizationData) currentObject).setDataQuery(textViewer
                .getDocument().get());
    }

    public void modifyText(ModifyEvent e) {
        IVisualizationData vis = (IVisualizationData) currentObject;

        if (!noModifyEvents) {
            if (e.getSource() == xAxisText) {
                if (!vis.getXAxisLabel().equals(xAxisText.getText())) {
                    vis.setXAxisLabel(xAxisText.getText());
                    queryEditor.setDirty();
                }
            } else if (e.getSource() == yAxisText) {
                if (!vis.getYAxisLabel().equals(yAxisText.getText())) {
                    vis.setYAxisLabel(yAxisText.getText());
                    queryEditor.setDirty();
                }
            } else if (e.getSource() == nameText) {
                if (!vis.getName().equals(nameText.getText())) {
                    vis.setName(nameText.getText());
                    queryEditor.setDirty();
                    tree.update(currentObject);
                }
            }
        }
    }

}
