package de.peerthing.scenarioeditor.persistence;

import java.io.IOException;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import de.peerthing.scenarioeditor.interchange.InvalidScenarioFileException;
import de.peerthing.scenarioeditor.model.IListen;
import de.peerthing.scenarioeditor.model.IUserBehaviour;
import de.peerthing.scenarioeditor.model.ICallUserBehaviour;
import de.peerthing.scenarioeditor.model.ICase;
import de.peerthing.scenarioeditor.model.ICommand;
import de.peerthing.scenarioeditor.model.ICommandContainer;
import de.peerthing.scenarioeditor.model.IConnectionCategory;
import de.peerthing.scenarioeditor.model.IDelay;
import de.peerthing.scenarioeditor.model.IDistribution;
import de.peerthing.scenarioeditor.model.ILinkSpeed;
import de.peerthing.scenarioeditor.model.ILoop;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.INodeConnection;
import de.peerthing.scenarioeditor.model.INodeResource;
import de.peerthing.scenarioeditor.model.IResourceCategory;
import de.peerthing.scenarioeditor.model.IScenario;
import de.peerthing.scenarioeditor.model.IScenarioCondition;
import de.peerthing.scenarioeditor.model.IUserAction;
import de.peerthing.scenarioeditor.model.IDefaultCase;
import de.peerthing.scenarioeditor.model.ScenarioFactory;
import de.peerthing.scenarioeditor.model.impl.Case;
import de.peerthing.scenarioeditor.model.impl.DefaultCase;

import java.io.*;

/**
 * This class loads a scenario from an XML file and writes a scenario to an XML
 * file.
 * 
 * @author Michael Gottschalk, Patrik
 * @review Sebastian Rohjans 27.03.2006
 * 
 */
public class ScenarioXMLAdapter {

    /**
     * this method writes the scenario into a xml-file.
     * 
     * @param scenario
     *            object the scenario is saved in
     * @param saveName
     *            name of the save-file
     */
    private static Document getScenarioDocument(IScenario scenario) {

        Element root = new Element("scenario");
        Document doc = new Document(root);

        root.setAttribute("name", scenario.getName());
             

        // writing data of the Resource Categories
        for (int i = 0; i < scenario.getResourceCategories().size(); i++) {
            Element resourceCategory = new Element("resourceCategory");
            root.addContent(resourceCategory);
            resourceCategory.setAttribute("name", scenario
                    .getResourceCategories().get(i).getName());
            resourceCategory.setAttribute("diversity", String.valueOf(scenario
                    .getResourceCategories().get(i).getDiversity()));
            resourceCategory.setAttribute("popularity", String.valueOf(scenario
                    .getResourceCategories().get(i).getPopularity()));
            Element size = new Element("size");
            resourceCategory.addContent(size);
            size.setAttribute("min", String.valueOf((scenario
                    .getResourceCategories().get(i).getSize().getMin() / 1024))
                    + "mb");
            size.setAttribute("max", String.valueOf((scenario
                    .getResourceCategories().get(i).getSize().getMax() / 1024))
                    + "mb");
            size.setAttribute("type", scenario.getResourceCategories().get(i)
                    .getSize().getType().name());
            size.setAttribute("mean", String.valueOf(scenario
                    .getResourceCategories().get(i).getSize().getMean()));
            size.setAttribute("variance", String.valueOf(scenario
                    .getResourceCategories().get(i).getSize().getVariance()));
        }

        // writing data of the Connection Categories
        for (int i = 0; i < scenario.getConnectionCategories().size(); i++) {
            Element connectionCategory = new Element("connectionCategory");
            root.addContent(connectionCategory);
            connectionCategory.setAttribute("name", scenario
                    .getConnectionCategories().get(i).getName());
            connectionCategory.setAttribute("duplex", scenario
                    .getConnectionCategories().get(i).getDuplex().name());
            Element uplink = new Element("uplink");
            connectionCategory.addContent(uplink);                        
            uplink.setAttribute("speed", String.valueOf((scenario
                    .getConnectionCategories().get(i).getUplinkSpeed()
                    .getSpeed() / 1024))
                    + "kb");
            uplink.setAttribute("delay", String.valueOf((scenario
                    .getConnectionCategories().get(i).getUplinkSpeed()
                    .getDelay()))
                    + "ms");
            Element downlink = new Element("downlink");
            connectionCategory.addContent(downlink);
            downlink.setAttribute("speed", String.valueOf((scenario
                    .getConnectionCategories().get(i).getDownlinkSpeed()
                    .getSpeed() / 1024))
                    + "kb");
            downlink.setAttribute("delay", String.valueOf((scenario
                    .getConnectionCategories().get(i).getDownlinkSpeed()
                    .getDelay()))
                    + "ms");
        }

        // writing data of the node Categories
        for (int i = 0; i < scenario.getNodeCategories().size(); i++) {
            Element nodeCategory = new Element("nodeCategory");
            root.addContent(nodeCategory);
            nodeCategory.setAttribute("nodeType", scenario.getNodeCategories()
                    .get(i).getNodeType());
            nodeCategory
                    .setAttribute("primaryBehaviour", scenario
                            .getNodeCategories().get(i).getPrimaryBehaviour()
                            .getName());
            nodeCategory.setAttribute("name", scenario.getNodeCategories().get(
                    i).getName());

            // writing data of a node Connection
            for (int j = 0; j < scenario.getNodeCategories().get(i)
                    .getConnections().size(); j++) {

                Element connection = new Element("connection");
                nodeCategory.addContent(connection);
                connection.setAttribute("category", String.valueOf(scenario
                        .getNodeCategories().get(i).getConnections().get(j)
                        .getCategory().getName()));
                connection.setAttribute("number", String.valueOf(scenario
                        .getNodeCategories().get(i).getConnections().get(j)
                        .getNumberOfNodes()));
            }

            // writing data of a node Resource
            for (int j = 0; j < scenario.getNodeCategories().get(i)
                    .getResources().size(); j++) {
                Element resource = new Element("resource");
                nodeCategory.addContent(resource);
                resource.setAttribute("category", String.valueOf(scenario
                        .getNodeCategories().get(i).getResources().get(j)
                        .getCategory().getName()));
                resource.setAttribute("min", String.valueOf(scenario
                        .getNodeCategories().get(i).getResources().get(j)
                        .getNumberDistribution().getMin()));
                resource.setAttribute("max", String.valueOf(scenario
                        .getNodeCategories().get(i).getResources().get(j)
                        .getNumberDistribution().getMax()));
                resource.setAttribute("type", String.valueOf(scenario
                        .getNodeCategories().get(i).getResources().get(j)
                        .getNumberDistribution().getType().name()));
                resource.setAttribute("mean", String.valueOf(scenario
                        .getNodeCategories().get(i).getResources().get(j)
                        .getNumberDistribution().getMean()));
                resource.setAttribute("variance", String.valueOf(scenario
                        .getNodeCategories().get(i).getResources().get(j)
                        .getNumberDistribution().getVariance()));
            }

            // writing data of a Behaviour
            for (int j = 0; j < scenario.getNodeCategories().get(i)
                    .getBehaviours().size(); j++) {
                Element behaviour = new Element("behaviour");
                nodeCategory.addContent(behaviour);
                behaviour.setAttribute("name", String.valueOf(scenario
                        .getNodeCategories().get(i).getBehaviours().get(j)
                        .getName()));

                for (int k = 0; k < scenario.getNodeCategories().get(i)
                        .getBehaviours().get(j).getCommands().size(); k++) {

                    ICommand com = scenario.getNodeCategories().get(i)
                            .getBehaviours().get(j).getCommands().get(k);
                    addCommand(com, behaviour);
                }
            }

        }
        
        return doc;
    }
   
    public static void saveScenario(IScenario scenario, String saveName) {
        Document doc = getScenarioDocument(scenario);
    	
    	// writing the xml-file
        try {
            FileOutputStream out = new FileOutputStream(saveName);
            XMLOutputter serializer = new XMLOutputter();
            serializer.setFormat(Format.getPrettyFormat());
            serializer.output(doc, out);
            out.flush();
            out.close();
        } catch (IOException e3) {
        	e3.printStackTrace();
        }
    }
    
    public static String getScenarioXMLString(IScenario scenario) {
    	Document doc = getScenarioDocument(scenario);
    	String ret = null;
    	
    	// writing the xml-file
        try {
        	ByteArrayOutputStream out = new ByteArrayOutputStream();
            XMLOutputter serializer = new XMLOutputter();
            serializer.setFormat(Format.getPrettyFormat());
            serializer.output(doc, out);
            out.flush();
            ret =  out.toString();
            out.close();
        } catch (IOException e3) {
        	e3.printStackTrace();
        }
        
        return ret;
    }

    
    

    /**
     * writing data of the Command in a behaviour
     */
    private static void addCommand(ICommand son, Element father) {

        // writing data of a Loop
        if (son instanceof ILoop) {
            ILoop l1 = (ILoop) son;
            Element loop = new Element("loop");
            father.addContent(loop);
            if (l1.getUntilCondition() != null) {
                loop.setAttribute("until", l1.getUntilCondition());
            }

            loop.setAttribute("min", String.valueOf(l1.getDistribution()
                    .getMin()));
            loop.setAttribute("max", String.valueOf(l1.getDistribution()
                    .getMin()));
            loop.setAttribute("type", String.valueOf(l1.getDistribution()
                    .getType().name()));
            loop.setAttribute("mean", String.valueOf(l1.getDistribution()
                    .getMean()));
            loop.setAttribute("max", String.valueOf(l1.getDistribution()
                    .getMax()));
            for (int i = 0; i < l1.getCommands().size(); i++) {
                addCommand(l1.getCommands().get(i), loop);
            }

        }

        // writing data of a Delay
        if (son instanceof IDelay) {
            IDelay de = (IDelay) son;
            Element delay = new Element("delay");
            father.addContent(delay);
            delay.setAttribute("min", String.valueOf(de.getDistribution()
                    .getMin()));
            delay.setAttribute("max", String.valueOf(de.getDistribution()
                    .getMax()));
            delay.setAttribute("type", String.valueOf(de.getDistribution()
                    .getType().name()));
            delay.setAttribute("mean", String.valueOf(de.getDistribution()
                    .getMean()));
            delay.setAttribute("variance", String.valueOf(de.getDistribution()
                    .getVariance()));
        }
        
        if (son instanceof IListen) {
            IListen li = (IListen) son;
            Element listen = new Element("listen");
            father.addContent(listen);
            listen.setAttribute("event", String.valueOf(li.getEvent()));
            listen.setAttribute("min", String.valueOf(li.getDistribution()
                    .getMin()));
            listen.setAttribute("max", String.valueOf(li.getDistribution()
                    .getMax()));
            listen.setAttribute("type", String.valueOf(li.getDistribution()
                    .getType().name()));
            listen.setAttribute("mean", String.valueOf(li.getDistribution()
                    .getMean()));
            listen.setAttribute("variance", String.valueOf(li.getDistribution()
                    .getVariance()));
        }

        // writing data of CallBehaviour
        if (son instanceof ICallUserBehaviour) {
            ICallUserBehaviour cb = (ICallUserBehaviour) son;
            Element callBehaviour = new Element("callBehaviour");
            father.addContent(callBehaviour);
            callBehaviour.setAttribute("name", cb.getBehaviour().getName());
            callBehaviour.setAttribute("probability", String.valueOf(cb
                    .getProbability()));
            if (cb.isStartTask())
            	callBehaviour.setAttribute("startTask", "yes");
            else
            	callBehaviour.setAttribute("startTask", "no");
        }

        // writing data of UserAction
        if (son instanceof IUserAction) {
            IUserAction ac = (IUserAction) son;
            Element actionElement = new Element("action");
            father.addContent(actionElement);
            actionElement.setAttribute("name", ac.getName());
            actionElement.setAttribute("probability", "" + ac.getProbability());
            for (String key : ac.getParameters().keySet()) {
                Element param = new Element("param");
                param.setAttribute("name", key);
                param.setText(ac.getParameters().get(key));
                actionElement.addContent(param);
            }
        }

        // writing data of a Condition
        if (son instanceof IScenarioCondition) {
            IScenarioCondition con = (IScenarioCondition) son;
            Element conElement = new Element("condition");
            father.addContent(conElement);

            for (int i = 0; i < con.getCases().size(); i++) {
                addCommand(con.getCases().get(i), conElement);
            }
            addCommand(con.getDefaultCase(), conElement);
        }

    }

    /**
     * writing data of a case/ default case
     */
    private static void addCommand(ICase son, Element father) {

        // writing data of a Case
        if (son instanceof ICase && !(son instanceof IDefaultCase)) {
            ICase ca = (ICase) son;
            Element caseElement = new Element("case");
            father.addContent(caseElement);
            if (ca.isConditionUsed()) {
                caseElement.setAttribute("expr", ca.getCondition());
            } else {
                caseElement.setAttribute("probability", String.valueOf(ca
                        .getProbability()));
            }
            for (int i = 0; i < ca.getCommands().size(); i++) {
                addCommand(ca.getCommands().get(i), caseElement);
            }
        }

        // writing data of a DefaultCase
        if (son instanceof IDefaultCase) {
            IDefaultCase ca = (IDefaultCase) son;
            Element caseElement = new Element("default");
            father.addContent(caseElement);
            for (int i = 0; i < ca.getCommands().size(); i++) {
                addCommand(ca.getCommands().get(i), caseElement);
            }
        }
    }


    public static IScenario getScenario(Document doc) {
        IScenario scenario = ScenarioFactory.createScenario();
        int numberOfRootChildren = 0;
        boolean hasPrimaryBehaviour = true;

        Element root = doc.getRootElement();
        if (root.getName() == null || !root.getName().equals("scenario")) {
            throw new InvalidScenarioFileException("The root element of the file is not scenario, but "+
                    root.getName());
        }
        
        if (root.getAttribute("name")== null ||
        		root.getAttributes().size()!=1){
        	return null;
        }
        
        scenario.setName(root.getAttributeValue("name"));

        // parse the global resource categories...
        for (Object resourceCategory : root.getChildren("resourceCategory")) {
        	numberOfRootChildren++;
            Element res = ((Element) resourceCategory);
            IResourceCategory category = ScenarioFactory.createResourceCategory(scenario);
            category.setScenario(scenario);
            if (res.getAttribute("name") == null ||
            		res.getAttribute("diversity") == null ||
            		res.getAttribute("popularity") == null ||
            		res.getChild("size")== null || res.getAttributes().size()!=3
            		|| res.getChildren().size()!=1){                	
            	return null;
            }            
            category.setName(res.getAttributeValue("name"));
            category.setDiversity(Integer.parseInt(res
                    .getAttributeValue("diversity")));
            category.setPopularity(Integer.parseInt(res
                    .getAttributeValue("popularity")));
            if (getDistribution(res.getChild("size"), scenario)==null){
            	return null;
            }
            category.setSize(getDistribution(res.getChild("size"), scenario));
            scenario.getResourceCategories().add(category);
        }

        // parse the global connection categories...
        for (Object connectionCategory : root.getChildren("connectionCategory")) {
        	numberOfRootChildren++;
            Element conn = (Element) connectionCategory;
            IConnectionCategory category = ScenarioFactory
                    .createConnectionCategory(scenario);
            category.setScenario(scenario);
            if (conn.getAttribute("name") == null ||            		
            		conn.getChild("uplink")==null ||
            		conn.getChild("downlink")==null ){                	
            	return null;
            }
            int conChildren = 2;
            category.setName(conn.getAttributeValue("name"));
            if (conn.getChild("duplex")!=null){
            	conChildren++;
            	category.setDuplex(IConnectionCategory.DuplexOption.valueOf(conn
                    .getAttributeValue("duplex")));
            } else {
            	category.setDuplex(IConnectionCategory.DuplexOption.full);
            }
            if (conChildren != conn.getChildren().size()){
            	return null;
            }
            if (getLinkSpeed(conn.getChild("uplink"), scenario)==null||
            	getLinkSpeed(conn.getChild("downlink"), scenario) == null){
            	return null;
            }
            category.setUplinkSpeed(getLinkSpeed(conn.getChild("uplink"), scenario));
            category.setDownlinkSpeed(getLinkSpeed(conn.getChild("downlink"), scenario));

            scenario.getConnectionCategories().add(category);
        }

        // parse the node categories...
        for (Object nodeCategory : root.getChildren("nodeCategory")) {        	
        	numberOfRootChildren++;
            Element node = (Element) nodeCategory;
            INodeCategory category = ScenarioFactory.createNodeCategory(scenario);
            category.setScenario(scenario);
            if (node.getAttribute("name") == null
            	|| node.getAttribute("nodeType")==null ||
            	node.getAttributes().size() !=3){            	
            	return null;
            }                        
            
            category.setName(node.getAttributeValue("name"));            
            category.setNodeType(node.getAttributeValue("nodeType"));
            
            int numberOfNodeCatChildren = 0;
            
            // parse connections...
            for (Object nodeConnection : node.getChildren("connection")) {
            	numberOfNodeCatChildren++;
                Element connection = (Element) nodeConnection;
                INodeConnection conn = ScenarioFactory.createNodeConnection();
                conn.setNode(category);
                if (connection.getAttribute("number") == null 
                		|| connection.getAttributes().size()!=2){                	
                	return null;
                }
                conn.setNumberOfNodes(getInt(connection
                        .getAttributeValue("number")));
                
                // Find corresponding connection category...
                IConnectionCategory connCat = null;
                for (IConnectionCategory c : scenario.getConnectionCategories()) {
                    if (connection.getAttribute("category")==null){
                    	return null;
                    }
                	if (c.getName().equals(
                            connection.getAttributeValue("category"))) {
                        connCat = c;
                        break;
                    }
                }
                conn.setCategory(connCat);

                // Add the connection to the node category
                category.getConnections().add(conn);
            }

            // parse resources...
            for (Object res : node.getChildren("resource")) {
            	numberOfNodeCatChildren++;
                Element resource = (Element) res;
                INodeResource nodeResource = ScenarioFactory.createNodeResource();
                nodeResource.setNode(category);                
                
                if (resource.getAttributes().size()!=1){
                	
                }
                // Find corresponding resource category...
                IResourceCategory resCat = null;
                for (IResourceCategory c : scenario.getResourceCategories()) {
                    if (resource.getAttribute("category")==null){
                    	return null;
                    }
                	if (c.getName().equals(
                            resource.getAttributeValue("category"))) {
                        resCat = c;                        
                    }
                }
                nodeResource.setCategory(resCat);

                if (getDistribution(resource, scenario)==null){
                	return null;
                }
                nodeResource.setNumberDistribution(getDistribution(resource, scenario));

                category.getResources().add(nodeResource);
            }
            hasPrimaryBehaviour = false;

            // parse behaviours...
            for (Object behObj : node.getChildren("behaviour")) {
            	numberOfNodeCatChildren++;
                Element behElem = (Element) behObj;
                IUserBehaviour behaviour = ScenarioFactory.createBehaviour(category);
                if (behElem.getAttribute("name")== null || node.getAttribute("primaryBehaviour")==null
                		||behElem.getAttributes().size()!=1){
                	return null;
                }
                behaviour.setName(behElem.getAttributeValue("name"));

                // don't add the commands yet, since
                // callBehaviour needs all behaviour objects first

                category.getBehaviours().add(behaviour);                                
                
                // Set primary behaviour...
                if (behaviour.getName().equals(
                        node.getAttributeValue("primaryBehaviour"))) {
                	hasPrimaryBehaviour = true;
                    category.setPrimaryBehaviour(behaviour);
                }
            }

            List children = node.getChildren("behaviour");
            for (int i = 0; i < children.size(); i++) {
                Element behElem = (Element) children.get(i);
                IUserBehaviour behaviour = category.getBehaviours().get(i);

                // parse commands, now that we have all behaviour
                // objects
                for (Object cmdObj : behElem.getChildren()) {
                    Element cmdElem = (Element) cmdObj;
                    if (!parseCommand(cmdElem, behaviour, category, scenario)){                    	
                    	return null;
                    }
                }
            }
            if (numberOfNodeCatChildren != node.getChildren().size()
            		|| !hasPrimaryBehaviour){            	
            	return null;
            }
            
            scenario.getNodeCategories().add(category);
        }
        if (root.getChildren().size() != numberOfRootChildren){
        	return null;
        }

        return scenario;
    }
    
    /**
     * Loads an existing scenario definition from an XML file.
     * 
     * @param filename
     *            The name of the xml file
     * @return
     */
    public static IScenario loadScenario(String filename) {
        SAXBuilder parser = new SAXBuilder();
        Document doc = null;
        try {
            doc = parser.build(filename);
        } catch (Exception ex) {  
            throw new InvalidScenarioFileException("Could not load scenario file: "+ ex.getMessage(), ex);
        }
        
        return getScenario(doc);
    }
    
    public static IScenario getScenarioFromXMLString(String xmldoc) {
    	 SAXBuilder parser = new SAXBuilder();
         Document doc = null;
         try {
        	 StringReader reader = new StringReader(xmldoc);
        	 
             doc = parser.build(reader);
         } catch (Exception ex) {                     	 
             return null;
         }
         
         return getScenario(doc);
    }

    /**
     * Returns an ICommand with the command that is included in the given
     * element
     * 
     * @return
     */
    private static boolean parseCommand(Element elem, ICommandContainer parent,
            INodeCategory nodeCategory, IScenario scenario) {
        String elemName = elem.getName();
        ICommand cmd = null;
        if (elemName.equals("loop")) {
            ILoop loop = ScenarioFactory.createLoop(scenario);
            if (getDistribution(elem, nodeCategory.getScenario())==null){
            	return false;
            }
            loop.setDistribution(getDistribution(elem, nodeCategory.getScenario()));
            loop.setUntilCondition(elem.getAttributeValue("until"));
            for (Object subElemObj : elem.getChildren()) {
                if (!parseCommand((Element) subElemObj, loop, nodeCategory, scenario)){
                	return false;
                }
            }
            cmd = loop;
        } else if (elemName.equals("action")) {
        	if (elem.getAttribute("name")==null){        		
            	return false;
            }
        	IUserAction action = ScenarioFactory.createUserAction(scenario);            
            action.setName(elem.getAttributeValue("name"));
            action.setProbability(getDouble(elem
                    .getAttributeValue("probability"), 1));
            // Parse parameters...
            for (Object paramObj : elem.getChildren("param")) {
                Element paramElem = (Element) paramObj;
                if (paramElem.getAttribute("name")==null){
                	return false;
                }
                action.getParameters().put(paramElem.getAttributeValue("name"),
                        paramElem.getValue());
            }
            cmd = action;
        } else if (elemName.equals("callBehaviour")) {
        	if (elem.getAttribute("name")==null ||
        			elem.getAttribute("probability")==null ||
        			elem.getAttribute("startTask")==null){
            	return false;
            }
            // search the referenced behaviour
            IUserBehaviour refBehaviour = null;
            for (IUserBehaviour b : nodeCategory.getBehaviours()) {
                if (b.getName().equals(elem.getAttributeValue("name"))) {
                    refBehaviour = b;
                    break;
                }
            }            
            ICallUserBehaviour behaviour = ScenarioFactory.createCallBehaviour(scenario, refBehaviour);
            behaviour.setProbability(getDouble(elem
                    .getAttributeValue("probability"), 1));

            if (elem.getAttributeValue("startTask") != null)
            	if (elem.getAttributeValue("startTask").equals("yes"))
            		behaviour.setStartTask(true);
            	else
            		behaviour.setStartTask(false);
            else
            	behaviour.setStartTask(false);
            
            //behaviour.setBehaviour(refBehaviour);
            cmd = behaviour;
        } else if (elemName.equals("delay")) {
            IDelay delay = ScenarioFactory.createDelay(scenario);
            if (getDistribution(elem, nodeCategory.getScenario())==null){
            	return false;
            }
            delay.setDistribution(getDistribution(elem, nodeCategory.getScenario()));
            cmd = delay;
        } else if (elemName.equals("listen")) {
            IListen listen = ScenarioFactory.createListen(scenario);
            listen.setDistribution(getDistribution(elem, nodeCategory.getScenario()));
            if (elem.getAttribute("event")==null){
            	return false;
            }
            if (elem.getAttributeValue("event")!= null){
            	listen.setEvent(elem.getAttributeValue("event"));            	
            } else {
            	listen.setEvent(elem.getAttributeValue(""));            	
            }
            
            cmd = listen;
        } else if (elemName.equals("condition")) {
            IScenarioCondition condition = ScenarioFactory
                    .createScenarioCondition(scenario);
            int numberOfCases = 1;
            for (Object caseObj : elem.getChildren("case")) {
            	numberOfCases++;
                Element caseElem = (Element) caseObj;
                Case c = (Case)ScenarioFactory.createCase(scenario);
                c.setScenario(nodeCategory.getScenario());
                c.setCondition(caseElem.getAttributeValue("expr"));
                if (caseElem.getAttributeValue("expr") != null) {
                    c.setConditionUsed(true);
                } else {
                    c.setConditionUsed(false);
                }
                c.setProbability(getDouble(caseElem
                        .getAttributeValue("probability"), 1));
                c.setScenarioCondition(condition);
                // parse sub-commands
                for (Object cmdObj : caseElem.getChildren()) {
                    parseCommand((Element) cmdObj, c, nodeCategory, scenario);
                }
                condition.getCases().add(c);
            }

            if (elem.getChild("default")==null){
            	return false;
            }
            
            // parse default case...
            Element defaultCaseElem = elem.getChild("default");
            
            if (elem.getChildren().size()!=numberOfCases){
            	return false;
            }
            // ICase defaultCase = ScenarioFactory.getCase();
            /*
             * I changed this line so i can differentiate Case and Default case
             * in the Data Stuctur
             */
            DefaultCase defaultCase = (DefaultCase)ScenarioFactory.createDefaultCase(scenario);

            for (Object cmdObj : defaultCaseElem.getChildren()) {
                parseCommand((Element) cmdObj, defaultCase, nodeCategory, scenario);
            }
            
            defaultCase.setScenarioCondition(condition);

            condition.setDefaultCase(defaultCase);
            cmd = condition;
        }

        // Add the command to the parent command container
        if (cmd != null) {
            cmd.setCommandContainer(parent);
            // Give the command the information about to what
            // scenario it belongs
            cmd.setScenario(nodeCategory.getScenario());
            
            parent.getCommands().add(cmd);
        } else {        	
            System.out.println("Command " + elemName + " could not be parsed!");
            return false;
        }
        return true;
    }

    /**
     * Extracts from an XML element information about a distribution. If no
     * information about the distribution is present in the element, 
     * then null is returned.
     * 
     * @param distrElem
     *            An XML element that includes distribution information
     * @return An IDistribution object containing the information from the XML
     *         element
     */
    private static IDistribution getDistribution(Element distrElem, IScenario scenario) {        
        
        IDistribution distr = ScenarioFactory.createDistribution(scenario);
        distr.setScenario(scenario);
        if (distrElem.getAttribute("min") == null) {
            distr.setMin((double)0);
        } else {
        	distr.setMin(getDouble(distrElem.getAttributeValue("min")));
        }
        
        if (distrElem.getAttribute("max") == null) {
            distr.setMax((double)0);
        } else {
        	distr.setMax(getDouble(distrElem.getAttributeValue("max")));
        }
        if (distrElem.getAttribute("mean") == null) {
            distr.setMean((double)0);
        } else {
        	distr.setMean(getDouble(distrElem.getAttributeValue("mean")));
        }
        if (distrElem.getAttribute("variance") == null) {
            distr.setVariance((double)0);
        } else {        
        	distr.setVariance(getDouble(distrElem.getAttributeValue("variance")));
        }
        if (distrElem.getAttributeValue("type") != null) {
        	if (!distrElem.getAttributeValue("type").equals("uniform") &&
        			!distrElem.getAttributeValue("type").equals("normal")){
        		return null;
        	}
            distr.setType(IDistribution.DistributionType.valueOf(distrElem
                    .getAttributeValue("type")));
        } else {
            distr.setType(IDistribution.DistributionType.uniform);
        }
        
        if(distr.getMin()<0 || distr.getMin()>distr.getMax()|| distr.getMean()<0 ||
        		distr.getVariance() <0){
        	return null;
        }

        return distr;
    }

    /**
     * Converts an integer to a String. If the String does not contain an
     * integer, then 0 is returned.
     * 
     * @param number
     */
    private static int getInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (Exception e) {
            return 0;
        }
    }


    /**
     * Converts a double to a String. If the String does not contain a double,
     * then defValue is returned.
     * 
     * @param number
     *            the number to be parsed
     * @param defaultValue
     *            the value to be taken if the given String is not a double
     */
    private static double getDouble(String number, double defaultValue) {
        try {
            return Double.parseDouble(number);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Converts a double to a String. If the String does not contain a double,
     * then 0 is returned.
     * 
     * @param number
     *            the number to be parsed
     */
    private static double getDouble(String number) {
        if (number != null) {
            if (number.endsWith("mb")) {
                return getDouble(number.substring(0, number.length() - 2)) * 1024;
            } else if (number.endsWith("kb")) {
                return getDouble(number.substring(0, number.length() - 2));
            }
        }
        return getDouble(number, 0);
    }

    /**
     * Parses an XML element with the definition of a link speed and returns an
     * ILinkSpeed object.
     * 
     * @param elem
     *            the XML element to parse
     * @return
     */
    private static ILinkSpeed getLinkSpeed(Element elem, IScenario scenario) {
        ILinkSpeed linkSpeed = ScenarioFactory.createLinkSpeed(scenario);
        linkSpeed.setScenario(scenario);
        
        if (elem.getAttribute("speed")==null ||
        	elem.getAttribute("delay")==null){
        	return null;
        }
        
        String speed = elem.getAttributeValue("speed");
        String delay = elem.getAttributeValue("delay");

        // In the object, the speed must be given in bytes per second,
        // so given values must be converted
        if (speed.endsWith("kb")) {
            linkSpeed.setSpeed(1024 * Long.parseLong(speed.substring(0, speed
                    .length() - 2)));
        } else if (speed.endsWith("mb")) {
            linkSpeed.setSpeed(1024 * 1024 * Long.parseLong(speed.substring(
                    0, speed.length() - 2)));
        } else if (speed.endsWith("gb")) {
            linkSpeed.setSpeed(1024 * 1024 * 1024 * Long.parseLong(speed
                    .substring(0, speed.length() - 2)));
        } else {
            // assume that the speed is already given in bytes per second,
            // without a dimension
            linkSpeed.setSpeed(getInt(speed));
        }

        // In the object, the delay must be given in milliseconds,
        // so given values must be converted
        if (delay.endsWith("ms")) {
            linkSpeed.setDelay(Long.parseLong(delay.substring(0, delay
                    .length() - 2)));
        } else if (delay.endsWith("s")) {
            linkSpeed.setDelay(1000 * Long.parseLong(delay.substring(0, delay
                    .length() - 1)));
        } else if (delay.endsWith("m")) {
            linkSpeed.setDelay(1000 * 60 * Long.parseLong(delay.substring(0,
                    delay.length() - 1)));
        } else if (delay.endsWith("h")) {
            linkSpeed.setDelay(1000 * 60 * 60 * Long.parseLong(delay
                    .substring(0, delay.length() - 1)));
        }

        return linkSpeed;
    }

}