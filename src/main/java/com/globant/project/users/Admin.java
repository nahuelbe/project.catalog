package com.globant.project.users;

import java.util.List;
import java.util.Set;

import com.globant.project.catalog.Catalog;
import com.globant.project.comic.Comic;
import com.globant.project.exception.NoGendersException;
import com.globant.project.exceptions.InvalidGenreException;

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
			viewCatalogMenu();
			break;
		case 2:
			fillUserRegister();
			break;
		case 3:
			deleteUser();
			break;
		case 4:
			fillComicRegister();
		default :
			break;
		}
	}

	private void fillComicRegister() {
		registerComic(new Comic(scanStringWithMessage("Enter new Comic name: "),
						scanStringWithMessage("Enter gender of new comic: ")));
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

	private void fillUserRegister(){
		registerUser(scanStringWithMessage("Enter new user ID: "),scanStringWithMessage("Enter new user password: "));
	}
	
	private void deleteUser(){
		revokeUser(scanStringWithMessage("Enter the ID to remove: "));
	}
	
	private void viewCatalogMenu() {
		System.out.println("Catalog visualization preference");
		viewCatalogMenuOptions();
		viewCatalogPreference(scanIntOption());
	}
	
	private void viewCatalogPreference(int option) {
		switch (option) {
		case 1:
			showComics();
			break;
		case 2:
			try{ selectGenre(); }
			catch (InvalidGenreException|NoGendersException ex){ 
				System.out.println(ex.getMessage()); 
				viewCatalogMenu();
			}
			
		default:
			break;
		}
	}

	private void selectGenre() throws InvalidGenreException,NoGendersException{
		viewGenres();
		List <Comic> filteredComics = getComicsByGender(scanStringWithMessage("Type a genre from the list"));
		if(filteredComics.isEmpty())
			throw new InvalidGenreException("The genre is invalid");
		else
			viewFilteredcomics(filteredComics);
	}

	private void viewFilteredcomics(List<Comic> filteredComics) {
		filteredComics.stream().forEach(comic -> System.out.println(comic.getName() + nextLine()));
	}

	private void viewCatalogMenuOptions(){
		System.out.println("1. View all comics" + nextLine() + "2. Sort by genre" + nextLine());
	}

	public Set<String> getGenres() {
		return getCatalog().getGenres();
	}

	public void editGenre(String actual, String changed) {
		getCatalog().editGenre(actual, changed);
	}

	public List<Comic> getComicsByGender(String gender) {
		return getCatalog().getComicsByGender(gender);
	}
	
	private void viewGenres() throws NoGendersException {
		Set<String> genres = getGenres();
		if(genres.isEmpty())
			throw new NoGendersException("There isn't any gender in the catalog" + nextLine());
		else
			genres.stream().forEach(genre -> System.out.println(genre + nextLine()));
	}
}
