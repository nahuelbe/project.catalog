package com.globant.project.users;

import java.util.List;
import com.globant.project.catalog.Catalog;
import com.globant.project.comic.Comic;
import com.globant.project.exceptions.InvalidGenreException;
import com.globant.project.loan.Loan;


public class Admin extends User {

	public Admin(String id, String pass) {
		super(id, pass);
	}

	public void registerUser(String id, String password) {
		getCatalog().addUser(new User(id,password));
	}

	public boolean revokeUser(String aName) {
			return (getUsers().removeIf(user -> user.getId().equals(aName)));
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
	
	public void registerComic(Comic spidermanComic) {
		getCatalog().registerComic(spidermanComic);
	}

	public Comic searchComic(String aName) {
		return getCatalog().searchComic(aName);
	}
	
	private Catalog getCatalog(){
		return Catalog.getInstance();
	}

	public void editGenre(String actual, String changed) throws InvalidGenreException {
		getCatalog().editGenre(actual, changed);
	}
	
	public void editUsername(String actualId, String newId) {
		getUsers().stream().filter(user -> user.getId().equals(actualId)).findFirst().get().setId(newId);
	}

	public void editPassword(String id, String newPassword) {
		getUsers().stream().filter(user -> user.getId().equals(id)).findFirst().get().setPassword(newPassword);	
	}

	public void removeComic(String aName) {
		Comic comicToRemove = searchComic(aName);
		if(comicToRemove.getCopies() == 1)
			getComics().remove(comicToRemove);
		else
			comicToRemove.removeCopy();
	}

	public void removeGenre(String string) throws InvalidGenreException {
		getCatalog().removeGenre(string);
	}

	public List<Loan> getLoans() {
		return getCatalog().getLoans();
	}
	
}
