package com.globant.project.catalog;

import java.util.Scanner;

import com.globant.project.users.User;

public class App 
{
	User loggedIn = null;
	public static App app = new App();
	private Catalog catalog = Catalog.getInstance();
	
    public static void main( String[] args )
    {
    	app.welcomeMessage();
    	Scanner sc = new Scanner(System.in);
        int i = sc.nextInt();
    }
    
    public void welcomeMessage(){
    	if(loggedIn == null){
    		guestMsg();
    	}
    	else if(loggedIn.getId().equals("Sheldon")){
    			adminWelcomeMessage();
    		}
    		else{
    			userWelcomeMessage();
    		}
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
    			"8. Logout");
    }
    
    private void userWelcomeMessage(){
    	String newLine = System.getProperty("line.separator");
    	System.out.println(standardWelcomeMsg() + "2. View my loans" + 
    			newLine + "3. Borrow a comic" + newLine + "4. Logout");
    }
    
}