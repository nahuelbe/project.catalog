package com.globant.project.catalog;

import java.util.ArrayList;

import com.globant.project.users.User;

public class Catalog {
	
	private static Catalog singleton = new Catalog();
	private ArrayList<User> users = new ArrayList<User>();
	
	public static Catalog getInstance(){
		return singleton;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void addUser(User anUser) {
		singleton.users.add(anUser);
	}
}
