package app;

import com.globant.project.catalog.Catalog;
import com.globant.project.comic.Comic;
import com.globant.project.exceptions.InvalidGenreException;
import com.globant.project.exceptions.NoGenreException;
import com.globant.project.exceptions.NoMoreComicsException;
import com.globant.project.exceptions.UserExistsException;
import com.globant.project.exceptions.inputs.InvalidComicGenreException;
import com.globant.project.exceptions.inputs.InvalidComicNameException;
import com.globant.project.exceptions.inputs.InvalidOptionException;
import com.globant.project.exceptions.login.IncorrectIDException;
import com.globant.project.exceptions.login.IncorrectPasswordException;
import com.globant.project.interfaces.NextLiner;
import com.globant.project.interfaces.Scanneable;
import com.globant.project.loan.Loan;
import com.globant.project.users.Admin;
import com.globant.project.users.User;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;


public class App implements Scanneable, NextLiner
{
	//User loggedIn = null;
	User loggedIn = Catalog.getInstance().getUsers().get(0);
	public static App app = new App();
	public static boolean run = true;
	
    public static void main( String[] args )
    {
    	while(run)
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
    	if(option < 1 || option > 5)
    		throw new InvalidOptionException();
		return option;
	}

	private int adminSelection() throws InvalidOptionException{
    	int option = scanIntOption();
    	if(option < 1 || option > 11)
    		throw new InvalidOptionException();
    	return option;
	}

	private String standardWelcomeMsg(){
    	return "Welcome to Comics Catalog, please select an option:" + nextLine() + nextLine() +
    	"1. View Catalog" + nextLine();
    }
    
    private void guestMsg(){
    	System.out.println(standardWelcomeMsg() + "2. Login" + nextLine() + "3. Exit" + nextLine());
    }
    
    private void adminshowMenu(){
    	System.out.println(standardWelcomeMsg() + "2. View users" + nextLine() + "3. Add user" + nextLine() + "4. Edit user" + 
    			nextLine() + "5. Remove user" + nextLine() + "6. Add Comic" + nextLine() + 
    			"7. Remove Comic" + nextLine() + "8. Remove Genre" + nextLine() + 
    			"9. Edit Genre" + nextLine() + "10. View Loans" + nextLine() + "11. Logout" + nextLine());
    }
    
    private void usershowMenu(){
    	System.out.println(standardWelcomeMsg() + "2. View my loans" + 
    			nextLine() + "3. Borrow a comic" + nextLine() + "4. Return a comic" + nextLine() + "5. Logout" + nextLine());
    }
    
    public void logOut(){
    	System.out.println(nextLine() + "Goodbye " + loggedIn.getId() + nextLine());
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
		case 3:
			run = false;
			break;
		default:
			if(true)
				throw new InvalidOptionException();
			break;
    	}
    }
    
    private void logIn() throws IncorrectPasswordException,IncorrectIDException {
    	String id = scanStringWithMessage(nextLine() + "Username: "), 
    		   password = scanStringWithMessage("Password: ");
    	if(checkUserLogIn(id,password)){
    		loggedIn = getUser(id, password);
    		System.out.println(nextLine() + "Welcome " + id + nextLine());
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
		if(Catalog.getInstance().getComics().isEmpty())
			System.out.println(nextLine() + "There isn't any comic yet" + nextLine());
		else{
			System.out.println("");
			System.out.println("Comic name - Genre - Copies available" + nextLine());
	    	Catalog.getInstance().getComics().stream().forEach(comic -> System.out.println(comic.getName() + " - " + comic.getGenre() + " - " + comic.getCopies() + nextLine()));
    	}
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
				showUsers();
				break;
			case 3:
				fillUserRegister();
				break;
			case 4:
				editUser();
				break;
			case 5:
				deleteUser();
				break;
			case 6:
				fillComicRegister();
				break;
			case 7:
				fillComicRemove();
				break;
			case 8:
				fillGenreRemove();
				break;
			case 9:
				fillGenreEdit();
				break;
			case 10:
				viewLoans();
				break;
			case 11:
	    		logOut();
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
	
	private void showUsers() {
		if(getAdmin().getUsers().isEmpty()){
			System.out.println(nextLine() + "There isn't any user" + nextLine());
		}
		else{
			System.out.println("");
			getAdmin().getUsers().stream().forEach(user -> System.out.println(user.getId() +  nextLine()));
		}
	}

	private void viewLoans() {
		nextLine();
		List<Loan> loans = getAdmin().getLoans();
		if(!loans.isEmpty()){
			System.out.println(nextLine() + "Username - Comic borrowed" + nextLine());
			getAdmin().getLoans().forEach(loan -> System.out.println(loan.getUser().getId() + " - " + loan.getComic().getName() + nextLine()));
		}
		else
			System.out.println(nextLine() + "There are no loans" + nextLine());
	}

	private void fillGenreEdit() {
		showGenres();
		try{
			if(getAdmin().getGenres().isEmpty())
				System.out.println(nextLine() + "There isn't any genre" + nextLine());
			else{
				getAdmin().editGenre(scanStringWithMessage(nextLine() + "Enter genre to modify: "), 
					scanStringWithMessage("Enter new genre:")); 
				System.out.println(nextLine() + "Genre modified successfully" + nextLine());
			}
		} 
		catch (InvalidGenreException ex){
			System.out.println(nextLine() + ex.getMessage());
			showMenu();
		}
	}

	private void fillGenreRemove() {
		showGenres();
		try {
			if(getAdmin().getGenres().isEmpty()){
				System.out.println("There isn't any genre in the catalog" + nextLine());
			}
			else{
				getAdmin().removeGenre(scanStringWithMessage(nextLine() + "Enter genre to remove: "));
				System.out.println(nextLine() + "Genre removed successfully" + nextLine());
			}
		} catch (InvalidGenreException e) {
			System.out.println(nextLine() + e.getMessage() + nextLine());
			showMenu();
		}
	}

	private void fillComicRemove() {
		showComics();
		try {
			getAdmin().removeComic(scanStringWithMessage(nextLine() + "Enter comic name to remove: "));
			System.out.println(nextLine() + "Comic removed successfully" + nextLine());
		}
		catch (NoSuchElementException ex) { 
			System.out.println(nextLine() + "The comic doesn't exist" + nextLine());
			showMenu();
		}
		
	}

	private void showUserMenu(){
		usershowMenu();
		try{ switch (userSelection()) {
			case 1:
				showCatalogMenu();
				break;
			case 2:
				showUserLoans();
				break;
			case 3:
				borrowAComic();
				break;
			case 4:
				returnAComic();
				break;
			case 5:
				logOut();
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
	
	private void returnAComic() {
		try{
			loggedIn.returnComic(scanStringWithMessage(nextLine() + "Enter comic name to return: "));
			System.out.println(nextLine() + "Comic returned successfully" + nextLine());
		}
		catch (NoSuchElementException ex){
			System.out.println(nextLine() + "The comic doesn't exist" + nextLine());
		}
		catch (Exception ex){
			System.out.println(nextLine() + ex.getMessage() + nextLine());
		}
	}

	private void borrowAComic() {
		showComics();
		try {
			loggedIn.borrowComic(scanStringWithMessage("Enter comic name to borrow: "));
			System.out.println(nextLine() + "Comic borrowed successfully" + nextLine());
		} 
		catch (NoMoreComicsException e) {
			System.out.println(nextLine() + e.getMessage() + nextLine());
		}
		catch (NoSuchElementException e) {
			System.out.println(nextLine() + "The comic doesn't exist" + nextLine());
		}
		
	}

	private void showUserLoans() {
		List<Loan> loans = loggedIn.getLoans();
		if(loans.isEmpty())
			System.out.println(nextLine() + "You don't have any loan" + nextLine());
		else{
			System.out.println("");
			loans.forEach(loan -> System.out.println(loan.getComic().getName() + " - " + loan.getComic().getGenre() + nextLine()));
		}
	}

	private void showCatalogMenu() {
		System.out.println("Catalog visualization preference" + nextLine());
		showCatalogMenuOptions();
		showCatalogPreference(scanIntOption());
	}
	
	private void showCatalogMenuOptions(){
		System.out.println("1. View all comics" + nextLine() + "2. Sort by genre" + nextLine() 
					+ "3. Back" + nextLine());
	}
	
	private void showCatalogPreference(int option) {
		switch (option) {
		case 1:
			showComics();
			showCatalogMenu();
			break;
		case 2:
			try{ selectGenre(); }
			catch (InvalidGenreException|NoGenreException ex){ 
				System.out.println(ex.getMessage()); 
				showCatalogMenu();
			}
			catch(NullPointerException ex){
				System.out.println(nextLine() + "There isn't any comic in the catalog" + nextLine());
				showCatalogMenu();
			}
			break;
		case 3:
			System.out.println("");
			showMenu();
			break;
		default:
			break;
		}
	}
	
	private void selectGenre() throws InvalidGenreException,NoGenreException{
		System.out.println("");
		viewGenres();
		List <Comic> filteredComics = Catalog.getInstance().getComicsByGenre(scanStringWithMessage("Type a genre from the list:" + nextLine()));
		if(filteredComics.isEmpty())
			throw new InvalidGenreException(nextLine() + "The genre is invalid" + nextLine());
		else
			showFilteredcomics(filteredComics);showCatalogMenu();
	}
	
	private void viewGenres() throws NoGenreException { 
		Set<String> genres = Catalog.getInstance().getGenres();
		if(genres.isEmpty())
			throw new NoGenreException("There isn't any genre in the catalog" + nextLine());
		else
			genres.stream().forEach(genre -> System.out.println(genre + nextLine()));
	}
	
	private void fillUserRegister(){
		try {
			getAdmin().registerUser(scanStringWithMessage(nextLine() + "Enter new user ID: "),
						scanStringWithMessage("Enter new user password: "));
			System.out.println(nextLine() + "User added successfully" + nextLine());
		} 
		catch (UserExistsException e) {
			System.out.println(nextLine() + "Failed to register new user" + nextLine() + 
								nextLine() + e.getMessage() + nextLine());
		}
		catch (Exception e) {
			System.out.println(nextLine() + e.getMessage() + nextLine());
		}
	}
	
	private void editUser(){
		showUsers();
		showEditOptions();
		try{
			switch (editOptionSelection()) {
			case 1:
				try {
					String id = scanStringWithMessage(nextLine() + "Enter the actual user ID:");
					if(id.equals("Sheldon")){
						System.out.println(nextLine() + "Invalid operation, can't edit admin username");
						editUser();
					}
					else{
						getAdmin().editUsername(id, scanStringWithMessage("Enter the new user ID:"));
						System.out.println("");
						System.out.println("Username edited successfully");
						System.out.println("");
					}
				} catch (UserExistsException e) {
					System.out.println(nextLine() + e.getMessage() + nextLine());
					editUser();
				}
				catch (Exception e) {
					System.out.println(nextLine() + e.getMessage() + nextLine());
					editUser();
				}
				break;
			case 2:
				try{
					getAdmin().editPassword(scanStringWithMessage(nextLine() + "Enter the ID to change the password: " ), 
							scanStringWithMessage("Enter the new password: "));
					System.out.println("");
					System.out.println("User password edited successfully");
					System.out.println("");
					editUser();
				}
				catch (Exception e) {
					System.out.println(nextLine() + e.getMessage() + nextLine());
					editUser();
				}
				break;
			case 3:
				System.out.println("");
				showMenu();
				break;
			default:
				break;
			}
		} 
		catch (InputMismatchException ex){
			System.out.println(nextLine() + 
			"You must enter a valid number"); 
			editUser();
		}
		catch (InvalidOptionException ex) {
			System.out.println(ex.getMessage());
			editUser();
		}
		catch(NoSuchElementException ex){
			System.out.println(nextLine() + "The ID doesn't exist");
			editUser();
		}
	}
	
	private void showEditOptions() {
		System.out.println("1. Edit user ID" + nextLine() + "2. Edit user password" + nextLine() + "3. Back" + nextLine());
	}
	
	private void showGenres() {
		System.out.println("");
		getAdmin().getGenres().stream().forEach(genre -> System.out.println(genre));
	}
	
	private int editOptionSelection() throws InvalidOptionException{
		int option = scanIntOption();
		if(!(option > 0 && option < 4))
			throw new InvalidOptionException();
		return option;
	}
	
	private void showFilteredcomics(List<Comic> filteredComics) {
		System.out.println(nextLine() + "Comic name - Genre - Copies available" + nextLine());
		filteredComics.stream().forEach(comic -> System.out.println(comic.getName() + " - " + comic.getGenre() + " - " + comic.getCopies() + nextLine()));
	}
	
	private void deleteUser(){
		showUsers();
		String idToRemove = scanStringWithMessage("Enter the ID to remove: ");
		if(idToRemove.equals("Sheldon")){
			System.out.println(nextLine() + "You can't remove yourself" + nextLine());
			showMenu();
		}
		else if(getAdmin().revokeUser(idToRemove))
			System.out.println(nextLine() + "User removed successfully");
		else
			System.out.println(nextLine() + "The ID doesn't exist" + nextLine());
	}
	
	private void fillComicRegister() {
		try {
			getAdmin().registerComic(new Comic(scanStringWithMessage(nextLine() + "Enter new Comic name: "),
							scanStringWithMessage("Enter genre of new comic: ")));
			System.out.println("");
		} catch (InvalidComicNameException | InvalidComicGenreException e) {
			System.out.println(e.getMessage() + nextLine());
		}
		
	}
	
	private Admin getAdmin(){
		Admin admin = (Admin) loggedIn;
		return admin;
	}

}