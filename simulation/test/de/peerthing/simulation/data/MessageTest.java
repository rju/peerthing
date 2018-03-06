package de.peerthing.simulation.data;

import org.junit.Test;

import de.peerthing.simulation.interfaces.DataFactory;

import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;

/**
 * 
 */
public class MessageTest extends TestCase {

	private String name; /* message name */

	private int sessionId;

	private long timeSent;

	private long timeReceived;

	private int id;

	private int size;

	// private INode sourceNode;
	//		
	// private ISystemTask sourceTask;
	//
	// private INode destinationNode;
	//		
	// private ISystemTask destinationTask;

	Message msg;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		msg = new Message(name, sessionId, timeSent, null, null, null);
		super.setUp();
	}

	/*
	 * Test method for 'de.peerthing.simulation.data.Message.getElementName()'
	 */
	public void testGetElementName() {
		assertEquals("message", msg.getElementName());
	}

	/*
	 * Test method for 'de.peerthing.simulation.data.Message.isElement()'
	 */
	public void testIsElement() {
		assertTrue(msg.isElement());
	}

	/*
	 * Test method for 'de.peerthing.simulation.data.Message.getId()'
	 */
	public void testGetId() {
		assertEquals(sessionId, msg.getId());
	}

	/*
	 * Test method for 'de.peerthing.simulation.data.Message.setName(String)'
	 */
	public void testSetName() {
		msg.setName(name);
	}

	/*
	 * Test method for 'de.peerthing.simulation.data.Message.getName()'
	 */
	public void testGetName() {
		msg.setName(name);
		assertEquals(name, msg.getName());
	}

	/*
	 * Test method for 'de.peerthing.simulation.data.Message.getTimeSent()'
	 */
	public void testGetTimeSent() {
		assertEquals(timeSent, msg.getTimeSent());
	}

	/*
	 * Test method for 'de.peerthing.simulation.data.Message.setTimeSent(long)'
	 */
	public void testSetTimeSent() {
		msg.setTimeSent(timeSent);
	}

	/*
	 * Test method for 'de.peerthing.simulation.data.Message.getTimeReceived()'
	 */
	public void testGetTimeReceived() {
		msg.setTimeReceived(timeReceived);
		assertEquals(timeReceived, msg.getTimeReceived());
	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.Message.setTimeReceived(long)'
	 */
	public void testSetTimeReceived() {
		msg.setTimeReceived(timeReceived);
	}

	/*
	 * Test method for 'de.peerthing.simulation.data.Message.setId(int)'
	 */
	public void testSetId() {
		msg.setId(id);
	}

	/*
	 * Test method for 'de.peerthing.simulation.data.Message.setSize(int)'
	 */
	public void testSetSize() {
		msg.setSize(size);
	}

	/*
	 * Test method for 'de.peerthing.simulation.data.Message.getSize()'
	 */
	public void testGetSize() {
		assertEquals(size, msg.getSize());
	}

	/*
	 * Test method for 'de.peerthing.simulation.data.Message.setSessionId(int)'
	 */
	public void testSetSessionId() {
		msg.setSessionId(sessionId);
	}

	/*
	 * Test method for 'de.peerthing.simulation.data.Message.getSessionId()'
	 */
	public void testGetSessionId() {
		assertEquals(sessionId, msg.getSessionId());
	}

	/*
	 * Test method for 'de.peerthing.simulation.data.Message.getSourceNode()'
	 */
	public void testGetSourceNode() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.Message.setSourceNode(INode)'
	 */
	public void testSetSourceNodeINode() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.Message.setDestinationNode(INode)'
	 */
	public void testSetDestinationNode() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.Message.getDestinationNode()'
	 */
	public void testGetDestinationNode() {

	}

	/*
	 * Test method for 'de.peerthing.simulation.data.Message.getSourceTask()'
	 */
	public void testGetSourceTask() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.Message.setSourceNode(ISystemTask)'
	 */
	public void testSetSourceNodeISystemTask() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.Message.setDestinationTask(ISystemTask)'
	 */
	public void testSetDestinationTask() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.Message.getDestinationTask()'
	 */
	public void testGetDestinationTask() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.Message.getParameter(String)'
	 */
	@Test
	public void testGetAddParameter() {

		Message message;
		message = new Message(null, 0, 0L, null, null, null);
		assertNull(message.getParameter("param1"));

		message.addParameter(DataFactory.createParameter("param1", "1"));
		assertNotNull(message.getParameter("param1"));
		// do not touch, parameter interface may change again
		// assertEquals(1,
		// ((Integer)message.getParameter("param1").getValue()).intValue());

		message.addParameter(DataFactory.createParameter("param2", "2"));
		// assertEquals(1,
		// ((Integer)message.getParameter("param1").getValue()).intValue());
		// assertEquals(2,
		// ((Integer)message.getParameter("param2").getValue()).intValue());
	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathContainer.getChildAxisCollection()'
	 */
	public void testGetChildAxisCollection() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathContainer.getDescendantAxisCollection()'
	 */
	public void testGetDescendantAxisCollection() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathContainer.XPathContainer()'
	 */
	public void testXPathContainer() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathContainer.addElement(IXPathObject)'
	 */
	public void testAddElement() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathContainer.addAllElement(List<IXPathObject>)'
	 */
	public void testAddAllElement() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathContainer.removeElement(IXPathObject)'
	 */
	public void testRemoveElement() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathContainer.removeAllElement()'
	 */
	public void testRemoveAllElement() {

	}

	/*
	 * Test method for 'de.peerthing.simulation.data.XPathObject.XPathObject()'
	 */
	public void testXPathObject() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathObject.getAncestorAxisCollection()'
	 */
	public void testGetAncestorAxisCollection() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathObject.getAncestorOrSelfAxisCollection()'
	 */
	public void testGetAncestorOrSelfAxisCollection() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathObject.getAttributeAxisCollection()'
	 */
	public void testGetAttributeAxisCollection() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathObject.getDescendantOrSelfAxisCollection()'
	 */
	public void testGetDescendantOrSelfAxisCollection() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathObject.getParentAxisCollection()'
	 */
	public void testGetParentAxisCollection() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathObject.getFollowingAxisCollection()'
	 */
	public void testGetFollowingAxisCollection() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathObject.getFollowingSiblingAxisIterator()'
	 */
	public void testGetFollowingSiblingAxisIterator() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathObject.getPrecedingAxisCollection()'
	 */
	public void testGetPrecedingAxisCollection() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathObject.getPrecedingSiblingAxisCollection()'
	 */
	public void testGetPrecedingSiblingAxisCollection() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathObject.getSelfAxisCollection()'
	 */
	public void testGetSelfAxisCollection() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathObject.setDocument(IXPathObject)'
	 */
	public void testSetDocument() {

	}

	/*
	 * Test method for 'de.peerthing.simulation.data.XPathObject.getDocument()'
	 */
	public void testGetDocument() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathObject.setParent(IXPathObject)'
	 */
	public void testSetParent() {

	}

	/*
	 * Test method for 'de.peerthing.simulation.data.XPathObject.getParent()'
	 */
	public void testGetParent() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathObject.addAttribute(XPathAttribute)'
	 */
	public void testAddAttribute() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathObject.getAttributeStringValue()'
	 */
	public void testGetAttributeStringValue() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathObject.getAttributeName()'
	 */
	public void testGetAttributeName() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathObject.getCommentStringValue()'
	 */
	public void testGetCommentStringValue() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathObject.getElementById(String)'
	 */
	public void testGetElementById() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathObject.getElementStringValue()'
	 */
	public void testGetElementStringValue() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathObject.getXPathNodeType()'
	 */
	public void testGetXPathNodeType() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathObject.getTextStringValue()'
	 */
	public void testGetTextStringValue() {

	}

	/*
	 * Test method for 'de.peerthing.simulation.data.XPathObject.isAttribute()'
	 */
	public void testIsAttribute() {

	}

	/*
	 * Test method for 'de.peerthing.simulation.data.XPathObject.isComment()'
	 */
	public void testIsComment() {

	}

	/*
	 * Test method for 'de.peerthing.simulation.data.XPathObject.isDocument()'
	 */
	public void testIsDocument() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathObject.isProcessingInstruction()'
	 */
	public void testIsProcessingInstruction() {

	}

	/*
	 * Test method for 'de.peerthing.simulation.data.XPathObject.isText()'
	 */
	public void testIsText() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathObject.evaluate(String)'
	 */
	public void testEvaluate() {

	}

	/*
	 * Test method for
	 * 'de.peerthing.simulation.data.XPathObject.evaluateCondition(String)'
	 */
	public void testEvaluateCondition() {

	}

	/*
	 * Test method for 'java.lang.Object.Object()'
	 */
	public void testObject() {

	}

	/*
	 * Test method for 'java.lang.Object.getClass()'
	 */
	public void testGetClass() {

	}

	/*
	 * Test method for 'java.lang.Object.hashCode()'
	 */
	public void testHashCode() {

	}

	/*
	 * Test method for 'java.lang.Object.equals(Object)'
	 */
	public void testEquals() {

	}

	/*
	 * Test method for 'java.lang.Object.clone()'
	 */
	public void testClone() {

	}

	/*
	 * Test method for 'java.lang.Object.toString()'
	 */
	public void testToString() {

	}

	/*
	 * Test method for 'java.lang.Object.notify()'
	 */
	public void testNotify() {

	}

	/*
	 * Test method for 'java.lang.Object.notifyAll()'
	 */
	public void testNotifyAll() {

	}

	/*
	 * Test method for 'java.lang.Object.wait(long)'
	 */
	public void testWaitLong() {

	}

	/*
	 * Test method for 'java.lang.Object.wait(long, int)'
	 */
	public void testWaitLongInt() {

	}

	/*
	 * Test method for 'java.lang.Object.wait()'
	 */
	public void testWait() {

	}

	/*
	 * Test method for 'java.lang.Object.finalize()'
	 */
	public void testFinalize() {

	}

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(MessageTest.class);
	}

}
