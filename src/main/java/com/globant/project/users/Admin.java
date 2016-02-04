package com.globant.project.users;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;

import com.globant.project.catalog.Catalog;
import com.globant.project.comic.Comic;
import com.globant.project.exception.NoGendersException;
import com.globant.project.exceptions.InvalidGenreException;
import com.globant.project.exceptions.InvalidOptionException;

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
			editUser();
			break;
		case 4:
			deleteUser();
		case 5:
			fillComicRegister();
		default :
			break;
		}
	}

	private void editUser(){
		showEditOptions();
		try{
			switch (editOptionSelection()) {
			case 1:
				break;
			case 2:
				break;
			default:
				break;
			}
		} 
		catch (InputMismatchException ex){
			System.out.println(nextLine() + 
			"You must enter a valid number" + nextLine()); 
			editUser();
		}
		catch (InvalidOptionException ex) {
			System.out.println(ex.getMessage());
			editUser();
		}
	}

	private void showEditOptions() {
		System.out.println("1. Edit user ID" + nextLine() + "2. Edit user password");
	}

	private void fillComicRegister() {
		registerComic(new Comic(scanStringWithMessage(nextLine() + "Enter new Comic name: " + nextLine()),
						scanStringWithMessage(nextLine() + "Enter gender of new comic: " + nextLine())));
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
		registerUser(scanStringWithMessage(nextLine() + "Enter new user ID: " + nextLine()),
					scanStringWithMessage(nextLine() + "Enter new user password: " + nextLine()));
		
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
		List <Comic> filteredComics = getComicsByGender(scanStringWithMessage(nextLine() + "Type a genre from the list") + nextLine());
		if(filteredComics.isEmpty())
			throw new InvalidGenreException(nextLine() + "The genre is invalid" + nextLine());
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
			throw new NoGendersException(nextLine() + "There isn't any gender in the catalog" + nextLine());
		else
			genres.stream().forEach(genre -> System.out.println(genre + nextLine()));
	}

	public void editUsername(String actualId, String newId) {
		getCatalog().getUsers().stream().filter(user -> user.getId().equals(actualId)).findFirst().get().setId(newId);
	}

	public void editPassword(String id, String newPassword) {
		getCatalog().getUsers().stream().filter(user -> user.getId().equals(id)).findFirst().get().setPassword(newPassword);	
	}
	
	private int editOptionSelection() throws InvalidOptionException{
		int option = scanIntOption();
		if(!(option > 0 && option < 3))
			throw new InvalidOptionException();
		return option;
	}
}
