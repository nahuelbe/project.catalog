package com.globant.project.users;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.globant.project.catalog.Catalog;
import com.globant.project.comic.Comic;

public class Admin extends User {
	
	Catalog catalog = Catalog.getInstance();

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
	
	public void execute(int option) {
		switch (option) {
		case 1:
		//	catalog.viewCatalog();
			break;
		case 2:
			Scanner scan = new Scanner(System.in);
			System.out.println("Enter new user ID: ");
			String id = scan.nextLine();
			System.out.println("Enter new user password: ");
			String password = scan.nextLine();
			registerUser(id,password.toString());
		default :
			break;
		}
	}

	public void registerComic(Comic spidermanComic) {
		catalog.registerComic(spidermanComic);
	}

	public Comic searchComic(String aName) {
		return catalog.searchComic(aName);
	}

	public Set<Comic> getComics() {
		return catalog.getComics();
	}

	public void removeUser(String anId) {
		getUsers().remove(getUsers().stream().filter(user -> user.getId().equals(anId)).findFirst().get());
	}

}
