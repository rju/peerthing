package de.peerthing.systembehavioureditor.gefeditor.editparts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FanRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;

import de.peerthing.systembehavioureditor.gefeditor.commands.TransitionDeleteCommand;
import de.peerthing.systembehavioureditor.model.ITransition;
import de.peerthing.systembehavioureditor.model.editor.Task;
import de.peerthing.systembehavioureditor.model.editor.Transition;

/**
 * This class specifies the Editpart of Transitions. Initialy a Figure for the Transition
 * will be created, on position changes or manipulation of the label's text a refresh will be called.
 * 
 * @author Petra Beenken
 * @author Peter Schwenkenberg
 * @review Michael, 2006-03-23
 */

public class TransitionEditPart extends AbstractConnectionEditPart implements
        PropertyChangeListener {

	private Label label;
	private PolylineConnection pc;
	private MidpointLocator ml;
	
	/*
	 * Gives any self-transition a number.
	 */
	private int selfTranNo = 0;
	
	/**
	 * Creates the initial figure of a transition 
	 */
    protected IFigure createFigure() {

        pc = new PolylineConnection();
        pc.setTargetDecoration(new PolygonDecoration());
        
        label = new Label();
        label.setOpaque(true);
        label.setBackgroundColor(new Color(Display.getCurrent(), 242, 248, 255));
        label.setForegroundColor(ColorConstants.darkBlue);
        label.setBorder(new MarginBorder(1));
        label.setFont(new Font(Display.getCurrent(), "Arial", 9, 2));
        ml = new MidpointLocator(pc, 0);
        ml.setRelativePosition(PositionConstants.SOUTH_EAST);
        pc.add(label, ml);

        return pc;
    }

    /**
     * Redraws a transition when things have changed.
     */
    protected void refreshVisuals() {

    	label.setText(this.getCastedModel().getEvent());
    	
    	// Mock-up for endTask="yes"
    	if (getCastedModel().isEndTask()) {
    		//label.setText(this.getCastedModel().getEvent() + " (ends this task)");
    		label.setText(this.getCastedModel().getEvent());
    	}
        
        /**
         * Don't show a transition name if the source of the transition is a task.
         * This includes the start task (dot) of course.
         */
        try{
        	if (getCastedModel().getState() instanceof Task) {
	       		label.setText("");
	       		label.setOpaque(false);
	       		
        	}
        	
        }
        catch (Exception e) {e.printStackTrace();}
        
        ConnectionLayer cLayer = (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);
        
        FanRouter ar = new FanRouter();
        ar.setSeparation(35);
        cLayer.setConnectionRouter(ar);
        
        try {
        	// If we have a transition to self, display it differently
        	if (this.getCastedModel().getState().equals(getCastedModel().getNextState())) {
	        	
	        	int numberOfSelfTran = -1;
	        	try {
		        	for (ITransition t : getCastedModel().getState().getTransitions()) {
		        		if (t.getState().equals(getCastedModel().getNextState())){
		        			numberOfSelfTran++;
		        			if (getCastedModel().equals(t)) {
		        				selfTranNo = numberOfSelfTran;
		        			}
		        		}
		        	}
	        	}
	        	catch (Exception e) {
	        		System.out.println("Cannot examine self transition count" + e.getCause());
	        		selfTranNo = 3; // failsafe
	        	}
        	
	        	// Display a triangle as source decoration
	        	// I have to admit that the implementation of self-transitions
	        	// is a little shitty.
	        	PolygonDecoration decoration = new PolygonDecoration();
	        	PointList decorationPointList = new PointList();
		        decorationPointList.addPoint(0, 0);
		        decorationPointList.addPoint(-2, 6);
		        decorationPointList.addPoint(-1, 10);
		        decorationPointList.addPoint(1, 10);
		        decorationPointList.addPoint(2, 6);
		        decorationPointList.addPoint(0, 0);
	        	decoration.setFill(false);
	        	decoration.setTemplate(decorationPointList);
	        	decoration.setScale(6 + ((selfTranNo-1))*5, 6 + ((selfTranNo-1))*1.2);
	        		
	        	pc.setSourceDecoration(decoration);
	        	
	        	// Display a loop as target decoration
	        	PolygonDecoration targetDecoration = new PolygonDecoration();
	        	PointList targetDecorationPointList = new PointList();
	        	targetDecorationPointList.addPoint(0, 0);
	        	targetDecorationPointList.addPoint(-1, 1);
	        	targetDecorationPointList.addPoint(1, 1);
	        	targetDecorationPointList.addPoint(0, 0);
	        	targetDecoration.translate(0, 4);
	        	targetDecoration.setScale(4, 8);
	        	targetDecoration.setTemplate(targetDecorationPointList);
	        	pc.setTargetDecoration(targetDecoration);
	        	
	        	Polyline loop = new Polyline();
	        	loop.addPoint(new Point(100, 100));
	        	loop.addPoint(new Point(20, 200));
	        	
	        	Point p1 = this.getConnectionFigure().getBounds().getBottom();
	        	loop.addPoint(p1);
	        	
	        	PointList list = this.getConnectionFigure().getPoints();
	        	
	        	this.getConnectionFigure().setPoints(list);
	        
	        	label.setOpaque(false);
	        	ml.setGap(selfTranNo*12);
	           label.setText(getWhiteSpaces(numberOfSelfTran) + getCastedModel().getEvent());
	        	pc.setFocusTraversable(true);
	        	}
	        }
        catch (Exception e) {
        	System.out.println("Cannot examine whether there is a self transition." + e);
        }
        
    	/**
    	 * If the target of the transition is a task, draw a trunc.
    	 */
    	if (getCastedModel().getNextState() instanceof Task) {
    		
       		PolygonDecoration taskDecoration = new PolygonDecoration();
        	PointList taskDecorationPointList = new PointList();
        	
        	// arrow
        	taskDecorationPointList.addPoint(0, 0);
        	taskDecorationPointList.addPoint(-1, -1);
        	taskDecorationPointList.addPoint(-1, 1);
        	taskDecorationPointList.addPoint(0, 0);

        	// trunk
        	taskDecorationPointList.addPoint(0, -8);
        	taskDecorationPointList.addPoint(0 , 8);
        	
        	taskDecoration.setLineWidth(2);
        	taskDecoration.setTemplate(taskDecorationPointList);
        	
        	// draw it only, if this is the only transition to a task 

        	Task theTask = (Task) (getCastedModel().getNextState());

        	/**
        	 * draw always a trunc, it's not worth diving into improvements
        	 */
        //	if (!theTask.hasDecoration) {
        		pc.setTargetDecoration(taskDecoration);
        		theTask.hasDecoration = true;
        		this.getCastedModel().hasTaskDecoration = true;
        		
        		// invoke to replace the icon at the task
        		try {
        			theTask.getEditPart().refresh();
        		} catch (Exception e) {};
        //	}
        	
    	}
        
        Dimension size = label.getPreferredSize();
        Rectangle bounds = new Rectangle(new Point(), size);
        ((GraphicalEditPart) getParent()).setLayoutConstraint(this, figure,
                bounds);
    }

    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
                new ConnectionEndpointEditPolicy());

        installEditPolicy(EditPolicy.CONNECTION_ROLE,
                new ConnectionEditPolicy() {
                    protected Command getDeleteCommand(
                            final GroupRequest request) {
                        return new TransitionDeleteCommand(
                                (Transition) getModel());
                    }
                });
    }

    public void activate() {
        super.activate();
        getCastedModel().addPropertyChangeListener(this);
    }

    public void deactivate() {
        getCastedModel().removePropertyChangeListener(this);
        super.deactivate();
    }

    public void propertyChange(PropertyChangeEvent evt) {
        refresh();
    }

    private Transition getCastedModel() {
        return (Transition) getModel();
    }
    
    private static String getWhiteSpaces(int count) {
    	
    	String whiteString = "";
    	
    	for (int i = 0; i <= count; i++ ) {
    		whiteString = whiteString + " ";
    	}
    	return whiteString;
    }
}