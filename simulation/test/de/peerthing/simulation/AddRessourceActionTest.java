package de.peerthing.simulation;

import java.util.Hashtable;

import de.peerthing.simulation.data.Document;
import de.peerthing.simulation.execution.Simulator;
import de.peerthing.simulation.interfaces.IActionExecutor;
import de.peerthing.simulation.interfaces.IEvaluate;
import de.peerthing.simulation.interfaces.INode;
import junit.framework.TestCase;

public class AddRessourceActionTest extends TestCase{
	
	Simulator sim;
	Document doc;
	INode node;
	IEvaluate evaluateNode;
	INode server;
	TestAction action;
	TestParameter param;
	Hashtable<String, Object> paramList;
	
	
	public void setUp() {
		sim = new Simulator();
		
		
		System.out.println(System.getProperty("user.dir"));
		sim.initialize(
						"peerthing/p2p/trunk/de.peerthing.simulation/test/de/peerthing/simulation/simple.arch",
						"peerthing/p2p/trunk/de.peerthing.simulation/examples/simple.scen",
						null);

		for (INode n : sim.getAllNodes()) {
			if (n.getSystemBehaviourName().equals("server"))
				server = n;
			if(n.getSystemBehaviourName().equals("peer")){
				node = (INode)n;
				evaluateNode = (IEvaluate)n;
			}
				
		}
		
		
		
		action = new TestAction();
		action.setName("addResourceAction");
		
		param = new TestParameter();
		
		param.setName("resource");
		
		
		
		
	}

	public void testAddResourceAction(){
		//make sure node contrains no resources
		assertEquals(48.0, (Double)(evaluateNode.evaluate("count(/simulation/node[1]/resource)")));
		IActionExecutor actionHandle = sim.getActionExecutors().get(action
                .getName());

        if (actionHandle != null) {
            Object result = actionHandle.executeAction(sim, node, null, 0l, sim.getDataStorage(), paramList);
        }
	}

}
