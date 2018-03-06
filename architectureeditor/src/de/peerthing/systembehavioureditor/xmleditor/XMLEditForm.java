package de.peerthing.systembehavioureditor.xmleditor;

/**
 * PeerThing SystemBehaviour-XML-Editor (tm)
 * Copyright: PeerThing Corp.
 * Author: David Hasselhoff
 */

import java.io.StringReader;
import java.util.Vector;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.jdom.input.SAXBuilder;


import de.peerthing.systembehavioureditor.gefeditor.SysGraphicalEditor;
import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;
import de.peerthing.systembehavioureditor.model.ISystemBehaviourObject;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.ITransition;
import de.peerthing.systembehavioureditor.model.ITransitionTarget;
import de.peerthing.systembehavioureditor.model.editor.State;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviourGEFModelFactory;
import de.peerthing.systembehavioureditor.model.editor.Task;
import de.peerthing.systembehavioureditor.model.editor.Transition;
import de.peerthing.systembehavioureditor.persistence.SystemBehaviourXMLAdapter;
import de.peerthing.workbench.filetyperegistration.INavigationTree;

public class XMLEditForm implements SelectionListener {
	
    protected FormToolkit toolkit;
    protected ScrolledForm form;
    protected INavigationTree tree;
    protected ISystemBehaviourObject currentObject;
    protected SysGraphicalEditor sysGraphicalEditor;
    private String oldXML;
    
    private SourceViewer sourceViewer;
    private Button applyButton;
    private Text errorText;
    private Text errorText1; // next line (for the specific error)

    public void widgetDefaultSelected(SelectionEvent e) {
    }

    public void widgetSelected(SelectionEvent e) {
    	if (e.getSource() == applyButton) {
			if (!oldXML.equals(sourceViewer.getDocument().get())) {
				applyAllChanges();
			}
		}
    }

    public void setNavigationTree(INavigationTree tree) {
    	
        this.tree = tree;
    }

    public void createForm(Composite parent, SysGraphicalEditor editor) {
    	
        sysGraphicalEditor = editor;
        toolkit = new FormToolkit(parent.getDisplay());
        form = toolkit.createScrolledForm(parent);
        toolkit.paintBordersFor(form.getBody());
        
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
		
		// dynamic help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(form, "de.peerthing.workbench.sysbehav_xml");
		form.setToolTipText("The XML-Editor to describe the Behaviour of an Architecture.");
		
		// error text
		errorText = toolkit.createText(form.getBody(),
				"This is just a long initialzing text for the text-label. The lenght of this determining the lenght of the text field. It could be a little longer.", SWT.NONE);	
		errorText.setVisible(false);
		errorText.setBackground(org.eclipse.draw2d.ColorConstants.blue);
		errorText1 = toolkit.createText(form.getBody(),
				"This is just a long initialzing text for the text-label. The lenght of this determining the lenght of the text field. It could be a little longer.", SWT.NONE);
		errorText1.setVisible(false);
    }

    public ScrolledForm getMainForm() {
    	
        return form;
    }

    public void update(ISystemBehaviourObject object) {
    	
    	errorText.setVisible(false);
    	errorText1.setVisible(false);
    	
    	currentObject = object;
    	
    	// saveArchitecture will safely not write to a file, if null is set as second argument.
    	oldXML = SystemBehaviourXMLAdapter.saveArchitecture((ISystemBehaviour)object, null);
    	
		sourceViewer.getDocument().set(oldXML);
    	
    }

    /**
     * This method is called when a different form is
     * about to be showed or if the input for this
     * form is about to change. Here, The user can be asked
     * if he wants to apply the changes he made to the form.
     *
     */
    public void aboutToClose() {
    	
    }

    /**
     * This method should apply all changes made
     * in the form to the underlying data model.
     * The method is called when the current file
     * should be saved, for example.
     *
     */
    public void applyAllChanges() {
    	
        try {

        	oldXML = sourceViewer.getDocument().get();
        	
        	SystemBehaviourGEFModelFactory factory = new SystemBehaviourGEFModelFactory();
            SystemBehaviourXMLAdapter xml = new SystemBehaviourXMLAdapter(factory);
	        currentObject = (SystemBehaviour) xml.loadArchitectureFromXMLString(oldXML);
	        
	        // endarbeiten for currentObject
			Vector<IState>iterateList = new Vector<IState>();
			// connect all transitions
			for (INodeType n : ((SystemBehaviour)currentObject).getNodes()) {
				for (ITask t : n.getTasks()) {
					for (IState s : t.getStates()) {
						for (ITransition tr : s.getTransitions()) {
	
							Transition tran = (Transition) tr;
							tran.setState(s);
							

							//  for decorative trans
							try{
								ITransitionTarget nextState = tran.getNextState();
								if (nextState instanceof Task) {
									
									Transition tBack = new Transition();
						        	tBack.setState((Task)tran.getNextState());
						        	tBack.setNextState((State)s);
						        	tBack.isDeco = true; // flag, that this transition is only decoration
						        	
						        	// connect tBack
						        	((Task)tran.getNextState()).addTransitionOutgoing(tBack);
					    			((State)s).addTransitionIncoming(tBack);
								}
							}
							catch (Exception e) {
								e.printStackTrace();
							}
							
							// create an implicit endState, if a transition has endTask="true"
							
							if (tran.isEndTask()){
								State endState = new State();
								endState.setEndState(true);
								try {
									endState.setX(tran.getState().getX()+72);
									endState.setY(tran.getState().getY()+80);
								}
								catch (Exception e) {
									endState.setX(100);
									endState.setY(100);
								}
								endState.setTask(t);
								((State)endState).addTransitionIncoming(tran);
								tran.setNextState(endState);
								
								// instead of t.addState(endState); to avoid a
								iterateList.add(endState); // CuncurrentModificationException
							}
						}
					}
	
					for (IState s : iterateList) {
						t.addState(s); // add the endStates to the task properly
					}
				}
			}
			setDirty();
			errorText.setText("The xml-model you entered is valid.");
        	errorText.setVisible(true);
        	errorText.setBackground(new Color(Display.getCurrent(), 242, 248, 255));
        	errorText1.setVisible(false);
        }
        catch (Exception e) {
        	errorText.setVisible(false);
        	System.out.println("The xml-model you edited is invalid. Changes made in the xml editor will be ignored and lost, when you leave the editor, till errors are corrected.");
        	errorText.setText("The xml-model is invalid. Leaving this editor with errors remaining causes lost of changes made here.");
        	
        	
        	// Get the error of the xml-model. It would be better to throw an exception in the persitence component instead.
        	SAXBuilder parser = new SAXBuilder();
            try {
                StringReader reader = new StringReader(oldXML);
                parser.build(reader);
            } catch (Exception e1) {
            	
            	// this e should be handeled to the caller for error messages
            	errorText1.setText(" (" + e1.getMessage() +")" );
            	errorText1.setVisible(true);
            	errorText1.setBackground(org.eclipse.draw2d.ColorConstants.orange);
            }
            
        	errorText.setVisible(true);
        	errorText.setBackground(org.eclipse.draw2d.ColorConstants.orange);
        	return; // prevent damage
        }
		
		// reset the graphical editor with the new Object
		try {
			sysGraphicalEditor.resetEditor(currentObject.getSystemBehaviour());
		}
		catch(Exception e) {
			
			 e.printStackTrace();
		}
    }

    protected void refreshTree() {

        tree.refresh(currentObject);
    }

    public ISystemBehaviourObject getCurrentlyEditedObject() {
    	
        return currentObject;
    }

    /**
     * Sets the dirty flag on the scenario editor to which
     * this form belongs.
     *
     */
    protected void setDirty() {
    	
    	sysGraphicalEditor.setDirty();
    }
}
