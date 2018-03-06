CREATE TABLE Node (
	NodeId INTEGER NOT NULL, 
	UploadSpeed INTEGER DEFAULT 0 NOT NULL,
	DownloadSpeed INTEGER DEFAULT 0 NOT NULL,
	UploadDelay INTEGER DEFAULT 0 NOT NULL,
	DownloadDelay INTEGER DEFAULT 0 NOT NULL,
	CategoryName VARCHAR, -- Name of the scenario category of this node
	NodeType VARCHAR, -- Name of the type that defines the system behaviour
    SimulationRun INTEGER, 
	PRIMARY KEY (NodeId, SimulationRun)
);
	
CREATE TABLE Session (
	SessionId INTEGER,
	StartTime BIGINT DEFAULT 0,
	EndTime BIGINT DEFAULT 0,
	SimulationRun INTEGER, 
	PRIMARY KEY (SessionId, SimulationRun)
);
	
CREATE TABLE Message (
	MessageId INTEGER,
	SourceId INTEGER, 
	DestinationId INTEGER, 
	SessionId INTEGER,
	Size FLOAT, 
	TimeSent BIGINT DEFAULT 0,
    TimeReceived BIGINT DEFAULT 0,
    MessageType VARCHAR,
    Success TINYINT,  -- 1 if the message was processed successfully by the receiver; 0 if not
	SimulationRun INTEGER,
	PRIMARY KEY(MessageId, SimulationRun)
);

	
-- Definition of global Resource information
CREATE TABLE Resource (
	ResourceId INTEGER,
	Size INTEGER, -- Size in bytes
	SimulationRun INTEGER,
	PRIMARY KEY(ResourceId, SimulationRun)
);
	
CREATE TABLE ResourceChange (
	NodeId INTEGER,
	ResourceId INTEGER, 
	FractionAvailable DOUBLE, -- Number from 0 to 1 indicating how much 
				-- of the resource is present on the node OR -1 indicating
				-- that the resource is no longer present at all
	Time BIGINT DEFAULT 0,
	Quality INTEGER, -- may be: 0 = not present, 1 = present, 
					 -- 2 = validating, 3 = valid (checked with checksum)
	SimulationRun INTEGER,
	PRIMARY KEY(ResourceId, Time, NodeId, SimulationRun)
);

CREATE TABLE NodeState (
	NodeId INTEGER, 
	Time BIGINT DEFAULT 0,
	StateChange INTEGER,
	SimulationRun INTEGER, 
	PRIMARY KEY(NodeId, Time, StateChange, SimulationRun)
);

CREATE TABLE UserLog (
	NodeId INTEGER,
	Time BIGINT DEFAULT 0,
	Name VARCHAR,
	Value VARCHAR,
	SimulationRun INTEGER
);

CREATE INDEX UserLog_Index ON UserLog(NodeId, Time, Name, SimulationRun);