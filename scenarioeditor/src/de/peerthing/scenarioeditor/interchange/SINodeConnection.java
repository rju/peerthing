package de.peerthing.scenarioeditor.interchange;

import de.peerthing.scenarioeditor.model.INodeConnection;

class SINodeConnection implements ISINodeConnection {
	private ISINodeCategory node;
	private ISIConnectionCategory conn;
	private INodeConnection nodeConn;

	public SINodeConnection(INodeConnection nodeConn, ISINodeCategory node) {
		this.nodeConn = nodeConn;
		this.node = node;
        for (ISIConnectionCategory connection : node.getScenario().getConnectionCategories()) {
            if (connection.getName().equals(nodeConn.getCategory().getName())) {
				conn = connection;
			}
		}
	}

	public ISIConnectionCategory getCategory() {
		return conn;
	}

	public int getNumberOfNodes() {
		return nodeConn.getNumberOfNodes();
	}

	public ISINodeCategory getNode() {
		return node;
	}

}
