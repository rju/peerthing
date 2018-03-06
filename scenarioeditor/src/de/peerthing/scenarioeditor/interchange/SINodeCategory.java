package de.peerthing.scenarioeditor.interchange;

import java.util.ArrayList;
import java.util.List;

import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.IUserBehaviour;
import de.peerthing.scenarioeditor.model.INodeConnection;
import de.peerthing.scenarioeditor.model.INodeResource;

class SINodeCategory implements ISINodeCategory {
	private INodeCategory category;
	private ISIScenario scenario;
	private List<ISIBehaviour> behaviours = new ArrayList<ISIBehaviour>();
	private ISIBehaviour primaryBehaviour;
	private List<ISINodeConnection> connections = new ArrayList<ISINodeConnection>();
	private List<ISINodeResource> resources = new ArrayList<ISINodeResource>();

	public SINodeCategory(INodeCategory category, ISIScenario scenario) {
		this.category = category;
		this.scenario = scenario;

		for (INodeConnection conn : category.getConnections()) {
			connections.add(new SINodeConnection(conn, this));
		}

		for (INodeResource res : category.getResources()) {
			resources.add(new SINodeResource(res, this));
		}

		for (IUserBehaviour beh: category.getBehaviours()) {
			SIBehaviour b = new SIBehaviour(beh, this);
			behaviours.add(b);
			if (beh == category.getPrimaryBehaviour()) {
				primaryBehaviour = b;
			}
		}

		// After all behaviours have been evaluated,
		// all callBehaviours must be found and given the right
		// ISIBehaviour objects...

		for (ISIBehaviour beh : behaviours) {
			setCallBehaviours(beh);
		}

	}

	/**
	 * Searches the given command container for callBehaviour
	 * objects and sets the behaviour attributes correctly.
	 * This must be done after all behaviours are evaluated.
	 *
	 * @param container
	 */
	private void setCallBehaviours(ISICommandContainer container) {
		for (ISICommand comm : container.getCommands()) {
			if (comm instanceof SICallBehaviour) {
				SICallBehaviour beh = ((SICallBehaviour) comm);
				beh.setBehaviour(getBehaviour(beh.getBehaviourName()));
			} else if (comm instanceof ISICondition) {
				for (ISICase c : ((ISICondition) comm).getCases()) {
					setCallBehaviours(c);
				}
			} else if (comm instanceof ISILoop) {
				setCallBehaviours((ISILoop) comm);
			}
		}
	}

	/**
	 * Returns the behaviour with the given name, or null
	 * if no such behaviour exists.
	 *
	 * @param name
	 * @return
	 */
	private ISIBehaviour getBehaviour(String name) {
		for (ISIBehaviour beh : behaviours) {
			if (beh.getName().equals(name)) {
				return beh;
			}
		}
		return null;
	}


	public String getName() {
		return category.getName();
	}

	public String getNodeType() {
		return category.getNodeType();
	}

	public ISIBehaviour getPrimaryBehaviour() {
		return primaryBehaviour;
	}

	public List<ISINodeConnection> getConnections() {
		return connections;
	}

	public List<ISINodeResource> getResources() {
		return resources;
	}

	public List<ISIBehaviour> getBehaviours() {
		return behaviours;
	}

	public ISIScenario getScenario() {
		return scenario;
	}

}
