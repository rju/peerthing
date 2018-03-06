package de.peerthing.scenarioeditor.model.impl;

import junit.framework.TestCase;

import org.eclipse.ui.forms.widgets.Form;

public class NameTestTest extends TestCase {

	NameTest nt;
	protected Form form;


	protected void setUp() throws Exception {
		super.setUp();
		nt = new NameTest();
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.NameTest.isNameOk(Shell, String)'
	 */
	public void testIsNameOkShellString() {
		/**
		 * need parent here from GUI parent = new Composite(); form = new
		 * Form(parent, 1); shell = form.getShell();
		 * assertFalse(nt.isNameOk(shell, newName));
		 */
	}

	/*
	 * Test method for
	 * 'de.peerthing.scenarioeditor.model.impl.NameTest.isNameOk(String)'
	 */
	public void testIsNameOkString() {

	}

}
