package de.peerthing.scenarioeditor.editor.forms;

import java.io.StringReader;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.jdom.input.SAXBuilder;

import de.peerthing.scenarioeditor.editor.ScenarioEditor;
import de.peerthing.scenarioeditor.editor.xml.XMLSourceViewerConfig;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.persistence.ScenarioXMLAdapter;
/**
 *  @author Michael, Petra (dynamic help)
 */
public class XMLEditForm extends AbstractScenarioEditorForm {
	private SourceViewer sourceViewer;

	private String oldXML;
	
	private String testXML;

	private Button applyButton;

	private Text errorText;
    private Text errorText1; // next line (for the specific error)
	
	/**
	 * All elements of the form are created and placed here.
	 */
	@Override
	public void createForm(Composite parent, ScenarioEditor editor) {
		super.createForm(parent, editor);

		form.setText("XML View:");
		form.getBody().setLayout(new GridLayout(1, false));

		GridData gd = new GridData(GridData.FILL_BOTH);
		sourceViewer = new SourceViewer(form.getBody(), null, SWT.BORDER
				| SWT.V_SCROLL);
		toolkit.adapt(sourceViewer.getControl(), true, true);
		sourceViewer.configure(new XMLSourceViewerConfig());
		sourceViewer.getControl().setLayoutData(gd);
		sourceViewer.setDocument(new Document());

		gd = new GridData();
		gd.horizontalAlignment = SWT.CENTER;

		applyButton = toolkit.createButton(form.getBody(), "Apply changes",
				SWT.PUSH);
		applyButton.setLayoutData(gd);
		applyButton.addSelectionListener(this);

		//dynamic help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(applyButton, "de.peerthing.workbench.scenario_xml");
		applyButton.setToolTipText("Push to save changes.");
		
		//		 error text
		errorText = toolkit.createText(form.getBody(),
				"This is just a long initialzing text for the text-label. The lenght of this determining the lenght of the text field. It could be a little longer.", SWT.NONE);	
		errorText.setVisible(false);
		errorText.setBackground(new Color(null, 255,193,37));		
		errorText1 = toolkit.createText(form.getBody(),
				"This is just a long initialzing text for the text-label. The lenght of this determining the lenght of the text field. It could be a little longer.", SWT.NONE);
		errorText1.setBackground(new Color(null, 255,193,37));
		errorText1.setVisible(false);

	}
	
	/**
     * This method is used every time a change is made. The changes are assumed and the form
     * is refreshed.
     */
	@Override
	public void update(IScenarioObject object) {
		currentObject = object;

		oldXML = ScenarioXMLAdapter.getScenarioXMLString(currentObject
				.getScenario());
		sourceViewer.getDocument().set(oldXML);
	}

	@Override
	public boolean aboutToClose() {
		if (!oldXML.equals(sourceViewer.getDocument().get())) {
			if (MessageDialog.openQuestion(form.getShell(), "XML changed",
					"Do you want to apply the changes?")) {
								
				testXML = sourceViewer.getDocument().get();			
				IScenario newScen = ScenarioXMLAdapter
						.getScenarioFromXMLString(testXML);
				
				if (newScen==null){												
					// Get the error of the xml-model. It would be better to throw an exception in the persitence component instead.		        	
		            if (MessageDialog.openQuestion(form.getShell(), "XML changed",
					"The xml-model is invalid. If you continue changes will be lost.\n" +
					"Do you want to continue?")) {
		            	return true;
		            } else {
		            	return false;
		            }
				}
			}
		}
		return true;
	}

	@Override
	public void applyAllChanges() {
		
		errorText.setVisible(false);
		errorText1.setVisible(false);				
		
		testXML = sourceViewer.getDocument().get();			
		IScenario newScen = ScenarioXMLAdapter
				.getScenarioFromXMLString(testXML);
		
		if (newScen==null){												
			// Get the error of the xml-model. It would be better to throw an exception in the persitence component instead.
        	SAXBuilder parser = new SAXBuilder();
            org.jdom.Document doc1 = new org.jdom.Document();
			try {
                StringReader reader = new StringReader(testXML);
                doc1 = parser.build(reader);
            } catch (Exception e1) {            	
            	errorText.setText("The xml-model is invalid. Leaving this editor with errors remaining causes lost of changes made here.");
            	errorText.setVisible(true);
            	errorText1.setText(" (" + e1.getMessage() +")" );
            	errorText1.setVisible(true);            	
            	// this e should be handeled to the caller for error messages
            	return;                        	
            }
            
            errorText.setText("The xml-model is invalid. Leaving this editor with errors remaining causes lost of changes made here.");
        	errorText.setVisible(true);
        	errorText1.setText("Attribute or child of an Element is missing or wrong.");
        	errorText1.setVisible(true);
            	
		}
		oldXML = sourceViewer.getDocument().get();
		update(currentObject);
		scenarioEditor.setScenario(newScen);
		setDirty();
	}
	
	/**
	 * method is called when applybutton is clicked. the reaction in this
	 * method is, that the xml code is checked and maybe applied by
	 * another method that is called from here
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		if (e.getSource() == applyButton) {
			if (!oldXML.equals(sourceViewer.getDocument().get())) {
				applyAllChanges();
			}
		}
	}

}
