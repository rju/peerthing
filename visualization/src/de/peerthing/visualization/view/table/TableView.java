/*
 * Created on 05.12.2005
 *
 */
package de.peerthing.visualization.view.table;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;

import de.peerthing.visualization.VisualizationPlugin;
import de.peerthing.visualization.view.charts.SimulationRunQueryResult;

/**
 * The table view shows a table with the results of a simulation run.
 * 
 * @author gom
 * @author Michael Gottschalk
 * 
 */
public class TableView extends ViewPart implements
        org.eclipse.ui.ISelectionListener {
    private TableViewer tableViewer;

    private Table datatable;

    private SimulationRunQueryResult currentData;

    /**
     * The ID of this view that was used for the registration the view at the
     * workbench.
     * 
     */
    public static final String ID = "de.peerthing.visualization.TableView";

    /**
     * Creates the table viewer.
     */
    public void createPartControl(Composite parent) {
        datatable = new Table(parent, SWT.BORDER);

        datatable.setLinesVisible(true);
        datatable.setHeaderVisible(true);

        parent.setLayout(new FillLayout());
        tableViewer = new TableViewer(datatable);
        tableViewer.setLabelProvider(new SimRunTableLabelProvider());
    }

    /**
     * Updates the table with the data given in res. Can also be called from a
     * different thread.
     * 
     * @param res
     *            The data to show in the table.
     * @param monitor
     *            With the monitor, this operation can be canceled. Furthermore,
     *            progress is indicated to the monitor object. Can also be null,
     *            then nothing is done with it.
     */
    public void updateTableData(final SimulationRunQueryResult res,
            final IProgressMonitor monitor) {

        getSite().getShell().getDisplay().syncExec(new Runnable() {
            public void run() {
                if (currentData != null) {
                    datatable.removeAll();
                }
                currentData = res;

                int columnCount = res.getNumberOfColumns();

                for (TableColumn column : datatable.getColumns()) {
                    column.dispose();
                }

                for (int i = 0; i < columnCount; i++) {
                    TableColumn column = new TableColumn(datatable, SWT.NONE);
                    column.setText(res.getColumnName(i));
                    column.pack();
                }

                setPartName(res.getName());
            }
        });

        if (!VisualizationPlugin.getDefault().isShowTables()) {
            getSite().getShell().getDisplay().syncExec(new Runnable() {
                public void run() {
                    if (datatable.getColumnCount() > 0) {
                        datatable.getColumns()[0].setWidth(400);
                        tableViewer
                                .add((Object) new String[] { "Display of table data is disabled!" });
                    }
                }

            });

            return;
        }

        /**
         * Always put 1000 entries at once into the table. If we only take one
         * at once, it is too slow. If we out all in at once, the user can't
         * interrupt and does not see progress
         */
        for (int lowerLimit = 0; lowerLimit < res.getData().size(); lowerLimit += 1000) {
            int upperLimit = lowerLimit + 1000;
            if (upperLimit > res.getData().size()) {
                upperLimit = res.getData().size();
            }

            if (monitor != null) {
                if (monitor.isCanceled()) {
                    throw new OperationCanceledException();
                }
                monitor.worked(upperLimit - lowerLimit);
            }

            final int u = upperLimit;
            final int l = lowerLimit;

            getSite().getShell().getDisplay().syncExec(new Runnable() {
                public void run() {
                    for (int i = l; i < u; i++) {
                        tableViewer.add((Object) res.getData().get(i));
                    }
                }
            });
        }
    }

    /**
     * Not used.
     */
    public void selectionChanged(org.eclipse.ui.IWorkbenchPart part,
            org.eclipse.jface.viewers.ISelection selection) {
    }

    /**
     * Returns the data that is currently shown in the table.
     * 
     * @return
     */
    public SimulationRunQueryResult getCurrentData() {
        return currentData;
    }

    /**
     * Sets the focus to the table control.
     */
    public void setFocus() {
        datatable.setFocus();
    }

    /**
     * Label provider for the table.
     * 
     * @author Michael Gottschalk
     */
    class SimRunTableLabelProvider implements ITableLabelProvider {
        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }

        public String getColumnText(Object element, int columnIndex) {
            if (element instanceof String[]) {
                if (columnIndex < ((String[]) element).length) {
                    return ((String[]) element)[columnIndex];
                } else {
                    return "";
                }
            }

            return null;
        }

        public void addListener(ILabelProviderListener listener) {
        }

        public void dispose() {
        }

        public boolean isLabelProperty(Object element, String property) {
            return false;
        }

        public void removeListener(ILabelProviderListener listener) {
        }

    }

}
