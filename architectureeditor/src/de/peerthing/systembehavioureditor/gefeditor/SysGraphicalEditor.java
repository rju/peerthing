/*******************************************************************************
 * Copyright (c) 2004 Boris Bokowski, Frank Gerhardt All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/org/documents/epl-v10.html
 *
 * Contributors: Boris Bokowski (bokowski@acm.org) Frank Gerhardt
 * (fg@frankgerhardt.com)
 ******************************************************************************/

/**
 * This is the main class of the SystemBehaviour Editor. It extends GraphicalEditorWithPalette
 * from the 'Eclipse Graphical Editing Framework', which provides a quick starting point
 * for GEF applications.
 * 
 * This class is like some others derived from a tutorial *) but might now as progress has been made be seen as  independant code.
 * 
 *  *) Bokowski, Boris und Frank Gerhardt: Ma?geschneiderte gra?sche Edito-
 * ren mit dem Graphical Editing Framework (GEF), Ausgabe 2 ,S. 85-90. eclipse
 * Magazin, 2004.

 * 
 * @author Petra Beenken and Peter Schwenkenberg
 * @review Johannes Fischer
 */

package de.peerthing.systembehavioureditor.gefeditor;

import java.util.EventObject;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackListener;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.jdom.input.SAXBuilder;

import de.peerthing.systembehavioureditor.PeerThingSystemBehaviourEditorPlugin;
import de.peerthing.systembehavioureditor.SystemBehaviourFiletypeRegistration;
import de.peerthing.systembehavioureditor.gefeditor.commands.StateDeleteCommand;
import de.peerthing.systembehavioureditor.gefeditor.commands.TaskDeleteCommand;
import de.peerthing.systembehavioureditor.gefeditor.commands.TransitionDeleteCommand;
import de.peerthing.systembehavioureditor.gefeditor.editparts.MyEditPartFactory;
import de.peerthing.systembehavioureditor.gefeditor.editparts.StateEditPart;
import de.peerthing.systembehavioureditor.model.IState;
import de.peerthing.systembehavioureditor.model.INodeType;
import de.peerthing.systembehavioureditor.model.ISystemBehaviour;
import de.peerthing.systembehavioureditor.model.ISystemBehaviourObject;
import de.peerthing.systembehavioureditor.model.ITask;
import de.peerthing.systembehavioureditor.model.ITransition;
import de.peerthing.systembehavioureditor.model.ITransitionTarget;
import de.peerthing.systembehavioureditor.model.editor.Node;
import de.peerthing.systembehavioureditor.model.editor.State;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviour;
import de.peerthing.systembehavioureditor.model.editor.SystemBehaviourGEFModelFactory;
import de.peerthing.systembehavioureditor.model.editor.Task;
import de.peerthing.systembehavioureditor.model.editor.Transition;
import de.peerthing.systembehavioureditor.persistence.SystemBehaviourXMLAdapter;
import de.peerthing.systembehavioureditor.xmleditor.XMLEditForm;

public class SysGraphicalEditor extends GraphicalEditorWithPalette implements
		ISelectionListener {

	public static final String ID = SysGraphicalEditor.class.getName();

	private boolean dirty = false;

	private SystemBehaviour system;

	private PaletteRoot paletteRoot;

	public SystemBehaviourFiletypeRegistration filetypeReg;

	private StackLayout stackLayout = new StackLayout();

	public SystemBehaviour sysToShow;

	private Composite gefEditor;

	Composite xmlPanel;

	Composite superComp;

	TabFolder tabFolder;

	MessageBox cancelBox;
	
	public XMLEditForm xmlEditForm;

	// experimental
	public IEditorInput input;

	public SystemBehaviour getSystemBehaviour() {
		return this.system;
	}

	public void setSystemBehaviour(SystemBehaviour newSystem) {

		SystemBehaviour oldSys = this.system;
		this.system = newSystem;
		newSystem.firePropertyChangeForSystem(oldSys, newSystem);
	}

	/**
	 * Sets the editor input from a XML-file.
	 */
	public void setInput(IEditorInput input) {
		super.setInput(input);
		this.input = input; // maybe not needed
		IFile file = (IFile) input.getAdapter(IResource.class);
		try {
			SystemBehaviourGEFModelFactory factory = new SystemBehaviourGEFModelFactory();
			SystemBehaviourXMLAdapter xml = new SystemBehaviourXMLAdapter(
					factory);
			system = (SystemBehaviour) xml.loadArchitecture(file.getLocation()
					.toString());

			Vector<IState>iterateList = new Vector<IState>();
			// connect all transitions
			for (INodeType n : system.getNodes()) {
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
		} 
		
	
		
		catch (Exception e) {
			
			// test, whether the file is empty, if not, it is defect and will not be loaded
			org.jdom.input.SAXBuilder parser = new SAXBuilder();
			try {
				parser.build(file.getLocation().toString());
			}
			catch (Exception exc) {
				
				if (!exc.toString().contains("Error on line -1: Premature end of file.")) { // file is not empty but corrupt
					
					cancelBox = new MessageBox( getEditorSite().getShell(), SWT.OK
							|SWT.ICON_WARNING );
					cancelBox.setText("Error");
					cancelBox.setMessage("Error loading the System Behaviour file. A new System Behaviour will be created. If you do not want to replace the old file, leave the editor without saving.");
					if (cancelBox.open()== SWT.OK || cancelBox.open() == SWT.ABORT){
						
						// dispose() causes exception inside eclipse
					}
				}
			}
			
			// Create a fresh architecture, but one that contains the initial
			// node and task infrastructure.
			system = new SystemBehaviour(true);
		}
		system.setFile(file);

		// register this editor with the system object
		system.setEditor(this);
        
        // Set the title of the editor
        setPartName(file.getName());
	}
	
	/**
	 * Reset the editor and systembehaviour after changes made in the xml
	 * editor. This is a very important method full of wisdom and insight.
	 */
	public void resetEditor(ISystemBehaviour sys) {

		// update the model
		system.getNodes().clear();
		for (INodeType n : sys.getNodes()) {
			system.addNode((Node) n);
			n.setArchitecture(system);
			for (ITask t : n.getTasks()) {
				((Task) t).setNode(n);
				for (IState s : t.getStates()) {
					((State) s).setTask(t);
					for (ITransition tr : s.getTransitions()) {
						((Transition)tr).setState(s);
					}
				}
			}
		}
		system.setName(sys.getName());
		
		// update the resource tree
		PeerThingSystemBehaviourEditorPlugin.getDefault().getNavigationTree()
				.refresh(
						system.getEditor().getFiletypeReg().getFile(
								system.getEditor()));
		
		// dispose all old tabs
		TabItem[] tabItems = this.getTabFolder().getItems();
		for (int i = 0; i < tabItems.length; i++) {
				tabItems[i].dispose();
		}
		
		// create for each node a new tabitem 
		for (INodeType n : sys.getNodes()) {

			TabItem nodeTab = new TabItem(tabFolder, SWT.NONE);
			nodeTab.setText(n.getName());
			nodeTab.setData(n);

			nodeTab.setControl(gefEditor);
		}

		// Select the first node
		tabFolder.setSelection(0);

		// reset the editor input
		changeEditorInput((INodeType)tabFolder.getItems()[0].getData());
		
		// set the color of the first tab manually
		tabFolder.setBackground(((Node)system.getNodes().get(0)).getColor());
		
        // don't do the following since this method is called from the
        // xml view and the view should stay there.
		//changeTopControlGEF();
	}

	/**
	 * The edit domain maintains the command stack, the current tool, etc.
	 */
	public SysGraphicalEditor() {
		setEditDomain(new DefaultEditDomain(this));
		this.getCommandStack().addCommandStackListener(new CommandStackListener() {
			public void commandStackChanged(EventObject event) {
				firePropertyChange(PROP_DIRTY);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#initializeGraphicalViewer()
	 */
	protected void initializeGraphicalViewer() {
		getGraphicalViewer().setEditPartFactory(new MyEditPartFactory());
		getGraphicalViewer().setContents(system);
	}

	
	/**
	 * Manages the Palette.
	 */
	protected PaletteRoot getPaletteRoot() {

		if (paletteRoot == null) {
			
			paletteRoot = new PaletteRoot();
			PaletteGroup paletteGroup = new PaletteGroup("Tools");
			paletteRoot.add(paletteGroup);
			SelectionToolEntry selectionToolEntry = new SelectionToolEntry();
			paletteGroup.add(selectionToolEntry);
			paletteGroup.add(new MarqueeToolEntry());
			
			paletteRoot.setDefaultEntry(selectionToolEntry);

			ImageDescriptor icon = PeerThingSystemBehaviourEditorPlugin
					.getDefault().getIcon("transition.png");
			paletteGroup.add(new ConnectionCreationToolEntry("Transition",
					"Draw a Transition Between Two States",
					new CreationFactory() {

						public Object getNewObject() {
							return new Transition();
						}

						public Object getObjectType() {
							return Transition.class;
						}
					}, icon, icon));

			icon = PeerThingSystemBehaviourEditorPlugin.getDefault().getIcon(
					"state.png");
			paletteGroup.add(new CreationToolEntry("State",
					"Place a New State for the Current Peer",
					new CreationFactory() {
						public Object getNewObject() {
							return new State();
						}

						public Object getObjectType() {
							return State.class;
						}
					}, icon, icon));

			icon = PeerThingSystemBehaviourEditorPlugin.getDefault().getIcon(
					"task.png");
			paletteGroup.add(new CreationToolEntry("Task",
					"Place a New Task (Comparable to  a \"New Thread\")",
					new CreationFactory() {
						public Object getNewObject() {
							return new Task();
						}

						public Object getObjectType() {
							return Task.class;
						}
					}, icon, icon));

			icon = PeerThingSystemBehaviourEditorPlugin.getDefault().getIcon(
					"end_task.png");
			paletteGroup.add(new CreationToolEntry("End-State",
					"Place an End-State (Terminating a Task)",
					new CreationFactory() {
						public Object getNewObject() {
							State s = new State();
							s.setName("___endState___");
							s.setEndState(true);
							return s;
						}

						public Object getObjectType() {
							return State.class;
						}
					}, icon, icon));

		}
		return paletteRoot;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void doSave(IProgressMonitor monitor) {
		dirty = false;
		try {
			IFile file = (IFile) getEditorInput().getAdapter(IResource.class);
			SystemBehaviourXMLAdapter.saveArchitecture(system, file
					.getLocation().toString());
			// After beeing saved, the editor should not be dirty.
			getCommandStack().markSaveLocation();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
     * Save as is not supported.
	 */
	public void doSaveAs() {
		dirty = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#isDirty()
	 */
	public boolean isDirty() {
		return getCommandStack().isDirty() || dirty;
	}

	public void setDirty() {
		dirty = true;
		firePropertyChange(PROP_DIRTY);
	}

	/**
     * Save as is not allowed here.
	 */
	public boolean isSaveAsAllowed() {
		return false;
	}

	public GraphicalViewer getMyGraphicalViewer() {
		return getGraphicalViewer();
	}
	
	public ActionRegistry getMyActionRegistry() {
		return getActionRegistry();
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);

		// Show the right perspective if this part is
		// shown in another perspective...
		getSite().getWorkbenchWindow().getPartService().addPartListener(
				new IPartListener() {
					public void partActivated(IWorkbenchPart part) {
					}

					public void partBroughtToTop(IWorkbenchPart part) {
						if (part == SysGraphicalEditor.this) {
							try {
								PlatformUI
										.getWorkbench()
										.showPerspective(
												"de.peerthing.systembehavioureditor.SystemBehaviourPerspective",
												PlatformUI
														.getWorkbench()
														.getActiveWorkbenchWindow());
							} catch (Exception e) {
								System.out
										.println("Could not show perspective:");
								e.printStackTrace();
							}
						}
					}

					public void partClosed(IWorkbenchPart part) {
					}

					public void partDeactivated(IWorkbenchPart part) {
					}

					public void partOpened(IWorkbenchPart part) {
					}

				});
	}

	@Override
	public void createPartControl(Composite parent) {
		/*
		 * The Uber-Composite: It has the TabFolder and the XML-Editor as
		 * children.
		 * 
		 * 		superComp
		 * 			/ \
		 * 	tabFolder xmlEditor 
		 * 		/
		 * gefEditor
		 * 
		 */
		superComp = new Composite(parent, SWT.NONE);
		superComp.setLayout(stackLayout);
		
		/*
		 * Tab Composite
		 */
		tabFolder = new TabFolder(superComp, SWT.BOTTOM);
		tabFolder.setLayout(new FillLayout());

		/*
		 * gefEditor is a sub-composite of tabfolder
		 */
		gefEditor = new Composite(tabFolder, SWT.NONE);
		gefEditor.setLayout(new FillLayout());

		// Create for each Node a corresponding TabItem
		for (INodeType n : getSystemBehaviour().getNodes()) {

			TabItem nodeTab = new TabItem(tabFolder, SWT.NONE);
			nodeTab.setText(n.getName());
			nodeTab.setData(n);
			nodeTab.setControl(gefEditor);
			
			// Set an icon for the tabs.
			ImageDescriptor icon = PeerThingSystemBehaviourEditorPlugin
			.getDefault().getIcon("node.png");
			Image img = icon.createImage();
			nodeTab.setImage(img);
		}

		// Select the first node
		tabFolder.setSelection(0);

		// Before selecting a tab, the default color for a node (see
		// StateEditPart) is choosen.
		// The color is not accessed by Node#getColor, because when the current
		// method is called, the model elements don't exist.
		tabFolder.setBackground(new Color(Display.getCurrent(), 255, 252, 230));

		tabFolder.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				
				try {
					changeEditorInput((Node) ((TabItem) (e.item)).getData());
					((TabItem) (e.item)).getParent().setBackground(
							((Node) ((TabItem) (e.item)).getData()).getColor());
					((SystemBehaviour) ((Node) ((TabItem) (e.item)).getData())
							.getArchitecture())
							.setCurrentNode(((Node) ((TabItem) (e.item))
									.getData()));

					// synchronize with resource tree selection
					SystemBehaviourFiletypeRegistration.getNavigationTree().select(
							(Node) ((TabItem) (e.item)).getData());
				} catch (Exception ex) {
					System.out.println("Exception caused by selecting a tab caught. Possibly the color of a node is not set yet." + e);
				}
			}
		});

		/*
		 * xmlEditor-Composite
		 */
		Composite xmlEditor = new Composite(superComp, SWT.NONE);
		xmlEditor.setLayout(new GridLayout(1, false));

		// Add the text editor to a new composite
		// which is completely filled and added
		// to the container with the stack layout.
		xmlPanel = new Composite(superComp, SWT.NONE);
		xmlPanel.setLayout(new FillLayout());

		xmlEditForm = new XMLEditForm();
		xmlEditForm.createForm(xmlPanel, this);
		xmlEditForm.update(system);

		super.createPartControl(gefEditor);
		
		
		stackLayout.topControl = tabFolder; // or xmlPanel
		
		// only this place seems to work
		// hideNodes(system.getNodes().get(0));

		sysToShow = new SystemBehaviour();
		sysToShow.addNode((Node) system.getNodes().get(0));
		getGraphicalViewer().setContents(sysToShow);

		
		//	dynamic help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(gefEditor, "de.peerthing.workbench.sysbehav_gefeditor");

		PlatformUI.getWorkbench().getHelpSystem().setHelp(xmlPanel, "de.peerthing.workbench.sysbehav_xml");
		
		// key listener
		try {
			this.getGraphicalViewer().getControl().addKeyListener(new org.eclipse.swt.events.KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.keyCode==127) {
						
						// delete all selected elements
						
						// delete tasks
						List<TaskDeleteCommand> listTdc = new Vector<TaskDeleteCommand>();
						List<TransitionDeleteCommand> listTranDc = new Vector<TransitionDeleteCommand>();
						
						for ( ITask t : sysToShow.getNodes().get(0).getTasks() ){
							Task task = (Task) t;
							if(task.getEditPart().getSelected() >= 1) {
								task.getEditPart().setSelected(0);
								TaskDeleteCommand delT = new TaskDeleteCommand(task, (SystemBehaviour)task.getSystemBehaviour());
								listTdc.add(delT);
							}
								
							// delete transitions of a task (to a state)
							for (ITransition tr : ((Task)t).getTransitionsOutgoing()) {
									
								Transition tran = (Transition) tr;
								
								if (tran.getEditPart().getSelected() >= 1) {
									tran.getEditPart().setSelected(0);
									TransitionDeleteCommand delTr = new TransitionDeleteCommand(tran);
									listTranDc.add(delTr);
								}
							}
								
							List<StateDeleteCommand> listSdc = new Vector<StateDeleteCommand>();
							// delete States
							for (IState s : t.getStates()) {
								
								State state = (State) s;
								if (state.getEditPart().getSelected() >= 1) {
									state.getEditPart().setSelected(0);
									StateDeleteCommand delS = new StateDeleteCommand(state, (SystemBehaviour)state.getSystemBehaviour());
									listSdc.add(delS);
									//delS.execute(); conncurred exception.
								}
								// delete Transitions
								//List<TransitionDeleteCommand> listTranDc = new Vector<TransitionDeleteCommand>();
								for (ITransition tr : s.getTransitions()) {
									Transition tran = (Transition) tr;
									
									if (tran.getEditPart().getSelected() >= 1) {
										tran.getEditPart().setSelected(0);
										TransitionDeleteCommand delTr = new TransitionDeleteCommand(tran);
										listTranDc.add(delTr);
									}
								}
								
								
								for (TransitionDeleteCommand trandc : listTranDc) {
									getCommandStack().execute(trandc);
								}
							}
							// execute the state delete commands safely.
							for (StateDeleteCommand sdc : listSdc) {
								getCommandStack().execute(sdc);
								
							}
						}
						for (TaskDeleteCommand tdc : listTdc) {
							getCommandStack().execute(tdc);
						}
					}
				}
			}
			);
		}
		catch(Exception e) {
			System.out.println("Error adding keylistener ");
			e.printStackTrace();
		}
	}
	
	public void addTab(INodeType node) {

		try {
			TabItem nodeTab = new TabItem(tabFolder, SWT.NONE);
			nodeTab.setText(node.getName());
			nodeTab.setData(node);
			nodeTab.setControl(gefEditor);
	
			int indexOfNode = tabFolder.getItems().length - 1;
			tabFolder.setSelection(indexOfNode);
			tabFolder.setBackground(((Node) tabFolder.getItem(indexOfNode)
					.getData()).getColor());
			this.changeEditorInput((Node) tabFolder.getItem(indexOfNode).getData());
	
			ImageDescriptor icon = PeerThingSystemBehaviourEditorPlugin
			.getDefault().getIcon("node.png");
			Image img = icon.createImage();
			nodeTab.setImage(img);
			
			// synchronize with resource tree selection
			
			SystemBehaviourFiletypeRegistration.getNavigationTree().select(
					(Node) tabFolder.getItem(indexOfNode).getData());
		}
		catch (Exception e) {
			System.out.println("Cannot create the new editor tab.");
		}
	}
	
	public void deleteTab(INodeType node){
		
		try {
			TabItem[] tabItems = this.getTabFolder().getItems();
			// find the matching tab
			for (int i = 0; i < tabItems.length; i++) {
				if (tabItems[i].getData().equals(node)) {
					tabItems[i].dispose();
				}
			}
		}
		catch (Exception e) {
			System.out.println("Cannot close the editor tab.");
		}
	}
	
	/**
	 * Rename the heading of a tab when a node changes his name.
	 * @param node INodeType The node that is belonging with the tab.
	 */
	public void renameTab(INodeType node) {
		
		TabItem[] tabItems = this.getTabFolder().getItems();
		// find the matching tab
		for (int i = 0; i < tabItems.length; i++) {
			if (tabItems[i].getData().equals(node)) {
				tabItems[i].setText(node.getName());
			}
		}
	}

	public void changeEditorInput(INodeType n) {
		sysToShow = new SystemBehaviour(); // This is only
		sysToShow.addNode((Node) n); // for the new editor input, not for the
										// model!
		getGraphicalViewer().setContents(sysToShow);
	}

	public void changeTopControlXML() {
		stackLayout.topControl = xmlPanel;
		xmlPanel.getParent().layout();
	}

	public void changeTopControlGEF() {
		stackLayout.topControl = tabFolder;
		tabFolder.getParent().layout();
	}

	public void setFiletypeRegistration(SystemBehaviourFiletypeRegistration reg) {
		this.filetypeReg = reg;
	}

	public void selectionChanged(IWorkbenchPart part, ISelection selection) {

		StructuredSelection sel = (StructuredSelection) selection;
		Object obj = sel.getFirstElement();
		
		
		// Important: only do something if the selected
		// object belongs to the file that this editor
		// window edits!
		if (obj instanceof ISystemBehaviourObject) {
			if (((ISystemBehaviourObject) obj).getSystemBehaviour() != system) {
				return;
			}
		}
		

		if (obj instanceof State && !(obj instanceof Task)) {
			StateEditPart sep = (StateEditPart) ((State) obj).getEditPart();
			sep.setSelected(2);
			// sep.getFigure().setVisible(false);

			sep.refresh();
			sep.getParent().refresh();

			for (INodeType n : ((IState) obj).getSystemBehaviour().getNodes()) {
				for (ITask t : n.getTasks()) {
					for (IState s : t.getStates()) {
						// if (!((State)s).getEditPart().equals(sep)) {
						((State) s).getEditPart().setSelected(0);
						((State) s).getEditPart().refresh();
						((State) s).getEditPart().getParent().refresh();
						// }
					}
				}
			}
		}
		
		
		if (obj instanceof INodeType) {

			// show only the node, that is selected in the resource tree

			changeEditorInput((Node) obj);
			try {
				tabFolder.setBackground(((Node) obj).getColor());
			} catch (Exception e) {
				tabFolder.setBackground(org.eclipse.draw2d.ColorConstants.blue);
			};

			// select the appropriate tab for the node
			system.setCurrentNode((Node) obj);
			tabFolder.setSelection(system.getNodes().indexOf((Node) obj));
		}
		
		if (obj instanceof ITask) {
			
			changeEditorInput(((Task) obj).getNode());
			try {
				tabFolder.setBackground(((Node)((Task) obj).getNode()).getColor());
			} catch (Exception e) {
				tabFolder.setBackground(org.eclipse.draw2d.ColorConstants.blue);
			};
			// select the appropriate tab for the node
			system.setCurrentNode((Node)((Task) obj).getNode());
			tabFolder.setSelection(system.getNodes().indexOf((Node)((Task) obj).getNode()));
		}
		
		if (obj instanceof IState) {

			changeEditorInput(((State) obj).getTask().getNode());
			try {
				tabFolder.setBackground(((Node)((State) obj).getTask().getNode()).getColor());
			} catch (Exception e) {
				tabFolder.setBackground(org.eclipse.draw2d.ColorConstants.blue);
			};
			// select the appropriate tab for the node
			system.setCurrentNode((Node)((State) obj).getTask().getNode());
			tabFolder.setSelection(system.getNodes().indexOf((Node)((State) obj).getTask().getNode()));
		}
		
		if (obj instanceof ITransition) {

			changeEditorInput(((Transition) obj).getState().getTask().getNode());
			try {
				tabFolder.setBackground(((Node)((Transition) obj).getState().getTask().getNode()).getColor());
			} catch (Exception e) {
				tabFolder.setBackground(org.eclipse.draw2d.ColorConstants.blue);
			};
			// select the appropriate tab for the node
			system.setCurrentNode((Node)((Transition) obj).getState().getTask().getNode());
			tabFolder.setSelection(system.getNodes().indexOf((Node)((Transition) obj).getState().getTask().getNode()));
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		filetypeReg.editorDisposed(this);
	}

	public SystemBehaviourFiletypeRegistration getFiletypeReg() {
		return filetypeReg;
	}

	public void setFiletypeReg(SystemBehaviourFiletypeRegistration filetypeReg) {
		this.filetypeReg = filetypeReg;
	}

	public TabFolder getTabFolder() {
		return tabFolder;
	}

	public void setTabFolder(TabFolder tabFolder) {
		this.tabFolder = tabFolder;
	}
	
	public CommandStack getTheCommandStack() {
		return getCommandStack();
	}

}