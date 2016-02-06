package com.globant.project.exceptions.inputs;

@SuppressWarnings("serial")
public class InvalidOptionException extends Exception {
	public InvalidOptionException() {
		super(System.getProperty("line.separator") + "Invalid option selected." + 
				System.getProperty("line.separator") + "Please select a valid number." + System.getProperty("line.separator"));
	}
}
