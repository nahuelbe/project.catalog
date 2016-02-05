package app;

import com.globant.project.catalog.Catalog;
import com.globant.project.comic.Comic;
import com.globant.project.exception.NoGendersException;
import com.globant.project.exceptions.InvalidGenreException;
import com.globant.project.exceptions.InvalidOptionException;
import com.globant.project.exceptions.login.IncorrectIDException;
import com.globant.project.exceptions.login.IncorrectPasswordException;
import com.globant.project.interfaces.NextLiner;
import com.globant.project.interfaces.Scanneable;
import com.globant.project.users.Admin;
import com.globant.project.users.User;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;

public class App implements Scanneable, NextLiner
{
	User loggedIn = null;
	public static App app = new App();
	
    public static void main( String[] args )
    {
    	while(true)
    		app.showMenu();
    }
    
    public void showMenu(){
    	if(isThereAUserLoggedIn())
    		showGuestMenu();
    	else if(isAdminLoggedIn())
    			showAdminMenu();
    		else
    			showUserMenu();
    }
    
    private void showGuestMenu() {
    	guestMsg();
    	try{ guestOptions(); }
    	catch (InvalidOptionException ex){
    		System.out.println(ex.getMessage());
    		showMenu();
    	}
    	catch (InputMismatchException e) {
    		System.out.println(nextLine() + 
    		"You must enter a number as an option" + nextLine());
    		showMenu();
		}
	}

	private int userSelection() throws InvalidOptionException{
    	int option = scanIntOption();
    	if(option < 1 || option > 4)
    		throw new InvalidOptionException();
    	if(option == 4)
    		logOut();
		return option;
	}

	private int adminSelection() throws InvalidOptionException{
    	int option = scanIntOption();
    	if(option < 1 || option > 9)
    		throw new InvalidOptionException();
    	if(option == 9)
    		logOut();
    	return option;
	}

	private String standardWelcomeMsg(){
    	return nextLine() + "Welcome to Comics Catalog, please select an option:" + nextLine()
    	+ "1. View Catalog" + nextLine();
    }
    
    private void guestMsg(){
    	System.out.println(standardWelcomeMsg() + "2. Login");
    }
    
    private void adminshowMenu(){
    	System.out.println(standardWelcomeMsg() + "2. Add user" + nextLine() + "3. Edit user" + 
    			nextLine() + "4. Remove user" + nextLine() + "5. Add Comic" + nextLine() + 
    			"6. Remove Comic" + nextLine() + "7. Remove Genre" + nextLine() + 
    			"8. Edit Genre" + nextLine() + "9. Logout");
    }
    
    private void usershowMenu(){
    	System.out.println(standardWelcomeMsg() + "2. View my loans" + 
    			nextLine() + "3. Borrow a comic" + nextLine() + "4. Logout" + nextLine());
    }
    
    public void logOut(){
    	loggedIn = null;
    	showMenu();
    }
    
    @SuppressWarnings("unused")
	private void guestOptions() throws InvalidOptionException{
    	switch (scanIntOption()) {
		case 1:
			showCatalogMenu();
			break;
		case 2:
			try{ logIn(); } 
			catch (IncorrectPasswordException|IncorrectIDException ex) { 
				System.out.println(nextLine() + ex.getMessage() + nextLine()); 
				showMenu();
			}
			break;
		default:
			if(true)
				throw new InvalidOptionException();
			break;
    	}
    }
    
    private void logIn() throws IncorrectPasswordException,IncorrectIDException {
    	String id = scanStringWithMessage("Username: "), 
    		   password = scanStringWithMessage("Password: ");
    	if(checkUserLogIn(id,password)){
    		loggedIn = getUser(id, password);
    		System.out.println(nextLine() + "Welcome " + id);
    		showMenu();
    	}
    	else if (userExists(id, password))
    		throw new IncorrectPasswordException();
    	else
    		throw new IncorrectIDException();
	}
    
    private boolean userExists(String id, String password) {
		return Catalog.getInstance().userExists(id,password);
	}

	private boolean checkUserLogIn(String id, String password){
    	return Catalog.getInstance().checkUserProperties(id,password);
    }
    
    private User getUser(String id, String password){
    	return Catalog.getInstance().getUsers().stream().filter(user -> user.getId().equals(id) && user.getPassword().equals(password)).findFirst().get();
    }

	private void showComics(){
		System.out.println(nextLine());
    	Catalog.getInstance().getComics().stream().forEach(comic -> System.out.println(comic.getName()));;
    }
	
	private boolean isThereAUserLoggedIn(){
		return loggedIn == null;
	}
	
	private boolean isAdminLoggedIn(){
		return loggedIn.getId().equals("Sheldon");
	}
	
	private void showAdminMenu(){
		adminshowMenu();
		try {
			switch (adminSelection()) {
			case 1:
				showCatalogMenu();
				break;
			case 2:
				fillUserRegister();
				break;
			case 3:
				editUser();
				break;
			case 4:
				deleteUser();
				break;
			case 5:
				fillComicRegister();
				break;
			case 6:
				fillComicRemove();
				break;
			case 7:
				fillGenreRemove();
				break;
			case 8:
				fillGenreEdit();
				break;
			default :
				break;
			}
		}
		catch (InvalidOptionException ex){
			System.out.println(ex.getMessage());
			showMenu(); } 
		catch (InputMismatchException ex){
			System.out.println(nextLine() + 
			"You must enter a valid number" + nextLine());
			showMenu();
		}
	}
	
	private void fillGenreEdit() {
		// TODO Auto-generated method stub
		
	}

	private void fillGenreRemove() {
		// TODO Auto-generated method stub
		
	}

	private void fillComicRemove() {
		// TODO Auto-generated method stub
		
	}

	private void showUserMenu(){
		usershowMenu();
		try{ switch (userSelection()) {
			case 1:
				showCatalogMenu();
				break;
			case 2:
				showUserLoans();
			default:
				break;
			}; 
		} 
		catch (InvalidOptionException ex) { 	
			System.out.println(ex.getMessage());
			showMenu();
		}
		catch (InputMismatchException ex){
			System.out.println(nextLine() + 
				"You must enter a valid number" + nextLine());
			showMenu();
		}
	}
	
	private void showUserLoans() {
		// TODO Auto-generated method stub
		
	}

	private void showCatalogMenu() {
		System.out.println("Catalog visualization preference" + nextLine());
		showCatalogMenuOptions();
		showCatalogPreference(scanIntOption());
	}
	
	private void showCatalogMenuOptions(){
		System.out.println("1. View all comics" + nextLine() + "2. Sort by genre" + nextLine());
	}
	
	private void showCatalogPreference(int option) {
		switch (option) {
		case 1:
			showComics();
			break;
		case 2:
			try{ selectGenre(); }
			catch (InvalidGenreException|NoGendersException ex){ 
				System.out.println(ex.getMessage()); 
				showCatalogMenu();
			}
			
		default:
			break;
		}
	}
	
	private void selectGenre() throws InvalidGenreException,NoGendersException{
		viewGenres();
		List <Comic> filteredComics = loggedIn.getComicsByGenre(scanStringWithMessage("Type a genre from the list:" + nextLine()));
		if(filteredComics.isEmpty())
			throw new InvalidGenreException(nextLine() + "The genre is invalid" + nextLine());
		else
			showFilteredcomics(filteredComics);
	}
	
	private void viewGenres() throws NoGendersException { 
		Set<String> genres = loggedIn.getGenres();
		if(genres.isEmpty())
			throw new NoGendersException(nextLine() + "There isn't any gender in the catalog" + nextLine());
		else
			genres.stream().forEach(genre -> System.out.println(genre + nextLine()));
	}
	
	private void fillUserRegister(){
		getAdmin().registerUser(scanStringWithMessage(nextLine() + "Enter new user ID: " + nextLine()),
					scanStringWithMessage(nextLine() + "Enter new user password: " + nextLine()));
		
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
	
	private int editOptionSelection() throws InvalidOptionException{
		int option = scanIntOption();
		if(!(option > 0 && option < 3))
			throw new InvalidOptionException();
		return option;
	}
	
	private void showFilteredcomics(List<Comic> filteredComics) {
		filteredComics.stream().forEach(comic -> System.out.println(comic.getName() + nextLine()));
	}
	
	private void deleteUser(){
		getAdmin().revokeUser(scanStringWithMessage("Enter the ID to remove: "));
	}
	
	private void fillComicRegister() {
		getAdmin().registerComic(new Comic(scanStringWithMessage(nextLine() + "Enter new Comic name: "),
						scanStringWithMessage("Enter genre of new comic: ")));
	}
	
	private Admin getAdmin(){
		Admin admin = (Admin) loggedIn;
		return admin;
	}

}