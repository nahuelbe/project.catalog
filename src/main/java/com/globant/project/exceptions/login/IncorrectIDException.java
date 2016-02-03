package com.globant.project.exceptions.login;

@SuppressWarnings("serial")
public class IncorrectIDException extends Exception {
	public IncorrectIDException() {
		super("The ID is doesn't exist");
	}
}
