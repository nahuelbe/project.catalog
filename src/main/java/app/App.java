package app;

import com.globant.project.catalog.Catalog;
import com.globant.project.exceptions.InvalidOptionException;
import com.globant.project.exceptions.login.IncorrectIDException;
import com.globant.project.exceptions.login.IncorrectPasswordException;
import com.globant.project.users.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App 
{
	User loggedIn = null;
	public static App app = new App();
	//private Catalog catalog = Catalog.getInstance();
	
    public static void main( String[] args )
    {
    	while(true)
    		app.welcomeMessage();
    }
    
    public void welcomeMessage(){
    	
    	if(loggedIn == null){
    		guestMsg();
    		guestOptions();
    	}
    	
    	else if(loggedIn.getId().equals("Sheldon")){
    			adminWelcomeMessage();
    			try {
    				int selectedOption = adminSelection();
    				execute(selectedOption);
    			} catch (InvalidOptionException ex){
    				System.out.println(ex.getMessage());
    				welcomeMessage();
    			} catch (InputMismatchException ex){
    				System.out.println(System.getProperty("line.separator") + 
    						"You must enter a valid number" + System.getProperty("line.separator"));
    				welcomeMessage();
    			}
    		}
    		else{
    			userWelcomeMessage();
    			try{
    				int selectedOption = userSelection();
    				loggedIn.execute(selectedOption);
    			} catch (InvalidOptionException ex) { 
    				System.out.println(ex.getMessage());}
    		}
    }
    
    private int userSelection() throws InvalidOptionException{
    	Scanner scan = new Scanner(System.in);
    	int option = scan.nextInt();
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
		Scanner scan = new Scanner(System.in);
    	int option = scan.nextInt();
    	if(option < 1 || option > 8)
    		throw new InvalidOptionException();
    	if(option == 8)
    		logOut();
    	return option;
	}

	private String standardWelcomeMsg(){
    	String newLine = System.getProperty("line.separator");
    	return "Welcome to Comics Catalog, please select an option:" + newLine
    	+ "1. View Catalog" + newLine;
    }
    
    private void guestMsg(){
    	System.out.println(standardWelcomeMsg() + "2. Login");
    }
    
    private void adminWelcomeMessage(){
    	String newLine = System.getProperty("line.separator");
    	System.out.println(standardWelcomeMsg() + "2. Add user" + newLine + "3. Remove user"
    			+ newLine + "4. Add Comic" + newLine + "5. Remove Comic" + newLine +
    			"6. Remove Genre" + newLine + "7. Edit Genre" + newLine +
    			"8. Logout" + newLine);
    }
    
    private void userWelcomeMessage(){
    	String newLine = System.getProperty("line.separator");
    	System.out.println(standardWelcomeMsg() + "2. View my loans" + 
    			newLine + "3. Borrow a comic" + newLine + "4. Logout" + newLine);
    }
    
    public void logOut(){
    	loggedIn = null;
    	welcomeMessage();
    }
    
    private void guestOptions(){
    	Scanner scan = new Scanner(System.in);
    	int option = scan.nextInt();
    	switch (option) {
		case 1:
			showComics();
			break;
		case 2:
			try{ logIn(); } 
			catch (IncorrectPasswordException|IncorrectIDException ex) { 
				System.out.println(ex.getMessage()); 
				welcomeMessage();
			}
			break;

		default:
			
			break;
    	}
    }
    
    private void logIn() throws IncorrectPasswordException,IncorrectIDException {
    	Scanner scan = new Scanner(System.in);
    	System.out.println("Username: ");
    	String id = scan.nextLine();
    	System.out.println("Password: ");
    	String password = scan.nextLine();
    	if(checkUserLogIn(id, password)){
    		loggedIn = getUser(id, password);
    		System.out.println("Welcome " + id);
    		welcomeMessage();
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
}