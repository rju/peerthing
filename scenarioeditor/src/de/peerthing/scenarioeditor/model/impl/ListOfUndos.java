package de.peerthing.scenarioeditor.model.impl;

import java.util.ArrayList;

import de.peerthing.scenarioeditor.ScenarioEditorPlugin;

public class ListOfUndos<ScenarioUndo> extends ArrayList<ScenarioUndo>{
	
	public boolean add(ScenarioUndo undo){		
		for (int i = 0; i < ScenarioEditorPlugin.getDefault().getRedoList().size(); i++){
			ScenarioEditorPlugin.getDefault().getRedoList().remove(0);	
		}		
		super.add(undo);
		if (ScenarioEditorPlugin.getDefault().getUndoList().size()>100){
			super.remove(0);
		}
		return true;		
	}
	
	public boolean addForRedo(ScenarioUndo undo){		
				
		super.add(undo);
		if (ScenarioEditorPlugin.getDefault().getUndoList().size()>100){
			super.remove(0);
		}
		return true;		
	}
	
}
