package com.globant.project.users;

import java.util.List;

public class Admin extends User {

	public Admin(String id, String pass) {
		super(id, pass);
	}

	public void registerUser(String id, String password) {
		catalog.addUser(new User(id,password));
	}

	public void revokeUser(String aName) {
			getUsers().removeIf(user -> user.getId().equals(aName));
	}
	
	public boolean userExist(String aName){
		return getUsers().stream().anyMatch(user -> user.getId().equals(aName));
	}
	
	public User getUser(String anUser){
		return getUsers().stream().filter(user -> user.getId().equals(anUser)).findFirst().get();
	}
	
	public List<User> getUsers(){
		return catalog.getUsers();
	}

}
