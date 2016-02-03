package com.globant.project.catalog;

import com.globant.project.exceptions.InvalidOptionException;
import com.globant.project.users.Admin;
import com.globant.project.users.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App 
{
	User loggedIn = new Admin("Sheldon","Bazinga");
	public static App app = new App();
	private Catalog catalog = Catalog.getInstance();
	
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
    		}
    }
    
    private void execute(int selectedOption) {
    	loggedIn.execute(selectedOption);
    }

	private int adminSelection() throws InvalidOptionException{
		Scanner scan = new Scanner(System.in);
    	int option = scan.nextInt();
    	if(option < 1 || option > 8)
    		throw new InvalidOptionException();
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
    }
    
    private void guestOptions(){
    	/*
    	switch (key) {
		case value:
			
			break;

		default:
			break;
		}*/
    }
}