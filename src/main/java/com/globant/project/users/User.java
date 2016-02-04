package com.globant.project.users;

import com.globant.project.interfaces.NextLiner;
import com.globant.project.interfaces.Scanneable;

public class User implements Scanneable, NextLiner{
	
	String id, password;

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
