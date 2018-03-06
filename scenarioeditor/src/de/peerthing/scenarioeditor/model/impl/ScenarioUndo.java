package de.peerthing.scenarioeditor.model.impl;

import de.peerthing.scenarioeditor.ScenarioEditorPlugin;
import de.peerthing.scenarioeditor.editor.ScenarioEditor;
import de.peerthing.scenarioeditor.editor.forms.CallUserBehaviourForm;
import de.peerthing.scenarioeditor.editor.forms.CaseForm;
import de.peerthing.scenarioeditor.editor.forms.ConnectionCategoryForm;
import de.peerthing.scenarioeditor.editor.forms.DelayForm;
import de.peerthing.scenarioeditor.editor.forms.ListenForm;
import de.peerthing.scenarioeditor.editor.forms.LoopForm;
import de.peerthing.scenarioeditor.editor.forms.NodeBehaviourOverview;
import de.peerthing.scenarioeditor.editor.forms.NodeCategoryForm;
import de.peerthing.scenarioeditor.editor.forms.NodeConnectionForm;
import de.peerthing.scenarioeditor.editor.forms.NodeConnectionOverview;
import de.peerthing.scenarioeditor.editor.forms.NodeResourceForm;
import de.peerthing.scenarioeditor.editor.forms.NodeResourceOverview;
import de.peerthing.scenarioeditor.editor.forms.UserActionForm;
import de.peerthing.scenarioeditor.editor.forms.UserBehaviourForm;
import de.peerthing.scenarioeditor.model.ICallUserBehaviour;
import de.peerthing.scenarioeditor.model.ICase;
import de.peerthing.scenarioeditor.model.ICommand;
import de.peerthing.scenarioeditor.model.ICommandContainer;
import de.peerthing.scenarioeditor.model.IConnectionCategory;
import de.peerthing.scenarioeditor.model.IDelay;
import de.peerthing.scenarioeditor.model.IListen;
import de.peerthing.scenarioeditor.model.ILoop;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.INodeConnection;
import de.peerthing.scenarioeditor.model.INodeResource;
import de.peerthing.scenarioeditor.model.IResourceCategory;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IScenarioCondition;
import de.peerthing.scenarioeditor.model.IScenarioObject;
import de.peerthing.scenarioeditor.model.IUserAction;
import de.peerthing.scenarioeditor.model.IUserBehaviour;

/**
 * @author Patrik Schulz
 */

public class ScenarioUndo {

    private byte operation = 0;

    private IScenarioObject chosenObject;

    private IScenarioObject involvedObject;

    /**
     * A new Undo object is created. It contains all the necessary information
     * that are needed to undo an operation
     * @param chosenObject Element that the user chose to make an operation on
     * @param involvedObject Element that is also involve in the operation
     * 		  (for example an element that is added or an version of
     *        the chosen object that is i little bit older so the change
     *        can be undone)
     * @param operation encodes the operation that was done
     */
    public ScenarioUndo(IScenarioObject chosenObject,
            IScenarioObject involvedObject, byte operation) {
        this.chosenObject = chosenObject;
        this.involvedObject = involvedObject;
        this.operation = operation;
    }

    /**
     * Returns if an undo is currently possible
     * 
     * @return
     */
    public static boolean canUndo() {
        return ScenarioEditorPlugin.getDefault().getUndoList().size() > 0;
    }

    /**
     * Returns if a redo operation is currently possible
     * @return
     */
    public static boolean canRedo() {
        return ScenarioEditorPlugin.getDefault().getRedoList().size() > 0;
    }

    /**
     * the last operation (that is saved in the undo list) gets undone here 
     * @param undo
     */
    public static void executeUndo(boolean undo) {
        if (undo && ScenarioEditorPlugin.getDefault().getUndoList().size() == 0) {
            return;
        }
        if (!undo
                && ScenarioEditorPlugin.getDefault().getRedoList().size() == 0) {
            return;
        }
        ScenarioUndo scenarioUndo;
        ScenarioEditor editor = ScenarioEditorPlugin.getDefault().getEditor();
        if (undo) {
            scenarioUndo = ScenarioEditorPlugin.getDefault().getUndoList().get(
                    ScenarioEditorPlugin.getDefault().getUndoList().size() - 1);
            // ScenarioEditorPlugin.getDefault().setRedoPosible(true);
        } else {
            scenarioUndo = ScenarioEditorPlugin.getDefault().getRedoList().get(
                    ScenarioEditorPlugin.getDefault().getRedoList().size() - 1);
        }
        if (scenarioUndo.getOperation() == UndoOperationValues.wasMovedUp) {

            if (undo) {
                ScenarioEditorPlugin.getDefault().getRedoList().add(
                        new ScenarioUndo(scenarioUndo.getChosenObject(), null,
                                UndoOperationValues.wasMovedDown));
            } else {
                ((ListOfUndos) ScenarioEditorPlugin.getDefault().getUndoList())
                        .addForRedo(new ScenarioUndo(scenarioUndo
                                .getChosenObject(), null,
                                UndoOperationValues.wasMovedDown));
            }

            if (scenarioUndo.getChosenObject() instanceof ICommand) {
                ICommand command = (ICommand) scenarioUndo.getChosenObject();
                ICommandContainer container = command.getCommandContainer();
                for (int i = 0; i < container.getCommands().size(); i++) {
                    if (container.getCommands().get(i).equals(command)) {
                        container.getCommands().remove(i);
                        container.getCommands().add(i + 1, command);
                        ScenarioEditorPlugin.getDefault().getNavigationTree()
                                .refresh(container);
                        break;
                    }
                }
            }
        }
        if (scenarioUndo.getOperation() == UndoOperationValues.wasMovedDown) {

            if (undo) {
                ScenarioEditorPlugin.getDefault().getRedoList().add(
                        new ScenarioUndo(scenarioUndo.getChosenObject(), null,
                                UndoOperationValues.wasMovedUp));
            } else {
                ((ListOfUndos) ScenarioEditorPlugin.getDefault().getUndoList())
                        .addForRedo(new ScenarioUndo(scenarioUndo
                                .getChosenObject(), null,
                                UndoOperationValues.wasMovedUp));
            }

            if (scenarioUndo.getChosenObject() instanceof ICommand) {
                ICommand command = (ICommand) scenarioUndo.getChosenObject();
                ICommandContainer container = command.getCommandContainer();
                for (int i = 0; i < container.getCommands().size(); i++) {
                    if (container.getCommands().get(i).equals(command)) {
                        container.getCommands().remove(i);
                        container.getCommands().add(i - 1, command);
                        ScenarioEditorPlugin.getDefault().getNavigationTree()
                                .refresh(container);
                        break;
                    }
                }
            }
        }
        if (scenarioUndo.getOperation() == UndoOperationValues.addWasDone) {

            int position = 0;

            if (scenarioUndo.getInvolvedObject() instanceof ICommand) {
                position = (ProvidesPositionOfCommand
                        .providesPosition((ICommand) scenarioUndo
                                .getInvolvedObject()));
            }
            if (scenarioUndo.getInvolvedObject() instanceof IResourceCategory) {
                position = (ProvidesPositionOfCommand
                        .providesPosition((IResourceCategory) scenarioUndo
                                .getInvolvedObject()));
            }
            if (scenarioUndo.getInvolvedObject() instanceof IConnectionCategory) {
                position = (ProvidesPositionOfCommand
                        .providesPosition((IConnectionCategory) scenarioUndo
                                .getInvolvedObject()));
            }
            if (scenarioUndo.getInvolvedObject() instanceof INodeCategory) {
                position = (ProvidesPositionOfCommand
                        .providesPosition((INodeCategory) scenarioUndo
                                .getInvolvedObject()));
            }
            if (scenarioUndo.getInvolvedObject() instanceof INodeResource) {
                position = (ProvidesPositionOfCommand
                        .providesPosition((INodeResource) scenarioUndo
                                .getInvolvedObject()));
            }
            if (scenarioUndo.getInvolvedObject() instanceof INodeConnection) {
                position = (ProvidesPositionOfCommand
                        .providesPosition((INodeConnection) scenarioUndo
                                .getInvolvedObject()));
            }
            if (scenarioUndo.getInvolvedObject() instanceof IUserBehaviour) {
                position = (ProvidesPositionOfCommand
                        .providesPosition((IUserBehaviour) scenarioUndo
                                .getInvolvedObject()));
            }
            if (scenarioUndo.getInvolvedObject() instanceof Case) {
                position = (ProvidesPositionOfCommand
                        .providesPosition((Case) scenarioUndo
                                .getInvolvedObject()));
            }

            INodeConnection savesPosition = new NodeConnection(position);

            if (undo) {
                ScenarioEditorPlugin.getDefault().getRedoList().add(
                        new ScenarioUndo(scenarioUndo.getInvolvedObject(),
                                savesPosition,
                                UndoOperationValues.deleteWasDone));
            } else {
                ((ListOfUndos) ScenarioEditorPlugin.getDefault().getUndoList())
                        .addForRedo(new ScenarioUndo(scenarioUndo
                                .getInvolvedObject(), savesPosition,
                                UndoOperationValues.deleteWasDone));
            }

            if (scenarioUndo.getChosenObject() instanceof IScenario) {
                IScenario scenario = (IScenario) scenarioUndo.getChosenObject();
                if (scenarioUndo.getInvolvedObject() instanceof IResourceCategory) {
                    IResourceCategory resCat = (IResourceCategory) scenarioUndo
                            .getInvolvedObject();
                    scenario.getResourceCategories().remove(resCat);
                    ScenarioEditorPlugin.getDefault().getNavigationTree()
                            .refresh(scenario.getResourceCategories());
                }
                if (scenarioUndo.getInvolvedObject() instanceof IConnectionCategory) {
                    IConnectionCategory conCat = (IConnectionCategory) scenarioUndo
                            .getInvolvedObject();
                    scenario.getConnectionCategories().remove(conCat);
                    ScenarioEditorPlugin.getDefault().getNavigationTree()
                            .refresh(scenario.getConnectionCategories());
                }
                if (scenarioUndo.getInvolvedObject() instanceof INodeCategory) {
                    INodeCategory nodeCat = (INodeCategory) scenarioUndo
                            .getInvolvedObject();
                    scenario.getNodeCategories().remove(nodeCat);
                    ScenarioEditorPlugin.getDefault().getNavigationTree()
                            .refresh(scenario.getNodeCategories());
                }
            }
            if (scenarioUndo.getChosenObject() instanceof IUserBehaviour) {
                IUserBehaviour behaviour = (IUserBehaviour) scenarioUndo
                        .getChosenObject();
                if (scenarioUndo.getInvolvedObject() instanceof ICommand) {
                    ICommand command = (ICommand) scenarioUndo
                            .getInvolvedObject();
                    behaviour.getCommands().remove(command);
                    ScenarioEditorPlugin.getDefault().getNavigationTree()
                            .refresh(behaviour);
                }
            }
            if (scenarioUndo.getChosenObject() instanceof ICommandContainer) {
                ICommandContainer container = (ICommandContainer) scenarioUndo
                        .getChosenObject();
                if (scenarioUndo.getInvolvedObject() instanceof ICommand) {
                    ICommand command = (ICommand) scenarioUndo
                            .getInvolvedObject();
                    container.getCommands().remove(command);
                    ScenarioEditorPlugin.getDefault().getNavigationTree()
                            .refresh(container);
                }
            }
            if (scenarioUndo.getChosenObject() instanceof INodeCategory) {
                INodeCategory nodeCategory = (INodeCategory) scenarioUndo
                        .getChosenObject();
                if (scenarioUndo.getInvolvedObject() instanceof INodeConnection) {
                    INodeConnection nodeConnection = (INodeConnection) scenarioUndo
                            .getInvolvedObject();
                    nodeCategory.getConnections().remove(nodeConnection);
                    NodeConnectionOverview nodeConnectionOverview = (NodeConnectionOverview) editor
                            .getForm(nodeCategory.getConnections());
                    nodeConnectionOverview
                            .update(nodeCategory.getConnections());
                }
                if (scenarioUndo.getInvolvedObject() instanceof INodeResource) {
                    INodeResource nodeResource = (INodeResource) scenarioUndo
                            .getInvolvedObject();
                    nodeCategory.getResources().remove(nodeResource);
                    NodeResourceOverview nodeResourceOverview = (NodeResourceOverview) editor
                            .getForm(nodeCategory.getResources());
                    nodeResourceOverview.update(nodeCategory.getResources());
                }
                if (scenarioUndo.getInvolvedObject() instanceof IUserBehaviour) {
                    IUserBehaviour behaviour = (IUserBehaviour) scenarioUndo
                            .getInvolvedObject();
                    nodeCategory.getBehaviours().remove(behaviour);
                    NodeBehaviourOverview nodeBehaviourOverview = (NodeBehaviourOverview) editor
                            .getForm(nodeCategory.getBehaviours());
                    nodeBehaviourOverview.update(nodeCategory.getBehaviours());
                }
            }
            if (scenarioUndo.getChosenObject() instanceof IScenarioCondition) {
                IScenarioCondition condition = (IScenarioCondition) scenarioUndo
                        .getChosenObject();
                if (scenarioUndo.getInvolvedObject() instanceof ICase) {
                    ICase case1 = (ICase) scenarioUndo.getInvolvedObject();
                    condition.getCases().remove(case1);
                    ScenarioEditorPlugin.getDefault().getNavigationTree()
                            .refresh(condition);
                }
            }
        }

        if (scenarioUndo.getOperation() == UndoOperationValues.deleteWasDone) {

            IScenarioObject father = null;
            if (scenarioUndo.getChosenObject() instanceof ICommand) {
                father = ((ICommand) scenarioUndo.getChosenObject())
                        .getCommandContainer();
            }
            if (scenarioUndo.getChosenObject() instanceof IResourceCategory) {
                father = ((IResourceCategory) scenarioUndo.getChosenObject())
                        .getScenario();
            }
            if (scenarioUndo.getChosenObject() instanceof IConnectionCategory) {
                father = ((IConnectionCategory) scenarioUndo.getChosenObject())
                        .getScenario();
            }
            if (scenarioUndo.getChosenObject() instanceof INodeCategory) {
                father = ((INodeCategory) scenarioUndo.getChosenObject())
                        .getScenario();
            }
            if (scenarioUndo.getChosenObject() instanceof INodeResource) {
                father = ((INodeResource) scenarioUndo.getChosenObject())
                        .getNode();
            }
            if (scenarioUndo.getChosenObject() instanceof INodeConnection) {
                father = ((INodeConnection) scenarioUndo.getChosenObject())
                        .getNode();
            }
            if (scenarioUndo.getChosenObject() instanceof IUserBehaviour) {
                father = ((IUserBehaviour) scenarioUndo.getChosenObject())
                        .getNodeCategory();
            }
            if (scenarioUndo.getChosenObject() instanceof Case) {
                father = ((Case) scenarioUndo.getChosenObject())
                        .getScenarioCondition();
            }

            if (undo) {
                ScenarioEditorPlugin.getDefault().getRedoList().add(
                        new ScenarioUndo(father,
                                scenarioUndo.getChosenObject(),
                                UndoOperationValues.addWasDone));
            } else {
                ((ListOfUndos) ScenarioEditorPlugin.getDefault().getUndoList())
                        .addForRedo(new ScenarioUndo(father, scenarioUndo
                                .getChosenObject(),
                                UndoOperationValues.addWasDone));
            }

            if (scenarioUndo.getChosenObject() instanceof INodeCategory) {
                INodeCategory nodeCategory = (INodeCategory) scenarioUndo
                        .getChosenObject();
                INodeConnection positionSave = (INodeConnection) scenarioUndo
                        .getInvolvedObject();
                nodeCategory.getScenario().getNodeCategories().add(
                        positionSave.getNumberOfNodes(), nodeCategory);
                ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(
                        nodeCategory.getScenario().getNodeCategories());
            }
            if (scenarioUndo.getChosenObject() instanceof IConnectionCategory) {
                IConnectionCategory connectionCategory = (IConnectionCategory) scenarioUndo
                        .getChosenObject();
                INodeConnection positionSave = (INodeConnection) scenarioUndo
                        .getInvolvedObject();
                connectionCategory.getScenario().getConnectionCategories().add(
                        positionSave.getNumberOfNodes(), connectionCategory);
                ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(
                        connectionCategory.getScenario()
                                .getConnectionCategories());
            }
            if (scenarioUndo.getChosenObject() instanceof IResourceCategory) {
                IResourceCategory resourceCategory = (IResourceCategory) scenarioUndo
                        .getChosenObject();
                INodeConnection positionSave = (INodeConnection) scenarioUndo
                        .getInvolvedObject();
                resourceCategory.getScenario().getResourceCategories().add(
                        positionSave.getNumberOfNodes(), resourceCategory);
                ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(
                        resourceCategory.getScenario().getResourceCategories());
            }
            if (scenarioUndo.getChosenObject() instanceof ICommand) {
                ICommand command = (ICommand) scenarioUndo.getChosenObject();
                INodeConnection positionSave = (INodeConnection) scenarioUndo
                        .getInvolvedObject();
                command.getCommandContainer().getCommands().add(
                        positionSave.getNumberOfNodes(), command);
                ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(
                        command.getCommandContainer());
            }
            if (scenarioUndo.getChosenObject() instanceof IUserBehaviour) {
                IUserBehaviour behaviour = (IUserBehaviour) scenarioUndo
                        .getChosenObject();
                INodeConnection positionSave = (INodeConnection) scenarioUndo
                        .getInvolvedObject();
                behaviour.getNodeCategory().getBehaviours().add(
                        positionSave.getNumberOfNodes(), behaviour);
                ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(
                        behaviour.getNodeCategory().getBehaviours());
            }
            if (scenarioUndo.getChosenObject() instanceof INodeConnection) {
                INodeConnection nodeConnection = (INodeConnection) scenarioUndo
                        .getChosenObject();
                INodeConnection positionSave = (INodeConnection) scenarioUndo
                        .getInvolvedObject();
                nodeConnection.getNode().getConnections().add(
                        positionSave.getNumberOfNodes(), nodeConnection);
                ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(
                        nodeConnection.getNode().getConnections());
            }
            if (scenarioUndo.getChosenObject() instanceof INodeResource) {
                INodeResource nodeResource = (INodeResource) scenarioUndo
                        .getChosenObject();
                INodeConnection positionSave = (INodeConnection) scenarioUndo
                        .getInvolvedObject();
                nodeResource.getNode().getResources().add(
                        positionSave.getNumberOfNodes(), nodeResource);
                ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(
                        nodeResource.getNode().getResources());
            }

            if (scenarioUndo.getChosenObject() instanceof ICase) {
                Case case1 = (Case) scenarioUndo.getChosenObject();
                INodeConnection positionSave = (INodeConnection) scenarioUndo
                        .getInvolvedObject();
                case1.getScenarioCondition().getCases().add(
                        positionSave.getNumberOfNodes(), case1);
                ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(
                        case1.getScenarioCondition());
            }
        }

        if (scenarioUndo.getOperation() == UndoOperationValues.valueChanged) {

            IScenarioObject ori = scenarioUndo.getChosenObject();
            IScenarioObject old = null;

            if (ori instanceof IResourceCategory) {
                old = new ResourceCategory((IResourceCategory) ori);
            }
            if (ori instanceof IConnectionCategory) {
                old = new ConnectionCategory((IConnectionCategory) ori);
            }
            if (ori instanceof INodeCategory) {
                old = new NodeCategory((INodeCategory) ori);
            }
            if (ori instanceof IUserBehaviour) {
                old = new UserBehaviour((IUserBehaviour) ori);
            }
            if (ori instanceof INodeResource) {
                old = new NodeResource((INodeResource) ori);
            }
            if (ori instanceof INodeConnection) {
                old = new NodeConnection((INodeConnection) ori);
            }
            if (ori instanceof ILoop) {
                old = new Loop((ILoop) ori);
            }
            if (ori instanceof IDelay) {
                old = new Delay((IDelay) ori);
            }
            if (ori instanceof ICallUserBehaviour) {
                old = new CallUserBehaviour((CallUserBehaviour) ori);
            }
            if (ori instanceof IListen) {
                old = new Listen((IListen) ori);
            }
            if (ori instanceof IUserAction) {
                old = new UserAction((IUserAction) ori);
            }
            if (ori instanceof ILoop) {
                old = new Loop((ILoop) ori);
            }
            if (ori instanceof ICase) {
                old = new Case((Case) ori);
            }

            if (undo) {
                ScenarioEditorPlugin.getDefault().getRedoList().add(
                        new ScenarioUndo(ori, old,
                                UndoOperationValues.valueChanged));
            } else {
                ((ListOfUndos) ScenarioEditorPlugin.getDefault().getUndoList())
                        .addForRedo(new ScenarioUndo(ori, old,
                                UndoOperationValues.valueChanged));
            }

            if (scenarioUndo.getChosenObject() instanceof IResourceCategory) {
                IResourceCategory resOri = (IResourceCategory) scenarioUndo
                        .getChosenObject();
                // System.out.println(resOri.getScenario().getName());
                IResourceCategory resOld = (IResourceCategory) scenarioUndo
                        .getInvolvedObject();
                resOri.setDiversity(resOld.getDiversity());
                resOri.setPopularity(resOld.getPopularity());
                resOri.setName(resOld.getName());
                resOri.setSize(resOld.getSize());
            }

            if (scenarioUndo.getChosenObject() instanceof IConnectionCategory) {
                IConnectionCategory connectionOri = (IConnectionCategory) scenarioUndo
                        .getChosenObject();
                IConnectionCategory connectionOld = (IConnectionCategory) scenarioUndo
                        .getInvolvedObject();
                connectionOri
                        .setDownlinkSpeed(connectionOld.getDownlinkSpeed());
                connectionOri.setUplinkSpeed(connectionOld.getUplinkSpeed());
                connectionOri.setDuplex(connectionOld.getDuplex());
                connectionOri.setName(connectionOld.getName());
                ConnectionCategoryForm connectionCatForm = (ConnectionCategoryForm) editor
                        .getForm(connectionOri);
                connectionCatForm.update(connectionOri);
            }

            if (scenarioUndo.getChosenObject() instanceof INodeCategory) {
                INodeCategory nodeCategoryOri = (INodeCategory) scenarioUndo
                        .getChosenObject();
                INodeCategory nodeCategoryOld = (INodeCategory) scenarioUndo
                        .getInvolvedObject();
                nodeCategoryOri.setName(nodeCategoryOld.getName());
                nodeCategoryOri.setNodeType(nodeCategoryOld.getNodeType());
                nodeCategoryOri.setPrimaryBehaviour(nodeCategoryOld
                        .getPrimaryBehaviour());
                NodeCategoryForm nodeCategoryForm = (NodeCategoryForm) editor
                        .getForm(nodeCategoryOri);
                nodeCategoryForm.update(nodeCategoryOri);
            }

            if (scenarioUndo.getChosenObject() instanceof IUserBehaviour) {
                IUserBehaviour behOri = (IUserBehaviour) scenarioUndo
                        .getChosenObject();
                IUserBehaviour behOld = (IUserBehaviour) scenarioUndo
                        .getInvolvedObject();
                behOri.setName(behOld.getName());
                ScenarioEditorPlugin.getDefault().getNavigationTree().update(
                        behOri);
                UserBehaviourForm behaviourForm = (UserBehaviourForm) editor
                        .getForm(behOri);
                behaviourForm.update(behOri);
            }
            if (scenarioUndo.getChosenObject() instanceof ILoop) {
                ILoop loopOri = (ILoop) scenarioUndo.getChosenObject();
                ILoop loopOld = (ILoop) scenarioUndo.getInvolvedObject();
                loopOri.setDistribution(loopOld.getDistribution());
                loopOri.setUntilCondition(loopOld.getUntilCondition());

                LoopForm behaviourForm = (LoopForm) editor.getForm(loopOri);
                behaviourForm.update(loopOri);
            }

            if (scenarioUndo.getChosenObject() instanceof ICallUserBehaviour) {
                CallUserBehaviour callBehaviourOri = (CallUserBehaviour) scenarioUndo
                        .getChosenObject();
                CallUserBehaviour callBehaviourOld = (CallUserBehaviour) scenarioUndo
                        .getInvolvedObject();
                callBehaviourOri.setBehaviour(callBehaviourOld.getBehaviour());
                callBehaviourOri.setProbability(callBehaviourOld
                        .getProbability());
                callBehaviourOri.setStartTask(callBehaviourOld.isStartTask());
                CallUserBehaviourForm callBehaviourForm = (CallUserBehaviourForm) editor
                        .getForm(callBehaviourOri);
                callBehaviourForm.update(callBehaviourOri);
            }
            if (scenarioUndo.getChosenObject() instanceof IDelay) {
                IDelay delayOri = (IDelay) scenarioUndo.getChosenObject();
                IDelay delayOld = (IDelay) scenarioUndo.getInvolvedObject();
                delayOri.setDistribution(delayOld.getDistribution());
                DelayForm delayForm = (DelayForm) editor.getForm(delayOri);
                delayForm.update(delayOri);
            }

            if (scenarioUndo.getChosenObject() instanceof IListen) {
                IListen listenOri = (IListen) scenarioUndo.getChosenObject();
                IListen listenOld = (IListen) scenarioUndo.getInvolvedObject();
                listenOri.setDistribution(listenOld.getDistribution());
                listenOri.setEvent(listenOld.getEvent());
                ListenForm listenForm = (ListenForm) editor.getForm(listenOri);
                listenForm.update(listenOri);
            }

            if (scenarioUndo.getChosenObject() instanceof IUserAction) {
                IUserAction userActionOri = (IUserAction) scenarioUndo
                        .getChosenObject();
                IUserAction userActionOld = (IUserAction) scenarioUndo
                        .getInvolvedObject();
                userActionOri.setProbability(userActionOld.getProbability());
                userActionOri.setName(userActionOld.getName());
                userActionOri.getParameters().clear();
                userActionOri.getParameters().putAll(
                        userActionOld.getParameters());
                UserActionForm userActionForm = (UserActionForm) editor
                        .getForm(userActionOri);
                userActionForm.update(userActionOri);
            }

            if (scenarioUndo.getChosenObject() instanceof ICase) {
                ICase caseOri = (ICase) scenarioUndo.getChosenObject();
                ICase caseOld = (ICase) scenarioUndo.getInvolvedObject();
                caseOri.setCondition(caseOld.getCondition());
                caseOri.setConditionUsed(caseOld.isConditionUsed());
                caseOri.setProbability(caseOld.getProbability());
                CaseForm caseForm = (CaseForm) editor.getForm(caseOri);
                caseForm.update(caseOri);
            }

            if (scenarioUndo.getChosenObject() instanceof INodeResource) {
                INodeResource nodeResourceOri = (INodeResource) scenarioUndo
                        .getChosenObject();
                INodeResource nodeResourceOld = (INodeResource) scenarioUndo
                        .getInvolvedObject();
                nodeResourceOri.setNumberDistribution(nodeResourceOld
                        .getNumberDistribution());
                NodeResourceForm nodeResourceForm = (NodeResourceForm) editor
                        .getForm(nodeResourceOri);
                nodeResourceForm.update(nodeResourceOri);
            }
            if (scenarioUndo.getChosenObject() instanceof INodeConnection) {
                INodeConnection nodeConnectionOri = (INodeConnection) scenarioUndo
                        .getChosenObject();
                INodeConnection nodeConnectionOld = (INodeConnection) scenarioUndo
                        .getInvolvedObject();
                nodeConnectionOri.setNumberOfNodes(nodeConnectionOld
                        .getNumberOfNodes());
                NodeConnectionForm nodeConnectionForm = (NodeConnectionForm) editor
                        .getForm(nodeConnectionOri);
                nodeConnectionForm.update(nodeConnectionOri);
            }

        }
        if (scenarioUndo.getOperation() == UndoOperationValues.deleteWasDone) {
            if (scenarioUndo.getChosenObject() instanceof INodeConnection) {
                INodeConnection nCon = (INodeConnection) scenarioUndo
                        .getChosenObject();
                editor.showFormFor(nCon.getNode().getConnections());
            }
            if (scenarioUndo.getChosenObject() instanceof INodeResource) {
                INodeResource nRes = (INodeResource) scenarioUndo
                        .getChosenObject();
                editor.showFormFor(nRes.getNode().getResources());
            }
            if (scenarioUndo.getChosenObject() instanceof IUserBehaviour) {
                IUserBehaviour behaviour = (IUserBehaviour) scenarioUndo
                        .getChosenObject();
                editor.showFormFor(behaviour.getNodeCategory().getBehaviours());
            }
        } else if (scenarioUndo.getChosenObject() instanceof INodeCategory
                && scenarioUndo.getInvolvedObject() instanceof INodeConnection) {
            editor.showFormFor(((INodeCategory) scenarioUndo.getChosenObject())
                    .getConnections());
        } else if (scenarioUndo.getChosenObject() instanceof INodeCategory
                && scenarioUndo.getInvolvedObject() instanceof INodeResource) {
            editor.showFormFor(((INodeCategory) scenarioUndo.getChosenObject())
                    .getResources());
        } else if (scenarioUndo.getChosenObject() instanceof INodeCategory
                && scenarioUndo.getInvolvedObject() instanceof IUserBehaviour) {
            editor.showFormFor(((INodeCategory) scenarioUndo.getChosenObject())
                    .getBehaviours());
        } else {
            editor.showFormFor(scenarioUndo.getChosenObject());
        }

        ScenarioEditorPlugin.getDefault().getNavigationTree().refresh(
                scenarioUndo.getChosenObject());
        ScenarioEditorPlugin.getDefault().getNavigationTree().update(
                scenarioUndo.getChosenObject());
        /*
         * ScenarioEditorPlugin.getDefault().getRedoList().add(
         * ScenarioEditorPlugin.getDefault().getUndoList().
         * get(ScenarioEditorPlugin.getDefault().getUndoList().size()-1));
         */
        if (undo) {
            ScenarioEditorPlugin.getDefault().getUndoList().remove(
                    ScenarioEditorPlugin.getDefault().getUndoList().size() - 1);
        }
        if (!undo) {
            ScenarioEditorPlugin.getDefault().getRedoList().remove(
                    ScenarioEditorPlugin.getDefault().getRedoList().size() - 1);
        }

        // After undoing an operation, the editor must be
        // set dirty
        ScenarioEditorPlugin.getDefault().getFiletypeRegistration().getEditor(
                scenarioUndo.getChosenObject().getScenario()).setDirty();
    }

    public IScenarioObject getChosenObject() {
        return chosenObject;
    }
    
    public void setChosenObject(IScenarioObject chosenObject) {
        this.chosenObject = chosenObject;
    }

    public IScenarioObject getInvolvedObject() {
        return involvedObject;
    }

    public void setInvolvedObject(IScenarioObject involvedObject) {
        this.involvedObject = involvedObject;
    }

    public byte getOperation() {
        return operation;
    }

    public void setOperation(byte operation) {
        this.operation = operation;
    }
    
    /**
     * when a redo is executed the parameter false is handed over, when
     * calling the method executeUndo. That way it is decoded, that a
     * redo has to be done.
     */
    public static void executeRedo() {
        executeUndo(false);
    }
}
