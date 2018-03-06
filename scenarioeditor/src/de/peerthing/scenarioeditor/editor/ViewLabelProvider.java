package de.peerthing.scenarioeditor.editor;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import de.peerthing.scenarioeditor.model.IListen;
import de.peerthing.scenarioeditor.model.IUserBehaviour;
import de.peerthing.scenarioeditor.model.ICallUserBehaviour;
import de.peerthing.scenarioeditor.model.ICase;
import de.peerthing.scenarioeditor.model.IConnectionCategory;
import de.peerthing.scenarioeditor.model.IDefaultCase;
import de.peerthing.scenarioeditor.model.IDelay;
import de.peerthing.scenarioeditor.model.IListWithParent;
import de.peerthing.scenarioeditor.model.ILoop;
import de.peerthing.scenarioeditor.model.INodeCategory;
import de.peerthing.scenarioeditor.model.INodeConnection;
import de.peerthing.scenarioeditor.model.INodeResource;
import de.peerthing.scenarioeditor.model.IResourceCategory;
import de.peerthing.scenarioeditor.model.IScenarioCondition;
import de.peerthing.scenarioeditor.model.IUserAction;

/**
* The Label of a tree node is figured out here   
* @author Hendrik Angenendt, Patrik Schulz
*/
public class ViewLabelProvider extends LabelProvider {
    
    /**
     * This method returns a string with the name of called category
     */
   public String getText(Object obj) {
       if (obj instanceof IConnectionCategory) {
           return ((IConnectionCategory) obj).getName();
       } else if (obj instanceof IListWithParent) {
           return ((IListWithParent) obj).getName();
       } else if (obj instanceof IResourceCategory) {
           return ((IResourceCategory) obj).getName();
       } else if (obj instanceof INodeCategory) {
           return ((INodeCategory) obj).getName();
       } else if (obj instanceof IUserAction) {
           return "Action: "+((IUserAction)obj).getName();            
       } else if (obj instanceof IScenarioCondition) {
           return "Condition";
       } else if (obj instanceof ILoop) {
           return "Loop";
       } else if (obj instanceof IUserBehaviour) {
           return ((IUserBehaviour) obj).getName();
       } else if (obj instanceof IDelay) {
           return "Delay";
       } else if (obj instanceof IListen) {
    	   String listenEvent = "";
    	   if (((IListen)obj).getEvent()!= null){
    		   listenEvent = ((IListen)obj).getEvent();
    	   }
           return "Listen: " + listenEvent;
       } else if (obj instanceof INodeConnection) {
           return ((INodeConnection) obj).getCategory().getName();
       } else if (obj instanceof INodeResource) {
           return ((INodeResource) obj).getCategory().getName();
       } else if (obj instanceof ICallUserBehaviour) {
           return "Call Behaviour";
       } else if (obj instanceof ICase && !(obj instanceof IDefaultCase)) {
           return "Case";
       } else if (obj instanceof IDefaultCase) {
           return "Default Case";
       }
       return obj.toString();
   }

   /**
    * This method returns an image 
    */
   public Image getImage(Object obj) {
       //String imageKey = ISharedImages.IMG_OBJ_FILE;
       Image img = null;           
       return img;
   }
}