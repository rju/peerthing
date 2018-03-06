package de.peerthing.scenarioeditor.model.impl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Patrik Schulz
 * There are standard rules that names of elements can never contain 
 * more than 255 signs and that they have to contain a sign,
 * other than "space". These rules are checked in a method of
 * this class. (I Message apears if an error is revealed)
 */
public class NameTest {

	/**
	 * method to test if name is ok, and show a message if not
	 * @param shell shell were error message apears
	 * @param newName name which is to chek
	 * @return value if name is ok
	 */
	public static boolean isNameOk(Shell shell, String newName) {

		if (newName.equals("")) {
			while (true) {
				MessageBox cancelBox = new MessageBox(shell, SWT.OK
						| SWT.ICON_WARNING);
				cancelBox.setText("Illegal Name");
				cancelBox.setMessage("You have to choose a name "
						+ "for the Element you want to add.");
				if (cancelBox.open() == SWT.OK) {
					break;
				}
			}
			return false;
		}

		if (newName.length() > 255) {
			while (true) {
				MessageBox cancelBox = new MessageBox(shell, SWT.OK
						| SWT.ICON_WARNING);
				cancelBox.setText("Illegal Name");
				cancelBox.setMessage("The name for the element "
						+ "you want to add must contain less then 256 signs.");
				if (cancelBox.open() == SWT.OK) {
					break;
				}
			}
			return false;
		}

		boolean signOk = false;
		for (char sign : newName.toCharArray()) {
			if (sign != ' ') {
				signOk = true;
			}
		}
		if (!signOk) {
			while (true) {
				MessageBox cancelBox = new MessageBox(shell, SWT.OK
						| SWT.ICON_WARNING);
				cancelBox.setText("Illegal Name");
				cancelBox.setMessage("You have to choose a name "
						+ "which includes another sign then \"space\"");
				if (cancelBox.open() == SWT.OK) {
					break;
				}
			}
			return false;
		}
		return true;
	}
		
	/**
	 * method to test if name is ok	
	 * @param newName name which is to chek
	 * @return value if name is ok
	 */
	public static boolean isNameOk(String newName) {

		if (newName.equals("") || newName.length() > 255) {
			return false;
		}
		boolean signOk = false;
		for (char sign : newName.toCharArray()) {
			if (sign != ' ') {
				signOk = true;
			}
		}
		if (!signOk) {
			return false;
		}
		return true;
	}
}
