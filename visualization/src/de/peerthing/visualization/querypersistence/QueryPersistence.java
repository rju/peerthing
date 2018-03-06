package de.peerthing.visualization.querypersistence;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.core.resources.IFile;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import de.peerthing.visualization.querymodel.Query;
import de.peerthing.visualization.querymodel.QueryDataModel;
import de.peerthing.visualization.querymodel.VisualizationData;
import de.peerthing.visualization.querymodel.interfaces.IQuery;
import de.peerthing.visualization.querymodel.interfaces.IQueryDataModel;
import de.peerthing.visualization.querymodel.interfaces.IVisualizationData;

/**
 * Loads and saves query definitions from and to
 * XML files.
 * 
 * @author Michael Gottschalk
 *
 */
public class QueryPersistence {
    
    /**
     * Creates a QueryDataModel from the xml contents
     * unter the given file.
     * 
     * @param file The file to load
     * @return The data model created from the file
     */
	public IQueryDataModel loadQueries(IFile file) {
		SAXBuilder parser = new SAXBuilder();
		Document doc = null;
		try {
			doc = parser.build(file.getLocation().toString());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			// If the document is empty, return a
			// new Data model
			IQueryDataModel model = new QueryDataModel();
			model.setFile(file);
			return model;
		}

		IQueryDataModel model = getQueryDataModel(doc);
		model.setFile(file);
		return model;
	}

	/**
	 * Initializes a QueryDataModel 
     * representing the given XML-Document. This is
     * used internally by loadQueries().
	 *
	 * @param doc
	 *            the Document
	 * @return QueryDataModel
	 */
	private IQueryDataModel getQueryDataModel(Document doc) {
		QueryDataModel model = new QueryDataModel();

		for (Object queryObj : doc.getRootElement().getChildren("query")) {
			Element queryElem = (Element) queryObj;

			Query q = new Query(model);
			q.setName(queryElem.getAttributeValue("name"));
			q.setPreparingQueries(queryElem.getChildText("preparingSql"));

			for (Object visObj : queryElem.getChildren("visualization")) {
				Element visElem = (Element) visObj;
				VisualizationData vis = new VisualizationData(q);
                vis.setName(visElem.getAttributeValue("name"));
				vis.setStyle(IVisualizationData.Styles.valueOf(visElem
						.getAttributeValue("style")));
				vis.setDataQuery(visElem.getText());
				vis.setNormalized(visElem.getAttributeValue("normalized") != null
						&& visElem.getAttributeValue("normalized").equals("true"));
				vis.setXAxisLabel(visElem.getAttributeValue("xaxis"));
				vis.setYAxisLabel(visElem.getAttributeValue("yaxis"));
				q.getVisualizationData().add(vis);
			}

			model.getQueries().add(q);
		}

		return model;
	}

    /**
     * Saves a given data model to an XML file. The file
     * in which to save is given in the data model.
     * 
     * @param model
     */
	public void saveToXml(IQueryDataModel model) {
		Document doc = getXMLDocument(model);

    	// write the xml-file
        try {
            FileOutputStream out = new FileOutputStream(model.getFile().getLocation().toString());
            XMLOutputter serializer = new XMLOutputter();
            serializer.setFormat(Format.getPrettyFormat());
            serializer.output(doc, out);
            out.flush();
            out.close();
        } catch (IOException e3) {
        	e3.printStackTrace();
        }
	}

    /**
     * Returns an XML document with the contents
     * of the given model. Is used internally by
     * saveToXML().
     * 
     * @param model The model
     * @return The XML document
     */
	protected Document getXMLDocument(IQueryDataModel model) {
		Element root = new Element("queries");
		Document doc = new Document(root);
		DocType type = new DocType("QueryDataModel.dtd");
		doc.setDocType(type);

		for (IQuery query : model.getQueries()) {
			Element queryElem = new Element("query");
			queryElem.setAttribute("name", query.getName());
			Element sql = new Element("preparingSql");
			sql.setText(query.getPreparingQueries());
			queryElem.addContent(sql);

			for (IVisualizationData vis : query.getVisualizationData()) {
				Element visElem = new Element("visualization");
				visElem.setAttribute("name", vis.getName());
                visElem.setAttribute("style", vis.getStyle().toString());
				visElem.setAttribute("normalized", vis.isNormalized() ? "true"
						: "false");
				visElem.setText(vis.getDataQuery());
				visElem.setAttribute("xaxis", vis.getXAxisLabel());
				visElem.setAttribute("yaxis", vis.getYAxisLabel());
				queryElem.addContent(visElem);
			}

			root.addContent(queryElem);
		}
		return doc;
	}

	/**
	 * Writes the given Document as a formatted 
     * XML-File to the given OutputStream. Only
     * used for test purposes.
	 *
	 * @param doc
	 *            the XML-Document
	 * @param out
	 *            the OutputStream
	 */
	public void setXML(Document doc, OutputStream out) {
		XMLOutputter xout = new XMLOutputter();
		xout.setFormat(Format.getPrettyFormat());
		try {
			xout.output(doc, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
