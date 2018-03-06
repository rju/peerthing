package de.peerthing.visualization.queryeditor.forms;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import de.peerthing.visualization.queryeditor.QueryEditor;
import de.peerthing.visualization.querymodel.interfaces.IQueryModelObject;

/**
 * This class displays a form for the query editor with
 * only the text "Overview". It is not really needed... 
 *
 * @author Michael Gottschalk
 *
 */
public class OverviewForm extends AbstractQueryEditorForm {
    @Override
    public void createForm(Composite parent, QueryEditor editor) {
        super.createForm(parent, editor);

        form.getBody().setLayout(new GridLayout(2, false));
        form.setText("Overview");
    }

    @Override
    public void update(IQueryModelObject object) {
    }

    @Override
    public void aboutToClose() {
    }

    @Override
    public void applyAllChanges() {
    }

}
