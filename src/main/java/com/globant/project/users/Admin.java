package com.globant.project.users;

import java.util.List;
import java.util.stream.Collectors;

import com.globant.project.catalog.Catalog;
import com.globant.project.comic.Comic;
import com.globant.project.exceptions.InvalidGenreException;
import com.globant.project.exceptions.UserExistsException;
import com.globant.project.exceptions.inputs.InvalidComicGenreException;
import com.globant.project.exceptions.inputs.InvalidComicNameException;
import com.globant.project.loan.Loan;


public class Admin extends User {

	public Admin(String id, String pass) {
		super(id, pass);
	}

	public void registerUser(String id, String password) throws Exception {
		if (id.isEmpty())
			throw new Exception("FAILED. Username can't be void");
		else if (password.isEmpty())
			throw new Exception("FAILED. Password can't be void");
		else if(getUsers().stream().filter(user -> user.getId().equals(id)).collect(Collectors.toList()).isEmpty())
			getCatalog().addUser(new User(id,password));
		else
			throw new UserExistsException("User already exists");
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
	
	public void registerComic(Comic spidermanComic) throws InvalidComicNameException, InvalidComicGenreException {
		if(spidermanComic.getName().isEmpty())
			throw new InvalidComicNameException("Invalid name. It can't be void");
		if(spidermanComic.getGenre().isEmpty())
			throw new InvalidComicGenreException("Invalid genre. It can't be void");
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
	
	public void editUsername(String actualId, String newId) throws Exception {
		if(newId.isEmpty())
			throw new Exception("FAILED. Username can't be void");
		else if(getUsers().stream().anyMatch(user -> user.getId().equals(newId)))
			throw new UserExistsException("ID already taken");
		else
			getUsers().stream().filter(user -> user.getId().equals(actualId)).findFirst().get().setId(newId);
	}

	public void editPassword(String id, String newPassword) throws Exception {
		if(newPassword.isEmpty())
			throw new Exception("FAILED. Password can't be void.");
		else
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
