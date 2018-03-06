package de.peerthing.visualization.view.charts;

import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.OperationCanceledException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.BoxAndWhiskerCalculator;
import org.jfree.data.statistics.BoxAndWhiskerItem;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import de.peerthing.visualization.querymodel.interfaces.IVisualizationData;
import de.peerthing.visualization.querymodel.interfaces.IVisualizationData.Styles;
import de.peerthing.visualization.simulationpersistence.DBinterface;

/**
 * This class provides methods that generate result sets from a query that were
 * further analysed and specially prepared for a given chart type. Data of
 * different simulation runs can be aggregated. JFreeChart objects can be
 * returned that fit a given visualization.
 * 
 * @author Gom
 * @author Michael Gottschalk
 */
public class ChartComputations {

    /**
     * Returns the result of the SQL query belonging to the given parameters.
     * For each given simulation run, the query is executed separately with the
     * String $RUN$ substituted with the current run. The Strings $STARTTIME$
     * and $ENDTIME$ are substituted with the parameters startTime and endTime.
     * 
     * @param db
     *            The database in which to execute the query
     * @param visData
     *            The visualization for which the data must be prepared
     * @param simRunsToConsider
     *            The simulation runs in the database that should be considered
     *            in the query
     * @param startTime
     *            The simulation time that should be substituted with
     *            $STARTTIME$ in the query.
     * @param endTime
     *            The simulation time that should be substituted with $ENDTIME$
     *            in the query.
     * 
     * @return The result of the query
     */
    public static DatabaseQueryResult getQueryData(DBinterface db,
            IVisualizationData visData, int[] simRunsToConsider,
            long startTime, long endTime) throws RuntimeException,
            OperationCanceledException {

        DatabaseQueryResult result = new DatabaseQueryResult(db.getDatabase());

        boolean firstColumnIsXAxis = false;
        if (visData.getStyle() == Styles.bar
                || visData.getStyle() == Styles.line
                || visData.getStyle() == Styles.multiline
                || visData.getStyle() == Styles.scatter) {
            firstColumnIsXAxis = true;
            result.setMaxXValue(Double.MIN_VALUE);
            result.setMinXValue(Double.MAX_VALUE);
        }

        for (int simRun : simRunsToConsider) {
            String query = visData.getQuery().getPreparingQueries() + "; "
                    + visData.getDataQuery();
            // Replace variables in the sql String...
            query = query.replace("$RUN$", "" + simRun);
            query = query.replace("$STARTTIME$", "" + startTime);
            query = query.replace("$ENDTIME$", "" + endTime);

            QueryThread queryThread = new QueryThread(db, query);
            queryThread.start();

            while (queryThread.getResultSet() == null) {
                if (Thread.interrupted()) {
                    queryThread.abort();
                    throw new OperationCanceledException();
                }

                if (queryThread.getException() != null) {
                    throw new RuntimeException(queryThread.getException()
                            .getMessage(), queryThread.getException());
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    queryThread.abort();
                    throw new OperationCanceledException();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            ResultSet rs = queryThread.getResultSet();

            SimulationRunQueryResult data = new SimulationRunQueryResult(
                    simRun, result.getShortDBName());
            try {
                int numCols = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= numCols; i++) {
                    data.addColumnName(rs.getMetaData().getColumnName(i)
                            .toLowerCase());
                }

                if (visData.getStyle() == Styles.line
                        || visData.getStyle() == Styles.scatter) {
                    if (numCols != 2) {
                        throw new RuntimeException(
                                "The query does not return the "
                                        + "needed number of columns (2), but "
                                        + numCols);
                    }
                } else if (visData.getStyle() == Styles.multiline) {
                    if (numCols < 2) {
                        throw new RuntimeException(
                                "The query must at least return "
                                        + "2 columns, but returns only "
                                        + numCols);
                    }
                } else if (visData.getStyle() == Styles.boxplot) {
                    if (numCols != 1) {
                        throw new RuntimeException("The query for a boxplot "
                                + "must return exactly 1 column, but returns "
                                + numCols);
                    }
                }

                while (rs.next()) {
                    if (Thread.interrupted()) {
                        throw new OperationCanceledException();
                    }

                    String[] values = new String[numCols];
                    for (int i = 0; i < numCols; i++) {
                        values[i] = rs.getString(i + 1);
                    }

                    if (firstColumnIsXAxis) {
                        Double value = Double.valueOf(values[0]);
                        if (result.getMinXValue() > value) {
                            result.setMinXValue(value);
                        }
                        if (result.getMaxXValue() < value) {
                            result.setMaxXValue(value);
                        }
                    }

                    data.addRow(values);
                }
            } catch (SQLException e) {
                System.out.println("Here: " + e.getMessage());
                throw new RuntimeException(e.getMessage(), e);
            }

            result.addSimulationRunData(simRun, data);
        }

        return result;

    }

    /**
     * Generates intervals of the data that each include the sum of the y-values
     * in an interval of x-values, or the average of the values if normalized is
     * true. MinXValue and maxXValue must be the minimum/maximum x values that
     * are included in the dataset. The interval width can be given with
     * intervalWidth.
     * 
     * For example, if there is the following dataset: <code>
     *  x-value   y-value
     *  -----------------
     *  1         5
     *  2         6
     *  4         7
     *  8         4
     *  10        20
     * </code>
     * ,the interval width is 4, normalized is false. Then the following array
     * is returned: <code>
     *   18
     *   4
     *   20
     * </code> With normalized=true, the following
     * is the result: <code>
     *   4,5
     *   1
     *   5
     * </code>
     * 
     * @param res
     *            The data to process
     * @param intervalWidth
     *            the width of the intervals
     * @param minXValue
     *            the minimum x value in the dataset
     * @param maxXValue
     *            the maximum x value in the dataset
     * @param normalized
     *            if true, the sum of the data is the value of each interval,
     *            else the sum of the data divided by the interval length.
     * 
     * @return the interval values
     */
    private static double[] generateIntervals(SimulationRunQueryResult res,
            double intervalWidth, double minXValue, double maxXValue,
            boolean normalized) {
        if (intervalWidth <= 0) {
            return null;
        }

        int numIntervals = (int) ((maxXValue - minXValue) / intervalWidth) + 1;
        if (numIntervals < 1) {
            return new double[0];
        }
        double[] intervals = new double[numIntervals];
        for (String[] row : res.getData()) {
            double xValue = Double.parseDouble(row[0]);
            double yValue = Double.parseDouble(row[1]);
            int intervalNum = (int) ((xValue - minXValue) / intervalWidth);
            intervals[intervalNum] += yValue;
        }

        if (normalized) {
            for (int i = 0; i < intervals.length; i++) {
                intervals[i] = (intervals[i] / intervalWidth);
            }
        }

        return intervals;
    }

    /**
     * Aggregates all runs included in the given database. It uses the method
     * generateIntervals internally with the given interval width. The
     * aggregation is different for each type of visualization. For boxplots,
     * the data from all simulation runs is simply put into one dataset since
     * this be be seen as a simple type of aggregation for this chart type.
     * 
     * @param res
     *            The data to aggregate
     * @param intervalWidth
     *            the interval width used for aggregation
     * @param visData
     *            The visualization for which to aggregate the data
     * 
     * @return The aggregated dataset. As the x values, the starts of the
     *         intervals are taken.
     */
    private static SimulationRunQueryResult aggregateRuns(
            DatabaseQueryResult res, double intervalWidth,
            IVisualizationData visData) {
        SimulationRunQueryResult newRes = new SimulationRunQueryResult(-1, res
                .getShortDBName());
        int firstRun = res.getIncludedSimulationRuns().get(0);
        int numCols = res.getDataForRun(firstRun).getNumberOfColumns();
        for (int i = 0; i < numCols; i++) {
            newRes.addColumnName(res.getDataForRun(firstRun).getColumnName(i));
        }

        double[] allData = null;
        int runCount = 0;
        for (int runNumber : res.getIncludedSimulationRuns()) {
            SimulationRunQueryResult simRes = res.getDataForRun(runNumber);
            if (visData.getStyle() == Styles.boxplot) {
                // For a boxplot, simply add all data of the
                // simulation runs to one new simulation run.
                newRes.addList(simRes.getData());
            } else {
                double[] intervalData = generateIntervals(simRes,
                        intervalWidth, res.getMinXValue(), res.getMaxXValue(),
                        true);
                if (allData == null) {
                    allData = intervalData;
                } else {
                    for (int i = 0; i < allData.length; i++) {
                        allData[i] += intervalData[i];
                    }
                }
            }
            runCount++;
        }

        if (visData.getStyle() != Styles.boxplot) {
            double intervalStart = res.getMinXValue();
            if (allData != null) {
                for (double value : allData) {
                    newRes.addRow(new String[] { "" + intervalStart,
                            "" + (value / runCount) });
                    intervalStart += intervalWidth;
                }
            }
        }

        return newRes;
    }

    /**
     * Aggregates the runs of all given databases. Returns a new QueryResult
     * with all databases as before, but with only one simulation run per
     * database, which includes the aggregation of all other runs. Uses the
     * method aggregateRuns(DatabaseQueryResult, ...) internally.
     * 
     * @param res
     *            The data in which to aggregate runs
     * @param intervalWidth
     *            The interval width used for aggregation
     * @param visData
     *            the visualization for which to aggregate the data
     * @return
     */
    public static QueryResult aggregateRuns(QueryResult res,
            double intervalWidth, IVisualizationData visData) {
        QueryResult newRes = new QueryResult();
        for (IFile db : res.getIncludedDatabases()) {
            DatabaseQueryResult dbRes = res.getDataForDB(db);
            SimulationRunQueryResult simRes = aggregateRuns(dbRes,
                    intervalWidth, visData);
            DatabaseQueryResult newDBRes = new DatabaseQueryResult(db);
            newDBRes.setMaxXValue(dbRes.getMaxXValue());
            newDBRes.setMinXValue(dbRes.getMinXValue());
            newDBRes.addSimulationRunData(-1, simRes);
            newRes.addDatabaseData(db, newDBRes);
        }
        return newRes;
    }

    /**
     * Returns a chart for the given parameters.
     * 
     * @param visData
     * @param queryData
     * @return
     */
    public static JFreeChart getJFreeChart(IVisualizationData visData,
            QueryResult queryData, double intervalWidth) {

        XYSeriesCollection data = new XYSeriesCollection();
        DefaultBoxAndWhiskerCategoryDataset boxAndWhiskerNumbers = new DefaultBoxAndWhiskerCategoryDataset();
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        for (IFile dbname : queryData.getIncludedDatabases()) {
            DatabaseQueryResult dbData = queryData.getDataForDB(dbname);
            String dbTitle = dbData.getShortDBName();
            for (int run : dbData.getIncludedSimulationRuns()) {
                if (visData.getStyle() == Styles.boxplot) {
                    // For boxplots, only one column is
                    // needed.

                    List<Double> numbers = new ArrayList<Double>();
                    for (String[] row : dbData.getDataForRun(run).getData()) {
                        numbers.add(Double.parseDouble(row[0]));
                    }
                    BoxAndWhiskerItem item = BoxAndWhiskerCalculator
                            .calculateBoxAndWhiskerStatistics(numbers);

                    // only add the item if min < max, else there will
                    // be an error...
                    if (item.getMinOutlier().doubleValue() < item
                            .getMaxOutlier().doubleValue()) {
                        boxAndWhiskerNumbers.add(item, "", dbData
                                .getDataForRun(run).getName());
                    }
                } else if (visData.getStyle() == Styles.multiline) {
                    // For multiline diagrams, create one dataset
                    // per column, excluding the first column.

                    SimulationRunQueryResult res = dbData.getDataForRun(run);
                    for (int i = 1; i < res.getNumberOfColumns(); i++) {
                        XYSeries series = new XYSeries(dbTitle + ", Run " + run
                                + ": " + res.getColumnName(i));

                        for (String[] row : dbData.getDataForRun(run).getData()) {
                            series.add(Double.parseDouble(row[0]), Double
                                    .parseDouble(row[i]));
                        }

                        data.addSeries(series);
                    }
                } else if (visData.getStyle() == Styles.bar) {
                    SimulationRunQueryResult srqr = dbData.getDataForRun(run);
                    double[] intervals = generateIntervals(srqr, intervalWidth,
                            dbData.getMinXValue(), dbData.getMaxXValue(),
                            visData.isNormalized());
                    for (int i = 0; i < intervals.length; i++) {
                        double intervalStart = dbData.getMinXValue()
                                + (i * intervalWidth);
                        double intervalEnd = intervalStart + intervalWidth;
                        barDataset.addValue(intervals[i], srqr.getName(), ""
                                + intervalStart + " - " + intervalEnd);
                    }

                } else {
                    XYSeries series = new XYSeries(dbData.getDataForRun(run)
                            .getName(), false, true);

                    for (String[] row : dbData.getDataForRun(run).getData()) {
                        series.add(Double.parseDouble(row[0]), Double
                                .parseDouble(row[1]));
                    }

                    data.addSeries(series);
                }
            }
        }

        JFreeChart chart = null;
        if (visData.getStyle() == Styles.boxplot) {
            return createBoxAndWhiskerChart(visData, boxAndWhiskerNumbers);
        } else if (visData.getStyle() == Styles.scatter) {
            chart = ChartFactory.createScatterPlot(
                    visData.getQuery().getName(), visData.getXAxisLabel(),
                    visData.getYAxisLabel(), data, PlotOrientation.VERTICAL,
                    true, true, false);
        } else if (visData.getStyle() == Styles.bar) {
            chart = createBarChart(barDataset, visData, intervalWidth);
        } else {
            chart = ChartFactory.createXYLineChart(
                    visData.getQuery().getName(), visData.getXAxisLabel(),
                    visData.getYAxisLabel(), data, PlotOrientation.VERTICAL,
                    true, true, false);
            NumberAxis xAxis = new NumberAxis(visData.getXAxisLabel());
            xAxis.setAutoRangeIncludesZero(false);
            NumberAxis yAxis = new NumberAxis(visData.getYAxisLabel());
            yAxis.setAutoRangeIncludesZero(false);
            chart.getXYPlot().setDomainAxis(xAxis);
            chart.getXYPlot().setRangeAxis(yAxis);
        }

        return chart;
    }

    /**
     * Creates a box and whisker chart with the given parameters.
     * 
     * @param visData
     * @param queryData
     * @param boxAndWhiskerNumbers
     * @return
     */
    private static JFreeChart createBoxAndWhiskerChart(
            IVisualizationData visData,
            DefaultBoxAndWhiskerCategoryDataset dataset) {
        CategoryAxis xAxis = new CategoryAxis("Simulation run");
        NumberAxis yAxis = new NumberAxis(visData.getYAxisLabel());
        yAxis.setAutoRangeIncludesZero(false);
        MyBoxAndWhiskerRenderer renderer = new MyBoxAndWhiskerRenderer();
        renderer.setFillBox(true);
        // renderer.setItemMargin(0.8);
        // renderer.setToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
        plot.setOrientation(PlotOrientation.VERTICAL);

        return new JFreeChart(visData.getQuery().getName(), new Font(
                "SansSerif", Font.BOLD, 14), plot, false);
    }

    /**
     * Creates a bar chart for the given parameters.
     * 
     * @param dataset
     * @param visData
     * @param intervalWidth
     * @return
     */
    private static JFreeChart createBarChart(DefaultCategoryDataset dataset,
            IVisualizationData visData, double intervalWidth) {

        JFreeChart chart = ChartFactory.createBarChart(visData.getQuery()
                .getName(), visData.getXAxisLabel(), visData.getYAxisLabel(),
                dataset, PlotOrientation.VERTICAL, true, true, false);

        return chart;
    }

}

/**
 * Executed a given query in a separate thread. The query can be aborted with
 * the abort function.
 * 
 * @author Michael Gottschalk
 * 
 */
class QueryThread extends Thread {
    private ResultSet rs = null;

    private String query;

    private DBinterface db;

    private boolean aborted = false;

    /**
     * If an exception occured during the runtime of this thread, it is saved in
     * this attribute.
     */
    private Exception exception = null;

    public QueryThread(DBinterface db, String query) {
        this.db = db;
        this.query = query;
    }

    /**
     * Aborts the query (stops the thread). All SQL exceptions that are thrown
     * after the abortion will be discarded.
     * 
     */
    public void abort() {
        aborted = true;
    }

    @Override
    public void run() {
        try {
            rs = db.dbQuery(query);
        } catch (Exception e) {
            if (!aborted) {
                exception = e;
            }
        }
    }

    public ResultSet getResultSet() {
        return rs;
    }

    /**
     * Returns the exception that occured during the runtime of this thread or
     * null, if none occured.
     * 
     * @return
     */
    public Exception getException() {
        return exception;
    }
}