/*
 * Created on 12.03.2006
 *
 */
package de.peerthing.simulation.gui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import de.peerthing.simulation.gui.SimulationExecutionView.SimulationMode;
import de.peerthing.simulation.interfaces.IMessage;
import de.peerthing.simulation.interfaces.IListener;
import de.peerthing.simulation.interfaces.ISimulationControl;

/**
 * This class implements a Thread that runs the simulation (in steps) specified
 * in the simulation view.
 * 
 * @author Michael Gottschalk
 * @review Johannes Fischer
 * 
 */
public class RunSimulationThread implements IRunnableWithProgress, IListener {

	private int timeToRun;

	private int messagesToProcess;

	private ISimulationControl simulation;

	private SimulationExecutionView view;

	private SimulationMode simulationMode;
    
    /**
     * true, if the user aborted the execution
     */
    private boolean aborted;

    /**
     * Number of messages processed in the current invocation
     * of the run method
     */
	private int messageCount = 0;
    
    /**
     * Number of messages processed during the whole lifetime
     * of this thread.
     */
    private int totalMessageCount = 0;

	boolean simulationFinished = false;
	
	private IProgressMonitor currentProgressMonitor = null;

	public RunSimulationThread(ISimulationControl simulation,
			SimulationExecutionView view) {
		this.simulation = simulation;
		this.view = view;
	}

	public void setSimulationMode(SimulationMode mode) {
		this.simulationMode = mode;
	}

	public void setTimeToRun(int timeToRun) {
		this.timeToRun = timeToRun;
	}

	public void setMessagesToProcess(int messages) {
		this.messagesToProcess = messages;
	}

	public ISimulationControl getSimulation() {
		return this.simulation;
	}

	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		if (simulation != null) {
			currentProgressMonitor = monitor;
			aborted = false;
			
            messageCount = 0;
			view.setStatus("running...");

			// Run the simulation
			long endTime = simulation.getSimulationTime() + this.timeToRun;
			if (simulationMode == SimulationMode.messages) {
				monitor.beginTask("Simulating...", messagesToProcess);
			} else {
				monitor.beginTask("Simulating...", timeToRun);
			}

			boolean noMoreEvents = false;
			long numEvents = 0;
			while (true) {
				if (!simulation.step()) {
					noMoreEvents = true;
					break;
				}

				if (simulationMode == SimulationMode.messages) {
					if (messageCount >= messagesToProcess) {
						break;
					}
				} else {
					if (simulation.getSimulationTime() >= endTime) {
						break;
					}
				}

				if (monitor.isCanceled()) {
                    aborted = true;
					break;
				}

				numEvents++;
			}

			if (noMoreEvents) {
				endSimulation();
				view.setStatus("Simulation finished");
			} else {
				displayTime(simulation.getSimulationTime());
			}

            totalMessageCount += messageCount;
            view.updateMessageCountLabel(totalMessageCount);
			currentProgressMonitor = null;
		}
	}

	/**
	 * Stops the simulation (the simulation can't be resumed after calling this
	 * function!)
	 * 
	 */
	public void endSimulation() {
		simulation.end();
		simulationFinished = true;
	}

	/**
	 * Returns if the simulation is finished or not. A simulation is finished if
	 * there were no more events to process.
	 * 
	 * @return
	 */
	public boolean isSimulationFinished() {
		return simulationFinished;
	}

	public void messageReceived(final IMessage message) {
		if (currentProgressMonitor != null
				&& simulationMode == SimulationMode.messages) {
			currentProgressMonitor.worked(1);
		}

		view.addMessageToTable(message, totalMessageCount + messageCount);
		messageCount++;
	}

	private void displayTime(long now) {
		view.setStatus("Elapsed time: " + now);
		view.setTimeLabel(now);
	}

	public void progress(long now) {
		if (currentProgressMonitor != null
				&& simulationMode == SimulationMode.time) {
			currentProgressMonitor.worked(100);
		}

		displayTime(now);
	}
    
    /**
     * Returns true if the user aborted the execution.
     * 
     * @return
     */
    public boolean wasAborted() {
       return aborted; 
    }
}
