package de.peerthing.simulation;

import java.util.ArrayList;

import de.peerthing.simulation.data.Document;
import de.peerthing.simulation.data.Parameter;
import de.peerthing.simulation.data.XPathContainer;
import de.peerthing.simulation.execution.Simulator;
import de.peerthing.simulation.interfaces.INode;
import junit.framework.TestCase;

public class XPathContainerTest extends TestCase{
	
	Simulator sim;
	Document doc;
	INode node;
	INode server;
	XPathContainer container;

	public void setUp() {
		sim = new Simulator();

		System.out.println(System.getProperty("user.dir"));
		sim.initialize(
						"trunk/de.peerthing.simulation/test/de/peerthing/simulation/simple.arch",
						"trunk/de.peerthing.simulation/examples/simple.scen",
						null);

		for (INode n : sim.getAllNodes()) {
			if (n.getSystemBehaviourName().equals("server"))
				server = n;
			if(n.getSystemBehaviourName().equals("peer"))
				node = (INode)n;
		}
		
		container = (XPathContainer)node.getVariable("knownPeer");
	}
	
	public void testAddElement(){
		Parameter param = new Parameter("param1");
		
		assertTrue(container.addElement(param));
		assertEquals(container, param.getParent());		
		
	}
	
	public void testAddAllElement(){
		ArrayList list = new ArrayList();
		Parameter param1, param2, param3;
		param1 = new Parameter("param1");
		param2 = new Parameter("param2");
		param3 = new Parameter("param3");
		
		assertEquals(0, container.getChildAxis().size());
		
		list.add(param1);
		list.add(param2);
		list.add(param3);
		
		assertTrue(container.addAllElement(list));
		assertEquals(container, param1.getParent());
		assertEquals(container, param2.getParent());
		assertEquals(container, param3.getParent());
		
		assertEquals(3, container.getChildAxis().size());
	}
	
	public void testRemoveElement(){
		Parameter p = new Parameter("p");
		assertEquals(0, container.getChildAxis().size());
		
		container.addElement(p);
		
		assertEquals(1, container.getChildAxis().size());
		
		container.removeElement(p);
		
		assertEquals(0, container.getChildAxis().size());
	}
	
	public void testRemoveAllElement(){
		ArrayList list = new ArrayList();
		Parameter param1, param2, param3;
		param1 = new Parameter("param1");
		param2 = new Parameter("param2");
		param3 = new Parameter("param3");
		
		list.add(param1);
		list.add(param2);
		list.add(param3);
		
		assertEquals(0, container.getChildAxis().size());
		
		container.addAllElement(list);
		
		assertEquals(3, container.getChildAxis().size());
		
		container.removeAllElement();
		
		assertEquals(0, container.getChildAxis().size());
	}
	
	public void testGetDescendantAxisList(){
		ArrayList list = new ArrayList();
		Parameter param1, param2, param3;
		param1 = new Parameter("param1");
		param2 = new Parameter("param2");
		param3 = new Parameter("param3");
		
//		assertTrue(param1.addElement(param4));
//		param2.addElement(param5);
		
		list.add(param1);
		list.add(param2);
		list.add(param3);
		
		container.addAllElement(list);
		
		//there should be 3 elements in the descendantAxis list now.
		assertEquals(3, container.getDescendantAxisList().size());
	}

}
