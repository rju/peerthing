package de.peerthing.systembehavioureditor.propertyeditor;

import java.util.List;

/**
 * This class is used to create folders in the tree structure.
 * 
 *@author Michael, (and a littel bit Patrik) 
 */

public class ParentObject {
	private String name;
    
    //the childreen of this object in the tree are saved in this variable 
	private List<Object> elements;
	
    // the father node in the tree is saved in this variable
    private Object father;
	
	public ParentObject(List<Object> elements, String name, Object parent) {
		this.elements = elements;
		this.name = name;
		this.father = parent;
	}
	
	public String getName() {
		return name;
	}
	
	public List getElements() {
		return elements;
	}
	
	public Object getFather(){
		return father;
	}
	
	public void setFather(Object father){
		this.father = father;
	}
}
