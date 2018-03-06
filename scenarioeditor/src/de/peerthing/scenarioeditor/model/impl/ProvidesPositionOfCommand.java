package de.peerthing.scenarioeditor.model.impl;

import de.peerthing.scenarioeditor.model.ICommand;
import de.peerthing.scenarioeditor.model.ICommandContainer;
import de.peerthing.scenarioeditor.model.IConnectionCategory;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.INodeConnection;
import de.peerthing.scenarioeditor.model.INodeResource;
import de.peerthing.scenarioeditor.model.IResourceCategory;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IScenarioCondition;
import de.peerthing.scenarioeditor.model.IUserBehaviour;

/**
* @author Patrik
* Elements can be cut. For a correct undo operation you have to save
* on which position the element was (which position of the list of
* its commandContainer or in another case of its listWithParent)
* this class contains methods to get this information  
*/
public class ProvidesPositionOfCommand {
	
	/**
	 * @param command
	 * @return position of command in its commandContainer
	 */
	public static int providesPosition(ICommand command){
		ICommandContainer container = command.getCommandContainer();
		for(int i = 0; i< container.getCommands().size(); i++){
			ICommand com = container.getCommands().get(i);
			if (com.equals(command)){
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * @param case1
	 * @return position of case in scenarioCondition
	 */
	public static int providesPosition(Case case1){
		IScenarioCondition condition = case1.getScenarioCondition();
		for(int i = 0; i< condition.getCases().size(); i++){
			Case case2 = (Case)condition.getCases().get(i);
			if (case1.equals(case2)){
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * @param resourceCat
	 * @return position of handed resourceCatagory its listWith parent
	 */
	public static int providesPosition(IResourceCategory resourceCat){
		IScenario scenario = (IScenario) resourceCat.getScenario();
		for(int i = 0; i< scenario.getResourceCategories().size(); i++){
			IResourceCategory resCat = scenario.getResourceCategories().get(i);
			if (resCat.equals(resourceCat)){
				return i;
			}
		}
		return 0;
	}
	

	/**
	 * @param connectionCategory
	 * @return position of handed connectionCatagory its listWith parent
	 */
	public static int providesPosition(IConnectionCategory connectionCat){
		IScenario scenario = (IScenario) connectionCat.getScenario();
		for(int i = 0; i< scenario.getConnectionCategories().size(); i++){
			IConnectionCategory conCat = scenario.getConnectionCategories().get(i);
			if (conCat.equals(connectionCat)){
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * @param nodeCat
	 * @return position of handed nodeCatagory its listWith parent
	 */
	public static int providesPosition(INodeCategory nodeCat){
		IScenario scenario = (IScenario) nodeCat.getScenario();
		for(int i = 0; i< scenario.getNodeCategories().size(); i++){
			INodeCategory nCat = scenario.getNodeCategories().get(i);
			if (nCat.equals(nodeCat)){
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * @param nodeRes
	 * @return position of the nodeResource in its listWithParent
	 */
	public static int providesPosition(INodeResource nodeRes){
		INodeCategory nodeCategory = (INodeCategory) nodeRes.getNode();
		for(int i = 0; i< nodeCategory.getResources().size(); i++){
			INodeResource nRes= nodeCategory.getResources().get(i);
			if (nRes.equals(nodeRes)){
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * @param nodeCon
	 * @return position of the nodeConnection in its listWithParent
	 */
	public static int providesPosition(INodeConnection nodeCon){
		INodeCategory nodeCategory = (INodeCategory) nodeCon.getNode();
		for(int i = 0; i< nodeCategory.getConnections().size(); i++){
			INodeConnection nCon= nodeCategory.getConnections().get(i);
			if (nCon.equals(nodeCon)){
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * @param nodeBeh
	 * @return position of the nodeBehaviour in its listWithParent
	 */
	public static int providesPosition(IUserBehaviour nodeBeh){
		INodeCategory nodeCategory = (INodeCategory) nodeBeh.getNodeCategory();
		for(int i = 0; i< nodeCategory.getBehaviours().size(); i++){
			IUserBehaviour nBeh= nodeCategory.getBehaviours().get(i);
			if (nBeh.equals(nodeBeh)){
				return i;
			}
		}
		return 0;
	}
}
