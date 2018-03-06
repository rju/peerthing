package de.peerthing.visualization.queryeditor.forms;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import de.peerthing.visualization.queryeditor.SQLSourceViewerConfig;
import de.peerthing.visualization.queryeditor.QueryEditor;
import de.peerthing.visualization.querymodel.interfaces.IQuery;
import de.peerthing.visualization.querymodel.interfaces.IQueryModelObject;

/**
 * This form is used for editing IQuery objects.
 * 
 * @author Michael Gottschalk, Petra (dynamic help)
 *
 */
public class QueryForm extends AbstractQueryEditorForm implements
        ModifyListener {
    private Text nameInput;

    /**
     * Indicates that at certain times, no modify events should be processed in
     * the text fields, e.g. when the input has changed and the text is set
     * programmatically.
     */
    private boolean noModifyEvents = true;

    private SourceViewer textViewer;

    private Button applyButton;

    @Override
    public void createForm(Composite parent, QueryEditor editor) {
        super.createForm(parent, editor);

        GridLayout layout = new GridLayout(2, false);

        form.getBody().setLayout(layout);
        form.setText("Query");

        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        toolkit.createLabel(form.getBody(), "Name:", SWT.NONE);

        nameInput = toolkit.createText(form.getBody(), "");
        nameInput.setLayoutData(gd);
        nameInput.addModifyListener(this);

        gd = new GridData();
        gd.verticalIndent = 20;
        gd.horizontalSpan = 2;
        Label l2 = toolkit
                .createLabel(form.getBody(),
                        "SQL statements preparing the visualization (e.g. creating temporary tables):");
        l2.setLayoutData(gd);

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
		PlatformUI.getWorkbench().getHelpSystem().setHelp( textViewer.getControl(), "de.peerthing.workbench.queryeditor");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(nameInput, "de.peerthing.workbench.queryeditor");
    }

    @Override
    public void update(IQueryModelObject object) {
        currentObject = object;
        IQuery query = (IQuery) object;

        noModifyEvents = true;
        nameInput.setText(query.getName());
        textViewer.getDocument().set(query.getPreparingQueries());
        noModifyEvents = false;
    }

    public void modifyText(ModifyEvent e) {
        if (!noModifyEvents) {
            ((IQuery) currentObject).setName(nameInput.getText());
            tree.update(currentObject);
            queryEditor.setDirty();
        }
    }

    @Override
    public void aboutToClose() {
        IQuery query = (IQuery) currentObject;
        if (!query.getPreparingQueries().equals(textViewer.getDocument().get())) {
            if (MessageDialog.openQuestion(form.getShell(), "Query changed",
                    "Do you want to apply the changes?")) {
                applyAllChanges();
                queryEditor.setDirty();
            }
        }
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        if (e.getSource() == applyButton) {
            ((IQuery) currentObject).setPreparingQueries(textViewer
                    .getDocument().get());
            queryEditor.setDirty();
        }
    }

    @Override
    public void applyAllChanges() {
        ((IQuery) currentObject).setPreparingQueries(textViewer.getDocument()
                .get());
    }

}
