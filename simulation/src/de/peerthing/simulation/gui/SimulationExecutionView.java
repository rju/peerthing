/*
 * Created on 05.12.2005
 *
 */
package de.peerthing.simulation.gui;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.IProgressService;


import de.peerthing.simulation.SimulationPlugin;
import de.peerthing.simulation.execution.SimulationCanceledException;
import de.peerthing.simulation.execution.Simulator;
import de.peerthing.simulation.interfaces.IMessage;
import de.peerthing.simulation.interfaces.INode;
import de.peerthing.simulation.interfaces.ISimulationControl;
import de.peerthing.simulation.interfaces.IXPathObject;
import de.peerthing.simulation.interfaces.SimulationException;
import de.peerthing.workbench.resourcetools.FileTools;
import de.peerthing.workbench.resourcetools.ResourceContentProvider;
import de.peerthing.workbench.resourcetools.ResourceLabelProvider;

/**
 * A view which is shown during a simulation run.
 * 
 * 
 * @author Michael Gottschalk
 * @review Johannes Fischer
 * 
 */
public class SimulationExecutionView extends ViewPart implements
        IHyperlinkListener, SelectionListener {
    public enum SimulationMode {
        time, messages
    }

    private Hyperlink archLink;

    private Hyperlink scenLink;

    private Button runMessagesMultipleButton, runMessagesOnceButton,
            runTimeOnceButton, runTimeMultipleButton;

    private Composite parent;

    private TableViewer nodesTableViewer;

    private TableViewer messagesTableViewer;

    private Text runTimeText;

    private Text runMessagesText;

    private Label simTimeLabel;

    private Label sentMessagesLabel;

    private Label statusLabel;

    private Button logMessagesCheck;

    private boolean logMessages;

    private RunSimulationThread computationThread = null;

    private Text logFileInput;

    public void createPartControl(Composite parent) {
        this.parent = parent;

        FormToolkit toolkit = new FormToolkit(parent.getDisplay());
        ScrolledForm form = toolkit.createScrolledForm(parent);
        form.setText("Simulator");

        TableWrapLayout layout = new TableWrapLayout();
        form.getBody().setLayout(layout);
        layout.numColumns = 2;

        // Status text

        Label statusText = toolkit.createLabel(form.getBody(), "Status:");
        TableWrapData td = new TableWrapData();
        td.colspan = 2;
        statusText.setLayoutData(td);

        statusLabel = toolkit.createLabel(form.getBody(), "not running");
        td = new TableWrapData(TableWrapData.FILL_GRAB);
        td.colspan = 2;
        td.grabHorizontal = true;
        statusLabel.setLayoutData(td);

        // ///////////////
        // 1st Section
        // ///////////////

        Section section = toolkit.createSection(form.getBody(),
                Section.EXPANDED | Section.TITLE_BAR);
        td = new TableWrapData(TableWrapData.FILL_GRAB);
        section.setLayoutData(td);
        section.setText("Configuration");
        toolkit.createCompositeSeparator(section);

        Composite sectionClient = toolkit.createComposite(section);
        sectionClient.setLayout(new GridLayout(2, false));

        // Create controls for this section
        createConfigurationSection(toolkit, sectionClient);

        section.setClient(sectionClient);

        // ///////////////////////
        // 2nd Section: Control
        // ///////////////////////

        section = toolkit.createSection(form.getBody(), Section.EXPANDED
                | Section.TITLE_BAR);
        td = new TableWrapData(TableWrapData.FILL_GRAB);
        // td.colspan = 1;
        section.setLayoutData(td);
        section.setText("Running");
        toolkit.createCompositeSeparator(section);

        sectionClient = toolkit.createComposite(section);

        // Create controls for this section
        createControlSection(toolkit, sectionClient);

        toolkit.paintBordersFor(sectionClient);
        section.setClient(sectionClient);

        // /////////////////////////////////////////////////////
        // 3rd section:
        // messages table
        // ////////////////////////////////////////////////////

        section = toolkit.createSection(form.getBody(), Section.EXPANDED
                | Section.TITLE_BAR);
        section.setText("Messages");
        section.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
        toolkit.createCompositeSeparator(section);

        sectionClient = toolkit.createComposite(section);

        // Create the message table control
        createMessagesTable(toolkit, sectionClient);

        toolkit.paintBordersFor(sectionClient);
        section.setClient(sectionClient);

        // /////////////////////////////////////////////
        // 4th section:
        // nodes table
        // /////////////////////////////////////////////

        section = toolkit.createSection(form.getBody(), Section.EXPANDED
                | Section.TITLE_BAR);
        section.setText("Nodes");
        td = new TableWrapData();
        td.grabHorizontal = true;
        section.setLayoutData(td);
        toolkit.createCompositeSeparator(section);

        sectionClient = toolkit.createComposite(section);

        // create the table control
        createNodesTable(toolkit, sectionClient);

        toolkit.paintBordersFor(sectionClient);
        section.setClient(sectionClient);

    }

    /**
     * Creates the text boxes and labels for the configuration of the
     * simulation: Input and log files.
     * 
     * @param toolkit
     * @param parent
     */
    private void createConfigurationSection(FormToolkit toolkit,
            Composite parent) {
        toolkit.createLabel(parent, "System behaviour file: ");
        archLink = toolkit.createHyperlink(parent, "select a file", SWT.NONE);
        archLink.setData("file", null);
        archLink.setData("SystemBehaviour");
        archLink.addHyperlinkListener(this);

        toolkit.createLabel(parent, "Scenario file: ");
        scenLink = toolkit.createHyperlink(parent, "select a file", SWT.NONE);
        scenLink.setData("file", null);
        scenLink.setData("Scenario");
        scenLink.addHyperlinkListener(this);

        toolkit.createLabel(parent, "Log file: ");
        logFileInput = toolkit.createText(parent, null);
        logFileInput.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        toolkit.paintBordersFor(parent);

    }

    /**
     * Creates the controls for controlling the current simulation run: running
     * a specified time or number of messages and displaying the current time
     * and the number of messages already sent.
     * 
     * @param toolkit
     * @param parent
     */
    private void createControlSection(FormToolkit toolkit, Composite parent) {
        parent.setLayout(new GridLayout(4, false));

        toolkit.createLabel(parent, "Current Simulator time: ");
        simTimeLabel = toolkit.createLabel(parent, "0");
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 3;
        simTimeLabel.setLayoutData(gd);

        toolkit.createLabel(parent, "Number of sent messages: ");
        sentMessagesLabel = toolkit.createLabel(parent, "0");
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 3;
        sentMessagesLabel.setLayoutData(gd);

        Hyperlink runResetLink = toolkit.createHyperlink(parent,
                "Reset simulation", SWT.NONE);
        runResetLink.setData("reset");
        runResetLink.addHyperlinkListener(this);
        gd = new GridData();
        gd.horizontalSpan = 4;
        runResetLink.setLayoutData(gd);

        Hyperlink runTimeLink = toolkit.createHyperlink(parent,
                "Run next time steps: ", SWT.NONE);
        runTimeLink.setData("runTime");
        runTimeLink.addHyperlinkListener(this);
        runTimeText = toolkit.createText(parent, "1000");
        runTimeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        runTimeOnceButton = toolkit.createButton(parent, "", SWT.PUSH);
        runTimeOnceButton.setImage(SimulationPlugin.getImageDescriptor(
                "icons/runOnce.gif").createImage());
        runTimeOnceButton
                .setToolTipText("Start one simulation run and let it run for the "
                        + "specified number of time steps");
        runTimeOnceButton.addSelectionListener(this);
        runTimeMultipleButton = toolkit.createButton(parent, "", SWT.PUSH);
        runTimeMultipleButton.setImage(SimulationPlugin.getImageDescriptor(
                "icons/runMultiple.gif").createImage());
        runTimeMultipleButton
                .setToolTipText("Start multiple simulation runs and let each run for the "
                        + "specified number of time steps");
        runTimeMultipleButton.addSelectionListener(this);

        Hyperlink runMessagesLink = toolkit.createHyperlink(parent,
                "Run until message count: ", SWT.NONE);
        runMessagesLink.addHyperlinkListener(this);
        runMessagesLink.setData("runMessages");
        runMessagesText = toolkit.createText(parent, "10");
        runMessagesText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        runMessagesOnceButton = toolkit.createButton(parent, "", SWT.PUSH);
        runMessagesOnceButton.setImage(SimulationPlugin.getImageDescriptor(
                "icons/runOnce.gif").createImage());
        runMessagesOnceButton
                .setToolTipText("Start one simulation run and let it run for the "
                        + "specified number of messages");
        runMessagesOnceButton.addSelectionListener(this);
        runMessagesMultipleButton = toolkit.createButton(parent, "", SWT.PUSH);
        runMessagesMultipleButton.setImage(SimulationPlugin.getImageDescriptor(
                "icons/runMultiple.gif").createImage());
        runMessagesMultipleButton
                .setToolTipText("Start multiple simulation runs and let each run for the "
                        + "specified number of messages");
        runMessagesMultipleButton.addSelectionListener(this);

        logMessagesCheck = toolkit.createButton(parent,
                "Log messages in the table below", SWT.CHECK);
        logMessagesCheck.setSelection(true);
        gd = new GridData();
        gd.horizontalSpan = 4;
        logMessagesCheck.setLayoutData(gd);
    }

    /**
     * Creates the messages table viewer.
     * 
     * @param toolkit
     * @param parent
     */
    private void createMessagesTable(FormToolkit toolkit, Composite parent) {
        parent.setLayout(new FormLayout());

        Table messagesTable = toolkit.createTable(parent, SWT.NONE);

        // use a form layout to give the table a size...
        FormData fd = new FormData();
        fd.left = new FormAttachment(0, 2);
        fd.top = new FormAttachment(0, 2);
        fd.right = new FormAttachment(100, -2);
        fd.bottom = new FormAttachment(0, 200);
        messagesTable.setLayoutData(fd);

        messagesTable.setLinesVisible(true);
        messagesTable.setHeaderVisible(true);
        String[] titles = { "Sent", "Received", "Type", "From node", "To node" };
        int[] columnLengths = { 65, 65, 140, 70, 70 };

        {
            int i = 0;
            for (String title : titles) {
                TableColumn column = new TableColumn(messagesTable, SWT.NONE);
                column.setText(title);
                column.setWidth(columnLengths[i]);
                i++;
            }
        }

        messagesTableViewer = new TableViewer(messagesTable);
        messagesTableViewer
                .addSelectionChangedListener(new TableSelectionChangedListener());
        messagesTableViewer.setLabelProvider(new NodeLabelProvider());
    }

    /**
     * Creates the nodes table viewer.
     * 
     * @param toolkit
     * @param parent
     */
    private void createNodesTable(FormToolkit toolkit, Composite parent) {
        parent.setLayout(new FormLayout());

        Table nodesTable = toolkit.createTable(parent, SWT.NONE);

        // use a form layout to give the table a size...
        FormData fd = new FormData();
        fd.left = new FormAttachment(0, 2);
        fd.top = new FormAttachment(0, 2);
        fd.right = new FormAttachment(100, -2);
        fd.bottom = new FormAttachment(0, 200);
        nodesTable.setLayoutData(fd);

        nodesTable.setLinesVisible(true);
        nodesTable.setHeaderVisible(true);
        String[] nodeTitles = { "ID", "Type", "Category", "Connection" };
        int[] columnLengths = new int[] { 50, 100, 100, 100 };

        {
            int i = 0;
            for (String title : nodeTitles) {
                TableColumn column = new TableColumn(nodesTable, SWT.NONE);
                column.setText(title);
                column.setWidth(columnLengths[i]);
                column.setData(new Integer(i));
                column
                        .addSelectionListener(new NodeTableColumnSelectionListener());
                i++;
            }
        }

        nodesTableViewer = new TableViewer(nodesTable);
        nodesTableViewer.setLabelProvider(new NodeLabelProvider());
        nodesTableViewer.setSorter(new NodeTableSorter());
        nodesTableViewer.setContentProvider(new NodeTableContentProvider());
        nodesTableViewer.setInput("");
        nodesTableViewer
                .addSelectionChangedListener(new TableSelectionChangedListener());

    }

    /**
     * Listens for Changes in the selection of the two tables. If something new
     * is selected, the debug view is shown and given the selected object.
     * 
     * @author Michael Gottschalk
     * 
     */
    class TableSelectionChangedListener implements ISelectionChangedListener {
        public void selectionChanged(SelectionChangedEvent event) {
            if (event.getSelection() instanceof IStructuredSelection) {
                Object selected = ((IStructuredSelection) event.getSelection())
                        .getFirstElement();
                if (selected instanceof IXPathObject) {
                    try {
                        DebugView debugView = (DebugView) getSite().getPage()
                                .showView(DebugView.ID, null,
                                        IWorkbenchPage.VIEW_CREATE);
                        debugView.setObjectToDebug((IXPathObject) selected);
                    } catch (PartInitException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Listens for clicks on the table columns. If a column is clicked, then the
     * the table is resorted by the values in this column.
     * 
     * @author Michael Gottschalk
     * 
     */
    class NodeTableColumnSelectionListener implements SelectionListener {
        public void widgetSelected(SelectionEvent e) {
            if (e.getSource() instanceof TableColumn) {
                Integer num = (Integer) ((TableColumn) e.getSource()).getData();
                nodesTableViewer.setSorter(new NodeTableSorter(num));
            }
        }

        public void widgetDefaultSelected(SelectionEvent e) {
        }
    }

    /**
     * Content provider for the nodes table. It stores an array of nodes
     * internally and returns the included nodes in getElements. A content
     * provider for the nodes table is necessary for providing a sorting
     * function.
     * 
     * @author Michael Gottschalk
     * 
     */
    class NodeTableContentProvider implements IStructuredContentProvider {
        private ArrayList<INode> nodes = new ArrayList<INode>();

        public Object[] getElements(Object inputElement) {
            return nodes.toArray();
        }

        public void addElement(INode node) {
            nodes.add(node);
        }

        public void clear() {
            nodes.clear();
        }

        public void dispose() {
        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }
    }

    public void setFocus() {
        logFileInput.setFocus();
    }

    public void linkEntered(HyperlinkEvent e) {
    }

    public void linkExited(HyperlinkEvent e) {
    }

    /**
     * Listens for clicks on a hyperlink in the form.
     */
    public void linkActivated(HyperlinkEvent e) {
        Hyperlink link = (Hyperlink) e.getSource();

        if (link.getData().equals("Scenario")
                || link.getData().equals("SystemBehaviour")) {
            IWorkspace workspace = ResourcesPlugin.getWorkspace();
            IWorkspaceRoot myWorkspaceRoot = workspace.getRoot();

            ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
                    parent.getShell(), new ResourceLabelProvider(),
                    new ResourceContentProvider());
            dialog.setInput(myWorkspaceRoot);
            dialog.setTitle("Select a file");
            dialog.open();

            if (dialog.getFirstResult() instanceof IFile) {
                if (link == archLink) {
                    setArchitectureInput((IFile) dialog.getFirstResult());
                } else {
                    setScenarioInput((IFile) dialog.getFirstResult());
                }
            }
        } else if (link.getData().equals("runTime")) {
            runSimulation(SimulationMode.time);
        } else if (link.getData().equals("runMessages")) {
            runSimulation(SimulationMode.messages);
        } else if (link.getData().equals("reset")) {
            resetSimulation();
        }
    }

    public void setArchitectureInput(IFile architectureInput) {
        archLink.setData("file", architectureInput);
        archLink.setText(architectureInput.getName());
        archLink.getParent().layout(true);
    }

    public void setScenarioInput(IFile scenarioInput) {
        scenLink.setData("file", scenarioInput);
        scenLink.setText(scenarioInput.getName());
        scenLink.getParent().layout(true);
    }

    /**
     * Resets the currently running simulation by stopping a possibly running
     * thread, calling endSimulation on the thread and resetting the tables and
     * labels.
     * 
     */
    public void resetSimulation() {
        if (computationThread != null) {
            computationThread.endSimulation();
        }
        computationThread = null;
        simTimeLabel.setText("0");
        statusLabel.setText("not running");
        sentMessagesLabel.setText("0");

        ((NodeTableContentProvider) nodesTableViewer.getContentProvider())
                .clear();
        nodesTableViewer.refresh();
        messagesTableViewer.getTable().removeAll();
    }

    /**
     * Runs the simulation for a specified amount of time or amount of sent
     * messages.
     * 
     * @return true, if the simulation has run successfully until the end of the
     *         simulation, false if the user has canceled or an error occured
     * 
     */
    public boolean runSimulation(SimulationMode mode) {
        IProgressService progressService = PlatformUI.getWorkbench()
                .getProgressService();
        logMessages = logMessagesCheck.getSelection();

        if (computationThread == null) {
            final IFile archFile = (IFile) archLink.getData("file");
            final IFile scenFile = (IFile) scenLink.getData("file");
            IFile logfile = null;
            
            try { 
                logfile = checkSimulationInput(archFile, scenFile);
            } catch (RuntimeException e) {
                return false;
            }

            InitializeSimulationThread initThread = new InitializeSimulationThread(
                    archFile, scenFile, logfile);

            try {
                progressService.busyCursorWhile(initThread);
            } catch (Exception e) {
                MessageDialog
                        .openError(
                                parent.getShell(),
                                "Could not initialize simulation",
                                "The input files are probably not correct. Error message:\n\n"
                                        + e.getCause().getMessage()
                                        + "\n\nSee the console output for more information.");
                return false;
            }

            if (initThread.wasAborted()) {
                computationThread = null;
                resetSimulation();
                return false;
            }

        } else {
            if (computationThread.isSimulationFinished()) {
                MessageDialog.openInformation(parent.getShell(),
                        "Simulation finished",
                        "The simulation is already finished, that means"
                                + " there are no more events to process.");
                return false;
            }
        }

        int timeToRun = 0;
        int messagesToProcess = 0;
        try {
            timeToRun = Integer.parseInt(runTimeText.getText());
            messagesToProcess = Integer.parseInt(runMessagesText.getText());
        } catch (Exception e) {
            MessageDialog.openError(parent.getShell(), "Wrong input",
                    "Please use valid numbers for time steps and message count: "
                            + e.getMessage());
            return false;
        }
        if (timeToRun < 0 || messagesToProcess < 0) {
            MessageDialog
                    .openError(parent.getShell(), "Wrong input",
                            "Please use numbers greater than 0 for time steps and message count!");
            return false;
        }

        computationThread.setSimulationMode(mode);
        computationThread.setTimeToRun(timeToRun);
        computationThread.setMessagesToProcess(messagesToProcess);

        // Run the simulation...
        try {
            progressService.busyCursorWhile(computationThread);
        } catch (SimulationException e) {
            MessageDialog.openError(parent.getShell(), "Error in simulation",
                    "The simulator found a runtime problem with your model: "
                            + e.getCause().getMessage()
                            + ". See the console output for more details.");
            System.out.println(e.getCause().getMessage());
            return false;
        } catch (Exception e) {
            MessageDialog.openError(parent.getShell(), "Error",
                    "There was an error executing the simulation: "
                            + e.getCause().getMessage()
                            + ". See the console output for more details.");
            e.printStackTrace();
            return false;
        }

        return !computationThread.wasAborted();
    }

    class InitializeSimulationThread implements IRunnableWithProgress {
        private Thread initThread = null;

        private boolean threadReady = false;

        private boolean aborted = false;

        private IFile archFile = null;

        private IFile scenFile = null;

        private IFile logFile = null;

        private RuntimeException innerThreadException = null;

        public InitializeSimulationThread(final IFile archFile,
                final IFile scenFile, final IFile logFile) {
            this.archFile = archFile;
            this.scenFile = scenFile;
            this.logFile = logFile;
        }

        class InitThread extends Thread {
            @Override
            public void run() {
                try {
                    initializeSimulationThread(archFile, scenFile, logFile);
                } catch (SimulationCanceledException e) {
                    aborted = true;
                } catch (RuntimeException e) {
                    aborted = true;
                    threadReady = true;
                    innerThreadException = e;
                }
                threadReady = true;
            }
        }

        public void run(IProgressMonitor monitor)
                throws InvocationTargetException, InterruptedException {
            initThread = new InitThread();
            initThread.start();

            while (!threadReady) {
                if (monitor.isCanceled()) {
                    initThread.interrupt();
                }
                Thread.sleep(200);
            }

            if (innerThreadException != null) {
                throw innerThreadException;
            }
        }

        public boolean wasAborted() {
            return aborted;
        }
    }

    /**
     * Checks if the input files exist and if the log file is valid. Returns the
     * log file. Returns null if the console logger should be used instead of a
     * log file. Throws a RuntimeException if something was wrong. Shows a
     * dialog before throwing the exception.
     * 
     * @param archFile
     * @param scenFile
     * @return
     */
    public IFile checkSimulationInput(IFile archFile, IFile scenFile) {
        if (archFile == null || scenFile == null || !archFile.exists()
                || !scenFile.exists()) {
            MessageDialog
                    .openError(parent.getShell(), "Error",
                            "Please choose existing system behaviour and scenario files first!");
            throw new RuntimeException("No scenario or system behaviour file");
        }

        String logFileName = logFileInput.getText();
        IFile log = null;
        if (!logFileName.equals("")) {
            if (FileTools.includesOnlyWhitespace(logFileName)) {
                MessageDialog.openError(parent.getShell(),
                        "Invalid log file name",
                        "Please use a log file name that does not only"
                                + " include whitespace!");
                throw new RuntimeException("Invalid log file");
            }
            if (FileTools.includesInvalidCharacters(logFileName)) {
                MessageDialog.openError(parent.getShell(),
                        "Invalid log file name",
                        "Please use a log file name that includes only"
                                + " ASCII-characters!");
                throw new RuntimeException("Invalid log file");
            }

            // This is important since the database
            // does not automatically append the ending
            if (!logFileName.endsWith(".simdata")) {
                logFileName = logFileName + ".simdata";
            }

            try {
                log = scenFile.getProject().getFile(logFileName);

                // The file must be created first, the
                // database does not do this!

                if (!log.exists()) {
                    ByteArrayInputStream in = new ByteArrayInputStream(
                            new byte[0]);
                    log.create(in, true, null);
                }
            } catch (Exception e) {
                MessageDialog.openError(parent.getShell(),
                        "Invalid log file name", e.getMessage());
                throw new RuntimeException("Invalid log file name");
            }
        } else {
            /* else proceed without logging to a file */
            if (!MessageDialog.openQuestion(parent.getShell(),
                    "No log file selected",
                    "You did not choose a log file. Do you want to continue"
                            + " using the standard output for logging?")) {
                throw new RuntimeException("No log file chosen");
            }
        }

        return log;
    }

    /**
     * Creates a new Simulator and RunSimulationThread. Assumes that no thread
     * is currently running. Takes the files to use from the input fields.
     * Creates a log file if needed. Initializes the simulation and puts the
     * participating nodes into the nodes table.
     * 
     * @return true, if the initialization was done; false if an error occured.
     */
    public void initializeSimulationThread(IFile archFile, IFile scenFile,
            IFile log) {
        // Start a simulation
        ISimulationControl simulation = new Simulator();
        try {
            simulation.initialize(archFile.getLocation().toString(), scenFile
                    .getLocation().toString(), log);
        } catch (SimulationCanceledException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }

        addNodesToTable(simulation.getAllNodes());

        computationThread = new RunSimulationThread(simulation, this);
        simulation.addSimulationListener(computationThread);
    }

    /**
     * Shows the given text as a status text. If statusText is null, then "not
     * running" is displayed. Can also be called from another thread!
     * 
     * @param statusText
     */
    public void setStatus(String statusText) {
        if (statusText == null) {
            statusText = "not running";
        }
        final String showText = statusText;

        parent.getDisplay().asyncExec(new Runnable() {
            public void run() {
                statusLabel.setText(showText);
            }
        });
    }

    /**
     * Sets the simulation time label with the given value. Can also be called
     * from another thread.
     * 
     * @param now
     *            Current simulation time
     * @param ofTime
     *            The max. simulation time
     */
    public void setTimeLabel(final long now) {
        parent.getDisplay().asyncExec(new Runnable() {
            public void run() {
                simTimeLabel.setText("" + now);
            }
        });
    }

    /**
     * Adds the given nodes to the table of nodes. This can be called from
     * another thread.
     * 
     * @param nodes
     */
    public void addNodesToTable(final Collection<INode> nodes) {
        parent.getDisplay().asyncExec(new Runnable() {
            public void run() {
                for (INode node : nodes) {
                    ((NodeTableContentProvider) nodesTableViewer
                            .getContentProvider()).addElement(node);
                }

                nodesTableViewer.refresh();
            }
        });
    }

    /**
     * Adds the given message to the table of messages and increments the
     * message counter and the messages label. This can be called from another
     * thread. If the checkbox "add messages to table" is not checked, then no
     * messages are added to the table and the label is only updated every 1000
     * messages.
     * 
     * @param message
     *            The message to be added
     * @param messageCount
     *            The number of messages already received during the simulation
     *            run
     */
    public void addMessageToTable(final IMessage message, final int messageCount) {
        if (logMessages) {
            parent.getDisplay().asyncExec(new Runnable() {
                public void run() {
                    messagesTableViewer.add(message);
                    sentMessagesLabel.setText("" + messageCount);
                }
            });
        } else if (messageCount % 1000 == 0) {
            updateMessageCountLabel(messageCount);
        }
    }

    /**
     * Updates the label with the message count. Can be called from another
     * thread.
     * 
     */
    public void updateMessageCountLabel(final int messageCount) {
        parent.getDisplay().asyncExec(new Runnable() {
            public void run() {
                sentMessagesLabel.setText("" + messageCount);
            }
        });
    }

    public void widgetSelected(SelectionEvent e) {
        if (e.getSource() == runMessagesOnceButton) {
            runSimulation(SimulationMode.messages);
        } else if (e.getSource() == runTimeOnceButton) {
            runSimulation(SimulationMode.time);
        } else if (e.getSource() == runTimeMultipleButton
                || e.getSource() == runMessagesMultipleButton) {
            if (logFileInput.getText().equals("")) {
                MessageDialog
                        .openError(
                                parent.getShell(),
                                "No log file given",
                                "You did not choose a log file into "
                                        + "which the simulation results should be written. "
                                        + "Without logging the data to a file, running multiple "
                                        + "simulation runs does not make sense.");
                return;
            }
            
            
            if (logMessagesCheck.getSelection()) {
                if (!MessageDialog
                        .openQuestion(
                                parent.getShell(),
                                "Message logging is activated",
                                "You chose to simulate multiple simulation "
                                        + "runs and left the checkbox for logging messages into "
                                        + "the table activated. This does not make much sense since "
                                        + "after each run, the table will be cleared and logging messages "
                                        + "will use extra time and memory. Do you still want "
                                        + "to proceed?")) {
                    return;
                }
            }


            InputDialog input = new InputDialog(parent.getShell(),
                    "Number of runs",
                    "Please enter the number of runs that should be executed",
                    "10", new IInputValidator() {
                        public String isValid(String newText) {
                            try {
                                int val = Integer.valueOf(newText);
                                if (val > 0) {
                                    return null;
                                } else {
                                    return "Please enter a number greater than 0!";
                                }
                            } catch (NumberFormatException e) {
                                return "Please enter a number!";
                            }
                        }

                    });
            if (input.open() == MessageDialog.CANCEL) {
                return;
            }

            int runs = Integer.valueOf(input.getValue());
            System.out.println("Start runs: " + runs);

            for (int run = 0; run < runs; run++) {
                boolean ok = false;

                if (e.getSource() == runMessagesMultipleButton) {
                    ok = runSimulation(SimulationMode.messages);
                } else {
                    ok = runSimulation(SimulationMode.time);
                }
                resetSimulation();

                if (!ok) {
                    // if there was an error, don't
                    // start new runs!
                    break;
                }
            }

        }
    }

    public void widgetDefaultSelected(SelectionEvent e) {
    }

}

/**
 * Provides labels for the nodes table
 * 
 * @author Michael Gottschalk
 * 
 */
class NodeLabelProvider implements ITableLabelProvider {

    public Image getColumnImage(Object element, int columnIndex) {
        return null;
    }

    public String getColumnText(Object element, int columnIndex) {
        if (element instanceof INode) {
            INode node = (INode) element;
            switch (columnIndex) {
            case 0:
                return "" + node.getId();
            case 1:
                return node.getSystemBehaviourName(); // "TYPE NAME MISSING";
            // //
            // node.getTypeName();
            case 2:
                return node.getUserBevahiourName();
            case 3:
                return node.getConnectionCategory().getName();
            }
        } else if (element instanceof IMessage) {
            IMessage message = (IMessage) element;

            switch (columnIndex) {
            case 0:
                return "" + message.getTimeSent();
            case 1:
                return "" + message.getTimeReceived();
            case 2:
                return message.getName();
            case 3:
                return "" + message.getDestination().getRemoteNode().getId();
            case 4:
                return "" + message.getSource().getRemoteNode().getId();
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

/**
 * Sorts the nodes table.
 * 
 * @author Michael Gottschalk
 * 
 */
class NodeTableSorter extends ViewerSorter {
    private int column = 0;

    /**
     * As an argument, the number of the column in the table must be given. The
     * data will be sorted according to this column.
     * 
     */
    public NodeTableSorter(int column) {
        this.column = column;
    }

    public NodeTableSorter() {
    }

    @Override
    public int compare(Viewer viewer, Object e1, Object e2) {
        if (e1 instanceof INode && e1 instanceof INode) {
            INode n1 = (INode) e1;
            INode n2 = (INode) e2;

            if (column == 0) {
                return new Integer(n1.getId()).compareTo(n2.getId());
            } else if (column == 1) {
                return n1.getSystemBehaviourName().compareTo(
                        n2.getSystemBehaviourName());
            } else if (column == 2) {
                return n1.getUserBevahiourName().compareTo(
                        n2.getUserBevahiourName());
            } else {
                return n1.getConnectionCategoryName().compareTo(
                        n2.getConnectionCategoryName());
            }
        }

        return 0;
    }

}
