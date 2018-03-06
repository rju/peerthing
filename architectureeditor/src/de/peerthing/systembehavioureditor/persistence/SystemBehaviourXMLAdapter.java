package de.peerthing.systembehavioureditor.persistence;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import de.peerthing.systembehavioureditor.interchange.EAIInitializeEvaluation;
import de.peerthing.systembehavioureditor.interchange.InvalidArchitectureFileException;
import de.peerthing.systembehavioureditor.model.*;
import de.peerthing.systembehavioureditor.model.editor.State;
import de.peerthing.systembehavioureditor.model.editor.Task;

/**
 * This class created a class structure from an architecture xml file. It can
 * use different concrete model classes, which are determined by factories. The
 * class can be used as follows:
 * 
 * <pre>
 * SystemBehaviourModelFactory factory = new SystemBehaviourModelFactory();
 * 
 * SystemBehaviourXMLAdapter xml = new SystemBehaviourXMLAdapter(factory);
 * 
 * ISystemBehaviour arch = xml.loadArchitecture(&quot;architecture.xml&quot;);
 * </pre>
 * 
 * @author mg
 * @review Johannes Fischer
 * 
 */
public class SystemBehaviourXMLAdapter {

    SystemBehaviourModelFactory modelFactory;

    public SystemBehaviourXMLAdapter(SystemBehaviourModelFactory modelFactory) {
        this.modelFactory = modelFactory;
    }

    /**
     * Loads an architecture from an existing XML file.
     * 
     * @param filename
     *            the file to process
     * @return
     */

    /**
     * Loads a SystemBehaviour from a String representing a XML document.
     */
    public ISystemBehaviour loadArchitectureFromXMLString(String xmlString) {

        SAXBuilder parser = new SAXBuilder();
        Document doc = new Document();
        try {
            StringReader reader = new StringReader(xmlString);
            doc = parser.build(reader);
        } catch (Exception e) {
        	
        	// this e should be handeled to the caller for error messages
            e.printStackTrace();
        }
        return loadArchitecture(doc);
    }

    /**
     * Loads a SystemBehaviour from a String representing a XML-file.
     */
    public ISystemBehaviour loadArchitecture(String filename) {

        SAXBuilder parser = new SAXBuilder();
        Document doc = null;
        try {
            doc = parser.build(filename);
        } catch (Exception e) {
            throw new InvalidArchitectureFileException("Could not load file", e);
        }
        return loadArchitecture(doc);
    }

    /*
     * 
     * public ISystemBehaviour loadArchitecture(String filename) {
     * ISystemBehaviour arch = modelFactory.createArchitecture();
     * 
     * SAXBuilder parser = new SAXBuilder(); Document doc = null; try { doc =
     * parser.build(filename); } catch (Exception e) {
     * java.lang.System.out.println("Cannot read from file: " + e); }
     * 
     * Element root = doc.getRootElement();
     * arch.setName(root.getAttributeValue("name"));
     * 
     */

    public ISystemBehaviour loadArchitecture(Document doc) {

        ISystemBehaviour arch = modelFactory.createArchitecture();

        /*
         * SAXBuilder parser = new SAXBuilder(); Document doc = null; try { doc =
         * parser.build(filename); } catch (Exception e) {
         * java.lang.System.out.println("Cannot read from file: " + e); }
         */

        Element root = doc.getRootElement();
        if (root == null || root.getName() == null
                || !root.getName().equals("architecture")) {
            throw new InvalidArchitectureFileException(
                    "The file's root element is not architecture, but "
                            + root.getName());
        }

        arch.setName(root.getAttributeValue("name"));

        for (Object nodeObj : root.getChildren("node")) {
            Element nodeElem = (Element) nodeObj;
            INodeType node = modelFactory.createNode();
            node.setArchitecture(arch);
            node.setName(nodeElem.getAttributeValue("name"));

            Element behaviourElem = nodeElem.getChild("behaviour");

            // parse variables
            for (Object varObj : nodeElem.getChildren("variable")) {
                Element varElem = ((Element) varObj);
                IVariable var = modelFactory.createVariable();
                var.setName(varElem.getAttributeValue("name"));
                var.setInitialValue(varElem.getAttributeValue("initialValue"));
                var.setNode(node);
                node.getVariables().add(var);
            }

            // parse tasks
            for (Object taskObj : behaviourElem.getChildren("task")) {
                Element taskElem = ((Element) taskObj);
                ITask task = modelFactory.createTask();
                task.setName(taskElem.getAttributeValue("name"));

                task.setNode(node); // Peter

                for (Object varObj : taskElem.getChildren("variable")) {
                    Element varElem = ((Element) varObj);
                    IVariable var = modelFactory.createVariable();
                    var.setName(varElem.getAttributeValue("name"));
                    var.setInitialValue(varElem
                            .getAttributeValue("initialValue"));
                    var.setTask(task);
                    task.getVariables().add(var);
                }

                // add coordinates for the editor
                try {
                    task
                            .setX(Integer.parseInt(taskElem
                                    .getAttributeValue("x")));
                    task
                            .setY(Integer.parseInt(taskElem
                                    .getAttributeValue("y")));
                } catch (Exception e) {
                    p("There are tasks w/o coordinates in the XML-file.");
                }

                // parse states
                for (Object stateObj : taskElem.getChildren("state")) {
                    Element stateElem = (Element) stateObj;
                    IState state = modelFactory.createState();
                    state.setName(stateElem.getAttributeValue("name"));
                    state.setTask(task);
                    // add coordinates for the editor
                    try {
                        state.setX(Integer.parseInt(stateElem
                                .getAttributeValue("x")));
                        state.setY(Integer.parseInt(stateElem
                                .getAttributeValue("y")));
                    } catch (Exception e) {
                        p("There are states w/o coordinates in the XML-file.");
                    }

                    // Check if there is an initialize-element
                    Element initializeElem = stateElem.getChild("initialize");
                    if (initializeElem != null) {
                        if (initializeElem.getAttributeValue("evaluate")
                                .equals("each"))
                            state
                                    .setInitializeEvaluation(EAIInitializeEvaluation.each);
                        else
                            state
                                    .setInitializeEvaluation(EAIInitializeEvaluation.once);

                        state.setContents(getTransitionContent(initializeElem
                                .getChildren(), state));
                    }

                    for (Object transitionObj : stateElem
                            .getChildren("transition")) {
                        Element transitionElem = (Element) transitionObj;
                        ITransition transition = modelFactory
                                .createTransition();
                        transition.setEvent(transitionElem
                                .getAttributeValue("event"));
                        // transition.setNextState()
                        // -- must be set later, when all states are known
                        String endTask = transitionElem
                                .getAttributeValue("endTask");
                        transition.setEndTask(endTask != null
                                && endTask.equals("yes"));
                        transition.setContents(getTransitionContent(
                                transitionElem.getChildren(), transition));

                        state.getTransitions().add(transition);

                    }

                    task.getStates().add(state);
                }

                // look for the start state...
                String startState = taskElem.getAttributeValue("startState");
                for (IState state : task.getStates()) {
                    if (state.getName().equals(startState)) {
                        task.setStartState(state);

                        // additonal for the editor
                        // for now only for the main task
                        try {
                            /*
                             * if (task.getName().equals("main")) {
                             * state.setIsStartState(true);
                             * arch.setStartState(state); // this is old }
                             */
                            ITransition tran = modelFactory.createTransition();
                            tran.setState((IState) task); // source
                            tran.setNextState(state); // target
                            tran.connect(); // inform states about their in- and
                            // outgoing transitions
                        } catch (Exception e) {
                            // e.printStackTrace();
                        }
                        // end additions for the editor

                        break;
                    }
                }

                // If this is the start task, then set it as the start task
                // of the node...
                if (task.getName().equals(
                        behaviourElem.getAttributeValue("startTask"))) {
                    node.setStartTask(task);
                }

                node.getTasks().add(task);
            }

            // Now that all tasks and states of the node are processed, we
            // can determine the next states of the transitions
            for (ITask task : node.getTasks()) {
                for (IState state : task.getStates()) {
                    int transitionCounter = 1;
                    for (ITransition transition : state.getTransitions()) {
                        Element transitionElem = null;
                        try {
                            XPath xpath = XPath.newInstance("task[@name=\""
                                    + task.getName() + "\"]/state[@name=\""
                                    + state.getName() + "\"]/transition["
                                    + transitionCounter + "]");
                            transitionElem = (Element) xpath
                                    .selectSingleNode(behaviourElem);
                        } catch (Exception e) {
                            System.out
                                    .println("Error parsing transition targets: ");
                            e.printStackTrace();
                        }
                        
                        String nextState = transitionElem
                                .getAttributeValue("nextState");
                        String startTask = transitionElem
                                .getAttributeValue("startTask");
                        if (nextState != null) {
                            transition
                                    .setNextState(searchState(node, nextState));
                        }
                        if (startTask != null) {
                            transition
                                    .setNextState(searchTask(node, startTask));
                        }
                        
                        // if nextState is not processed (nor startTask), define a self-transition

                        if (nextState == null && startTask == null) {
                            try {
                                transition.setNextState(state); // draw a
                                // transition to
                                // self
                                // for editor
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            transition.connect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // end additions

                        transitionCounter++;
                    }
                }
            }

            arch.getNodes().add(node);
        }

        return arch;
    }

    /**
     * Searches all states of a given node for a state with the given name.
     * Returns null if no such state could be found.
     * 
     * @param node
     * @param name
     * @return
     */
    private IState searchState(INodeType node, String name) {
        for (ITask task : node.getTasks()) {
            for (IState state : task.getStates()) {
                if (state.getName().equals(name)) {
                    return state;
                }
            }
        }

        return null;
    }

    /**
     * Searches all tasks of a given node for a task with the given name.
     * Returns null if no such task could be found.
     * 
     * @param node
     * @param name
     * @return
     */
    private ITask searchTask(INodeType node, String name) {
        for (ITask task : node.getTasks()) {
            if (task.getName().equals(name)) {
                return task;
            }
        }

        return null;
    }

    /**
     * Returns the content of a transition or initialize part of a state.
     * 
     * @param elements
     *            List of XML elements that are located in the tree below
     *            transition, initialize or case
     * @param container
     *            The container that includes the elements
     * @return A list of ITransitionContent objects
     */
    private List<ITransitionContent> getTransitionContent(List elements,
            IContentContainer container) {
        List<ITransitionContent> content = new ArrayList<ITransitionContent>();

        for (Object elemObj : elements) {
            Element elem = (Element) elemObj;
            if (elem.getName().equals("action")) {
                IAction action = modelFactory.createAction();
                action.setContainer(container);
                action.setName(elem.getAttributeValue("name"));
                Element result = elem.getChild("result");

                // add return value to the action
                if (result != null) {
                    action.setResult(result.getAttributeValue("select"));
                }

                // add parameters to the action
                for (Object paramObj : elem.getChildren("param")) {
                    Element paramElem = (Element) paramObj;
                    IParameter param = modelFactory.createParameter();
                    param.setName(paramElem.getAttributeValue("name"));
                    param.setValue(paramElem.getAttributeValue("value"));
                    param.setExpression(paramElem.getAttributeValue("select"));
                    param.setAction(action);

                    action.getParameters().put(param.getName(), param);
                }

                content.add(action);
            } else if (elem.getName().equals("condition")) {
                ICondition condition = modelFactory.createCondition();
                condition.setContainer(container);

                for (Object caseObj : elem.getChildren("case")) {
                    Element caseElem = (Element) caseObj;
                    ICaseArchitecture c = modelFactory.createCaseArchitecture();
                    c.setCondition(condition);
                    c.setExpression(caseElem.getAttributeValue("expr"));

                    c.setContents(getTransitionContent(caseElem.getChildren(),
                            c));

                    condition.getCases().add(c);
                }

                Element defaultElem = elem.getChild("default");
                if (defaultElem != null) {
                    ICaseArchitecture c = modelFactory.createCaseArchitecture();
                    c.setCondition(condition);
                    c.setContents(getTransitionContent(defaultElem
                            .getChildren(), c));

                    condition.setDefaultCase(c);
                }

                content.add(condition);
            }
        }

        return content;
    }

    /**
     * Saves the given architecture to an XML file.
     * 
     * @param arch
     *            The architecture to save
     * @param filename
     *            The name of the file
     */
    public static String saveArchitecture(ISystemBehaviour arch, String filename) {
        Element root = new Element("architecture");
        Attribute attr_name = new Attribute("name", arch.getName());
        root.setAttribute(attr_name);

        for (INodeType node : arch.getNodes()) {

            Element nodeElem = new Element("node");
            Attribute attr_nameNode = new Attribute("name", node.getName());
            nodeElem.setAttribute(attr_nameNode);
            root.addContent(nodeElem);

            // Add variables to a task
            for (IVariable var : node.getVariables()) {
                Element varElem = new Element("variable");
                if (var.getInitialValue() != null) {
                    varElem.setAttribute("initialValue", var.getInitialValue());
                }
                varElem.setAttribute("name", var.getName());
                nodeElem.addContent(varElem);
            }

            Element behaviourElem = new Element("behaviour");
            Attribute behavStartTaskAttr = new Attribute("startTask", node
                    .getStartTask().getName());
            behaviourElem.setAttribute(behavStartTaskAttr);
            nodeElem.addContent(behaviourElem);

            try {
                for (ITask task : node.getTasks()) {

                    Element taskElem = new Element("task");
                    Attribute taskName = new Attribute("name", task.getName());
                    taskElem.setAttribute(taskName);
                    if (task.getStartState() != null) {
                        Attribute startStateAttr = new Attribute("startState",
                                task.getStartState().getName());
                        taskElem.setAttribute(startStateAttr);
                    }
                    Attribute xAttr = new Attribute("x", "" + task.getX());
                    taskElem.setAttribute(xAttr);
                    Attribute yAttr = new Attribute("y", "" + task.getY());
                    taskElem.setAttribute(yAttr);
                    behaviourElem.addContent(taskElem);

                    // Add variables to a task
                    for (IVariable var : task.getVariables()) {
                        Element varElem = new Element("variable");
                        varElem.setAttribute("initialValue", var
                                .getInitialValue());
                        varElem.setAttribute("name", var.getName());
                        taskElem.addContent(varElem);
                    }

                    for (IState state : task.getStates()) {

                        /*
                         * Exclude so called endStates. Dirty, dirty, don't show
                         * Michael!!
                         */

                        if (((State) state).isEndState()) {
                            // Don't save that. End-States are computed.
                            continue;
                        }

                        Element stateElem = new Element("state");
                        Attribute stateName = new Attribute("name", state
                                .getName());
                        Attribute valX = new Attribute("x", "" + state.getX());
                        Attribute valY = new Attribute("y", "" + state.getY());
                        stateElem.setAttribute(stateName);
                        stateElem.setAttribute(valX);
                        stateElem.setAttribute(valY);
                        taskElem.addContent(stateElem);

                        // Initialize elem
                        if (state.getInitializeEvaluation() != null) {

                            Element initializeElem = new Element("initialize");

                            Attribute evalAttr = new Attribute("evaluate",
                                    state.getInitializeEvaluation().name());
                            initializeElem.setAttribute(evalAttr);
                            stateElem.addContent(initializeElem);

                            for (ITransitionContent content : state
                                    .getContents()) {

                                if (content instanceof IAction) {
                                    IAction action = (IAction) content;
                                    Element actionElem = new Element("action");
                                    Attribute actionName = new Attribute(
                                            "name", action.getName());
                                    actionElem.setAttribute(actionName);
                                    initializeElem.addContent(actionElem);

                                    for (String key : action.getParameters()
                                            .keySet()) {
                                        Element paramElem = new Element("param");
                                        try {
                                            Attribute paramName = new Attribute(
                                                    "name", action
                                                            .getParameters()
                                                            .get(key).getName());
                                            paramElem.setAttribute(paramName);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        try {
                                            if (action.getParameters().get(key)
                                                    .getExpression() != null) {
                                                Attribute paramSel = new Attribute(
                                                        "select",
                                                        action
                                                                .getParameters()
                                                                .get(key)
                                                                .getExpression());
                                                paramElem
                                                        .setAttribute(paramSel);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        try {
                                            if (action.getParameters().get(key)
                                                    .getValue() != null) {
                                                Attribute paramVal = new Attribute(
                                                        "value",
                                                        action.getParameters()
                                                                .get(key)
                                                                .getValue());
                                                paramElem
                                                        .setAttribute(paramVal);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        actionElem.addContent(paramElem);

                                    }

                                } else {
                                    if (content instanceof ICondition) { // <condition>
                                        // <case><action><condition..></case>)
                                        // |
                                        // <default><action><condition..></default>

                                        ICondition theContent = (ICondition) content;

                                        // computes the contents of "content"
                                        // recursively
                                        Element condElems = computeContentXML(theContent);
                                        initializeElem.addContent(condElems);

                                    } // ends conditions
                                }
                            }// ends action
                        }

                        for (ITransition transition : state.getTransitions()) {

                            Element transitionElem = new Element("transition");
                            if (transition.getEvent() != null) {
                                Attribute transitionEvent = new Attribute(
                                        "event", transition.getEvent());
                                transitionElem.setAttribute(transitionEvent);
                            }

                            if (transition.isEndTask()) {
                                Attribute endTaskAttr = new Attribute(
                                        "endTask", "yes");
                                transitionElem.setAttribute(endTaskAttr);
                            }

                            // Don't set startTask, if endTask=true
                            if (transition.getNextState() instanceof Task
                                    && !(transition.isEndTask())) {

                                Attribute startTaskAttr = new Attribute(
                                        "startTask", transition.getNextState()
                                                .getName());
                                transitionElem.setAttribute(startTaskAttr);
                            }

                            else if (transition.getNextState() != null
                                    && transition.getNextState() instanceof State
                                    && !(((State) transition.getNextState())
                                            .isEndState())) { // Ignore
                                                                // endStates,
                                                                // this targets
                                                                // will be
                                                                // computed
                                Attribute transitionTarget = new Attribute(
                                        "nextState", transition.getNextState()
                                                .getName());
                                transitionElem.setAttribute(transitionTarget);
                            }

                            stateElem.addContent(transitionElem);

                            for (ITransitionContent content : transition
                                    .getContents()) {

                                if (content instanceof IAction) {
                                    IAction action = (IAction) content;
                                    Element actionElem = new Element("action");
                                    Attribute actionName = new Attribute(
                                            "name", action.getName());
                                    actionElem.setAttribute(actionName);
                                    transitionElem.addContent(actionElem);

                                    for (String key : action.getParameters()
                                            .keySet()) {
                                        Element paramElem = new Element("param");
                                        Attribute paramName = new Attribute(
                                                "name", action.getParameters().get(key).getName());
                                        paramElem.setAttribute(paramName);
                                        try {
                                            if (action.getParameters().get(key)
                                                    .getExpression() != null) {
                                                Attribute paramSel = new Attribute(
                                                        "select",
                                                        action
                                                                .getParameters()
                                                                .get(key)
                                                                .getExpression());
                                                paramElem
                                                        .setAttribute(paramSel);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        try {
                                            if (action.getParameters().get(key)
                                                    .getValue() != null) {
                                                Attribute paramVal = new Attribute(
                                                        "value",
                                                        action.getParameters()
                                                                .get(key)
                                                                .getValue());
                                                paramElem
                                                        .setAttribute(paramVal);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        actionElem.addContent(paramElem);
                                    }
                                } else {
                                    if (content instanceof ICondition) { // <condition>
                                        // <case><action><condition..></case>)
                                        // |
                                        // <default><action><condition..></default>

                                        ICondition theContent = (ICondition) content;

                                        // computes the contents of "content"
                                        // recursively
                                        Element condElems = computeContentXML(theContent);
                                        transitionElem.addContent(condElems);
                                    }
                                }
                            }// ends action
                        }// ends transition
                    }// ends state
                }// ends task
            }// ends node
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        Document doc = new Document(root);

        /*
         * On-terminal output for testing purpose Output to file is done in
         * SysGraphicalEditor::doSave()
         */
        XMLOutputter outputter = new XMLOutputter();
        outputter.setFormat(Format.getPrettyFormat());

        if (filename != null) {
            try {
                FileOutputStream stream = new FileOutputStream(filename);
                outputter.output(doc, stream);
                stream.flush();
                stream.close();
            } catch (IOException e) {
                java.lang.System.out.println("Cannot write to file: " + e);
                e.printStackTrace();
            }
        }

        return outputter.outputString(doc);
    }

    /**
     * Helper method for saveArchitecture.
     * 
     * @param content
     *            A content-object as part of the architecture, that may contain
     *            an undefined number of other content-objects.
     * @return A XML-Element (with sub-elements) that might be added to a
     *         container-element.
     */
    private static Element computeContentXML(ICondition content) {

        Element conditionElem = new Element("condition");

        if (content.getCases() != null) { // <case>

            for (ICaseArchitecture theCase : content.getCases()) {
                Element caseElem = new Element("case");
                Attribute caseExpression = new Attribute("expr", theCase
                        .getExpression());
                caseElem.setAttribute(caseExpression);
                conditionElem.addContent(caseElem);

                for (ITransitionContent tranCon : theCase.getContents()) {

                    if (tranCon instanceof IAction) {
                        IAction action = (IAction) tranCon;
                        Element actionElem = new Element("action");
                        Attribute actionName = new Attribute("name", action
                                .getName());
                        actionElem.setAttribute(actionName);
                        caseElem.addContent(actionElem);

                        for (String key : action.getParameters().keySet()) {
                            Element paramElem = new Element("param");
                            try {
                                Attribute paramName = new Attribute("name",
                                        action.getParameters().get(key)
                                                .getName());
                                paramElem.setAttribute(paramName);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if (action.getParameters().get(key).getExpression() != null) {
                                Attribute paramSel = new Attribute("select",
                                        action.getParameters().get(key)
                                                .getExpression());
                                paramElem.setAttribute(paramSel);
                            }

                            if (action.getParameters().get(key).getValue() != null) {
                                Attribute paramVal = new Attribute("value",
                                        action.getParameters().get(key)
                                                .getValue());
                                paramElem.setAttribute(paramVal);

                            }
                            actionElem.addContent(paramElem);
                        }
                    }

                    else if (tranCon instanceof ICondition) {

                        ICondition theCont = (ICondition) tranCon;
                        // computes the contents of "content" recursively
                        Element condElems = computeContentXML(theCont);
                        caseElem.addContent(condElems);
                    }
                }
            }
        }

        if (content.getDefaultCase() != null) { // <default>

            Element defElem = new Element("default");
            conditionElem.addContent(defElem);
            ICaseArchitecture def = content.getDefaultCase();

            for (ITransitionContent cont : def.getContents()) {
                if (cont instanceof IAction) { // actions of default
                    IAction action = (IAction) cont;
                    Element actionElem = new Element("action");
                    Attribute actionName = new Attribute("name", action
                            .getName());
                    actionElem.setAttribute(actionName);
                    defElem.addContent(actionElem);

                    for (String key : action.getParameters().keySet()) {
                        Element paramElem = new Element("param");
                        Attribute paramName = new Attribute("name", action.getParameters().get(key).getName());
                        paramElem.setAttribute(paramName);

                        if (action.getParameters().get(key).getExpression() != null) {
                            Attribute paramSel = new Attribute("select", action
                                    .getParameters().get(key).getExpression());
                            paramElem.setAttribute(paramSel);
                        }
                        if (action.getParameters().get(key).getValue() != null) {
                            Attribute paramVal = new Attribute("value", action
                                    .getParameters().get(key).getValue());
                            paramElem.setAttribute(paramVal);

                        }
                        actionElem.addContent(paramElem);
                    }
                }
                if (cont instanceof ICondition) {

                    ICondition theCont = (ICondition) cont;
                    // computes the contents of "content" recursively
                    Element condElems = computeContentXML(theCont);
                    defElem.addContent(condElems);
                }
            }
        }
        return conditionElem;
    }

    /**
     * Only for test purposes.
     * 
     * @param args
     */
    public static void main(String[] args) {
        SystemBehaviourModelFactory factory = new SystemBehaviourModelFactory();
        SystemBehaviourXMLAdapter xml = new SystemBehaviourXMLAdapter(factory);

        ISystemBehaviour arch = xml.loadArchitecture("xml/Architecture3.xml");
        System.out.println("loaded architecture " + arch.getName());

        for (INodeType node : arch.getNodes()) {
            p("Node: " + node.getName());
            for (ITask task : node.getTasks()) {
                p(" Task: " + task.getName());
                for (IState state : task.getStates()) {
                    p("  State: " + state.getName());
                    for (ITransition trans : state.getTransitions()) {
                        p("  Transition: " + trans);
                        for (ITransitionContent content : trans.getContents()) {
                            p("    Content: " + content);
                        }
                    }
                }
            }
        }
    }

    /**
     * Prints a line to the standard output. Only for test purposes.
     * 
     * @param s
     */
    public static void p(String s) {
        System.out.println(s);
    }

}
