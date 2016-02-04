package com.globant.project.users;

import java.util.Set;

import com.globant.project.catalog.Catalog;
import com.globant.project.comic.Comic;
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
	
	public Set<Comic> getComics() {
		return Catalog.getInstance().getComics();
	}
	
	public void showComics(){
		getComics().stream().forEach(comic -> System.out.println(comic.getName() + nextLine()));
	}

	public void execute(int selectedOption) {
		switch (selectedOption) {
		case 1:
			showComics();
			break;
		default:
			break;
		}
	}

}
