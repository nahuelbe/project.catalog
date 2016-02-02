package com.globant.project.users;

public class Admin extends User {

	public Admin(String id, String pass) {
		super(id, pass);
	}

	public void register(String id, String password) {
		catalog.addUser(new User(id,password));
	}

}
