package de.peerthing.visualization.view.table;

import java.io.FileWriter;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;

import de.peerthing.visualization.view.charts.SimulationRunQueryResult;

/**
 * A menu action used the table view. It exports the data from the currently
 * selected table view or from all open table views to CSV files.
 * 
 * @author Michael Gottschalk
 * 
 */
public class ExportTableAction implements IViewActionDelegate {
    /**
     * The currently selected table view
     */
    TableView tableView;

    /**
     * Separator used for the CSV file
     */
    private final String SEPARATOR = ",";

    /**
     * Sets the tableView variable
     */
    public void init(IViewPart view) {
        if (view instanceof TableView) {
            tableView = (TableView) view;
        }
    }

    /**
     * Displays a dialog for the selection of a folder where to export the CSV
     * files. Then calls writeCSVFile for the data from the currently selected
     * table view or, if selected by the user, from all currently open table
     * views.
     */
    public void run(IAction action) {
        if (tableView != null) {
            DirectoryDialog d = new DirectoryDialog(tableView.getSite()
                    .getShell());
            d.setMessage("Select the directory in which"
                    + " the CSV file(s) should be saved.");
            String dir = d.open();
            if (dir == null) {
                return;
            }

            if (action.getId().equals(
                    "de.peerthing.visualization.ExportAllTablesAction")) {
                // Look for all table views...
                for (IViewReference viewRef : tableView.getSite()
                        .getWorkbenchWindow().getActivePage()
                        .getViewReferences()) {
                    IViewPart view = viewRef.getView(false);
                    if (view instanceof TableView) {
                        SimulationRunQueryResult data = ((TableView) view)
                                .getCurrentData();
                        writeCSVFile(data, dir);
                    }
                }
            } else {
                SimulationRunQueryResult data = tableView.getCurrentData();
                writeCSVFile(data, dir);
            }
        }
    }

    /**
     * Writes the data contained in the given parameter to a CSV file in the
     * given directory.
     * 
     * @param data
     *            The data to write to the file
     * @param dir
     *            The directory in which to store the file
     */
    private void writeCSVFile(SimulationRunQueryResult data, String dir) {
        StringBuffer fileContent = new StringBuffer();

        for (int i = 0; i < data.getNumberOfColumns(); i++) {
            String colName = data.getColumnName(i);
            fileContent.append(colName);
            if (i != data.getNumberOfColumns() - 1) {
                fileContent.append(SEPARATOR);
            }
        }
        fileContent.append("\n");

        for (String[] row : data.getData()) {
            for (int i = 0; i < row.length; i++) {
                fileContent.append(row[i]);

                if (i != row.length - 1) {
                    fileContent.append(SEPARATOR);
                }
            }
            fileContent.append("\n");
        }

        try {
            // Write the file to the selected directory...
            FileWriter writer = new FileWriter(dir + "/" + data.getName()
                    + ".csv");
            writer.write(fileContent.toString());
            writer.close();
        } catch (Exception e) {
            MessageDialog
                    .openError(tableView.getSite().getShell(),
                            "Could not export file", "Error message: "
                                    + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Not needed.
     */
    public void selectionChanged(IAction action, ISelection selection) {
    }

}
