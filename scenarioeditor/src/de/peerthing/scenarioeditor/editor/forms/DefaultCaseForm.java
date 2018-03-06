package de.peerthing.scenarioeditor.editor.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

import de.peerthing.scenarioeditor.model.ICallUserBehaviour;
import de.peerthing.scenarioeditor.model.IDefaultCase;
import de.peerthing.scenarioeditor.model.IDelay;
import de.peerthing.scenarioeditor.model.IListen;
import de.peerthing.scenarioeditor.model.ILoop;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IScenarioCondition;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.IUserAction;
import de.peerthing.scenarioeditor.model.IUserBehaviour;
import de.peerthing.scenarioeditor.model.ScenarioFactory;
import de.peerthing.scenarioeditor.model.impl.ExecuteAddition;
import de.peerthing.scenarioeditor.model.impl.ProvideUserBehaviour;
import de.peerthing.scenarioeditor.editor.ScenarioEditor;

/**
 * This class manages a form with which you can manipulate the data of a
 * Defaultcase
 *
 * @author Patrik, Petra (dynamic help)
 * @reviewer Petra 24.03.2006
 */
public class DefaultCaseForm extends AbstractScenarioEditorForm {
    
    /**
     * A new default case
     */
    IDefaultCase defaultCase;
    
    /**
     * A button to create a new loop
     */
    Button createLoopButton;
    
    /**
     * A button to create a new delay
     */
    Button createDelayButton;
    
    /**
     * A button to create a new listen
     */
    Button createListenButton;;
    
    /**
     * A button to create a new callBehaviour
     */
    Button createCallBehaviourButton;
    
    /**
     * A button to create a new action
     */
    Button createActionButton;
    
    /**
     * A button to create a new condition
     */
    Button createConditionButton;
    
    /**
     * The current scenario
     */
    IScenario scenario;

    /**
     * Gui is initialised in the constructor
     *
     * @param container
     */
    public DefaultCaseForm() {
    }

    /**
     * The actions of the user are handled here. The Data the user changed will
     * be saved in the DefaultCase-Object.
     */
    public void widgetSelected(SelectionEvent e) {
        Object source = e.getSource();

        if (source == createLoopButton) {
            ILoop newLoop = ScenarioFactory.createLoop(scenario);
            ExecuteAddition.addScenarioObjekt(defaultCase, newLoop);
            scenarioEditor.setDirty();
        }

        if (source == createDelayButton) {
            IDelay newDelay = ScenarioFactory.createDelay(scenario);
            ExecuteAddition.addScenarioObjekt(defaultCase, newDelay);
            scenarioEditor.setDirty();
        }
        
        if (source == createListenButton) {
            IListen newListen = ScenarioFactory.createListen(scenario);
            ExecuteAddition.addScenarioObjekt(defaultCase, newListen);
            scenarioEditor.setDirty();
        }

        if (source == createCallBehaviourButton) {
        	IUserBehaviour behaviourToCall = ProvideUserBehaviour.provideBehaviour(defaultCase);
			ICallUserBehaviour newCallBehaviour = ScenarioFactory.createCallBehaviour(scenario, behaviourToCall);                   
			ExecuteAddition.addScenarioObjekt(defaultCase, newCallBehaviour);
            scenarioEditor.setDirty();
        }

        if (source == createConditionButton) {
            IScenarioCondition newScenarioCondition = ScenarioFactory
                    .createScenarioCondition(scenario);
            ExecuteAddition.addScenarioObjekt(defaultCase, newScenarioCondition);
            IDefaultCase ca = ScenarioFactory.createDefaultCase(scenario);
            newScenarioCondition.setDefaultCase(ca);            
            scenarioEditor.setDirty();
        }

        if (source == createActionButton) {
            IUserAction newAction = ScenarioFactory.createUserAction(scenario);
            ExecuteAddition.addScenarioObjekt(defaultCase, newAction);
            scenarioEditor.setDirty();
        }
    }

	@Override
	public boolean aboutToClose() {
		return true;
	}

	@Override
	public void applyAllChanges() {
	}
    
     /**
     * This method visualises the given inputs
     */
	@Override
	public void createForm(Composite parent, ScenarioEditor editor) {
		super.createForm(parent, editor);

        form.getBody().setLayout(new GridLayout(3, false));
        form.setText("Default Case:");

        GridData sameWidth = new GridData();
		sameWidth.widthHint = 150;
        
        createLoopButton = toolkit.createButton(form.getBody(), "Create Loop",
                SWT.NONE);
        createLoopButton.setLayoutData(sameWidth);       
        createLoopButton.addSelectionListener(this);

        createDelayButton = toolkit.createButton(form.getBody(),
                "Create Delay", SWT.NONE);
        createDelayButton.setLayoutData(sameWidth);
        createDelayButton.addSelectionListener(this);
        
        createListenButton = toolkit.createButton(form.getBody(),
                "Create Listen", SWT.NONE);
        createListenButton.setLayoutData(sameWidth);
        createListenButton.addSelectionListener(this);


        createCallBehaviourButton = toolkit.createButton(form.getBody(),
                "Create CallBehaviour", SWT.NONE);
        createCallBehaviourButton.setLayoutData(sameWidth);
        createCallBehaviourButton.addSelectionListener(this);

        createActionButton = toolkit.createButton(form.getBody(),
                "Create Action", SWT.NONE);
        createActionButton.setLayoutData(sameWidth);
        createActionButton.addSelectionListener(this);

        createConditionButton = toolkit.createButton(form.getBody(),
                "Create Condition", SWT.NONE);
        createConditionButton.setLayoutData(sameWidth);
        createConditionButton.addSelectionListener(this);
        
		//dynamic help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(createLoopButton, "de.peerthing.workbench.scenario_nodes");
		createLoopButton.setToolTipText("Push to create a loop for this loop");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(createDelayButton, "de.peerthing.workbench.scenario_nodes");
		createDelayButton.setToolTipText("Push to create a delay for this loop");		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(createCallBehaviourButton, "de.peerthing.workbench.scenario_nodes");
		createCallBehaviourButton.setToolTipText("Push to create a call behaviour for this loop");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(createActionButton, "de.peerthing.workbench.scenario_nodes");
		createActionButton.setToolTipText("Push to create a action for this loop");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(createConditionButton, "de.peerthing.workbench.scenario_nodes");
		createConditionButton.setToolTipText("Push to create a condition for this loop");
		PlatformUI.getWorkbench().getHelpSystem().setHelp(createListenButton, "de.peerthing.workbench.scenario_nodes");
		createListenButton.setToolTipText("Push to create a listen for this loop");     
	}
    
    /**
     * This method is used every time a change is made. The changes are assumed and the form
     * is refreshed.
     */
	@Override
	public void update(IScenarioObject object) {
		currentObject = object;
        defaultCase = (IDefaultCase) object;
        scenario = object.getScenario();
	}
}
