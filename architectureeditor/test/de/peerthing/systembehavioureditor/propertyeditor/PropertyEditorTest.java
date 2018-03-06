package de.peerthing.systembehavioureditor.propertyeditor;


import org.eclipse.swt.widgets.Composite;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

public class PropertyEditorTest extends MockObjectTestCase {

	Mock treeviewermock;
	PropertyEditor proped;
	Mock compositemock;
	Composite comp;
	protected void setUp() throws Exception {
		super.setUp();
//		treeviewermock = mock(TreeViewer.class);
		proped = new PropertyEditor();
//		proped.createPartControl(comp);
		
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.propertyeditor.PropertyEditor.setFocus()'
	 */
	public void testSetFocus() {
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.propertyeditor.PropertyEditor.getTreeViewer()'
	 */
	public void testGetTreeViewer() {
		assertNotNull(proped.getTreeViewer());
	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.propertyeditor.PropertyEditor.getGraphed()'
	 */
	public void testGetGraphed() {

	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.propertyeditor.PropertyEditor.getEvent()'
	 */
	public void testGetEvent() {

	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.propertyeditor.PropertyEditor.createPartControl(Composite)'
	 */
	public void testCreatePartControlComposite() {

	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.propertyeditor.PropertyEditor.initForms()'
	 */
	public void testInitForms() {

	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.propertyeditor.PropertyEditor.updateForm(Object)'
	 */
	public void testUpdateForm() {

	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.propertyeditor.PropertyEditor.nodeNameChanged(Object)'
	 */
	public void testNodeNameChanged() {

	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.propertyeditor.PropertyEditor.getTransition()'
	 */
	public void testGetTransition() {

	}

	/*
	 * Test method for 'de.peerthing.systembehavioureditor.propertyeditor.PropertyEditor.selectionChanged(IWorkbenchPart, ISelection)'
	 */
	public void testSelectionChanged() {

	}

}
