package com.globant.project.exceptions.login;

@SuppressWarnings("serial")
public class IncorrectPasswordException extends Exception {
	public IncorrectPasswordException(){
		super("The password doesn't match with ID");
	}
}
