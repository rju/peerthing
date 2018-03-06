/*
 * Created on 05.12.2005
 *
 */
package de.peerthing.visualization.view.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JLabel;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.IProgressService;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import de.peerthing.visualization.VisualizationPerspective;
import de.peerthing.visualization.VisualizationPlugin;
import de.peerthing.visualization.querymodel.interfaces.IVisualizationData;
import de.peerthing.visualization.querymodel.interfaces.IVisualizationData.Styles;
import de.peerthing.visualization.simulationpersistence.BasicQueries;
import de.peerthing.visualization.simulationpersistence.DBinterface;
import de.peerthing.visualization.view.selection.SelectionView;
import de.peerthing.visualization.view.table.TableView;

/**
 * View supposed to represent the JFreeCharts
 *
 * @author Gom
 * @author Michael Gottschalk
 *
 */
public class ChartView extends ViewPart implements ISelectionListener,
		SelectionListener {
	private Panel chartPanel;

	private QueryResult currentQueryResult;

	private Composite mainContainer;

	/**
	 * The background color of the swt components - the awt components need to
	 * have the same color.
	 */
	private Color bgColor;

	private Scale fromScale, toScale;

	private Spinner fromSpinner, toSpinner, intervalSpinner;

	private Button applyButton, aggregateRunsButton;

	private IVisualizationData currentVisualizationData;

	public ChartView() {
		VisualizationPlugin.getDefault().setChartView(this);
	}

	public void initLayout(Composite parent) {
		mainContainer = new Composite(parent, SWT.NONE);
		mainContainer
				.setLayout(new org.eclipse.swt.layout.GridLayout(1, false));

		bgColor = new Color(mainContainer.getBackground().getRed(),
				mainContainer.getBackground().getGreen(), mainContainer
						.getBackground().getBlue());

		Composite swtawt = new Composite(mainContainer, SWT.EMBEDDED);
		GridData gd = new GridData(GridData.FILL_BOTH);
		swtawt.setLayoutData(gd);

		Frame swingframe = SWT_AWT.new_Frame(swtawt);
		swingframe.setBackground(bgColor);
		chartPanel = new Panel();
		chartPanel.setBackground(bgColor);
		swingframe.add(chartPanel);

		chartPanel.setLayout(new GridLayout(2, 2));
		setChart(null);
		getViewSite().getPage().addSelectionListener(this);

		/*
		 * Components for the sliders with which the displayed simulation time
		 * can be adjusted:
		 *
		 */
		Composite timeSliderContainer = new Composite(mainContainer, SWT.NONE);
		timeSliderContainer.setLayout(new org.eclipse.swt.layout.GridLayout(8,
				false));
		Label l1 = new Label(timeSliderContainer, SWT.NONE);
		l1.setText("Simulation time to consider:");

		gd = new GridData();
		gd.horizontalIndent = 20;
		l1 = new Label(timeSliderContainer, SWT.NONE);
		l1.setText("From:");
		l1.setLayoutData(gd);

		gd = new GridData();
		gd.widthHint = 150;
		fromScale = new Scale(timeSliderContainer, SWT.HORIZONTAL);
		fromScale.setLayoutData(gd);
		fromSpinner = new Spinner(timeSliderContainer, SWT.BORDER);
		gd = new GridData();
		gd.widthHint = 50;
		fromSpinner.setLayoutData(gd);
		fromScale.addSelectionListener(this);
		fromSpinner.addSelectionListener(this);

		gd = new GridData();
		gd.horizontalIndent = 20;
		l1 = new Label(timeSliderContainer, SWT.NONE);
		l1.setText("To:");
		l1.setLayoutData(gd);

		gd = new GridData();
		gd.widthHint = 150;
		toScale = new Scale(timeSliderContainer, SWT.HORIZONTAL);
		toScale.setLayoutData(gd);
		toSpinner = new Spinner(timeSliderContainer, SWT.BORDER);
		gd = new GridData();
		gd.widthHint = 50;
		toSpinner.setLayoutData(gd);
		toScale.addSelectionListener(this);
		toSpinner.addSelectionListener(this);

		/*
		 * Components for adjusting the interval length for bar charts and for
		 * combining more than one simulation run:
		 *
		 */
		Composite intervalContainer = new Composite(mainContainer, SWT.NONE);
		intervalContainer.setLayout(new org.eclipse.swt.layout.GridLayout(4,
				false));

		l1 = new Label(intervalContainer, SWT.NONE);
		l1.setText("Interval length (for bar charts and aggregation): ");
		gd = new GridData();
		gd.horizontalIndent = 10;
		gd.widthHint = 50;
		intervalSpinner = new Spinner(intervalContainer, SWT.BORDER);
		intervalSpinner.setLayoutData(gd);

		aggregateRunsButton = new Button(intervalContainer, SWT.CHECK);
		aggregateRunsButton.setText("Aggregate runs of each selected database");
		aggregateRunsButton.addSelectionListener(this);
		gd = new GridData();
		gd.horizontalIndent = 30;
		aggregateRunsButton.setLayoutData(gd);

		/*
		 * Update-Button to apply changes in the controls
		 */
		gd = new GridData();
		gd.horizontalIndent = 10;
		applyButton = new Button(intervalContainer, SWT.PUSH);
		applyButton.setText("Update");
		applyButton.setLayoutData(gd);
		applyButton.addSelectionListener(this);
	}

	public void createPartControl(Composite parent) {
		initLayout(parent);
	}

	/**
	 * Listens for selection changes in the chart selection view and updates the
	 * chart view accordingly.
	 */
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// only react on changes in the selection view:
		if (!(part instanceof SelectionView)) {
			return;
		}

		StructuredSelection sselection = (StructuredSelection) selection;

		if (sselection.getFirstElement() instanceof IVisualizationData) {
			IVisualizationData visData = (IVisualizationData) sselection
					.getFirstElement();

			if (currentVisualizationData != visData) {
				currentVisualizationData = visData;

				updateAll();
			}
		}
	}

	/**
	 * Updates the chart view, the tables and sliders.
	 *
	 */
	public void updateAll() {
		List<DBinterface> dbs = VisualizationPlugin.getDefault()
				.getCurrentlySelectedDatabases();
		if (dbs.size() == 0) {
			showError("Select a database first by double clicking on a"
					+ " .simdata file!");
			return;
		}
		if (currentVisualizationData == null) {
			showError("Select a visualization first by double clicking on a"
					+ " .qdef file!");
			return;
		}

		resetTimeSliders();
		updateChartView();
	}

	private void updateChartView() {
		// only update the chart view, if the current perspective
		// is the visualization perspective. Else, there will
		// be errors because of the table views.
		if (!getSite().getPage().getPerspective().getId().equals(
				VisualizationPerspective.ID)) {
			return;
		}

		/*
		 * If no database is selected, don't show anything...
		 */
		if (VisualizationPlugin.getDefault().getCurrentlySelectedDatabases()
				.size() == 0) {
			return;
		}

		JFreeChart chart = null; // chartCache.get(new DBVisDataHash(
		// currentVisualizationData, db));
		if (chart == null) {
			try {
				ExecuteQueryThread thread = new ExecuteQueryThread(fromSpinner
						.getSelection(), toSpinner.getSelection());
				IProgressService progressService = PlatformUI.getWorkbench()
						.getProgressService();
				progressService.busyCursorWhile(thread);

				// If the user aborted the operation, do nothing.
				if (thread.wasAborted()) {
					return;
				}
				currentQueryResult = thread.getQueryResult();

				resetIntervalComponents();

				// Now decide if all runs of a db should be aggregated...
				if (aggregateRunsButton.isEnabled()
						&& aggregateRunsButton.getSelection()) {
					currentQueryResult = ChartComputations.aggregateRuns(
							currentQueryResult, intervalSpinner.getSelection(),
							currentVisualizationData);
				}

				// Show tables with the data
				try {
					updateTableViews(currentQueryResult);
				} catch (OperationCanceledException e) {
					showError("You canceled the operation while inserting data into the table");
					return;
				}

				if (currentVisualizationData.getStyle() != Styles.table) {
					chart = ChartComputations.getJFreeChart(
							currentVisualizationData, currentQueryResult,
							intervalSpinner.getSelection());
				} else {
					showError("This visualization type does not show a chart. The results of the query are only displayed in the tables below.");
				}

			} catch (Exception e) {
				e.printStackTrace();
				showError("Error in DB query: " + e.getCause().getMessage());
			}
		}

		if (chart != null) {
			setChart(chart);
		}
	}

	/**
	 * Executes the currently selected query in a separate thread so that the
	 * operation can be canceled if too much time is needed.
	 *
	 * @author Michael Gottschalk
	 *
	 */
	class ExecuteQueryThread implements IRunnableWithProgress {
		private Thread initThread = null;

		private boolean threadReady = false;

		private boolean aborted = false;

		private QueryResult result = null;

		private RuntimeException exception;

		public ExecuteQueryThread(final long startTime, final long endTime) {
			initThread = new Thread() {
				@Override
				public void run() {
					try {
						result = executeCurrentQuery(startTime, endTime);
					} catch (OperationCanceledException e) {
						aborted = true;
					} catch (RuntimeException e) {
						aborted = true;
						threadReady = true;
						exception = e;
					}
					threadReady = true;
				}
			};
		}

		public void run(IProgressMonitor monitor)
				throws InvocationTargetException, InterruptedException {
			initThread.start();
			monitor.beginTask("Executing the SQL query...",
					IProgressMonitor.UNKNOWN);

			while (!threadReady) {
				if (monitor.isCanceled()) {
					initThread.interrupt();
				}
				Thread.sleep(200);
			}

			if (exception != null) {
				throw exception;
			}
		}

		public boolean wasAborted() {
			return aborted;
		}

		public QueryResult getQueryResult() {
			return result;
		}
	}

	/**
	 * Executes the currently selected query with a restriction to the given
	 * startTime and endTime.
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private QueryResult executeCurrentQuery(long startTime, long endTime) {
		QueryResult res = new QueryResult();

		for (DBinterface db : VisualizationPlugin.getDefault()
				.getCurrentlySelectedDatabases()) {
			int[] runs = new int[db.getSelectedRuns().size()];
			for (int i = 0; i < db.getSelectedRuns().size(); i++) {
				runs[i] = db.getSelectedRuns().get(i);
			}

			DatabaseQueryResult dbRes = ChartComputations.getQueryData(db,
					currentVisualizationData, runs, startTime, endTime);
			res.addDatabaseData(db.getDatabase(), dbRes);

			// Compute min/max values for all
			// included databases...
			if (dbRes.getMinXValue() == null) {
				res.setMinXValue(null);
			} else {
				if (res.getMinXValue() == null

				|| res.getMinXValue() > dbRes.getMinXValue()) {
					res.setMinXValue(dbRes.getMinXValue());
				}
				if (res.getMaxXValue() == null
						|| res.getMaxXValue() < dbRes.getMaxXValue()) {
					res.setMaxXValue(dbRes.getMaxXValue());
				}
			}
		}

		return res;
	}

	/**
	 * Updates the table views with data from the given QueryResult.
	 *
	 * @param res
	 *            The data to display in the tables
	 */
	private void updateTableViews(QueryResult res) throws PartInitException {

		UpdateTableThread thread = new UpdateTableThread(res);
		try {
			new ProgressMonitorDialog(getSite().getShell()).run(true, true,
					thread);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (thread.wasAborted()) {
			throw new OperationCanceledException();
		}

		// close old tables if there are too much now
		int tableCount = 0;
		for (IViewReference viewRef : getSite().getWorkbenchWindow()
				.getActivePage().getViewReferences()) {
			IViewPart view = viewRef.getView(false);
			if (view instanceof TableView) {
				tableCount++;
				if (tableCount > (thread.getTableIdCount() - 1)) {
					view.getSite().getPage().hideView(view);
					view.dispose();
				}
			}
		}
	}

	/**
	 * Updates the given view with the given data. Excutes this in a different
	 * thread. So the user has the possibility of canceling the operation.
	 *
	 * @author Michael Gottschalk
	 *
	 */
	class UpdateTableThread implements IRunnableWithProgress {
		private boolean aborted = false;

		private TableView view;

		private QueryResult res;

		/**
		 * used as the secondary ID of the table views (must start at 1!)
		 */
		private int tableIdCount = 1;

		public UpdateTableThread(final QueryResult res) {
			this.res = res;
		}

		public void run(IProgressMonitor monitor)
				throws InvocationTargetException, InterruptedException {
			monitor.beginTask("Updating Tables", IProgressMonitor.UNKNOWN);

			for (IFile dbFile : res.getIncludedDatabases()) {
				DatabaseQueryResult dbRes = res.getDataForDB(dbFile);

				for (int simRun : dbRes.getIncludedSimulationRuns()) {
					SimulationRunQueryResult data = dbRes.getDataForRun(simRun);

					monitor.beginTask("Updating Table " + tableIdCount, data
							.getData().size());

					mainContainer.getDisplay().syncExec(new Runnable() {
						public void run() {
							try {
								view = (TableView) getSite().getPage()
										.showView(TableView.ID,
												"" + (tableIdCount++),
												IWorkbenchPage.VIEW_CREATE);
							} catch (PartInitException e) {
								throw new RuntimeException(e);
							}
						}
					});

					try {
						view.updateTableData(data, monitor);
					} catch (OperationCanceledException e) {
						aborted = true;
					}
				}
			}
		}

		public int getTableIdCount() {
			return tableIdCount;
		}

		public boolean wasAborted() {
			return aborted;
		}
	}

	/**
	 * Gives the sliders and spinners the correct values for the current
	 * database. Sets the from-Spinner to 0 and the to-Spinner to MaxSimTime if
	 * the Maximum time changed.
	 *
	 */
	private void resetTimeSliders() {
		int maxTime = 0;
		for (DBinterface db : VisualizationPlugin.getDefault()
				.getCurrentlySelectedDatabases()) {
			int max = (int) BasicQueries.getMaxSimulationTime(db);
			if (max > maxTime) {
				maxTime = max;
			}
		}

		if (fromScale.getMaximum() != maxTime) {
			int inc = maxTime / 20;
			int pageInc = maxTime / 10;

			fromScale.setMaximum(maxTime);
			fromScale.setIncrement(inc);
			fromScale.setPageIncrement(pageInc);
			fromScale.setSelection(0);

			fromSpinner.setMaximum(maxTime);
			fromSpinner.setIncrement(inc);
			fromSpinner.setPageIncrement(pageInc);
			fromSpinner.setSelection(0);

			toScale.setMaximum((int) maxTime);
			toScale.setIncrement(inc);
			toScale.setPageIncrement(pageInc);
			toScale.setSelection((int) maxTime);

			toSpinner.setMaximum((int) maxTime);
			toSpinner.setIncrement(inc);
			toSpinner.setPageIncrement(pageInc);
			toSpinner.setSelection((int) maxTime);
		}

	}

	/**
	 * Resets the interval spinner and the aggregation checkbox according to the
	 * current visualization and selection in the checkbox.
	 *
	 */
	private void resetIntervalComponents() {
		if (currentVisualizationData == null || currentQueryResult == null) {
			return;
		}

		// Only allow aggregation if a maximum value could be computed
		// for the current visualization or if the type is box plot
		aggregateRunsButton
				.setEnabled(currentVisualizationData.getStyle() != Styles.multiline
						&& (currentQueryResult.getMaxXValue() != null || currentVisualizationData
								.getStyle() == Styles.boxplot));

		intervalSpinner
				.setEnabled((currentVisualizationData.getStyle() == Styles.bar || aggregateRunsButton
						.getSelection())
						&& currentQueryResult.getMaxXValue() != null);

		if (intervalSpinner.isEnabled()) {
			double range = currentQueryResult.getMaxXValue()
					- currentQueryResult.getMinXValue();
			int oldMax = intervalSpinner.getMaximum();
			int newMax = (int) Math.round(range / 2) + 1;

			if (oldMax != newMax) {
				intervalSpinner.setMaximum(newMax);
				intervalSpinner.setMinimum(1);

				int inc = (int) range / 100;
				int pageInc = (int) range / 10;
				if (inc < 1) {
					inc = 1;
				}
				if (pageInc < 1) {
					pageInc = 1;
				}

				intervalSpinner.setIncrement(inc);
				intervalSpinner.setPageIncrement(inc);

				// The initial value should be the maximum
				intervalSpinner.setSelection(intervalSpinner.getMaximum());
			}
		}
	}

	/**
	 * Displays the given chart. Show an error message instead of the chart if
	 * chart is null.
	 *
	 * @param chart
	 *            The chart to display.
	 */
	private void setChart(JFreeChart chart) {
		// currentChart = chart;

		if (chart == null) {
			showError("No chart selected");
			return;
		}

		chartPanel.removeAll();
		chartPanel.setLayout(new GridLayout(1, 1));
		chart.setBackgroundPaint(bgColor);
		ChartPanel p = new ChartPanel(chart);
		p.setMouseZoomable(true);
		p.setDisplayToolTips(true);
		p.setBackground(bgColor);
		chartPanel.add(p);
		chartPanel.doLayout();
	}

	/**
	 * Shows an error message instead of a chart (if no chart can be displayed)
	 *
	 * @param errorMessage
	 *            The error message
	 */
	private void showError(String errorMessage) {
		chartPanel.removeAll();
		chartPanel.setLayout(new BorderLayout());
		JLabel lbl = new JLabel(errorMessage);
		chartPanel.add(lbl, BorderLayout.CENTER);
		chartPanel.doLayout();
	}

	public void setFocus() {
		fromScale.setFocus();
	}

	public void widgetSelected(SelectionEvent e) {
		Object src = e.getSource();
		if (src == fromScale) {
			fromSpinner.setSelection(fromScale.getSelection());
		} else if (src == toScale) {
			toSpinner.setSelection(toScale.getSelection());
		} else if (src == fromSpinner) {
			fromScale.setSelection(fromSpinner.getSelection());
		} else if (src == toSpinner) {
			toScale.setSelection(toSpinner.getSelection());
		} else if (src == applyButton) {
			updateChartView();
		} else if (src == aggregateRunsButton) {
			resetIntervalComponents();
		}

		if (src == fromScale || src == fromSpinner) {
            if (toSpinner.getSelection() <= fromSpinner.getSelection()) {
                int newValue = fromSpinner.getSelection()+1;
                if (newValue > fromSpinner.getMaximum()) {
                    newValue = fromSpinner.getMaximum();
                }
                toScale.setSelection(newValue);
                toSpinner.setSelection(newValue);
            }
			toScale.setMinimum(fromSpinner.getSelection());
			toSpinner.setMinimum(fromSpinner.getSelection());
		}
	}

	public void widgetDefaultSelected(SelectionEvent e) {
	}

}

class DBVisDataHash {
	private DBinterface db;

	private IVisualizationData visData;

	public DBVisDataHash(IVisualizationData visData, DBinterface db) {
		this.visData = visData;
		this.db = db;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DBVisDataHash) {
			DBVisDataHash dv = (DBVisDataHash) obj;
			return (dv.db == db && dv.visData == visData);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return db.hashCode() + visData.hashCode();
	}

}
