package com.globant.project.users;

import com.globant.project.catalog.Catalog;

public class User{
	
	String id, password;
	Catalog catalog = Catalog.getInstance();

	public User(String id, String password) {
		this.id = id;
		this.password = password;
	}

	public String getId() {
		return id;
	}
	
	public String getPassword() {
		return password;
	}

	public void execute(int selectedOption) {
		// TODO Auto-generated method stub
		
	}

}
