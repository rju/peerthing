package de.peerthing.actioncontributor.actions;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import de.peerthing.simulation.interfaces.*;

public class LogAction implements IActionExecutor {
	
	private void printObject(Object obj) {
		String label[] = obj.getClass().getName().split("\\.");
		System.out.print("<" + label[label.length-1] + ">");
		if (obj instanceof IXPathAttribute)
			this.printObject(((IXPathAttribute)obj).getValue());
		else if (obj instanceof IXPathContainer) {
			IXPathContainer container = (IXPathContainer)obj;
			if ((container.getContent()!=null) && ((container.getContent().length()>0)))
				this.printObject(container.getContent());
			else
				this.printList(container.getChildAxis());
		} else if (obj instanceof IResourceDefinition)
			this.printObject("res id " + ((IResourceDefinition)obj).getId());
		else if (obj instanceof INode)
			this.printObject("node id " + ((INode)obj).getId());
		else if (obj instanceof ITask)
			this.printObject("task id " + ((ITask)obj).getId());
		else if (obj instanceof List)
			this.printList((List)obj);
		else if (obj instanceof String)
			System.out.print((String)obj);
		else if (obj instanceof Integer)
			System.out.print((Integer)obj);
		else if (obj instanceof Double)
			System.out.print((Double)obj);
		else if (obj!=null)
			this.printObject(obj.toString());
		else
			System.out.print("<null>");
	}
	
	private void printList(Collection list) {
		int i=1;
		System.out.print("[");
		for(Object obj : list) { 
			printObject(obj);
			if (i < list.size())
				System.out.print(",");
			i++;
		}
		System.out.print("]");
	}
	
	public Object executeAction(IActionSimulator simulator, INode contextNode, ISystemTask task,
			long time, IDataStorage dataStorage, Map<String, Object> parameters) {
		System.out.println("Log [" + contextNode.getId() + ":" + task.getId() + "] at " + time);
		
		for(String key : parameters.keySet()) {
			System.out.print ("  " + key + " = ");
			printObject(parameters.get(key));
			System.out.println("");
		}
		return 0;
	}

	public String executionError() {
		return null;
	}

	public long getExecutionTime() {
		return 0;
	}

}
