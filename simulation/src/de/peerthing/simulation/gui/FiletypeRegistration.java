package de.peerthing.simulation.gui;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import de.peerthing.workbench.filetyperegistration.IFileTypeRegistration;
import de.peerthing.workbench.filetyperegistration.INavigationTree;



/**
 * Registers the filetypes arch and scen for the simulation component at the
 * peerthing workbench.
 * 
 * 
 * @author Michael Gottschalk
 * 
 */
public class FiletypeRegistration implements IFileTypeRegistration {

	public String[] getFileNameEndings() {
		return new String[] { "arch", "scen" };
	}

	public void showComponent(IFile[] inputFiles) {
		try {
			PlatformUI.getWorkbench().showPerspective(
					"de.peerthing.SimulationPerspective",
					PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		} catch (WorkbenchException e) {
			e.printStackTrace();
		}

		SimulationExecutionView view = (SimulationExecutionView) PlatformUI
				.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.findView("de.peerthing.simulation.View");
		if (view != null) {
			for (IFile file : inputFiles) {
				if (file != null && file.getFileExtension() != null) {
					if (file.getFileExtension().equals("arch")) {
						view.setArchitectureInput(file);
					} else if (file.getFileExtension().equals("scen")) {
						view.setScenarioInput(file);
					}
				}
			}

		}
	}

	public Object[] getTreeElements(IFile file) {
		return null;
	}

	public ITreeContentProvider getSubtreeContentProvider() {
		return null;
	}

	public ILabelProvider getSubtreeLabelProvider() {
		return null;
	}

	public boolean canHandleSubtreeObject(Object obj) {
		return false;
	}

	public boolean hasSubTree(IFile file) {
		return false;
	}

	public void setNavigationTree(INavigationTree navigationTree) {
	}

	public void subTreeElementSelected(Object subTreeElement) {
	}

	public void subTreeElementDoubleClicked(Object subTreeElement) {
	}

	public String getComponentName() {
		return "Simulator";
	}

	public boolean wantsToBeDefaultEditor() {
		return false;
	}

	public String[] getNewFileDefinition() {
		return null;
	}

}
