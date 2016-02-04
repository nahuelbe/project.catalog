package com.globant.project.users;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.globant.project.catalog.Catalog;
import com.globant.project.comic.Comic;

public class Admin extends User {
	


	public Admin(String id, String pass) {
		super(id, pass);
	}

	public void registerUser(String id, String password) {
		getCatalog().addUser(new User(id,password));
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
		return getCatalog().getUsers();
	}
	
	public void execute(int option) {
		switch (option) {
		case 1:
		//	catalog.viewCatalog();
			break;
		case 2:
			fillRegister();
			break;
		case 3:
			deleteUser();
		default :
			break;
		}
	}

	public void registerComic(Comic spidermanComic) {
		getCatalog().registerComic(spidermanComic);
	}

	public Comic searchComic(String aName) {
		return getCatalog().searchComic(aName);
	}

	public Set<Comic> getComics() {
		return getCatalog().getComics();
	}
	
	private Catalog getCatalog(){
		return Catalog.getInstance();
	}

	public void removeUser(String anId) {
		getUsers().remove(getUsers().stream().filter(user -> user.getId().equals(anId)).findFirst().get());
	}
	
	private void fillRegister(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter new user ID: ");
		String id = scan.nextLine();
		System.out.println("Enter new user password: ");
		String password = scan.nextLine();
		registerUser(id,password.toString());
	}
	
	private void deleteUser(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the ID to remove: ");
		String id = scan.nextLine();
		revokeUser(id);
	}

}
