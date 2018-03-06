package de.peerthing.scenarioeditor.model;

import de.peerthing.scenarioeditor.model.impl.*;

/**
 * The methods in this class return concrete instances of the
 * scenario interfaces.
 * 
 * @author Michael Gottschalk
 * @review Sebastian Rohjans 27.03.2006
 *
 */
public class ScenarioFactory {
	public static IScenario createScenario() {
		return new Scenario();
	}
	
	/**
	 * @return a new behaviour which belongs to the handed parent	 
	 */
	public static IUserBehaviour createBehaviour(INodeCategory parent) {
		IUserBehaviour beh = new UserBehaviour();
		beh.setNodeCategory(parent);		
		return beh;
	}
	
	/**
	 * @return a new callbehaviour which calls the handed behaviour	 
	 */
	public static ICallUserBehaviour createCallBehaviour(IScenario scenario, IUserBehaviour behaviour) {
		return new CallUserBehaviour(scenario, behaviour);
	}
	
	/**
	 * @return a new case	 
	 */
	public static ICase createCase(IScenario scenario) {
		return new Case(scenario);
	}
	
	/**
	 * @return a new connection category
	 */
	public static IConnectionCategory createConnectionCategory(IScenario scenario) {
		return new ConnectionCategory(scenario);
	}
	
	/**
	 * @return a new delay
	 */
	public static IDelay createDelay(IScenario scenario) {
		return new Delay(scenario);
	}
	
	/**
	 * @return a new listen
	 */
	public static IListen createListen(IScenario scenario) {
		return new Listen(scenario);
	}
	
	/**
	 * @return a new distribution
	 */
	public static IDistribution createDistribution(IScenario scenario) {
		return new Distribution(scenario);
	}
	
	/**
	 * @return a new linkspeed
	 */
	public static ILinkSpeed createLinkSpeed(IScenario scenario) {
		return new LinkSpeed(scenario);
	}
	
	/**
	 * @return a new loop
	 */
	public static ILoop createLoop(IScenario scenario) {
		return new Loop(scenario);
	}
	
	/**
	 * @return a new node category
	 */
	public static INodeCategory createNodeCategory(IScenario scenario) {
		return new NodeCategory(scenario);
	}
	
	/**
	 * @return a new node connection
	 */
	public static INodeConnection createNodeConnection() {
		return new NodeConnection();
	}
	
	/**
	 * @return a new node resource
	 */
	public static INodeResource createNodeResource() {
		return new NodeResource();
	}
	
	/**
	 * @return a new resource category
	 */
	public static IResourceCategory createResourceCategory(IScenario scenario) {
		return new ResourceCategory(scenario);
	}
	
	/**
	 * @return a new scenario condition
	 */
	public static IScenarioCondition createScenarioCondition(IScenario scenario) {
		return new ScenarioCondition(scenario);		
	}
	
	/**
	 * @return a new user action
	 */
	public static IUserAction createUserAction(IScenario scenario) {
		return new UserAction(scenario);
	}
    
	/**
	 * @return a new default case
	 */
    public static IDefaultCase createDefaultCase(IScenario scenario) {
        return new DefaultCase(scenario);
    }
}
