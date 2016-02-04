package app;

import com.globant.project.catalog.Catalog;
import com.globant.project.exceptions.InvalidOptionException;
import com.globant.project.exceptions.login.IncorrectIDException;
import com.globant.project.exceptions.login.IncorrectPasswordException;
import com.globant.project.interfaces.NextLiner;
import com.globant.project.interfaces.Scanneable;
import com.globant.project.users.User;

import java.util.InputMismatchException;

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

	private void execute(int selectedOption) {
    	loggedIn.execute(selectedOption);
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
    	return "Welcome to Comics Catalog, please select an option:" + nextLine()
    	+ "1. View Catalog" + nextLine();
    }
    
    private void guestMsg(){
    	System.out.println(standardWelcomeMsg() + "2. Login");
    }
    
    private void adminshowMenu(){
    	System.out.println(standardWelcomeMsg() + "2. Add user" + nextLine() + "3. Edit user" + 
    			nextLine() + "4. Remove user" + nextLine() + "5. Add Comic" + nextLine() + 
    			"6. Remove Comic" + nextLine() + "7. Remove Genre" + nextLine() + 
    			"8. Edit Genre" + nextLine() + "9. Logout" + nextLine());
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
			showComics();
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
    		System.out.println("Welcome " + id);
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
    	Catalog.getInstance().getComics();
    }
	
	private boolean isThereAUserLoggedIn(){
		return loggedIn == null;
	}
	
	private boolean isAdminLoggedIn(){
		return loggedIn.getId().equals("Sheldon");
	}
	
	private void showAdminMenu(){
		adminshowMenu();
		try { execute(adminSelection()); } 
		catch (InvalidOptionException ex){
			System.out.println(ex.getMessage());
			showMenu(); } 
		catch (InputMismatchException ex){
			System.out.println(nextLine() + 
			"You must enter a valid number" + nextLine());
			showMenu();
		}
	}
	
	private void showUserMenu(){
		usershowMenu();
		try{ loggedIn.execute(userSelection()); } 
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

}