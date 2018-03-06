package de.peerthing.visualization.querypersistence;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.StringBufferInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;

import junit.framework.TestCase;
import de.peerthing.visualization.querymodel.Query;
import de.peerthing.visualization.querymodel.QueryDataModel;
import de.peerthing.visualization.querymodel.VisualizationData;
import de.peerthing.visualization.querymodel.interfaces.IQuery;
import de.peerthing.visualization.querymodel.interfaces.IQueryDataModel;
import de.peerthing.visualization.querymodel.interfaces.IVisualizationData;
import de.peerthing.visualization.querymodel.interfaces.IVisualizationData.Styles;


public class QueryPersistenceTest extends TestCase {
	private QueryPersistence qp;
	String filename = "logdata";
	IProject project;
	IFile file;
	String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
		"<!DOCTYPE QueryDataModel.dtd>"+
		
		"<queries>"+
		  "<query name=\"Messages\">"+
		   "<preparingSql />"+
		    "<visualization style=\"line\" normalized=\"false\" xaxis=\"Messages\" yaxis=\"Time\">select MessageId, TimeReceived from"+ 
		"Message where SimulationRun = $RUN$ and TimeReceived between $STARTTIME$ and $ENDTIME$;</visualization>"+
		    "<visualization style=\"table\" normalized=\"false\" xaxis=\"MessageId\" yaxis=\"Time received\">select MessageId, TimeSent, TimeReceived, MessageType from Message where SimulationRun = $RUN$ and TimeReceived between $STARTTIME$ and $ENDTIME$;</visualization>"+
		    "<visualization style=\"boxplot\" normalized=\"false\" xaxis=\"Simulation Runs\" yaxis=\"Time Received\">select TimeReceived from Message where SimulationRun=$RUN$ and TimeReceived between $STARTTIME$ and $ENDTIME$</visualization>"+
		    "<visualization style=\"scatter\" normalized=\"false\" xaxis=\"X-Axis\" yaxis=\"Y-Axis\">select MessageId, TimeReceived from "+
		"Message where SimulationRun = $RUN$ and TimeReceived between $STARTTIME$ and $ENDTIME$;</visualization>"+
		    "<visualization style=\"bar\" normalized=\"false\" xaxis=\"Time at which the message was received\" yaxis=\"Message Count\">select TimeReceived, 1 from"+ 
		"Message where SimulationRun = $RUN$ and TimeReceived between $STARTTIME$ and $ENDTIME$;</visualization>"+
		  "</query>"+
		"</queries>";
	private StringBufferInputStream stream;
	protected void setUp() throws Exception {
		super.setUp();
		qp = new QueryPersistence();

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		project = workspace.getRoot().getProject("qptest");
			
		if (!project.exists()) {
			project.delete(true,true,null);
		}
		
		project.create(null);
		project.open(null);
		
        
		file = project.getFile(filename);        
        if (file.exists()){
        	file.delete(true, false, null);
        }
        stream = new StringBufferInputStream(xml);
        file.create(stream,true,null);
		file.touch(null);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		project.close(null);
		project.delete(true,true,null);
	}

	/*
	 * Test method for 'de.peerthing.visualization.querypersistence.QueryPersistence.saveToXml(IQueryDataModel)'
	 * Test method for 'de.peerthing.visualization.querypersistence.QueryPersistence.loadQueries(IFile)'
	 */
	private String getString(IQueryDataModel qdm) {
		String ret="";
		PipedOutputStream src = new PipedOutputStream();
		PipedInputStream snk = new PipedInputStream();
		try {			
			src.connect(snk);	
			qp.setXML(qp.getXMLDocument(qdm), src);
			src.flush();
			src.close();		
			int i;
			while ((i=snk.read())!=-1) {ret = ret+(char)i;}		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;	
	}
	public void testSaveToXml() {
		qp.saveToXml(getQDM());
		IQueryDataModel tmpqdm = qp.loadQueries(file);		
		String s1=getString(getQDM());
		String s2=getString(tmpqdm);
		assertTrue(s1.equals(s2));		
	}

	/*
	 * Test method for 'de.peerthing.visualization.querypersistence.QueryPersistence.setXML(Document, OutputStream)'
	 */
	private IQueryDataModel getQDM() {
		IQueryDataModel returnvalue = new QueryDataModel();
		returnvalue.setFile(file);
		IQuery query = new Query(returnvalue);
		returnvalue.getQueries().add(query);
		query.setName("Query 1");
		query.setPreparingQueries("SELECT a,b FROM PREPARING");
		IVisualizationData visdata = new VisualizationData(query);
		visdata.setDataQuery("SELECT * FROM DATA");
		visdata.setName("Visdata 1");
		visdata.setNormalized(true);
		visdata.setStyle(Styles.boxplot);
		visdata.setXAxisLabel("X-Thingi");
		visdata.setYAxisLabel("Y-Thingi");
		query.addVisualizationData(visdata);
		return returnvalue;
	}

}
