package com.globant.project.comic;

public class Comic {
	
	String name;
	int copies = 1;
	
	public Comic(String aName){
		name = aName;
	}
	
	public String getName() {
		return name;
	}
	
	public int getCopies(){
		return copies;
	}
	
	public void addCopy(){
		copies++;
	}
}
