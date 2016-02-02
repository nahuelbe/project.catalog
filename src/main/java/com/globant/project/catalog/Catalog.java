package com.globant.project.catalog;

import java.util.ArrayList;
import java.util.List;

import com.globant.project.users.User;

public class Catalog {
	
	private static Catalog singleton = new Catalog();
	private List<User> users = new ArrayList<User>();
	
	public static Catalog getInstance(){
		return singleton;
	}

	public List<User> getUsers() {
		return users;
	}

	public void addUser(User anUser) {
		singleton.users.add(anUser);
	}
	
	public static void emptyUsers(){
		getInstance().users = new ArrayList<User>();
	}
}
