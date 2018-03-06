/*
 * Created on 01.02.2006
 * Changed on 22.06.2006
 */

package de.peerthing.visualization.simulationpersistence;

import org.eclipse.core.resources.IFile;

import de.peerthing.simulation.interfaces.ILogger;

/**
 * Implements an ILogger which logs to console. 
 * Used for debugging purposes.
 *
 * @author mg
 * @author prefec2
 *
 */

public class ConsoleLogger implements ILogger {
	
	private int messageId = 0;
	
	private IFile name;
	
	/*
	 *  (non-Javadoc)
	 * @see de.peerThing.simulation.interfaces.ILogger#logSessionInformation(long, long, long)
	 */
	public void addSessionInformation(long sessionId, long startTime,
			long endTime) {
		System.out.println("LOG: session: " + sessionId + 
				" start time: " + startTime +
				" end time: " + endTime);
	}

	public void changeSessionEndTime(long sessionId, long endTime) {
		System.out.println("LOG: session change " + sessionId + " new end time: " + endTime);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see de.peerthing.visualization.simulationpersistence.ILogger#addMessage(int, int, int, int, long, long, java.lang.String)
	 */
	public void addMessage(int sourceId, int destinationId, int sessionId, int size, long timeSent, long timeReceived, String name, boolean success) {
		String s;
		if (success)
			s=" success";
		else
			s=" failed"; 
		System.out.println("LOG: message: " + this.messageId + " name: " + name + 
				" sender: " + sourceId + " time sent: " + timeSent + 
				" receiver: " + destinationId + " time received: " + timeReceived + " size: " + size + s);
		this.messageId++;
	}

	/*
	 *  (non-Javadoc)
	 * @see de.peerthing.visualization.simulationpersistence.ILogger#addResource(int, int, int, int, long, int)
	 */
	public void addResource(int nodeId, int resourceId, int startSegment, int endSegment, long time, int quality) {
		System.out.println("LOG: State of a resource has changed: " + resourceId + 
				" on node "	+ nodeId + " for segment [" +
				startSegment + ":" + endSegment + "] to " + quality);
	}

	/*
	 *  (non-Javadoc)
	 * @see de.peerthing.visualization.simulationpersistence.ILogger#addNodeStateChange(int, long, int)
	 */
	public void addNodeStateChange(int nodeId, long time, int state) {
		System.out.println("LOG: node state changed: node: " + nodeId + " time:  " + time
				+ " state: " + state);
	}

	/*
	 *  (non-Javadoc)
	 * @see de.peerthing.visualization.simulationpersistence.ILogger#debug(java.lang.String)
	 */
	public void debug(String s) {
		System.out.println("Debug: "+s);
	}

	/*
	 *  (non-Javadoc)
	 * @see de.peerthing.visualization.simulationpersistence.ILogger#startLog(java.lang.String)
	 */
	public void startLog(IFile name) {
		System.out.println("LOG: start: "+name);
		this.name = name;
	}

	/*
	 *  (non-Javadoc)
	 * @see de.peerthing.visualization.simulationpersistence.ILogger#endLog()
	 */
	public void endLog() {
		System.out.println("LOG: end: "+this.name);
	}

    public void addResource(int resourceId, int sizeInBytes) {
    	System.out.println("LOG: add resource: " + resourceId + " size " + sizeInBytes);
    }

    public void addResourceChange(int nodeId, int resourceId, double fractionAvailable, long time, int quality) {
    	System.out.println("LOG: resource change: node: " + nodeId + " resource: " + resourceId + " available: " + fractionAvailable + " time: " + time + " quality:" + quality);
    }

    public void addNodeInformation(int nodeId, long uploadSpeed, long downloadSpeed, long uploadDelay, long downloadDelay, String categoryName, String nodeType) {
    	System.out.println("LOG: add node: node: " + nodeId + " type: " + nodeType + " category: " + categoryName +
				" link speed: " + downloadSpeed + "/" + uploadSpeed +
				" link delay: " + downloadDelay + "/" + uploadDelay);
    }

    public void addUserLogInformation(int nodeId, long time, String name, String value) {
        System.out.println("LOG: user log was called with: nodeId="+ nodeId +
                ", time="+ time + ", name="+ name+ ", value="+ value);
    }

    public void discardLog() {
        System.out.println("Simulation Run discarded.");
    }
    
}
