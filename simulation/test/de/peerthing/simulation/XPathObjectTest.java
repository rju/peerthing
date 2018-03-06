package de.peerthing.simulation;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.simulation.data.Document;
import de.peerthing.simulation.data.Parameter;
import de.peerthing.simulation.data.Segment;
import de.peerthing.simulation.data.Variable;
import de.peerthing.simulation.data.XPathAttribute;
import de.peerthing.simulation.data.XPathContainer;
import de.peerthing.simulation.data.XPathObject;
import de.peerthing.simulation.execution.Simulator;
import de.peerthing.simulation.interfaces.IEvaluate;
import de.peerthing.simulation.interfaces.INode;
import junit.framework.TestCase;

public class XPathObjectTest extends TestCase{
	
	Simulator sim;
	Document doc;
	INode node;
	INode node2;
	INode server;
	XPathObject object;

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
		
		object = (XPathContainer)node.getVariable("knownPeer");
	}
	
	public void testGetAncestorAxisList(){
		Parameter p1, p2, p3;
		p1 = new Parameter("p1");
		p2 = new Parameter("p2");
		p3 = new Parameter("p3");
		
		p2.addElement(p3);
		p1.addElement(p2);
		
		// p3 contains two parent elements
		assertEquals(2, p3.getAncestorAxisList().size());
		//p3 contains two parent elements and itself
		assertEquals(3, p3.getAncestorOrSelfAxisList().size());
	}

	
	public void testGetDescendantAxisList(){
		Segment s1, s2;
		s1 = new Segment(0,0,"");
		
		
		assertNull(s1.getDescendantAxisList());
		assertEquals(1, s1.getDescendantOrSelfAxisList().size());
	}
	
	public void testGetParentAxisList(){
		Parameter p1, p2, p3;
		p1 = new Parameter("p1");
		p2 = new Parameter("p2");
		p3 = new Parameter("p3");
		
		p2.addElement(p3);
		p1.addElement(p2);
		
		assertEquals(1, p2.getParentAxisList().size());
		assertEquals(p1, p2.getParentAxisList().get(0));
	}
	
	public void testGetFollowingAxisList(){
		
		assertEquals(0, node.getDocument().getFollowingAxisList().size());
		
		ArrayList list = (ArrayList)((IEvaluate)node).evaluate("/simulation/node");
		assertEquals(3, list.size());
		
		//currentNode = list[0]
		List following = ((INode)list.get(0)).getFollowingSiblingAxisList();
		assertEquals(2, following.size());

		assertNotNull(node.getDescendantAxisList());
//		following = ((INode)list.get(0)).getFollowingAxisList();
//		assertEquals(92, following.size());
		
		//currentNode = list[1]
		following = ((INode) list.get(1)).getFollowingSiblingAxisList();
		assertEquals(1, following.size());
		
		//currentNode = list[2]
		following = ((INode) list.get(2)).getFollowingSiblingAxisList();
		assertEquals(0, following.size());
	}
	
	public void testGetPrecedingAxisList(){
		assertEquals(0, node.getDocument().getPrecedingAxisList().size());
		
		ArrayList list = (ArrayList)((IEvaluate)node).evaluate("/simulation/node");
		assertEquals(3, list.size());
		
		//There are 509 resources preceding the nodes		

		//currentNode = list[0]
		List preceding = ((INode)list.get(0)).getPrecedingSiblingAxisList();
		assertEquals(510, preceding.size());

		assertNotNull(node.getDescendantAxisList());
//		following = ((INode)list.get(0)).getFollowingAxisList();
//		assertEquals(92, following.size());
		
		//currentNode = list[1]
		preceding = ((INode) list.get(1)).getPrecedingSiblingAxisList();
		assertEquals(511, preceding.size());
		
		//currentNode = list[2]
		preceding = ((INode) list.get(2)).getPrecedingSiblingAxisList();
		assertEquals(512, preceding.size());
	}
	
	public void testSetAttribute(){
		Variable var = new Variable("var1");
		assertEquals(0, var.getSize());
		
		XPathAttribute att = new XPathAttribute("att1", "27");
		var.addAttribute(att);
		assertEquals(att.getSize(), var.getSize());
		
		assertEquals(1, var.getAttributeList().size());
		
		assertEquals("27", var.getAttributeList().get(0).getValue().toString());
		
		//modify the attribute value
		var.setAttribute("att1", "-1");
		
		assertEquals("-1", var.getAttributeList().get(0).getValue().toString());
		
	}
	
	public void testIsSimilarTo(){
		
		ArrayList list = (ArrayList)((IEvaluate)node).evaluate("/simulation/node");
		INode n1 = (INode)list.get(0);
		INode n2 = (INode)list.get(1);
		INode s1 = (INode)list.get(2);
				
		assertTrue(n1.isSimilarTo(n1));
		assertTrue(n2.isSimilarTo(n2));
		assertTrue(s1.isSimilarTo(s1));
		
		assertFalse(n1.isSimilarTo(n2));
		assertFalse(s1.isSimilarTo(n1));
	}
	
}
